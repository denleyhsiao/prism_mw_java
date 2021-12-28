package Prism.core;

import Prism.util.*;
import Prism.extensions.port.*;

/**
 * This class defines the basic Prism component and the send methods that
 * allow any class extending this one to interact with the architecture.
 * The usual practice is to create a component in your own architecture
 * by extending this class and implementing the handle method.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Component extends Brick implements IComponent, java.io.Serializable 
{
    public transient DynamicArray ports;

  /**
    * This constructor calls on the <code>Brick(String)</code> constructor.
    *
    * @param name	String: the name of this component object. 
    */
   public Component (String name)
   {
	   super(name);
	   ports=new DynamicArray(CONNS_PER_COMPONENT);

   }

  /**
    * This method sends the event up/down the Prism architecture that this component is a
    * part of.
    *
    * @param e		a Event to be sent to the Brick above/below.
    */   
   public void send(Event e)
   {
        e.originatingBrick=this;
	ports.reset();
       
        while (ports.hasNext())
            {                
                IPort thisPort = (IPort)ports.getNext();
                if ( thisPort.getPortType() == e.eventType )
                    {
                        if (thisPort instanceof ExtensiblePort)
                        {
                            Event e1 = e.replicate();
                            e1.handlingBrick = (Brick)thisPort;
                            add(e1);
                        }
                        else if (thisPort instanceof Port)
                        {
                            Event e1 = e.replicate();
                            IPort mutualPort = (IPort)(((Brick)thisPort).getPorts()).getNext();
                            e1.handlingBrick = mutualPort.getParentBrick();
                            add(e1);
                        }                        
                    }
            }
   }

  /**
    * Handling of Event. This is application specific code and should be implemented by subclasses of this class.
    *
    * @param e	Event to be handled
    */   
   public void handle(Event e)
   {
   }

   /**
    * Adds a port to this component. 
    * @param port    Port to be added.
    */
   public void addPort(IPort port) 
   {
	if (ports == null)
	{
	   	ports=new DynamicArray(CONNS_PER_COMPONENT);
       	   	ports.add(port);
	}
	else
	   	ports.add(port);
   }   

   /**
    * Removes a port from this component.
    *@param port    Port to be removed.
    */
   public void removePort(IPort port) 
   {
	if (ports == null)
	{
		return;
	}
        else
       		ports.remove(port);
   }  
   
   /**
    * This method returns a list of all available ports in this component.
    *@return DynamicArray   List of available ports  
    */
   public DynamicArray getPorts ()
   {
	if (ports == null)
		return null;
	else
	{
       		ports.reset();
       		return ports;
	}
   }   
}