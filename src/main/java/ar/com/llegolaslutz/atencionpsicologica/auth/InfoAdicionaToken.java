package ar.com.llegolaslutz.atencionpsicologica.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;
import ar.com.llegolaslutz.atencionpsicologica.service.I_UsuarioAuthService;

@Component
public class InfoAdicionaToken implements TokenEnhancer {
	
	@Autowired
	public I_UsuarioAuthService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		UserAuth usuario = usuarioService.findByEmail(authentication.getName());
		
		Map<String, Object> info= new HashMap<>();
		info.put("Name", authentication.getName());
		info.put("Uid", usuario.getUid());
		
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
