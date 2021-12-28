package Prism.test.extensible_port;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;
import Prism.extensions.port.compression.*;
import Prism.handler.*;
import Prism.test.*;

public class testClientWithExtensiblePort
{
public static void main(String argv[])
{
/*	if (argv.length!=2)
	{
		System.out.println("wrong number of arguments");
		System.out.println("The usage is Prism.test.testArchClient hostname portnum");
		System.exit(0);
	}
	else
	{
 */
                String hostName = "localhost";
                int portNum = 2605;
                
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;
		
		BasicC2Topology c2Topology = new BasicC2Topology();								
		Architecture arch = new Architecture("Demo1", c2Topology);
		arch.scaffold=s;

		C2BasicHandler hndle=new C2BasicHandler();
		Connector conn=new Connector("conn", hndle);
		conn.scaffold=s;

                ExtensiblePort ep = new ExtensiblePort (PrismConstants.REQUEST, conn);

		SocketDistribution sd=new SocketDistribution(ep);    
		ep.addDistributionModule(sd);                

                //Compression compression = new Compression();
                //ep.addCompressionModule(compression);

                //Security security = new Security();
                //ep.addSecurityModule(security);

                ep.scaffold = s;
                conn.addPort(ep);

		GUIComp b = new GUIComp("GUI");
		b.scaffold=s;

		arch.add(conn);
		arch.add(b);
                arch.add(ep);

		Port bRequestPort = new Port(PrismConstants.REQUEST, b);
		b.addPort(bRequestPort);
		Port connReplyPort = new Port(PrismConstants.REPLY, conn);
		conn.addPort(connReplyPort);
		arch.weld(bRequestPort, connReplyPort);

		disp.start();
		arch.start();
                
            ep.connect(hostName, portNum);
                
//		ep.connect(argv[0],new Integer(argv[1]).intValue());
//	}
}
}