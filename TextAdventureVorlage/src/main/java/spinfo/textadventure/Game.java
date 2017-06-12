package spinfo.textadventure;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game implements spinfo.textadventure.games.Game {
	private String name = "Schere, Stein, Papier";
	private String comment = "Lass uns eine Runde Schere, Stein, Papier spielen. Wenn du gewinnst, darfst du eintreten!";
		
	/**
	 * Schere, Stein, Papier. 
	 * Das Spiel weist bestimmten Zustände einen Zahlenraum zu und überprüft mittels einer switch-Struktur
	 * ob der Gegenspieler oder der Spieler selbst gewinnt. Dabei ist der Zahlenraum <= 0.33 = Papier, 0.33 bis <= 0.66 Schere und >0.66 Stein.
	 * Unentschieden führt zum selben Ergebnis, wie wenn der Spieler verliert! 
	 */
	@Override
	public boolean play() {
		System.out.println("Wähle: Stein, Schere oder Papier?");
		Scanner scanner = new Scanner(System.in);
		
		double random = Math.random();
		
			String scanned = scanner.nextLine();
			scanned = scanned.toLowerCase().trim(); // um Fehler bei der Eingabe zu minimieren: vereinheitlichen klein, Leerstellen entfernen
			
			Pattern pattern = Pattern.compile("(stein|schere|papier)");
			Matcher matcher = pattern.matcher(scanned);
		
		if (matcher.find()){
			scanned = matcher.group();
			
			
			switch (scanned){
				case "stein":
					if (random <= 0.33) {
						System.out.println("Gegner wählt: Papier. Verloren! Neuer Versuch...");
						
						play(); 			// Rekursion für eine neue Runde
						scanner.close();
						break;
					}
					if (random > 0.33 && random <= 0.66) {
						System.out.println("Gegner wählt: Schere. Gewonnen! Du darfst passieren...");
						scanner.close();
						return true;
						
					}
					if (random > 0.66) {
						System.out.println("Gegner wählt: Stein. Unentschieden! Aber da nur Gewinner passieren dürfen... neuer Versuch! ;-)");
						
						play();
						scanner.close();
						break;
					}
				case "papier":
					if (random <= 0.33) {
						System.out.println("Gegner wählt: Papier. Unentschieden! Aber da nur Gewinner passieren dürfen... neuer Versuch! ;-)");
						
						play();
						scanner.close();
						break;
					}
					if (random > 0.33 && random <= 0.66) {
						System.out.println("Gegner wählt: Schere. Verloren! Neuer Versuch ...");
						
						play();
						scanner.close();
						break;
						
					}
					if (random > 0.66) {
						System.out.println("Gegner wählt: Stein. Gewonnen! Du darfst passieren...");
						scanner.close();
						return true;
					}
				case "schere":
					if (random <= 0.33) {
						System.out.println("Gegner wählt: Papier. Gewonnen! Du darfst passieren...");
						
						scanner.close();
						return true;
						
					}
					if (random > 0.33 && random <= 0.66) {
						System.out.println("Gegner wählt: Schere. Unentschieden! Aber da nur Gewinner passieren dürfen... neuer Versuch! ;-)");
						
						play();
						scanner.close();
						break;
					}
					if (random > 0.66) {
						System.out.println("Gegner wählt: Stein. Verloren! Neuer Versuch...");
						
						play();
						scanner.close();
						break;
					}
//				default: 			// falls eine ungültige Wahl getroffen wird
//					System.out.println("... Wir spielen Schere/Stein/Papier, du Spaßvogel ... triff eine gültige Wahl!");
//					play();
//					break;
					
			}
			} else { //falls eine ungültige Wahl getroffen wird
				System.out.println("... Wir spielen Schere/Stein/Papier, du Spaßvogel ... triff eine gültige Wahl!");
				scanner.close();
				play();
				
			}
				
			
		
		scanner.close();
		return false;
	}
	
	

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getComment() {
		return this.comment;
	}

}
