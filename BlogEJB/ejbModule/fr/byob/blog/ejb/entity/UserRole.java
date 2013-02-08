package fr.byob.blog.ejb.entity;
import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="User_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserRolePK pk;

	@ManyToOne
	@JoinColumn(name="roleID")
	private Role roleid;

	public UserRole() {
		super();
	}

	public UserRolePK getPk() {
		return this.pk;
	}

	public void setPk(UserRolePK pk) {
		this.pk = pk;
	}

	public Role getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Role roleid) {
		this.roleid = roleid;
	}
}
