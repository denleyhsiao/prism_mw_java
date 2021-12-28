package Prism.test.extensible_port;

import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.extensions.port.ExtensiblePort;
import Prism.extensions.port.distribution.SocketDistribution;
import Prism.test.components.Addition;

public class testServerWithExtensiblePort {
	public static void main(String argv[]) {
		int portNum = 2601;

		// Create an architecture for the calculator server.
		Architecture serverArchitecture = new Architecture();

		// Create the Addition component.
		Component additionComponent = new Component();
		additionComponent.setImplementation(new Addition());

		// Add a port to the Addition for receiving requests.
		Port additionReplyPort = new Port(PrismConstants.REPLY);
		additionComponent.addPort(additionReplyPort);

		// Add the Addition to the calculator architecture.
		serverArchitecture.add(additionComponent);

		// Create a connector for the calculator server.
		Connector serverConnector = new Connector();

		// Add a port to the connector for forwarding requests.
		Port connRequestPort = new Port(PrismConstants.REQUEST);
		serverConnector.addPort(connRequestPort);

		// Add a port to the connector for receiving requests from the
		// calculator client.
		ExtensiblePort connReplyPort = new ExtensiblePort(PrismConstants.REPLY);
		connReplyPort.addDistribution(new SocketDistribution(portNum));
		serverConnector.addPort(connReplyPort);

		// Add the server connector to the calculator architecture.
		serverArchitecture.add(serverConnector);

		// Weld the Addition reply port to the connector request port.
		serverArchitecture.weld(additionReplyPort, connRequestPort);

		// Start the architecture.
		serverArchitecture.start();
	}
}