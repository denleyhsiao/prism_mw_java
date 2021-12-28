package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;

public class testRealTimeReceiver
{
	public int count;

	public static void main(String [] argv)
  {
    	if (argv.length!=1)
    	{
      		System.out.println("wrong number of arguments");
    	  	System.out.println("The usage is Prism.test.testRealTimeServer portnum");
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

      		Architecture arch = new Architecture("RealTimReceiver");
      		arch.scaffold=s;

      		Connector conn=new Connector("conn");
      		conn.scaffold=s;

            ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REPLY);
            SocketDistribution sd=new SocketDistribution(ep, Integer.parseInt(argv[0]));                
            ep.addDistributionModule(sd);
            ep.scaffold = s;
            conn.addConnPort(ep);

      		testRealTimeReceivingComp receiverImpl = new testRealTimeReceivingComp();
      		Component receiver = new Component("receiver", receiverImpl);
      		receiver.scaffold=s;

      		arch.add(conn);
      		arch.add(receiver);
            arch.add(ep);

            Port receiverReplyPort = new Port("receiverReplyPort", PrismConstants.REPLY);
			receiver.addCompPort(receiverReplyPort);
			Port connRequestPort = new Port("connRequestPort", PrismConstants.REQUEST);
			conn.addConnPort(connRequestPort);
      		arch.weld(receiverReplyPort, connRequestPort);

      		disp.start();
      		arch.start();

     	} // end of else

  } // end of main


}