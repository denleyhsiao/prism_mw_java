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
package Prism.test.core;

import Prism.core.*;
import Prism.test.*;

class testArchLocally
{
	static public void main(String argv[])
	{
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold scaf = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		scaf.dispatcher=disp;
		scaf.scheduler=sched;

		Architecture arch = new Architecture("Demo");
		arch.scaffold=scaf;

		AbstractImplementation addition = new Addition();
		Component addComp = new Component("add", addition);
		addComp.scaffold=scaf;

		AbstractImplementation subtract = new Subtract();
		Component subComp = new Component("Sub", subtract);
		subComp.scaffold=scaf;

		AbstractImplementation gui = new GUI();
		Component guiComp = new Component("GUI", gui);
		guiComp.scaffold=scaf;
		Connector conn = new Connector("conn");
		conn.scaffold =scaf;

		arch.add(guiComp);
		arch.add(conn);
		arch.add(addComp);
		arch.add(subComp);

		Port subReplyPort = new Port("subReplyPort", PrismConstants.REPLY);
		subComp.addCompPort (subReplyPort);
		Port connRequestPort1 = new Port("connRequestPort1", PrismConstants.REQUEST);
		conn.addConnPort(connRequestPort1);
		arch.weld(subReplyPort, connRequestPort1);

		Port addReplyPort = new Port("addReplyPort", PrismConstants.REPLY);
		addComp.addCompPort(addReplyPort);
		Port connRequestPort2 = new Port("connRequestPort2", PrismConstants.REQUEST);
		conn.addConnPort(connRequestPort2);
		arch.weld(addReplyPort, connRequestPort2);

		Port guiRequestPort = new Port ("guiRequestPort", PrismConstants.REQUEST);
		guiComp.addCompPort(guiRequestPort);
		Port connReplyPort1 = new Port("connReplyPort1", PrismConstants.REPLY);
		conn.addConnPort(connReplyPort1);
		arch.weld(guiRequestPort, connReplyPort1);

		disp.start();
		arch.start();
	}
}
