package Prism.test.style.p2p;

import Prism.core.*;
import Prism.style.*;
import Prism.extensions.architecture.*;
import Prism.extensions.component.*;

public class P2PTest 
{
	public static void main(String[] args) {

			FIFOScheduler sched = new FIFOScheduler(100);
			Scaffold s = new Scaffold();
			RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
			s.dispatcher=disp;
			s.scheduler=sched;
						
			ExtensibleArchitecture arch = StyleFactory.generateArchitecture("arch", PrismConstants.P2P_ARCH);
			arch.scaffold=s;
						
			PeerOne peerOneImpl = new PeerOne();
			ExtensibleComponent peerOne = StyleFactory.generateComponent("SF", PrismConstants.P2P_COMP, peerOneImpl);
			peerOne.scaffold=s;
			
			PeerTwo peerTwoImpl = new PeerTwo();
			ExtensibleComponent peerTwo = StyleFactory.generateComponent("RF", PrismConstants.P2P_COMP, peerTwoImpl);
			peerTwo.scaffold=s;
						
			arch.add(peerOne);
			arch.add(peerTwo);
			
			arch.weld(peerOne,peerTwo);
			
			disp.start();
			
			arch.start();
			
	}
}
