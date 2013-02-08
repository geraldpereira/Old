package fr.byob.blog.client.view.panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.proxy.CommentServiceProxy;
import fr.byob.blog.client.view.factory.CommentPanelFactory;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.IListener;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.om.view.ObjectManagementToolbarPanel;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.PlayerWidget;
import fr.byob.client.view.widget.smiley.SmileyUtils;

/**
 * Décrit le Panel pour l'affichage des articles
 * 
 * @author Akemi
 */
public class PostPanel extends Composite implements IDeckListener, IListener {
    /**
     * Le model de données à afficher
     */
    private ITableModel<IPost> tableModel;
    /**
     * Categorie de l'article
     */
    private Set<ICategory> categorie = null;
    /**
     * Titre
     */
    private Label tit = new Label();
    /**
     * Date de l'article
     */
    private Label date = new Label();
    /**
     * Texte
     */
    private HTML texte = new HTML();
    /**
     * Auteur
     */
    private Label auteur = new Label();
    /**
     * Commentaire de l'article
     */
    private Set<IComment> commentaires = null;
    /**
     * L'article
     */
    /**
     * Les catégories
     */
    private Label cats = new Label();
    private Label privatePost = new Label(" | "+BlogStrings.INSTANCE.isPrivate());
    /**
     * Nb de commentaires
     */
    private Label nbComments = new Label();

    private ObjectManagementToolbarPanel<IComment> comments;
    
    private VerticalPanel global = new VerticalPanel();
    private VerticalPanel console = new VerticalPanel();
    private VerticalPanel postPanel;
    /**
     * Constructor
     * 
     * @param model
     */
    public PostPanel(ITableModel<IPost> tableModel) {
        this.tableModel = tableModel;
        tableModel.addListener(this);
        initView();
    }

    /**
     * Initialise la vue
     */
    protected void initView() {
        this.cats.addStyleName(Css.INSTANCE.ajoutTitleArtCat());
        tit.addStyleName(Css.INSTANCE.ajoutTitleArt());
        tit.addStyleName(Css.INSTANCE.padl5());
        RoundedPanel title = new RoundedPanel(tit, RoundedPanel.ALL, 2, Css.INSTANCE.cbgRPBorderTitleArt());
        title.setWidth("100%");
         postPanel = new VerticalPanel();
        cats.setText(getCategory(categorie));
        postPanel.add(cats);
        postPanel.add(title);
        HorizontalPanel comm = new HorizontalPanel();
        comm.add(date);
        comm.add(new Label( " | "));
        comm.add(auteur);
        if (commentaires != null) {
            if (commentaires.size() > 1) {
                nbComments.setText(" | " + commentaires.size()+" " + BlogStrings.INSTANCE.comments());
            } else {
                nbComments.setText(" | " + commentaires.size()+" " + BlogStrings.INSTANCE.comment());
            }
        }
        comm.add(nbComments);
        comm.add(privatePost);
        comm.addStyleName(Css.INSTANCE.msgdate());
        comm.addStyleName(Css.INSTANCE.mrgt10());
        comm.addStyleName(Css.INSTANCE.mrgb10());
        postPanel.add(comm);

        postPanel.add(BlogUtils.getCenteredTextWidget(texte,70));
        
        Label commentaire = new Label(BlogStrings.INSTANCE.commentsUpper());
        commentaire.addStyleName(Css.INSTANCE.ajoutTitleArtCat());
        Label[] titlegrid = { new Label("post") };
        List<Label> tit = Arrays.asList(titlegrid);
        List<List<Label>> titl = new ArrayList<List<Label>>();
        titl.add(tit);
        comments = CommentPanelFactory.getInstance().getCommentPanel();
        comments.setWidth("65%");
        comments.addStyleName(Css.INSTANCE.padl1ex());
        
        postPanel.setWidth("100%");
        global.add(postPanel);
        global.setCellHorizontalAlignment(postPanel, HasHorizontalAlignment.ALIGN_CENTER);
        global.add(commentaire);
        global.add(comments);
        global.setCellHorizontalAlignment(comments, HasHorizontalAlignment.ALIGN_CENTER);
        global.setWidth("100%");
        console.setWidth("100%");
        global.add(console);
        initWidget(global);
        
    }

