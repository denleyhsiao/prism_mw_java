package Prism.core;

import java.util.Vector;

/**
 * Base class for the Prism Architecture. This class is a Brick by itself that
 * can be a part of another Prism Brick. It is responsible for keeping state of
 * the components and connectors that are currently part of this Prism
 * architecture.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Architecture extends Brick implements IArchitecture {
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 1793987560177267531L;
	
	private Vector<IBrick> bricks = new Vector<IBrick>(IArchitecture.INITIAL_SIZE_OF_BRICK_ARRAY);;

	/**
	 * This constructor creates an architecture with default parameters.
	 */
	public Architecture() {
		super();
	}

	/**
	 * This constructor sets the name of this architecture to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this architecture. It cannot be a null value.
	 */
	public Architecture(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this architecture to
	 * <code>style</code>.
	 * 
	 * @param style
	 *            The style of this architecture.
	 */
	public Architecture(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this architecture to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this architecture.
	 */
	public Architecture(AbstractScaffold scaffold) {
		super(scaffold);
	}

	/**
	 * Initializes the name, style, and scaffold of this architecture.
	 * 
	 * @param name
	 *            The name of this architecture.
	 * @param style
	 *            The style of this architecture.
	 * @param scaffold
	 *            The scaffold of this architecture.
	 */
	public Architecture(String name, int style, AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	/**
	 * This method must be called to start the architecture. This method will in
	 * turn call the <code>start()</code> method of each Brick in the
	 * architecture.
	 */
	public void start() {
		for (int i = 0; i < this.bricks.size(); i++) {
			IBrick b = (IBrick) this.bricks.elementAt(i);
			try {
				b.start();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * This method will define the action to be taken by this architecture when
	 * it recieves an event from another Brick attached to it.
	 */
	public void handle(Event e) {
	}

	/**
	 * This method will add a brick instance <code>b</code> to the
	 * architecture.
	 * 
	 * @param b
	 *            A brick object to be added
	 */
	public void add(IBrick b) {
		this.bricks.addElement(b);
	}

	/**
	 * This method removes a brick from the architecture and thereby preventing
	 * it from receiving anymore messages from this architecture. If it is not
	 * found to be a part of this architecture then the method does nothing and
	 * returns.
	 * 
	 * @param b
	 *            A brick object to be removed from the architecture.
	 */
	public void remove(IBrick b) {
		if (this.bricks.contains(b)) {
			this.bricks.removeElement(b);
		}
	}

	/**
	 * This is a generic method for connecting two Ports. It is assumed that the
	 * two ports have already been assigned to another parent Brick
	 * (component/connector) object.
	 * 
	 * @param p1
	 *            First Port
	 * @param p2
	 *            second Port
	 */
	public void weld(IPort p1, IPort p2) {
		p1.setMutualPort(p2);
		p2.setMutualPort(p1);

	}

	/**
	 * This method disconnects two ports.
	 * 
	 * @param p1
	 *            First Port
	 * @param p2
	 *            Second Port
	 */
	public void unweld(IPort p1, IPort p2) {
		p1.setMutualPort(null);
		p2.setMutualPort(null);
	}

	public IBrick getBrickByInstanceName(String instName) {
		IBrick b = null;
		for (int i = 0; i < this.bricks.size(); i++) {
			b = (IBrick) this.bricks.elementAt(i);
			if (b.getName().equals(instName)) {
				return b;
			}
		}
		return null;
	}

	public Vector<IBrick> getBricks() {
		return this.bricks;
	}

}
