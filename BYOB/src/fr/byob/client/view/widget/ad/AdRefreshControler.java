package fr.byob.client.view.widget.ad;

import java.util.ArrayList;

public class AdRefreshControler {

	private final ArrayList<IAdRefreshListener> listeners;
	
	private AdRefreshControler(){
		listeners = new ArrayList<IAdRefreshListener>();
	}
	
	private static final AdRefreshControler instance = new AdRefreshControler();
	
	public static AdRefreshControler getInstance (){
		return instance;
	}
	
	public void addAdRefreshListener (IAdRefreshListener listener){
		listeners.add(listener);
	}
	
	public void remvoveAdRefreshListener (IAdRefreshListener listener){
		listeners.remove(listener);
	}
	
	public void refreshAds (){
		for (IAdRefreshListener listener : listeners){
			listener.adRefreshed();
		}
	}
}
