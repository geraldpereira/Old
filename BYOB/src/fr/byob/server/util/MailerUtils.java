package fr.byob.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.byob.mail.Emailer;

public abstract class MailerUtils {
    
    private static Emailer mailer = new Emailer();
    
    public static void main(String[] args) {
//        sendmailForNewPost("Traversée de l'Atlantique 'Récit'", "Akemi", new Date(), Arrays.asList(new String[]{"Voyage","Plongée"}), "edeltil@gmail.com","#Post=2");
//        sendmailForNewComment("Traversée de l'Atlantique 'Récit'", "Akemi", new Date(), "edeltil@gmail.com","#Post=2");
        sendmailForNewPhoto("P575847.jpg", "Akemi",  "edeltil@gmail.com", "Trets");
        sendmailForNewVideo("P575847.avi", "Akemi",  "edeltil@gmail.com", "Trets");
    }
    
    private static String HEADER_MAIL = "[BYOB-Blog] ";
    
    private static String CONTENT_MAIL = "Bonjour,\n\n";
    
    private static String FOOTER_MAIL = "\n\nByobement,\n\nBYOB Team : Akemi & Kojiro";
    
    private static String ADDR_BLOG = "www.byob.fr/blog/";
    
    public static void sendmailForNewPost(String title,String author,Date date,List<String> categories,String addrMail,String addrPost){
        String titleMail= HEADER_MAIL + "Ajout Article : "+title;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");

        String content=CONTENT_MAIL+
        "Un nouvel article a été posté sur www.byob.fr. Il porte le titre : "+title+", il appartient ";
        if(categories != null && categories.size() != 0){
            if(categories.size() == 1){
                content +="à la catégorie "+categories.get(0)+" ";
            }else{
                content +="aux catégories ";
                for(String cat :categories){
                    content += cat+", ";
                }
            }
        }
        content +="et il a été écrit par "+author+", le "+format.format(date) + 
        "\n\nVous pouvez accéder directement à l'article à l'adresse suivante : "+ADDR_BLOG+addrPost
        +FOOTER_MAIL;
        
        String mailTo=addrMail;
        System.out.println("sendmailForNewPost !!!!!!!!!!!");
        System.out.println("Sujet : " + titleMail+"\n");
        System.out.println(content);
        System.out.println("\n"+"@to : "+mailTo);
        System.out.println("sendmailForNewPost !!!!!!!!!!!");
        mailer.sendEmail(mailTo, titleMail, content);
    }
    public static void sendmailForNewComment(String titlePost,String author,Date date,String addrMail,String addrPost){
        String titleMail= HEADER_MAIL + "Ajout Commentaire pour l'article : "+titlePost;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");
        String content=CONTENT_MAIL + "Un nouveau commentaire a été posté sur www.byob.fr par "+author+
        ". Il répond à votre article intitulé : "+titlePost+", écrit le "+format.format(date) +
        "\n\nVous pouvez accéder directement à votre article à l'adresse suivante : "+ADDR_BLOG+addrPost+
        FOOTER_MAIL;
        String mailTo=addrMail;
        System.out.println("sendmailForNewComment !!!!!!!!!!!");
        System.out.println("Sujet : " + titleMail+"\n");
        System.out.println(content);
        System.out.println("\n"+"@to : "+mailTo);
        System.out.println("sendmailForNewComment !!!!!!!!!!!");
        mailer.sendEmail(mailTo, titleMail, content);
    }
    public static void sendmailForNewPhoto(String titlePhoto,String author,String addrMail,String album){
        String titleMail= HEADER_MAIL + "Ajout Photo : "+titlePhoto;
        String content = "";
        if(author != null){
            content=CONTENT_MAIL + "Une nouvelle photo a été postée sur www.byob.fr par "+author+
        ". Elle se nomme "+titlePhoto+", et se situe dans l'album "+album +
        "\n\nVous pouvez accéder directement à cet album à l'adresse suivante : "+ADDR_BLOG+"#Album="+album+
        FOOTER_MAIL;
        }else{
            content=CONTENT_MAIL + "Une nouvelle photo a été postée sur www.byob.fr"+
            ". Elle se nomme "+titlePhoto+", et se situe dans l'album "+album +
            "\n\nVous pouvez accéder directement à cet album à l'adresse suivante : "+ADDR_BLOG+"#Album="+album+
            FOOTER_MAIL;
        }
        String mailTo=addrMail;
        System.out.println("sendmailForNewPhoto !!!!!!!!!!!");
        System.out.println("Sujet : " + titleMail+"\n");
        System.out.println(content);
        System.out.println("\n"+"@to : "+mailTo);
        System.out.println("sendmailForNewPhoto !!!!!!!!!!!");
        mailer.sendEmail(mailTo, titleMail, content);
    }
    public static void sendmailForNewVideo(String titleVideo,String author,String addrMail,String album){
        String titleMail= HEADER_MAIL + "Ajout Vidéo : "+titleVideo;
        String content = "";
        if(author != null){
            content=CONTENT_MAIL + "Une nouvelle vidéo a été postée sur www.byob.fr par "+author+
        ". Elle se nomme "+titleVideo+", et se situe dans l'album "+album +
        "\n\nVous pouvez accéder directement à cet album à l'adresse suivante : "+ADDR_BLOG+"#Album="+album+
        FOOTER_MAIL;
        }else{
            content=CONTENT_MAIL + "Une nouvelle vidéo a été postée sur www.byob.fr"+
            ". Elle se nomme "+titleVideo+", et se situe dans l'album "+album +
            "\n\nVous pouvez accéder directement à cet album à l'adresse suivante : "+ADDR_BLOG+"#Album="+album+
            FOOTER_MAIL;
        }
        String mailTo=addrMail;
        System.out.println("sendmailForNewVideo !!!!!!!!!!!");
        System.out.println("Sujet : " + titleMail+"\n");
        System.out.println(content);
        System.out.println("\n"+"@to : "+mailTo);
        System.out.println("sendmailForNewVideo !!!!!!!!!!!");
        mailer.sendEmail(mailTo, titleMail, content);
    }
}
