package fr.byob.client.application.model;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.application.page.view.IPage;

/**
 * Interface de description d'un model d'application. Un application BYOB
 * contient des pages , des widget South, West et East, un header, une barre de
 * login (overHeader car elle est placée au dessus de ce dernier) et enfin un
 * titre.
 * 
 */
public interface IApplicationModel {
	/**
	 * Retourne les pages de l'application
	 * @return les pages de l'application
	 */
	public ArrayList<IPage> getPages();

	/**
	 * Retourne le panel est de l'application
	 * @return le panel est de l'application
	 */
	public Widget getSouthWidget();

	/**
	 * Retourne le panel sud de l'application
	 * @return le panel sud de l'application
	 */
	public Widget getEastWidget();

	/**
	 * Retourne le header de l'application
	 * @return le header de l'application
	 */
	public Widget getHeaderWidget();

	/**
	 * Retourne le widget West
	 * @return le widget West
	 */
	public Widget getWestWidget();

	/**
	 * Retourne le widget placé au dessus du header (typiquement le widget de login)
	 * @return le widget placé au dessus du header (typiquement le widget de login)
	 */
	public Widget getOverHeaderWidget();

	/**
	 * Retourne le titre de l'application
	 * @return le titre de l'application
	 */
	public String getTitle();
}
