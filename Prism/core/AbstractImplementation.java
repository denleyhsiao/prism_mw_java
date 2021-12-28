package Prism.core;

public abstract class AbstractImplementation {
	private IComponent associatedComp;

	public void start() {
	}

	public abstract void handle(Event e);
	
	public void send(Event e) {
		if (this.associatedComp != null) {
			this.associatedComp.send(e);
		}
	}

	public void setAssociatedComponent(IComponent comp) {
		this.associatedComp = comp;
	}

	public IComponent getAssociatedComponent() {
		return this.associatedComp;
	}

}
