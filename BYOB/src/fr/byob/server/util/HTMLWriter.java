package fr.byob.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class HTMLWriter {
    
    private static String address = "/home/www/";
    
    
    //OK
    public static void createHTML(String titleHTML,String title,String redirection,List<String> texts){
        FileOutputStream fout;      
        try{
            fout = new FileOutputStream (address+titleHTML+".html");
            PrintStream writer = new PrintStream(fout);
            writer.println ("<html>\n<head>"+"\n<title>"+title+"</title>"+"<meta name=\"description\" content= \""+title+"\">"+"\n<meta http-equiv=\"refresh\" content=\"3; URL=/blog/#"+redirection+"\">"+"\n</head>\n<body>\n");
            for(String text : texts){
                writer.append(text+"\n");
            }
            writer.append("\n</body>\n</html>");
            fout.close();   
            
            addUrlSitemap(titleHTML);
        }catch (IOException e){
            System.err.println ("Unable to write to file");
        }
    }
    
    // OK
    public static void editHTML(String titleHTML,String title,String redirection,List<String> texts){
        FileOutputStream fout;      
        try{
            fout = new FileOutputStream (address+titleHTML+".html");
            PrintStream writer = new PrintStream(fout);
            writer.println ("<html>\n<head>"+"\n<title>"+title+"</title>"+"<meta name=\"description\" content= \""+title+"\">"
                    +"\n<meta http-equiv=\"refresh\" content=\"3; URL=/blog/#"+redirection+"\">"+"\n</head>\n<body>\n");
            for(String text : texts){
                writer.append(text+"\n");
            }
            writer.append("\n</body>\n</html>");
            fout.close();      

            updateDateSitemap(titleHTML);
        }catch (IOException e){
            System.err.println ("Unable to write to file");
            System.exit(-1);
        }
        
    }
    //OK
    private static void addUrlSitemap(String titleHTML){

        FileInputStream fin;        
        String content = "";
        try{
            fin = new FileInputStream (address+"sitemap.xml");
            BufferedReader read = new BufferedReader(new InputStreamReader(fin));
            while(read.ready()){
                String text = read.readLine();
                if(text != null && !text.equals("</urlset>")){
                    content += text+"\n";
                }else{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    content +="<url><loc>http://www.byob.fr/"+titleHTML+".html"+"</loc><lastmod>"+format.format(new Date())+
                    "</lastmod><changefreq>daily</changefreq><priority>0.7</priority> "+"</url>\n";
                    content += text+"\n";
                }
            }
            fin.close();   
            FileOutputStream fout;      
                fout = new FileOutputStream (address+"sitemap.xml");
                new PrintStream(fout).println (content);
                fout.close();     
        }catch (IOException e){
            System.err.println ("Unable to read from file");
        }
    }
    //OK
    private static void updateDateSitemap(String titleHTML){

        FileInputStream fin;        
        String content = "";
        try{
            fin = new FileInputStream (address+"sitemap.xml");

            BufferedReader read = new BufferedReader(new InputStreamReader(fin));
            while(read.ready()){
                String text = read.readLine();
                if(text != null && !text.contains("<url><loc>http://www.byob.fr/"+titleHTML+".html")){
                    content += text+"\n";
                }else if(text.contains("<url><loc>http://www.byob.fr/"+titleHTML+".html")){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    int index = text.indexOf("<lastmod>")+9;
                    String dateS = text.substring(index, index+10);
                    text = text.replaceAll(dateS,format.format(new Date()));
                    content += text+"\n";
                }
            }

            fin.close();   
            FileOutputStream fout;      
            fout = new FileOutputStream (address+"sitemap.xml");
            new PrintStream(fout).println (content);
            fout.close();     
        }catch (IOException e){
            System.err.println ("Unable to read from file");
        }
        
    }
    
    //OK
    private static void deleteUrlSitemap(String titleHTML){
        
        FileInputStream fin;        
        String content = "";
        try{
            fin = new FileInputStream (address+"sitemap.xml");

            BufferedReader read = new BufferedReader(new InputStreamReader(fin));
            while(read.ready()){
                String text = read.readLine();
                if(text != null && !text.contains("<url><loc>http://www.byob.fr/"+titleHTML+".html")){
                    content += text+"\n";
                }
            }
            fin.close();   
            FileOutputStream fout;      
            fout = new FileOutputStream (address+"sitemap.xml");
            new PrintStream(fout).println (content);
            fout.close();     
        }catch (IOException e){
            System.err.println ("Unable to read from file");
        }
    }
    
    //OK
    public static void deleteHTML(String titleHTML){
        File file;      
        file = new File (address+titleHTML+".html");
        file.delete();
        deleteUrlSitemap(titleHTML);
    }
    
    public static void main(String[] args) {
//        HTMLWriter.createHTML("Post13","Aix marseille","Post=13", Arrays.asList(new String[]{"titre","aaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaa","cndhbfjsdbfj"}));
        HTMLWriter.editHTML("Post13","Aix marseille","Post=13", Arrays.asList(new String[]{"titre","aaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaa","cndhbfjsdbfj"}));
//        HTMLWriter.deleteUrlSitemap("Post13");
//        HTMLWriter.updateDateSitemap("Post13");
//        HTMLWriter.deleteHTML("Post13");
    }
}
