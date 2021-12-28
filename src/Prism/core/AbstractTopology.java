package Prism.core;
 
/**
 * Abstract topology class. Implementation for the methods of this class enforce the topological constraints.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractTopology
{
    public abstract boolean checkWeld (Brick b1, Brick b2);    
}
