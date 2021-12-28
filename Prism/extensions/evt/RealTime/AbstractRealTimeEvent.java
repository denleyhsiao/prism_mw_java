package Prism.extensions.evt.RealTime;

/**
 * An implementation of real time event needs to implement this interface.
 * 
 * @see Prism.core.EDFScheduler
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractRealTimeEvent implements java.io.Serializable {

	/**
	 * Each serializable class needs a serial UID. 
	 */
	private static final long serialVersionUID = 3610473227275186184L;

	public abstract long getTimeStamp();

	public abstract long getDeadlineTime();

	public abstract long getBufferTime();
}
