package partie;

import jeu.Carte;
import jeu.Joueur;
import jeu.Territoire;

public class IA {
    private static IReglesAction regles;
    private static Carte carte;

    public static void setRegles(IReglesAction regles) {
        IA.regles = regles;
    }

    public static void setCarte(Carte carte) {
        IA.carte = carte;
    }

    public static void jouer(Joueur joueur){
        for (Territoire t:this.carte.getTerritoires()) {
            

        }

    }
}
