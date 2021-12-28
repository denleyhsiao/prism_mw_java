package Prism.extensions.architecture.topology;

import Prism.core.*;
import Prism.core.PrismException;

/**
 * This class enforces basic peer 2 peer constraints.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class P2PTopology extends AbstractTopology
{
	public void weld (Brick b1, Brick b2) throws PrismException
	{
		int b1Style = b1.getStyle();
		int b2Style = b2.getStyle();    	
		if (b1Style != PrismConstants.P2P_COMP && b1Style != PrismConstants.P2P_COMP)
		{
			throw new PrismException("At least one of the bricks is not a peer component.");
		}
		else
		{
				Port b1Request = new Port("b1Request", PrismConstants.REQUEST);
				((IComponent)b1).addCompPort(b1Request);
				Port b2Reply = new Port("b2Reply", PrismConstants.REPLY);
				((IComponent)b2).addCompPort(b2Reply);
				b1Request.setMutualPort(b2Reply);
				b2Reply.setMutualPort(b1Request);

				Port b1Reply = new Port("b1Reply", PrismConstants.REPLY);
				((IComponent)b1).addCompPort(b1Reply);
				Port b2Request = new Port("b2Request", PrismConstants.REQUEST);
				((IComponent)b2).addCompPort(b2Request);
				b1Reply.setMutualPort(b2Request);
				b2Request.setMutualPort(b1Reply);
								
		}
	}
}