package Prism.benchmark;

import Prism.core.*;

public class MiddleCompImpl extends AbstractImplementation
{

	public void handle(Event e)
	{
		//System.out.println("received:" + e.name);
		send(e);
	}
}
