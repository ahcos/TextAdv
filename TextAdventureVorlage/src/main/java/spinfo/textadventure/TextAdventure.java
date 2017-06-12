package spinfo.textadventure;

import java.io.File;

import spinfo.textadventure.io.IO;

public class TextAdventure {

	public static IO getIO() {
		File gameDirectory = new File("games/adventure");
		
		IO io = new spinfo.textadventure.IO();
		

		io.setGameDirectory(gameDirectory);
		return io;
	}

	public static void main(String[] args) {
		try {
			IO io = getIO();
			Runtime runtime = new Runtime();
			runtime.initialize(io);
			runtime.play();
		} catch (InvalidGameException e) {
			e.printStackTrace();
		}
	}

}
