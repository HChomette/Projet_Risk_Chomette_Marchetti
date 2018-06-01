package visuel;

import javax.swing.*;
import java.util.ArrayList;

public class PopupManager {

	public static String choixType(ArrayList<String> types, ArrayList<Integer> couts, int budget){
		for(int i = 0; i < couts.size(); i++){
			if(couts.get(i) > budget){
				couts.remove(i);
				types.remove(i);
				i--;
			}
		}

		String[] choices = new String[types.size()];
		for(int i = 0; i < types.size(); i++){
			choices[i] = types.get(i) + " : " + couts.get(i);
		}
		String res = (String)(JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Quelles unités voulez vous créer ?\nVotre budget est de " + budget, "Renforts", JOptionPane.PLAIN_MESSAGE, null, choices, null));

		if(res != null)
			res = res.substring(0, res.length() - 4);
		return res;
	}
}
