package jeu;

import java.util.ArrayList;

/**
 * Carte regroupant toutes les régions utilisées dans une partie.
 */
public class Carte {
	private ArrayList<Region> regions;
	private boolean[][] adjacence;

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
	 * Renvoie le territoite de numéro donné
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

	public boolean areVoisins(int num1, int num2){
		return adjacence[num1][num2];
	}

	public void setAdjacence(boolean[][] adjacence){
		this.adjacence = adjacence;
	}

	public String toString(){
		String res = "";
		for(Region r : regions) res += r.toString();
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
}
