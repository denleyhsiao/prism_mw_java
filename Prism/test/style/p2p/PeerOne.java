package Prism.test.style.p2p;

import Prism.core.*;

public class PeerOne extends AbstractImplementation 
{
	public void start()
	{
		Event e1=new Event("Comp One event");
		e1.eventType = PrismConstants.REQUEST;
		send(e1);	
	}
	public void handle(Event e)
	{
		System.out.println("got event " + e.name);
	}	
}
