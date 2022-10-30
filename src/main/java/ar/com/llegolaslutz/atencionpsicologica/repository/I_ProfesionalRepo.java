package ar.com.llegolaslutz.atencionpsicologica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;


public interface I_ProfesionalRepo extends CrudRepository<Profesional, String> {

	Optional<Profesional> findByIdHex(String idHex);

	@Query("select p from Paciente p left join fetch p.p r where p.id=?1")
	public Paciente fetchByIdWithProfesional(Long id); 

}
