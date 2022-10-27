package ar.com.llegolaslutz.atencionpsicologica.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_UserAuthRepository;

@Service									//Interfaz de spring security
public class UsuarioAuthService implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioAuthService.class);
	
	@Autowired
	public I_UserAuthRepository userAuth;
	

	@Override
	@Transactional(readOnly = true) 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//UserAuth user = userAuth.findByUsername(username);
		UserAuth user = userAuth.findByEmail(username);
		
		if( user == null) {
			logger.error("no existe el usuario '" + username + "' en el sistema");
			throw new UsernameNotFoundException("no existe el usuario '" + username + "' en el sistema");
		}
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()   //parecido a RxJS
				.map( role -> new SimpleGrantedAuthority(role.getName())) // = a role -> { return new SimpleGrantedAuthority(role.getName()} con 1 solo rtrn no tiene sentido
				.peek( authority -> logger.info("Role: "+ authority.getAuthority() ))
				.collect(Collectors.toList());
		
		return new User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

}
