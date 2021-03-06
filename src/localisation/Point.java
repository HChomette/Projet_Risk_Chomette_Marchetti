package localisation;

public class Point {

	private final static double radius = 0.02;
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Vérifie si les coordonnées données sont dans le cercle du point
	 * @param xClic abscisse
	 * @param yClic ordonnée
	 * @return true si dans le cercle, false sinon
	 */
	public boolean isIn(double xClic, double yClic){
		double distance = Math.sqrt(Math.pow(xClic - x, 2) + Math.pow(yClic - y, 2));
		return distance <= radius;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public static double getRadius(){
		return radius;
	}
}
