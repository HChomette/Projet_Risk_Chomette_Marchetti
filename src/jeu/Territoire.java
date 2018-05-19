package jeu;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Territoire de la carte du Risk
 */
public class Territoire {

	private ArrayList<Territoire> voisins;
	private ArrayList<Armee> armees;
	private Joueur proprietaire; //Pour le moment j'ai mis comme ça, finalement ça me parait le plus pratique

	public Territoire(){
		this.voisins = new ArrayList<>();
		this.armees = new ArrayList<>();
	}

	public ArrayList<Territoire> getVoisins() {
		return voisins;
	}

	public ArrayList<Armee> getArmees() {
		return armees;
	}

	public Joueur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Joueur j){
		this.proprietaire = j;
	}


	public void addArmee(Armee armee){
		this.armees.add(armee);
	}

	public void removeArmee(Armee armee){
		this.armees.remove(armee);
	}

}
