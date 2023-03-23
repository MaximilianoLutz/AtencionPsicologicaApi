package ar.com.llegolaslutz.atencionpsicologica.models.entity.auth;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;

@Entity
@Table(name = "Users")
public class UserAuth implements Serializable {


	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String uid;
	@Column(unique = true, length = 20)
	private String username;
	@Column(unique = true, length = 60)
	private String email;
	@Column(length = 60)
	private String password;
	private Boolean enabled;
	@Column(name = "reset_password_token")
	private String resetPasswordToken;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "Users_Authorities", joinColumns = @JoinColumn(name = "Uid"), inverseJoinColumns = @JoinColumn(name = "Rid"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"Uid", "Rid" }))
	private List<Role> roles;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "uAuth", fetch =FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Profesional> profesionales;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	

	public List<Profesional> getProfesionales() {
		return profesionales;
	}

	public void setProfesionales(List<Profesional> profesionales) {
		this.profesionales = profesionales;
	}
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

    @Override
	public String toString() {
		return "UserAuth [uid=" + uid + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", enabled=" + enabled + ", resetPasswordToken=" + resetPasswordToken + ", roles=" + roles
				+ ", profesionales=" + profesionales + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
