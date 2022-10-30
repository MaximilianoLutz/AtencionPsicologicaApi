package ar.com.llegolaslutz.atencionpsicologica.repository;



import org.springframework.data.repository.CrudRepository;

import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;



public interface I_PacienteRepository extends CrudRepository<Paciente, String>{
	
}
