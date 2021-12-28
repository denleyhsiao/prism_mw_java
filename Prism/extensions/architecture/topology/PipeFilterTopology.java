package Prism.extensions.architecture.topology;

import Prism.core.*;

public class PipeFilterTopology extends AbstractTopology 
{
	public void weld (Brick b1, Brick b2) throws PrismException
	{
		int b1Style = b1.getStyle();
		int b2Style = b2.getStyle(); 		
		if ((b1Style != PrismConstants.PIPE && b1Style != PrismConstants.FILTER) ||
			(b2Style != PrismConstants.PIPE && b2Style != PrismConstants.FILTER))
		{
			throw new PrismException("At least one of the bricks is neither a pipe nor a filter");
		}
		else if ( b1Style == PrismConstants.FILTER && b2Style == PrismConstants.FILTER)
		{
			throw new PrismException("Cannot weld two filters directly");
		}
		else
		{	
			if (b1Style == PrismConstants.FILTER && b2Style == PrismConstants.PIPE)
			{	
				Port filterRequest = new Port("filterRequest", PrismConstants.REQUEST);
				((IComponent)b1).addCompPort (filterRequest);
				Port pipeReply = new Port("pipeReply", PrismConstants.REPLY);
				((IConnector)b2).addConnPort(pipeReply);
				filterRequest.setMutualPort(pipeReply);
				pipeReply.setMutualPort(filterRequest);					
			}
			else if (b1Style == PrismConstants.PIPE && b2Style == PrismConstants.FILTER)
			{	  
				Port filterReply = new Port("filterReply", PrismConstants.REPLY);
				((IComponent)b2).addCompPort (filterReply);
				Port pipeRequest = new Port("pipeRequest", PrismConstants.REQUEST);
				((IConnector)b1).addConnPort(pipeRequest);
				filterReply.setMutualPort(pipeRequest);
				pipeRequest.setMutualPort(filterReply);					
			}
		}
	}
}
