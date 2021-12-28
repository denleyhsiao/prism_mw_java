package Prism.core;

import java.util.Vector;

import Prism.extensions.port.*;

/**
 * A connector provides interaction and mediation services to attached components.
 * The connector is connected to a set of components on both sides (a set may be empty). 
 * Connectors use the provided AbstractHandler to distribute event among connected Bricks.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */

public class Connector extends Brick implements IConnector
{
   private Vector ports;   
   
/**
    * Constructor for connector that defines a simple connector with the given name.  
    * @param name       String name of the connector
    * 
    */
   public Connector (String name)
   {
		super (name);
		ports=new Vector(INITIAL_SIZE_OF_BRICK_QUEUE );
        style = PrismConstants.DEFAULT;
   }
   
   public Connector (String name, int pStyle)
   {
		super (name);
		ports=new Vector(INITIAL_SIZE_OF_BRICK_QUEUE );
		style = pStyle;
   }   

   /**
    * This method distributes the incoming event to connected Bricks. Distribution
    * policy depends on the type of IHandler that is installed.
    * @param e      Incoming Event
    */
   public void handle (Event e)
   {
		e.handlingBrick = (Brick)this;
	
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
    * Adds a port to this connector. 
    * @param port    Port to be added.
    */   
   public void addConnPort(IPort port) 
   {    
   	   port.setParentBrick(this);
       ports.addElement(port);              
   }
   
   /**
    * Removes a port from this component.
    *@param port    Port to be removed.
    */   
   public void removeConnPort(IPort port) 
   {
       if (ports != null)
       {	
       		ports.removeElement(port);
       		port.setParentBrick(null);       		
       }
   }

   /**
    * This method returns a list of all available ports in this connector.
    *@return DynamicArray   List of available ports  
    */   
   public Vector getConnPorts() 
   {
       return ports;
   }
   
}
