package Prism.test.style.clientserver;

import Prism.core.*;
import Prism.style.*;
import Prism.extensions.architecture.*;
import Prism.extensions.component.*;

public class ClientServerTest 
{    
	public static void main(String[] args) 
        {
                FIFOScheduler sched = new FIFOScheduler(100);
                Scaffold s = new Scaffold();
                RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
                s.dispatcher=disp;
                s.scheduler=sched;
                ExtensibleArchitecture arch = StyleFactory.generateArchitecture("arch", PrismConstants.CLIENT_SERVER_ARCH);                                
                arch.scaffold = s;
                
                AbstractImplementation clientImpl = new Client();              
                ExtensibleComponent client = StyleFactory.generateComponent("client", PrismConstants.CLIENT, clientImpl);                
                client.scaffold = s;
                
                AbstractImplementation serverImpl = new Server();
                ExtensibleComponent server = StyleFactory.generateComponent("server", PrismConstants.SERVER, serverImpl);
                server.scaffold = s;
                
                arch.add(client);
                arch.add(server);
                arch.weld(client,server);

                disp.start();

                arch.start();            
        }
}
