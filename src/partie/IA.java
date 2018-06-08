package partie;

import jeu.Armee;
import jeu.Carte;
import jeu.Joueur;
import jeu.Territoire;
import unites.UnitFactory;

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
        int countCan = 0;
        int countCav = 0;
        int countLic = 0;
        ArrayList<Armee> armee = new ArrayList<>();
        for(int i=0; i<n; i=i+7){
            armee.add(UnitFactory.getArmee("Canon"));
            countCan ++;
        }
        for(int i=0; i<n; i=i+3){
            armee.add(UnitFactory.getArmee("Cavalier"));
            countCav ++;

        }
        for(int i=0; i<n; i=i+15){
            armee.add(UnitFactory.getArmee("Licorne"));
            countLic++;
        }

        n = countCan * 7 + countCav * 3 + countLic *15 ;
        for(int i=n; i<renforts; i++){
            armee.add(UnitFactory.getArmee("Soldat"));
        }
        ArrayList<Territoire> territoires = carte.territoiresJoueur(joueur);
        Collections.sort(territoires, Territoire.SortByArmySize);
        Collections.sort(armee,Armee.SortByPower);
        for(int i=0; i<armee.size(); i++){
            territoires.get(i%territoires.size()).addArmee(armee.get(i));
        }
    }

    public static void jouer(Joueur joueur){
        for (Territoire t:carte.territoiresJoueur(joueur)) {
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
            voisins.clear();
        }

    }
}
