package Prism.core;

import java.util.Vector;

/**
 * A connector provides interaction and mediation services to attached
 * components. The connector is connected to a set of components on both sides
 * (a set may be empty). Connectors use the provided AbstractHandler to
 * distribute event among connected Bricks.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */

public class Connector extends Brick implements IConnector {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 58452729097080741L;
	private Vector<IPort> ports = new Vector<IPort>(IConnector.INITIAL_SIZE_OF_BRICK_QUEUE);

	/**
	 * This constructor creates an connector with default parameters.
	 */
	public Connector() {
		super();
	}

	/**
	 * This constructor sets the name of this connector to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this connector. It cannot be a null value.
	 */
	public Connector(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this connector to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this connector.
	 */
	public Connector(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this connector to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this connector.
	 */
	public Connector(AbstractScaffold scaffold) {
		super(scaffold);
	}

	/**
	 * Initializes the name, style, and scaffold of this connector.
	 * 
	 * @param name
	 *            The name of this connector.
	 * @param style
	 *            The style of this connector.
	 * @param scaffold
	 *            The scaffold of this connector.
	 */
	public Connector(String name, int style, AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	/**
	 * This method distributes the incoming event to connected Bricks.
	 * Distribution policy depends on the type of IHandler that is installed.
	 * 
	 * @param e
	 *            Incoming Event
	 */
	public void handle(Event e) {
		// System.out.println("received something");
		e.setHandlingBrick(this);

		Port thisPort;
		for (int i = 0; i < this.ports.size(); i++) {
			thisPort = (Port) this.ports.elementAt(i);
			if (thisPort.getPortType() == e.getEventType()) {
				thisPort.handle(e);
			}
		}
	}

	/**
	 * Adds a port to this connector.
	 * 
	 * @param port
	 *            Port to be added.
	 */
	public void addPort(IPort port) {
		port.setParentBrick(this);
		this.ports.addElement(port);
	}

	/**
	 * Removes a port from this connector.
	 * 
	 * @param port
	 *            Port to be removed.
	 */
	public void removePort(IPort port) {
		if (this.ports != null) {
			this.ports.removeElement(port);
			port.setParentBrick(null);
		}
	}

	public Vector<IPort> getPorts() {
		return this.ports;
	}
	
	@Override
	public void start() {
		super.start();
		for (IPort aPort : this.ports) {
			aPort.start();
		}
	}

}
