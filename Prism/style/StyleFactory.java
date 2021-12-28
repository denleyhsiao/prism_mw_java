package Prism.style;

import Prism.core.*;
import Prism.extensions.component.*;
import Prism.extensions.component.synchronism.*;
import Prism.extensions.connector.*;
import Prism.extensions.connector.handler.*;
import Prism.extensions.architecture.*;
import Prism.extensions.architecture.topology.*;

public class StyleFactory 
{
	public static ExtensibleComponent generateComponent(String name, int style, AbstractImplementation pImpl)	
	{	
		switch (style)
		{
			case PrismConstants.C2_COMP:
				return (new ExtensibleComponent(name, style, pImpl));
			case PrismConstants.FILTER:
				return (new ExtensibleComponent(name, style, pImpl));
			case PrismConstants.SERVER:
				return (new ExtensibleComponent(name, style, pImpl));
			case PrismConstants.PUB_SUB_COMP:
				return (new ExtensibleComponent(name, style, pImpl));
			case PrismConstants.P2P_COMP:
				return (new ExtensibleComponent(name, style, pImpl));
			case PrismConstants.CLIENT:
				ExtensibleComponent ec = new ExtensibleComponent(name, style, pImpl);
				ec.addCompSynchronism(new CompSynchronism());
				return ec;				
			default:
				return null;
		}     	
	}
	
	public static ExtensibleConnector generateConnector(String name, int style)	
	{	
		AbstractHandler h;
		switch (style)
		{
			case PrismConstants.C2_CONN:							
				h = new C2Handler();
				return (new ExtensibleConnector(name, h, style));												
			case PrismConstants.PIPE:				
				h = new PipeFilterHandler();
				return (new ExtensibleConnector(name, h, style));				
			case PrismConstants.PUB_SUB_CONN:
				h = new PubSubHandler();
				return (new ExtensibleConnector(name, h, style));
			default:
				return null;
		}
	}
	
	public static ExtensibleArchitecture generateArchitecture (String name, int style)
	{
		AbstractTopology topology;		
		switch (style)
		{
			case PrismConstants.C2_ARCH:							
				topology = new C2Topology();
				return (new ExtensibleArchitecture(name, topology, style));												
			case PrismConstants.PIPE_FILTER_ARCH:
				topology = new PipeFilterTopology();
				return (new ExtensibleArchitecture(name, topology, style));		
			case PrismConstants.CLIENT_SERVER_ARCH:
				topology = new ClientServerTopology();
				return (new ExtensibleArchitecture(name, topology, style));
			case PrismConstants.PUB_SUB_ARCH:
				topology = new PubSubTopology();
				return (new ExtensibleArchitecture(name, topology, style));
			case PrismConstants.P2P_ARCH:
				topology = new P2PTopology();
				return (new ExtensibleArchitecture(name, topology, style));				
			default:
				return null;
		}
	}
	
}
