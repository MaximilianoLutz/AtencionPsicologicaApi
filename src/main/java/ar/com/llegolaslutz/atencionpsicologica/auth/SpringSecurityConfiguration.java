package ar.com.llegolaslutz.atencionpsicologica.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)  // para habilitar proteccion de las rutas a traves de @Secured
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService usuarioAuthService; //interfaz de spring q implementa nuestra clase UsuarioAuthService.. spring solo busca la implementacion

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired //se inyecta via argumento este componente de Sprig  AuthenticationManagerBuilder auth
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService( this.usuarioAuthService ).passwordEncoder(passwordEncoder());
	}

	
	@Override
	@Bean("authenticationManager") //no es necesario el nombre aca
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}


	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
			.and() //came back to the  HttpSecurity http configuration
			.csrf().disable() //no la necesitamos en el back x q estamos trabajando con el frontend x separado
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //desabilitamos sesiones, spring sec se usn cuaando el frontend es parte del mismo proyecto la dejamos stateless    
			
	} 	

}
