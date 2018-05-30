package jeu;

import java.util.ArrayList;

/**
 * Territoire de la carte du Risk
 */
public class Territoire {

	private ArrayList<Armee> armees;
	private Joueur proprietaire; //Pour le moment j'ai mis comme ça, finalement ça me parait le plus pratique
	private int numero;

	public Territoire(int numero){
		this.armees = new ArrayList<>();
		this.numero = numero;
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

	public int getNumero(){return numero;}

	public String toString(){
		return Integer.toString(numero);
	}

}
