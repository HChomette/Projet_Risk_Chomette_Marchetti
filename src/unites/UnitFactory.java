package unites;

import plateau.Armee;

/**
 * Factory permettant la création des unités de ce package sans dépendance chez le créateur <br/>
 * (Camille je sais pas si t'as déjà vu une factory, si tu vois pas à quoi ça sert je t'expliquerai c'est simple)
 */
public class UnitFactory {

	/**
	 * Obtient une armée du type souhaitée
	 * @param nomType le nom du type d'armée
	 * @return la nouvelle armée du type demandé
	 */
	public static Armee getArmee(String nomType){

		if(nomType.equals("Canon"))
			return new Canon();

		else if(nomType.equals("Cavalier"))
			return new Cavalier();

		else if(nomType.equals("Soldat"))
			return new Soldat();

		return null; //Si aucun type ne correspond
	}
}
