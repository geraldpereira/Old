package fr.byob.blog.client;

import java.util.Date;

public interface IProfil {

	public final static String CSS_BLEUET = "bleuet";
	public final static String CSS_BLANC = "blanc";
	public final static String CSS_GRIS = "gris";
	public final static String CSS_FONCE = "fonce";
	
    public int getProfilid() ;

    public void setProfilid(int profilid);

    public IUser getUserid() ;

    public void setUserid(IUser userid);

    public String getProfilmail() ;

    public void setProfilmail(String profilmail);
    
    public String getProfilsite() ;

    public void setProfilsite(String profilsite);

    /**
     * Date de dernière visite 
     * @return
     */
    public Date getProfildate() ;

    public void setProfildate(Date profildate) ;

    public String getProfilpresentation() ;

    public void setProfilpresentation(String profilpresentation) ;

    public String getProfilphoto() ;

    public void setProfilphoto(String profilphoto) ;

    public String getProfilcss() ;

    public void setProfilcss(String profilcss) ;
    
    public Date getProfilinscription() ;

    public void setProfilinscription(Date profilinscription) ;
    
    public String getProfilstatut() ;

	public void setProfilstatut(String profilstatut) ;
    
}
