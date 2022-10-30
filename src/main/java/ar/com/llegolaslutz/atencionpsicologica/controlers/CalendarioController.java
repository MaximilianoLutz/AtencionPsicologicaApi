package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.llegolaslutz.atencionpsicologica.entity.EventoCalenadarioCita;
import ar.com.llegolaslutz.atencionpsicologica.service.I_PacienteService;



@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired 
	public I_PacienteService pacienteService;
	
	@PostMapping("/eventoCita")
	@ResponseStatus(HttpStatus.CREATED)
	public EventoCalenadarioCita SaveEventoCita(@RequestBody EventoCalenadarioCita evento) {
		
		logger.info(evento);
		
		return pacienteService.saveEventoCita(evento);
	}
	
	@GetMapping("/all/{id}")
	public List<EventoCalenadarioCita> getEventosByProfesional(@PathVariable String id){
		logger.info(id);
		try {
			 
			return pacienteService.getEventosByProf(id);
		}catch (Exception e) {
			logger.error(e);
			return null;
		}
		
		
	}
	
}
