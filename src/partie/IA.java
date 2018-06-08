package partie;

import jeu.Armee;
import jeu.Carte;
import jeu.Joueur;
import jeu.Territoire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class IA {
    private static IReglesAction regles;
    private static Carte carte;

    public static void setRegles(IReglesAction regles) {
        IA.regles = regles;
    }

    public static void setCarte(Carte carte) {
        IA.carte = carte;
    }

    public static void renforts(Joueur joueur, int renforts){
        int n = renforts/4;
        //creer le max d'unite de chaque categorie possible
        //le reste = soldats

        //ajouter renforts aux territoires les plus faibles

    }

    public static void jouer(Joueur joueur){
        for (Territoire t:carte.getTerritoires()) {
            ArrayList<Territoire> voisins = new ArrayList<>();
            voisins.addAll(carte.getVoisins(t));
            Collections.sort(voisins, Territoire.SortByArmySize);
            Territoire cible = voisins.get(0);
            Collections.sort(t.getArmees(), Armee.SortForIa);
            while(t.getArmees().size()>2 && t.getArmees().get(0).getMouvement()>0){
                if(t.getArmees().size()>=5 && t.getArmees().get(2).getMouvement()>0){
                    regles.attaquer(new ArrayList<Armee>(t.getArmees().subList(0,3)),t,cible);
                }else if(t.getArmees().size()>2 && t.getArmees().get(1).getMouvement()>0){
                    regles.attaquer(new ArrayList<Armee>(t.getArmees().subList(0,1)),t,cible);
                }else if(t.getArmees().size()==2 && t.getArmees().get(0).getMouvement()>0){
                    if(regles.de(0,1)==1){
                        regles.attaquer(new ArrayList<Armee>(Arrays.asList(t.getArmees().get(0))),t,cible);
                    }
                }

            }
        }

    }
}
