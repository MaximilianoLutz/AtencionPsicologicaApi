package ar.com.llegolaslutz.atencionpsicologica.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.llegolaslutz.atencionpsicologica.entity.Contacto;
import ar.com.llegolaslutz.atencionpsicologica.entity.DatosFiliatorios;
import ar.com.llegolaslutz.atencionpsicologica.entity.EventoCalenadarioCita;
import ar.com.llegolaslutz.atencionpsicologica.entity.HistoriaClinica;
import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;




public interface I_PacienteService {

	 /* Paciente */
	public List<Paciente> findAll();

	// public Page<Paciente> findAll(Pageable pageable);


	public Paciente getPacienteById(String id);

	public void deletePaciente(String id);

	public Paciente savePaciente(Paciente paciente);

	public void mergePaciente(Paciente paciente);



	// public Page<Paciente> findAll(Pageable pageable);

	
    /* Datos Filiatorios */
	public DatosFiliatorios saveDatosFiliatorios(DatosFiliatorios datos);

	public void deleteDatosFiliatorios(Long id);

	 /* Contacto */
	public Contacto saveContacto(Contacto contacto);

	public void deleteContacto(Long id);
	
	
	public Contacto getContactoById(Long id);
	
	 /* Profesional */
	
	public List<Profesional> findAllProfesional();


	public void deleteProfesional(String idHex);

	public Profesional saveProfesional(Profesional profesional);

	public Profesional getProfesionalByIdHex(String idHex);
	
	public Paciente fetchByIdWithProfesional (long id);
	
	
	//HISTORIA CLINICA
	
	public HistoriaClinica saveHistoria (HistoriaClinica historia);
	public HistoriaClinica getHistoriaByDate(Paciente paciente, LocalDate date);
    public List<HistoriaClinica> getAllHistoriaByPaciente(Paciente paciente);

    
    //EventosCita Calendario
    
    public EventoCalenadarioCita saveEventoCita(EventoCalenadarioCita evento);
    public List<EventoCalenadarioCita> getEventosByProf(String idProf);
    public List<EventoCalenadarioCita> getEventosByPaciente(String idprof, Long idPaciente);
    
	
	
	


	


	

}
