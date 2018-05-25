package unites;

import jeu.Armee;

/**
 * Armée de type soldat. Doit être créée par UnitFactory
 * @see UnitFactory
 */

class Soldat extends Armee {

	Soldat(){
		super("Soldat", 1, 1, 6, 2, 1, 2);
	}

	@Override
	public int getMouvementMax(){
		return 2;
	}
}
