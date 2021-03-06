package jeu;

import java.util.ArrayList;
import java.util.Comparator;

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

	public void addAllArmee(ArrayList<Armee> unites){this.armees.addAll(unites);}

	public void removeArmee(Armee armee){
		this.armees.remove(armee);
	}

	public void removeAllArmee(ArrayList<Armee> unites){this.armees.removeAll(unites);}

	public int getNumero(){return numero;}

	public String toString(){
		return Integer.toString(numero);
	}

	public static Comparator<Territoire> SortByArmySize = new Comparator<Territoire>() {

		public int compare(Territoire s1, Territoire s2) {

			int rollno1 = s1.getArmees().size();
			int rollno2 = s2.getArmees().size();

			/*For ascending order*/
			return rollno1-rollno2;

			/*For descending order*/
			//return rollno2-rollno1;
		}};


}
