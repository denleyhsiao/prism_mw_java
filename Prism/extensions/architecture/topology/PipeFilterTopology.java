package Prism.extensions.architecture.topology;

import Prism.core.IPort;
import Prism.core.PrismConstants;
import Prism.core.PrismException;

public class PipeFilterTopology extends AbstractTopology {
	public void weld(IPort p1, IPort p2) throws PrismException {
		int p1Style = p1.getParentBrick().getStyle();
		int p2Style = p2.getParentBrick().getStyle();
		if (p1Style != PrismConstants.PIPE && p1Style != PrismConstants.FILTER
				|| p2Style != PrismConstants.PIPE && p2Style != PrismConstants.FILTER) {
			throw new PrismException(
					"At least one of the bricks is neither a pipe nor a filter");
		} else if (p1Style == PrismConstants.FILTER
				&& p2Style == PrismConstants.FILTER) {
			throw new PrismException("Cannot weld two filters directly");
		} else {
			if (p1Style == PrismConstants.FILTER
					&& p2Style == PrismConstants.PIPE) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			} else if (p1Style == PrismConstants.PIPE
					&& p2Style == PrismConstants.FILTER) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			}
		}
	}
}
