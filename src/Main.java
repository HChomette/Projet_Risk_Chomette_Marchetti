import edu.princeton.cs.introcs.StdDraw;
import jeu.Joueur;
import localisation.Point;
import partie.MapLoader;
import partie.Partie;
import regles.ReglesAction;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

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
		p.getCarte().setLocalisations(MapLoader.loadLocalisations("resources/localizations"));

		System.out.println(p.getCarte());

		StdDraw.setCanvasSize(1200, 614);
		StdDraw.picture(0.5, 0.5, "resources/riskmap.png");
		StdDraw.setPenRadius(0.005);


		for(Point point : p.getCarte().getLocalisations()){
			StdDraw.circle(point.getX(), point.getY(), Point.getRadius());
		}

		String[] choices = {"2", "3", "4", "5", "6"};

		int res = Integer.parseInt((String)JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Combien de joueurs ?", "Initialisation", JOptionPane.PLAIN_MESSAGE, null, choices, "2"));

		for(int i = 0; i < res; i++){
			String name = null;
			while(name == null) {
				name = JOptionPane.showInputDialog("Entrez le nom du joueur " + (i + 1));
				p.addJoueur(new Joueur(name));
			}
		}

		System.out.println(p.getJoueurs());

		while(true){
			if(StdDraw.isMousePressed()){
				System.out.println(StdDraw.mouseX() + " - " + StdDraw.mouseY());
				System.out.println(p.getCarte().getTarget(StdDraw.mouseX(), StdDraw.mouseY()));
			}
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}

/*
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
		}*/

    }
}
