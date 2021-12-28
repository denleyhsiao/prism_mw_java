package Prism.test.extensible_port;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;
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
            int portNum = 2601;
	                
			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;
											
			Architecture arch = new Architecture("Demo1");
			arch.scaffold=s;
	
			Connector conn=new Connector("conn");
			conn.scaffold=s;
	
	        ExtensiblePort ep = new ExtensiblePort ("ep", PrismConstants.REQUEST);
	
			SocketDistribution sd=new SocketDistribution(ep);    
			ep.addDistributionModule(sd);                
	
            //Compression compression = new Compression();
            //ep.addCompressionModule(compression);

            //Security security = new Security();
            //ep.addSecurityModule(security);

            ep.scaffold = s;
            conn.addConnPort(ep);
	
            GUI gui = new GUI();
			Component b = new Component("GUI", gui);
			b.scaffold=s;
	
			arch.add(conn);
			arch.add(b);
	        arch.add(ep);
	
			Port bRequestPort = new Port("bRequestPort", PrismConstants.REQUEST);
			b.addCompPort(bRequestPort);
			Port connReplyPort = new Port("connReplyPort", PrismConstants.REPLY);
			conn.addConnPort(connReplyPort);
			arch.weld(bRequestPort, connReplyPort);
	
			disp.start();
			arch.start();
	                
	        ep.connect(hostName, portNum);
	//		ep.connect(argv[0],new Integer(argv[1]).intValue());
	//	}
	}
}