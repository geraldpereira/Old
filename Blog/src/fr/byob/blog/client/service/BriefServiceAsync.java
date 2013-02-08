package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.model.BriefGWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface BriefServiceAsync{
	public void addBrief(BriefGWT brief, AsyncCallback<?> callback);
	public void getBriefs(AsyncCallback<List<BriefGWT>> callback);
	public void getAllBriefs(AsyncCallback<List<BriefGWT>> callback);
}
