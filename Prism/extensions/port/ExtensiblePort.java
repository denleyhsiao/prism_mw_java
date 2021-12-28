package Prism.extensions.port;

import Prism.core.AbstractScaffold;
import Prism.core.Event;
import Prism.core.IPort;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.extensions.port.compression.AbstractCompression;
import Prism.extensions.port.distribution.AbstractDistribution;

/**
 * A subclass of Port provides extra functionality on top of Port object. Extra
 * capability can be selected by installing the appropriate extension.
 * Installation of appropriate extension can be done by done by setting the
 * appropriate interface to the implementation of extensions. There are access
 * methods provided to allow installation of these extensions.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class ExtensiblePort extends Port {

	/**
	 * Each serializable class needs a serial UID.
	 */
	private static final long serialVersionUID = -6758027736474429L;

	private AbstractDistribution distribution = null;

	private AbstractCompression compression = null;

	/**
	 * This constructor creates an port with default parameters.
	 */
	public ExtensiblePort(int portType) {
		super(portType);
		super.setExtensiblePort(this);
	}

	/**
	 * This constructor sets the name of this port to <code>name</code>.
	 * 
	 * @param name
	 *            The name of this port. It cannot be a null value.
	 */
	public ExtensiblePort(String name, int portType) {
		super(name, portType);
		super.setExtensiblePort(this);
	}

	/**
	 * This constructor sets the style of this port to <code>style</code>.
	 * 
	 * @param style
	 *            The style of this port.
	 */
	public ExtensiblePort(int style, int portType) {
		super(style, portType);
		super.setExtensiblePort(this);
	}

	/**
	 * This constructor sets the scaffold of this port to <code>scaffold</code>.
	 * 
	 * @param scaffold
	 *            The scaffold of this port.
	 */
	public ExtensiblePort(AbstractScaffold scaffold, int portType) {
		super(scaffold, portType);
		super.setExtensiblePort(this);
	}

	/**
	 * Initializes the name, style, and scaffold of this port.
	 * 
	 * @param name
	 *            The name of this port.
	 * @param style
	 *            The style of this port.
	 * @param scaffold
	 *            The scaffold of this port.
	 */
	public ExtensiblePort(String name, int style, AbstractScaffold scaffold,
			int portType) {
		super(name, style, scaffold, portType);
		super.setExtensiblePort(this);
	}

	/**
	 * Returns the installed Compression extension.
	 * 
	 * @return AbstractCompression installed compression extension
	 */
	public AbstractCompression getCompressionModule() {
		return this.compression;
	}

	/**
	 * Calls the start method of all the implemented interfaces in the
	 * appropriate order.
	 */
	public void start() {
		if (this.distribution != null) {
			this.distribution.start();
		}

	}

	/**
	 * The default handler, determines the type of processing needed to be done.
	 * There are two types of processing. Processing event that is incoming and
	 * processing event that is outgoing.
	 * 
	 * @param e
	 *            event
	 */
	public void handle(Event e) {
		if (this.getPortType() == PrismConstants.REQUEST) {
			if (e.getEventType() == PrismConstants.REQUEST) {
				this.handle(e, "OUT");
			} else if (e.getEventType() == PrismConstants.REPLY) {
				this.handle(e, "IN");
			}
		} else if (this.getPortType() == PrismConstants.REPLY) {
			if (e.getEventType() == PrismConstants.REPLY) {
				this.handle(e, "OUT");
			} else if (e.getEventType() == PrismConstants.REQUEST) {
				this.handle(e, "IN");
			}
		}
	}

	/**
	 * Checks the type of extensions installed. Depending on the extensions
	 * installed the appropriate processing is done.
	 * 
	 * @param eventObject
	 *            represents the event
	 * @param direction
	 *            incoming vs outgoing
	 */
	public void handle(Object eventObject, String direction) {
		if (direction.equals("IN")) {
			if (this.compression != null) {
				eventObject = this.compression.processEvent(eventObject,
						direction);
			}

			Event e = (Event) eventObject;
			e.setOriginatingBrick(this);
			e.setHandlingBrick(this.getParentBrick());
			this.add(e);
		} else {
			if (this.compression != null) {
				eventObject = this.compression.processEvent(eventObject,
						direction);
			}
			if (this.distribution != null) {
				this.distribution.writeEvent(eventObject);
			} else {
				IPort tempMutualPort = this.getMutualPort();
				ExtensiblePort mutualExtPort = tempMutualPort
						.getExtensiblePort();
				if (mutualExtPort != null) {
					mutualExtPort.handle(eventObject, direction);
				}
			}
		}
	}

	/**
	 * Installs the distribution extension.
	 * 
	 * @param dist
	 *            distribution extension.
	 */
	public void addDistribution(AbstractDistribution distribution) {
		this.distribution = distribution;
		this.distribution.setParentPort(this);
	}

	/**
	 * Installs the compression extension.
	 * 
	 * @param compression
	 *            compression extension.
	 */
	public void addCompression(AbstractCompression compression) {
		this.compression = compression;
	}

	/**
	 * Method for connecting to a remote host. Also initiates security key
	 * exchange if security extension is installed.
	 * 
	 * @param hostName
	 *            host name or IP address of destination
	 * @param portNum
	 *            port num of destination
	 */
	public void connect(String hostName, int portNum) {
		if (this.distribution != null) {
			this.distribution.connect(hostName, portNum);
		} else {
			System.out
					.println("Usage Error: Add Distribution module before connecting.");
		}
	}

}
