package ar.com.llegolaslutz.atencionpsicologica.service;

import java.util.List;
import java.util.Optional;
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

@Service							//Interfaz de spring security
public class UsuarioAuthService implements UserDetailsService, I_UserAuthRepository{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioAuthService.class);
	
	@Autowired
	public I_UserAuthRepository userAuthRepository;
	

	@Override
	@Transactional(readOnly = true) 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//UserAuth user = userAuth.findByUsername(username);
		UserAuth user = userAuthRepository.findByEmail(username);
		
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
	

	@Override
	@Transactional(readOnly = true)
	public UserAuth findByUsername(String username) {
		
		return userAuthRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public UserAuth findByUid(String uid) {
		
		return userAuthRepository.findByUid(uid);
	}

	@Override
	@Transactional
	public UserAuth save(UserAuth userAuth) {
		
		return userAuthRepository.save(userAuth);
	}

	@Override
	@Transactional
	public void delete(UserAuth userAuth) {
		userAuthRepository.delete(userAuth);		
	}

	@Override
	@Transactional(readOnly = true)
	public UserAuth findByEmail(String email) {
		
		return userAuthRepository.findByEmail(email);
	}


	@Override
	public <S extends UserAuth> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<UserAuth> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Iterable<UserAuth> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterable<UserAuth> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAll(Iterable<? extends UserAuth> entities) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}


}
