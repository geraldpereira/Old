package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.CommentGWT;
import fr.byob.blog.client.model.PostGWT;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.service.PostService;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.HTMLWriter;

public class PostServiceImpl extends RemoteServiceServlet implements PostService {

	private static final long serialVersionUID = 1L;

	private final ArrayList<IPost> posts = new ArrayList<IPost>();
	
	private int id = 0;
	
	public PostServiceImpl(){
		for (id = 0; id <15 ; id++){
			PostGWT post = new PostGWT();
			post.setPostid(id);
			Date date = new Date(108,10,16-id);
			post.setPostdate(date);
			Date update = new Date(108,10,17-id);
			post.setPostlastupdate(update);
			post.setPosttext(
			        "on s'est bien amusé <video>www.byob.fr/videos/maison-trets.avi</video>sur ces <b>ptites </b>routes de <A href=\"http://ww.byob.fr\">sud</A> sud : "+id);/* . Et voici une vidéo de notre maison :D :P Le :s hey :@hey :$ weekend (k) a en ;) fait <3 commencé :-o deux :D jours :( avant :'( le B-) départ:-/. Etx-( ouiB-), avec:p le;) stress:-| de savoir si j’allais :break: être à la hauteur:break:, pas facile de dormir:break:. Ainsi, les deux nuits précédentes ce sont cauchemars et réveils en sueur.Vendredi 21 août, le grand jour, réveil à 8h théorique car j’étais en fait réveillée bien avant. La  pluie battant contre le volet fait encore plus monter la pression (c’est ma 1ère expérience sous l’eau après mon plateau foiré). Je me demande si je vais réussir à rouler, pas sur les plaques d’égouts, pas les bandes blanches …On se prépare pour le départ prévu à 9h. Alain arrive avec sa petite famille et ses premières moqueries à mon sujet, puis Didier. Nous avons quelques entretiens à faire sur les motos en attendant l’arrivée de Sylvain (comme d’hab en retard, et pour le coup de mauvaise humeur vu le temps pourri). Nous préparons les outils qui pourront être utiles en cas de problèmes (eh oui avec les machines de Gérald et d’Alain nous ne sommes pas sûrs d’arriver sans embûche).Bien équipés (sacs plastiques dans les chaussures pour certains, gants mappa et kway pour d’autres …) nous partons sous une pluie battante. Il est alors 10h, direction chez Bichon. Gérald est en tête, je les suis, puis Sylvain et enfin Didier et Alain pour fermer la marche.Nous passons par les petites routes derrière Ozoir, il pleut vraiment fort : par moment je sens les impacts des gouttes sur mes jambes à travers mon pantalon de pluie. Les premières sensations de peur arrivent avec les camions que je dois croiser en serrant à gauche de ma voie (pour garder la formation en quinconce avec Gérald). Je me prends de grosses rafales d’eau à chacun de leurs passages et comprend vite qu’il vaut mieux se décaler pour les éviter.Nous arrivons vers Melun, je suis déjà trompée jusqu’aux os, complètement claquée et un peu vannée. Il m’est difficile de conduire sous la pluie, bizarrement j’ai le regard très bas à environ à 3m devant ma roue avant. Cela qui complique beaucoup de choses et me fatigue (de temps en temps Gérald le voit et me rappelle de regarder loin devant).Nous roulons toujours par les petites routes jusqu’à Montereau où nous décidons de faire une pause au Mac Do (perso je ne sais pas si c’est une bonne idée car je sens que tous mes vêtements vont se coller à moi et je vais avoir froid). Mon idée était correcte, dès les premiers pas, je sens mon pantalon trempé se coller à mes jambes (sensations très désagréables). Nous allons donc nous réchauffer un peu (café, thé et chocolats) et manger des barres céréalières. Nous enlevons toutes nos affaires de moto qui dégoulinent de flotte. En moins de 5 minutes le Trash Do est dégueulasse et il y a cinq millimètres de flotte partout où l’on passe. Pour respecter les normes de la chaine quelqu’un vient nettoyer tout ce qu’on a saloppé (autant dire que ça  ne sert à rien vu qu’on y est encore la pour un moment)."+

"Nous repartons pour chez Bichon toujours aussi mouillés. Cette partie de route (par temps sec) est la plus sympa, ça commence à viroler un chouille. Donc pour moi le stress monte encore d’un cran. Je suis toujours de près le Gérald. Arrivé près de Courtenay sa moto commence à prendre l’eau, ce qui l’agace particulièrement (malgré les bas qu’il lui a mis sur les cornets). Ca lui vaut quelques coups de tatane et elle repart sans broncher (à croire qu’elle sent sa colère !!!).Nous arrivons chez bichon, il est 13h30. Nous avons mis plus de 3h, bonne moyenne ...Voici une ptite photo de notre arrivée :On  fait tous sécher nos affaires dans son sèche-linge, ce qui « oblige » certains à se foutre à poil pour manger (et ils aiment ça !!!). Après de magnifiques lasagnes de Bichon, gros dodo pour moi, car après une longue route m’attend (nous avons fait seulement la moitié du chemin).Pour toute cette partie, c’est Alain qui est devant, moi juste après et c’est maintenant Gérald qui ferme la marche. Nous avons donc environ 180km à faire et il pleut toujours. Espérons que ça se calme vite pour le trajet, car celui-ci promet d’être particulièrement viroleu. Sous la pluie méfiance, surtout dans ma tête et pour mon psychotage. Alain me prend en charge comme le fait Gérald ou Didier pour m’indiquer à quelle allure rouler quand ça tourne, car je ne suis pas du tout à l’aise. Je le suis de plus en plus car bizarrement moins plus je m’écarte et moins ca se passe bien. Alors que dans sa roue, je passe plutôt bien les virages. J’essaie de le regarder au maximum pour prendre le plus d’informations avant le virage (ralentissement, changement de vitesse …). Plus je le copie plus je suis à l’aise ! Donc maintenant j’ai compris : le virage arrive, pti frein, baisse d’un rapport, maintien de l’allure, arrivé à l’extérieur du virage, (fin du regard sur Alain), tournage de tête vers l’extérieur du virage, et accélération (bizarrement c’est comme dans les bouquins mais maintenant ca marche aussi sur moi ^^).Plus nous nous rapprochons du Morvan et du lac des Settons et plus ca virole et plus je comprend la moto et ai de plaisir à conduire ?. J’arrive alors presque à faire tous les petits gestes pour indiquer aux autres les problèmes qu’il peut y avoir sur la route (bon pas en virage bien sur).Après énormément d’attention et donc de fatigue dans les virages, nous arrivons au lac des Settons. Je suis totalement claquée et j’ai mal partout. Mais je reste contente de prendre un peu de plaisir à moto.Le soir, faute de place et a cause d’un excès gentillesse envers ma sœur (eh oui ca me perdra), nous « dormons » Gérald et moi dans un lit superposé une place avec d’un coté le vide (pour Gérald) et de l’autre le crépis (pour ma pomme). Autant dire que le lendemain matin nous ne sommes pas frais… Sympa pour la ballade qui arrive.Après le pti dej, rebalade à moto. Alain nous a préparé un road book pour la matinée. Nous partons donc, je suis toujours le scrambler. Le groupe s’est agrandi par rapport à la veille vu que Serge, Philippe et Dominique sont arrivés le soir. L’ordre est donc : Alain, moi, Philippe, Serge, Sylvain, Didier, Gérald et Dominique. Le début de la balade est sympa même s’il fait un peu froid. Les routes sont agréables bien qu’il nous faille faire attention aux graviers. Dans un moment de délire Alain, suivant son road book, nous fait passer dans un endroit pas cool. Déjà rien que la vue de la route ne me signalait rien de bien : elle est très pentue et à première vue pas entretenue. Nous nous engageons donc sur cette route/chemin qui s’avère n’être pas top du tout (d’énormes caillasses, des gravillons partout et une sacrée pente). J’ai du laisser une belle trace de marron derrière moi. Les esprits un peu agacés (seul Alain s’est amusé) nous continuerons donc la balade sur des routes plus potables, mais avec toujours quelques gravillons. Pour finir une belle route avec de superbes virages (bon je pense que vu l’allure à laquelle j’allais ya que moi qui me suis amusée ^^)."+

"L’après midi est réservé à la décontraction"+" en famille : visite du lac en bateau et baignade dans l’eau glacée avec les gamins.Belle lignée de brelles !!!Pour cette nouvelle nuit nous décidons d’innover et dormons dans la salle commune en espérant être frais pour la route du retour. Mais quenini, il a fait un froid de canard, ces batards coupent le chauffage la nuit. Nous avons donc passé encore une mauvaise nuit, gelés.Le dimanche sonne le jour du retour. Je suis totalement claquée, j’ai l’impression d’avoir la moto incrusté en moi. Nous nous trainons donc le cul grâce à 90 sur toute la route. Je n’arrive pu à grand-chose. Mais bon nous atteignons tant bien que mal la maison de Bichon, où nous nous amusons avec les gosses et nous nous reposons un peu (pas tout le monde en fait ‘Allez cheval’ !). En milieu d’après midi on repart direction Noisiel. Arrivés à Courtenay la route est bloquée, nous passons donc par « les champs » (surtout Alain) en suivant la déviation. Puis Philippe se rend compte que son pneu arrière est crevé. Donc arrêt, mise en place d’une mèche pour réussir à rouler un minimum. Nous allons donc à Montereau dans une station pour regonfler son pneu. Mais là c’est la galère, impossible de gonfler le pneu (valve de merde !) et découverte d’une autre fuite (Heureusement que Doumé avait son kit de mèches !). Le pneu est finalement plus ou moins regonflé à l’aide d’un cartouche de CO². Nous rentrons donc à Noisiel doucement, là je n’en peux plus. Les kilomètres paraissent très longs. On voit de plus en plus notre destination se rapprocher (moi moins vite que les autres car tout le monde m’a doublé, je suis Dominique). Enfin arrivée chez Gérald, je suis claquée mais contente : c’était quand même un bon week-end très enrichissant:).Merci à tout le monde."+id);*/
			UserGWT user = new UserGWT();
			user.setUserid("admin");
			post.setUserid(user);
			post.setPosttitle("Titre"+id);

			if(id%2 == 1){
			    post.setPostisprivate(true);
			}else{
			    post.setPostisprivate(false);
			}
			HashSet<ICategory> categoryCollection = new HashSet<ICategory>();
			try {
				categoryCollection.add(new CategoryServiceImpl().findCategory("label"+id%5));
				categoryCollection.add(new CategoryServiceImpl().findCategory("label5"));
			} catch (BlogException e) {
			}
			post.setCategoryCollection(categoryCollection);
			
			HashSet<IComment> comTreeSet = new HashSet<IComment>();
			CommentGWT commentGWT = new CommentGWT();
			commentGWT.setCommentautor("auteur"+id);
			Calendar dateCom = Calendar.getInstance();
			dateCom.set(2009, 1, id, id, id,id);
			commentGWT.setCommentdate(dateCom.getTime());
			commentGWT.setCommentmail(id+"test@test.fr");
			commentGWT.setCommenttext("blaaaaaaaaaaaaaaaaaaaaaaaaabla balbaldfs POI !"+id);
			commentGWT.setCommentid(id);
			commentGWT.setPostid(post);
			CommentGWT commentGWT1 = new CommentGWT();
			commentGWT1.setCommentautor("auteur"+id);
			dateCom = Calendar.getInstance();
            dateCom.set(2009, 1, id+1, id+1, id+1,id+1);
            commentGWT1.setCommentdate(dateCom.getTime());
			commentGWT1.setCommentmail(id+"test@test.fr");
			commentGWT1.setCommenttext("blaaaaaaaaaaaaaaaaaaaaaaaaabla balbaldfs POI !"+id);
			commentGWT1.setCommentid(id);
			commentGWT1.setPostid(post);
			CommentGWT commentGWT2 = new CommentGWT();
			commentGWT2.setCommentautor("auteur"+id);
			dateCom = Calendar.getInstance();
            dateCom.set(2009, 1, id+2, id+2, id+2,id+2);
            commentGWT2.setCommentdate(dateCom.getTime());
			commentGWT2.setCommentmail(id+"test@test.fr");
			commentGWT2.setCommenttext("blaaaaaaaaaaaaaaaaaaaaaaaaabla balbaldfs POI !"+id);
			commentGWT2.setCommentid(id);
			commentGWT2.setPostid(post);
			CommentGWT commentGWT3 = new CommentGWT();
			commentGWT3.setCommentautor("auteur"+id);
			dateCom = Calendar.getInstance();
            dateCom.set(2009, 1, id+3, id+3, id+3,id+3);
            commentGWT3.setCommentdate(dateCom.getTime());
			commentGWT3.setCommentmail(id+"test@test.fr");
			commentGWT3.setCommenttext("blaaaaaaaaaaaaaaaaaaaaaaaaabla balbaldfs POI !"+id);
			commentGWT3.setCommentid(id);
			commentGWT3.setPostid(post);
			comTreeSet.add(commentGWT);
			comTreeSet.add(commentGWT1);
			comTreeSet.add(commentGWT2);
			comTreeSet.add(commentGWT3);
			post.setCommentCollection(comTreeSet);
			posts.add(post);
		}
	}
	
//	@Override
	public synchronized IPost addPost(IPost post) throws BlogException, ValidationException {
		post.setPostid(++id);
		posts.add(post);
		String text ="";
		if(post.getCommentCollection() != null){
		for(int i=0;i<post.getCommentCollection().size();i++){
		    text += ((IComment)post.getCommentCollection().toArray()[i]).getCommenttext();
		}
		}
		return post;
	}

//	@Override
	public synchronized IPost editPost(IPost post) throws BlogException, ValidationException {
		for (IPost postt : posts){
			if (postt.getPostid()== post.getPostid()){
				posts.remove(postt);
				posts.add(post);
				String text ="";
		        if(post.getCommentCollection() != null){
		        for(int i=0;i<post.getCommentCollection().size();i++){
		            text += ((IComment)post.getCommentCollection().toArray()[i]).getCommenttext();
		        }
		        }
				
				return post;
			}
		}	
		return null;
	}

//	@Override
	public synchronized List<IPost> findAllPosts() throws BlogException {
		return posts;
	}

//	@Override
	public synchronized IPost findPost(String title) throws BlogException {
		for (IPost postt : posts){
			if (postt.getPosttitle().equals(title)){
				return postt;
			}
		}
		return null;
	}

//	@Override
	public synchronized void removePost(IPost post) throws BlogException {
	    
	    int i = 0;
        int index = -1;
        for (IPost p : posts) {
            if (p.getPostid()== post.getPostid()) {
                index = i;
                HTMLWriter.deleteHTML("Post"+p.getPostid());
                break;
            }
            i++;
        }
        if (index != -1) {
            posts.remove(index);
            
        }	
	}

