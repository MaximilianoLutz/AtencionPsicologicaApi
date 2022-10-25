package ar.com.llegolaslutz.atencionpsicologica.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ar.com.llegolaslutz.atencionpsicologica.models.entity.auth.UserAuth;

@Entity
public class Profesional implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id_hex")
	private String idHex;
	private String nombre;
	private String apellido;
	private String matricula;
	private String matricula2;
	private String profesion;
	private String email;
	private String telefono;

	@JsonManagedReference
	@OneToMany(mappedBy = "p", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Paciente> pacientes;
	
	
	@ManyToOne
    @JoinColumn(name="uAuth", referencedColumnName="uid") 
	@JsonBackReference    
    private UserAuth uAuth;

	public Profesional() {
	}
	
	public Profesional( String nombre, String apellido, String matricula, String matricula2,
			String profesion, String email, String telefono, List<Paciente> pacientes) {
	
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
		this.matricula2 = matricula2;
		this.profesion = profesion;
		this.email = email;
		this.telefono = telefono;
		this.pacientes = pacientes;
	}

	public Profesional(String idHex, String nombre, String apellido, String matricula, String matricula2,
			String profesion, String email, String telefono, List<Paciente> pacientes) {

		this.idHex = idHex;
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
		this.matricula2 = matricula2;
		this.profesion = profesion;
		this.email = email;
		this.telefono = telefono;
		this.pacientes = pacientes;
	}
	
	public Profesional(String idHex, String nombre, String apellido, String matricula, String matricula2,
			String profesion, String email, String telefono, List<Paciente> pacientes, String uid) {

		this.idHex = idHex;
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
		this.matricula2 = matricula2;
		this.profesion = profesion;
		this.email = email;
		this.telefono = telefono;
		this.pacientes = pacientes;
		this.uAuth.setUid(uid);
	}

	public String getIdHex() {
		return idHex;
	}

	public void setIdHex(String idHex) {
		this.idHex = idHex;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMatricula2() {
		return matricula2;
	}

	public void setMatricula2(String matricula2) {
		this.matricula2 = matricula2;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public UserAuth getuAuth() {
		return uAuth;
	}

	public void setuAuth(UserAuth uAuth) {
		this.uAuth = uAuth;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "Profesional [Nombre=" + nombre + ", idHex=" + idHex  + ", Apellido=" + apellido + ", matricula=" + matricula
				+ ", matricula2=" + matricula2 + ", profesion=" + profesion + ", email=" + email + ", telefono="
				+ telefono + ", pacientes="
				+ (pacientes != null ? pacientes.subList(0, Math.min(pacientes.size(), maxLen)) : null) + "]";
	}

}
