package fr.byob.blog.client.view.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface BlogStrings extends Constants {

    public final static BlogStrings INSTANCE = (BlogStrings) GWT.create(BlogStrings.class);


    // Application strings
    String applicationLastNews (); // Dernières news
    String applicationArchives (); // Archives
    String applicationUsers (); // Membres
    String applicationCategories (); // Catégories
    String applicationAddPost (); // Publication
    String applicationLinks (); // Liens
    String applicationChat (); 
    String applicationHTMLCreator (); 
    String applicationAlbum (); 
    String applicationPhoto (); 
    String applicationTitleAdmin (); // Administration
    String applicationTitleBlog (); // Blog
    String applicationTitleConnected (); // Blog

    //  South
    String southLastUpdate (); // Dernière mise à jour : 
    String southCreateBy (); // Créé par Emilie et Gérald
    String southCompany (); // BYOB Company
    String southContact (); // Contact
    String southHelp (); // Aide

    // ButtonStrings
    String buttonAdd (); // Ajouter
    String buttonDelete (); // Supprimer
    String buttonModify (); // Modifier
    String buttonSearch (); // Rechercher
    String buttonReinit (); // Réinitialiser
    String buttonOK();
    String buttonCancel();
    String buttonAvatar();
    
    // Category strings
    String categoryInfo (); // Informations catégorie
    String categoryNbPost (); // Nb. articles
    String categoryNbLink (); // Nb. liens
    String categoryLabel (); // Catégorie

    // Comments strings
    String     commentCreate (); // Créer votre commentaire ici ....
    String comment (); // commentaire
    String commentUpper (); // Commentaire
    String comments (); //  commentaires
    String commentsUpper (); // Commentaires

    // Archives
    String archiveByAuthor (); // Par auteur
    String archiveByDate (); // Par date
    String archiveByCategory (); // Par catégorie
    String archiveByNbPosts (); // Par nombre de posts
    String archiveByTitle (); // Par titre
    String archiveByText (); // Par texte

    // Links
    String     linkInfo (); // Informations lien
    String linkUpperLabel (); // Lien
    String linkUrlUpper (); // Url
    String linkUrl (); // url

    //  User
    String     userInfo (); // Informations utilisateur
    String userLogIn (); // Connexion
    String userLogOut (); // Déconnexion
    String userLogin (); // Identifiant
    String userPassword (); // Mot de passe
    String userLastPassword();
    String userNewPassword (); // Mot de passe
    String userNewPasswordConf (); // Mot de passe
    String userGuest (); // Invité
    String userMail(); //@ mail
    String userPresentation(); //presention
    String userLastConn(); // Dernière connexion
    String userInscription();
    String userCSS();
    String userSite();
    String userPhoto();
    String userParam();
    String userErreurConfMdp();
    String userErreurLastMdp();
    String userSaisieURLAvatar();
    
    //  Post
    String     postInfo (); // Informations article
    String post (); // Article
    String postNbComments (); // Nb. Commentaires :
    String isPrivate();

    // Ajout post
    String addPostText (); // Texte
    String addPostCategories (); // Catégories
    String addPostDate (); // Date
    String addPostTitle (); // Titre
    
    // Chat
    String chatContacts ();
    String chatGeneral ();
    
    // Divers
    String divTitleDot (); // Titre :
    String divAuthor (); // Auteur :
    String divMail (); // @ mail :
    String divText (); // Texte :
    String divCategory (); // Catégorie
    String divCategoryDot (); // Catégorie :
    String divCategoriesDot (); // Catégories :
    String divDate (); // Date
    String divBy (); // par 
    String divThe (); // le  
    String divUpdate ();
    String divHttp ();
    
    //Gestion photo
    String gestionAlbum();
    String createAlbum();
    String newAlbum();
    String album();
    String photo();
    String video();
    String addPhoto();
    String sizePhoto();
    String sizeVideo();
    String addVideo();
    String gestionMedia();
    String addMedia();
    String deleteAlbum();
    String deletedAlbum();
    String deleteMedia();
    String pleaseAddMedia();
    String addedMedia();
}
