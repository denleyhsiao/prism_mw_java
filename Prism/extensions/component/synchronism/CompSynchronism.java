package Prism.extensions.component.synchronism;

import Prism.core.Event;

public class CompSynchronism extends AbstractCompSynchronism {
	private boolean locked = false;

	private double eventID = 0;

	public Event lock(Event e) {
		if (!this.locked) {
			this.locked = true;
			this.eventID = Math.random();
			e.addParameter("EventID", new Double(this.eventID));
			return e;
		}
		return null;
	}

	public boolean unlock(Event e) {
		if (this.locked) {
			double thisEventID = ((Double) e.getParameter("EventID"))
					.doubleValue();
			if (thisEventID != this.eventID) {
				return false;
			}
		}
		this.eventID = 0;
		this.locked = false;
		return true;
	}
}
