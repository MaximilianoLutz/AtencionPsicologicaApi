package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api")
public class UserController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	public I_UsuarioAuthService usuarioService;

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	

	
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
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

	/*
	 * Forgotten password controller
	 */
	@PostMapping("/forgottenPassword")
	public Boolean processForgotPassword(@RequestBody Forgotten data) {
		
		String email = data.getEmail();
		 String token = RandomString.make(30);
		try {
			usuarioService.updateResetPasswordToken(token, email);
			
			String resetPasswordLink = "doredin" + "/reset_password?token=" + token;
			System.out.println(resetPasswordLink);
			sendEmail(email, resetPasswordLink);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	@PostMapping("/restorePassword")
	public Boolean processRestorePassword(@RequestBody restorePassword data ) {
		
		String token = data.getToken();
		String password= data.getNewPassword();
		
		UserAuth user = usuarioService.getByResetPasswordToken(token);
		
		if (user != null) {
			usuarioService.updatePassword(user, password);
			
			return true;
		}else {
			return false;
		}
		
	}

	public void sendEmail(String recipientEmail, String link)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("mlutzdev@gmail.com", "Atención Psicológica App");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Aquí se encuentra el link para reestablecer tu contraseña";
	     
	    String content = "<p>Hola,</p>"
	            + "<p>Has solicitado reestablecer tu contraseña.</p>"
	            + "<p>Puedes hacer click en el enlace que se encuentra debajo:</p>"
	            + "<p><a href=\"" + link + "\">Cambiar contraseña</a></p>"
	            + "<br>"
	            + "<p>Ignora este email si recuerdas tu contraseña, "
	            + "o si no hubieses solicitado el cambio de contraseña.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}

}

 class Forgotten{
	 private String email;
	 private String url;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
 }
	
	 class restorePassword{
		 private String newPassword;
		 private String token;
		 
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		} 
	 
	 
 }
