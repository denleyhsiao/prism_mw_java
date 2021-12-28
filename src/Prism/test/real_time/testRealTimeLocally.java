package Prism.test.real_time;

import Prism.core.*;
import Prism.handler.*;
import Prism.extensions.evt.*;
import Prism.extensions.evt.RealTime.*;


public class testRealTimeLocally 
{
    public static void main(String [] argv)
    {

      		EDFScheduler sched = new EDFScheduler(100);
      		Scaffold s = new Scaffold();
      		RRobinDispatcher disp = new RRobinDispatcher(sched, 3);
      		s.dispatcher=disp;
      		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
      		Architecture arch = new Architecture("RealTimeSender", c2Topology);
      		arch.scaffold=s;

      		C2BasicHandler hndl=new C2BasicHandler();
      		Connector conn=new Connector("conn", hndl);

      		conn.scaffold=s;
      		arch.add(conn);

      		testRealTimeSendingComp sender = new testRealTimeSendingComp("sender");
      		sender.scaffold=s;

      		testRealTimeReceivingComp receiver = new testRealTimeReceivingComp("receiver");
      		receiver.scaffold=s;          

      		arch.add(sender);
      		arch.add(receiver);

		Port senderRequestPort = new Port(PrismConstants.REQUEST, sender);
		sender.addPort(senderRequestPort);
		Port connReplyPort = new Port(PrismConstants.REPLY, conn);
		conn.addPort(connReplyPort);
      		arch.weld(senderRequestPort, connReplyPort);

		Port receiverReplyPort = new Port(PrismConstants.REPLY, receiver);
		receiver.addPort(receiverReplyPort);
		Port connRequestPort = new Port(PrismConstants.REQUEST, conn);
		conn.addPort(connRequestPort);
      		arch.weld(receiverReplyPort, connRequestPort);

      		disp.start();

        	arch.start();

          try {
            Thread.sleep(2000);
          }
          catch (Exception e)
          {}

          sender.test();

    } // end of main
}
