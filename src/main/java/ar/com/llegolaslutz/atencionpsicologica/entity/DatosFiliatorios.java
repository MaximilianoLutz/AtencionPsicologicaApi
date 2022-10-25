
package ar.com.llegolaslutz.atencionpsicologica.entity;


import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ar.com.llegolaslutz.atencionpsicologica.enums.Estado_Civil;
import ar.com.llegolaslutz.atencionpsicologica.enums.Estudios;

@Entity
//@Table(name="datos_filiatorios", uniqueConstraints = {@UniqueConstraint(columnNames = { "paciente_id", "id" }) })
@Table(name="datos_filiatorios")
public class DatosFiliatorios implements Serializable{
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Estado_Civil estadoCivil;
    private Estudios estudios;
    private String ocupacion;
    private String nacionalidad;
    private String derivacion;
    private String genero;
    private LocalDate fechaAdmision; 
    private LocalDate fechaNacimiento;
    
    @OneToOne
    @JoinColumn(name="paciente_datos_id", referencedColumnName="id")    
    @JsonBackReference
    private Paciente idPaciente;

    public DatosFiliatorios() {
    }
    
    public DatosFiliatorios(Estado_Civil estadoCivil, Estudios estudios, String ocupacion, String nacionalidad, String derivacion, String genero, LocalDate fechaAdmision, LocalDate fechaNacimiento, Paciente idPaciente) {
        this.estadoCivil = estadoCivil;
        this.estudios = estudios;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.derivacion = derivacion;
        this.genero = genero;
        this.fechaAdmision = fechaAdmision;
        this.fechaNacimiento = fechaNacimiento;
        this.idPaciente = idPaciente;
    }
    
    public DatosFiliatorios(Long id, Estado_Civil estadoCivil, Estudios estudios, String ocupacion, String nacionalidad, String derivacion, String genero, LocalDate fechaAdmision, LocalDate fechaNacimiento, Paciente idPaciente) {
        this.id = id;
        this.estadoCivil = estadoCivil;
        this.estudios = estudios;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.derivacion = derivacion;
        this.genero = genero;
        this.fechaAdmision = fechaAdmision;
        this.fechaNacimiento = fechaNacimiento;
        this.idPaciente = idPaciente;
    }

    public DatosFiliatorios(Paciente paciente, Estado_Civil estadoCivil, Estudios estudios, String ocupacion, String nacionalidad, String derivacion, String genero, LocalDate fechaAdmision, LocalDate fechaNacimiento, Paciente idPaciente) {
        
        this.estadoCivil = estadoCivil;
        this.estudios = estudios;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.derivacion = derivacion;
        this.genero = genero;
        this.fechaAdmision = fechaAdmision;
        this.fechaNacimiento = fechaNacimiento;
        this.idPaciente = idPaciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado_Civil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Estado_Civil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Estudios getEstudios() {
        return estudios;
    }

    public void setEstudios(Estudios estudios) {
        this.estudios = estudios;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDerivacion() {
        return derivacion;
    }

    public void setDerivacion(String derivacion) {
        this.derivacion = derivacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaAdmision() {
        return fechaAdmision;
    }

    public void setFechaAdmision(LocalDate fechaAdmision) {
        this.fechaAdmision = fechaAdmision;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return "DatosFiliatorios{" + "id=" + id + ", estadoCivil=" + estadoCivil + ", estudios=" + estudios + ", ocupacion=" + ocupacion + ", nacionalidad=" + nacionalidad + ", derivacion=" + derivacion + ", genero=" + genero + ", fechaAdmision=" + fechaAdmision + ", fechaNacimiento=" + fechaNacimiento + ", idPaciente=" + idPaciente + '}';
    }
    
    /**
  	 * 
  	 */
  	private static final long serialVersionUID = 1L;

    
}