package Prism.core;

import Prism.extensions.port.ExtensiblePort;

/**
 * Port represents locus of communication in Prism.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Port extends Brick implements IPort {

	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 7399463401561180253L;

	private int portType;

	private IBrick parentBrick = null;

	private IPort mutualPort = null;

	protected ExtensiblePort extPort;

	/**
	 * This constructor creates an port with default parameters.
	 */
	public Port(int portType) {
		super();
		this.portType = portType;
	}

	/**
	 * This constructor sets the name of this port to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this port. It cannot be a null value.
	 */
	public Port(String name, int portType) {
		super(name);
		this.portType = portType;
	}

	/**
	 * This constructor sets the style of this port to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this port.
	 */
	public Port(int style, int portType) {
		super(style);
		this.portType = portType;
	}

	/**
	 * This constructor sets the scaffold of this port to <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this port.
	 */
	public Port(AbstractScaffold scaffold, int portType) {
		super(scaffold);
		this.portType = portType;
	}

	/**
	 * Initializes the name, style, and scaffold of this port.
	 * 
	 * @param name
	 *            The name of this port.
	 * @param style
	 *            The style of this port.
	 * @param scaffold
	 *            The scaffold of this port.
	 */
	public Port(String name, int style, AbstractScaffold scaffold, int portType) {
		super(name, style, scaffold);
		this.portType = portType;
	}

	/**
	 * Passes an event to the parent to be handled.
	 */
	public void handle(Event e) {
		this.getMutualPort().getParentBrick().handle(e);
	}

	/**
	 * Sets the mutual port for this port
	 * 
	 * @param pPort
	 *            Port to be added
	 */
	public void setMutualPort(IPort pPort) {
		this.mutualPort = pPort;
	}

	/**
	 * This method returns the mutual port
	 * 
	 * @return IPort the mutual port
	 */
	public IPort getMutualPort() {
		return this.mutualPort;
	}

	/**
	 * Returns the type of port. Request is equivalent to the notion of Top in
	 * C2. Reply is equivalent to the notion of Bottom in C2.
	 * 
	 * @return int port type
	 */
	public int getPortType() {
		return this.portType;
	}

	/**
	 * Returns the parent brick of this Port.
	 * 
	 * @return Brick parent Brick
	 */
	public IBrick getParentBrick() {
		return this.parentBrick;
	}

	/**
	 * Sets the parent brick of this Port.
	 * 
	 * @return Brick parent Brick
	 */
	public void setParentBrick(IBrick pParentBrick) {
		this.parentBrick = pParentBrick;
	}

	/**
	 * In situations when the Port is actually more specialized and is an
	 * ExtensiblePort, this method returns the ExtensiblePort.
	 * 
	 * @return ExtensiblePort child ExtensiblePort
	 */
	public ExtensiblePort getExtensiblePort() {
		return this.extPort;
	}

	/**
	 * Sets the reference of this port object to its ExtensiblePort(child).
	 * 
	 * @param pExtPort
	 *            child ExtensiblePort
	 */
	public void setExtensiblePort(ExtensiblePort pExtPort) {
		this.extPort = pExtPort;
	}

	protected void setPortType(int portType) {
		this.portType = portType;
	}

}
