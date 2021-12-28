package Prism.extensions.port.distribution;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import Prism.core.PrismConstants;

/**
 * Connection object used by the SocketDistribution to connect.
 */

public class Connection extends AbstractConnection {

	private ObjectOutputStream writer;

	/**
	 * TCP socket belonging to this connection
	 */
	private Socket socket;

	/**
	 * Thread that sits at the socket for this connection and creates new events
	 * when they arrive
	 */
	private ReadConnectionThread readConn;

	public Connection(AbstractDistribution p, String hostName, int portNum) {
		this.setParent(p);
		try {
			this.socket = new Socket(hostName, portNum);
			this.writer = new ObjectOutputStream(this.socket.getOutputStream());

			System.out.println("local port:" + this.socket.getLocalPort());
			this.readConn = new ReadConnectionThread(this, this.socket);
			this.readConn.start();
		} catch (Exception e) {
			if (PrismConstants.DEBUG_MODE) {
				System.out.println("\nException in Connection: constructor ->"
						+ e.toString());
			}
		}
	}

	public Connection(AbstractDistribution p, Socket s) {
		this.setParent(p);
		this.socket = s;
		try {
			this.writer = new ObjectOutputStream(this.socket.getOutputStream());

			this.readConn = new ReadConnectionThread(this, this.socket);
			this.readConn.start();
		} catch (IOException e) {
			if (PrismConstants.DEBUG_MODE) {
				System.out.println("\nException in Connection: constructor ->"
						+ e.toString());
			}
		}
	}

	public InetAddress getHost() {
		return this.socket.getInetAddress();
	}

	public int getPort() {
		return this.socket.getPort();
	}

	public synchronized void writeEvent(Object eventObject) {
		try {
			// System.out.println("sending event: "+((Event)eventObject).name);
			this.writer.writeObject(eventObject);
			// System.out.println("finishing transmission ...");
			this.writer.flush();
			this.writer.reset();
			// System.out.println("Sent event: " + ((Event)eventObject).name);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			this.close();
			// this.reconnect();
		} catch (Exception e1) {
			e1.printStackTrace();
			if (PrismConstants.DEBUG_MODE) {
				System.out.println("%%%%%error in writing event");
			}
			this.close();
			// this.reconnect();
		}
	}

	public void readEvent(Object eventObject) {
		// System.out.println("Received event: " + ((Event)eventObject).name);
		this.getParent().readEvent(eventObject);
	}

	public void close() {
		try {
			this.writer.close();
			this.readConn.stopWorking();
			this.getParent().removeConnection(this);
		} catch (Exception e) {
			if (PrismConstants.DEBUG_MODE) {
				System.out.println(" Connection: Caught exception in close() "
						+ e.toString());
			}
		}
	}

	public void teardown() {
		try {
			this.writer.close();
			this.readConn.stopWorking();
		} catch (Exception e) {
			System.out
					.println("TBC Connection: Caught exception in teardown() "
							+ e.toString());
		}
	}

}
