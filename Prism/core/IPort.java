package Prism.core;

import Prism.extensions.port.ExtensiblePort;

/**
 * This interface is implemented by any object that wants to represent a port in
 * Prism.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IPort extends IBrick {
	/**
	 * Returns the type of port.
	 * 
	 * @return int port type
	 */
	public int getPortType();

	/**
	 * Returns the parent brick of this Port.
	 * 
	 * @return Brick parent Brick
	 */
	public IBrick getParentBrick();

	/**
	 * Sets the parent brick of this Port.
	 * 
	 * @return Brick parent Brick
	 */
	public void setParentBrick(IBrick  pParentBrick);

	/**
	 * In situations when the Port is actually more specialized and is an
	 * ExtensiblePort, this method returns the ExtensiblePort.
	 * 
	 * @return ExtensiblePort child ExtensiblePort
	 */
	public ExtensiblePort getExtensiblePort();

	/**
	 * Sets the reference of this port object to its ExtensiblePort(child).
	 * 
	 * @param pExtPort
	 *            child ExtensiblePort
	 */
	public void setExtensiblePort(ExtensiblePort pExtPort);

	/**
	 * Sets the mutual port for this port
	 * 
	 * @param pPort
	 *            Port to be added
	 */
	public void setMutualPort(IPort pPort);

	/**
	 * This method returns the mutual port
	 * 
	 * @return IPort the mutual port
	 */
	public IPort getMutualPort();

}
