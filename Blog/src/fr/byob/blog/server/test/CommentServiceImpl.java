package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.CommentGWT;
import fr.byob.blog.client.service.CommentService;
import fr.byob.client.exception.ValidationException;

public class CommentServiceImpl extends RemoteServiceServlet implements CommentService {

	private static final long serialVersionUID = 1L;

	List<IComment> comments;

	private int id = 0;
	
	public CommentServiceImpl() {
		comments = new ArrayList<IComment>();
		for(int i=0;i<5;i++){
			IComment comment = new CommentGWT();
			comment.setCommentautor("akemi");
			Calendar date = Calendar.getInstance();
			date.set(2009, 1, i, i, i,i);
			comment.setCommentdate(date.getTime());
			comment.setCommentmail("akhdg@free.fr");
			comment.setCommenttext("Emilie je t'aiiiimmeuuuuu <3 :) ;) x-( :-o Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu Emilie je t'aiiiimmeuuuuu !"+id);
			comment.setCommentid(id);
			comments.add(comment);
		}
	}
	
	public synchronized IComment addComment(IComment comment) throws BlogException, ValidationException {
		comment.setCommentid(++id);
		return comment;
	}

	public synchronized List<IComment> findAllComments(IPost post) throws BlogException {
		return new ArrayList<IComment>(post.getCommentCollection());
	}
	public List<IComment> findAllComments() throws BlogException {
		return comments;
	}
	public synchronized void removeAllComments(IPost post) throws BlogException {
		post.setCommentCollection(new TreeSet<IComment>());
	}

	public synchronized void removeComment(IComment comment) throws BlogException {
		comment.getPostid().getCommentCollection().remove(comment);
	}
	public List<IComment> findComments(int start, int end) throws BlogException {
        List<IComment> commentsSub = new ArrayList<IComment>();
        for(int i=start;i<end;i++){
            commentsSub.add(comments.get(i));   
        }
        return commentsSub;
    }


    public int countComments(IPost post) throws BlogException {
        return comments.size();
    }

    public List<IComment> findComments(IPost post, int start, int end) throws BlogException {
        System.out.println("CommentServiceImpl !!! "+post.getPostid()+" / "+start+" / "+end);
        List<IComment> commentsSub = new ArrayList<IComment>();
        for(int i=start;i<end;i++){
            commentsSub.add(comments.get(i));   
        }
        return commentsSub;
    }
}
