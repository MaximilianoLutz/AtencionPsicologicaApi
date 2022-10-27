package ar.com.llegolaslutz.atencionpsicologica.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	public AuthenticationManager authenticationManager;
	
	@Autowired
	public InfoAdicionaToken infoAdicionaToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		security.tokenKeyAccess("permitAll()") //permiso de ingreso a la ruta del login.. generar el token al utenticarse
		.checkTokenAccess("isAuthenticated()"); //dar permiso al endpoint q se encarga de validar el touen
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory()
			.withClient("PacientesFront")// credenciales de app cliente
			.secret(bCryptPasswordEncoder.encode("1234$"))
			.scopes("read", "write")                                                        //tipica pag de login
			.authorizedGrantTypes("password", "refresh_token") // enviamos credenciales de usuario.. usuario y contrase;a existen otras 2 formas vid 131 min 3
			.accessTokenValiditySeconds(3600)
			.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain =  new TokenEnhancerChain();
		
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionaToken ,accessTokenConverter())); // agrego info adicional a la x defecto en la clase  infoAdicionaToken
		

		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()) // no es necesario x q lo hace
																						// automaticamnente
				.accessTokenConverter(accessTokenConverter())   // traduce info del token y valida la firma
				.tokenEnhancer(tokenEnhancerChain); 
	}

	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtCinfig.CLAVE_SECRETA);
//		jwtAccessTokenConverter.setSigningKey(JwtCinfig.CLAVE_PRIVADA);
//		jwtAccessTokenConverter.setVerifierKey(JwtCinfig.CLAVE_PUBLICA);
		return jwtAccessTokenConverter;
	}

}
