package partie;

import jeu.Carte;

import jeu.Region;
import jeu.Territoire;
import org.json.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Classe chargeant une map à partir d'un fichier JSON.
 */
public class MapLoader {

	/**
	 * Retourne une nouvelle carte créée à partir d'un fichier JSON.
	 * @param path Chemin du fichier JSON
	 * @return Nouvelle carte remplie
	 */
	public static Carte loadMap(String path){
		Carte mainMap = new Carte();

		try {
			String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			JSONArray global = new JSONArray(json);
			int numTerritoire = 0;
			for(int i = 0; i < global.length(); i++){
				JSONObject region = global.getJSONObject(i);
				Region newRegion = new Region(region.getString("name"));
				int nbTerritoires = region.getInt("territoires");
				for(int j = 0; j < nbTerritoires; j++){
					newRegion.addTerritoire(new Territoire(numTerritoire));
					numTerritoire++;
				}
				mainMap.addRegion(newRegion);
			}
		} catch (IOException|JSONException e) {
			e.printStackTrace();
		}

		return mainMap;
	}

	public static boolean[][] loadAdjacence(String path, int length){
		boolean[][] adjacence = new boolean[length][length];

		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			int lineCounter = 0;
			for(String line; (line = br.readLine()) != null; ) {
				for(int i = 0; i < line.length(); i++){
					if(line.charAt(i) == '1'){
						adjacence[lineCounter][i] = true;
					} else {
						adjacence[lineCounter][i] = false;
					}
				}
				lineCounter++;
			}
		}catch (IOException e){
			e.printStackTrace();
		}

		return adjacence;
	}
}
