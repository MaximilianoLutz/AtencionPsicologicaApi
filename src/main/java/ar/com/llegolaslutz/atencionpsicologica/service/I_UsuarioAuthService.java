package ar.com.llegolaslutz.atencionpsicologica.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;


public interface I_UsuarioAuthService extends CrudRepository<UserAuth, String> {

	public UserAuth findByUsername(String username);
	
	public UserAuth findByEmail(String email);
	
    public UserAuth findByUid(String uid);
    
    public UserAuth save(UserAuth userAuth);
    
    public void delete(UserAuth userAuth);
	

	
}
