package visuel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.util.ArrayList;

public class PopupManager {

	/**
	 * Ouvre une popup pour demander à l'utilisateur ses choix lors de la création initiale de ses armées
	 * @param types les types d'armée
	 * @param couts Les couts des différents types d'armée
	 * @param budget Le budget du joueur
	 * @return La liste des créations du joueur
	 */
	public static ArrayList<Integer> choixTypes(ArrayList<String> types, ArrayList<Integer> couts, int budget){
		ArrayList<Integer> res = null;
		ArrayList<JFormattedTextField> fields = new ArrayList<>();
		ArrayList<JLabel> labels = new ArrayList<>();

		JPanel panel = new JPanel();

		//Formatter limitant les entrées à des nombres
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("***");
			formatter.setValidCharacters("0123456789 ");
		} catch (java.text.ParseException exc) {
			System.err.println(exc.getMessage());
			System.exit(-1);
		}

		//Génération des fields et labels correspondants, et ajouts sur la fenêtre.
		for(int i = 0; i < types.size(); i++){
			labels.add(new JLabel(types.get(i) + " : " + couts.get(i)));
			JFormattedTextField nextField = new JFormattedTextField(formatter);
			nextField.setColumns(10);

			fields.add(nextField);

			panel.add(labels.get(i));
			panel.add(fields.get(i));
			panel.add(Box.createVerticalStrut(15));

		}

		int result = -1;
		boolean valid = false;

		//On boucle tant que le résultat n'est pas valide
		while(result != JOptionPane.OK_OPTION || !valid) {
			result = JOptionPane.showConfirmDialog(null, panel,
					"", JOptionPane.OK_CANCEL_OPTION);

			valid = true;
			res = new ArrayList<>();

			for (JFormattedTextField field : fields){
				if(field.getText() != null){
					try {
						String cutText = field.getText().replaceAll("\\s", "");
						res.add(Integer.parseInt(cutText));
					}catch (NumberFormatException e){
						valid = false;
						JOptionPane.showMessageDialog(null, "Champ numéro " + (fields.indexOf(field) + 1) + " invalide.");
					}
				} else {
					valid = false;
				}
			}

			int total = 0;
			for(int i = 0; i < res.size(); i++){
				total += res.get(i) * couts.get(i);
			}
			if(total > budget) {
				valid = false;
				JOptionPane.showMessageDialog(null, "Vous n'avez pas assez de budget !");
			} else if (total < budget){
				valid = false;
				JOptionPane.showMessageDialog(null, "Il vous reste " + (budget - total) + " renforts.");
			}
		}

		return res;
	}
}
