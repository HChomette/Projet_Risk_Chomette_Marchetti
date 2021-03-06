package partie;

import jeu.Armee;
import jeu.Carte;
import jeu.Joueur;
import jeu.Territoire;

import java.util.ArrayList;

/**
 * Interface de règles de résolution des actions en jeu.<br/>
 * Pour changer les règles de combat, on change la classe en implémentant cette interface.
 */
public interface IReglesAction {

	/**
	 * Deplace des armées d'un point d'origine à un point de destination, et résout les conflits si nécessaire
	 * @param armees les armées se déplaçant
	 * @param origine le territoire d'origine
	 * @param cible le territoire cible
	 */
	void deplacer(ArrayList<Armee> armees, Territoire origine, Territoire cible);

	void attaquer(ArrayList<Armee> armees, Territoire origine, Territoire cible);

	/**
	 * Calcule la quantité d'armées de renforts reçues par un joueur en début de tour
	 * @param j le joueur concerné
	 * @return le nombre d'armées de renforts
	 */
	int renforts(Joueur j);

	/**
	 * Donne la carte de la partie à la classe
	 * @param carte la carte utilisée
	 */
	void setCarte(Carte carte);

	int nombreArmeesInit(int nbJoueur);

	boolean verifChoixArmees(Joueur j, int nbArmees);

	boolean verifChoixPlacement(Joueur j, int nbUnitesRestantes);

	int de(int min, int max);
}
