package Prism.benchmark;

import Prism.core.*;

public class FirstCompImpl extends AbstractImplementation
{
	public long startTime;
	public long endTime;
	
	public void start ()
	{
		startTime = System.currentTimeMillis();
		
		Event e = new Event ("do");
		e.eventType = PrismConstants.REQUEST;	
		send(e); 		
	}
	public void handle(Event e)
	{
		endTime = System.currentTimeMillis();
		System.out.println("Time taken\t"+

		(endTime - startTime) + " ms");
		System.exit(0); 		
	}
 
}
