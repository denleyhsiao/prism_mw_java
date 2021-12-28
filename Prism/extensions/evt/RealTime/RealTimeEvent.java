package Prism.extensions.evt.RealTime;

/**
 * Represents real time information of an ExtensibleEvent that supports real
 * time.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class RealTimeEvent extends AbstractRealTimeEvent implements
		java.io.Serializable {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = -6468401463753019387L;

	/**
	 * Time when the real time message was orriginated.
	 */
	private long timeStamp;

	/**
	 * Deadline time for real-time message
	 */
	private long deadlineTime;

	/**
	 * Time period, after deadline time, while message can still have certain
	 * value. Used for soft-real time messages.
	 */
	private long bufferTime;

	/**
	 * Constructor that initializes internal state. A time stamp is put on an
	 * event here, which is used for real time calculations.
	 * 
	 * @param pDeadlineTime
	 *            The deadline for this event
	 * @param pBufferTime
	 *            The buffer time for this event. Used in soft real time, where
	 *            an event may still be of value even after the deadline.
	 */
	public RealTimeEvent(long pDeadlineTime, long pBufferTime) {
		this.timeStamp = System.currentTimeMillis();
		this.deadlineTime = pDeadlineTime;
		this.bufferTime = pBufferTime;
	}

	/**
	 * Gets the time stamp of this event. A RealTimeEvent is automatically
	 * tagged with a time stamp at the time of creation.
	 * 
	 * @return long time stamp
	 */
	public long getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * Gets the deadline time of this event.
	 * 
	 * @return long deadline time
	 */
	public long getDeadlineTime() {
		return this.deadlineTime;
	}

	/**
	 * Gets the buffer time of this event. In soft real time, an event may still
	 * be of value even after the deadline. This time is called the buffer time.
	 * 
	 * @return long buffer time
	 */
	public long getBufferTime() {
		return this.bufferTime;
	}
}
