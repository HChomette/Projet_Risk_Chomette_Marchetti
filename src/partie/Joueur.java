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
}
