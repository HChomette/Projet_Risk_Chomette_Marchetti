package visuel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		panel.add(new JLabel("Vous avez " + budget + " armées disponibles. ")); //TODO : Manquerait un saut de ligne

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

	/**
	 * Affiche un simple message d'information à l'utilisateur
	 * @param message Le message à afficher
	 */
	public static void alert(String message){
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Affiche une liste dans une fenêtre
	 * @param list La liste des textes à afficher
	 * @param title Le titre de la fenêtre
	 */
	public static void list(ArrayList<String> list, String title){
		JFrame frame = new JFrame(title);

		DefaultListModel toDisplay = new DefaultListModel();
		for(String s : list) toDisplay.addElement(s);

		JList jlist = new JList(toDisplay);
		jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jlist.setVisibleRowCount(5);
		frame.add(jlist);
		frame.pack();
		frame.setSize(frame.getWidth() + 200, Math.max(frame.getHeight(), 350));
		frame.setVisible(true);
	}

	/**
	 * Affiche une liste dans une fenêtre et renvoie
	 * @param list
	 * @param title
	 * @return
	 */
	public static ArrayList<Integer> selectList(ArrayList<String> list, String title){
		JFrame frame = new JFrame(title);

		DefaultListModel toDisplay = new DefaultListModel();
		for(String s : list) toDisplay.addElement(s);

		JList jlist = new JList(toDisplay);
		jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jlist.setVisibleRowCount(5);
		frame.add(jlist);
		frame.pack();
		frame.setSize(frame.getWidth() + 200, Math.max(frame.getHeight(), 350));
		JButton bouton = new JButton("Valider");
		bouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		frame.setVisible(true);
	}
}
