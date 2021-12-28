package Prism.test.extensible_port;

import Prism.core.*;
import Prism.extensions.port.*;
import Prism.extensions.port.distribution.*;
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
	        int portNum = 2601;
			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;
	
			Architecture arch = new Architecture("Demo");
			arch.scaffold=s;
	
			Connector conn=new Connector("conn");
			conn.scaffold=s;

			ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REPLY);
	
			SocketDistribution sd=new SocketDistribution(ep, portNum);
			//sd.setListeningPortNum((new Integer(argv[0])).intValue());
			ep.addDistributionModule(sd);
	
	        //Compression compression = new Compression();
	        //ep.addCompressionModule(compression);
	
	        //Security security = new Security();
	        //ep.addSecurityModule(security);
	
	        ep.scaffold = s;
	        conn.addConnPort(ep);
	
			Addition addition = new Addition();
			Component t = new Component("Add", addition);
			t.scaffold=s;
	
			arch.add(conn);
			arch.add(t);
	        arch.add(ep);
	
			Port tReplyPort = new Port ("tReplyPort", PrismConstants.REPLY);
			t.addCompPort(tReplyPort);
			Port connRequestPort = new Port("connRequestPort", PrismConstants.REQUEST);
			conn.addConnPort(connRequestPort);
			arch.weld(tReplyPort, connRequestPort);
	
			disp.start();
			arch.start();
	//	}
	}
}