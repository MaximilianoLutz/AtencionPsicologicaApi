package ar.com.llegolaslutz.atencionpsicologica.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "consultas")
@Entity
public class Consulta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombrePaciente;
	private String apellidoPaciente;
	private String edad;
	private String motivoDeConsulta;
	private String contacto;
	private String derivadoPor;
	private Profesional derivadoHacia;
	private String telefono;
	private String email;
	private LocalDate CreateAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	
	public String getApellidoPaciente() {
		return apellidoPaciente;
	}

	public void setApellidoPaciente(String apellidoPaciente) {
		this.apellidoPaciente = apellidoPaciente;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getMotivoDeConsulta() {
		return motivoDeConsulta;
	}

	public void setMotivoDeConsulta(String motivoDeConsulta) {
		this.motivoDeConsulta = motivoDeConsulta;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDerivadoPor() {
		return derivadoPor;
	}

	public void setDerivadoPor(String derivadoPor) {
		this.derivadoPor = derivadoPor;
	}

	public Profesional getDerivadoHacia() {
		return derivadoHacia;
	}

	public void setDerivadoHacia(Profesional derivadoHacia) {
		this.derivadoHacia = derivadoHacia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getCreateAt() {
		return CreateAt;
	}

	public void setCreateAt(LocalDate createAt) {
		CreateAt = createAt;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5435074723356526258L;

}
