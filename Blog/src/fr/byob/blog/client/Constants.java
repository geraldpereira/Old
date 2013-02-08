package fr.byob.blog.client;

/**
 * Classe contenant les différentes constantes globales a toute application BYOB
 * 
 * @author gpereira
 *
 */
public interface Constants {
	/**
	 * Constant indiquant la page d'affichage par d�faut
	 */
	public final static int DEFAULT_PAGE_INDEX = 0;
	
	/**
	 * Url 
	 */
	public final static String HOME_PAGE = "/blog/";
	public final static String SECURED_PAGE = "/blog/www/BlogSecured.html";
	public final static String NOT_LOGGED_PAGE = "/blog/www/BlogNotLogged.html";
	public final static String LOGIN_PAGE = "/blog/www/BlogLogin.html";
	public final static String LOGOUT_PAGE = "/blog/www/BlogLogout.html";
	public final static String AIDE_PAGE = "/blog/www/Blog.htm";
}
