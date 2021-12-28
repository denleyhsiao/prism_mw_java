package Prism.extensions.port.distribution;

import java.net.ServerSocket;
import java.net.Socket;

import Prism.core.PrismConstants;

/**
 * Listens for incoming connections. It is associated with a Parent distribution
 * object. The distribution object instantiates this to listen for incoming
 * connections.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
class ListenForConnectionsThread extends Thread {
	private boolean keepWorking;

	private AbstractDistribution parentDistribution;

	private ServerSocket ss;

	/**
	 * Simple constructor.
	 * 
	 * @param parent
	 *            Parent distribution object
	 */
	public ListenForConnectionsThread(AbstractDistribution parent) {
		super();
		this.parentDistribution = parent;
		this.keepWorking = true;

	}

	/**
	 * Starts the thread that listens for incoming connections.
	 */
	public void run() {
		try {
			this.ss = new ServerSocket(this.parentDistribution.getListeningPortNum());
		} catch (Exception e) {
			System.out.println("\n Exception in ServerSocket(): "
					+ e.toString());
		}

		System.out.println("Ready to accept connections");

		Socket sock;

		while (this.keepWorking) {
			try {
				sock = this.ss.accept();
				if (PrismConstants.DEBUG_MODE) {
					System.out.println("\n** Connect from: "
							+ sock.getInetAddress() + ":" + sock.getPort());
				}
				this.parentDistribution.addConnection(new Connection(
						this.parentDistribution, sock));

			} catch (Exception e) {
				System.out
						.println("Exception while listening for connections:  "
								+ e.toString());
			}
		}
	}

	protected void setKeepWorking(boolean keepWorking) {
		this.keepWorking = keepWorking;
	}

	protected boolean isKeepWorking() {
		return this.keepWorking;
	}

}
