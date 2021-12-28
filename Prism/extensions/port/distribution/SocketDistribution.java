package Prism.extensions.port.distribution;

import java.util.Vector;

import Prism.extensions.port.ExtensiblePort;

/**
 * This provides a socket based implementation of distribution over TCP/IP
 * protocol. In practice, a SocketDistribution is assigned to an ExtensiblePort
 * and maintains a list of network connections. A SocketDistribution may have a
 * SocketServer listen for incoming connections on a specified port.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class SocketDistribution extends AbstractDistribution {
	private ListenForConnectionsThread listen;

	private ExtensiblePort parentPort;

	private Vector<AbstractConnection> conns = new Vector<AbstractConnection>();

	private int listeningPortNum = 0;

	private boolean isConnectionThreadInitialized = false;

	public SocketDistribution() {
	}

	public SocketDistribution(int pListeningPortNum) {
		this.listeningPortNum = pListeningPortNum;
	}

	/**
	 * Use this constructor if you don't want this distribution object to listen
	 * for incoming connections.
	 * 
	 * @param pPort
	 *            Parent ExtensiblePort of this distribution object
	 */
	public SocketDistribution(ExtensiblePort pPort) {
		this.setParentPort(pPort);
	}

	/**
	 * Use this constructor if you want this distribution object to listen for
	 * incoming connections.
	 * 
	 * @param pPort
	 *            Parent ExtensiblePort of this distribution object
	 * @param pListeningPortNum
	 *            Port number that this distribution object will listen on for
	 *            incoming connections
	 */
	public SocketDistribution(ExtensiblePort pPort, int pListeningPortNum) {
		this.setParentPort(pPort);
		this.listeningPortNum = pListeningPortNum;
	}

	/**
	 * Returns the parent ExtensiblePort, which is the ExtensiblePort that is
	 * associated with this distribution object.
	 * 
	 * @return Parent ExtensiblePort
	 */
	public ExtensiblePort getParentPort() {
		return this.parentPort;
	}

	/**
	 * Returns the port number used to listen for incoming connection requests.
	 * 
	 * @return port number
	 */
	public int getListeningPortNum() {
		return this.listeningPortNum;
	}

	/**
	 * Returns list of connections that are affiliated with the parent port
	 * 
	 * @return list of connections
	 */
	public Vector<AbstractConnection> getConnections() {
		return this.conns;
	}

	/**
	 * This method is called from the ExtensiblePort to initialized the
	 * distribution.
	 */
	public void start() {
		if (!this.isConnectionThreadInitialized && this.listeningPortNum != 0) {
			this.listen = new ListenForConnectionsThread(this);
			this.listen.start();
			this.isConnectionThreadInitialized = true;
		}
	}

	/**
	 * Creates a connection between this ExtensiblePort to the desired
	 * ExtensiblePort.
	 * 
	 * @param hostName
	 *            Name of the host to which this connector is connecting. It is
	 *            in the form of IP address or hostname.
	 * @param portNum
	 *            Port to which this connection will be binded.
	 */
	public synchronized AbstractConnection connect(String hostName, int portNum) {

		AbstractConnection conn = new Connection(this, hostName, portNum);
		this.conns.addElement(conn);
		return conn;
	}

	/**
	 * Sends the outgoing event to the connection.
	 * 
	 * @param eventObj
	 *            Outgoing event
	 */
	public void writeEvent(Object eventObj) {
		for (int i = 0; i < this.conns.size(); i++) {
			Connection thisConn = (Connection) this.conns.elementAt(i);
			thisConn.writeEvent(eventObj);
		}
	}

	/**
	 * Gets the incoming event from the connection.
	 * 
	 * @param eventObj
	 *            Incoming event
	 */
	public void readEvent(Object eventObj) {
		this.getParentPort().handle(eventObj, "IN");
	}

	/**
	 * Adds a connection to the list of connections.
	 * 
	 * @param pConn
	 *            Connection to be added
	 */
	public void addConnection(AbstractConnection pConn) {
		this.conns.addElement(pConn);
	}

	/**
	 * Removes a connection from the list of connections.
	 * 
	 * @param pConn
	 *            Connection to be removed
	 */
	public void removeConnection(AbstractConnection pConn) {
		this.conns.removeElement(pConn);
	}

	@Override
	public void setParentPort(ExtensiblePort port) {
		this.parentPort = port;
	}

}
