package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.model.BriefGWT;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface BriefService extends RemoteService{
	public void addBrief(BriefGWT brief);
	public List<BriefGWT> getBriefs();
	public List<BriefGWT> getAllBriefs();
}
