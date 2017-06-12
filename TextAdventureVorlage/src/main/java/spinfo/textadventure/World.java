package spinfo.textadventure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import spinfo.textadventure.data.Place;

public class World implements spinfo.textadventure.data.World {
	
	private Place[][] places;
	
	public World(){
		Place[][] places = new Place[4][3];
		setPlaces(places);
	
	}
	
	@Override
	public void setPlaces(Place[][] places) {
		AssistMap assMap = new AssistMap();
		HashMap<String, Integer> intFromName = assMap.getPlaceToInteger(); //Hilfs-HashMap in der zu jedem Namen ein Gruppenwert zugewiesen ist
		
		for (int i = 0; i < places.length; i++) {
			for (int j = 0; j < places[i].length; j++) {
				Place place = new spinfo.textadventure.Place ();
// es folgen viele if-conditions, die umfangreich sein müssen, da jeder Ort an einen ganz bestimmten Platz gesetzt werden soll und die Orte 
// immer sehr unterschiedliche Felder haben. Eine Möglichkeit, dies dynamisch(er) zu erzeugen, sehe ich derzeit nicht. 
				if (i == 0 && j == 0 ) { //
					int intDing = intFromName.get("Das Ding"); // Wert der Gruppe, die durch "Das Ding" repräsentiert wird
					place.setName(assMap.showPlaceFromValue("name", intDing));
					place.setAction(assMap.showPlaceFromValue("action", intDing));
					place.setInConditions(assMap.showConditionFromValue("in_conditions", intDing));
					place.setAddConditions(assMap.showConditionFromValue("add_conditions", intDing));
				}
				if (i == 0 && j == 1) {
					int intNord = intFromName.get("Zülpicher Straße (Nord)");
					place.setName(assMap.showPlaceFromValue("name", intNord));
					place.setAddConditions(assMap.showConditionFromValue("add_conditions", intNord));
					place.setAction(assMap.showPlaceFromValue("action", intNord));
				}
				if (i == 1 && j == 0) {
					int intStiefel = intFromName.get("Stiefel");
					place.setName(assMap.showPlaceFromValue("name", intStiefel));
					place.setGame(new Game());
					place.setRemoveConditions(assMap.showConditionFromValue("remove_conditions", intStiefel));
					place.setAddConditions(assMap.showConditionFromValue("add_conditions", intStiefel));
					place.setAction(assMap.showPlaceFromValue("action", intStiefel));
					
				}
				if (i == 1 && j == 2){
					int intOma = intFromName.get("Bei Oma Kleinmann");
					place.setAction(assMap.showPlaceFromValue("action", intOma));
					place.setAddConditions(assMap.showConditionFromValue("add_conditions", intOma));
					place.setName(assMap.showPlaceFromValue("name", intOma));
					
				}
				if (i == 0 && j == 2) {
					int intBarb = intFromName.get("Barbarossa-Platz");
					place.setName(assMap.showPlaceFromValue("name", intBarb));
					place.setAction(assMap.showPlaceFromValue("action", intBarb));
					
				}
				if (i == 1 && j == 1) {
					int intMitte = intFromName.get("Zülpicher Straße (Mitte)");
					place.setName(assMap.showPlaceFromValue("name", intMitte));
					place.setAction(assMap.showPlaceFromValue("action", intMitte));
					
				}
				if (i == 2 && j == 0) {
					place.setName("----");
				}
				if (i == 2 && j == 1 ) {
					int intSued = intFromName.get("Zülpicher Straße (Süd)");
					place.setName(assMap.showPlaceFromValue("name", intSued));
					place.setInConditions(assMap.showConditionFromValue("in_conditions", intSued));
					place.setAction(assMap.showPlaceFromValue("action", intSued));
					
				}
				if (i == 2 && j == 2) {
					place.setName("----");
				}
				if (i == 3 && j == 0) {
					int intUni = intFromName.get("Uni");
					place.setName(assMap.showPlaceFromValue("name", intUni));
					place.setAction(assMap.showPlaceFromValue("action", intUni));
					place.setAddConditions(assMap.showConditionFromValue("add_conditions", intUni));
					
				}
				if (i == 3 && j == 1 ) {
					int intHeim = intFromName.get("Zu Hause");
					place.setName(assMap.showPlaceFromValue("name", intHeim));
					
				}
				if (i == 3 && j == 2) {
					place.setName("----");
				}
				places[i][j] = place;
				
			}
		}
		this.places = places;
	}
	
	@Override
	public void printMap(int playerX, int playerY) {
			File file = new File ("games/adventure/world.config");

		try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line;
				String filecontent =""; //soll kurzzeitig den gesamten Inhalt der Datei zwischenspeichern
				
				while ((line = br.readLine()) != null) {
					filecontent += line + "\n";
				}
				br.close();
				
				// die einzelnen Namen aus der File an den Stellen "|" und "\n" splitten
				
				String[]fileArray= filecontent.split("\\||\\n");

				// ausreichend großes Array erzeugen und mit den Werten des eben erstellten Array befüllen 
				// ist leider hardcoded, da ich nicht direkt erkennen kann, wie man die Größe dynamisch gestalten kann
				
				String[][] twoDimArr = new String[4][3];
				int count = 0; // Läufervariable für das eindimensionale Array fileArray[]
				for (int i = 0; i < 4; i++) {
					
					for (int j = 0; j < 3; j++) {
						twoDimArr[i][j] = fileArray[count];
						count++;
					}
				}
				// Position des Spielers einfügen
				
				String addPlayer = twoDimArr[playerX][playerY];
				addPlayer = addPlayer + "*";
				twoDimArr[playerX][playerY] = addPlayer;
				
				// Karte ausgeben
				
				for (int i = 0; i < twoDimArr.length; i++) {
					for (int j = 0; j < twoDimArr[j].length; j++) {
						String placeName = twoDimArr[i][j];
						
						if (j != 0 ) 	{	// Sofern es sich nicht um das erste Element der Spalte handelt:
							if (placeName.length() > 31)
								System.out.print("\t" + placeName + "|");
							if (placeName.length() >= 15 && placeName.length() < 32)
								System.out.print("\t" + placeName + "\t|");

							if (placeName.length() >= 5 && placeName.length() < 15)
								System.out.print("\t" + placeName + "\t\t\t|");
							
							if (placeName.length() < 5)
								System.out.print("\t" + placeName + "|");
						
						}
						else {	// Sofern es sich um ein Element der ersten Spalte handelt:
							if (placeName.length() >= 10)
								System.out.print(placeName + "\t|");
							
							if (placeName.length() < 10)
								System.out.print(placeName + "\t\t|");
						
						}
					}
					System.out.println();
				}
				
				// Hilfscode um die Formatierungsspezifikationen leichter gestalten zu können falls benötigt; gibt Länge der Strings aus
//				for (int i = 0; i < twoDimArr.length; i++) {
//					for (int j = 0; j < twoDimArr[j].length; j++) {
//						String ort = twoDimArr[i][j];
//						System.out.print(ort.length()+ " ");
//					}
//					System.out.println();
//				}

				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	/**
	 * Man braucht nun doch wirklich einen Getter für diesen Parameter... ;)
	 * @return Place[][]
	 */
	public Place[][] getPlaces(){
		return places;
	}
	
	
	@Override
	public Place getPlace(int x, int y) {
		if (x < 4 && y < 3) // Sicherstellen, dass der Raum in der Welt vorhanden sein kann bezogen auf die Größe des Arrays[][]
			return places[x][y];
		else
			return null;
		
	}

}
