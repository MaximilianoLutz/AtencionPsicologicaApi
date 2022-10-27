package ar.com.llegolaslutz.atencionpsicologica.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="pacientes")
public class Paciente implements Serializable {

	
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id")
    private String id;
    private String nombre;
    private String apellido;
    private String dni; 
    private boolean acitve;
    
    
    @OneToOne (mappedBy = "idPaciente", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="paciente_datos_id")
    private DatosFiliatorios datosFiliatorios;
	
    @JsonManagedReference
    @OneToOne(mappedBy = "idPaciente", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="paciente_contacto_id")
    private Contacto contacto;
    
    
    @JsonManagedReference
    @OneToMany(mappedBy = "idPaciente", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<HistoriaClinica> historia;
    
	@ManyToOne
    @JoinColumn(name="p", referencedColumnName="id_hex") 
	@JsonBackReference    
    private Profesional p;
    

    public Paciente() {
    	this.historia = new ArrayList<HistoriaClinica>();
    }

    public Paciente(String id, String nombre, String apellido, String dni, boolean active) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.acitve = active;        
        this.historia = new ArrayList<HistoriaClinica>();
    }
    
    public Paciente(String id, String nombre, String apellido, String dni, boolean active, Profesional profesional) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.p = profesional;
        this.acitve = active;
        this.historia = new ArrayList<HistoriaClinica>();
    }
    
    public Paciente(String id, String nombre, String apellido, String dni, boolean active, String idProf) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.acitve = active;
        this.p.setIdHex(idProf);
        this.historia = new ArrayList<HistoriaClinica>();
    }
    
    
    
    public List<HistoriaClinica> getHistoria() {
		return historia;
	}

	public void setHistoria(List<HistoriaClinica> historia) {
		this.historia = historia;
	}

	public DatosFiliatorios getDatosFiliatorios() {
		return datosFiliatorios;
	}

	public void setDatosFiliatorios(DatosFiliatorios datosFiliatorios) {
		this.datosFiliatorios = datosFiliatorios;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
    
    
    
    public boolean isAcitve() {
		return acitve;
	}

	public void setAcitve(boolean acitve) {
		this.acitve = acitve;
	}

	public Profesional getP() {
		return p;
	}

	public void setP(Profesional p) {
		this.p = p;
	}

	
    
	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", acitve="
				+ acitve + ", p=" + p + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 5211911956090073810L;

}

