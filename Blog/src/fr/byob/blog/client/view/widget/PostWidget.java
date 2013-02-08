package fr.byob.blog.client.view.widget;

import java.util.Date;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.view.image.NewItemImage;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.smiley.SmileyUtils;

public class PostWidget extends Composite {
//    private final FocusPanel focus;
    private FlexTable post;
    private String titlePost;
    private IUser authorPost;
    private Date datePost;
    private Date updatePost;
    private Set<ICategory> categoriesPost;
    private Set<IComment> commentsPost;
    private String textPost;

    public PostWidget() {
        post = new FlexTable();
        post.setWidth("100%");
        initWidget(post);
    }

    public void setData(Set<ICategory> csategory, String stitle, IUser sauthor, Date sdate,Date supdate, Set<IComment> scomments, String scontent,int id) {
        for (int row = 0; row < post.getRowCount(); row++) {
            post.removeRow(row);
        }
        this.titlePost = stitle;
        this.authorPost = sauthor;
        this.datePost = sdate;
        this.updatePost = supdate;
        this.categoriesPost = csategory;
        this.commentsPost = scomments;
        this.textPost = scontent;
        Label author = new Label(BlogStrings.INSTANCE.divBy() + sauthor.getUserid());
        author.addStyleName(Css.INSTANCE.msgdate());
        String dateStr = Utils.formatDate(datePost);
        String updateStr = Utils.formatDate(updatePost);
        Label date = new Label(BlogStrings.INSTANCE.divThe() + " " + dateStr);
        date.addStyleName(Css.INSTANCE.msgdate());
        Label update = new Label("");
        if (dateStr != updateStr){
            update.setText(BlogStrings.INSTANCE.divUpdate()+" "+updateStr);
        }
        update.addStyleName(Css.INSTANCE.msgdate());        
        HTML content = new HTML();
        content.setHTML(SmileyUtils.getTextWithSmiley(scontent));
        content.setWidth("100%");
        content.addStyleName(Css.INSTANCE.msglabel());
        Label cat = new Label(BlogUtils.getCategoriesString(categoriesPost));
        cat.addStyleName(Css.INSTANCE.msgcat());
         
        Label title = new Label(stitle);
        title.addClickHandler(Utils.getLinkPage(BYOBBlog.POST+id));
        title.setWidth("100%");
        title.addStyleName(Css.INSTANCE.msgtitle());
        Label comments = new Label(BlogStrings.INSTANCE.postNbComments() + " " + scomments.size());
        comments.addStyleName(Css.INSTANCE.msgdate());
        IProfil connectedUser = ConnectedUserModel.getInstance().getConnectedUserProfil();
        
        HorizontalPanel titlePanel = new HorizontalPanel();
        titlePanel.setBorderWidth(0);
        titlePanel.setSpacing(2);
        if (connectedUser != null && connectedUser.getProfildate().compareTo(supdate) < 0){
            titlePanel.add(NewItemImage.INSTANCE.newItem().createImage());
        }
        titlePanel.add(title);
        titlePanel.setCellVerticalAlignment(title,HasVerticalAlignment.ALIGN_BOTTOM);
        post.setWidget(0, 0, titlePanel);
        post.setWidget(0, 1, cat);
        post.setWidget(1, 0, author);
        post.setWidget(1, 1, date);
        post.setWidget(1, 2, update);
        post.setWidget(1, 3, comments);
        post.getFlexCellFormatter().setColSpan(0, 0, 2);
        post.getFlexCellFormatter().setColSpan(0, 1, 2);
        post.setWidget(2, 0,BlogUtils.getCenteredTextWidget(content,80));
        post.getFlexCellFormatter().setColSpan(2, 0, 4);
        post.getCellFormatter().setWidth(0, 0, "50%");
        post.getCellFormatter().setWidth(0, 1, "50%");
        post.getCellFormatter().setWidth(1, 0, "25%");
        post.getCellFormatter().setWidth(1, 1, "25%");
        post.getCellFormatter().setWidth(1, 2, "25%");
        post.getCellFormatter().setWidth(1, 3, "25%");
        
        post.addStyleName(Css.INSTANCE.cursor());
    }

    public String getTitlePost() {
        return titlePost;
    }

    public IUser getAuthorPost() {
        return authorPost;
    }

    public Date getDatePost() {
        return datePost;
    }

    public Set<ICategory> getCategoriesPost() {
        return categoriesPost;
    }

    public Set<IComment> getCommentsPost() {
        return commentsPost;
    }

    public String getTextPost() {
        return textPost;
    }
}
