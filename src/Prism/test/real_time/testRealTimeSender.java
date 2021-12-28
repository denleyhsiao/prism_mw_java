package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;
import Prism.handler.*;
import Prism.extensions.evt.*;
import Prism.extensions.evt.RealTime.*;


public class testRealTimeSender
{
	public int count;

	public static void main(String [] argv)
  {
    	if (argv.length!=2)
    	{
      		System.out.println("wrong number of arguments");
      		System.out.println("The usage is Prism.test.testRealTimeClient hostname portnum");
      		System.exit(0);
    	}
      else
      {
      		EDFScheduler sched = new EDFScheduler(100);
//          FIFOScheduler sched = new FIFOScheduler(100);
      		Scaffold s = new Scaffold();
      		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
      		s.dispatcher=disp;
      		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
      		Architecture arch = new Architecture("RealTimeSender", c2Topology);
      		arch.scaffold=s;

      		C2BasicHandler hndl=new C2BasicHandler();
      		Connector conn=new Connector("conn", hndl);
      		conn.scaffold=s;
                
                ExtensiblePort ep = new ExtensiblePort(PrismConstants.REQUEST, conn);
      		SocketDistribution sd=new SocketDistribution(ep);
                ep.addDistributionModule(sd);
                ep.scaffold = s;
                conn.addPort(ep);

      		testRealTimeSendingComp sender = new testRealTimeSendingComp("sender");
      		sender.scaffold=s;

      		arch.add(conn);
      		arch.add(sender);
                arch.add(ep);

		Port senderRequestPort = new Port (PrismConstants.REQUEST, sender);
		sender.addPort(senderRequestPort);
		Port connReplyPort = new Port (PrismConstants.REPLY, conn);
		conn.addPort(connReplyPort);
      		arch.weld(senderRequestPort, connReplyPort);

      		disp.start();
        	arch.start();

      		ep.connect(argv[0],(new Integer(argv[1])).intValue());

                try {
                    Thread.sleep(3000);
                }
                    catch (Exception e)
                {}

                sender.test();

     	} // end of else

  } // end of main


}