package es.dam.openpad;

import java.util.List;
import java.util.Scanner;

class Locker extends Thread {
	private List<Connection> conns;
	private Connection locked;

	public Locker(List<Connection> conns) {
		this.conns = conns;
	}

	public void run() {
		try {
			for (;;) {
				Thread.sleep(5000);

				// ¿Hay hilos en la lista?
				if (conns.size() == 0) {
					locked = null;
					Debugger.print("List empty");
					continue;
				}

				// Condición rara, cuando ponemos conexión nueva partiendo de 0
				if (locked == null) {
					locked = conns.get(0);
					locked.recvLock();
					Debugger.print("Started up " + locked.getUserName());
					continue;
				}

				if (1 < conns.size()&&locked.unlocked()) {
					int indx = conns.indexOf(locked);
					if (++indx >= conns.size()) {
						indx %= conns.size();
					}
					
					Connection testsock = conns.get(indx);
					if(!testsock.alive()) {
						Debugger.print("Removing "+testsock.getUserName() +
								", " + conns.size() + " connections remain");
						conns.remove(indx);
						testsock = null; // Para el recolector de basura
						continue;
					}

					locked = testsock;
					locked.recvLock();
					Debugger.print("Changed locker to "	+ locked.getUserName());
				} else {
					Debugger.print("Thread kept "+locked.getUserName());
				}
			}
		} catch (/*Interrupted*/Exception e) {
		}
	}

	public Connection getLocked() {
		return locked;
	}
}
