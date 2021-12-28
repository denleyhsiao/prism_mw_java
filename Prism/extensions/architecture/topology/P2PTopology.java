package Prism.extensions.architecture.topology;

import Prism.core.IPort;
import Prism.core.PrismConstants;
import Prism.core.PrismException;

/**
 * This class enforces basic peer 2 peer constraints.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class P2PTopology extends AbstractTopology {
	public void weld(IPort p1, IPort p2) throws PrismException {
		int p1Style = p1.getParentBrick().getStyle();
		int p2Style = p2.getParentBrick().getStyle();
		if (p1Style != PrismConstants.P2P_COMP
				|| p2Style != PrismConstants.P2P_COMP) {
			throw new PrismException(
					"At least one of the bricks is not a peer component.");
		} else {
			p1.setMutualPort(p2);
			p2.setMutualPort(p1);
		}
	}
}