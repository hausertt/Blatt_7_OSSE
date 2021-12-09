package de.hfu;

import java.util.Scanner;


/**
 * Soll einen Zeichenkette von der Tastatur einlesen und in Großbuchstaben unwanden
 * und danach ausgeben.
 */
public class App 
{
	
    public static void main( String[] args )
    {
        System.out.println( "starting main... " );
        System.out.println( "Geben Sie einen Text ein:" );
		Scanner Input = new Scanner(System.in);
		String Eingabe = Input.nextLine();
		System.out.println(Eingabe.toUpperCase());
    }
}
