package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;
import Prism.handler.*;
import Prism.extensions.evt.*;
import Prism.extensions.evt.RealTime.*;


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

		BasicC2Topology c2Topology = new BasicC2Topology();
      		Architecture arch = new Architecture("RealTimReceiver", c2Topology);
      		arch.scaffold=s;

      		C2BasicHandler hndl=new C2BasicHandler();
      		Connector conn=new Connector("conn", hndl);
      		conn.scaffold=s;

                ExtensiblePort ep = new ExtensiblePort(PrismConstants.REPLY, conn);
                SocketDistribution sd=new SocketDistribution(ep, Integer.parseInt(argv[0]));                
                ep.addDistributionModule(sd);
                ep.scaffold = s;
                conn.addPort(ep);

      		testRealTimeReceivingComp receiver = new testRealTimeReceivingComp("receiver");
      		receiver.scaffold=s;

      		arch.add(conn);
      		arch.add(receiver);
                arch.add(ep);

		Port receiverReplyPort = new Port(PrismConstants.REPLY, receiver);
		receiver.addPort(receiverReplyPort);
		Port connRequestPort = new Port(PrismConstants.REQUEST, conn);
		conn.addPort(connRequestPort);
      		arch.weld(receiverReplyPort, connRequestPort);

      		disp.start();
      		arch.start();


     	} // end of else

  } // end of main


}