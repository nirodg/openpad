package es.dam.openpad;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Server {
	List<Connection> conexiones;
	Connection bloqueada;
	Locker bloqueos;
	String[] pad;

	ServerSocket serverSocket;
	
	public Server() {
		conexiones = new LinkedList<Connection>();
		bloqueos = new Locker(conexiones);
		pad = new String[10];		

		try {
			Scanner s = new Scanner(System.in);
			
			serverSocket = new ServerSocket(8090);
			bloqueos.start();
			
			ConnHandler ch = new ConnHandler(serverSocket);
			ch.start();
			
			while(true){
				String cmd = s.next();
				
								
				if(cmd.equals("n")) {
					int n = s.nextInt();
					System.out.println("Added: "+n);
					Debugger.setMsgs(n);
				}
			}

		} catch (Exception e) {
		}
	}

	private class ConnHandler extends Thread {
		ServerSocket sock;

		public ConnHandler(ServerSocket socket) {
			sock = socket;
		}

		public void run() {
			try {
				while (true) {
					Socket cliSocket;

					cliSocket = sock.accept();

					Connection conn = new Connection(cliSocket, pad);
					conexiones.add(conn);
					conn.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
