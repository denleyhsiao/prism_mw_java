package Prism.extensions.architecture.topology;

import Prism.core.*;

/**
 * This class enforces basic C2 topology constraint that disallows two components connect to each other.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class C2Topology extends AbstractTopology
{
	public void weld (Brick b1, Brick b2) throws PrismException
	{
		int b1Style = b1.getStyle();
		int b2Style = b2.getStyle();
	
		if ((b1Style != PrismConstants.C2_COMP && b1Style != PrismConstants.C2_CONN) ||
				(b2Style != PrismConstants.C2_COMP && b2Style != PrismConstants.C2_CONN))
		{
			throw new PrismException("At least one of the two bricks isn't a C2 style brick.");	
		}
		else if ( b1Style == PrismConstants.C2_COMP && b2Style == PrismConstants.C2_COMP)
		{
			throw new PrismException("Cannot weld two components directly");
		}		
		else
		{
			if (b1Style == PrismConstants.C2_COMP)
			{			
				Port compReq = new Port("compReq", PrismConstants.REPLY);
				((IComponent)b1).addCompPort (compReq);
				Port connRep = new Port("connRep", PrismConstants.REQUEST);
				((IConnector)b2).addConnPort(connRep);
				compReq.setMutualPort(connRep);
				connRep.setMutualPort(compReq);				
			}
			else if (b1Style == PrismConstants.C2_CONN)
			{	
				Port connReq = new Port("connReq", PrismConstants.REPLY);
				((IConnector)b1).addConnPort (connReq);
				Port compRep = new Port("compRep", PrismConstants.REQUEST);
				((IComponent)b2).addCompPort(compRep);
				connReq.setMutualPort(compRep);
				compRep.setMutualPort(connReq);
			}
		}
	}
}