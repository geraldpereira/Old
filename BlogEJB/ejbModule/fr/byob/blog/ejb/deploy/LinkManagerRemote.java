package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Link;
import fr.byob.client.exception.ValidationException;

@Remote
public interface LinkManagerRemote {

	public Link addLink(final Link link) throws BlogException, ValidationException;

	public Link editLink(final Link link) throws BlogException, ValidationException;

	public void removeLink(final int linkId) throws BlogException;

	public List<Link> findAllLinks() throws BlogException;

	public Link findLink(final String title) throws BlogException;
	
	public List<Link> findUserLinks (final String userid,final int start,final int end) throws BlogException;
	
	public int countLinks() throws BlogException;
	
	public List<Link> findLinks(int start, int end) throws BlogException;
	
	public Link findLinkId(final int linkId) throws BlogException;
}


