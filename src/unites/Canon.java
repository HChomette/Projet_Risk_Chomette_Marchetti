package unites;

import jeu.Armee;

/**
 * Armée de type canon. Doit être créée par UnitFactory
 * @see UnitFactory
 */

class Canon extends Armee {

	Canon(){
		super("Canon", 7, 4, 9, 3, 2, 1);
	}
}
