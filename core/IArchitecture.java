package Prism.core;

import java.util.*;

/**
 *  An architecture class in Prism needs to implement this interface.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IArchitecture 
{
	/** This method will add a brick instance to the architecture
	* enabling it to send/recieve events from this architecture.
        *
	* @param b 			A brick object to be added to the architecture.
	*/
	public void add(Brick b);

        
	public Brick getBrickByInstanceName(String name);

	/**
	* This method removes a brick from the architecture and thereby preventing
	* it from receiving anymore messages from this architecture. If 
	* it is not found to be a part of this architecture then the method 
	* does nothing and returns.
        *
	* @param b 		A brick object to be removed from the architecture.
	*/
	public void remove(Brick b);
        
        /**
         * Returns a list of Bricks in this architecture.
         *@return DyanmicArray      list of Bricks
         */
	public Vector getBricks();

	/**
	* This is a generic method for connecting two Ports. It is assumed that the two ports have already been
	* assigned to another parent Brick (component/connector) object. Here we also enforce topological 
	* constraints by allowing or disallowing the weld.
        *
        *@param p1      First Port
        *@param p2      second Port
	*/     
        public void weld(IPort p1,IPort p2);
        
        /**
        * This method disconnects two ports 
        * @param p1 		First Port
        * @param p2 		Second Port
        */
        public void unweld(IPort p1, IPort p2);
	
	public static final int INITIAL_SIZE_OF_BRICK_ARRAY=10;
}
