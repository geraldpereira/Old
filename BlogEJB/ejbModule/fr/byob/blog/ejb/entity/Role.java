package fr.byob.blog.ejb.entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="Role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	public final static String ROLE_USER = "ROLE_USER";
	public final static String ROLE_GUEST = "ROLE_GUEST";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int roleid;

	private String role;

	@OneToMany(mappedBy="roleid")
	private Set<UserRole> userRoleCollection;


	public Role() {
		super();
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<UserRole> getUserRoleCollection() {
		return this.userRoleCollection;
	}

	public void setUserRoleCollection(Set<UserRole> userRoleCollection) {
		this.userRoleCollection = userRoleCollection;
	}

}