	public synchronized void removePostByTitle(String title) throws BlogException {
		for (IPost postt : posts){
			if (postt.getPosttitle().equals(title)){
				removePost(postt);
			}
		}
	}
	public List<IPost> findPosts(int start, int end) throws BlogException {
        List<IPost> postsSub = new ArrayList<IPost>();
        for(int i=start;i<end;i++){
            postsSub.add(posts.get(i));   
        }
        return postsSub;
    }


    public int countPosts() throws BlogException {
        return posts.size();
    }

    public List<IPost> findUserPosts(IUser user, int start, int end) throws BlogException {
        List<IPost> postsSub = new ArrayList<IPost>();
        for(int i=start;i<end;i++){
            postsSub.add(posts.get(i));   
        }
        return postsSub;
    }

    public List<IPost> findPostsByCriteria(final Integer startIndice,final Integer endIndice, Set<IUser> authors, Set<ICategory> categories, String text, String title, Date start,
            Date end) throws BlogException {
        return findPosts(startIndice,endIndice);
    }

    public int countPostsByCriteria(Integer startIndice, Integer endIndice, Set<IUser> authors, Set<ICategory> categories, String text, String title,
            Date start, Date end) throws BlogException {
        return 0;
    }

    public int countUserPosts(IUser user) throws BlogException {
        return posts.size();
    }

    public IPost findPostId(int postid) throws BlogException {
        return posts.get(postid);
    }
}
