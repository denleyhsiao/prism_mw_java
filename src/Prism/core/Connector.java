package Prism.core;

import Prism.util.*;

/**
 * A connector provides interaction and mediation services to attached components.
 * The connector is connected to a set of components on both sides (a set may be empty). 
 * Connectors use the provided IHandler to distribute event among connected Bricks.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */

public class Connector extends Brick implements IConnector
{
   private DynamicArray ports;
   private IHandler evtHandler;

  /**
    * Constructor for connector that defines a simple connector with the given name.  
    * @param name       String name of the connector
    * @param pHndl      IHandler object that determines the distribution policy
    */
   public Connector (String name, IHandler pHndl)
   {
	super (name);
	ports=new DynamicArray(INITIAL_SIZE_OF_BRICK_QUEUE );
        evtHandler = pHndl;
        evtHandler.setParentConnector(this);       
   }

   /**
    * Sets the IHandler of this connector.
    * @param pHndl       IHandler object that determines the distribution policy
    */
   public void setEvtHandler (IHandler pHndl)
   {
     evtHandler = pHndl;
     evtHandler.setParentConnector(this);
   }
   
   /**
    * Gets the IHandler of this connector.
    * @return IHandler   IHandler object that determines the distribution policy
    */
   public IHandler getEvtHandler()
   {
	   return evtHandler;
   }

   /**
    * Get instance name of this connector.
    * @return String     Name of this instance
    */
   public String instanceName()
   {
	    return name;
   }

   /**
    * This method distributes the incoming event to connected Bricks. Distribution
    * policy depends on the type of IHandler that is installed.
    * @param e      Incoming Event
    */
   public void handle (Event e)
   {
	   this.evtHandler.handle(e);
   }

   /**
    * Adds a port to this connector. 
    * @param port    Port to be added.
    */   
   public void addPort(IPort b) 
   {    
       ports.add(b);           
   }
   
   /**
    * Removes a port from this component.
    *@param port    Port to be removed.
    */   
   public void removePort(IPort b) 
   {
       ports.remove(b);
   }

   /**
    * This method returns a list of all available ports in this connector.
    *@return DynamicArray   List of available ports  
    */   
   public DynamicArray getPorts() 
   {
       ports.reset();
       return ports;
   }
   
}
