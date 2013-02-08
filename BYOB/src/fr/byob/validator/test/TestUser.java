package fr.byob.validator.test;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

import fr.byob.validator.annotation.MailFormatted;
import fr.byob.validator.annotation.NotEmpty;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
public class TestUser implements Serializable,ITestUser {

	private static final long serialVersionUID = 1L;
	
	@Id
	@MailFormatted
	@Sized(min=3,max=20)
	private String userid;

	@Deprecated
	@Required
	private String password;
	
	@NotEmpty
	private ArrayList<String> tests;

	public TestUser() {
		super();
	}
	
	@Required
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Sized(min=3,max=20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@NotEmpty
	public ArrayList<String> getTests() {
		return tests;
	}

	public void setTests(ArrayList<String> tests) {
		this.tests = tests;
	}
}
