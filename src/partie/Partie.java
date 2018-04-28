package partie;

import jeu.Carte;
import jeu.Joueur;
import jeu.Region;
import jeu.Territoire;

import java.util.ArrayList;

/**
 * Classe principale gérant l'ensemble d'une partie de Risk
 */

public class Partie {

	private Carte carte;
	private ArrayList<Joueur> joueurs;
	private IReglesAction regles;

	//////////////////
	// CONSTRUCTORS //
	//////////////////

	public Partie(Carte carte, IReglesAction regles){
		this.carte = carte;
		this.regles = regles;
		this.joueurs = new ArrayList<>();
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

	///////////////////////
	// GETTERS & SETTERS //
	///////////////////////


	public Carte getCarte() {
		return carte;
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public IReglesAction getRegles() {
		return regles;
	}
}
