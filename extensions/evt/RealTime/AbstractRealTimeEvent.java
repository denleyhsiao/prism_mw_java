package Prism.extensions.evt.RealTime;

/**
 * An implementation of real time event needs to implement this interface. 
 * @see Prism.core.EDFScheduler
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractRealTimeEvent implements java.io.Serializable
{
    public abstract long getTimeStamp ();
    public abstract long getDeadlineTime ();
    public abstract long getBufferTime ();
}
