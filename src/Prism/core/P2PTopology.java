package Prism.core;

/**
 * This class enforces basic peer to peer constraints.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class P2PTopology extends AbstractTopology
{
	public boolean checkWeld (Brick b1, Brick b2)
	{
		return true;
	}
}