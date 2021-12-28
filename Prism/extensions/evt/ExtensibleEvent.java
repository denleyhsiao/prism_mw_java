package Prism.extensions.evt;

import Prism.core.Brick;
import Prism.core.Event;
import Prism.core.IConnector;
import Prism.extensions.evt.RealTime.AbstractRealTimeEvent;

/**
 * A subclass of Event provides extra capability on top of Event object. Extra
 * capability can be selected by installing the appropriate extension.
 * Installation of appropriate extension can be done by setting the appropriate
 * interface to the implementation of extensions. There are access methods
 * provided to allow installation of these extensions.
 */
public class ExtensibleEvent extends Event implements java.io.Serializable {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = -4992020393296101271L;
	
	private AbstractRealTimeEvent realTimeEvent = null;

	public ExtensibleEvent(int eventType) {
		super(eventType);
	}

	public ExtensibleEvent(String name, int eventType) {
		super(name, eventType);
	}

	public ExtensibleEvent(String name, int eventType, Brick originatingBrick,
			IConnector handlingBrick) {
		super(name, eventType, originatingBrick, handlingBrick);
	}

	/**
	 * Installs the real time extension.
	 * 
	 * @param realtimeEvent
	 *            The real time extension object
	 */

	public void addRealTimeEvent(AbstractRealTimeEvent realTimeEvent) {
		this.realTimeEvent = realTimeEvent;
	}

	/**
	 * Return the installed real time extension object.
	 * 
	 * @return IRealTime The real time extension object
	 */
	public AbstractRealTimeEvent getRealTime() {
		return this.realTimeEvent;
	}

	/**
	 * Creates a clone of this ExtensibleEvent.
	 * 
	 * @return Event Clone of this event
	 */
	public Event replicate() {
		ExtensibleEvent eventClone = new ExtensibleEvent(super.getName(),
				super.getEventType());

		eventClone.setParameterNames(super.getParameterNames());
		eventClone.setParameterValues(super.getParameterValues());
		eventClone.setOriginatingBrick(super.getOriginatingBrick());
		eventClone.setHandlingBrick(super.getHandlingBrick());
		eventClone.addRealTimeEvent(this.realTimeEvent);

		return eventClone;
	}

}
