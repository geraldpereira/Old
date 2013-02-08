package fr.byob.client.chat.link;

import fr.byob.client.util.Utils;

public class LinkAnalyser {

	private static LinkAnalyser instance = new LinkAnalyser();

	private LinkAnalyser() {
	}

	public static LinkAnalyser getInstance() {
		return instance;
	}

	public Object[] trimLinksSaveNb(String message) {
//	    System.out.println("==============> Links !!!!!!!!!!!!!!!!!!!!!!! "+message);
	    boolean trimed = false;
	    int nbString =0 ;
        String trimedMessage = "";
        String[] messageTab = message.split(" ");
        boolean multiUrl = false;
        int nbFirstUrl = 0;
        int nbLastUrl = 0;
        for (int i = 0; i < messageTab.length; i++) {
            if (Utils.isURL(messageTab[i])){
                trimed = true;
                
                if (messageTab[i].length() > 40) {
                    messageTab[i] = Utils.getLinkHTMLNewPage(messageTab[i], messageTab[i].substring(0, 40) + "...");
                    if(nbLastUrl != 0){
                        multiUrl = true;
//                        System.out.println("link analyser : multi");
                        if(nbFirstUrl == 0){
//                            System.out.println("link analyser : first : "+nbLastUrl);
                            nbFirstUrl = nbLastUrl;
                        }
                    }
                    nbString += 43;
                    nbLastUrl = messageTab[i].length();
//                    System.out.println("link analyser :last "+nbLastUrl);
                } else {
                    messageTab[i] = Utils.getLinkHTMLNewPage( messageTab[i],messageTab[i] );
                    if(nbString<messageTab[i].length()){ 
                        nbString+= messageTab[i].length();
                    }
                }
            }else{
                nbString += messageTab[i].length();
            }
            trimedMessage += messageTab[i] + " ";
        }
        if (!trimed) {
//            System.out.println("!trimed ");
            if(!multiUrl){
//                System.out.println("!multi");
                return new Object[]{message,nbString};
            }else{
//                System.out.println("multi");
                return new Object[]{message,nbString,nbFirstUrl};
            }
        } else {
//            System.out.println("trimed ");
            if(!multiUrl){
//                System.out.println("!multi");
                return new Object[]{trimedMessage,nbString};
            }else{
//                System.out.println("multi");
                return new Object[]{trimedMessage,nbString,nbFirstUrl};
            }
        }
	}
	public String trimLinks(String message) {
		boolean trimed = false;
		String trimedMessage = "";
		String[] messageTab = message.split(" ");

		for (int i = 0; i < messageTab.length; i++) {
			if (Utils.isURL(messageTab[i])){
				trimed = true;
				if (messageTab[i].length() > 40) {
					messageTab[i] = Utils.getLinkHTMLNewPage(messageTab[i], messageTab[i].substring(0, 40) + "...");
				} else {
					messageTab[i] = Utils.getLinkHTMLNewPage( messageTab[i],messageTab[i] );
				}
			}
			trimedMessage += messageTab[i] + " ";
		}

		if (!trimed) {
			return message;
		} else {
			return trimedMessage;
		}

	}
}
