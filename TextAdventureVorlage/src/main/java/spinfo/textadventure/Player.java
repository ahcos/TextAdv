package spinfo.textadventure;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import spinfo.textadventure.data.Place;
import spinfo.textadventure.data.World;

public class Player implements spinfo.textadventure.data.Player {
	
	private int x;  //Horizontale Position 
	private int y; // Vertikale Position
	
	private ArrayList<String> entered; //Liste mit den bereits betretenen Räume
	private ArrayList<String> conditions; // Liste mit den Bedingungen, die der Spieler bereits "gesammelt" hat
	

	public Player(int x, int y, ArrayList<String> conditions, ArrayList<String> entered) {
		this.x = x;
		this.y = y;
		this.conditions = conditions;
		this.entered = entered;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public List<String> getConditions() {
		return conditions;
	}

	@Override
	public void addConditions(List<String> conditions) {
		this.conditions.addAll(conditions);
	}
	// die move() Funktion muss relativ umfangreich ausfallen, mit vielen verschachtelten if-cases, um NullPointerExceptions beim Umgang mit dem Array world.getplaces[x][y] zu vermeiden
	@Override
	public void move(String direction, World world) {
		Player player = this;
		direction = direction.toLowerCase(); // zur Sicherheit der Abfrage, toLowerCase()

		
		if (direction.contains("east")) { //sofern irgendwo in der Eingabe "east" auftaucht
			if (this.y == 2){ // falls man sich am "linken Rand" der Karte befindet
				System.out.println("Hierher kannst du dich nicht bewegen, da dort nichts ist! Bitte eine andere Richtung wählen");
				Scanner s = new Scanner(System.in);
				String newDirection = s.nextLine();
				s.close();
				move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
			}
			if (this.y < 2) { //sofern man sich tatsächlich in diese Richtung bewegen kann
				
				this.y = y+1; // Playerposition erhöhen
				Place placeToEnter = world.getPlace(x, y); //Raum den man betreten möchte erhalten
				if (placeToEnter.canEnter(player)){ //sofern man die Bedingungen zum Betreten des Raumes erfüllt
					placeToEnter.enter(player); // Raum betreten
				}
				else {
					this.y--;
					System.out.println("Du kannst den Raum nicht betreten. Versuch dein Glück woanders ...");
					Scanner s = new Scanner(System.in);
					String newDirection = s.nextLine();
					s.close();
					move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
				}
			}
			
		}
		if(direction.contains("west")){
			if (this.y == 0){ // falls man sich am "rechten Rand" der Karte befindet
				System.out.println("Hierher kannst du dich nicht bewegen, da dort nichts ist! Bitte eine andere Richtung wählen");
				Scanner s = new Scanner(System.in);
				String newDirection = s.nextLine();
				s.close();
				move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
			}
			if (this.y > 0) { //sofern man sich tatsächlich in diese Richtung bewegen kann
				
				this.y = y-1; // Playerposition verringern
				Place placeToEnter = world.getPlace(x, y); //Raum den man betreten möchte erhalten
				if (placeToEnter.canEnter(player)){ //sofern man die Bedingungen zum Betreten des Raumes erfüllt
					placeToEnter.enter(player); // Raum betreten
				}
				else {
					this.y++;
					System.out.println("Du kannst den Raum nicht betreten. Versuch dein Glück woanders ...");
					Scanner s = new Scanner(System.in);
					String newDirection = s.nextLine();
					s.close();
					move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
				}
			}
		}
		if (direction.contains("north")) {
			if (this.x == 0){ // falls man sich am "oberen Rand" der Karte befindet
				System.out.println("Hierher kannst du dich nicht bewegen, da dort nichts ist! Bitte eine andere Richtung wählen");
				Scanner s = new Scanner(System.in);
				String newDirection = s.nextLine();
				s.close();
				move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
			}
			if (this.x > 0) { //sofern man sich tatsächlich in diese Richtung bewegen kann
				
				this.x = x-1; // Playerposition verringern
				Place placeToEnter = world.getPlace(x, y); //Raum den man betreten möchte erhalten
				if (placeToEnter.canEnter(player)){ //sofern man die Bedingungen zum Betreten des Raumes erfüllt
					placeToEnter.enter(player); // Raum betreten
				}
				else {
					this.x++;
					System.out.println("Du kannst den Raum nicht betreten. Versuch dein Glück woanders ...");
					Scanner s = new Scanner(System.in);
					String newDirection = s.nextLine();
					s.close();
					move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
				}
			}
			
		}
		if (direction.contains("south")) {
			if (this.x == 3){ // falls man sich am "unteren Rand" der Karte befindet
				System.out.println("Hierher kannst du dich nicht bewegen, da dort nichts ist! Bitte eine andere Richtung wählen");
				Scanner s = new Scanner(System.in);
				String newDirection = s.nextLine();
				s.close();
				move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
			}
			if (this.x < 3) { //sofern man sich tatsächlich in diese Richtung bewegen kann
				
				this.x = x+1; // Playerposition erhöhen
				Place placeToEnter = world.getPlace(x, y); //Raum den man betreten möchte erhalten
				if (placeToEnter.canEnter(player)){ //sofern man die Bedingungen zum Betreten des Raumes erfüllt
					placeToEnter.enter(player); // Raum betreten
				}
				else {
					this.x--;
					System.out.println("Du kannst den Raum nicht betreten. Versuch dein Glück woanders ...");
					Scanner s = new Scanner(System.in);
					String newDirection = s.nextLine();
					s.close();
					move(newDirection, world); // Spieler zurück schicken mit neuer Richtungswahl
				}
			}
			
		}
		
	}

	@Override
	public boolean entered(String roomName) {
		return entered.contains(roomName);
	}

	@Override
	public List<String> getEnteredRooms() {
		return entered;
	}

}
