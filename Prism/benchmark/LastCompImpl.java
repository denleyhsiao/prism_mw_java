package Prism.benchmark;

import Prism.core.*;

public class LastCompImpl extends AbstractImplementation
{

	public void handle(Event e)
	{
		Event e1 = new Event ("got_it");
		e1.eventType = PrismConstants.REPLY;	
		send(e1); 		
	}
}
