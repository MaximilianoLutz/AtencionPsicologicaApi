package ar.com.llegolaslutz.atencionpsicologica.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.com.llegolaslutz.atencionpsicologica.entity.EventoCalenadarioCita;


public interface I_CalendarioCItaRepository extends CrudRepository<EventoCalenadarioCita, String> {
	
	public List<EventoCalenadarioCita> findByIdProfesional(String idProfesional);
	public List<EventoCalenadarioCita> findByIdProfesionalAndIdPaciente(String idProfesional, Long IdPaciente);

}
