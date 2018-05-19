package jeu;

import java.util.ArrayList;

/**
 * Carte regroupant toutes les régions utilisées dans une partie.
 */
public class Carte {
	private ArrayList<Region> regions;

	public Carte(){
		this.regions = new ArrayList<>();
	}

	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void addRegion(Region r){
		regions.add(r);
	}

	public String toString(){
		String res = "";
		for(Region r : regions) res += r.toString();
		return res;
	}
}
