package Prism.core;

import java.util.Vector;

import Prism.extensions.port.ExtensiblePort;

/**
 * This class defines the basic Prism component and the send methods that allow
 * any class extending this one to interact with the architecture. The usual
 * practice is to create a component in your own architecture by extending this
 * class and implementing the handle method.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Component extends Brick implements IComponent,
		java.io.Serializable {
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 6689888742317866000L;

	private transient Vector<IPort> ports = new Vector<IPort>(IComponent.CONNS_PER_COMPONENT);

	private AbstractImplementation implementation;

	/**
	 * This constructor creates an component with default parameters.
	 */
	public Component() {
		super();
	}

	/**
	 * This constructor sets the name of this component to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this component. It cannot be a null value.
	 */
	public Component(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this component to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this component.
	 */
	public Component(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this component to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this component.
	 */
	public Component(AbstractScaffold scaffold) {
		super(scaffold);
	}

	/**
	 * Initializes the name, style, and scaffold of this component.
	 * 
	 * @param name
	 *            The name of this component.
	 * @param style
	 *            The style of this component.
	 * @param scaffold
	 *            The scaffold of this component.
	 */
	public Component(String name, int style, AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	public void start() {
		if (this.implementation != null) {
			this.implementation.start();
		}
		
		for (IPort aPort : this.ports) {
			aPort.start();
		}
		
	}

	public void setImplementation(AbstractImplementation impl) {
		this.implementation = impl;
		impl.setAssociatedComponent(this);
	}

	public AbstractImplementation getImplementation() {
		return this.implementation;
	}

	/**
	 * This method sends the event up/down the Prism architecture that this
	 * component is a part of.
	 * 
	 * @param e
	 *            a Event to be sent to the Brick above/below.
	 */
	public void send(Event e) {
		e.setOriginatingBrick(this);

		boolean wasEventTypeMatched = false;
		for (int i = 0; i < this.ports.size(); i++) {
			IPort thisPort = (IPort) this.ports.elementAt(i);
			if (thisPort.getPortType() == e.getEventType()
					|| thisPort.getPortType() == PrismConstants.REQUEST_REPLY) {
				wasEventTypeMatched = true;
				if (thisPort instanceof ExtensiblePort) {
					Event e1 = e.replicate();
					e1.setHandlingBrick((Brick) thisPort);
					this.add(e1);
				} else if (thisPort instanceof Port) {
					Event e1 = e.replicate();
					IPort mutualPort = thisPort.getMutualPort();
					e1.setHandlingBrick(mutualPort.getParentBrick());
					this.add(e1);
				}
			} 
		}
		
		if (PrismConstants.DEBUG_MODE && !wasEventTypeMatched) {
			System.out.println("The component could not send the event \""
					+ e.getName() + "\" " + e
					+ ".\n No matching outgoing port type was found.");
		}
	}

	/**
	 * Handling of Event. This is application specific code and should be
	 * implemented by subclasses of this class.
	 * 
	 * @param e
	 *            Event to be handled
	 */
	public void handle(Event e) {
		if (this.implementation != null) {
			this.implementation.handle(e);
		}
	}

	/**
	 * Adds a port to this component.
	 * 
	 * @param port
	 *            Port to be added.
	 */
	public void addPort(IPort port) {
		port.setParentBrick(this);
		this.ports.addElement(port);
	}

	/**
	 * Removes a port from this component.
	 * 
	 * @param port
	 *            Port to be removed.
	 */
	public void removePort(IPort port) {
			this.ports.removeElement(port);
			port.setParentBrick(null);
	}

	public Vector<IPort> getPorts() {
		return this.ports;
	}
}