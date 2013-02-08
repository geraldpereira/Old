package fr.byob.blog.ejb.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.byob.blog.client.IUser;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.Validable;


@Entity
@Validable
@Table(name="User")
public class User implements Serializable,IUser {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Required
	@Sized(min=4,max=12)
	private String userid;

	@Required
	@Sized(min=4,max=10)
	private String password;

	public User() {
		super();
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
