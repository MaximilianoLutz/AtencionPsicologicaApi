package ar.com.llegolaslutz.atencionpsicologica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;


public interface I_UserAuthRepository extends CrudRepository<UserAuth, String> {
	
	public UserAuth findByUsername(String username);
	
	public UserAuth findByEmail(String emai);
	
	public UserAuth findByUid(String uid);
	
	public UserAuth findByResetPasswordToken(String token);
	

}
