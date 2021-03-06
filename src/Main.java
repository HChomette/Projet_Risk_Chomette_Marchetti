import edu.princeton.cs.introcs.StdDraw;
import jeu.Armee;
import jeu.Joueur;
import jeu.Territoire;
import localisation.Point;
import partie.IA;
import partie.MapLoader;
import partie.Partie;
import regles.ReglesAction;
import unites.UnitFactory;
import visuel.CarteManager;
import visuel.PopupManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Projet de jeu de Risk simplifié. <br/>
 * Classe principale. Gère la boucle de jeu et le visuel.
 * @author Camille Marchetti, Hector Chomette
 */

public class Main {

	private static double xSize ;
	private static double ySize ;
	private static int nbTerr ;
	private static int tailleMenu = 400;
	private static double xScale = (xSize + tailleMenu)/ySize;
	private static double facteur ; //Utilisé pour placer les points sur la carte et pas le menu
	private static Partie p;

    public static void main(String[] args) {

    	String[] choixCarte = {"Terre","Westeros"};
		boolean isMonde = ((String)(JOptionPane.showInputDialog(JOptionPane.getRootFrame(),
				"Sur quel plateau voulez-vous jouer ?", "Choix de la carte",
				JOptionPane.PLAIN_MESSAGE, null, choixCarte,"Terre"))).equals("Terre");
		if(isMonde){ Main.setCarte("Terre");} else {Main.setCarte("Westeros");}
    	//Chargement des fichiers


		p.getRegles().setCarte(p.getCarte());
		IA.setCarte(p.getCarte());
		IA.setRegles(p.getRegles());



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
				Joueur jCourant = new Joueur(name);
				p.addJoueur(jCourant);
				String[] ouiNon = {"Oui", "Non"};

				boolean isIA = ((String)(JOptionPane.showInputDialog(JOptionPane.getRootFrame(),
						"Est-ce une IA ?", "Initialisation",
						JOptionPane.PLAIN_MESSAGE, null, ouiNon, "Non"))).equals("Oui");
				if(isIA) p.setIA(jCourant);

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

			CarteManager.tourJoueur(p.getJoueurs().indexOf(j));

			int renforts = p.getRegles().nombreArmeesInit(p.getJoueurs().size());

			if(p.isIA(j)){
				IA.renforts(j, renforts);
				for(Territoire t : p.getCarte().territoiresJoueur(j)){
					Point point = p.getCarte().getLocalisation(t.getNumero());
					CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
							point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
				}
			} else {

				int total;
				ArrayList<Integer> choix;
				do {
					total = 0;
					PopupManager.alert("Vous devez créer au moins un soldat par territoire possédé.");
					choix = PopupManager.choixTypes(types, couts, renforts);
					for (Integer integer : choix) total += integer;
				} while (!p.getRegles().verifChoixArmees(j, total));

				ArrayList<Armee> armeesInit = new ArrayList<>();
				//Création des armées
				for (int i = 0; i < choix.size(); i++) {
					for (int k = 0; k < choix.get(i); k++) {
						armeesInit.add(UnitFactory.getArmee(types.get(i)));
					}
				}

				double[] pos;
				//Placement des armées
				for (int i = 0; i < armeesInit.size(); i++) {
					Territoire t;
					int empty = 1;
					do {
						PopupManager.alert(j.getNom() + ", veuillez placer votre " + armeesInit.get(i).getNom());
						pos = waitClic(-1);
						t = p.getCarte().getTarget(pos[0] / facteur, pos[1]);
						if (t != null) empty = t.getArmees().size() == 0 ? 0 : 1;
						if(!p.getRegles().verifChoixPlacement(j, armeesInit.size() - i - empty)){
							PopupManager.alert("Vous ne pouvez pas placer votre unité ici. Vous devez avoir au moins une unité par territoire");
						}
					}
					while (t == null || t.getProprietaire() != j || !p.getRegles().verifChoixPlacement(j, armeesInit.size() - i - empty));

					t.addArmee(armeesInit.get(i));
					Point point = p.getCarte().getLocalisation(t.getNumero());
					CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
							point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
				}
			}
		}


