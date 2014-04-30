package es.dam.openpad;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		System.out.println("-----------------------");
		System.out.println("     EDICION MUTEX     ");
		System.out.println("        SERVIDOR     ");
		System.out.println("-----------------------");
		
		new Server();

	}	
	
	
//	public static void main(String[] args) {
//
//		@SuppressWarnings("resource")
//		Scanner s = new Scanner(System.in);
//
//		System.out.println("-----------------------");
//		System.out.println("     EDICION MUTEX     ");
//		System.out.println("");
//		System.out.println("1. Servidor");
//		System.out.println("2. Cliente");
//		System.out.println("\n-----------------------");
//
//		switch (s.nextInt()) {
//		case 1:
//			System.out.println("SERVER");
//			new Server();
//			break;
//		case 2:
//			System.out.println("CLIENT");
//			new Client();
//			break;
//		default:
//			System.out.println("Comando no disponible");
//			break;
//		}
//
//	}
}
