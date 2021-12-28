package Prism.core;

/**
 * This interface is implemented by any object that wants to represent a brick in
 * Prism.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IBrick {

	/**
	 * By default a brick does not do anything. The start method is called to
	 * activate this brick
	 */
	public abstract void start();

	/**
	 * Add a message to the scaffold which results in a message getting added to
	 * the scaffold's queue.
	 * 
	 * @param event
	 *            Event object to be added
	 */
	public abstract void add(Event event);

	/**
	 * This abstract method needs to be overriden by any object that extends
	 * Brick. The subclassing object shall provide the desired implementation to
	 * handle an Event.
	 * 
	 * @param eEvent
	 *            object to be handled
	 */
	public abstract void handle(Event e);

	public abstract void setScaffold(AbstractScaffold scaffold);

	public abstract AbstractScaffold getScaffold();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract int getStyle();

}