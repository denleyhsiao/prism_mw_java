package Prism.benchmark;

import Prism.core.*;

class Demo3 
{
	static public void main(String argv[]) 
	{
		
				int numberOfComps = 100001, numberOfConns = 100000, queueSize = 1000, threadCount = 10, messageCount = 1;
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
				Component[] comps = new Component[numberOfComps];
				Connector[] conns = new Connector[numberOfConns];
				Port[] compsReqPorts = new Port[numberOfComps];
				Port[] compsRepPorts = new Port[numberOfComps];
				Port[] connsReqPorts = new Port[numberOfConns];
				Port[] connsRepPorts = new Port[numberOfConns];
				
				FIFOScheduler sched = new FIFOScheduler(queueSize);
				Scaffold s = new Scaffold();
				RRobinDispatcher disp = new RRobinDispatcher(sched,threadCount);
				s.dispatcher=disp;
				s.scheduler=sched;

				Architecture arch = new Architecture("Demo");                
				arch.scaffold=s;
                                
		        
				for(i = 0; i < numberOfComps; i++)
				{
					if (i==0)
					{	
						FirstCompImpl firstCompImpl = new FirstCompImpl();
						comps[i] = new Component("Comp"+i, firstCompImpl);
						comps[i].scaffold=s;				               
						arch.add(comps[i]);
						compsReqPorts[i] = new Port("Port"+i, PrismConstants.REQUEST);
						comps[i].addCompPort(compsReqPorts[i]);
						conns[i] = new Connector ("Conn"+i);
						conns[i].scaffold = s;
						arch.add(conns[i]);
						connsRepPorts[i] = new Port("Port"+i, PrismConstants.REPLY);
						conns[i].addConnPort(connsRepPorts[i]);
						arch.weld(compsReqPorts[i], connsRepPorts[i]);
					}
					else if (i == numberOfComps-1)
					{
						LastCompImpl lastCompImpl = new LastCompImpl();
						comps[i] = new Component("Comp"+i, lastCompImpl);
						comps[i].scaffold = s;
						arch.add(comps[i]);	
						compsRepPorts[i] = new Port("Port"+i, PrismConstants.REPLY);
						comps[i].addCompPort(compsRepPorts[i]);
						connsReqPorts[i-1] = new Port("Port"+i,  PrismConstants.REQUEST);
						conns[i-1].addConnPort(connsReqPorts[i-1]);
						arch.weld(connsReqPorts[i-1], compsRepPorts[i]);						
					}
					else
					{	
						// Create Middle Comp
						MiddleCompImpl middleCompImpl = new MiddleCompImpl();
						comps[i] = new Component("Comp"+i, middleCompImpl);
						comps[i].scaffold = s;
						arch.add(comps[i]);

						// Comps Request & Reply Ports
						compsReqPorts[i] = new Port("Port"+i, PrismConstants.REQUEST);
						comps[i].addCompPort(compsReqPorts[i]);						
						compsRepPorts[i] = new Port("Port"+i, PrismConstants.REPLY);
						comps[i].addCompPort(compsRepPorts[i]);

						// Create Top Connector
						conns[i] = new Connector("Conn"+i);
						arch.add(conns[i]);
						conns[i].scaffold = s;

						// Create Top Connector's Reply Port
						connsRepPorts[i] = new Port("Port"+i, PrismConstants.REPLY);
						conns[i].addConnPort(connsRepPorts[i]);

						// create Bottom Connector's Request Port
						connsReqPorts[i-1] = new Port("Port"+i, PrismConstants.REQUEST);
						conns[i-1].addConnPort(connsReqPorts[i-1]);
						
						arch.weld(connsReqPorts[i-1], compsRepPorts[i]);
						arch.weld(compsReqPorts[i], connsRepPorts[i]);	
					}
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
