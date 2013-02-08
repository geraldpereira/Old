package fr.byob.blog.server.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.sf.dozer.util.mapping.MapperIF;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.service.BYOBService;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.blog.server.dozer.Mapper;

/**
 * Impl�mentation permettant des actions sur les utilisateurs
 * 
 * @author akemi
 * 
 */
public class BYOBServiceImpl extends RemoteServiceServlet implements BYOBService {
    
    private static String Acces_Photos = "O:/testAlbum/";

    private static final long serialVersionUID = 5518535747328421858L;

    Logger log = Logger.getLogger(this.getClass());
    
    private BYOBSessionRemote session;

    private final MapperIF mapper = Mapper.getMapper();

    /**
     * Constructeur
     */
    public BYOBServiceImpl() {
    }

    public void createHTMLCategory(final int id) throws BlogException {
//        session.createHTMLCategory(id);
    }

    public void createHTMLLink(final int id) throws BlogException {
//        session.createHTMLLink(id);
    }

    public void createHTMLPost(final int id) throws BlogException {
//        session.createHTMLPost(id);
    }

    public void createHTMLUser(final String id) throws BlogException {
//        session.createHTMLUser(id);
    }

    public List<String> getAlbums(){
        return Arrays.asList(new String[]{"","1","2","3","4"});
    }
    public List<String> getPhotos(String album){
        System.out.println("getPhotos : "+album);
        if(album.equals("")){
            return Arrays.asList(new String[]{Acces_Photos+"kirbon.AVI",Acces_Photos+"P1030840.JPG",Acces_Photos+"P1030856.JPG",Acces_Photos+"P1030874.JPG"});
        }else if(album.equals("1")){
            return Arrays.asList(new String[]{"http://www.byob.fr/videos/maison-trets.AVI",Acces_Photos+"1/DSC00685.JPG",Acces_Photos+"1/P1000528.JPG",Acces_Photos+"1/P1020256.JPG",Acces_Photos+"1/P1030832.JPG"});
        }else if(album.equals("2")){
            return Arrays.asList(new String[]{Acces_Photos+"2/P1030839.JPG",Acces_Photos+"2/P1030852.JPG"});
        }else if(album.equals("3")){
            return Arrays.asList(new String[]{Acces_Photos+"3/P1020256.JPG",Acces_Photos+"3/P1030882.JPG"});
        }else if(album.equals("4")){
            return Arrays.asList(new String[]{Acces_Photos+"traversee/DSC00274.JPG",Acces_Photos+"traversee/DSC00298.JPG",Acces_Photos+"traversee/IMG_3410.JPG"});
        }
        return Arrays.asList(new String[]{Acces_Photos+"crotoy16.jpg",Acces_Photos+"P1030840.JPG",Acces_Photos+"P1030856.JPG",Acces_Photos+"P1030874.JPG"});
    }
    public void addPhotoAlbum(String photo, String album) {
        System.out.println("addPhotoAlbum / ByobServiceImpl : "+photo+" / "+album);
        try {
            TestPhotoDim.redimImage(photo,Acces_Photos+album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addVideoAlbum(String video, String album) {
        System.out.println("addPhotoAlbum / ByobServiceImpl : "+video+" / "+album);
        try {
            TestPhotoDim.redimImage(video,Acces_Photos+album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteMediaAlbum(String media) {
        TestPhotoDim.deleteMediaAlbum(media);
    }
    public void addAlbum(String album) {
        System.out.println("addAlbum service : "+album);
        TestPhotoDim.addAlbum(Acces_Photos+album);
    }
    public void deleteAlbum(String album) {
        System.out.println("deleteAlbum service : "+album);
        TestPhotoDim.deleteAlbum(Acces_Photos+album);
    }
    public boolean modifyAlbum(String oldDir,String newDir){
        return TestPhotoDim.modifyAlbum(oldDir, newDir);
    }

    public boolean modifyMedia(String oldName, String newName) {
        return TestPhotoDim.modifyAlbum(Acces_Photos+oldName, Acces_Photos+newName);
    }
}
