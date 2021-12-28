package Prism.core;

import Prism.extensions.port.*;
import Prism.util.*;

/**
 * Port represents locus of communication in Prism.  
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Port extends Brick implements IPort
{
    protected int portType;
    protected Brick parentBrick;
    protected DynamicArray mutualPorts;

    protected ExtensiblePort extPort;

    /**
     * Port constructor. 
     * @param pPortType     PortType which could either be Request or Reply. 
     * @param pBrick        parent of this Port. Usually a Component/Connector.
     */
    public Port(int pPortType, Brick pBrick)
    {
      super("port");
      portType = pPortType;
      parentBrick = pBrick;
      mutualPorts = new DynamicArray(2);
    }
    
    /**
     * Simple place holder method. Doesn't do anything.
     */
    public void handle(Event e)
    {
    }

   /**
    * Adds a mutual port to this port
    *@param pPort   Port to be added
    */
    public void addPort (IPort pPort)
    {
        mutualPorts.add(pPort);
    }
    
   /**
    * Removes a mutual port from this Port.
    *@param pPort    Port to be removed.
    */    
    public void removePort (IPort pPort)
    {
        mutualPorts.remove(pPort);
    }
   
   /**
    * This method returns a list of all available mutual ports in this Port.
    *@return DynamicArray   List of available ports  
    */    
    public DynamicArray getPorts() 
    {
        mutualPorts.reset();
        return mutualPorts;
    }  

    public int getPortType ()
    {
        return portType;
    }
    
    public Brick getParentBrick ()
    {
        return parentBrick;
    }

    public ExtensiblePort getExtensiblePort ()
    {
        return extPort;
    }

    public void setExtensiblePort (ExtensiblePort pExtPort)
    {
        extPort = pExtPort;
    }   
  
}
