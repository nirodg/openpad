import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class CopyOfNoobSocket {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("-----------------------");
		System.out.println("     EDICION MUTEX     ");
		System.out.println("");
		System.out.println("1. Servidor");
		System.out.println("2. Cliente");
		System.out.println("\n-----------------------");
		
		switch (s.nextInt()) {
		case 1:
			new server();
			break;
		case 2:
			new client();
			break;
		default:
			System.out.println("Comando no disponible");
			break;
		}
		
	}

}


class server{
	public server() {
		try {
			ServerSocket serverSocket = new ServerSocket(8090);
			Socket cliSocket = serverSocket.accept();

			OutputStream out = cliSocket.getOutputStream();
			DataInputStream entrada = new DataInputStream(cliSocket.getInputStream());
			DataOutputStream salidaDatos = new DataOutputStream(out);
			
			
			salidaDatos.writeUTF("SERVER: TE HAS CONECTADO AL SERVIDOR\n");
			
			System.out.println(entrada.readUTF());
			
			
			salidaDatos.close();
			entrada.close();
			cliSocket.close();
			serverSocket.close();
			
		} catch (Exception e) {
		}
	}
	
}
class client{
	public client() {
		try {
			Socket cli = new Socket("127.0.0.1", 8090);
			System.out.println("Conectado");
			
			OutputStream out = cli.getOutputStream();
			DataInputStream entrada = new DataInputStream(cli.getInputStream());
			DataOutputStream salidaDatos = new DataOutputStream(out);
			
			System.out.println(entrada.readUTF());
			
			salidaDatos.writeUTF("CLIENTE: CONECTADO CORRECTAMENTE\n");
			
			
			salidaDatos.close();
			entrada.close();
			cli.close();
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}