		//Boucle de jeu
		boolean fini = false;
		while(!fini) {
			for (Joueur j : p.getJoueurs()) {


				if(fini) break;
				if(p.getCarte().nombreTotalTerritoires(j)>0) {
					CarteManager.tourJoueur(p.getJoueurs().indexOf(j)); //Changement du tour de joueur affiché
					PopupManager.alert("Tour du joueur " + j.getNom());

					//Début de tour : réception des renforts
					int renforts = p.getRegles().renforts(j); //Valeur calculée
					j.resetConquetes();
					if (p.isIA(j)) {
						IA.renforts(j, renforts);
						for (Territoire t : p.getCarte().territoiresJoueur(j)) {
							Point point = p.getCarte().getLocalisation(t.getNumero());
							CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
									point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
						}
					} else {
						PopupManager.alert("Choisissez vos renforts de début de tour");
						ArrayList<Integer> choix = PopupManager.choixTypes(types, couts, renforts);

						ArrayList<Armee> armeesInit = new ArrayList<>();
						//Création des armées
						for (int i = 0; i < choix.size(); i++) {
							for (int k = 0; k < choix.get(i); k++) {
								armeesInit.add(UnitFactory.getArmee(types.get(i)));
							}
						}
						//Placement des renforts
						double[] pos;
						for (int i = 0; i < armeesInit.size(); i++) {
							Territoire t;
							do {
								PopupManager.alert(j.getNom() + ", veuillez placer votre " + armeesInit.get(i).getNom());
								pos = waitClic(-1);
								t = p.getCarte().getTarget(pos[0] / facteur, pos[1]);
							} while (t == null || t.getProprietaire() != j);

							t.addArmee(armeesInit.get(i));
							Point point = p.getCarte().getLocalisation(t.getNumero());
							CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
									point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
						}
					}

					if (p.isIA(j)) {
						IA.jouer(j);
					} else {
						double[] pos;
						boolean end = false;
						while (!end) {
							pos = waitClic(KeyEvent.VK_I);
							Territoire cible = p.getCarte().getTarget(pos[0] / facteur, pos[1]);
							if (CarteManager.isPass(pos[0], pos[1])) end = true; //Clic sur fin du tour
							else if (cible != null && cible.getProprietaire() == j && cible.getArmees().size() > 1) { //Clic sur un territoire possédé
								CarteManager.save("temp"); //Sauvegarde de la carte avant d'highlight les voisins
								ArrayList<Territoire> voisins = p.getCarte().getVoisins(cible);
								for (Territoire t : voisins) {
									Point point = p.getCarte().getLocalisation(t.getNumero());
									if (t.getProprietaire() == j) {
										CarteManager.highlight(Color.GREEN, point.getX() * facteur, point.getY(), Point.getRadius() * 1.3); //Territoires amis
									} else {
										CarteManager.highlight(Color.RED, point.getX() * facteur, point.getY(), Point.getRadius() * 1.3); //Territoires ennemis
									}
								}
								//Cancel highlight après action
								//Choix de la cible de l'action
								boolean done = false;
								while (!done) {
									pos = waitClic(-1);
									Territoire cible2 = p.getCarte().getTarget(pos[0] / facteur, pos[1]);
									if (cible2 != null && voisins.contains(cible2)) {
										CarteManager.load("temp", xScale); //TODO : Problème de save
										//Popup de sélection des armées utilisées
										ArrayList<String> options = new ArrayList<>();
										for (Armee a : cible.getArmees()) {
											options.add(a.getNom() + " - " + a.getMouvement() + " mouvements restants.");
											//TODO : pas proposer unités sans mouvement
										}
										ArrayList<Integer> stackChoisi = PopupManager.selectList(options, "Sélection");
										while (!PopupManager.isCallbackButton()) {
											try {
												TimeUnit.MILLISECONDS.sleep(100); //Pour ne pas check la condition trop souvent
											} catch (Exception e) {
											}
										} //On attend que l'utilisateur ait appuyé sur le bouton
										PopupManager.setCallbackButton(false); //On reset l'état du bouton.
										ArrayList<Armee> unitesChoisies = new ArrayList<>(); //Liste des unités choisies par le joueur
										for (Integer index : stackChoisi) {
											unitesChoisies.add(cible.getArmees().get(index));
										}
										//Attaque ou déplacement & coloration résultat
										boolean validChoice = true;
										if (cible2.getProprietaire() != j) {
											if (unitesChoisies.size() > 3) {
												validChoice = false;
												PopupManager.alert("3 armées maximum pour attaquer. Veuillez recommencer votre choix.");
											} else {
												p.getRegles().attaquer(unitesChoisies, cible, cible2);
												done = true;
											}
										} else {
											for (Armee a : unitesChoisies) {
												if (a.getMouvement() < 1) {
													validChoice = false;
													PopupManager.alert("Votre " + a.getNom() + " n'a plus de mouvement disponible. Veuillez reommencer votre choix.");
												}
											}
											if (validChoice) {
												p.getRegles().deplacer(unitesChoisies, cible, cible2);
												done = true;
											}
										}

										if (validChoice) {
											Point point = p.getCarte().getLocalisation(cible.getNumero());
											CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(j)),
													point.getX() * facteur, point.getY(), Point.getRadius(), cible.getArmees().size());

											Point point2 = p.getCarte().getLocalisation(cible2.getNumero());
											CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(cible2.getProprietaire())),
													point2.getX() * facteur, point2.getY(), Point.getRadius(), cible2.getArmees().size());
										}


									}
								}

							} else if (pos[0] == -1 && pos[1] == -1) { //Informations du territoire
								Territoire cibleTouche = p.getCarte().getTarget(StdDraw.mouseX() / facteur, StdDraw.mouseY());
								if (cibleTouche != null) {
									ArrayList<String> infos = new ArrayList<>();
									for (Armee a : cibleTouche.getArmees()) {
										infos.add(a.getNom() + " : " + a.getMouvement() + " mouvements restants");
									}
									PopupManager.list(infos, "Informations");
								}
							}
						}
					}

					for (Territoire t : p.getCarte().getTerritoires()) {
						Point point = p.getCarte().getLocalisation(t.getNumero());
						CarteManager.dessineCercle(CarteManager.getColor(p.getJoueurs().indexOf(t.getProprietaire())),
								point.getX() * facteur, point.getY(), Point.getRadius(), t.getArmees().size());
					}


					//Check de victoire
					Joueur gagnant = p.checkVictoireDestruction();
					if (gagnant != null) {
						PopupManager.alert(gagnant.getNom() + " a gagné la partie !");
						fini = true;
					}

					for (Territoire tOwned : p.getCarte().territoiresJoueur(j)) {
						for (Armee a : tOwned.getArmees()) {
							a.setMouvement(a.getMouvementMax());
						}
					}
				}


			}

		}

    }

	/**
	 * Attends le prochain clic, et renvoie ses coordonnées
	 * @param keyCode le code de la touche demandée, ou -1 si on ne veut aucune touche
	 * @return double[0] le x, double[1] le y, ou -1 et -1 quand on a pressé la touche demandée à la place
	 */
	private static double[] waitClic(int keyCode){
		while(true){
			if(StdDraw.isMousePressed()){
				double[] res = new double[]{StdDraw.mouseX(), StdDraw.mouseY()};
				try {
					TimeUnit.MILLISECONDS.sleep(130); /*On fait passer du temps pour éviter de compter
					Plusieurs fois le clic*/
				} catch (InterruptedException e){
					e.printStackTrace();
				}
				return res;
			} else if (keyCode != -1 && StdDraw.isKeyPressed(keyCode)){
				double[] res = new double[]{-1, -1};
				try {
					TimeUnit.MILLISECONDS.sleep(130);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
				return res;
			}

		}
	}

	public static void setCarte(String choix){
		switch (choix){
			case "Terre" :
				xSize = 1200;
				ySize = 614;
				nbTerr = 42;
				xScale = (xSize + tailleMenu)/ySize;
				facteur = xSize / ySize;
				p = new Partie(MapLoader.loadMap("resources/territoire.json"), new ReglesAction());
				p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence", nbTerr));
				p.getCarte().setLocalisations(MapLoader.loadLocalisations("resources/localizations"));
				p.getRegles().setCarte(p.getCarte());

				//Affichage de la carte
				CarteManager.initCarte(xScale, 1, 0.005, "resources/riskmap.png", xSize, ySize, tailleMenu);
				CarteManager.initMenu(tailleMenu, xSize, ySize, xScale, 1);
				break;

			case "Westeros" :
				xSize = 537;
				ySize = 799;
				nbTerr = 48;
				xScale = (xSize + tailleMenu)/ySize;
				facteur = xSize / ySize;
				p = new Partie(MapLoader.loadMap("resources/territoire2.json"), new ReglesAction());
				p.getCarte().setAdjacence(MapLoader.loadAdjacence("resources/adjacence2", nbTerr));
				p.getCarte().setLocalisations(MapLoader.loadLocalisations("resources/localizations2"));
				p.getRegles().setCarte(p.getCarte());

				//Affichage de la carte
				CarteManager.initCarte(xScale, 1, 0.005, "resources/riskmap2.jpg", xSize, ySize, tailleMenu);
				CarteManager.initMenu(tailleMenu, xSize, ySize, xScale, 1);
				break;
		}
	}
}
