package mutex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client extends Thread{

	String user, command, prompt, ip;
	boolean hasLock, isConnect=false;
	
	String[] pad;
        
        //SERVER - Don't touch
        Socket cli;
        OutputStream out;
        DataInputStream entrada;
        DataOutputStream salidaDatos;
        //SERVER - Don't touch
       

        public Client(){
            this.user = "";
            this.ip = "";
            this.command = "";
            this.prompt = "";
        }
        
        //Iniciar-Parar servido
        @Override
	public void run() {
     		pad = new String[10];
		try {
			cli = new Socket(this.ip, 8090);
			hasLock = false;
                  
                        out  = cli.getOutputStream();
                        entrada = new DataInputStream(cli.getInputStream());
                        salidaDatos = new DataOutputStream(out);

                        salidaDatos.writeInt(0); //Handshake
                        isConnect = true;
                        salidaDatos.writeUTF(this.user); //Enviar usuario al server
                        System.out.println(entrada.readUTF());    
                        
                        salidaDatos.writeInt(0); //Handshake
                        salidaDatos.writeInt(4);
                        
                        
                        for (int i = 0; i < pad.length; i++) {
                                pad[i] = entrada.readUTF();
                        }	
                        
                        /*
                        while(cli.isConnected()){
                            salidaDatos.writeInt(4);
                            // Recibe los datos
                            for (int i = 0; i < pad.length; i++) {
                                    pad[i] = entrada.readUTF();
                            }	
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }*/
		} catch (UnknownHostException e) {
                    System.err.println(e.getMessage());
		} catch (IOException e) {
                    System.err.println(e.getMessage());
                    isConnect = false;
		}
	}
        public void parar(){
            try {
                gUp();
                salidaDatos.close();
                entrada.close();
                cli.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }finally{
                System.out.println("Conexión cerrada");
            }
        }
        public void gUp(){
            try { 
                salidaDatos.writeInt(2);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void dormir(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
          
        //Comprobaciones de Estadp
        public boolean isConnected(){
            return cli.isConnected();
        }
        
        public boolean isLock(){
            try {
                salidaDatos.writeInt(3);
                return entrada.readBoolean();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return false;
        }

        public void setuser(String user){
            this.user = user;
        }
        public void setIp(String ip){
            this.ip = ip;
        }
        
        public void setCommand(){}
        public void setPrompt(){}

        public String[] getText(){
            return pad;
        }
        
        public String getUser(){
            return user;
        }
        public String getIp(){
            return ip;
        }        
        
}