package fr.byob.blog.ejb.entity;
import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String rolegroup;

	public UserRolePK() {
		super();
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid2) {
		this.userid = userid2;
	}

	public String getRolegroup() {
		return this.rolegroup;
	}

	public void setRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof UserRolePK)) {
			return false;
		}
		UserRolePK other = (UserRolePK) o;
		return this.userid.equals(other.userid)
			&& this.rolegroup.equals(other.rolegroup);
	}

	@Override
	public int hashCode() {
		return this.userid.hashCode()
			^ this.rolegroup.hashCode();
	}

}
