package spinfo.textadventure;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spinfo.textadventure.data.Place;
import spinfo.textadventure.data.Player;
import spinfo.textadventure.data.World;

public class IO implements spinfo.textadventure.io.IO  {


	@SuppressWarnings("unused")
	private File gamedirectory;
	public IO () {
		try {
			loadPlaces();
		} catch (InvalidGameException e) {

			e.printStackTrace();
		}
	}
	
	@Override
	public List<Place> loadPlaces() throws InvalidGameException {
		
		// benötigte Strukturen initialisieren und Werte auslesen
		List<Place> toReturn = new ArrayList<Place>();
		
		Place[][] placeList = new spinfo.textadventure.World().getPlaces();
		
		// Werte des Arrays in die Liste schreiben
		
		for (int i = 0; i < placeList.length; i++) {
			for (int j = 0; j < placeList[i].length; j++) {
				toReturn.add(placeList[i][j]);
			
			}
		}

		return toReturn;
	}

	@Override
	public World loadWorld(List<Place> loadedPlaces) throws InvalidGameException {
		World world = new spinfo.textadventure.World();
		world.setPlaces(new Place[4][3]); // dies ist hardcoded, man könnte dies aber auch dynamisch gestalten, falls man die Spielwelt selbst dynamisch initialisiert
		return world;
		
	}

	@Override
	public void saveGame(Player player) throws IOException {
		
		// Werte aus player auslesen, ablegen zur Verwendung
		
		int x = player.getX();
		int y = player.getY();
		ArrayList<String> conditions = (ArrayList<String>) player.getConditions();
		ArrayList<String> entered = (ArrayList<String>) player.getEnteredRooms();
		
		String conditionsString	= new String();
		String enteredString = new String();
		
		for (String string : conditions) {
			conditionsString += string + ","; // "," wird benötigt, damit es später wieder ausgelesen und zugewiesen werden kann 
		}
		for (String string : entered) {
			enteredString += string + ",";
		}
		// File auf die geschrieben werden soll initiatlisieren, FileWriter und BufferedWriter initialisieren
		
		File file = new File("games/adventure/save_game.config");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		// in die Datein schreiben; man könnte dies auch dynamischer machen, bspw. durch matcher.replace("x=", "x=" + x); 
		// oder andere Operationen mit Regex, so ist es aber kurz und übersichtlich.
		
		bw.write("x=" + x + "\ny=" + y + "\nconditions=" + conditionsString + "\nentered=" + enteredString);
		bw.close();
		

	}

	@Override
	public Player loadGame(boolean loadSaveGame) throws IOException {
	
		//alle benötigten Datentypen initialisieren
		
		int x, y;
		ArrayList<String> conditions = new ArrayList<String>(); 
		ArrayList<String> entered = new ArrayList<String>();
		
		if (loadSaveGame){ //sofern das Savegame geladen werden soll:
			
			// save_game.config auslesen und auf String filecontent packen
			
			File file = new File("games/adventure/save_game.config");
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String line = new String();
			String filecontent = new String();
			while ((line = br.readLine()) != null){
				filecontent += line + "\n";
			}
			br.close();
			
			// Regex initialisieren, das den Inhalt an der Position genau hinter dem jeweiligen "=" ausliest bis zum Ende der Zeile
			
			Pattern pattern = Pattern.compile("(.+?\\=(.+?)\\n)");
			Matcher matcher = pattern.matcher(filecontent);
			
		
			// das passende Objekt ist immer in Gruppe 2, daher könnte man es theoretisch auch mit einer while-schleife durchführen, ist aber hier unnötig
			
			matcher.find();
			x = Integer.parseInt(matcher.group(2));
			matcher.find();
			y = Integer.parseInt(matcher.group(2));
			if (matcher.find()) {
				String[] temp = matcher.group(2).split(","); // Einträge splitten am ",", damit sie einzelne Objekten zugewiesen werden können
				for (int i = 0; i < temp.length; i++) {
					conditions.add(temp[i]);		
				}
			} else 
				conditions = null;
			
			if (matcher.find()) {
				String[] temp2 = matcher.group(2).split(",");
				for (int i = 0; i < temp2.length; i++) {
					entered.add(temp2[i]);
				
				}
			} else
				entered = null;
			
			// einen player initialisieren mit Hilfe des Konstruktors, mit den ausgelesenen Werten
			
			spinfo.textadventure.Player player = new spinfo.textadventure.Player(x, y, conditions, entered);
			return player;
		}
		
		// falls loadSaveGame nicht ausgewählt wurde:
		
		if (!loadSaveGame) {
			
			// hier habe ich hardcoded, da das Prinzip das gleiche wäre wie oben angegeben, der Inhalt aber niemals dynamisch sein wird weshalb es schlicht überflüssig wäre
			// diese Schritte nochmals zu durchlaufen. Falls man die initial_game.config Datei abändern möchte, könnte man prinzipiell den Code von oben eins zu eins hierher kopieren
			// ODER eine Methode schreiben, welche lediglich die spezifische config-file übergeben bekommt, um in dieser Methode nur die if-Abfrage zu verwalten, welche dann die config-file bestimmt
			
			spinfo.textadventure.Player player = new spinfo.textadventure.Player(3, 1, conditions, entered);
			return player;
		}
		else
			return null;
		
	}

	@Override
	public void setGameDirectory(File gameDirectory) {
		this.gamedirectory = gameDirectory;

	}

}
