package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.Role;
import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;
import ar.com.llegolaslutz.atencionpsicologica.service.I_UsuarioAuthService;
import ch.qos.logback.core.net.SyslogOutputStream;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api")
public class UserController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired 
	public I_UsuarioAuthService usuarioService;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/user/{id}")
	public UserAuth getUser(@PathVariable String id) {
		
		UserAuth user = usuarioService.findByUid(id);
		
		return user;
	}
	
	
	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserAuth createUser(@RequestBody UserAuth userAuth) {
		
		logger.info(userAuth);
		Role role = new Role();
		role.setUid(1L);
		role.setName("ROLE_USER");
		List<Role> roles = Arrays.asList(role);
		String pass = userAuth.getPassword();
		
		userAuth.setPassword(bCryptPasswordEncoder.encode(pass));
		userAuth.setRoles(roles);
		
		return usuarioService.save(userAuth);
	}
	
	@PutMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserAuth updateUser(@RequestBody UserAuth userAuth) {
		
		logger.info(userAuth);
	
		UserAuth uAuth = usuarioService.findByUid(userAuth.getUid());
		
		logger.info(uAuth);
	
		uAuth.setEnabled(true);
		return usuarioService.save(uAuth);
	}

}
