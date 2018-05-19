package partie;

import jeu.Carte;

import jeu.Region;
import jeu.Territoire;
import org.json.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
			for(int i = 0; i < global.length(); i++){
				JSONObject region = global.getJSONObject(i);
				Region newRegion = new Region(region.getString("name"));
				int nbTerritoires = region.getInt("territoires");
				for(int j = 0; j < nbTerritoires; j++){
					newRegion.addTerritoire(new Territoire());
				}
				mainMap.addRegion(newRegion);
			}
		} catch (IOException|JSONException e) {
			e.printStackTrace();
		}

		return mainMap;
	}
}
