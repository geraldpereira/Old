package fr.byob.blog.server.test;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TestPhotoDim {
 // Les types de compression
    public final static String IMAGE_TYPE_JPEG = "jpeg";
    public final static String IMAGE_TYPE_JPG = "jpg"; 
    public final static String IMAGE_TYPE_GIF = "gif";
     
    public final static String IMAGE_TYPE_PNG = "png";
    public static int heightFinal;
    public static int widthFinal;
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
//        redimImage();
//        deleteAlbum("O:/testAlbum/8");
//        redimImage("O:\\testAlbum\\P1000504.JPG", "O:\\testPhoto.jpg");
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
        resizeImage(buf, 500);
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
            ImageIO.write(bufFinal, IMAGE_TYPE_JPEG, new File(pictureName));
        } catch (IOException e) {
            e.printStackTrace();
        }
     }else{
         System.out.println("pas possible !!!!");
     }
    }
    public static void deleteMediaAlbum(String urlPhoto){
//        for(String url : urlPhoto){
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
    public static boolean modifyAlbum(String oldDir,String newDir){
        System.out.println("modifyAlbum : "+oldDir+" / "+newDir);
        File file = new File (oldDir);
       return file.renameTo(new File(newDir));
    }
  public static void resizeImage(final BufferedImage image, final int max) {
  int width = image.getWidth();
  int height = image.getHeight();
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
      widthFinal = newWidth ;
      heightFinal = newHeight;
  }
}
}
