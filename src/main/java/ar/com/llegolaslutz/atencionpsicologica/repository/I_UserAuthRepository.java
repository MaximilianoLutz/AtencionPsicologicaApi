package ar.com.llegolaslutz.atencionpsicologica.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;

public interface I_UserAuthRepository extends CrudRepository<UserAuth, String> {
	
	public UserAuth findByUsername(String username);
	
	public UserAuth findByEmail(String emai);
	
//	@Query("select u from User u where u.username=?1")
//	public UserAuth findByUsername2(String username);
	
	public UserAuth findByUid(String uid);

}
