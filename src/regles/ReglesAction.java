package regles;

import partie.IReglesAction;
import jeu.Joueur;
import jeu.Armee;
import jeu.Territoire;

import java.util.ArrayList;

/**
 * Classe regroupant les règles de résolution de conflit et les traitant de manière statique. <br/>
 * Cette classe correspond aux règles de base de l'énoncé.
 */
public class ReglesAction implements IReglesAction {
	/**
	 * Deplace des armées d'un point d'origine à un point de destination, et résout les conflits si nécessaire
	 *
	 * @param armees  les armées se déplaçant
	 * @param origine le territoire d'origine
	 * @param cible   le territoire cible
	 */
	@Override
	public void deplacer(ArrayList<Armee> armees, Territoire origine, Territoire cible) {
		//TODO

		int taille = armees.size();
		if(cible.getProprietaire() != origine.getProprietaire()){
			//attaquer()
		}else{
			for (Armee armee : armees) {
				origine.removeArmee(armee);
				cible.addArmee(armee);
			}
		}

	}

	public boolean isVoisin(Territoire origine, Territoire cible){
		for (Territoire voisin : origine.getVoisins()) {
			if (voisin == cible){
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcule la quantité d'armées de renforts reçues par un joueur en début de tour
	 *
	 * @param j le joueur concerné
	 * @return le nombre d'armées de renforts
	 */
	@Override
	public int renforts(Joueur j) {
		//TODO
		return 0;
	}
}
