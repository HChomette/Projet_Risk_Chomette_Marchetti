import edu.princeton.cs.introcs.In;
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
    	double xSize = 1200;
    	double ySize = 614;
    	int tailleMenu = 400;
		double xScale = (xSize + tailleMenu)/ySize;
		double facteur = xSize / ySize; //Utilisé pour placer les points sur la carte et pas le menu

    	//Chargement des fichiers
		Partie p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
		p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence", 42));
		p.getCarte().setLocalisations(MapLoader.loadLocalisations("resources/localizations"));
		p.getRegles().setCarte(p.getCarte());

		//Affichage de la carte
		CarteManager.initCarte(xScale, 1, 0.005, "resources/riskmap.png", xSize, ySize, tailleMenu);
		CarteManager.initMenu(tailleMenu, xSize, ySize, xScale, 1);

		//Dessin du cercle de chaque territoire
		for(Point point : p.getCarte().getLocalisations()){
			CarteManager.dessineCercle(Color.BLACK, point.getX() * facteur, point.getY(), Point.getRadius() * 1.2);
			CarteManager.dessineCercle(Color.WHITE, point.getX() * facteur, point.getY(), Point.getRadius());
		}

		//Initialisation des joueurs & de leur nombre
		String[] choices = {"2", "3", "4", "5", "6"};

		int res = Integer.parseInt((String)JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Combien de joueurs ?", "Initialisation", JOptionPane.PLAIN_MESSAGE, null, choices, "2"));

		ArrayList<String> nomsJoueurs = new ArrayList<>();
		for(int i = 0; i < res; i++){
			String name = null;
			while(name == null) {
				name = JOptionPane.showInputDialog("Entrez le nom du joueur " + (i + 1));
				p.addJoueur(new Joueur(name));
				nomsJoueurs.add(name);
			}
		}
		CarteManager.legende(nomsJoueurs);

		//Répartition des territoires au différents joueurs
		p.distributionTerritoires();

		for(Territoire t : p.getCarte().getTerritoires()){
			Joueur j = t.getProprietaire();
			Point point = p.getCarte().getLocalisation(t.getNumero());
			CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)), point.getX() * facteur, point.getY(), Point.getRadius(), 0);
		}

		//Dépenser ses points pour acheter des soldats
		ArrayList<String> types = UnitFactory.getTypes();
		ArrayList<Integer> couts = new ArrayList<>();
		for(String type : types){
			Armee a = UnitFactory.getArmee(type);
			int cout = a.getCout();
			couts.add(cout);
		}

		for(Joueur j : p.getJoueurs()){

			int renforts = p.getRegles().nombreArmeesInit(p.getJoueurs().size());

			int total = 0;
			ArrayList<Integer> choix = new ArrayList<>();
			do{
				total = 0;
				PopupManager.avertNombre();
				choix = PopupManager.choixTypes(types, couts, renforts);
				for(Integer integer : choix) total += integer;
			}while(p.getRegles().verifChoixArmees(j, total));


			//Placement des armées
			for(int i = 0; i < choix.size(); i++){

			}
		}



		//}

		//TEST UNIQUEMENT
		while(true){
			if(StdDraw.isMousePressed()){
				System.out.println("Pass : " + CarteManager.isPass(StdDraw.mouseX(), StdDraw.mouseY()));
				System.out.println(StdDraw.mouseX() / facteur + " - " + StdDraw.mouseY());
				System.out.println(p.getCarte().getTarget(StdDraw.mouseX() / facteur, StdDraw.mouseY()));
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
