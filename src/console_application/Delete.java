package console_application;

import facade.Facade;

public class Delete {
	
	public Delete() {
		Facade.start();
		
		try {
			Facade.deleteGame("CrossCode");
			System.out.println("Registro excluido com sucesso!");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Facade.finish();
	}
	
	public static void main(String[] args) {
		new Delete();

	}

}
