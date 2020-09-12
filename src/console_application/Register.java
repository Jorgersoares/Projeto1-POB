package console_application;

import facade.Facade;
import model.Game;
import model.Genre;

public class Register {
	
	public Register(Game obj, String[] array) {
		Facade.start();
	
		try {
			Facade.registerGame(obj, array);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}			
		
		Facade.finish();
	}
	
	public Register(String name, String email, String password) {
		Facade.start();
		
		try {
			Facade.registerUser(name, email, password);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}			
		
		Facade.finish();
	}
	
	public Register(Genre obj) {
		Facade.start();
		
		try {
			Facade.registerGenre(obj);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}			
		
		Facade.finish();
	} 
	
	public static void main(String[] args) {
		
		// Adding genres
		new Register(new Genre("Action"));
		new Register(new Genre("Shooter"));
		new Register(new Genre("Adventure"));
		new Register(new Genre("Casual"));
		new Register(new Genre("Indie"));
		new Register(new Genre("RPG"));
		new Register(new Genre("RTS"));
		new Register(new Genre("Sports"));
		new Register(new Genre("Racing"));
		new Register(new Genre("Simulation"));
		
		// Adding genres
		String[] arrayA = {"Action", "Adventure"};
		String[] arrayB = {"Action"};
		String[] arrayC = {"Action"};
		
		// Adding games
		new Register(new Game("CrossCode", 35.25), arrayA);
		new Register(new Game("GTA V", 69.99), arrayB);
		new Register(new Game("Borderlands 3", 119.99), arrayC);
		
		// Adding users
		new Register("Adilson","adelso@gmail.com", "123456");
		new Register("Paulo", "paulo@hotmail.com", "abcd");
	}
}
