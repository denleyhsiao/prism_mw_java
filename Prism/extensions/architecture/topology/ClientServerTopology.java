package Prism.extensions.architecture.topology;

import Prism.core.IPort;
import Prism.core.PrismConstants;
import Prism.core.PrismException;

public class ClientServerTopology extends AbstractTopology {
	public void weld(IPort p1, IPort p2) throws PrismException {
		int p1Style = p1.getParentBrick().getStyle();
		int p2Style = p2.getParentBrick().getStyle();
		if (p1Style != PrismConstants.CLIENT && p1Style != PrismConstants.SERVER
				|| p2Style != PrismConstants.CLIENT && p2Style != PrismConstants.SERVER) {
			throw new PrismException(
					"At least one of the bricks is neither a client nor a server");
		} else if (p1Style != PrismConstants.CLIENT
				&& p2Style != PrismConstants.CLIENT) {
			throw new PrismException("Cannot weld two clients directly");
		} else {
			if (p1Style == PrismConstants.CLIENT
					&& p2Style == PrismConstants.SERVER) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			} else {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			}
		}
	}

}
