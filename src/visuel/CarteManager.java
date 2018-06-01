package visuel;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * Classe statique permettant de dessiner la carte grâce à la bibliothèque StdDraw
 */
public class CarteManager {

	private final static Color[] couleursJoueur = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK};

	/**
	 * Dessine un cercle de territoire.
	 * @param color Couleur du cercle
	 * @param x Position x
	 * @param y Position y
	 * @param radius rayon du cercle
	 */
	public static void dessineCercle(Color color, double x, double y, double radius){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(x, y, radius);
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

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.line(xSize * xScale / (xSize + tailleMenu), 0, xSize * xScale / (xSize + tailleMenu), yScale);
	}



	/**
	 * Retourne la couleur correspondant au joueur de numéro donné
	 * @param num numéro du joueur
	 * @return Couleur du joueur
	 */
	public static Color getColor(int num){
		return couleursJoueur[num];
	}
}
