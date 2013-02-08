package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.client.exception.ValidationException;

@Remote
public interface ProfilManagerRemote {

	public Profil editProfil(final Profil profil) throws BlogException, ValidationException;

	public void updateProfil(final String userid, final String profilstatut) throws BlogException;

	public List<Profil> findAllProfils() throws BlogException;

	public Profil findProfilUser(final String userid) throws BlogException;
	
	public Profil findProfil(final int profilid) throws BlogException;
	
	public int countProfils() throws BlogException;
	
	public List<Profil> findProfils(int start, int end) throws BlogException;
}
