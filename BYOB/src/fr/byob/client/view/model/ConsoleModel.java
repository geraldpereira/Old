package fr.byob.client.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.byob.client.IListener;

public class ConsoleModel implements IConsoleModel {

	private final List<IListener> listeners;

	private List<String>  consoleText = new ArrayList<String>();

	public ConsoleModel() {
		listeners = new ArrayList<IListener>();
	}

	public void addListener(IListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(IListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireModelChanged() {
		for (IListener listener : listeners) {
			listener.manageModelModification();
		}
	}

    public List<String> getConsoleText() {
        return consoleText;
    }

    public void setConsoleText(List<String> text) {
        consoleText = text;
        fireModelChanged();
    }

    public void setConsoleText(String text) {
        consoleText = Arrays.asList(new String[]{text});
        fireModelChanged();
    }
    

    
}
