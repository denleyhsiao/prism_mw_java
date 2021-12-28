package Prism.core;

import Prism.extensions.evt.ExtensibleEvent;
import Prism.extensions.evt.RealTime.AbstractRealTimeEvent;

/**
 * A Priority based message store which allows a dynamic set of queue to be
 * handled. Messages are scheduled based on their priority. Higher priority
 * messages are added to the head of the queue, while lower priority messages
 * are added to the end of the queue.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class EDFScheduler implements AbstractScheduler {

	/**
	 * Store for the queue in the FIFO organized as a circular queue
	 */
	private Object queue[];

	/**
	 * By default a 250 event queue is assumed.
	 */
	final static int DEFAULT_SIZE = 250;

	/**
	 * Current capacity of the message queue
	 */
	private int size;

	/**
	 * Keep track of the number of empty slots in the queue
	 */
	private int emptySlots;

	/**
	 * The location of the head of the FIFO
	 */
	private int head;

	/**
	 * The location of the tail of the FIFO
	 */

	private int tail;

	/**
	 * Create the event store with DEFAULT_SIZE elements
	 */
	public EDFScheduler() {
		this(EDFScheduler.DEFAULT_SIZE);
	}

	/**
	 * Create the event store
	 */
	public EDFScheduler(int pSize) {
		this.size = pSize;
		this.queue = new Object[this.size + 1];
		this.head = 0;
		this.tail = 0;
		this.emptySlots = this.size;
	}

	/**
	 * Increments the circular queue by one. This is a potential major
	 * bottleneck.
	 * 
	 * @param i
	 *            index of location to be incremented
	 */
	private synchronized int increment(int i) {

		if (i == this.size - 1) {
			return 0;
		} else {
			return ++i;
		}

	}

	/**
	 * Decrements the circular queue by one. This is a potential major
	 * bottleneck.
	 * 
	 * @param i
	 *            index of location to be decremented
	 */
	private synchronized int decrement(int i) {
		int temp = i;
		if (i == 0) {
			temp = this.size - 1;
		} else {
			temp = i - 1;
		}
		// System.out.println("TEMP In Decrement " + temp );
		return temp;
	}

	/**
	 * Adds a new event to the queue. Location of new event is determined
	 * according to its priority.
	 * 
	 * @param evt
	 *            Event to be added
	 */
	public synchronized void add(Event evt) {
		int i;
		try {
			while (this.isQueueFull()) {
				this.wait();
				System.out.println("waiting for the queue to get empty");
			}
		} catch (InterruptedException e) {
			if (PrismConstants.DEBUG_MODE) {
				System.out
						.println("Caught exception while waiting for queue to empty"
								+ e);
			}
			e.printStackTrace();
		}

		if (evt instanceof ExtensibleEvent) // extensible event
		{
			if (((ExtensibleEvent) evt).getRealTime() == null) // if Real-Time
																// module isn't
																// set, it must
																// be non
																// real-time
			{
				i = this.tail;
			} else // real-time
			{
				i = this.head;
				while (i != this.tail) {
					if (this.queue[i] instanceof ExtensibleEvent) {
						ExtensibleEvent thisEvent = (ExtensibleEvent) this.queue[i];
						if (thisEvent.getRealTime() != null) {
							if (this.deadlineGT(thisEvent, (ExtensibleEvent) evt)) // if
																				// thisEvent
																				// has
																				// longer
																				// deadline
																				// than
																				// evt
							{
								break;
							}
						} else {
							// Real-Time module isn't set, it must be non
							// real-time
							break;
						}
					} else {
						// regular event and by default non real-time
						break;
					}

					i = this.increment(i);
				}
			}
		} else // regular event and by default non real-time
		{
			i = this.tail;
		}

		int j = this.tail;
		while (j != i) {
			int k = j;
			k = this.decrement(k);
			this.queue[j] = this.queue[k];
			j = this.decrement(j);
		}
		this.queue[i] = evt;

		this.incrementTail();
		--this.emptySlots;
		this.notifyAll();
	}

	private boolean deadlineGT(ExtensibleEvent queueEvt, ExtensibleEvent newEvt) {

		long totalTime1 = 0;
		long totalTime2 = 0;

		AbstractRealTimeEvent evt1 = queueEvt.getRealTime();
		AbstractRealTimeEvent evt2 = newEvt.getRealTime();

		totalTime1 = evt1.getTimeStamp() + evt1.getDeadlineTime()
				+ evt1.getBufferTime();
		totalTime2 = evt2.getTimeStamp() + evt2.getDeadlineTime()
				+ evt2.getBufferTime();

		if (totalTime1 > totalTime2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get a message from the head of the queue and remove it from the message
	 * store, although the message object itself is not deleted.
	 * 
	 * @return Message The message object at the head of the FIFO
	 */
	public synchronized Event getEvent() {
		Event m = null;
		try {
			while (this.isQueueEmpty()) {
				this.wait();
			}
		} catch (Exception e) {
				if (PrismConstants.DEBUG_MODE) {
				System.out
						.println("Caught exception while waiting for queue to fill"
								+ e);
				}
			e.printStackTrace();
		}
		m = (Event) this.queue[this.head];
		this.queue[this.head] = null;
		this.incrementHead();
		++this.emptySlots;
		this.notifyAll();
		return m;
	}

	/**
	 * Get the number of queued events waiting to be dispatched
	 * 
	 * @return int Number of queue remaining
	 */
	public synchronized int getWaitingLength() {
		return this.size - this.emptySlots;
	}

	/**
	 * Get a string representation of the message queue. This is used primarily
	 * for debugging
	 * 
	 * @return String The stringified representation of the queue.
	 */
	public String toString() {
		String msg = new String();
		if (this.head == this.tail) {
			return "Empty";
		} else {
			msg = ((Event) this.queue[this.head]).getName();
		}
		int start = this.head + 1;
		if (start > this.size) {
			start = 0;
		}
		for (int i = start;; i++) {
			if (i == this.tail) {
				break;
			}
			msg += ", " + ((Event) this.queue[i]).getName();
			if (i == this.size) {
				i = -1;
			}
		}
		return msg;
	}

	/**
	 * Set the capacity of messages that can be stored before being dispatched
	 * 
	 * @param n
	 *            int the required capacity of the message store
	 */
	public void setEventCapacity(int n) {
		// do not do anything as we have hardcoded number of messages for now
	}

	private boolean isQueueEmpty() {
		return this.emptySlots == this.size;
	}

	private boolean isQueueFull() {
		return this.emptySlots == 1;
	}

	private void incrementTail() {
		if (this.tail == this.size - 1) {
			this.tail = 0;
		} else {
			++this.tail;
		}
	}

	private void incrementHead() {
		if (this.head == this.size - 1) {
			this.head = 0;
		} else {
			++this.head;
		}
	}

	void setSize(int size) {
		this.size = size;
	}

	int getSize() {
		return this.size;
	}

	void setHead(int head) {
		this.head = head;
	}

	int getHead() {
		return this.head;
	}

	void setTail(int tail) {
		this.tail = tail;
	}

	int getTail() {
		return this.tail;
	}

}
