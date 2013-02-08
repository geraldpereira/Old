package fr.byob.client.view.model;

import java.util.ArrayList;
import java.util.List;

import fr.byob.client.IListener;

public class FormModel<T> implements IFormModel<T> {

	private final List<IListener> listeners = new ArrayList<IListener>();

	private boolean enabled;
	private boolean addVisible;
	private boolean modVisible;
	private boolean delVisible;
	private boolean buttonsEnabled;
	private T object;

	public FormModel() {
		enabled = false;
		addVisible = false;
		modVisible = false;
		delVisible = false;
		buttonsEnabled = false;
	}

	public boolean isButtonsEnabled() {
		return buttonsEnabled;
	}

	public void setButtonsEnabled(boolean buttonsEnabled) {
		this.buttonsEnabled = buttonsEnabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAddVisible() {
		return addVisible;
	}

	public void setAddVisible(boolean addVisible) {
		this.addVisible = addVisible;
	}

	public boolean isModVisible() {
		return modVisible;
	}

	public void setModVisible(boolean modVisible) {
		this.modVisible = modVisible;
	}

	public boolean isDelVisible() {
		return delVisible;
	}

	public void setDelVisible(boolean delVisible) {
		this.delVisible = delVisible;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
		fireModelChanged();
	}

	public void setAddOnlyMode() {
		setAddVisible(true);
		setModVisible(false);
		setDelVisible(false);
		setEnabled(true);
		setButtonsEnabled(true);
		setObject(null);
	}

	public void setModDelMode(T object) {
		setAddVisible(false);
		setModVisible(true);
		setDelVisible(true);
		setEnabled(true);
		setButtonsEnabled(true);
		setObject(object);
	}

	public void setDelOnlyMode(T object) {
		setAddVisible(false);
		setModVisible(false);
		setDelVisible(true);
		setEnabled(false);
		setButtonsEnabled(true);
		setObject(object);
	}

	public void setModOnlyMode(T object) {
		setAddVisible(false);
		setModVisible(true);
		setDelVisible(false);
		setEnabled(true);
		setButtonsEnabled(true);
		setObject(object);
	}

	public void setInitMode() {
		setAddOnlyMode();
//		setAddVisible(true);
//		setModVisible(false);
//		setDelVisible(false);
//		setEnabled(false);
//		setButtonsEnabled(false);
//		setObject(null);
	}
}
