package Prism.extensions.component;

import Prism.core.AbstractScaffold;
import Prism.core.Component;
import Prism.core.Event;
import Prism.core.IArchitecture;
import Prism.extensions.component.synchronism.AbstractCompSynchronism;

public class ExtensibleComponent extends Component {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 3412566939541775011L;

	private AbstractCompSynchronism compSynchronism = null;

	private IArchitecture architecture = null;

	/**
	 * This constructor creates an component with default parameters.
	 */
	public ExtensibleComponent() {
		super();
	}

	/**
	 * This constructor sets the name of this component to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this component. It cannot be a null value.
	 */
	public ExtensibleComponent(String name) {
		super(name);
	}

	/**
	 * This constructor sets the style of this component to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this component.
	 */
	public ExtensibleComponent(int style) {
		super(style);
	}

	/**
	 * This constructor sets the scaffold of this component to
	 * <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this component.
	 */
	public ExtensibleComponent(AbstractScaffold scaffold) {
		super(scaffold);
	}

	/**
	 * Initializes the name, style, and scaffold of this component.
	 * 
	 * @param name
	 *            The name of this component.
	 * @param style
	 *            The style of this component.
	 * @param scaffold
	 *            The scaffold of this component.
	 */
	public ExtensibleComponent(String name, int style, AbstractScaffold scaffold) {
		super(name, style, scaffold);
	}

	public void addCompSynchronism(AbstractCompSynchronism compSynchronism) {
		this.compSynchronism = compSynchronism;
	}

	public void send(Event e) {
		if (this.compSynchronism != null) {
			e = this.compSynchronism.lock(e);
			if (e == null) {
				return;
			}
		}
		super.send(e);
	}

	public void handle(Event e) {
		if (this.compSynchronism != null) {
			if (this.compSynchronism.unlock(e)) {
				this.getImplementation().handle(e);
			}
		} else {
			this.getImplementation().handle(e);
		}
	}

	public void setArchitecture(IArchitecture arch) {
		this.architecture = arch;
	}

	public IArchitecture getArchitecture() {
		return this.architecture;
	}

}
