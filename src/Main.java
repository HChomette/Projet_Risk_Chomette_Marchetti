import jeu.Joueur;
import partie.MapLoader;
import partie.Partie;
import regles.ReglesAction;

/**
 * Projet de jeu de Risk simplifié. <br/>
 * Classe principale.
 * @author Camille Marhetti, Hector Chomette
 */

public class Main {

    public static void main(String[] args) {

    	//Exemple d'utilisation


		Partie p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
		//TODO : ajout des régions dans la map
		p.addJoueur(new Joueur("Hector"));
		p.addJoueur(new Joueur("Camille"));
		p.addJoueur(new Joueur("Toufik"));
		p.addJoueur(new Joueur("Hubert"));

		System.out.println(p.getCarte());
		//TODO : partie visuelle

		for(Joueur j : p.getJoueurs()){
			//TODO : Boucle de jeu
		}

    }
}
