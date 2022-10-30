package ar.com.llegolaslutz.atencionpsicologica.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.llegolaslutz.atencionpsicologica.entity.Consulta;
import ar.com.llegolaslutz.atencionpsicologica.entity.Contacto;
import ar.com.llegolaslutz.atencionpsicologica.entity.DatosFiliatorios;
import ar.com.llegolaslutz.atencionpsicologica.entity.EventoCalenadarioCita;
import ar.com.llegolaslutz.atencionpsicologica.entity.HistoriaClinica;
import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;
import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_CalendarioCItaRepository;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_ContactoRepository;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_DatosFiliatoriosRepository;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_HistoriaClinicaRepository;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_PacienteRepository;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_ProfesionalRepo;
import ar.com.llegolaslutz.atencionpsicologica.repository.I_UserAuthRepository;



@Service //Dao Manager
public class PacienteService implements I_PacienteService {
	
	
	@Autowired
	public I_PacienteRepository pacienteDao;

	@Autowired
	public I_ContactoRepository contactoDao;
	
	@Autowired
	public I_DatosFiliatoriosRepository datosFiliatoriosDao;
	

	@Autowired
	public I_ProfesionalRepo profesionalDao;
	
	
	@Autowired
	public I_HistoriaClinicaRepository historaRepository;
	
	@Autowired
	public I_CalendarioCItaRepository calendarioCitaRepository;
	
	
	/* Paciente */

	
	@Override
	public List<Paciente> findAll() {
		
		pacienteDao.findAll().forEach(p -> p.getP());
		pacienteDao.findAll().forEach(System.out::println);
		return (List<Paciente>) pacienteDao.findAll();
	}


	@Override
	public Paciente getPacienteById(String id) {
		
		return pacienteDao.findById(id).orElse(null);
	}

	@Override
	public void deletePaciente(String id) {
		pacienteDao.deleteById(id);		
	}

	@Override
	public Paciente savePaciente(Paciente paciente) {
		pacienteDao.save(paciente);
		return pacienteDao.findById(paciente.getId()).orElse(null) ;
	}

	@Override
	public void mergePaciente(Paciente paciente) {
		pacienteDao.save(paciente);		
	}


	/* Datos filiatorios */

	@Override
	@Transactional
	public DatosFiliatorios saveDatosFiliatorios(DatosFiliatorios datos) {
		datosFiliatoriosDao.save(datos);
		return datosFiliatoriosDao.findById(datos.getId()).orElse(null);
	}

	@Override
	@Transactional
	public void deleteDatosFiliatorios(Long id) {
		datosFiliatoriosDao.deleteById(id);
		
	}
	
	/* Contacto */

	@Override
	@Transactional
	public Contacto saveContacto(Contacto contacto) {
		return contactoDao.save(contacto);
		//return contactoDao.findById(contacto.getId()).orElse(null);
	}

	@Override
	@Transactional
	public void deleteContacto(Long id) {
		contactoDao.deleteById(id);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Contacto getContactoById(Long id) {
		return contactoDao.findById(id).orElse(new Contacto());
	}
	
	/* Profesional */
	@Override
	@Transactional(readOnly = true)
	public List<Profesional> findAllProfesional() {
	
		return (List<Profesional>) profesionalDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Profesional getProfesionalByIdHex(String idHex) {
		
		return profesionalDao.findByIdHex(idHex).orElse(null);
	}


	@Override
	@Transactional
	public void deleteProfesional(String idHex) {
		profesionalDao.deleteById(idHex);
		
	}

	@Override
	@Transactional
	public Profesional saveProfesional(Profesional profesional) {
		if(profesional == null) return new Profesional();
		
		return profesionalDao.save(profesional);
	}
	
	/* User Auth */

	@Override
	@Transactional(readOnly = true)
	public Paciente fetchByIdWithProfesional(long id) {
		
		return profesionalDao.fetchByIdWithProfesional(id);
	}

	@Override
	@Transactional
	public HistoriaClinica saveHistoria(HistoriaClinica historia) {
	
		return historaRepository.save(historia);
	}

	
	@Override
	@Transactional(readOnly = true)
	public HistoriaClinica getHistoriaByDate(Paciente paciente, LocalDate date) {
		HistoriaClinica historia = historaRepository.findByIdPacienteAndDate(paciente, date);
		return historia;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HistoriaClinica> getAllHistoriaByPaciente(Paciente paciente) {
		
		 return (List<HistoriaClinica>) historaRepository.findByIdPaciente(paciente);
	}

	// Calendario Cita
	
	@Override
	@Transactional
	public EventoCalenadarioCita saveEventoCita(EventoCalenadarioCita evento) {
	
		return calendarioCitaRepository.save(evento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventoCalenadarioCita> getEventosByProf(String idProf) {
		
		return calendarioCitaRepository.findByIdProfesional(idProf);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventoCalenadarioCita> getEventosByPaciente(String idprof, Long idPaciente) {
		
		return calendarioCitaRepository.findByIdProfesionalAndIdPaciente(idprof, idPaciente);
	}


	


}
