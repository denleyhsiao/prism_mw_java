package Prism.extensions.connector.handler;

import java.util.Vector;

import Prism.core.Brick;
import Prism.core.Event;
import Prism.core.IConnector;
import Prism.core.IPort;
import Prism.core.PrismConstants;

public class PipeFilterHandler extends AbstractHandler {
	private Vector<IPort> ports;

	private IConnector parentConnector;

	public void handle(Event e) {
		if (e.getEventType() == PrismConstants.REQUEST) {

			this.ports = this.parentConnector.getPorts();
			e.setHandlingBrick((Brick) this.parentConnector);

			for (IPort aPort : this.ports) {
				if (aPort.getPortType() == PrismConstants.REQUEST) {
					aPort.handle(e);
				}
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
