package Prism.test.style.c2;

import Prism.core.*;
import Prism.style.*;
import Prism.extensions.architecture.*;
import Prism.extensions.component.*;
import Prism.extensions.connector.*;

public class C2test 
{
	public static void main(String[] args) 
	{
	
			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;

			ExtensibleArchitecture arch = StyleFactory.generateArchitecture("Arch", PrismConstants.C2_ARCH);
			arch.scaffold=s;
			      
			Addition addition = new Addition();
			ExtensibleComponent t = StyleFactory.generateComponent("Add", PrismConstants.C2_COMP, addition);
			t.scaffold=s;
		
			Subtract subtract = new Subtract();
			ExtensibleComponent sub = StyleFactory.generateComponent("Sub", PrismConstants.C2_COMP, subtract);
			sub.scaffold=s;
			
			GUI gui = new GUI();
			ExtensibleComponent b = StyleFactory.generateComponent("GUI", PrismConstants.C2_COMP, gui);
			b.scaffold=s;
			
			ExtensibleConnector conn1 = StyleFactory.generateConnector("conn1", PrismConstants.C2_CONN);
			conn1.scaffold =s;
			

			arch.add(b);
			arch.add(conn1);
			arch.add(t);
			arch.add(sub);
		
			arch.weld(t, conn1);
			arch.weld(sub, conn1);
			arch.weld( conn1, b);						
      
			disp.start();
			
			arch.start();
	}
}