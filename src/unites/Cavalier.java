package unites;

import plateau.Armee;

/**
 * Armée de type cavalier. Doit être créée par UnitFactory.
 * @see UnitFactory
 */

class Cavalier extends Armee {

	Cavalier(){
		super("Cavalier", 3, 2, 7, 1, 3, 3);
	}
}
