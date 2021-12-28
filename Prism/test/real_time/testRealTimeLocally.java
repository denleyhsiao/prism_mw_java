package Prism.test.real_time;

import Prism.core.*;

public class testRealTimeLocally 
{
    public static void main(String [] argv)
    {

      		EDFScheduler sched = new EDFScheduler(100);
      		Scaffold s = new Scaffold();
      		RRobinDispatcher disp = new RRobinDispatcher(sched, 3);
      		s.dispatcher=disp;
      		s.scheduler=sched;

      		Architecture arch = new Architecture("RealTimeSender");
      		arch.scaffold=s;

      		Connector conn=new Connector("conn");

      		conn.scaffold=s;
      		arch.add(conn);

      		testRealTimeSendingComp senderImpl = new testRealTimeSendingComp();
      		Component sender = new Component("sender", senderImpl);
      		sender.scaffold=s;

      		testRealTimeReceivingComp receiverImpl = new testRealTimeReceivingComp();
      		Component receiver = new Component("receiver", receiverImpl);
      		receiver.scaffold=s;          

      		arch.add(sender);
      		arch.add(receiver);

			Port senderRequestPort = new Port("senderRequestPort", PrismConstants.REQUEST);
			sender.addCompPort(senderRequestPort);
			Port connReplyPort = new Port("connReplyPort", PrismConstants.REPLY);
			conn.addConnPort(connReplyPort);
      		arch.weld(senderRequestPort, connReplyPort);

			Port receiverReplyPort = new Port("receiverReplyPort", PrismConstants.REPLY);
			receiver.addCompPort(receiverReplyPort);
			Port connRequestPort = new Port("connRequestPort", PrismConstants.REQUEST);
			conn.addConnPort(connRequestPort);
      		arch.weld(receiverReplyPort, connRequestPort);

      		disp.start();

        	arch.start();

			  try {
			    Thread.sleep(2000);
			  }
			  catch (Exception e)
			  {}

	        senderImpl.test();

    } // end of main
}
