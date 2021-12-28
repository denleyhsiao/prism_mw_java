package Prism.test.style.pipefilter;

import Prism.core.*;

public class ReceiverFilter extends AbstractImplementation 
{
	public void handle(Event e)
	{
		System.out.println("got the event");
		Event e1=new Event("this one should not be received by the sender");
		e1.eventType = PrismConstants.REQUEST;
		send(e1);
	}
}
