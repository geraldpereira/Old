package fr.byob.client.application.model;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.application.page.view.IPage;

public abstract class AApplicationModel implements	IApplicationModel {

	/**
	 * Liste contenant les pages du centre de l'application
	 */
	protected ArrayList<IPage> pages;
	/**
	 * Le panneau du sud
	 */
	protected Widget southWidget;
	/**
	 * Le panneau de l'est
	 */
	protected Widget eastWidget;
	/**
	 * Le panneau west
	 */
	protected Widget westWidget;
	/**
	 * Le panneau entête
	 */
	protected Widget headerWidget;
	/**
	 * Le panneau au dessus du header
	 */
	protected Widget overHeaderWidget;

	/**
	 * Constructeur
	 */
//	public AApplicationModel(int defaultPage) {
//		super(defaultPage);
//	}

	public ArrayList<IPage> getPages() {
		return pages;
	}

	public Widget getEastWidget() {
		return eastWidget;
	}

	public Widget getSouthWidget() {
		return southWidget;
	}

	public Widget getHeaderWidget() {
		return headerWidget;
	}

	public Widget getOverHeaderWidget() {
		return overHeaderWidget;
	}

	public Widget getWestWidget() {
		return westWidget;
	}

}
