package Prism.benchmark;

import Prism.core.*;

class Demo 
{
	static public void main(String argv[]) 
	{
		
				int numberOfComps = 100, queueSize = 1000, threadCount = 10, messageCount = 100000;
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
				
				//Event [] evts = new Event[100];
				//for (int i=0; i< 100; i++)
				//	evts[i] = new Event("x");
				
				Event x = new Event("x");
				x.addParameter("sabooli", new Integer(2));
				//Component x = new Component("x");
				//Component y = new Component("y");
				//Port x = new Port("x", PrismConstants.REPLY);

				/*
				//-----------------------------------
				// Architecture initialization begins
				//-----------------------------------
                int i;
                Component[] topComps;
                Port[] topCompsPorts;
                Port[] connReqPorts;
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
                
                Connector conn1 = new Connector("conn1");
                conn1.scaffold =s;                
                arch.add(conn1);                
				arch.add(b);
				Port conn1ReplyPort = new Port("conn1ReplyPort", PrismConstants.REPLY);
				conn1.addConnPort(conn1ReplyPort);
				Port bRequestPort = new Port("bRequestPort", PrismConstants.REQUEST);
				b.addCompPort(bRequestPort);
		        arch.weld(conn1ReplyPort, bRequestPort);
		        
		        topComps = new Component[numberOfComps];
		        topCompsPorts = new Port[numberOfComps];
		        connReqPorts = new Port[numberOfComps];
				for(i = 0; i < numberOfComps; i++)
				{
					TopImpl topImpl = new TopImpl(messageCount);
					topComps[i] = new Component("Top "+(i+1), topImpl);
					topCompsPorts[i] = new Port("topCompsPorts", PrismConstants.REPLY); 
					topComps[i].addCompPort(topCompsPorts[i]);
					topComps[i].scaffold =s;
                    arch.add(topComps[i]);
                    
                    connReqPorts[i] = new Port("connReqPorts", PrismConstants.REQUEST);
                    conn1.addConnPort(connReqPorts[i]);
                    arch.weld(topCompsPorts[i], connReqPorts[i]);                    
				}
				*/
				
		
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
		
				//disp.start();
				//arch.start();
		
				//-----------------------------------
				// Architecture execution ends
				//-----------------------------------
		
	}
}
