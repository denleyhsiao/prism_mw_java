package Prism.extensions.port.distribution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Prism.core.PrismConstants;

/**
 * ReadConnectionThread class is used to read the socket continuosly.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
class ReadConnectionThread extends Thread {
	/**
	 * ReadConnectionThread will keep on running as long as keepWorking is true.
	 */
	private boolean keepWorking;

	/**
	 * Connection to which this thread belongs
	 */
	private Connection conn;

	/**
	 * The StreamConnection class is used to instantiate a socket on a
	 * particular port
	 */
	private Socket streamConn;

	private ObjectInputStream ir;

	/**
	 * This constructor instantiates a ReadConnectionThread on a particular
	 * socket.
	 * 
	 * @params parent A connector that drives the ReadConnectionThread
	 * @params socket The socket on which the connection is made
	 */
	public ReadConnectionThread(Connection conn, Socket sc) {
		super();
		this.keepWorking = true;
		this.conn = conn;
		this.streamConn = sc;

		try {
			this.ir = new ObjectInputStream(this.streamConn.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		Object eventObject = null;
		try {
			while (this.keepWorking) {
				eventObject = this.ir.readObject();
				this.conn.readEvent(eventObject);
			} // while ends

			this.finalize1();
		} catch (Exception e1) {
			if (PrismConstants.DEBUG_MODE) {
				System.out.println("Caught Exception in ReadConnectionThread "
						+ e1.toString());
			}
			this.conn.close();
		} // catch ends
	}

	void finalize1() {
		try {
			this.ir.close();
			this.streamConn.close();
		} catch (IOException e) {
		}
	}

	public void stopWorking() {
		this.keepWorking = false;
	}

	protected void setKeepWorking(boolean keepWorking) {
		this.keepWorking = keepWorking;
	}

	protected boolean isKeepWorking() {
		return this.keepWorking;
	}
}
