import edu.princeton.cs.introcs.StdDraw;
import jeu.Armee;
import jeu.Joueur;
import jeu.Territoire;
import localisation.Point;
import partie.MapLoader;
import partie.Partie;
import regles.ReglesAction;
import unites.UnitFactory;
import visuel.CarteManager;
import visuel.PopupManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Projet de jeu de Risk simplifié. <br/>
 * Classe principale. Gère la boucle de jeu et le visuel.
 * @author Camille Marchetti, Hector Chomette
 */

public class Main {

    public static void main(String[] args) {
    	double xScale = 1200f/614f;

    	//Chargement des fichiers
		Partie p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
		p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence", 42));
		p.getCarte().setLocalisations(MapLoader.loadLocalisations("resources/localizations"));

		System.out.println(p.getCarte());

		//Affichage de la carte
		CarteManager.initCarte(xScale, 1, 0.005, "resources/riskmap.png", 1200, 614);

		//Dessin du cercle de chaque territoire
		for(Point point : p.getCarte().getLocalisations()){
			CarteManager.dessineCercle(Color.BLACK, point.getX() * xScale, point.getY(), Point.getRadius() * 1.2);
			CarteManager.dessineCercle(Color.WHITE, point.getX() * xScale, point.getY(), Point.getRadius());
		}

		//Initialisation des joueurs & de leur nombre
		String[] choices = {"2", "3", "4", "5", "6"};

		int res = Integer.parseInt((String)JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Combien de joueurs ?", "Initialisation", JOptionPane.PLAIN_MESSAGE, null, choices, "2"));

		for(int i = 0; i < res; i++){
			String name = null;
			while(name == null) {
				name = JOptionPane.showInputDialog("Entrez le nom du joueur " + (i + 1));
				p.addJoueur(new Joueur(name));
			}
		}

		//Répartition des territoires au différents joueurs
		//TODO
		p.distributionTerritoires();

		for(Territoire t : p.getCarte().getTerritoires()){
			Joueur j = t.getProprietaire();
			Point point = p.getCarte().getLocalisation(t.getNumero());
			CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)), point.getX() * xScale, point.getY(), Point.getRadius());
		}

		//Dépenser ses points pour acheter des soldats
		int renforts = 9; //Test
		ArrayList<String> types = UnitFactory.getTypes();
		ArrayList<Integer> couts = new ArrayList<>();
		for(String type : types){
			Armee a = UnitFactory.getArmee(type);
			int cout = a.getCout();
			couts.add(cout);
		}

		while(renforts > 0){
			String typeChoisi = PopupManager.choixType(types, couts, renforts);
			if(typeChoisi != null) {
				Armee choix = UnitFactory.getArmee(typeChoisi);
				System.out.println(choix);
				renforts -= choix.getCout();
			}
		}

		//TEST UNIQUEMENT
		while(true){
			if(StdDraw.isMousePressed()){
				System.out.println(StdDraw.mouseX() + " - " + StdDraw.mouseY());
				System.out.println(p.getCarte().getTarget(StdDraw.mouseX() / xScale, StdDraw.mouseY()));
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
