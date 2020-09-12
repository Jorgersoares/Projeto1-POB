package console_application;

import facade.Facade;

public class Consulta {

	public Consulta(){
		Facade.start();
		consultar();
		Facade.finish();
	}
	
	//Nome de jogos que tem preço acima de 50
	public void consultar() {
		System.out.println(Facade.pricesAboveFifty());
	}
	
	public static void main(String[] args) {
		new Consulta();

	}

}
