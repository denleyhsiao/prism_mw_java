package Prism.core;

import java.util.Vector;

/**
 * Event represents the basic message that is used by Bricks to communicate.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Event implements java.io.Serializable {

	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 4503842147292820054L;

	private String name;

	private Vector<String> parameterNames = null;

	private Vector<Object> parameterValues = null;

	/**
	 * Brick that created this event
	 */
	private transient IBrick originatingBrick;

	/**
	 * Brick through which this event should be sent
	 */
	private transient IBrick handlingBrick;

	/**
	 * Event type. Could be a request or reply.
	 */
	private int eventType;

	private final static int EVENT_INITIAL_LENGTH = 2;

	public Event(int eventType) {
		this.eventType = eventType;
	}

	public Event(String name, int eventType) {
		this.name = name;
		this.eventType = eventType;
	}

	public Event(String name, int eventType, Brick originatingBrick,
			IConnector handlingBrick) {
		this.name = name;
		this.eventType = eventType;
		this.originatingBrick = originatingBrick;
		this.handlingBrick = (Brick) handlingBrick;

	}

	/**
	 * Adds a name-value pair to the event object.
	 * 
	 * @param name
	 *            String name of the value being stored
	 * @param value
	 *            Object that contains a Java object for the value
	 */
	public void addParameter(String name, Object value) {
		if (this.parameterNames == null) {
			this.parameterNames = new Vector<String>(Event.EVENT_INITIAL_LENGTH);
			this.parameterValues = new Vector<Object>(Event.EVENT_INITIAL_LENGTH);
		}
		this.parameterNames.addElement(name);
		this.parameterValues.addElement(value);
	}

	/**
	 * Checks to see if the event contains a name-value pair identified by name.
	 * 
	 * @return Boolean true if the event contains parameter Name and false if
	 *         not.
	 */
	public boolean hasParameter(String name) {
		if (this.parameterNames.contains(name)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets a parameter from the event object.
	 * 
	 * @param name
	 *            String identifier for the name-value pair being read
	 * @return Object The value being requested. If not found, null is returned
	 */
	public Object getParameter(String name) {
		if (this.parameterNames.contains(name)) {
			return this.parameterValues.elementAt(this.parameterNames.indexOf(name));
		}

		return null;
	}

	/**
	 * Removes a name-value pair from the event object.
	 * 
	 * @param name
	 *            String the name of the value being removed from the message
	 */
	public void removeParameter(String name) {
		if (this.parameterNames.contains(name)) {
			this.parameterValues.removeElementAt(this.parameterNames.indexOf(name));
			this.parameterNames.removeElement(name);
		}
	}

	/**
	 * Creates a new Event object that is the exact replica of this Event
	 * object.
	 * 
	 * @return Event new cloned Event object
	 */
	public Event replicate() {
		Event e1 = new Event(this.name, this.eventType);
		e1.parameterNames = this.parameterNames;
		e1.parameterValues = this.parameterValues;
		e1.originatingBrick = this.originatingBrick;
		e1.eventType = this.eventType;
		return e1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setParameterNames(Vector<String> parameterName) {
		this.parameterNames = parameterName;
	}

	public Vector<String> getParameterNames() {
		return this.parameterNames;
	}

	public void setParameterValues(Vector<Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public Vector<Object> getParameterValues() {
		return this.parameterValues;
	}

	public void setOriginatingBrick(IBrick originatingBrick) {
		this.originatingBrick = originatingBrick;
	}

	public IBrick getOriginatingBrick() {
		return originatingBrick;
	}

	public void setHandlingBrick(IBrick handlingBrick) {
		this.handlingBrick = handlingBrick;
	}

	public IBrick getHandlingBrick() {
		return handlingBrick;
	}

	public int getEventType() {
		return eventType;
	}

}
