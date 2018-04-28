package plateau;

/**
 * Classe abstraite représentant une armée sans type défini.<br/>
 * Toute unité doit posséder ces mêmes caractéristiques
 */

public abstract class Armee {

	private String nom;
	private int cout;
	private int puissanceMin;
	private int puissanceMax;
	private int prioriteAtt;
	private int prioriteDef;
	private int mouvement;

	//////////////////
	// CONSTRUCTORS //
	//////////////////

	public Armee(String nom, int cout, int puissanceMin, int puissanceMax, int prioriteAtt, int prioriteDef, int mouvement) {
		this.nom = nom;
		this.cout = cout;
		this.puissanceMin = puissanceMin;
		this.puissanceMax = puissanceMax;
		this.prioriteAtt = prioriteAtt;
		this.prioriteDef = prioriteDef;
		this.mouvement = mouvement;
	}

	///////////////////////
	// GETTERS & SETTERS //
	///////////////////////


	public String getNom() {
		return nom;
	}

	public int getCout() {
		return cout;
	}

	public int getPuissanceMin() {
		return puissanceMin;
	}

	public int getPuissanceMax() {
		return puissanceMax;
	}

	public int getPrioriteAtt() {
		return prioriteAtt;
	}

	public int getPrioriteDef() {
		return prioriteDef;
	}

	public int getMouvement() {
		return mouvement;
	}
}
