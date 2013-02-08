package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.User;

@Remote
public interface BYOBSessionRemote {
	public User getConnectedUser();
	public void logout(final String statut);
	public void createHTMLPost(final int id) throws BlogException;
	public void createHTMLUser(final String id) throws BlogException;
	public void createHTMLLink(final int id) throws BlogException;
	public void createHTMLCategory(final int id) throws BlogException;
	public List<String> getAlbums();
	public List<String> getPhotos(String album);
	public void addPhotoAlbum(String photo, String album) ;
	public void addVideoAlbum(String photo, String album) ;
    public void deleteMediaAlbum(String media) ;
    public void addAlbum(String album) ;
    public void deleteAlbum(String album) ;
    public boolean modifyAlbum(String oldDir,String newDir);
    public boolean modifyMedia(String oldName,String newName);
}
