package partie;

import jeu.Carte;
import jeu.Joueur;
import jeu.Region;
import jeu.Territoire;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe principale gérant l'ensemble d'une partie de Risk
 */

public class Partie {

	private Carte carte;
	private ArrayList<Joueur> joueurs;
	private IReglesAction regles;
	private ArrayList<Joueur> ia;

	//////////////////
	// CONSTRUCTORS //
	//////////////////

	public Partie(Carte carte, IReglesAction regles){
		this.carte = carte;
		this.regles = regles;
		this.joueurs = new ArrayList<>();
		this.ia = new ArrayList<>();
	}

	/////////////
	// METHODS //
	/////////////

	/**
	 * Ajoute un nouveau joueur à la partie.
	 * @param j joueur à ajouter
	 */
	public void addJoueur(Joueur j){
		this.joueurs.add(j);
	}

	/**
	 * Ajoute un joueur dans la liste des IA
	 * @param j le joueur IA
	 */
	public void setIA(Joueur j){
		this.ia.add(j);
	}

	public boolean isIA(Joueur j){
		return ia.contains(j);
	}

	/**
	 * Vérifie si un joueur a remporté la partie par conquête.<br/>
	 * Si oui, retourne le vainqueur
	 * @return le joueur gagnant, ou null si personne n'a encore gagné
	 * //TODO : Tester cette méthode
	 */
	public Joueur checkVictoireDestruction(){
		Joueur res = null;
		for(Region r : this.carte.getRegions()){
			for(Territoire t : r.getTerritoires()){
				if(res == null) //Cas du premier territoire vérifié
					res = t.getProprietaire();

				else if (res != t.getProprietaire())
					return null; //Il y a au moins 2 propriétaires de territoires, la partie n'est pas finie

			}
		}
		return res;
	}



	public void distributionTerritoires(){
		ArrayList<Territoire> territoires = new ArrayList<>();
		for (Region r :this.carte.getRegions() ) {
			territoires.addAll(r.getTerritoires());
		}
		Collections.shuffle(territoires);
		int i = 0;
		for (Territoire t : territoires) {
			t.setProprietaire(this.joueurs.get(i % this.joueurs.size()));
			i ++;
		}
	}

	///////////////////////
	// GETTERS & SETTERS //
	///////////////////////


	public Carte getCarte() {
		return carte;
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public Joueur getJoueur(int i){
		return joueurs.get(i);
	}

	public IReglesAction getRegles() {
		return regles;
	}
}
