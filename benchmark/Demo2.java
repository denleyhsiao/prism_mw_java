package Prism.benchmark;

import Prism.core.*;

class Demo2 
{
	static public void main(String argv[]) 
	{
		
				int numberOfComps = 500, queueSize = 100, threadCount = 1, messageCount = 1;
				/*
				try
				{
					numberOfComps = Integer.parseInt(argv[0]);
					System.out.println("Component count\t" + numberOfComps);
					queueSize = Integer.parseInt(argv[1]);
					System.out.println("Queue size\t" + queueSize);
					threadCount = Integer.parseInt(argv[2]);
					System.out.println("Thread count\t" + threadCount);
					messageCount = Integer.parseInt(argv[3]);
					System.out.println("Message count\t" + messageCount);
				}
				catch (Exception e)
				{
					System.out.println("Invalid parameters");
					System.exit(0);
				}
				*/
				Runtime rt = Runtime.getRuntime();
				System.gc();
				long initMemory = rt.freeMemory();
				System.out.println("Initial memory " + initMemory);
				long totalMemory = rt.totalMemory();
				System.out.println("Initial total memory " + totalMemory);
		
				//-----------------------------------
				// Architecture initialization begins
				//-----------------------------------
                int i;
                Component[] topComps;
                Port[] topCompsPorts;
                Port[] bottomCompPorts;                                
                FIFOScheduler sched = new FIFOScheduler(queueSize);
                Scaffold s = new Scaffold();
                RRobinDispatcher disp = new RRobinDispatcher(sched,threadCount);
                s.dispatcher=disp;
                s.scheduler=sched;

                Architecture arch = new Architecture("Demo");                
                arch.scaffold=s;

                BottomImpl bImpl = new BottomImpl(messageCount,numberOfComps);
                Component b = new Component("Bottom", bImpl);                
                b.scaffold=s;         
                arch.add(b);

		        topComps = new Component[numberOfComps];
		        topCompsPorts = new Port[numberOfComps];
		        bottomCompPorts = new Port[numberOfComps];
				for(i = 0; i < numberOfComps; i++)
				{
					TopImpl thisImpl = new TopImpl(messageCount);
					topComps[i] = new Component("Top "+(i+1), thisImpl);
					topCompsPorts[i] = new Port("TopPort", PrismConstants.REPLY); 
					topComps[i].addCompPort(topCompsPorts[i]);
					topComps[i].scaffold =s;
                    arch.add(topComps[i]);
                    
                    bottomCompPorts[i] = new Port("BottomPort", PrismConstants.REQUEST);
                    b.addCompPort(bottomCompPorts[i]);
                    arch.weld(topCompsPorts[i], bottomCompPorts[i]);                    
				}
		
		
				//-----------------------------------
				// Architecture initialization ends
				//-----------------------------------
				System.gc();
				long remainingMemory = rt.freeMemory();
				System.out.println("Final memory " + remainingMemory);
				totalMemory = rt.totalMemory();
				System.out.println("Final total memory " + totalMemory);
				System.out.println("Memory used\t" + (initMemory - remainingMemory) +" bytes");
		
				//-----------------------------------
				// Architecture execution starts
				//-----------------------------------
		
				disp.start();
				arch.start();
		
				//-----------------------------------
				// Architecture execution ends
				//-----------------------------------
		
	}
}
