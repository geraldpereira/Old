package fr.byob.blog.ejb;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jboss.logging.Logger;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IProfil;
import fr.byob.server.util.HTMLWriter;

public abstract class BlogUtils {
    public static void main(String[] args) {
        deleteAlbum(acces_Photo+"add");
        List<String> photos = getPhotos("1");
        for(String p : photos){
            System.out.println("photo : "+p);
        }
    }
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
    public static String acces_Photo = "/home/www/album";
    public static String acces_Photo_Client = "/album";
//        public static String acces_Photo = "O:/testAlbum/";
//        public static String acces_Photo_Client = "O:/testAlbum/";
    private static final Logger log = Logger.getLogger(BlogUtils.class);

    private final static String IMAGE_TYPE_JPEG = "jpeg";
    private final static String IMAGE_TYPE_JPG = "jpg"; 
    private final static String IMAGE_TYPE_GIF = "gif";
    private final static String IMAGE_TYPE_PNG = "png";

    private final static String VIDEO_TYPE_AVI = "avi";

    public static int heightFinal;
    public static int widthFinal;

    public static void createPostHTMLSitemap(IPost post){
        HTMLWriter.createHTML("Post"+post.getPostid(),post.getPosttitle(), "Post="+post.getPostid(), getPostHTML(post));
    }
    public static void editPostHTMLSitemap(IPost post){
        HTMLWriter.editHTML("Post"+post.getPostid(),post.getPosttitle(), "Post="+post.getPostid(), getPostHTML(post));
    }
    public static void deletePostHTMLSitemap(IPost post){
        HTMLWriter.deleteHTML("Post"+post.getPostid());
    }
    private static List<String> getPostHTML(IPost post){
        List<String> texts = new ArrayList<String>();
        texts.add("par "+post.getUserid().getUserid()+" le "+format.format(post.getPostdate()) + " maj. le "+format.format(post.getPostlastupdate()));
        String cats = "";
        if(post.getCategoryCollection() != null){
            for(ICategory category : post.getCategoryCollection()){
                cats += category.getCategorylabel()+ " ";
            }
        }
        texts.add(cats);
        texts.add(post.getPosttext());
        if(post.getCommentCollection() != null){
            for(IComment comment : post.getCommentCollection()){
                texts.add("par "+comment.getCommentautor()+" le "+format.format(comment.getCommentdate()));
                texts.add(comment.getCommenttext());
            }
        }
        return texts;
    }

    public static void createLinkHTMLSitemap(ILink link){
        HTMLWriter.createHTML("Lien"+link.getLinkid(), link.getLinktitle(), "Liens", getLinkHTML(link));
    }
    public static void editLinkHTMLSitemap(ILink link){
        HTMLWriter.editHTML("Lien"+link.getLinkid(), link.getLinktitle(), "Liens", getLinkHTML(link));
    }
    public static void deleteLinkHTMLSitemap(ILink link){
        HTMLWriter.deleteHTML("Lien"+link.getLinkid());
    }
    private static List<String> getLinkHTML(ILink link){
        List<String> texts = new ArrayList<String>();
        texts.add(link.getLinktitle());
        texts.add(link.getUserid().getUserid());
        String cats = "";
        if(link.getCategoryCollection() != null){
            for(ICategory category : link.getCategoryCollection()){
                cats += category.getCategorylabel()+ " ";
            }
        }
        texts.add(cats);
        texts.add(link.getLinkurl());
        texts.add(link.getLinktext());
        return texts;
    }

    public static void createProfilHTMLSitemap(IProfil profil){
        HTMLWriter.createHTML("User"+profil.getUserid().getUserid(), profil.getUserid().getUserid(), "User="+profil.getUserid().getUserid(), getProfilHTML(profil));
    }
    public static void editProfilHTMLSitemap(IProfil profil){
        HTMLWriter.editHTML("User"+profil.getUserid().getUserid(), profil.getUserid().getUserid(), "User="+profil.getUserid().getUserid(), getProfilHTML(profil));
    }
    public static void deleteProfilHTMLSitemap(IProfil profil){
        HTMLWriter.deleteHTML("User"+profil.getUserid().getUserid());
    }
    private static List<String> getProfilHTML(IProfil profil){
        List<String> texts = new ArrayList<String>();
        texts.add(profil.getUserid().getUserid());
        texts.add("Inscrit le "+format.format(profil.getProfilinscription()));
        texts.add("Dernière connexion le "+format.format(profil.getProfildate()));
        texts.add("Site perso : "+profil.getProfilsite());
        texts.add("Photo/Avatar : "+profil.getProfilphoto());
        texts.add("Status : "+profil.getProfilstatut());
        texts.add("Présentation : "+profil.getProfilpresentation());
        return texts;
    }

