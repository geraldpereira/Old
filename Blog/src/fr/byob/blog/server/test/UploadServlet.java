package fr.byob.blog.server.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

public class UploadServlet extends HttpServlet {

    private String PATH_UPLOAD = "O:/testAlbum/";
    Logger log = Logger.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File tmp = new File("O:/tmp");
        System.out.println(request.getAttribute("choose"));
        log.info("tmp : " + tmp.getAbsolutePath() + " | " + tmp.getCanonicalPath() + " | " + tmp.getName() + " | " + tmp.getPath() + " | " + tmp.isDirectory() + " | " + tmp.isFile());
        System.out.println("tmp : " + tmp.getAbsolutePath() + " | " + tmp.getCanonicalPath() + " | " + tmp.getName() + " | " + tmp.getPath() + " | " + tmp.isDirectory() + " | " + tmp.isFile());
        DiskFileItemFactory factory = new DiskFileItemFactory(4096, tmp);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(10000000);
        List<FileItem> items = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
                return;
            }
            String choose = "";
            System.out.println("7 size : " + items.size());
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
//            for (FileItem item : items) {
//                System.out.println("8 " + item);
//                // if (item.isFormField()) {
//                // if(item.getFieldName().equals("choose")){
//                // choose = item.getString();
//                //System.out.println("service : choose : "+item.getString()+" / "
//                // +item.getContentType()+" / "+item.getName());
//                // continue;
//                // }
//                // }
//                //
//                // String fileName = item.getName();
//                // int slash = fileName.lastIndexOf("/");
//                // if (slash == -1) {
//                // slash = fileName.lastIndexOf("\\");
//                // }
//                // if (slash != -1) {
//                // fileName = fileName.substring(slash + 1);
//                // }
//                //
//                // try {
//                // log.info("item : "+item.getName()+" / "+fileName);
//                // System.out.println("item : "+item.getName()+" / "+fileName);
//                // // File tmpFile = new File(tmp,"fileName");
//                // // item.write(tmpFile);
//                // //
//                // AlbumControler.getInstance().addPhotoAlbum(tmpFile.getPath(
//                // ));
//                //
//                // // service.addPhotoAlbum(uploadedFile, album, callback)
//                // }
//                // catch (Exception e) {
//                // e.printStackTrace();
//                // }
//
//                // /////////////////////////////////////////////////
//
//                if (item.isFormField()) {
//                    if (item.getFieldName().equals("chooseAlbum")) {
//                        choose = item.getString();
//                        System.out.println("service : choose : " + item.getString() + " / " + item.getContentType() + " / " + item.getName());
//                        photo = true;
//                        writeTmp(item, photo, video, tmp);
//                        continue;
//                    }
////                    if (item.getFieldName().equals("chooseVideo")) {
////                        chooseVideo = item.getString();
////                        System.out.println("service : chooseVideo : " + item.getString() + " / " + item.getContentType() + " / " + item.getName());
////                        video = true;
////                        writeTmp(item, photo, video, tmp);
////                        continue;
////                    }
//                }
//
//                /*String fileName = item.getName();
//                int slash = fileName.lastIndexOf("/");
//                if (slash == -1) {
//                    slash = fileName.lastIndexOf("\\");
//                }
//                if (slash != -1) {
//                    fileName = fileName.substring(slash + 1);
//                }*/
//                
//
//            }
        } else {
            log.info("Rien d'envoyé !");
        }
    }
    private void writeTmp(FileItem item,boolean photo,boolean video,File tmp,String chooseAlbum ) {
        log.info("0 item : "+ item+" / isphoto : "+  photo+"/ is video : "+  video+": tmp : "+  tmp+"/ choose : "+  chooseAlbum );
        String fileName = item.getName();
        if(fileName != null){
        System.out.println("file 0 : "+fileName);
         int slash = fileName.lastIndexOf("/");
         System.out.println("file 1 : "+slash);
         if (slash == -1) {
         slash = fileName.lastIndexOf("\\");
         System.out.println("file 2 : "+slash);
         
         }
         System.out.println("file 3 : "+slash);
         if (slash != -1) {
         fileName = fileName.substring(slash + 1);
         System.out.println("file 4 : "+fileName);
         }
        try {
            File tmpFile = new File(tmp, fileName);
            System.out.println("fileName : "+fileName);
            System.out.println("tmp file : "+tmpFile.getPath());
            item.write(tmpFile);
            if (photo) {
                log.info("item photo : " + item.getName() + " / " + fileName);
                System.out.println("item photo : " + item.getName() + " / " + fileName+" / "+tmpFile);
                addPhotoAlbum(tmpFile.getPath(),chooseAlbum+"/"+fileName);
            }
            if (video) {
                log.info("item video : " + item.getName() + " / " + item.getName());
                System.out.println("item video : " + item.getName() + " / " + item.getName());
                addVideoAlbum(tmpFile.getPath(),chooseAlbum+"/"+fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
    public void addPhotoAlbum(String fileUploadStream,String fileName){
        System.out.println("addPhotoAlbum : "+fileUploadStream+" / "+fileName);
        try {
            TestPhotoDim.redimImage(fileUploadStream,PATH_UPLOAD+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addVideoAlbum(String fileUploadStream,String fileName){
        System.out.println("addVideoAlbum UploadServlet : "+fileName+" / "+ConnectedUserModel.getInstance().getConnectedUserProfil());
        try {
            TestPhotoDim.redimImage(fileUploadStream,PATH_UPLOAD+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
