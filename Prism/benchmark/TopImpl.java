package Prism.benchmark;

import Prism.core.*;

class TopImpl extends AbstractImplementation
{
	private int called;
	private int evtCount;

	public TopImpl(int evtcnt)

	{
            called=0;
            evtCount=evtcnt;
	}

	public synchronized void handle(Event e)
	{		
            ++called;
            /*
            try {
            	Thread.sleep(50);
            }
            catch (Exception exc)
            {}
            */

            //if (called%10 == 0)
            	//System.out.println("top comp handled " + called);

            if (called==evtCount)
            {
                Event new_n = new Event ("done");
                new_n.eventType = PrismConstants.REPLY;
                send (new_n);
            }
	}
}



