package Prism.extensions.connector.handler;

import java.util.Vector;

import Prism.core.*;
import Prism.extensions.port.*;

public class PipeFilterHandler extends AbstractHandler 
{
	private Vector ports;
	private IConnector parentConnector;
	
	public void handle(Event e)
	{
		if (e.eventType==PrismConstants.REQUEST)
		{
	
            ports = parentConnector.getConnPorts();
            e.handlingBrick=(Brick)parentConnector;

            Port thisPort;
            Brick b;
            int pID;
            for (int i=0;i<ports.size();i++)
            {
                thisPort = (Port)ports.elementAt(i);
                if (thisPort.getPortType() == PrismConstants.REQUEST)
            	{
                    if (thisPort instanceof ExtensiblePort)
                        b = thisPort;
                    else
                        b = (thisPort.getMutualPort()).getParentBrick();
    			
                    b.handle(e);
  		 }	
            }
        }
	}
	
	/**
	 * Sets the connector to which this handler belongs
	 * @param conn		 an IConnector  to which the handler belongs
	 */
	public void setParentConnector(IConnector conn){parentConnector=conn;}
}
