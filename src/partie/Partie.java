package partie;

import plateau.Carte;

import java.util.ArrayList;

/**
 * Classe principale gérant l'ensemble d'une partie de Risk
 */

public class Partie {

	private Carte carte;
	private ArrayList<Joueur> joueurs;
	private IReglesConflit regles;
}
