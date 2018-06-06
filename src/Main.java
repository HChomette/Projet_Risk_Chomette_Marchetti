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

	private static double xSize = 1200;
	private static double ySize = 614;
	private static int nbTerr = 42;
	//Valeurs pour la 2e carte
	//private static double xSize = 537;
	//private static double ySize = 799;
	//private static int nbTerr = 48;
	private static int tailleMenu = 400;
	private static double xScale = (xSize + tailleMenu)/ySize;
	private static double facteur = xSize / ySize; //Utilisé pour placer les points sur la carte et pas le menu

    public static void main(String[] args) {

    	//Chargement des fichiers
		Partie p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
		p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence", nbTerr));
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
		//TODO : passer dans popupmanager
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

			int total;
			ArrayList<Integer> choix;
			do{
				total = 0;
				PopupManager.avertNombre();
				choix = PopupManager.choixTypes(types, couts, renforts);
				for(Integer integer : choix) total += integer;
			}while(!p.getRegles().verifChoixArmees(j, total));

			ArrayList<Armee> armeesInit = new ArrayList<>();
			//Création des armées
			for(int i = 0; i < choix.size(); i++){
				for(int k = 0; k < choix.get(i); k++){
					armeesInit.add(UnitFactory.getArmee(types.get(i)));
				}
			}

			double[] pos;
			//Placement des armées
			for(int i = 0; i < armeesInit.size(); i++){
				Territoire t;
				int empty = 1;
				do {
					PopupManager.askPlacement(j.getNom(), armeesInit.get(i).getNom());
					pos = waitClic();
					t = p.getCarte().getTarget(pos[0] / facteur, pos[1]);
					if(t != null) empty = t.getArmees().size() == 0?0:1;
				}while(t == null || t.getProprietaire() != j || !p.getRegles().verifChoixPlacement(j, armeesInit.size() - i - empty));

				t.addArmee(armeesInit.get(i));
				Point point = p.getCarte().getLocalisation(t.getNumero());
				CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
						point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
			}

		}



		//}

		//TEST UNIQUEMENT
		double[] coord = waitClic();
		System.out.println("Pass : " + CarteManager.isPass(coord[0], coord[1]));
		System.out.println(coord[0] / facteur + " - " + coord[1]);
		System.out.println(p.getCarte().getTarget(coord[0] / facteur, coord[1]));

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

	/**
	 * Attends le prochain clic, et renvoie ses coordonnées
	 * @return double[0] le x, double[1] le y
	 */
	private static double[] waitClic(){
		while(true){
			if(StdDraw.isMousePressed()){
				double[] res = new double[]{StdDraw.mouseX(), StdDraw.mouseY()};
				try {
					TimeUnit.MILLISECONDS.sleep(130);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
				return res;
			}

		}
	}
}
