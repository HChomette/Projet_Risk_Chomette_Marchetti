package visuel;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Classe statique permettant de dessiner la carte grâce à la bibliothèque StdDraw
 */
public class CarteManager {

	private final static Color[] couleursJoueur = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK};

	private static double leftMenu;
	private static double rightMenu;

	private static double yMin; //Limites des différents boutons
	private static double yPass;
	private static double yTurn;
	private static double yLegende;

	private static int compteur = 0; //Compteur de sauvegardes

	/**
	 * Dessine un cercle de territoire.
	 * @param color Couleur du cercle
	 * @param x Position x
	 * @param y Position y
	 * @param radius rayon du cercle
	 */
	public static void dessineCercle(Color color, double x, double y, double radius){
		dessineCercle(color, x, y, radius, -1);
	}
	public static void dessineCercle(Color color, double x, double y, double radius, int nombre){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(x, y, radius);
		ecrisNombre(nombre, x, y);
	}

	/**
	 * Ecris un nombre au milieu d'une pastille de territoire
	 * @param nombre Le nombre à écrire
	 * @param x position x de la pastille
	 * @param y position y de la pastille
	 */
	private static void ecrisNombre(int nombre, double x, double y){
		if(nombre == -1) return;
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(x, y, Integer.toString(nombre));
	}

	/**
	 * Affiche la carte et définit les modalités de la fenêtre de jeu
	 * @param xScale échelle des abscisses
	 * @param yScale échelle des ordonnées
	 * @param penRadius rayon du stylo
	 * @param path emplacement du fichier
	 * @param xSize taille x du fichier
	 * @param ySize taille y du fichier
	 */
	public static void initCarte(double xScale, double yScale, double penRadius, String path, double xSize, double ySize, double tailleMenu){
		StdDraw.setCanvasSize((int)xSize + (int)tailleMenu, (int)ySize);
		StdDraw.picture(0.5 * (xSize / (xSize + tailleMenu)), 0.5, path);
		StdDraw.setPenRadius(penRadius);
		StdDraw.setYscale(0, yScale);
		StdDraw.setXscale(0, xScale);
		Font f = new Font ("Monospaced", Font.BOLD, 18);
		StdDraw.setFont(f);

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.line(xSize * xScale / (xSize + tailleMenu), 0, xSize * xScale / (xSize + tailleMenu), yScale);
	}

	public static void initMenu(int tailleMenu, double xSize, double ySize, double xScale, double yScale){
		leftMenu = xSize * xScale / (xSize + tailleMenu);
		rightMenu = xScale;
		yMin = 0;
		yPass = yScale / 4;
		yLegende = 3 * yScale / 4;
		yTurn = yScale;

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.line(leftMenu, yPass, rightMenu, yPass);
		StdDraw.text((leftMenu + rightMenu) / 2, yPass / 2, "Passer le tour");

		StdDraw.line(leftMenu, yLegende, rightMenu, yLegende);
		//StdDraw.text((leftMenu + rightMenu) / 2, (yLegende + yPass) / 2, "Légende");

		tourJoueur(0);
	}

	/**
	 * Passe l'affichage au tour du joueur suivant
	 * @param num numéro du joueur (à partir de 0)
	 */
	public static void tourJoueur(int num){
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.filledRectangle((leftMenu + rightMenu) / 2, (yTurn + yLegende) / 2,
				(rightMenu - leftMenu - StdDraw.getPenRadius()) / 2, (yTurn - yLegende - StdDraw.getPenRadius()) / 2);

		StdDraw.setPenColor(getColor(num));
		StdDraw.text((leftMenu + rightMenu) / 2, (yTurn + yLegende) / 2, "Tour du joueur " + (num + 1));
	}

	/**
	 * Ecris la légende des joueurs
	 * @param joueurs les noms des joueurs
	 */
	public static void legende(ArrayList<String> joueurs){
		for(int i = 0; i < joueurs.size(); i++){
			StdDraw.setPenColor(getColor(i));
			StdDraw.text((leftMenu + rightMenu) / 2, yLegende - 0.05 - i * 0.08, joueurs.get(i));
		}
	}

	/**
	 * Vérifie si l'utilisateur a cliqué sur passer son tour
	 * @param x position x de sa souris
	 * @param y position y de sa souris
	 * @return true si passer, false sinon
	 */
	public static boolean isPass(double x, double y){
		return (x <= rightMenu && x >= leftMenu && y < yPass);
	}

	/**
	 * Retourne la couleur correspondant au joueur de numéro donné
	 * @param num numéro du joueur
	 * @return Couleur du joueur
	 */
	public static Color getColor(int num){
		return couleursJoueur[num];
	}

	public static void highlight(Color color, double x, double y, double radius){
		StdDraw.setPenColor(color);
		StdDraw.circle(x, y, radius);
	}

	/**
	 * StdDraw utilise un cache. On doit don
	 * @param path
	 */
	public static void save (String path){
		//StdDraw utilise un cache. On doit donc changer le nom de la sauvegarde pour ne pas réutiliser la même image.
		if(compteur > 0){
			File file = new File(path + (compteur - 1) + ".png"); //On supprime l'ancienne sauvegarde
			file.delete();
		}
		StdDraw.save(path + compteur + ".png");
		compteur++;
	}

	public static void load (String path, double xScale){
		StdDraw.picture(0.5 * xScale, 0.5, path + (compteur - 1) + ".png");
	}
}
