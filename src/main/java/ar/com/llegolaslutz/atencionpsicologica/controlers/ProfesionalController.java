package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;
import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;
import ar.com.llegolaslutz.atencionpsicologica.service.I_PacienteService;
import ar.com.llegolaslutz.atencionpsicologica.service.I_UsuarioAuthService;

import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/api/entrypoint")
public class ProfesionalController {

	@Autowired
	public I_PacienteService pacienteService;
	
	@Autowired 
	public I_UsuarioAuthService usuarioService;
	

	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping("/listarPacientes/{prof}")
	public List<Paciente> getAll(@PathVariable String prof, @RequestHeader("Authorization") String token) {

		Profesional profesional = pacienteService.getProfesionalByIdHex(prof);


		Base64.Decoder decoder = Base64.getUrlDecoder();

		String[] chunks = token.split("[ .]");

		String payload = new String(decoder.decode(chunks[2]));

		String userId = payload.substring(8, 40);
		
		logger.info("----------------------------------------------------------------------");
		logger.info(payload);
		logger.info(profesional.getuAuth().getUid());
		logger.info("----------------------------------------------------------------------");
		if(!profesional.getuAuth().getUid().equals(userId)) {
			UserAuth userHacked = usuarioService.findByUid(userId);
			userHacked.setEnabled(false);
			usuarioService.save(userHacked);
			logger.info("-444444444444444444444444444444444444444444444444------------------------------------------------");
			return new ArrayList<Paciente>();
		}
		
		logger.info(profesional.getuAuth().getUid());

		logger.info("----------------------------------------------------------------------");

		logger.info(profesional.getPacientes());

		return profesional.getPacientes();

	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/profesional")
	public List<Profesional> getAllCheck() {

		return pacienteService.findAllProfesional();

	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/profesionales/{userId}")
	public List<Profesional> getListProfByUser(@PathVariable String userId) {
		
		UserAuth user = usuarioService.findByUid(userId);
		
		logger.info(user);

		return user.getProfesionales();

	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/profesional")
	public Profesional saveProfesional(@RequestBody Profesional profesional) {
		if (profesional == null)
			return null;
		
		if (profesional.getuAuth() == null)
			return null;
		
		return pacienteService.saveProfesional(profesional);
	}

	@GetMapping("/profesional/{id}")
	public Profesional getProfesional(@PathVariable String id) {
		return pacienteService.getProfesionalByIdHex(id);
	}

	@GetMapping("/pacientes/{id}")
	public Paciente getById(@PathVariable String id, @RequestBody Profesional prof) {
		if (prof == null || id == null)
			return new Paciente();
		// logger.info(prof);
		Paciente paciente = new Paciente();
		List<Paciente> lista = new ArrayList<Paciente>();
		List<Paciente> lista2 = new ArrayList<Paciente>();
		lista = pacienteService.findAll();
		// logger.info(prof.getId());
		// lista.forEach(System.out::println);
		String idProf = prof.getIdHex();
		lista2 = lista.stream().filter(p -> p.getP().getIdHex().equals(idProf)).collect(Collectors.toList());
		lista2.forEach(System.out::println);
		paciente = lista2.stream().filter(pa -> pa.getId() == id).findAny().orElse(new Paciente());

		logger.info(id);
		return paciente;

	}

	@GetMapping("/paciente/{id}")
	public Paciente getByIdLong(@PathVariable String id, @RequestBody String prof) {
		if (prof == null || id == null)
			return new Paciente();

		Profesional profesional = pacienteService.getProfesionalByIdHex(prof);
		return profesional.getPacientes().stream().filter(p -> p.getId() == id).findAny().orElse(new Paciente());

	}
}