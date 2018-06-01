package regles;

import jeu.*;
import partie.IReglesAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe regroupant les règles de résolution de conflit et les traitant de manière statique. <br/>
 * Cette classe correspond aux règles de base de l'énoncé.
 */
public class ReglesAction implements IReglesAction {

	private static Carte carte;

	/**
	 * Deplace des armées d'un point d'origine à un point de destination, et résout les conflits si nécessaire
	 *
	 * @param armees  les armées se déplaçant
	 * @param origine le territoire d'origine
	 * @param cible   le territoire cible
	 */
	@Override
	public void deplacer(ArrayList<Armee> armees, Territoire origine, Territoire cible) {
		int taille = armees.size();
		if (origine.getArmees().size() - armees.size() <= 1) {
			System.out.println("il doit rester au moins 1 armée sur le territoire d'origine");
		} else {
			if (isVoisin(origine, cible, carte)) {
				if (cible.getProprietaire() != origine.getProprietaire()) {
					//attaquer()
				} else {
					this.bouger(armees,origine,cible);
				}
			} else {
				System.out.println("Vous ne pouvez pas vous déplacer sur un territoire non voisin");
				//afficher des trucs genre lol no possiblo
			}
		}
	}

	public void bouger(ArrayList<Armee> armees, Territoire origine, Territoire cible){
		for (Armee armee : armees) {
			if (armee.getMouvement() > 0) {
				origine.removeArmee(armee);
				cible.addArmee(armee);
				armee.setMouvement(armee.getMouvement() - 1);
				System.out.println(armee.getNom() + " a été déplacé avec succès !");
			} else {
				System.out.println("le " + armee.getNom() + " n'a pas pu être déplacé (plus de mouvements disponibles");
			}
		}
	}
	public int de(int min, int max){
		Random rd = new Random();
		return rd.nextInt(max - min + 1)+min;
	}

	public int attaque(ArrayList<Armee> armees, Territoire origine, Territoire cible){
		if(armees.size()>3){
			System.out.println("Vous ne pouvez pas attaquer avec plus de 3 armées");
			return -1;
		}else{
			for(Armee armee:armees){
				armee.setScore(de(armee.getPuissanceMin(),armee.getPuissanceMax()));
			}
			//je sais pas encore selectionner les 1 ou 2 defenseurs cibles en fonction des priorites defenses
		}
		return -1;
	}

	public boolean isVoisin(Territoire origine, Territoire cible, Carte carte){
		return carte.areVoisins(origine.getNumero(), cible.getNumero());
	}



	/**
	 * Calcule la quantité d'armées de renforts reçues par un joueur en début de tour
	 *
	 * @param j le joueur concerné
	 * @return le nombre d'armées de renforts
	 */
	@Override
	public int renforts(Joueur j) {
		int r = 0;
		//pour les territoires
		r = r + Math.max(nombreTerritoireCarte(j)/2,2);
		//pour les regions
		r = r + nombreRenfortsRegion(j);
		//pour les victoires
		for(int i = 1; i <= j.getNbConquetes() ; i ++){
			r = r + de(0,1) ;
		}
		return r;
	}

	public int nombreRenfortsRegion(Joueur j){
		int i = 0;
		for (Region r : this.carte.getRegions() ) {
			if(r.estControleePar(j)){
				i = i + (r.getTerritoires().size()/2);
			}

		}
		return i;
	}

	public int nombreTerritoireCarte(Joueur j){
		int i = 0;
		for (Region r :this.carte.getRegions()) {
			i += r.nombreTerritoires(j);
		}
		return i;
	}


	@Override
	public void setCarte(Carte c){
		carte = c;
	}
}