    /**
     * Construit les donnees sous la forme appropriee pour la grille
     * 
     * @param category
     *            list des categories de l'article
     * @return les donnees
     */
    private String getCategory(Set<ICategory> category) {
        String cat = "";
        if (category != null) {
            for (ICategory categor : category) {
                cat += categor.getCategorylabel()+" ";
            }
        }
        return cat;
    }

    /**
     * Raffraichit la vue quand le model est modifi�
     */
    public void manageModelModification() {
        int selectedIndex = tableModel.getSelectedObjectIndex();
        if ( selectedIndex != ITableModel.NEW_SELECTED_INDEX && selectedIndex != ITableModel.UNSELECTED_INDEX ) { // Pour ne prendre en compte que les cas où un objet concret est sélectionné
            IPost post = tableModel.getSelectedObject();
            // Affecter le post courant à l'adapteur des commentaires.
            CommentServiceProxy.getInstance().setPost(post);
            if (post != null) {
                Utils.changeUrl(BYOBBlog.POST+post.getPostid());
                this.categorie = post.getCategoryCollection();
                this.commentaires = post.getCommentCollection();
                tit.setText(post.getPosttitle());
                date.setText(Utils.formatDate(post.getPostdate()));
                cats.setText(getCategory(categorie));
                auteur.setText(" "+post.getUserid().getUserid());
                if (commentaires != null) {
                    if (commentaires.size() > 1) {
                        nbComments.setText(" | " + commentaires.size()+" " + BlogStrings.INSTANCE.comments());
                    } else {
                        nbComments.setText(" | " + commentaires.size()+" " + BlogStrings.INSTANCE.comment());
                    }
                }
                if(post.getPostisprivate()){
                    privatePost.setVisible(true);
                }else{
                    privatePost.setVisible(false);
                }
                List<String> videos = new ArrayList<String>();
                List<String> texts =BlogUtils.getVideoAddress(SmileyUtils.getTextWithSmiley(BlogUtils.getTextWithoutBreak(post.getPosttext())), videos); 
                int txt = 0;
                texte.setHTML(texts.get(txt));
                
                int count = postPanel.getWidgetCount();
                if(count>4){
                    for(int i =count-1;i>3;i--){
                        postPanel.remove(i);
                    }
                }
                if(videos.size() != 0){
                    for(String video:videos){
                        txt++;
                        postPanel.add(BlogUtils.getCenteredTextWidget(new PlayerWidget(video),70));
                        if(txt<texts.size()){
                            postPanel.add(BlogUtils.getCenteredTextWidget(new HTML(texts.get(txt)),70));
                        }
                    }
                }else{
                    texte.setHTML(SmileyUtils.getTextWithSmiley(BlogUtils.getTextWithoutBreak(post.getPosttext())));
                }
                
                for(int i =0;i<global.getWidgetCount();i++){
                    global.getWidget(i).setVisible(true);
                }
                console.setVisible(false);
            }
        }
    }
    
    public void setConsoleText(List<String> texts){
        
        for(String text:texts){
            Label label = new Label(text);
            label.addStyleName(Css.INSTANCE.msgconsole());
            console.add(label);
            console.setCellHorizontalAlignment(label,HasHorizontalAlignment.ALIGN_CENTER);
        }
        for(int i =0;i<global.getWidgetCount();i++){
            global.getWidget(i).setVisible(false);
        }
        console.setVisible(true);
    }
    
    public void deckCharged() {
        manageModelModification();
        comments.deckCharged();
    }
    public void add(Widget w){
        global.add(w);
    }

    public ITableModel<IPost> getTableModel() {
        return tableModel;
    }
}
