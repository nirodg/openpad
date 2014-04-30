package es.dam.openpad;

public class Debugger {
	static int msgs;
	
	public static void setMsgs(int n) {
		msgs = n;
	}
	
	public static void print(String msg) {
		if(0 < msgs) {
			System.err.println("D"+msgs+": "+msg);
			msgs--;
		}
	}
}
