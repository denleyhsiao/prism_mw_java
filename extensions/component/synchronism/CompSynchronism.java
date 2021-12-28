package Prism.extensions.component.synchronism;

import Prism.core.Event;

public class CompSynchronism extends AbstractCompSynchronism 
{
	private boolean locked = false;
	private double eventID = 0;
	
	public Event lock(Event e)
	{
		if (!locked)
		{
			locked = true;
			eventID = Math.random();
			e.addParameter("EventID", new Double(eventID)); 
			return e;
		}
		return null;
	}
    
	public boolean unlock (Event e)
	{
		if (locked)
		{
			double thisEventID = ((Double)e.getParameter("EventID")).doubleValue();
			if (thisEventID != eventID)
				return false;
		}   
		eventID = 0;
		locked = false;        
		return true;
	}
}
