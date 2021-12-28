package Prism.test.style.pubsub;

import Prism.core.*;

class PubSubComp2 extends AbstractImplementation 
{
	public void handle(Event e)
	{
		System.out.println("got the event "+ e.name);
	}
	
	public void start()
	{
		subscribe("2");
		
		Event e=new Event("1");
		e.eventType=PrismConstants.REQUEST;
		System.out.println("Sending the event 1");
		send(e);	
	}

	public void subscribe(String evtName)
	{
		Event e = new Event("Subscription");
		e.addParameter("EventName", evtName);
		e.eventType = PrismConstants.REQUEST;
		send(e);
	}	
	
	public void unsubscribe(String evtName)
	{
		Event e=new Event("Unsubscription");
		e.addParameter("EventName", evtName);
		e.eventType = PrismConstants.REQUEST;
		send(e);
	}		
}
