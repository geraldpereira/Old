package fr.byob.blog.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.server.util.NameFactory;

public class UploadServlet extends HttpServlet {

    Logger log = Logger.getLogger(this.getClass());
    private BYOBSessionRemote session;
    
    public UploadServlet() {
        Context context;
        try {
            context = new InitialContext();
            session = (BYOBSessionRemote) context.lookup(NameFactory.getName("BlogEAR/BYOBSessionBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        service(req, resp);
    }
    @SuppressWarnings("unchecked")
    public void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
//        File tmp = new File("O:/tmp");
        File tmp = new File("/tmp");
        DiskFileItemFactory factory = new DiskFileItemFactory(4096,tmp);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        if ( ServletFileUpload.isMultipartContent(request) ) {
            try {
                items = upload.parseRequest(request);
            }
            catch (FileUploadException e) {
                e.printStackTrace();
                return;
            }
            String choose = "";
            log.info("7 size : " + items.size());
            for (FileItem item : items) { 
                if(item.getFieldName().equals("uploadPhoto")){
                    if(item.getName() != null && !item.getName().equals("")){
                        writeTmp(item, true, false, tmp, choose);
                    }
                }
                if(item.getFieldName().equals("uploadVideo")){
                    if(item.getName() != null && !item.getName().equals("")){
                        writeTmp(item, false, true, tmp, choose);
                    }
                }
                if (item.isFormField()) {
                    log.info("choose : "+ item.getString());
                    log.info("choose : "+item+" / ");
                    if(item.getFieldName().equals("chooseAlbum")){
                        choose = item.getString();
                        log.info("choosePhoto : "+ item.getString());
                        log.info("choosePhoto : "+item+" / ");
//                        writeTmp(item, true, false, tmp, choose, chooseVideo);
                        continue;
                    }
//                    if(item.getFieldName().equals("chooseVideo")){
//                        chooseVideo = item.getString();
//                        log.info("chooseVideo : "+ item.getString());
//                        log.info("chooseVideo : "+item);
////                        writeTmp(item, false, true, tmp, choose, chooseVideo);
//                        continue;
//                    }
                }

            }
        } else {
            log.info( "Rien d'envoyé !" ) ; 
        }

    }
    public void addPhotoAlbum(String fileUploadStream,String fileName){
        session.addPhotoAlbum(fileUploadStream, ""+fileName);
    }
    public void addVideoAlbum(String fileUploadStream,String fileName){
        log.info("addVideoAlbum UploadServlet : "+fileName+" / "+ConnectedUserModel.getInstance().getConnectedUserProfil());
        session.addVideoAlbum(fileUploadStream, ""+fileName);
    }
    private void writeTmp(FileItem item,boolean photo,boolean video,File tmp,String chooseAlbum ) {
        log.info("0 item : "+ item+" / isphoto : "+  photo+"/ is video : "+  video+": tmp : "+  tmp+"/ choose : "+  chooseAlbum );
        String fileName = item.getName(); 
        if(fileName != null){
            int slash = fileName.lastIndexOf("/");
            if (slash == -1) { 
                slash = fileName.lastIndexOf("\\"); 
            }
            if (slash != -1) { 
                fileName = fileName.substring(slash + 1);
            } 
            try {
                File tmpFile = new File(tmp,fileName);
                item.write(tmpFile);
                if(photo){
                    addPhotoAlbum(tmpFile.getPath(),chooseAlbum+"/"+fileName);
                }
                if(video){
                    addVideoAlbum(tmpFile.getPath(),chooseAlbum+"/"+fileName);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
