package ar.com.llegolaslutz.atencionpsicologica.controlers;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import ar.com.llegolaslutz.atencionpsicologica.utils.GeneratePdfReport;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api")
public class PacienteController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	public I_PacienteService pacienteService;

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/check_token")
	public String checkToken() {
		String ok = "true";
		return ok;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/pacientes")
	public List<Paciente> getAll() {
		return pacienteService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/pacientes/{id}")
	public Paciente getById(@PathVariable String id) {
		return pacienteService.getPacienteById(id);
	}

	/**
	 * Retorna lista de pacientes activos. Unicamente con los datos del paciente(id,
	 * Nombre, Apellido, DNI) Para obtener el resto de los datos de paciente
	 * referirse al Metodo getById
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/pacientesAllActive/{idHex}")
	public List<Paciente> getPacientesActiveByProfesional(@PathVariable String idHex) {

		try {

			Profesional profesional = pacienteService.findAllProfesional().stream()
					.filter(p -> p.getIdHex().equals(idHex)).findAny().orElse(new Profesional());

			return profesional.getPacientes().stream().filter(p -> (p.isAcitve() == true))
					.map(p -> new Paciente(p.getId(), p.getNombre(), p.getApellido(), p.getDni(), p.isAcitve()))
					.collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList<Paciente>();
		}
	}

	/**
	 * Retorna lista de pacientes inactivos. Unicamente con los datos del
	 * paciente(id, Nombre, Apellido, DNI) Para obtener el resto de los datos de
	 * paciente referirse al Metodo getById
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/pacientesAllInactive/{idHex}")
	public List<Paciente> getPacientesInactiveByProfesional(@PathVariable String idHex) {

		try {

			Profesional profesional = pacienteService.findAllProfesional().stream()
					.filter(p -> p.getIdHex().equals(idHex)).findAny().orElse(new Profesional());

			return profesional.getPacientes().stream().filter(p -> (p.isAcitve() == false))
					.map(p -> new Paciente(p.getId(), p.getNombre(), p.getApellido(), p.getDni(), p.isAcitve()))
					.collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList<Paciente>();
		}
	}

	/**
	 * 
	 * @param paciente
	 * @return Paciente
	 */
	@PostMapping("/pacientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente save(@RequestBody Paciente paciente) {

		try {
			return pacienteService.savePaciente(paciente);

		} catch (Exception ex) {
			logger.error(ex);
			return new Paciente();
		}

	}

	// Revisar
	@PutMapping("/pacientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente update(@RequestBody Paciente paciente) {
		logger.info(paciente);
		Paciente pacienteActual = pacienteService.getPacienteById(paciente.getId());

		pacienteActual.setNombre(paciente.getNombre());
		pacienteActual.setApellido(paciente.getApellido());
		pacienteActual.setDni(paciente.getDni());

		logger.info(pacienteActual);

		return pacienteService.savePaciente(pacienteActual);
	}


	/**
	 * Utilizado para guardar y actualizar datos filiatorios del paciente
	 * @param paciente
	 * @return DatosFiliatorios
	 */
	@PutMapping("/datos")
	@ResponseStatus(HttpStatus.CREATED)
	public DatosFiliatorios updateDatos(@RequestBody Paciente paciente) {
		
		try {
		// filtro para unicamente acceder a los pacientes del usuario activo
		Profesional profe = pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());

		Paciente pacienteDb = profe.getPacientes().stream().filter(p -> p.getId().equals(paciente.getId())).findAny()
				.orElse(null);

		//Actualizar
		if (pacienteDb.getDatosFiliatorios() != null) {
			if (pacienteDb.getDatosFiliatorios().getId() > 0)
				logger.info(pacienteDb.getDatosFiliatorios().getId());

			paciente.getDatosFiliatorios().setId(pacienteDb.getDatosFiliatorios().getId());
			paciente.getDatosFiliatorios().setIdPaciente(pacienteDb);
			pacienteDb.setDatosFiliatorios(paciente.getDatosFiliatorios());
			logger.info(pacienteDb.getDatosFiliatorios());
			return pacienteService.saveDatosFiliatorios(pacienteDb.getDatosFiliatorios());

		}
		
		//Guardar
		DatosFiliatorios datos = paciente.getDatosFiliatorios();
		datos.setIdPaciente(pacienteDb);

		return pacienteService.saveDatosFiliatorios(datos);
		
		} catch (Exception ex) {
			logger.error(ex);
			return new DatosFiliatorios();
		}

	}
	
	
	/**
	 * Utilizado para guardar y actualizar datos de contacto del paciente
	 * @param paciente
	 * @return Contacto
	 */
	@PutMapping("/contacto")
	@ResponseStatus(HttpStatus.CREATED)
	public Contacto saveContacto(@RequestBody Paciente paciente) {
		
		// filtro para unicamente acceder a los pacientes del usuario activo
		Profesional profe = pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());

		logger.info(profe);

		Paciente pacienteDb = profe.getPacientes().stream().filter(p -> p.getId().equals(paciente.getId())).findAny()
				.orElse(null);
		
		//Actualizar datos
		if (pacienteDb.getContacto() != null) {
			if (pacienteDb.getContacto().getId() > 0)
		
			paciente.getContacto().setId(pacienteDb);
			pacienteDb.setContacto(paciente.getContacto());

			logger.info(pacienteDb.getContacto());
			return pacienteService.saveContacto(pacienteDb.getContacto());

		}
		//Guardar datos
		Contacto contacto = paciente.getContacto();
		contacto.setIdPaciente(pacienteDb);

		return pacienteService.saveContacto(contacto);

	}

	// HISTORIA CLINICA

	@PostMapping("/historia")
	public HistoriaClinica saveHistoria(@RequestBody HistoriaClinica historia) {
		if (historia.getIdPaciente() == null)
			return new HistoriaClinica();

		LocalDate fecha = LocalDate.from(historia.getDate());

		int compare = fecha.compareTo(LocalDate.now());
		logger.info(fecha);
		logger.info(compare);

		return pacienteService.saveHistoria(historia);
	}


