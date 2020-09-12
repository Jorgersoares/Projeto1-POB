package console_application;

import facade.Facade;

public class List {
	
	public List() {
		Facade.start();
		try {
			listar();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Facade.finish();
	}
	
	public void listar() {
		System.out.println(Facade.listAllGames());
		System.out.println(Facade.listAllUsers());
		System.out.println(Facade.listAllGenres());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new List();

	}

}
