package ar.com.llegolaslutz.atencionpsicologica.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.com.llegolaslutz.atencionpsicologica.entity.HistoriaClinica;
import ar.com.llegolaslutz.atencionpsicologica.entity.Paciente;



public interface I_HistoriaClinicaRepository extends CrudRepository<HistoriaClinica, Long> {
	
	public HistoriaClinica findByIdPacienteAndDate(Paciente paciente, LocalDate date);

    public List<HistoriaClinica> findByIdPaciente(Paciente idPaciente);
	
	

}
