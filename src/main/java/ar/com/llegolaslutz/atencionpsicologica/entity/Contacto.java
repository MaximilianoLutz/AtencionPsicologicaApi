
package ar.com.llegolaslutz.atencionpsicologica.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Contacto")
public class Contacto implements Serializable {
	@Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String direccion;
    private String telefono;
    private String email;
    private String ciut;
    private String obraSocial;
    
    
    @OneToOne
    @JoinColumn(name="paciente_contacto_id", referencedColumnName="id")
    @JsonBackReference                                       
    private Paciente idPaciente;

    public Contacto() {
    }
    
    public Contacto(String direccion, String telefono, String email, String ciut, String obraSocial, Paciente idPaciente) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ciut = ciut;
        this.obraSocial = obraSocial;
        this.idPaciente = idPaciente;
    }
    
    public Contacto(Long id, String direccion, String telefono, String email, String ciut, String obraSocial, Paciente idPaciente) {
    	this.id = Long.valueOf(id);
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ciut = ciut;
        this.obraSocial = obraSocial;
        this.idPaciente = idPaciente;
    }
    public Contacto(Long id, String direccion, String telefono, String email, String ciut, String obraSocial) {
    	this.id = Long.valueOf(id);
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ciut = ciut;
        this.obraSocial = obraSocial;
    
    }


    public Contacto(Paciente paciente, String direccion, String telefono, String email, String ciut, String obraSocial, Paciente idPaciente) {
        this.id = paciente.getContacto().getId();
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ciut = ciut;
        this.obraSocial = obraSocial;
        this.idPaciente = idPaciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Paciente paciente) {
        this.id = paciente.getContacto().getId();
    }
 

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getCiut() {
        return ciut;
    }

    public void setCiut(String ciut) {
        this.ciut = ciut;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return "Contacto{" + "id=" + id + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", ciut=" + ciut + ", obraSocial=" + obraSocial + ", idPaciente=" + idPaciente + '}';
    }
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
}