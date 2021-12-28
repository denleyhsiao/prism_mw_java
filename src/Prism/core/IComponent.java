package Prism.core;

/**
 *  This interface needs to be implemented by any Prism component.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IComponent
{              
  /**
    * This method sends the event up/down the Prism architecture that this component is a
    * part of.
    *
    * @param e		a Event to be sent to the Brick above/below.
    */ 
    public void send(Event e);
	
  /**
    * Handling of Event. This is application specific code and should be implemented by subclasses of this class.
    *
    * @param e	Event to be handled
    */ 
    public void handle(Event e);	
	
    public static final int CONNS_PER_COMPONENT=10;
}
