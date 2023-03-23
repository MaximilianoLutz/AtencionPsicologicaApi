package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
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

import ar.com.llegolaslutz.atencionpsicologica.entity.Contacto;
import ar.com.llegolaslutz.atencionpsicologica.entity.DatosFiliatorios;
import ar.com.llegolaslutz.atencionpsicologica.entity.HistoriaClinica;
import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;
import ar.com.llegolaslutz.atencionpsicologica.service.I_PacienteService;



@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api")
public class PacienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired 
	public I_PacienteService pacienteService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/check_token")
	public String checkToken(){
		String ok = "true";
		return ok;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/pacientes")
	public List<Paciente> getAll(){
		return pacienteService.findAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"}) // si no pongo nada la ruta seria publica
	@GetMapping("/pacientes/{id}")
	public Paciente getById(@PathVariable String id) {		
		return pacienteService.getPacienteById(id);
	}
	
	@GetMapping("/pacientesP/{idHex}")
	public List<Paciente> getByIdP(@PathVariable String idHex) {		
		
		//String idHex = "1234";
		Profesional profe =  
				pacienteService
				.findAllProfesional()
				.stream().filter(p -> p.getIdHex().equals(idHex)).findAny().orElse(new Profesional());
		
		logger.info(profe);
		
		
		 
		 return profe.getPacientes();
	}
	
	@PostMapping("/pacientes")
	@ResponseStatus(HttpStatus.CREATED) // x default es 200 .OK
	public Paciente save(@RequestBody Paciente paciente) {
		Contacto c = new Contacto();
		logger.info(paciente.getP().getIdHex());
		paciente.setP(pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex()));
		logger.info("---------------------------------------------------------------");
		logger.info(paciente);

		return pacienteService.savePaciente(paciente);		
		
	}
	
	@PutMapping("/pacientes")
	@ResponseStatus(HttpStatus.CREATED) 
	public Paciente update(@RequestBody Paciente paciente) {
		logger.info(paciente);
		Paciente pacienteActual = pacienteService.getPacienteById(paciente.getId());
		
		pacienteActual.setNombre(paciente.getNombre());
		pacienteActual.setApellido(paciente.getApellido());
		pacienteActual.setDni(paciente.getDni());
	
		
		logger.info(pacienteActual);
		//paciente.setContacto();
		
		return pacienteService.savePaciente(pacienteActual);
	}
	
	@PostMapping("/datos")
	@ResponseStatus(HttpStatus.CREATED)
	public DatosFiliatorios saveDatos(@RequestBody Paciente paciente) {
		
		//solo podra acceder a los pacientes del usuario activo
		Profesional profe =  pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());
			
			
			Paciente pacienteDb  = profe.getPacientes()
							.stream()
							.filter(p -> p.getId() == paciente.getId()).findAny().orElse(null);
			
			if(pacienteDb.getDatosFiliatorios() != null) {
				if(pacienteDb.getDatosFiliatorios().getId() > 0) logger.info(pacienteDb.getDatosFiliatorios().getId());
				
				paciente.getDatosFiliatorios().setId(pacienteDb.getDatosFiliatorios().getId());
				pacienteDb.setDatosFiliatorios(paciente.getDatosFiliatorios());
				logger.info(pacienteDb.getDatosFiliatorios());
				return pacienteService.saveDatosFiliatorios(pacienteDb.getDatosFiliatorios());
				
			}
			DatosFiliatorios datos = paciente.getDatosFiliatorios();
			datos.setIdPaciente(pacienteDb);
			
			return pacienteService.saveDatosFiliatorios(datos);
		
		
	}
	@PutMapping("/datos")
	@ResponseStatus(HttpStatus.CREATED)
	public DatosFiliatorios updateDatos(@RequestBody Paciente paciente) {
		
		//solo podra acceder a los pacientes del usuario activo
		Profesional profe =  pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());

			Paciente pacienteDb  = profe.getPacientes()
							.stream()
							.filter(p -> p.getId() == paciente.getId()).findAny().orElse(null);
			
			if(pacienteDb.getDatosFiliatorios() != null) {
				if(pacienteDb.getDatosFiliatorios().getId() > 0) logger.info(pacienteDb.getDatosFiliatorios().getId());

				
				paciente.getDatosFiliatorios().setId(pacienteDb.getDatosFiliatorios().getId());
				paciente.getDatosFiliatorios().setIdPaciente(pacienteDb);
				pacienteDb.setDatosFiliatorios(paciente.getDatosFiliatorios());
				logger.info(pacienteDb.getDatosFiliatorios());
				return pacienteService.saveDatosFiliatorios(pacienteDb.getDatosFiliatorios());
				
				
			}
			
			DatosFiliatorios datos = paciente.getDatosFiliatorios();
			datos.setIdPaciente(pacienteDb);
			
			logger.info(pacienteDb.getDatosFiliatorios());
			
			
			return pacienteService.saveDatosFiliatorios(datos);
		
		
	}
	
	@PutMapping("/contacto")
	@ResponseStatus(HttpStatus.CREATED)
	public Contacto saveContacto(@RequestBody Paciente paciente) {
		//solo podra acceder a los pacientes del usuario activo
			Profesional profe =  pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());
								
				Paciente pacienteDb  = profe.getPacientes()
								.stream()
								.filter(p -> p.getId() == paciente.getId()).findAny().orElse(null);
				

				
				if(pacienteDb.getContacto() != null) {
					if(pacienteDb.getContacto().getId() > 0) logger.info(pacienteDb.getContacto().getId());

					paciente.getContacto().setId(pacienteDb);
					pacienteDb.setContacto(paciente.getContacto());
					
					logger.info(pacienteDb.getContacto());
					return pacienteService.saveContacto(pacienteDb.getContacto());
					
					
				}
				Contacto contacto = paciente.getContacto();
				contacto.setIdPaciente(pacienteDb);
				
				logger.info(pacienteDb.getContacto());
				
				
				return pacienteService.saveContacto(contacto);

				
	}
	
	//HISTORIA CLINICA
	
	@PostMapping("/historia")
	public HistoriaClinica saveHistoria(@RequestBody HistoriaClinica historia) {
		if(historia.getIdPaciente() == null) return new HistoriaClinica();
		
		
		LocalDate fecha = LocalDate.from(historia.getDate());
		 
		int compare = fecha.compareTo(LocalDate.now());
		logger.info(fecha);
		logger.info(compare);

		return pacienteService.saveHistoria(historia);
	}
	
	
	@GetMapping("/historia/{date}{idPaciente}")
	public ResponseEntity<Resource> getHistoriaPdf(@PathVariable LocalDate date, @PathVariable String idPaciente ) {
		
		Paciente paciente = pacienteService.getPacienteById(idPaciente); 
		
		HistoriaClinica historiaDb = pacienteService.getHistoriaByDate(paciente, date);
		
		Profesional profesional = pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());
		
		return  null;
	}
	
	@GetMapping("/historias/{idPaciente}")
	public List<HistoriaClinica> getHistoriaPdfAll(@PathVariable String idPaciente) {
		Paciente paciente = pacienteService.getPacienteById(idPaciente); 
		
		List<HistoriaClinica> historiasDb = pacienteService.getAllHistoriaByPaciente(paciente);
		
		return historiasDb;
	}
	

	
}
