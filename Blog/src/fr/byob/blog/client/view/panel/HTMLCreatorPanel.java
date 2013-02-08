package fr.byob.blog.client.view.panel;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.blog.client.controler.AlbumControler;
import fr.byob.blog.client.controler.HTMLCreatorControler;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.util.Css;
import fr.byob.client.view.widget.BYOBTextBox;


public class HTMLCreatorPanel extends Composite implements IDeckListener{
    
    private Label post = new Label("Id de l'article : ");
    private Label user = new Label("Id de l'utilisateur : ");
    private Label category = new Label("Id de la catégorie : ");
    private Label link = new Label("Id du lien : ");
    private BYOBTextBox postId = new BYOBTextBox();
    private BYOBTextBox userId = new BYOBTextBox();
    private BYOBTextBox categoryId = new BYOBTextBox();
    private BYOBTextBox linkId = new BYOBTextBox();
    private Label console = new Label();
    
    public HTMLCreatorPanel() {
        initView();
    }

    private void initView(){
        AlbumControler.getInstance().setHTMLCreatorPanel(this);
        console.setStyleName(Css.INSTANCE.msgconsole());
        HTMLCreatorControler.getInstance().setHTMLCreatorPanel(this);
        HorizontalPanel postPanel = new HorizontalPanel();
        postPanel.addStyleName(Css.INSTANCE.padall3());
        postPanel.add(post);
        postPanel.add(postId);
        postPanel.add(new Button(BlogStrings.INSTANCE.buttonOK(),HTMLCreatorControler.getInstance().getClickCreatePostListener()));
        HorizontalPanel userPanel = new HorizontalPanel();
        userPanel.addStyleName(Css.INSTANCE.padall3());
        userPanel.add(user);
        userPanel.add(userId);
        userPanel.add(new Button(BlogStrings.INSTANCE.buttonOK(),HTMLCreatorControler.getInstance().getClickCreateUserListener()));
        HorizontalPanel categoryPanel = new HorizontalPanel();
        categoryPanel.addStyleName(Css.INSTANCE.padall3());
        categoryPanel.add(category);
        categoryPanel.add(categoryId);
        categoryPanel.add(new Button(BlogStrings.INSTANCE.buttonOK(),HTMLCreatorControler.getInstance().getClickCreateCategoryListener()));
        HorizontalPanel linkPanel = new HorizontalPanel();
        linkPanel.addStyleName(Css.INSTANCE.padall3());
        linkPanel.add(link);
        linkPanel.add(linkId);
        linkPanel.add(new Button(BlogStrings.INSTANCE.buttonOK(),HTMLCreatorControler.getInstance().getClickCreateLinkListener()));
        VerticalPanel global = new VerticalPanel();
        global.add(postPanel);
        global.add(userPanel);
        global.add(categoryPanel);
        global.add(linkPanel);
        
        /*Label chooseAlbum = new Label("Entrer le nom du nouvel album : ");
        BYOBTextBox newNameAlbum = new BYOBTextBox();
        Button addAlbum = new Button("Ajouter");
        addAlbum.addClickListener(AlbumControler.getInstance().addAlbum(newNameAlbum));
        HorizontalPanel newAlbum = new HorizontalPanel();
        newAlbum.add(chooseAlbum);
        newAlbum.add(newNameAlbum);
        newAlbum.add(addAlbum);
        
        final ListBox chooseDeleteAlbum = new ListBox();
        Label chooseAlbumDelete = new Label("Choisir l'album dans lequel vous voulez supprimer la photo : ");
        AlbumControler.getInstance().setListNewAlbumDelete(chooseDeleteAlbum);
        AlbumControler.getInstance().getAlbums();
        Button deleteAlbum = new Button("Supprimer");
        deleteAlbum.addClickListener(AlbumControler.getInstance().deleteAlbum(chooseDeleteAlbum));
        
        global.add(newAlbum);
        global.add(chooseAlbumDelete);
        global.add(chooseDeleteAlbum);
        global.add(deleteAlbum);*/
        
        global.add(console);
        global.setCellHorizontalAlignment(console, HasHorizontalAlignment.ALIGN_CENTER);
        initWidget(global);
    }

    public int getPostId(){
        return Integer.parseInt(postId.getText());
    }
public int getLinkId(){
    return Integer.parseInt(linkId.getText());
    }
public int getCategoryId(){
    return Integer.parseInt(categoryId.getText());
}
public String getUserId(){
    return userId.getText();
}
public void setConsoleText(List<String> texts){
    StringBuilder consoleText = new StringBuilder();
    for (String curText : texts){
        consoleText.append(curText);
    }
    console.setText(consoleText.toString());
}

public void deckCharged() {
    console.setText("");
        
    }
    
}
