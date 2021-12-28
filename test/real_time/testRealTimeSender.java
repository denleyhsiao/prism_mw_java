package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;

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

      		Architecture arch = new Architecture("RealTimeSender");
      		arch.scaffold=s;

      		Connector conn=new Connector("conn");
      		conn.scaffold=s;
                
            ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REQUEST);
      		SocketDistribution sd=new SocketDistribution(ep);
            ep.addDistributionModule(sd);
            ep.scaffold = s;
            conn.addConnPort(ep);

      		testRealTimeSendingComp senderImpl = new testRealTimeSendingComp();
      		Component sender = new Component("sender", senderImpl);
      		sender.scaffold=s;

      		arch.add(conn);
      		arch.add(sender);
            arch.add(ep);

			Port senderRequestPort = new Port ("senderRequestPort", PrismConstants.REQUEST);
			sender.addCompPort(senderRequestPort);
			Port connReplyPort = new Port ("connReplyPort", PrismConstants.REPLY);
			conn.addConnPort(connReplyPort);
      		arch.weld(senderRequestPort, connReplyPort);

      		disp.start();
        	arch.start();

      		ep.connect(argv[0],(new Integer(argv[1])).intValue());

                try {
                    Thread.sleep(3000);
                }
                    catch (Exception e)
                {}

                senderImpl.test();

     	} // end of else

  } // end of main


}