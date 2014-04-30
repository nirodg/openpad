package es.dam.openpad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

class Connection extends Thread {
	private String name;

	// PARTE DEL SERVER PARA COMUNICARSE CON EL CLIENTE.
	Socket socket;
	DataOutputStream salidaDatos;
	DataInputStream entrada;
	long startTime;
	boolean giveUp;
	boolean hasLock;
	String[] pad;
	boolean alive = true;

	public Connection(Socket s, String[] pad) {
		socket = s;
		giveUp = false;
		hasLock = false;
		this.pad = pad;
		
		pad[0] = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.";
		pad[1] = "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes";
		pad[2] = "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem.";
		pad[3] = "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu";
		pad[4] = "Nullam dictum felis eu pede mollis pretium. Integer tincidunt.";
		pad[5] = "";
		pad[6] = "";
		pad[7] = "";
		pad[8] = "";
		pad[9] = "";

		try {
			OutputStream out = socket.getOutputStream();
			entrada = new DataInputStream(socket.getInputStream());
			salidaDatos = new DataOutputStream(out);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void run() {
		try {
			salidaDatos.writeUTF("Te has conectado!\n");

			if (entrada.readInt() == 0) {
				name = entrada.readUTF();
				System.out.println("Identificado como " + name);
			}

			


			try {
			while (!giveUp) {
				switch (entrada.readInt()) {
				case 1: // 
					System.out.println(entrada.readUTF());
					break;
				case 2: // ELIBERA
					giveUp = true;
					System.err.println(name +" abandona el servidor");
					break;
				case 3: //Preguntar bloqueo
					salidaDatos.writeBoolean(hasLock);
					break;
					
				case 4:
					//ENVIA AL CLIENTE EL PAD
					for (String i : pad) {
						salidaDatos.writeUTF(i);
					}
					break;
				}
			}
			} catch (SocketException se) {
				alive = false;
			}
			salidaDatos.close();
			entrada.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void recvLock() {
		startTime = System.currentTimeMillis();
		hasLock = true;
	}

	public boolean unlocked() {
		hasLock = giveUp || System.currentTimeMillis() > (startTime + 10000);
		return hasLock;
	}

	public boolean alive() {
		return alive;
	}

	public String getUserName() {
		return name;
	}
}
