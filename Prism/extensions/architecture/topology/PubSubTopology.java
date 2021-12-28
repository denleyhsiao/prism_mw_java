package Prism.extensions.architecture.topology;

import Prism.core.IPort;
import Prism.core.PrismConstants;
import Prism.core.PrismException;

public class PubSubTopology extends AbstractTopology {
	public void weld(IPort p1, IPort p2) throws PrismException {
		int p1Style = p1.getParentBrick().getStyle();
		int p2Style = p2.getParentBrick().getStyle();
		if (p1Style != PrismConstants.PUB_SUB_COMP
				|| p2Style != PrismConstants.PUB_SUB_CONN) {
			throw new PrismException(
					"Wrong stylistic elements passed to the PubSubTopology.");
		} else {
			p1.setMutualPort(p2);
			p2.setMutualPort(p1);
		}
	}
}
