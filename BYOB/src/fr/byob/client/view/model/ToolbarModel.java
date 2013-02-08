package fr.byob.client.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fr.byob.client.IListener;


public class ToolbarModel implements IToolbarModel {

    private int nbData;

    protected int maxData = 0;
    private int maxDataStopped = 0;
    private int startData =0;
    private int endData = 0;
    private boolean isStopped = false;
    private HashMap<Integer, Boolean> lastOperationToolbar = new HashMap<Integer, Boolean>();
    
    
    private final List<IListener> listeners = new ArrayList<IListener>();
    
    public ToolbarModel(int nbData) {
        this.nbData = nbData;
        lastOperationToolbar.put(PREV_OPERATION, false);
        lastOperationToolbar.put(NEXT_OPERATION, false);
    }

    public ToolbarModel(int nbData,int maxData) {
        this.nbData = nbData;
        this.maxData =maxData;
        this.maxDataStopped = maxData;
        isStopped = true;
        lastOperationToolbar.put(PREV_OPERATION, false);
        lastOperationToolbar.put(NEXT_OPERATION, false);
    }
    
    protected void fireModelChanged() {
		for (IListener listener : listeners) {
			listener.manageModelModification();
		}
	}
    
    public int getNbData(){
        return nbData;
    }
    public int getMaxData(){
        return maxData;
    }
    public void setMaxData(int maxData){
        if(!isStopped || maxData < this.maxDataStopped){
            this.maxData = maxData;
        }else if(isStopped && maxData > this.maxDataStopped){
            this.maxData = maxDataStopped;
        }
    }
    public void setStartAndEndDate(int start, int end) {
        startData = start;
        endData = end;
        fireModelChanged();
    }
    public List<Integer> getValuesToolbar(){
        return Arrays.asList(new Integer[]{startData,endData,maxData});
    }
    public HashMap<Integer, Boolean> getOperationToolbar(){
        return lastOperationToolbar;
    }

    public void setOperationToolbar(int operation,boolean enabled) {
        lastOperationToolbar.put(operation, enabled);   
    }
    
    public void addListener(IListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(IListener listener) {
		this.listeners.remove(listener);
	}
}
