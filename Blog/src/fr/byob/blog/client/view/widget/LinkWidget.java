package fr.byob.blog.client.view.widget;

import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.smiley.SmileyUtils;


public class LinkWidget extends Composite{
	private FlexTable link ;
	private String titleLink;
	private IUser authorLink;
	private String urlLink;
	private Set<ICategory> categoriesLink;
	private String textLink;
	public LinkWidget() {
		link = new FlexTable();
		link.setWidth("100%");
        initWidget(link);
	}

	public void setData(Set<ICategory> csategory,String stitle,IUser sauthor,String surl, String scontent){
	    for(int row = 0;row<link.getRowCount();row ++){
			link.removeRow(row);
		}
		this.titleLink = stitle;
		this.authorLink=sauthor;
		this.urlLink=surl;
		this.categoriesLink=csategory;
		this.textLink=scontent;
		Label author = new Label(BlogStrings.INSTANCE.divBy()+" "+sauthor.getUserid());
		author.addStyleName(Css.INSTANCE.msgdateLink());
		HTML content= new HTML();
		content.setHTML(SmileyUtils.getTextWithSmiley(scontent));
		content.setWidth("100%");
		content.addStyleName(Css.INSTANCE.msglabelLink());
        content.addStyleName(Css.INSTANCE.cursorFleche());
		Label cat = new Label(BlogUtils.getCategoriesString(categoriesLink));
		cat.addStyleName(Css.INSTANCE.msgcatLink());
		HTML title= new HTML(Utils.getLinkHTMLNewPage(surl,stitle));
		title.addStyleName(Css.INSTANCE.msgtitleLink());
		title.addStyleName(Css.INSTANCE.cursor());
		link.setWidget(0, 0, title);
		link.setWidget(0, 1,cat);
		link.setWidget(0, 2, author);
		link.setWidget(1, 0, BlogUtils.getCenteredTextWidget(content,80));
		link.getFlexCellFormatter().setColSpan(1, 0, 3);
		link.getCellFormatter().setWidth(0, 0, "40%");
		link.getCellFormatter().setWidth(0, 1, "40%");
		link.getCellFormatter().setWidth(0, 2, "20%");
	}
	public String getTitleLink(){
		return titleLink;
	}
	public IUser getAuthorLink() {
		return authorLink;
	}
	public String getUrlLink() {
		return urlLink;
	}
	public Set<ICategory> getCategoriesLink() {
		return categoriesLink;
	}
	public String getTextLink() {
		return textLink;
	}
}
