package unites;

import jeu.Armee;

import java.util.ArrayList;

/**
 * Factory permettant la création des unités de ce package sans dépendance chez le créateur <br/>
 */
public class UnitFactory {

	/**
	 * Obtient une armée du type souhaitée
	 * @param nomType le nom du type d'armée
	 * @return la nouvelle armée du type demandé
	 */
	public static Armee getArmee(String nomType){
		switch (nomType){
			case "Canon" :
				return new Canon();
			case "Cavalier" :
				return new Cavalier();
			case "Soldat" :
				return new Soldat();
		}

		return null; //Si aucun type ne correspond
	}

	/**
	 * Renvoie une liste contenant tous les types pouvant être instanciés par la factory. <br/>
	 * La classe cliente peut donc afficher à l'utilisateur la liste des types qu'il peut créer, sans dépendance.
	 * @return Liste complète des types
	 */
	public static ArrayList<String> getTypes(){
		ArrayList<String> res = new ArrayList<>();
		res.add("Canon");
		res.add("Cavalier");
		res.add("Soldat");
		return res;
	}
}
