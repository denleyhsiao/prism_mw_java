			/**
			 * This is the architecture being created
			 * 
			 *  ------     -----
			 *  |adder|    |subt|
			 *  ------     -----
			 *     |         |
			 * ------------------conn1
			 *          |
			 *       ----------
			 *       |gui comp|
			 *       ----------
			 */
package Prism.test;

import Prism.core.*;
import Prism.util.*;

class testArchLocally {
	static public void main(String argv[]) {
		
		
		
			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;

			BasicC2Topology c2Topology = new BasicC2Topology();
			Architecture arch = new Architecture("Demo", c2Topology);
			arch.scaffold=s;
			      
			AddComponent t = new AddComponent("Add");
			t.scaffold=s;
		
			SubComponent sub = new SubComponent("Sub");
			sub.scaffold=s;
			
	 		GUIComp b = new GUIComp("GUI");
			b.scaffold=s;
			Prism.handler.C2BasicHandler  bbc1=new Prism.handler.C2BasicHandler();
			Connector conn1 = new Connector("conn1", bbc1);
			conn1.scaffold =s;
			

			arch.add(b);
			arch.add(conn1);
			arch.add(t);
			arch.add(sub);	

			Port subReplyPort = new Port(PrismConstants.REPLY, sub);
			sub.addPort (subReplyPort);
			Port conn1RequestPort1 = new Port(PrismConstants.REQUEST, conn1);
			conn1.addPort(conn1RequestPort1);
			arch.weld(subReplyPort, conn1RequestPort1);  
                        
			Port tReplyPort = new Port(PrismConstants.REPLY, t);
			t.addPort(tReplyPort);
			Port conn1RequestPort2 = new Port(PrismConstants.REQUEST, conn1);
			conn1.addPort(conn1RequestPort2);
			arch.weld(tReplyPort, conn1RequestPort2);                                             
			
			Port bRequestPort = new Port (PrismConstants.REQUEST, b);
			b.addPort(bRequestPort);
			Port conn1ReplyPort1 = new Port(PrismConstants.REPLY, conn1);
			conn1.addPort(conn1ReplyPort1);
			arch.weld(bRequestPort, conn1ReplyPort1);                         
      
			disp.start();
			
			arch.start();
		
	}
}
				