import jeu.Joueur;
import jeu.Territoire;
import partie.MapLoader;
import partie.Partie;
import regles.ReglesAction;
import unites.UnitFactory;

import java.util.ArrayList;

/**
 * Projet de jeu de Risk simplifié. <br/>
 * Classe principale.
 * @author Camille Marhetti, Hector Chomette
 */

public class Main {

    public static void main(String[] args) {

    	//Exemple d'utilisation


		Partie p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
		p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence", 42));

		p.addJoueur(new Joueur("Hector"));
		p.addJoueur(new Joueur("Camille"));
		p.addJoueur(new Joueur("Toufik"));
		p.addJoueur(new Joueur("Hubert"));


		System.out.println(p.getCarte());
		//TODO : partie visuelle





		for(Joueur j : p.getJoueurs()){
			//TODO : Boucle de jeu

			//Début de tour : réception des renforts
			int nbRenforts = 0; //Valeur calculée

			ArrayList<String> catalogue = UnitFactory.getTypes();


			//Sélection des unités à créer
			while(nbRenforts > 0){

				System.out.println(catalogue); //Sera fait en visuel, pas en console
				String choix = null; //Choix déterminé à partir d'un menu visuel

				Territoire cible = null; //Territoire cible choisi au clic

				cible.addArmee(UnitFactory.getArmee(choix)); //Ajout du type d'armée choisi sur le territoire cible
			}

			//Déplacements et attaque
			//Boucle jusqu'à fin du tour par le joueur
		}

    }
}
