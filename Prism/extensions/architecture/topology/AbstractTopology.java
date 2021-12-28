package Prism.extensions.architecture.topology;

import Prism.core.Brick;
import Prism.core.PrismException;

/**
 * Abstract topology class. Implementation for the methods of this class enforce the topological constraints.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractTopology
{
    public abstract void weld (Brick b1, Brick b2) throws PrismException;    
}
