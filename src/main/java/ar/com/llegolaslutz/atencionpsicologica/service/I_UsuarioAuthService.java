package ar.com.llegolaslutz.atencionpsicologica.service;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;

public interface I_UsuarioAuthService {

	public UserAuth findByUsername(String username);
	
	public UserAuth findByEmail(String email);
	
    public UserAuth findByUid(String uid);
    
    public UserAuth save(UserAuth userAuth);
    
    public void delete(UserAuth userAuth);
	

	
}
