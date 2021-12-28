package Prism.extensions.architecture.topology;

import Prism.core.*;


public class PubSubTopology extends AbstractTopology 
{
	public void weld (Brick b1, Brick b2) throws PrismException
	{
		int b1Style = b1.getStyle();
		int b2Style = b2.getStyle(); 			
		if ( (b1Style != PrismConstants.PUB_SUB_COMP) || (b2Style != PrismConstants.PUB_SUB_CONN))
		{
			throw new PrismException("Wrong stylistic elements passed to the PubSubTopology.");
		}
		else
		{	
			Port b1Req = new Port("b1Request", PrismConstants.REQUEST);
			((IComponent)b1).addCompPort(b1Req);
			Port b2Rep = new Port("b2Reply", PrismConstants.REPLY);
			((IConnector)b2).addConnPort(b2Rep);
			b1Req.setMutualPort(b2Rep);
			b2Rep.setMutualPort(b1Req);				
	
			Port b1Rep = new Port("b1Reply", PrismConstants.REPLY);
			((IComponent)b1).addCompPort (b1Rep);
			Port b2Req = new Port("b2Req", PrismConstants.REQUEST);
			((IConnector)b2).addConnPort(b2Req);
		}			
	}
}
