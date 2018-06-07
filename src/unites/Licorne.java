package unites;

import jeu.Armee;

public class Licorne extends Armee {

	Licorne(){
		super("Licorne", 12, 3, 16, 1, 4, 5);
	}

	@Override
	public int getMouvementMax(){
		return 5;
	}

}
