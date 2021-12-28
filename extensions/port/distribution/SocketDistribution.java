package Prism.extensions.port.distribution;

import Prism.extensions.port.*;

import java.util.*;

/**
 * This provides a socket based implementation of distribution over TCP/IP protocol. In practice, a SocketDistribution is assigned to
 * an ExtensiblePort and maintains a list of network connections. A SocketDistribution may have a SocketServer listen for incoming
 * connections on a specified port. 
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class SocketDistribution extends AbstractDistribution
{
    private ExtensiblePort parentPort;
    private Vector conns = new Vector();
    private ListenForConnectionsThread listen;
    private int listeningPortNum=0;

    /**
     * Use this constructor if you don't want this distribution object to listen for incoming connections.
     *@param pPort  Parent ExtensiblePort of this distribution object
     */
    public SocketDistribution (ExtensiblePort pPort)
    {
            parentPort = pPort;
    }
    
    /**
     * Use this constructor if you want this distribution object to listen for incoming connections.
     *@param pPort  Parent ExtensiblePort of this distribution object
     *@param pListeningPortNum  Port number that this distribution object will listen on for incoming connections
     */    
    public SocketDistribution (ExtensiblePort pPort, int pListeningPortNum)
    {
        parentPort=pPort;
        listeningPortNum = pListeningPortNum;
    }
    
    /**
     * Set the parent ExtensiblePort, which is the ExtensiblePort that is associated with this distribution object.
     *@param parent ExtensiblePort
     */    
    public void setParentPort (ExtensiblePort pPort)
    {
        parentPort = pPort;
    }
    
    /** 
     * Returns the parent ExtensiblePort, which is the ExtensiblePort that is associated with this distribution object.
     *@return Parent ExtensiblePort
     */    
    public ExtensiblePort getParentPort ()
    {
        return parentPort;
    }

    /**
     * Returns the port number used to listen for incoming connection requests.
     *@return port number
     */    
    public int getListeningPortNum()
    {
	return(listeningPortNum);
    }
    
    /**
     * Returns list of connections that are affiliated with the parent port
     *@return list of connections
     */    
    public Vector getConnections()
    {
        return conns;
    }

    /**
     * This method is called from the ExtensiblePort to initialized the distribution.
     */    
    public void start()
    {
        if (listeningPortNum != 0)
        {
            listen=new ListenForConnectionsThread(this);
            listen.start();
        }
    }

   /** 
    * Creates a connection between this ExtensiblePort to the desired ExtensiblePort.
    * @param hostName Name of the host to which this connector is connecting. It
    *      is in the form of IP address or hostname.
    * @param portNum Port to which this connection will be binded.
    */     
    public synchronized Connection connect(String hostName, int portNum)
    {

            Connection conn = new Connection(this,hostName,portNum);
            conns.addElement(conn);
            return conn;
    }

    /**
     * Sends the outgoing event to the connection.
     *@param eventObj   Outgoing event
     */
    public void writeEvent(Object eventObj)
    {
        for (int i=0; i< conns.size(); i++)
        {
            Connection thisConn = (Connection)conns.elementAt(i);
            thisConn.writeEvent(eventObj);
        }
    }
    
    /** 
     * Gets the incoming event from the connection.
     *@param eventObj   Incoming event
     */
    public void readEvent(Object eventObj) 
    {
        parentPort.handle(eventObj, "IN");        
    }    

    /**
     * Adds a connection to the list of connections.
     *@param pConn  Connection to be added
     */
    public void addConnection(Connection pConn)
    {
        conns.addElement(pConn);
    }
    
    /** 
     * Removes a connection from the list of connections.
     *@param pConn  Connection to be removed
     */
    public void removeConnection(Connection pConn) 
    {
        conns.removeElement(pConn);
    }
 
}
