package regles;

import jeu.*;
import partie.IReglesAction;

import java.util.ArrayList;
import java.util.Collections;
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
		if (origine.getArmees().size() - armees.size() < 1) {
			System.out.println("il doit rester au moins 1 armée sur le territoire d'origine");
		} else {
			if (isVoisin(origine, cible, carte)) {
				if (cible.getProprietaire() != origine.getProprietaire()) {
					System.out.println("vous ne pouvez pas vous déplacer sur un territoire qui ne vous appartient pas");
				} else {
					this.bouger(armees,origine,cible);
				}
			} else {
				System.out.println("Vous ne pouvez pas vous déplacer sur un territoire non voisin");
			}
		}
	}

	/**
	 * Armee attaque un territoire
	 * @param armees pour l'attaque
	 * @param origine
	 * @param cible
	 */
	public void attaquer(ArrayList<Armee> armees, Territoire origine, Territoire cible){
		for(Armee a : armees){
			if(a.getMouvement()<=0){
				System.out.println("cette armée n'a pas assez de points de mouvement pour se déplacer");
			}
		}
		if (origine.getArmees().size() - armees.size() < 1) {
			System.out.println("il doit rester au moins 1 armée sur le territoire d'origine");
		} else {

			if (armees.size() > 3) {
				System.out.println("Vous ne pouvez pas attaquer avec plus de 3 unités");
			}else if(armees.size()==0){
				System.out.println("Vous devez attaquer avec au moins une armee");
			}else {
				//choix des armées de défense sur le territoire cible
				Collections.sort(cible.getArmees(), Armee.SortByPrioriteDefense);
				ArrayList<Armee> defenseurs = new ArrayList<>();
				defenseurs.add(cible.getArmees().get(0));
				if(cible.getArmees().size()>=2){
					defenseurs.add(cible.getArmees().get(1));
				}
				//lancer des des
				for(Armee armee:armees){
					armee.setScore(de(armee.getPuissanceMin(),armee.getPuissanceMax()));
					System.out.println("score attaque :"+armee.getScore());
				}
				for(Armee armee : defenseurs){
					armee.setScore(de(armee.getPuissanceMin(),armee.getPuissanceMax()));
					System.out.println("score defense :"+armee.getScore());
				}

				//tri et comparaison des scores
				Collections.sort(armees, Armee.SortAttaque);
				System.out.println(armees);
				Collections.sort(defenseurs, Armee.SortAttaque);

				//comparaison des premiers scores
				if(armees.get(0).getScore()>=defenseurs.get(0).getScore()){
					System.out.println("defenseur mort");
					cible.removeArmee(defenseurs.get(0));
				}else{
					System.out.println("attaquant mort");
					origine.removeArmee(armees.get(0));
					armees.remove(armees.get(0));
				}
				//comparaison des deuxieme scores si il y en a
				if(armees.size()>=2 && defenseurs.size()==2){
					if(armees.get(1).getScore()>=defenseurs.get(1).getScore()){
						System.out.println("defenseur mort");
						cible.removeArmee(defenseurs.get(1));
					}else{
						System.out.println("attaquant mort");
						origine.removeArmee(armees.get(1));
						armees.remove(armees.get(1));
					}
				}
				//TODO : Il y en avait pas 3 ?
				//si il ne reste plus de defenseurs, on fait une capture
				if(cible.getArmees().size()==0){
					capturer(armees, origine, cible);
				}

			}
		}
	}

	/**
	 * Capture un territoire attaqué qui n'a plus de défenseur
	 * @param armees
	 * @param origine
	 * @param cible
	 */

	public void capturer(ArrayList<Armee> armees, Territoire origine, Territoire cible){
		cible.setProprietaire(origine.getProprietaire());
		origine.removeAllArmee(armees);
		cible.addAllArmee(armees);
		origine.getProprietaire().addConquete();
		for(Armee a :armees){
			a.setMouvement(a.getMouvement() - 1);
		}
		origine.getProprietaire().addConquete();
		System.out.println("territoire capturé");
	}


	/**
	 *bouguer une armee d'un territoire a l'autre
	 * @param armees
	 * @param origine
	 * @param cible
	 */
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
		r = r + Math.max(carte.nombreTotalTerritoires(j)/2,2);
		//pour les regions
		r = r + nombreRenfortsRegion(j);
		//pour les victoires
		for(int i = 1; i <= j.getNbConquetes() ; i ++){
			r = r + de(0,1) ;
		}
		return r;
	}

	/**
	 * @param j joueur
	 * @return nb renforts d'un joueur j en fct des regions controllees
	 */
	public int nombreRenfortsRegion(Joueur j){
		int i = 0;
		for (Region r : carte.getRegions() ) {
			if(r.estControleePar(j)){
				i = i + (r.getTerritoires().size()/2);
			}

		}
		return i;
	}




	@Override
	public void setCarte(Carte c){
		carte = c;
	}

	/**
	 * Détermine le nombre d'armées à l'initialisation
	 * possibilité de changer les règles et les nombres d'armées
	 * @param nbJoueurs
	 * @return nombre d'armées
	 */
	public int nombreArmeesInit(int nbJoueurs){
		switch (nbJoueurs){
			case 2 :
				return 40;

			case 3 :
				return 35;

			case 4 :
				return 30;

			case 5 :
				return 25;

			case 6 :
				return 20;
		}
		return -1;
	}

	/**
	 *Vérifie que le joueur crée assez d'armées pour ses territoires
	 * @param j (joueur)
	 * @param nbArmees
	 * @return true/false
	 */
	public boolean verifChoixArmees(Joueur j, int nbArmees){
		return !(nbArmees<carte.nombreTotalTerritoires(j));
	}

	/**
	 *Vérifie qu'un joueur place ses armées de sorte à couvrir l'ensemble de ses territoires
	 * @param j
	 * @param nbUnitesRestantes
	 * @return true/false
	 */

	public boolean verifChoixPlacement(Joueur j,int nbUnitesRestantes){
		int count = 0;
		for (Territoire t : carte.getTerritoires()) {
			if(t.getProprietaire()==j){
				if(t.getArmees().size()==0){
					count++;
				}
			}
		}
		return count <= nbUnitesRestantes;
	}
}
