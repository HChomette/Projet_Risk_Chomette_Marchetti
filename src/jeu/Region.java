package jeu;

import java.util.ArrayList;

/**
 * Région regroupant plusieurs territoires.
 */
public class Region {

	private String nom;
	private ArrayList<Territoire> territoires;

	public Region(String nom){
		this.nom = nom;
		this.territoires = new ArrayList<>();
	}

	public void addTerritoire(Territoire t){
		this.territoires.add(t);
	}

	public String getNom() {
		return nom;
	}

	public ArrayList<Territoire> getTerritoires() {
		return territoires;
	}

	public String toString(){
		return nom + " : " + territoires.size() + " territoires.";
	}
}
