package Prism.extensions.connector;

import Prism.core.AbstractScaffold;
import Prism.core.Connector;
import Prism.core.Event;
import Prism.extensions.connector.handler.AbstractHandler;

public class ExtensibleConnector extends Connector {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = -7757202728721913214L;
	
	private AbstractHandler handler;

	/**
	 * This constructor creates an connector with default parameters.
	 */
	public ExtensibleConnector() {
		super();
	}

	/**
	 * This constructor sets the name of this connector to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this connector. It cannot be a null value.
	 */
	public ExtensibleConnector(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this connector to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this connector.
	 */
	public ExtensibleConnector(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this connector to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this connector.
	 */
	public ExtensibleConnector(AbstractScaffold scaffold) {
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
	public ExtensibleConnector(String name, int style, AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	/**
	 * Sets the handler of this connector.
	 * 
	 * @param handler
	 *            Handler object that determines the distribution policy
	 */
	public void addHandler(AbstractHandler handler) {
		this.handler = handler;
		this.handler.setParentConnector(this);
	}

	/**
	 * Gets the IHandler of this connector.
	 * 
	 * @return AbstractHandler Handler object that determines the distribution
	 *         policy
	 */
	public AbstractHandler getEvtHandler() {
		return this.handler;
	}

	/**
	 * This method distributes the incoming event to connected Bricks.
	 * Distribution policy depends on the type of IHandler that is installed.
	 * 
	 * @param e
	 *            Incoming Event
	 */
	public void handle (Event e)
	{
		if (this.handler != null)
		{
			this.handler.handle(e);
		}
		else
		{
			super.handle(e);
		}
	}

}
