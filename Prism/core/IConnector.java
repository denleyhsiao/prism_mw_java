package Prism.core;

import java.util.Vector;

/**
 * This interface needs to be implemented by any Prism connector.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IConnector extends IBrick {
	/**
	 * This method distributes the incoming event to connected Bricks.
	 * Distribution policy depends on the type of IHandler that is installed.
	 * 
	 * @param e
	 *            Incoming Event
	 */
	public void handle(Event e);

	/**
	 * Adds a port.
	 * 
	 * @param port
	 *            Port to be added.
	 */
	public abstract void addPort(IPort b);

	/**
	 * Removes a port.
	 * 
	 * @param port
	 *            Port to be removed.
	 */
	public abstract void removePort(IPort b);

	/**
	 * This method returns a list of all available ports.
	 * 
	 * @return Vector List of available ports
	 */
	public abstract Vector<IPort> getPorts();

	public static final int INITIAL_SIZE_OF_BRICK_QUEUE = 5;
}
