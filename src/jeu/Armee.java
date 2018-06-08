package jeu;

import java.util.Comparator;

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
	private int score;

	//////////////////
	// CONSTRUCTORS //
	//////////////////

	protected Armee(String nom, int cout, int puissanceMin, int puissanceMax, int prioriteAtt, int prioriteDef, int mouvement) {
		this.nom = nom;
		this.cout = cout;
		this.puissanceMin = puissanceMin;
		this.puissanceMax = puissanceMax;
		this.prioriteAtt = prioriteAtt;
		this.prioriteDef = prioriteDef;
		this.mouvement = mouvement;
		this.score = 0;
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

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public void setPuissanceMin(int puissanceMin) {
		this.puissanceMin = puissanceMin;
	}

	public void setPuissanceMax(int puissanceMax) {
		this.puissanceMax = puissanceMax;
	}

	public void setPrioriteAtt(int prioriteAtt) {
		this.prioriteAtt = prioriteAtt;
	}

	public void setPrioriteDef(int prioriteDef) {
		this.prioriteDef = prioriteDef;
	}

	public void setMouvement(int mouvement) {
		this.mouvement = mouvement;
	}

	public int getMouvementMax(){
		return -1;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static Comparator<Armee> SortByPrioriteDefense = new Comparator<Armee>() {

		public int compare(Armee s1, Armee s2) {

			int rollno1 = s1.getPrioriteDef();
			int rollno2 = s2.getPrioriteDef();

			/*For ascending order*/
			return rollno1-rollno2;

			/*For descending order*/
			//return rollno2-rollno1;
		}};


	public static Comparator<Armee> SortForIa = new Comparator<Armee>() {

		public int compare(Armee s1, Armee s2) {

			int rollno1 = s1.getPuissanceMax();
			int rollno2 = s2.getPuissanceMax();

			int roll1 = s1.getMouvement();
			int roll2 = s2.getMouvement();

			/*For ascending order*/
			//return rollno1-rollno2;

			/*For descending order*/
			if(roll1 == roll2){
				return rollno1 - rollno2;
			}else {
				return roll2 - roll1;
			}
		}};

	public static Comparator<Armee> SortAttaque = new Comparator<Armee>() {

		public int compare(Armee s1, Armee s2) {

			int rollno1 = s1.getScore();
			int rollno2 = s2.getScore();

			/*For ascending order*/
			//return rollno1-rollno2;

			/*For descending order*/
			if(rollno1 == rollno2){
				return s1.getPrioriteAtt() - s2.getPrioriteAtt();
			}else {
				return rollno2 - rollno1;
			}

			}};



}
