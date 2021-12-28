package Prism.test.style.pipefilter;

import Prism.core.*;

public class SenderFilter extends AbstractImplementation 
{
	public void start()
	{
		Event e=new Event("here it is");
		System.out.println("Sender filter sending the event");
		e.eventType = PrismConstants.REQUEST;
		send(e);
	}
	
	public void handle(Event e)
	{}
}
