package ar.com.llegolaslutz.atencionpsicologica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class EventoCalenadarioCita implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String idProfesional;
	private Long idPaciente;
	private LocalDateTime start;
	private LocalDateTime end;
	private String title;
	private String notes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdProfesional() {
		return idProfesional;
	}

	public void setIdProfesional(String idProfesional) {
		this.idProfesional = idProfesional;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "EventoCalenadarioCita [id=" + id + ", idProfesional=" + idProfesional + ", idPaciente=" + idPaciente
				+ ", start=" + start + ", end=" + end + ", title=" + title + ", notes=" + notes + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
