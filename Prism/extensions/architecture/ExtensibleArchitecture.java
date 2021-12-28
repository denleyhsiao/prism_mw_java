package Prism.extensions.architecture;

import Prism.core.AbstractScaffold;
import Prism.core.Architecture;
import Prism.core.IPort;
import Prism.core.PrismException;
import Prism.extensions.architecture.topology.AbstractTopology;

public class ExtensibleArchitecture extends Architecture {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 2126242119975400022L;
	
	private AbstractTopology topology;

	/**
	 * This constructor creates an architecture with default parameters.
	 */
	public ExtensibleArchitecture() {
		super();
	}

	/**
	 * This constructor sets the name of this architecture to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this architecture. It cannot be a null value.
	 */
	public ExtensibleArchitecture(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this architecture to
	 * <code>style</code>.
	 * 
	 * @param style
	 *            The style of this architecture.
	 */
	public ExtensibleArchitecture(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this architecture to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this architecture.
	 */
	public ExtensibleArchitecture(AbstractScaffold scaffold) {
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
	public ExtensibleArchitecture(String name, int style,
			AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	public void addTopology(AbstractTopology topology) {
		this.topology = topology;
	}

	public void weld(IPort p1, IPort p2) {
		try {
			if (this.topology != null) {
				this.topology.weld(p1, p2);
			} else {
				super.weld(p1, p2);
			}
		} catch (PrismException e) {
			new RuntimeException(e);
		}
	}

}
