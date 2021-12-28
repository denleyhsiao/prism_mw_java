package Prism.test.extensible_port;

import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.extensions.port.ExtensiblePort;
import Prism.extensions.port.distribution.SocketDistribution;
import Prism.test.components.GUI;

public class testClientWithExtensiblePort
{
	public static void main(String argv[])
	{
            String hostName = "localhost";
            int portNum = 2601;

            // Create an architecture for the calculator client.
			Architecture clientArchitecture = new Architecture();
			
			// Create the GUI component.
			Component guiComponent = new Component();
			guiComponent.setImplementation(new GUI());
			
			// Add a port to the GUI for sending requests.
			Port guiRequestPort = new Port(PrismConstants.REQUEST);
			guiComponent.addPort(guiRequestPort);
			
			// Add the GUI to the calculator architecture.
			clientArchitecture.add(guiComponent);
	
			// Create a connector for the calculator client.
			Connector clientConnector = new Connector();
			
			// Add a port to the connector for receiving requests.
			Port connReplyPort = new Port(PrismConstants.REPLY);
			clientConnector.addPort(connReplyPort);			
	
			// Add a port to the connector for forwarding requests to the calculator server.
	        ExtensiblePort connRequestPort = new ExtensiblePort (PrismConstants.REQUEST);
			connRequestPort.addDistribution(new SocketDistribution());           
            clientConnector.addPort(connRequestPort);
            
            // Add the client connector to the calculator architecture.
			clientArchitecture.add(clientConnector);
			
			// Weld the GUI request port to the connector reply port.
			clientArchitecture.weld(guiRequestPort, connReplyPort);

			// Start the architecture.
			clientArchitecture.start();

			// Connect the connector request port to the calculator server.
	        connRequestPort.connect(hostName, portNum);
	}
}