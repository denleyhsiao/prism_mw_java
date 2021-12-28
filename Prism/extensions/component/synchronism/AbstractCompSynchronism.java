package Prism.extensions.component.synchronism;

import Prism.core.Event;

public abstract class AbstractCompSynchronism {
	public abstract Event lock(Event e);

	public abstract boolean unlock(Event e);
}
