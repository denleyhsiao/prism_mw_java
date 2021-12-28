package Prism.core;

import Prism.extensions.port.*;

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
    protected IPort mutualPort;

    protected ExtensiblePort extPort;

    /**
     * Port constructor. 
     * @param pPortType     PortType which could either be Request or Reply. 
     * @param pBrick        parent of this Port. Usually a Component/Connector.
     */
    public Port(String name, int pPortType)
    {
      super(name);
      portType = pPortType;
      parentBrick = null;
      mutualPort = null;
    }
    
    /**
     * Simple place holder method. Doesn't do anything.
     */
    public void handle(Event e)
    {
    }

   /**
    * Sets the mutual port for this port
    *@param pPort   Port to be added
    */
    public void setMutualPort (IPort pPort)
    {
        mutualPort = pPort;
    }    
   
   /**
    * This method returns the mutual port
    *@return IPort the mutual port  
    */    
    public IPort getMutualPort() 
    {
        return mutualPort;
    }  

  /**
   * Returns the type of port. Request is equivalent to the notion of Top in C2. Reply is
   * equivalent to the notion of Bottom in C2.
   * @return int    port type
   */    
    public int getPortType ()
    {
        return portType;
    }

  /**
   * Returns the parent brick of this Port.
   *@return Brick   parent Brick
   */    
    public Brick getParentBrick ()
    {
        return parentBrick;
    }
    
	/**
	 * Sets the parent brick of this Port.
	 *@return Brick   parent Brick
	 */    
	  public void setParentBrick (Brick pParentBrick)
	  {
		   parentBrick = pParentBrick;
	  }    

  /**
   * In situations when the Port is actually more specialized and is an 
   * ExtensiblePort, this method returns the ExtensiblePort.
   *@return ExtensiblePort      child ExtensiblePort
   */    
    public ExtensiblePort getExtensiblePort ()
    {
        return extPort;
    }

  /**
   * Sets the reference of this port object to its ExtensiblePort(child).
   *@param pExtPort     child ExtensiblePort
   */    
    public void setExtensiblePort (ExtensiblePort pExtPort)
    {
        extPort = pExtPort;
    }   
  
    
}
