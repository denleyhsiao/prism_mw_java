package Prism.core;

/**
 * Brick is the abstract building block for architectures. It is never used
 * directly, but instantiated as either a component, connector, or port. This
 * class does not have a behavior of its own, but depends on sub classes to
 * handle messages.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class Brick implements java.io.Serializable, IBrick {
	
	/**
	 * Each serializable class needs a serial UID. 
	 */
	private static final long serialVersionUID = 2164943179308548849L;

	private String name;

	private int style;

	private transient AbstractScaffold scaffold = null;

	/**
	 * This constructor creates a brick with default name, style, and scaffold.
	 */
	public Brick() {
		this("", PrismConstants.DEFAULT, new Scaffold());
	}

	/**
	 * A simple constructor to store the brick name.
	 * 
	 * @param name
	 *            Name of the brick.
	 */
	public Brick(String name) {
		this(name, PrismConstants.DEFAULT, new Scaffold());
	}

	/**
	 * A simple constructor to store the brick style.
	 * 
	 * @param style
	 *            Style of the brick.
	 */
	public Brick(int style) {
		this("", style, new Scaffold());
	}

	/**
	 * A simple constructor to store the brick scaffold.
	 * 
	 * @param scaffold
	 *            Scaffold of the brick.
	 */
	public Brick(AbstractScaffold scaffold) {
		this("", PrismConstants.DEFAULT, scaffold);
	}

	/**
	 * Initializes the name, style, and scaffold of the brick.
	 * 
	 * @param name
	 *            Name of the brick.
	 * @param style
	 *            Style of the brick.
	 * @param scaffold
	 *            Scaffold of the brick.
	 */
	public Brick(String name, int style, AbstractScaffold scaffold) {
		this.name = name;
		this.style = style;
		this.scaffold = scaffold;
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#start()
	 */
	public void start() {
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#add(Prism.core.Event)
	 */
	public void add(Event event) {
		if (event.getOriginatingBrick() == null) {
			event.setOriginatingBrick(this);
		}
		if (this.scaffold != null) {
			this.scaffold.call("add", event);
		}
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#handle(Prism.core.Event)
	 */
	public abstract void handle(Event e);

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#setScaffold(Prism.core.AbstractScaffold)
	 */
	public void setScaffold(AbstractScaffold scaffold) {
		this.scaffold = scaffold;
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#getScaffold()
	 */
	public AbstractScaffold getScaffold() {
		return this.scaffold;
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#getName()
	 */
	public String getName() {
		return this.name;
	}

	protected void setStyle(int style) {
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see Prism.core.IBrick#getStyle()
	 */
	public int getStyle() {
		return this.style;
	}

}
