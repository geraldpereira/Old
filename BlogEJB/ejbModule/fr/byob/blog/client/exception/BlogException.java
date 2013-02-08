package fr.byob.blog.client.exception;

import java.util.ArrayList;
import java.util.List;

import fr.byob.client.exception.BYOBException;

public class BlogException extends BYOBException {

	private static final long serialVersionUID = -34L;

	public BlogException(){
	}
	
	public BlogException(Exception cause, String message) {
		super(cause, message);
	}
	
	public BlogException(Exception cause, String message, String redirectRequested) {
		super(cause, message,redirectRequested);
	}
	
	public List<String> toStringList() {
		ArrayList<String> retour = new ArrayList<String>();
		retour.add(super.getMessage());
//		retour.add(this.getClass().toString()+" : "+super.getMessage());
//		if (super.getCause() != null) {
//			retour.add("Causée par : "+super.getCause().toString());
//		}
//		if (super.getStackTraceStr() != null) {
//			String[] trace = super.getStackTraceStr();
//			for (int i = 0 ; i < trace.length ; i++) {
//				retour.add("	"+trace[i]);
//			}
//		}
		return retour;
	}
}
