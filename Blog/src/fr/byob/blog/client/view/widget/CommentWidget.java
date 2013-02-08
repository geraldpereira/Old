package fr.byob.blog.client.view.widget;

import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.smiley.SmileyUtils;


public class CommentWidget extends Composite{
//    private final FocusPanel focus;
	private FlexTable link ;
	private IUser authorPost;
	private Date datePost;
	private String textPost;
	public CommentWidget() {
		link = new FlexTable();
		link.setWidth("100%");
        initWidget(link);
	}

	public void setData(IUser sauthor,Date sdate,  String scontent){
	    Css css = Css.INSTANCE;
	    for(int row = 0;row<link.getRowCount();row ++){
			link.removeRow(row);
		}
		this.authorPost=sauthor;
		this.datePost=sdate;
		this.textPost=scontent;
		Label author = new Label(BlogStrings.INSTANCE.divBy()+" "+sauthor.getUserid());
		author.addStyleName(css.msgdateComm());
		Label date= new Label(BlogStrings.INSTANCE.divThe()+" "+Utils.formatDate(datePost));
		date.addStyleName(css.msgdateComm());
		HTML content= new HTML();
		content.setHTML(SmileyUtils.getTextWithSmiley(scontent));
		content.setWidth("100%");
		content.addStyleName(css.msglabelComm());
		link.setWidget(0, 0, author);
		link.setWidget(0, 1, date);
		link.setWidget(1, 0, BlogUtils.getCenteredTextWidget(content,80));

		link.getFlexCellFormatter().setColSpan(1, 0, 3);
		link.getCellFormatter().setWidth(0, 0, "50%");
		link.getCellFormatter().setWidth(0, 1, "50%");
		link.addStyleName(css.cursor());
	}
	public IUser getAuthorPost() {
		return authorPost;
	}
	public Date getDatePost() {
		return datePost;
	}
	public String getTextPost() {
		return textPost;
	}
	
	
}
