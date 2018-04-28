package unites;

import plateau.Armee;

/**
 * Armée de type soldat. Doit être créée par UnitFactory
 * @see UnitFactory
 */

class Soldat extends Armee {

	Soldat(){
		super("Soldat", 1, 1, 6, 2, 1, 2);
	}
}
