package jeu;

import java.util.ArrayList;

/**
 * Région regroupant plusieurs territoires.
 */
public class Region {

	private String nom;
	private ArrayList<Territoire> territoires;

	public String getNom() {
		return nom;
	}

	public ArrayList<Territoire> getTerritoires() {
		return territoires;
	}
}
