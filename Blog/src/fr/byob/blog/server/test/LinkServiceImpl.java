package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.LinkGWT;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.service.LinkService;
import fr.byob.client.exception.ValidationException;

public class LinkServiceImpl extends RemoteServiceServlet implements LinkService {

	private static final long serialVersionUID = 1L;

	private final ArrayList<ILink> links = new ArrayList<ILink>();
	private int id = 0;
	
	public LinkServiceImpl(){
		for (id = 0; id <15 ; id++){
		    LinkGWT link = new LinkGWT();
		    link.setLinkid(id);
		    link.setLinkurl("http://www.byob.fr");
		    link.setLinktext("Emilie  <3 :) ;) x-( :-o je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! "+id);
			UserGWT user = new UserGWT();
			user.setUserid("admin");
			link.setUserid(user);
			link.setLinktitle("Titre"+id);

			HashSet<ICategory> categoryCollection = new HashSet<ICategory>();
			try {
				categoryCollection.add(new CategoryServiceImpl().findCategory("label"+id%5));
				categoryCollection.add(new CategoryServiceImpl().findCategory("label5"));
			} catch (BlogException e) {
			}
			link.setCategoryCollection(categoryCollection);
			
			links.add(link);
		}
	}
	
//	@Override
	public synchronized ILink addLink(ILink link) throws BlogException, ValidationException {
		link.setLinkid(++id);
		links.add(link);
		return link;
	}

//	@Override
	public synchronized ILink editLink(ILink link) throws BlogException, ValidationException {
		for (ILink linkt : links){
			if (linkt.getLinkid()== link.getLinkid()){
			    links.remove(linkt);
			    links.add(link);
				return link;
			}
		}	
		return null;
	}

//	@Override
	public synchronized List<ILink> findAllLinks() throws BlogException {
		return links;
	}

//	@Override
	public synchronized ILink findLink(String title) throws BlogException {
	    for (ILink linkt : links){
			if (linkt.getLinktitle().equals(title)){
				return linkt;
			}
		}
		return null;
	}

//	@Override
	public synchronized void removeLink(ILink link) throws BlogException {
	    
	    int i = 0;
        int index = -1;
        for (ILink p : links) {
            if (p.getLinkid()== link.getLinkid()) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            links.remove(index);
        }	
	}

	public synchronized void removeLinkByTitle(String title) throws BlogException {
		for (ILink linkt : links){
			if (linkt.getLinktitle().equals(title)){
				removeLink(linkt);
			}
		}
	}
	public List<ILink> findLinks(int start, int end) throws BlogException {
        List<ILink> linksSub = new ArrayList<ILink>();
        for(int i=start;i<end;i++){
            linksSub.add(links.get(i));   
        }
        return linksSub;
    }


    public int countLinks() throws BlogException {
        return links.size();
    }

    public List<ILink> findUserLinks(IUser user, int start, int end) throws BlogException {
        List<ILink> linksSub = new ArrayList<ILink>();
        for(int i=start;i<end;i++){
            linksSub.add(links.get(i));   
        }
        return linksSub;
    }

}
