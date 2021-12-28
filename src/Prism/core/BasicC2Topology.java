package Prism.core;

/**
 * This class enforces basic C2 topology constraint that disallows two components connect to each other.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class BasicC2Topology extends AbstractTopology
{
	public boolean checkWeld (Brick b1, Brick b2)
	{
		if ( (b1 instanceof IComponent) && (b2 instanceof IComponent))
		{
			System.err.println("Usage Error: Prism style doesn't allow two IComponent types to be welded together directly.");
			return false;
		}
		return true;
	}
}