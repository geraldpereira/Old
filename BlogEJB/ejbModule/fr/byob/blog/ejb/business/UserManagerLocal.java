package fr.byob.blog.ejb.business;

import javax.ejb.Local;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.User;
import fr.byob.client.exception.ValidationException;

@Local
public interface UserManagerLocal {
	public User findUser(final String id);
	public User editUser(final User user) throws BlogException, ValidationException;
}
