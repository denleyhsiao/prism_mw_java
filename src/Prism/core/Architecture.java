package Prism.core;

import Prism.util.*;
import Prism.exception.*;

/**
 * Base class for the Prism Architecture. This class is a Brick by itself
 * that can be a part of another Prism Brick. It is responsible for keeping
 * state of the components and connectors that are currently part of this Prism 
 * architecture.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Architecture extends Brick implements IConnector, IComponent, IArchitecture 
{
   private DynamicArray bricks;
   private AbstractTopology topology;
   
    /**
    * This constructor sets the name of this architecture to <code>str</code>.
    * @param str 	The name of this architecture. It cannot be a null value
    */
    public Architecture(String str, AbstractTopology pTopology) 
    {
	  super(str);
        name = str;     
	  bricks = new DynamicArray(INITIAL_SIZE_OF_BRICK_ARRAY);
	  topology = pTopology;
    }

    /**
    * This method must be called to start the architecture.
    *  This method will in turn call the <code>start()</code>
    * method of each Brick in the architecture. 
    */
    public void start() 
    {
	bricks.reset();
	while(bricks.hasNext())
	{
            Brick b = (Brick) bricks.getNext();
            try {
		b.start();
            }
            catch (Exception e) {}
	}

    }	
	
    /**
    * This method will define the action to be taken by this architecture when it
    * recieves an event from another Brick attached to it.
    */
    public void handle(Event e){}

	
    /**
    * This method will add a brick instance <code>b</code> to the architecture.
    * 
    * @param b		A brick object to be added
    */
    public void add(Brick b) 
    {
            bricks.add(b);
    }

    /**
    * This method removes a brick from the architecture and thereby preventing
    * it from receiving anymore messages from this architecture. If 
    * it is not found to be a part of this architecture then the method 
    * does nothing and returns.
    * @param b 		A brick object to be removed from the architecture.
    */
    public void remove(Brick b)
    {
        if (bricks.indexOf(b) != -1)
        {
            bricks.remove(b);
        }
    }


	/**
	* This is a generic method for connecting two Ports. It is assumed that the two ports have already been
	* assigned to another parent Brick (component/connector) object. Here we also enforce topological 
	* constraints by allowing or disallowing the weld.
        *
        *@param p1      First Port
        *@param p2      second Port
	*/
	public void weld(IPort p1, IPort p2) 
	{
	    Brick p1ParentBrick = p1.getParentBrick();
	    Brick p2ParentBrick = p2.getParentBrick();
            if (topology.checkWeld(p1ParentBrick, p2ParentBrick))
            {
                ((Brick)p1).addPort(p2);
                ((Brick)p2).addPort(p1);
            }
	}
        

    /**
    * This method disconnects two ports.
    * @param p1 		First Port
    * @param p2 		Second Port
    */
    public void unweld(IPort p1, IPort p2)
    {
        DynamicArray p1Ports = ((Brick)p1).getPorts();            
        while (p1Ports.hasNext())
        {
            IPort thisPort = (IPort)p1Ports.getNext();
            if (thisPort == p2)
            {
                ((Brick)p1).removePort(thisPort);
                ((Brick)(p1.getParentBrick())).removePort(p1);
            }
        }

        DynamicArray p2Ports = ((Brick)p2).getPorts();            
        while (p2Ports.hasNext())
        {
            IPort thisPort = (IPort)p2Ports.getNext();
            if (thisPort == p1)
            {
                ((Brick)p2).removePort(thisPort);
                ((Brick)(p2.getParentBrick())).removePort(p2);                       
            }                
        }                       
    }

	public void send (Event e){}  
	public DynamicArray getAttachedPorts(){return null;}
	public boolean isAttachedToConnector(IConnector c){return false;}
	public String instanceName(){return this.name;}
	public Brick getBrickByInstanceName(String instName)
	{
		bricks.reset();
		Brick b=null;
		while(bricks.hasNext())
		{
			b = (Brick) bricks.getNext();
			if (b.name.equals(instName))
			{
				return b;
			}
		}
		return null;
	}

	public DynamicArray getBricks()
	{
		return bricks;
	}                
        
        public void addPort(IPort port) {
        }
        
        public void removePort(IPort port) {
        }
        
        public DynamicArray getPorts ()
        {
            return null;
        }
        
}

