package Prism.extensions.port.distribution;

import java.net.InetAddress;

/**
 * Connection object used by the SocketDistribution to connect.
 */

public abstract class AbstractConnection {
	private AbstractDistribution parent;

	public abstract InetAddress getHost();

	public abstract int getPort();

	public AbstractDistribution getParentDistribution() {
		return this.parent;
	}

	public abstract void writeEvent(Object eventObject);

	public abstract void readEvent(Object eventObject);

	public abstract void close();

	public abstract void teardown();

	protected void setParent(AbstractDistribution parent) {
		this.parent = parent;
	}

	protected AbstractDistribution getParent() {
		return this.parent;
	}
}
