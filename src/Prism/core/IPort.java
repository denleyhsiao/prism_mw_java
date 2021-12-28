package Prism.core;

import Prism.extensions.port.*;

/**
 * This interface is implemented by any object that wants to represent a port
 * in Prism.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IPort 
{
  /**
   * Returns the type of port. A port could be either of type Request, Reply
   * or both. Request is equivalent to the notion of Top in C2. Reply is
   * equivalent to the notion of Bottom in C2.
   * @return int    port type
   */
  public int getPortType ();

  /**
   * Returns the parent brick of this Port.
   *@return Brick   parent Brick
   */
  public Brick getParentBrick ();

  /**
   * In situations when the Port is actually more specialized and is an 
   * ExtensiblePort, this method returns the ExtensiblePort.
   *@return ExtensiblePort      child ExtensiblePort
   */
  public ExtensiblePort getExtensiblePort ();

  /**
   * Sets the reference of this port object to its ExtensiblePort(child).
   *@param pExtPort     child ExtensiblePort
   */
  public void setExtensiblePort (ExtensiblePort pExtPort);
    
}
