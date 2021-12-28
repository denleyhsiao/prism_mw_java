package Prism.test.style.pipefilter;

import Prism.core.*;
import Prism.style.*;
import Prism.extensions.architecture.*;
import Prism.extensions.component.*;
import Prism.extensions.connector.*;

public class PipeFilterTest {
	public static void main(String[] args) {

			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;
						
			ExtensibleArchitecture arch = StyleFactory.generateArchitecture("arch", PrismConstants.PIPE_FILTER_ARCH);
			arch.scaffold=s;
						
			SenderFilter sfImpl = new SenderFilter();
			ExtensibleComponent sf = StyleFactory.generateComponent("SF", PrismConstants.FILTER, sfImpl);
			sf.scaffold=s;
			
			ReceiverFilter rfImpl = new ReceiverFilter();
			ExtensibleComponent rf = StyleFactory.generateComponent("RF", PrismConstants.FILTER, rfImpl);
			rf.scaffold=s;
			
			ExtensibleConnector p = StyleFactory.generateConnector("pipe", PrismConstants.PIPE);
			p.scaffold=s;
			
			arch.add(sf);
			arch.add(rf);
			arch.add(p);
			
			arch.weld(sf,p);
			arch.weld(p,rf);
			
			disp.start();
			
			arch.start();
			
	}	
}



		

			
		