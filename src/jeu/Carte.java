package jeu;

import localisation.Point;

import java.util.ArrayList;

/**
 * Carte regroupant toutes les régions utilisées dans une partie.
 */
public class Carte {
	private ArrayList<Region> regions;
	private boolean[][] adjacence;
	private ArrayList<Point> localisations;

	public Carte(){
		this.regions = new ArrayList<>();
	}

	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void addRegion(Region r){
		regions.add(r);
	}

	/**
	 * Renvoie le territoire de numéro donné
	 * @param numero numéro du territoire
	 * @return Le territoire de numéro recherché
	 */
	public Territoire getTerritoire(int numero){
		int numTerritoire = 0;
		for(Region r : regions){
			if(numTerritoire + r.getTerritoires().size() > numero){
				return r.getTerritoires().get(numero - numTerritoire);
			}
			numTerritoire += r.getTerritoires().size();
		}

		return null;
	}

	public ArrayList<Territoire> getTerritoires(){
		ArrayList<Territoire> res = new ArrayList<>();
		for(Region r : regions){
			for(Territoire t : r.getTerritoires())
				res.add(t);
		}

		return res;
	}

	/**
	 * Méthode de test pour vérifier que la matrice est symétrique
	 */
	public void checkAdjacence(){
		for(int i = 0; i < 42; i++){
			for(int j = 0; j < 42; j++){
				if(adjacence[i][j] != adjacence[j][i])
					System.out.println("Erreur : " + (i + 1) + " - " + (j + 1));
			}
		}
	}

	/**
	 * Retourne le territoire dans lequel l'utilisateur a cliqué <br/>
	 * Si aucun territoire n'est visé, retourne null
	 * @param x abscisse du clic
	 * @param y ordonnée du clic
	 * @return Le territoire cible
	 */
	public Territoire getTarget(double x, double y){
		for(int i = 0; i < localisations.size(); i++){
			if(localisations.get(i).isIn(x, y)){
				return getTerritoire(i);
			}
		}
		return null;
	}

	public boolean areVoisins(int num1, int num2){
		return adjacence[num1][num2];
	}

	public void setAdjacence(boolean[][] adjacence){
		this.adjacence = adjacence;
	}

	public void setLocalisations(ArrayList<Point> localisations){
		this.localisations = localisations;
	}

	public ArrayList<Point> getLocalisations() {
		return localisations;
	}

	public Point getLocalisation(int i){
		return localisations.get(i);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Region r : regions) sb.append(r.toString());
		return sb.toString();
	}
}
