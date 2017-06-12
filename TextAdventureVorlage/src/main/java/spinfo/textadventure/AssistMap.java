package spinfo.textadventure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.Map;

/**
 * Hilfsklasse um das Verwalten der  Place Klasse zu erleichtern. 
 * Erzeugt eine ArrayListe, welche die verschiedenen Konfigurationsblöcke als Map gespeichert hat.
 * Ermöglicht den Zugriff auf die einzelnen Konfigurationsblöcke Mittels Nummer (0 = oberster Block).
 * Zugriff auf Felder mittels Namen des Feldes (bspw.: "name=").
 *
 * @author FME
 * @version 0.01
 */
public class AssistMap {
	
	/**
	 * Alle Orte als ArrayList, jeder Eintrag enthält eine Map die dem Key vor dem "=" einen Value hinter "=" zuweist
	 */
	private ArrayList<Map<String, String>> places;
	
	/**
	 * Alle Orte erhalten einen Value, der von dem Key hinter "name=" aus der .config Datei abhängt.
	 * Somit kann man man später die Funktion {@link #showPlaceFromValue(String, int) showPlaceFromValue()} leichter benutzen, indem man 
	 * ihr einen Wert übergibt, der vom Namen des Ortes abhängt und nicht von dem int-Wert.
	 * @see {@link #getPlaceToInteger()}
	 * @see {@link #setPlaceToInteger(HashMap)}
	 */
	private HashMap<String, Integer> placeToInteger;
	
	/**
	 * Erschafft eine Liste mit Maps, welche die Werte als Key/Value Paar enthalten: key: "name=", value: "Das Ding",
	 * sowie eine Hashmap, die den Namen einen int-Wert zuweist, zur späteren Verwendung.
	 */
	public AssistMap () {
	HashMap<String, Integer> placeToInteger = new HashMap<>();
	
	// die Map repräsentiert die Abfolge der Orte in der Datei "places.config" und muss leider hardcoded sein
	placeToInteger.put("Das Ding", 0);
	placeToInteger.put("Zülpicher Straße (Nord)", 1);
	placeToInteger.put("Stiefel", 2);
	placeToInteger.put("Bei Oma Kleinmann", 3);
	placeToInteger.put("Zülpicher Straße (Mitte)", 4);
	placeToInteger.put("Zülpicher Straße (Süd)", 5);
	placeToInteger.put("Zu Hause", 6);
	placeToInteger.put("Barbarossa-Platz", 7);
	placeToInteger.put("Uni", 8);
	this.setPlaceToInteger(placeToInteger);
	
	File file = new File("games/adventure/places.config");
	
	
	try {
		BufferedReader reader = new BufferedReader(new FileReader(file));
	
	ArrayList<Map<String, String>> blocks = new ArrayList<>();

	String line = new String();
	Map<String, String> block = new HashMap<>();

	
		while ((line = reader.readLine()) != null) {
		    line = line.trim();

		    if (!line.isEmpty() && line.indexOf('=') > 0) {
		        if (line.startsWith("name") && !block.isEmpty()) { //sofern neuer Block beginnt ("name="), erschaffe neuen Block
		            blocks.add(block);
		            block = new HashMap<>();
		        }

		        String[] parts = line.split("="); //in aktuellen Block, füge Key/Value ein
		        if (parts.length == 2) {
		            block.put(parts[0], parts[1]);
		        }
		    }
		}
		if (! block.isEmpty()) {
		    blocks.add(block);
		}
		reader.close();
		this.places = blocks;
//		for (Map<String, String> block2 : blocks) {
//		    System.out.println(block2);
//		}
			} catch (IOException e) {
				
				e.printStackTrace();
		}
	
	}
	/** Über Name des Feldes und Nummer des Blocks den Wert des Feldes erhalten. 
	 * Über {@link #setPlaceToInteger(HashMap) setPlaceToInteger()} kann man anhand des Namens des ersten Feldes die Nummer der Gruppe erhalten.
	 * @param condition
	 * @param group
	 * @return String mit Wert des Feldes in der spezifizierten Gruppe.
	 */
	public String showPlaceFromValue (String condition, int group) {
		String toReturn = new String();
		
		Map<String, String> pGroup = places.get(group); //aus der ArrayList places die Map ziehen
		toReturn = pGroup.get(condition); // aus der Map anhand der Bedingung den Wert des Feldes ziehen
		
		return toReturn;
	}
	
	/**
	 * Über Name der Bedingung und Nummer des Blocks den Wert des Feldes erhalten.
	 * Über {@link #setPlaceToInteger(HashMap) setPlaceToInteger()} kann man anhand des Namens des ersten Feldes die Nummer der Gruppe erhalten.
	 * @param condition
	 * @param group
	 * @return LinkedList mit den Werten des Feldes.
	 */
	
	public LinkedList<String> showConditionFromValue (String condition, int group){
		LinkedList<String> toReturn = new LinkedList<String>();
		String conditionList = new String();
		
		Map<String, String> pGroup = places.get(group); //aus der ArrayList places die Map ziehen

		conditionList = pGroup.get(condition).trim(); // trim() um sicher zu gehen, dass kein Whitespace den Wert "verunreinigt"
		
		if (conditionList.contains(",")) { // sofern "," vorhanden sind es mehrere Items, welche erst aufgeteilt und dann der Liste hinzugefügt werden müssen:
			String[] t = conditionList.split(","); 
			for (int i = 0; i < t.length; i++) {
				toReturn.add(t[i]);
			}
			return toReturn;
		}
		else { // andernfalls ist es nur eine Condition, welche einfach angehängt werden kann:
			toReturn.add(conditionList);
			return toReturn;
		}
			
		
		
		
		
	}

	public HashMap<String, Integer> getPlaceToInteger() {
		return placeToInteger;
	}
	
	/**
	 * Falls man doch einmal die HashMap ändern möchte
	 * @param placeToInteger
	 */

	public void setPlaceToInteger(HashMap<String, Integer> input) {
		this.placeToInteger = input;
	}

}
