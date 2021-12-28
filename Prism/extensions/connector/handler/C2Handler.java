package Prism.extensions.connector.handler;

import java.util.Vector;

import Prism.core.Brick;
import Prism.core.Event;
import Prism.core.IConnector;
import Prism.core.IPort;

/**
 * C2BasicHandler is an implementation of AbstractHandler for handling events in
 * the manner basic to C2 style connectors handle messages.
 * 
 * @version 0.2
 */
public class C2Handler extends AbstractHandler {
	
	private Vector<IPort> ports;

	private IConnector parentConnector;

	/**
	 * Handles the events in the same way basic C2 connectors handle them
	 * 
	 * @param e
	 *            Event to be handled
	 */
	public void handle(Event e) {
		this.ports = this.parentConnector.getPorts();
		e.setHandlingBrick((Brick) this.parentConnector);

		for (IPort aPort : this.ports) {
			if (aPort.getPortType() == e.getEventType()) {
				aPort.handle(e);
			}
		}
	}

	/**
	 * Sets the connector to which this handler belongs
	 * 
	 * @param conn
	 *            an IConnector to which the handler belongs
	 */
	public void setParentConnector(IConnector conn) {
		this.parentConnector = conn;
	}

}