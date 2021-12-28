package Prism.test.style.pubsub;

import Prism.core.*;
import Prism.style.*;
import Prism.extensions.architecture.*;
import Prism.extensions.component.*;
import Prism.extensions.connector.*;

public class testPubSub 
{
	public static void main(String[] args) 
	{

			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;

			ExtensibleArchitecture arch = StyleFactory.generateArchitecture("Arch", PrismConstants.PUB_SUB_ARCH);			
			arch.scaffold=s;
			
			PubSubComp1 ps1Impl = new PubSubComp1();
			ExtensibleComponent ps1 = StyleFactory.generateComponent("ps1", PrismConstants.PUB_SUB_COMP, ps1Impl);			
			ps1.scaffold=s;			
			
			PubSubComp2 ps2Impl = new PubSubComp2();
			ExtensibleComponent ps2 = StyleFactory.generateComponent("ps2", PrismConstants.PUB_SUB_COMP, ps2Impl);
			ps2.scaffold=s;
			
			ExtensibleConnector conn = StyleFactory.generateConnector("conn", PrismConstants.PUB_SUB_CONN);
			conn.scaffold=s;

			arch.add(conn);
			arch.add(ps2);			
			arch.add(ps1);			

		
			arch.weld(ps1,conn);
			arch.weld(ps2,conn);
			
			disp.start();
			
			arch.start();
			
	}	
}



		

			
		