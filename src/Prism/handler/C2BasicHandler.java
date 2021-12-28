package Prism.handler;

import Prism.util.*;
import Prism.core.*;
import Prism.extensions.port.*;

/**
 * C2BasicHandler is an implementation of IHandler interface for handling events in the manner basic
 * C2 style connectors handle messages. 
 * 
 * @version 0.2
 */
public class C2BasicHandler implements IHandler 
{
	private DynamicArray ports;
	private IConnector parentConnector;

	public C2BasicHandler()
	{
	}
        
	/**
	 * Handles the events in the same way basic C2 connectors handle them
	 * @param e		Event to be handled
	 */
	public void handle(Event e)
	{
            ports = ((Brick)parentConnector).getPorts();
            e.handlingBrick=(Brick)parentConnector;

            Port thisPort;
            Brick b;
            int pID;
            for (int i=0;i<ports.size();i++)
            {
                thisPort = (Port)ports.get(i);
		if (thisPort.getPortType() == e.eventType)
                {
                    if (thisPort instanceof ExtensiblePort)
                        b = thisPort;
                    else
                        b = ((IPort)(thisPort.getPorts()).getNext()).getParentBrick();              
    			
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