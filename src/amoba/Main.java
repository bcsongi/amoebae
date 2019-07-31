package amoba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Main {
	public static Coordinate beolvas() {
	     int x = 0, y = 0;
		return new Coordinate(x, y);
	}
	
	public static void main(String[] args) {
		int x = 0, y = 0;
		String s;
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		GameBoard b = new GameBoard(3, 3);
        Random rand = new Random();

        b.kiir();

        System.out.println("Who's gonna move first? (1)Computer (2)User: ");
        int choice = 2;
		try {
			choice = Integer.parseInt(bufferRead.readLine().toString());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        if (choice == 1) {
            Coordinate p = new Coordinate(rand.nextInt(3), rand.nextInt(3));
            b.beszur(p, 1);
            b.kiir();
        }

        while (b.ki_nyert() == 0) {
            System.out.println("Your move: ");
          //  

    		System.out.println("Adja meg a koordinatakat:");
    		try {
    			s = bufferRead.readLine();
    			x = Integer.parseInt(s);
    			s = bufferRead.readLine();
    			y = Integer.parseInt(s);
    		} catch (IOException e) {}
    		Coordinate userMove = new Coordinate(x, y);
        	
            
            b.beszur(userMove, 2); //2 for O and O is the user
            b.kiir();
            if (b.ki_nyert() != 0) 
            	break;
            
            b.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
            for (Score pas : b.faErtekek) { 
                System.out.println("Point: " + pas.getCoordinate() + " Score: " + pas.getScore());
            }
            
            b.beszur(b.legjobbLepes(), 1);
            b.kiir();
        }
        
        int ki = b.ki_nyert();
        if (ki == 1) {
            System.out.println("Unfortunately, you lost!");
        } else if (ki == 2) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}