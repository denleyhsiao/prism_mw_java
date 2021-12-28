package Prism.extensions.port.distribution;

import Prism.extensions.port.*;

import java.util.*;

/**
 * Any distribution implementation in Prism needs to extend this abstract class. The communication protocol doesn't matter as
 * long as the appropriate implementation is provided for the methods specified in this abstract class.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractDistribution
{
    /**
     * Set the parent ExtensiblePort, which is the ExtensiblePort that is associated with this distribution object.
     *@param parent ExtensiblePort
     */
    public abstract void setParentPort (ExtensiblePort port);

    /** 
     * Returns the parent ExtensiblePort, which is the ExtensiblePort that is associated with this distribution object.
     *@return Parent ExtensiblePort
     */
    public abstract ExtensiblePort getParentPort();

    /**
     * Returns the port number used to listen for incoming connection requests.
     *@return port number
     */
    public abstract int getListeningPortNum();

    /**
     * Returns list of connections that are affiliated with the parent port
     *@return list of connections
     */
    public abstract Vector getConnections();
    
    /**
     * This method is called from the ExtensiblePort to initialized the distribution.
     */
    public abstract void start();
    
   /** 
    * Creates a connection between this ExtensiblePort to the desired ExtensiblePort.
    * @param hostName Name of the host to which this connector is connecting. It
    *      is in the form of IP address or hostname.
    * @param portNum Port to which this connection will be binded.
    */    
    public  abstract Connection connect(String hostName, int portNum);

    /**
     * Sends the outgoing event to the connections.
     *@param eventObj   Outgoing event
     */
    public abstract void writeEvent (Object eventObj);
    
    /**
     * Gets the incoming event from the connection.
     *@param eventObj   Incoming event
     */
    public abstract void readEvent (Object eventObj);
    
    /**
     * Adds the connection to the list of connections.
     *@param pConn      Connection to be added
     */
    public abstract void addConnection (Connection pConn);
    
    /**
     * Removes the connection from the list of connections.
     *@param pConn      Connection to be removed
     */
    public abstract void removeConnection (Connection pConn);
}
