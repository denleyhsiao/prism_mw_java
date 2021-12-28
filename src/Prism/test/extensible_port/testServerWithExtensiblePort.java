package Prism.test.extensible_port;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;

import Prism.extensions.port.compression.*;
import Prism.handler.*;
import Prism.test.*;

public class testServerWithExtensiblePort
{
public static void main(String argv[])
{
/*	if (argv.length!=1)
	{
		System.out.println("wrong number of arguments");
		System.out.println("The usage is Prism.test.testArchServer portnum");
		System.exit(0);
	}
	else
	{
 */
                int portNum = 2605;
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;

		BasicC2Topology c2Topology = new BasicC2Topology();
		Architecture arch = new Architecture("Demo", c2Topology);
		arch.scaffold=s;

		C2BasicHandler hndle=new C2BasicHandler();
		Connector conn=new Connector("conn", hndle);
		conn.scaffold=s;

                ExtensiblePort ep = new ExtensiblePort(PrismConstants.REPLY, conn);

		SocketDistribution sd=new SocketDistribution(ep, portNum);
//		sd.setListeningPortNum((new Integer(argv[0])).intValue());
		ep.addDistributionModule(sd);

                //Compression compression = new Compression();
                //ep.addCompressionModule(compression);

                //Security security = new Security();
                //ep.addSecurityModule(security);

                ep.scaffold = s;
                conn.addPort(ep);

		AddComponent t = new AddComponent("Add");
		t.scaffold=s;

		arch.add(conn);
		arch.add(t);
                arch.add(ep);

		Port tReplyPort = new Port (PrismConstants.REPLY, t);
		t.addPort(tReplyPort);
		Port connRequestPort = new Port(PrismConstants.REQUEST, conn);
		conn.addPort(connRequestPort);
		arch.weld(tReplyPort, connRequestPort);

		disp.start();
		arch.start();
//	}
}
}