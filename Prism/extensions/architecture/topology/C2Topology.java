package Prism.extensions.architecture.topology;

import Prism.core.IPort;
import Prism.core.PrismConstants;
import Prism.core.PrismException;

/**
 * This class enforces basic C2 topology constraint that disallows two
 * components connect to each other.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class C2Topology extends AbstractTopology {
	public void weld(IPort p1, IPort p2) throws PrismException {
		int p1Style = p1.getParentBrick().getStyle();
		int p2Style = p2.getParentBrick().getStyle();

		if (p1Style != PrismConstants.C2_COMP && p1Style != PrismConstants.C2_CONN
				|| p2Style != PrismConstants.C2_COMP && p2Style != PrismConstants.C2_CONN) {
			throw new PrismException(
					"At least one of the two bricks isn't a C2 style brick.");
		} else if (p1Style == PrismConstants.C2_COMP
				&& p2Style == PrismConstants.C2_COMP) {
			throw new PrismException("Cannot weld two components directly");
		} else {
			if (p1Style == PrismConstants.C2_CONN
					&& p2Style == PrismConstants.C2_CONN) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			} else if (p1Style == PrismConstants.C2_COMP) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			} else if (p1Style == PrismConstants.C2_CONN) {
				p1.setMutualPort(p2);
				p2.setMutualPort(p1);
			}
		}
	}
}