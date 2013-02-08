package fr.byob.validator.test;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import fr.byob.validator.annotation.UrlFormatted;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
public class TestLink implements Serializable,ITestLink {

	private static final long serialVersionUID = 1L;
	
	
	private String description;
	
	@Id
	private int linkid;
	
	private String title;
	
	@UrlFormatted
	private String url;
	
//	@NotEmpty
//	private ArrayList<String> tests;

	public TestLink() {
		super();
	}
	

//	@NotEmpty
//	public ArrayList<String> getTests() {
//		return tests;
//	}
//
//	public void setTests(ArrayList<String> tests) {
//		this.tests = tests;
//	}


    public String getDescription() {
        return description;
    }


    public int getLinkid() {
        return linkid;
    }


    public String getTitle() {
        return title;
    }


    public String getUrl() {
        return url;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setLinkid(int linkid) {
        this.linkid = linkid;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