//	@GetMapping("/historia/{date}{idPaciente}")
//	public ResponseEntity<Resource> getHistoriaByDate(@PathVariable LocalDate date, @PathVariable Paciente paciente) {
//
//		try {
//
//		HistoriaClinica historiaDb = pacienteService.getHistoriaByDate(paciente, date);
//
//		Profesional profesional = pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());
//
//		return null;
//	}

	@GetMapping("/historias/{idPaciente}")
	public List<HistoriaClinica> getHistoriasByIdPaciente(@PathVariable String idPaciente) {
		try {
		List<HistoriaClinica> historias = pacienteService.getPacienteById(idPaciente).getHistoria();
		
		return historias;
	
		}catch(Exception ex) {
			logger.error(ex);
			return new ArrayList<HistoriaClinica>();
		}
	}
	//Historia clinica formato PDF 
	
	/**
	 * 
	 * @param idPaciente
	 * @return Historia Clinica completa en formato PDF
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/historiaspdf/{idPaciente}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getListHistoriaClinica(@PathVariable String idPaciente) {
		
		Paciente paciente = pacienteService.getPacienteById(idPaciente); 

		List<HistoriaClinica> historiasDb = pacienteService.getAllHistoriaByPaciente(paciente);
		
		Profesional profesional = pacienteService.getProfesionalByIdHex(paciente.getP().getIdHex());

        ByteArrayInputStream responseHistorias = GeneratePdfReport.historiaClinica(historiasDb, profesional);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=historiaClinica"+
        paciente.getApellido() + paciente.getNombre() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(responseHistorias));
    }
	
	/**
	 * 
	 * @param idPaciente
	 * @return Historia Clinica completa en formato PDF
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/historiapdf/{idPaciente}/{date}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getListHistoriaClinica(@PathVariable String idPaciente, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		
		Paciente paciente = pacienteService.getPacienteById(idPaciente); 

		List<HistoriaClinica> historia= pacienteService.getHistoriaByDate(paciente, date);
		
		Profesional profesional = paciente.getP();

        ByteArrayInputStream responseHistoria = GeneratePdfReport.historiaClinica(historia, profesional);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=historia"+ date.toString() +".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(responseHistoria));
    }
	
	

}
