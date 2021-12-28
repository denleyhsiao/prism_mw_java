/**
 * This is the architecture being created
 * 
 *  ------     -----
 *  |adder|    |subt|
 *  ------     -----
 *     |         |
 * ------------------conn1
 *          |
 *       ----------
 *       |gui comp|
 *       ----------
 */
package Prism.test.core;

import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.test.components.*;

class testArchLocally {
	static public void main(String argv[]) {
		// Create an architecture for the calculator.
		Architecture calculatorArchitecture = new Architecture();

		// Create the GUI component.
		Component guiComponent = new Component();
		guiComponent.setImplementation(new GUI());

		// Add a port to the GUI for sending requests.
		Port guiRequestPort = new Port(PrismConstants.REQUEST);
		guiComponent.addPort(guiRequestPort);

		// Add the GUI to the calculator architecture.
		calculatorArchitecture.add(guiComponent);

		// Create the subtraction component.
		Component subtractComponent = new Component();
		subtractComponent.setImplementation(new Subtract());

		// Add a port to the subtraction component for receiving requests.
		Port subReplyPort = new Port(PrismConstants.REPLY);
		subtractComponent.addPort(subReplyPort);

		// Add the subtraction component to the calculator architecture.
		calculatorArchitecture.add(subtractComponent);

		// Create the addition component.
		Component additionComponent = new Component();
		additionComponent.setImplementation(new Addition());

		// Add a port to the addition component for receiving requests.
		Port addReplyPort = new Port(PrismConstants.REPLY);
		additionComponent.addPort(addReplyPort);

		// Add the addition component to the calculator architecture.
		calculatorArchitecture.add(additionComponent);

		// Create a connector for the calculator.
		Connector connector = new Connector();

		// Add a port to the connector for receiving requests.
		Port connectorReplyPort1 = new Port(PrismConstants.REPLY);
		connector.addPort(connectorReplyPort1);
		
		calculatorArchitecture.weld(guiRequestPort, connectorReplyPort1);

		// Add a port to the connector for forwarding requests to the
		// subtraction component.
		Port connectorRequestPort1 = new Port(PrismConstants.REQUEST);
		connector.addPort(connectorRequestPort1);

		calculatorArchitecture.weld(subReplyPort, connectorRequestPort1);

		// Add a port to the connector for forwarding requests to the addition
		// component.
		Port connectorRequestPort2 = new Port(PrismConstants.REQUEST);
		connector.addPort(connectorRequestPort2);

		calculatorArchitecture.weld(addReplyPort, connectorRequestPort2);

		calculatorArchitecture.start();
	}
}
