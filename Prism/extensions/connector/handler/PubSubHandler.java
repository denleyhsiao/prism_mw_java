package Prism.extensions.connector.handler;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import Prism.core.Event;
import Prism.core.IBrick;
import Prism.core.IConnector;

public class PubSubHandler extends AbstractHandler {

	private IConnector parentConnector;

	private Map<String, Vector<IBrick>> subscriptions = new Hashtable<String, Vector<IBrick>>();

	public PubSubHandler() {
	}

	public synchronized void handle(Event e) {
		String evtName = null;
		if (e.getName().equals("Subscription")) {
			evtName = (String) e.getParameter("EventName");
			Vector<IBrick> v = this.subscriptions.get(evtName);
			if (v == null) {
				v = new Vector<IBrick>();
			}
			v.add(e.getOriginatingBrick());
			this.subscriptions.put(evtName, v);
		} else if (e.getName().equals("Unsubscription")) {
			evtName = (String) e.getParameter("EventName");
			Vector<IBrick> v = this.subscriptions.get(evtName);
			int i = v.indexOf(e.getOriginatingBrick());
			v.remove(i);
			this.subscriptions.put(evtName, v);
		} else {
			Vector<IBrick> v = this.subscriptions.get(e.getName());
			if (v != null) {
				for (IBrick aBrick : v) {
					aBrick.handle(e);
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

	public IConnector getParentConnector() {
		return parentConnector;
	}
}
