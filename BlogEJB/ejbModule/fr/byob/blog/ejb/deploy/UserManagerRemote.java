package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;
import fr.byob.client.exception.ValidationException;

@Remote
public interface UserManagerRemote { 
	public User addUser(final User user)throws BlogException, ValidationException;
	public User addUser(final User user, final String roleStr)throws BlogException, ValidationException;
	public User findUser(final String id);
	public void removeUser(final String id) throws BlogException;
	public List<User> findAllUsers() throws BlogException;
	public User editUser(final User user) throws BlogException, ValidationException;
	public Role getUserRole(final User user) throws BlogException;
	public int countUsers() throws BlogException;
	public List<User> findUsers(int start, int end)	throws BlogException;
}
