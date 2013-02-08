package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.model.BriefGWT;
import fr.byob.blog.client.service.BriefService;

/**
 * Impl�mentation permettant des actions sur les utilisateurs
 * 
 * @author akemi
 * 
 */
public class BriefServiceImpl extends RemoteServiceServlet implements BriefService {

    private static final long serialVersionUID = 5518535747328421858L;
    Logger log = Logger.getLogger(this.getClass());
    
    private List<BriefGWT> briefs = new ArrayList<BriefGWT>(20);

    /**
     * Constructeur
     */
    public BriefServiceImpl() {
    }
    

    public void addBrief(BriefGWT brief) {
        if(briefs.size()>=20){
            for(int sup=19;sup<=briefs.size()-1;sup++){
                briefs.remove(sup);
            }
        }
        briefs.add(0,brief);
    }

    public List<BriefGWT> getAllBriefs() {
        return briefs;
    }
    public List<BriefGWT> getBriefs() {
        List<BriefGWT> subBriefs = new ArrayList<BriefGWT>();
        if(briefs.size() != 0){
            if(briefs.size()>5){
                for(int i=0;i<5;i++){
                    subBriefs.add(briefs.get(i));
                }
            }else{
                for(int i=0;i<briefs.size();i++){
                    subBriefs.add(briefs.get(i));
                }
            }
        }
        return subBriefs;
    }
    public void addBriefFromChat(String user, String text) {
        BriefGWT brief = new BriefGWT();
        brief.setText(text);
        brief.setUserid(user);
        brief.setDate(new Date());
        brief.setBriefid(briefs.size());
        addBrief(brief);
    }
    
}
