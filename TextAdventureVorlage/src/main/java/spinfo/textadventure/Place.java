package spinfo.textadventure;


import java.util.ArrayList;

import java.util.List;


import spinfo.textadventure.data.Player;
import spinfo.textadventure.games.Game;



public class Place implements spinfo.textadventure.data.Place{
	/*
	 *  @param
	 */
	private String name; 
	private String action;
	private ArrayList<String> inConditions;
	private ArrayList<String> addConditions;
	@SuppressWarnings("unused")
	private ArrayList<String> remConditions;
	@SuppressWarnings("unused")
	private Game game;
	


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void setInConditions(List<String> inConditions) {
		ArrayList<String> toReturn = new ArrayList<String>();
		
		for (String string : inConditions) {
			toReturn.add(string);
		}
		this.inConditions = toReturn;

	}

	@Override
	public void setAddConditions(List<String> addConditions) {
//		this.addConditions.addAll(addConditions);
		ArrayList <String> temp = new ArrayList<String>();
		temp.addAll(addConditions);
		this.addConditions = temp;
		

	}

	@Override
	public void setRemoveConditions(List<String> removeConditions) {
		ArrayList<String> temp = new ArrayList<String>();
		
		for (String string : removeConditions) {
			temp.add(string);
		}
		this.remConditions = temp;
//		this.inConditions.removeAll(removeConditions);
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public boolean canEnter(Player player) {
		if(this.inConditions == null) // wenn es keine inConditions gibt, kann man den Raum betreten
			return true;
		else // ansonsten: prüfe, ob der Spieler die inConditions erfüllt
			return player.getConditions().containsAll(inConditions);
	}
	// Das Verhalten für jeden Raum habe ich hardcoded, da im Interface keine Möglichkeit vorgesehen ist, in einer Instanz zu überprüfen, ob bestimmte Felder vorhanden sind oder nicht - 
	// also: es sind keine Getter vorgesehen. Somit ist hardcoden die einzige Variante sicherzustellen, dass jeder Raum sich richtig verhält.
	@Override
	public void enter(Player player) {
		int x = player.getX(); //zur Bestimmung des Ortes
		int y = player.getY();
		System.out.println("Du bist hier: " + this.getName());
		
		if (x == 0 && y == 0){
			System.out.println(action);
			player.addConditions(addConditions);
				
		}
		if (x == 0 && y == 1){
			System.out.println(action);
			player.addConditions(addConditions);
		}
		if (x == 0 && y == 2) 
			System.out.println(action);
		
		if (x == 1 && y == 0) {
			
			Game game = new spinfo.textadventure.Game();
			System.out.println(game.getComment());
			
			boolean temp = game.play();
			if (temp){ // Wenn man das Spiel gewinnt = true, sonst false
				System.out.println(action);
				
				if (player.getConditions().contains("from_street")) {
					ArrayList <String> cond = (ArrayList<String>) player.getConditions(); //temporäre Liste erstellen mit den bisherigen Conditions
					cond.remove("from_street"); // from_street entfernen
					player.addConditions(cond); // temporäre Liste = neue Liste von conditions
					player.addConditions(addConditions); // erst jetzt die neue Condition hinzufügen
				} 
				
			}
		}
		if (x == 1 && y == 1 ) {
			System.out.println(action);
			
		}
		if (x == 1 && y == 2) {
			System.out.println(action);
			player.addConditions(addConditions);
		}
		if (x == 2 && y == 0)
			System.out.println("Hier ist nichts von Interesse. Bahngleise soweit das Auge reicht...");
		
		if (x == 2 && y == 1)
			System.out.println(action);
		
		if (x == 2 && y == 2)
			System.out.println("Hier ist nichts von Interesse. Ein Bahnhof halt. Meh.");
		
		if (x == 3 && y == 0){
			System.out.println(action);
			player.addConditions(addConditions);
		}
		if (x == 3 && y == 1)
			System.out.println("Hier bist du Zuhause. Du solltest bei Gelegenheit mal aufräumen...");
		
		if (x == 3 && y == 2)
			System.out.println("Hier ist nichts von Interesse. In der Asta sind scheinbar alle besoffen...");
		
		
		
	}

}
