package Prism.extensions.architecture.topology;

import Prism.core.*;

public class ClientServerTopology extends AbstractTopology
{    
    public void weld(Brick b1, Brick b2) throws PrismException 
    {
		int b1Style = b1.getStyle();
		int b2Style = b2.getStyle();    	
		if ((b1Style != PrismConstants.CLIENT && b1Style != PrismConstants.SERVER) ||
			(b2Style != PrismConstants.CLIENT && b2Style != PrismConstants.SERVER))
		{
			throw new PrismException("At least one of the bricks is neither a client nor a server");
		}
        else if (b1Style != PrismConstants.CLIENT && b2Style != PrismConstants.CLIENT)
        {
        	throw new PrismException("Cannot weld two clients directly");
        }
        else
        {
        	if (b1Style == PrismConstants.CLIENT && b2Style == PrismConstants.SERVER)
        	{	
				Port clientRequest = new Port("clientRequest", PrismConstants.REQUEST);
				((IComponent)b1).addCompPort(clientRequest);
				Port serverReply = new Port("serverReply", PrismConstants.REPLY);
				((IComponent)b2).addCompPort(serverReply);
				clientRequest.setMutualPort(serverReply);
				serverReply.setMutualPort(clientRequest);					
        	}
        	else
        	{
				Port clientRequest = new Port("clientRequest", PrismConstants.REQUEST);
				((IComponent)b2).addCompPort(clientRequest);
				Port serverReply = new Port("serverReply", PrismConstants.REPLY);
				((IComponent)b1).addCompPort(serverReply);
				clientRequest.setMutualPort(serverReply);
				serverReply.setMutualPort(clientRequest);					 		
        	}
        }
    }
    
}
