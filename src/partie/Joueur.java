package partie;

/**
 * Joueur d'une partie de Risk
 */
public class Joueur {

	private String nom;
	private int nbConquetes; //Nombres de conquêtes effectuées dans le tour courant

	public Joueur(String nom) {
		this.nom = nom;
		this.nbConquetes = 0;
	}

	/////////////
	// METHODS //
	/////////////

	/**
	 * Enregistre une nouvelle conquête ce tour
	 */
	public void addConquete(){
		this.nbConquetes++;
	}

	/**
	 * Remet à zéro le nombre de conquêtes du tour
	 */
	public void resetConquetes(){
		this.nbConquetes = 0;
	}

	///////////////////////
	// GETTERS & SETTERS //
	///////////////////////


	public String getNom() {
		return nom;
	}

	public int getNbConquetes() {
		return nbConquetes;
	}
}
