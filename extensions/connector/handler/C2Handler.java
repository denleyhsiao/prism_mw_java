package Prism.extensions.connector.handler;

import java.util.Vector;

import Prism.core.*;
import Prism.extensions.port.*;

/**
 * C2BasicHandler is an implementation of AbstractHandler for handling events in the manner basic to
 * C2 style connectors handle messages. 
 * 
 * @version 0.2
 */
public class C2Handler extends AbstractHandler 
{
	private Vector ports;
	private IConnector parentConnector;
        
	/**
	 * Handles the events in the same way basic C2 connectors handle them
	 * @param e		Event to be handled
	 */
	public void handle(Event e)
	{
            ports = parentConnector.getConnPorts();
            e.handlingBrick=(Brick)parentConnector;

            Port thisPort;
            Brick b;
            int pID;
            for (int i=0; i < ports.size(); i++)
            {
                thisPort = (Port)ports.elementAt(i);
                if (thisPort.getPortType() == e.eventType)
                {
                    if (thisPort instanceof ExtensiblePort)
                        b = thisPort;
                    else
                        b = (thisPort.getMutualPort()).getParentBrick();              
    			
                    b.handle(e);
                }
            }
    }
  
	/**
	 * Sets the connector to which this handler belongs
	 * @param conn		 an IConnector  to which the handler belongs
	 */
	public void setParentConnector(IConnector conn){parentConnector=conn;}

}