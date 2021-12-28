package Prism.style;

import Prism.core.AbstractImplementation;
import Prism.core.AbstractScaffold;
import Prism.extensions.architecture.ExtensibleArchitecture;
import Prism.extensions.architecture.topology.AbstractTopology;
import Prism.extensions.component.ExtensibleComponent;
import Prism.extensions.connector.ExtensibleConnector;
import Prism.extensions.connector.handler.AbstractHandler;

public class StyleFactory {
	public static ExtensibleComponent generateComponent(String name, int style,
			AbstractScaffold scaffold, AbstractImplementation implementation) {
		ExtensibleComponent extensibleComponent = new ExtensibleComponent(name,
				style, scaffold);
		extensibleComponent.setImplementation(implementation);
		return extensibleComponent;
	}

	public static ExtensibleConnector generateConnector(String name, int style,
			AbstractScaffold scaffold, AbstractHandler handler) {
		ExtensibleConnector extensibleConnector = new ExtensibleConnector(name,
				style, scaffold);
		extensibleConnector.addHandler(handler);
		return extensibleConnector;
	}

	public static ExtensibleArchitecture generateArchitecture(String name,
			int style, AbstractScaffold scaffold, AbstractTopology topology) {
		ExtensibleArchitecture extensibleArchitecture = new ExtensibleArchitecture(
				name, style, scaffold);
		extensibleArchitecture.addTopology(topology);
		return extensibleArchitecture;
	}
}
