package Prism.benchmark;

import Prism.core.*;

class BottomImpl extends AbstractImplementation
{
	public long startTime;
	public long endTime;
	private int eventCount;
	private int numComps;
	private int called;

	public BottomImpl(int n,int nc)
	{
		eventCount = n + 1;
		called=0;
		numComps=nc;
	}

    public void start()
	{
		startTime = System.currentTimeMillis();
		
		Event new_r = new Event ("do");
        new_r.eventType = PrismConstants.REQUEST;
        		
		for (int i=1;i<eventCount;i++)
		{
             	//if (i%10 == 0)
                	//System.out.println("Sending out " + i);
        		send (new_r);
		}
	}

	public void handle(Event e)
	{
		++called;

		if (called==numComps)
		{
                    endTime = System.currentTimeMillis();
                    System.out.println("Time taken\t"+

                    (endTime - startTime) + " ms");
                    System.exit(0); 
		}
	}

	
}



