package es.dam.openpad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client {

	String user, command, prompt;
	int currentline;
	boolean hasLock;
	DataOutputStream salidaDatos;
	String[] pad;

	public Client() {
		pad = new String[10];

		Scanner s = new Scanner(System.in);

		System.out.print("Nickname: ");
		user = s.nextLine();

		try {
			Socket cli = new Socket("127.0.0.1", 8090);
			System.out.println("Conectado");
			hasLock = false;

			OutputStream out = cli.getOutputStream();
			DataInputStream entrada = new DataInputStream(cli.getInputStream());
			salidaDatos = new DataOutputStream(out);

			System.out.println(entrada.readUTF());

			salidaDatos.writeInt(0);
			salidaDatos.writeUTF(user);
			currentline = 0;

			// Recibe los datos
			for (int i = 0; i < pad.length; i++) {
				pad[i] = entrada.readUTF();
			}

//			while (readCommands()) {
//			} // Leer comandos
			
			readCommands();

			salidaDatos.writeInt(2);

			salidaDatos.close();
			entrada.close();
			cli.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readCommands() throws IOException {
	}
}
