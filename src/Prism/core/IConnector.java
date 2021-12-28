package Prism.core;

import Prism.util.*;

/**
 *  This interface needs to be implemented by any Prism connector.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IConnector
{      
   /**
    * This method distributes the incoming event to connected Bricks. Distribution
    * policy depends on the type of IHandler that is installed.
    * @param e      Incoming Event
    */	
    public void handle (Event e);

    /**
    * Get instance name of this connector.
    * @return String     Name of this instance
    */
    public String instanceName();
	
    public static final int INITIAL_SIZE_OF_BRICK_QUEUE=5;
}