    public static void createCategoryHTMLSitemap(ICategory category){
        HTMLWriter.createHTML("Catégorie"+category.getCategoryid(), category.getCategorylabel(), "Catégories", getCategoryHTML(category));
    }
    public static void editCategoryHTMLSitemap(ICategory category){
        HTMLWriter.editHTML("Catégorie"+category.getCategoryid(), category.getCategorylabel(), "Catégories", getCategoryHTML(category));
    }
    public static void deleteCategoryHTMLSitemap(ICategory category){
        HTMLWriter.deleteHTML("Catégorie"+category.getCategoryid());
    }
    private static List<String> getCategoryHTML(ICategory category){
        List<String> texts = new ArrayList<String>();
        texts.add(category.getCategorylabel());
        return texts;
    }
    public static List<String> getDirectoryPhotos(){
        File repertoire = new File(BlogUtils.acces_Photo);
        List<String> reps = new ArrayList<String>();
        String [] listefichiers = null; 

        int i; 
        listefichiers=repertoire.list(); 
        reps.add("");
        for(i=0;i<listefichiers.length;i++){ 
            if(!listefichiers[i].contains(".")){
                reps.add(listefichiers[i]);
            }
        } ;
        return reps;
    }
    public static List<String> getPhotos(String photo){
        File repertoire ;
        if(photo != null && !photo.equals("")){
            repertoire = new File(BlogUtils.acces_Photo+"/"+photo);
        }else{
            repertoire = new File(BlogUtils.acces_Photo);
        }
        List<String> files = new ArrayList<String>();
        String [] listefichiers = null; 
        int i; 
        listefichiers=repertoire.list(); 
        for(i=0;i<listefichiers.length;i++){ 
            if(listefichiers[i].contains(".")){
                if(photo != null && !photo.equals("")){
                    files.add(acces_Photo_Client+"/"+photo+"/"+listefichiers[i]);
                }else{
                    files.add(acces_Photo_Client+"/"+listefichiers[i]);    
                }
            }
        } 
        return files;
    }
    public static void redimImage(String startPhoto,String finalPhoto) throws IOException{
        String ext = startPhoto.substring(startPhoto.lastIndexOf(".")+1);
        System.out.println(ext);
        if(ext.toLowerCase().equals(IMAGE_TYPE_GIF) || ext.toLowerCase().equals(IMAGE_TYPE_JPEG) || ext.toLowerCase().equals(IMAGE_TYPE_PNG)|| ext.toLowerCase().equals(IMAGE_TYPE_JPG) ){
            // Spécifier le nom du fichier de l'image redimensionnée
            String pictureName = finalPhoto;
            // Mettez la dimension de la capture finale ici

            // L'image originale
            BufferedImage buf = ImageIO.read(new File(startPhoto));
            resizeImage(buf, 1000);
            System.out.println("width : "+widthFinal +" / h : "+heightFinal);
            Dimension finalDim = new Dimension(widthFinal, heightFinal);
            Dimension screenshotFinalDimension = new Dimension(widthFinal, heightFinal);
            // L'image redimensionnée
            BufferedImage bufFinal = new BufferedImage(screenshotFinalDimension.width,
                    screenshotFinalDimension.height, BufferedImage.TYPE_INT_RGB);

            // Redimensionnement de l'image
            Graphics2D g = (Graphics2D) bufFinal.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(buf, 0, 0, finalDim.width, finalDim.height, null);
            g.dispose();

            // Ecriture de l'image sur le disque
            try {
                log.info("BlogUtils : redimImage : "+pictureName);
                ImageIO.write(bufFinal, IMAGE_TYPE_JPEG, new File(pictureName));
                new File(startPhoto).delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("pas possible !!!!");
        }
    }
    public static void addVideo(String startPhoto,String finalPhoto) throws IOException{
        String ext = startPhoto.substring(startPhoto.lastIndexOf(".")+1);
        System.out.println(ext);
//        if(ext.toLowerCase().equals(VIDEO_TYPE_AVI)){
            File video = new File(startPhoto);
            File videoFinal = new File(finalPhoto);
            FileInputStream read = new FileInputStream(video);
            try {
                FileOutputStream write = new FileOutputStream(videoFinal);
                try {
                    byte[] tabLu = new byte[10240];
                    int nbLu;
                    while((nbLu = read.read(tabLu)) > 0){
                        write.write(tabLu,0,nbLu);
                    }
                } finally {
                    write.close();
                }
            } finally {
                read.close();
            } 
//        }else{
//            System.out.println("Pas possible !!");
//        }
    }
    public static void deletePhoto(String urlPhoto){
//        for(String url : urlPhoto){
            urlPhoto = urlPhoto.replaceAll(acces_Photo_Client, acces_Photo);
            File file = new File (urlPhoto);
            file.delete();
//        }
    }
    public static void deleteAlbum(String directory){
        File file = new File (directory);
        file.delete();
    }
    public static void addAlbum(String directory){
        File file = new File (directory);
        file.mkdir();
    }
    public static boolean modifyMedia(String oldDirectory,String newDirectory){
        log.debug("BlogUtils : modifyMedia : "+ oldDirectory+" / "+ newDirectory);
        File file = new File (oldDirectory);
        return file.renameTo(new File(newDirectory));
    }
    public static void resizeImage(final BufferedImage image, final int max) {
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("w : "+width+" / h : "+height);
        if (width > max || height > max) {
            int newWidth = width;
            int newHeight = height;
            if (width > max && height > max) {
                if (width > height) {
                    newWidth = max;
                    double coef = (double) max / width;
                    newHeight = (int) (coef * (double) height);
                } else {
                    newHeight = max;
                    double coef = (double) max / height;
                    newWidth = (int) (coef * (double) width);
                }
            } else {
                if (height > max) {
                    newHeight = max;
                    double coef = (double) max / height;
                    newWidth = (int) (coef * (double) coef * width);
                }
                if (width > max) {
                    newWidth = max;
                    double coef = (double) max / width;
                    newHeight = (int) (coef * (double) coef * height);
                }
            }
            System.out.println("wf : "+widthFinal+" / hf : "+heightFinal);
            widthFinal = newWidth ;
            heightFinal = newHeight;
        }else{
            widthFinal = width ;
            heightFinal = height;
        }
    }
}
