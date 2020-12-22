package juegos_de_azar;

import java.io.IOException;

public class Cara_o_cruz {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		String cara_o_cruz;
		
		
		long contador_david = 0;
		long contador_oliver = 0;
		
		
		while (true)
		{
			double numero_aletario = Math.random();
			
			if (numero_aletario>0.5) cara_o_cruz = "Cara";
			else cara_o_cruz = "Cruz";
			
			if (cara_o_cruz == "Cara") 
			{
				contador_david = contador_david + 1;
			}
			else contador_oliver = contador_oliver + 1;
			
			System.out.println("David = " + contador_david);
			System.out.println("Oliver = " + contador_oliver);
			Thread.sleep(500);
			clearScreen();
			
			
		}
			
	}

	public static void clearScreen() throws InterruptedException, IOException {  
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); 
	}
}
