package Prism.core;

/**
 * A simple FIFO based message store which allows a dynamic set of queue to be
 * handled in the architecture. Messages are always added to the end of the
 * queue whereas they are removed at the head of the queue. There is no
 * knowledge of priorities.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class FIFOScheduler implements AbstractScheduler {

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
	 * 
	 * @see C3.conn.FIFODispatch#DEFAULT_SIZE
	 */

	public FIFOScheduler() {
		this(FIFOScheduler.DEFAULT_SIZE);
	}

	/**
	 * Create the event store
	 */
	public FIFOScheduler(int FIFOSize) {
		this.size = FIFOSize;
		this.queue = new Object[this.size + 1];
		this.head = 0;
		this.tail = 0;
		this.emptySlots = FIFOSize;
	}

	/**
	 * Add an event to the beginning of the queue. The method is synchronized so
	 * that simultaneous additions and removals are not possible. This is a
	 * potential major bottleneck.
	 * 
	 * @param m
	 *            Event to be added
	 */

	public synchronized void add(Event evt) {
		try {
			while (this.isQueueFull()) {
				this.wait();
			}
		} catch (InterruptedException e)

		{
			if (PrismConstants.DEBUG_MODE) {
				System.out
					.println("Caught exception while waiting for queue to empty"
							+ e);
			}
			e.printStackTrace();
		}
		this.queue[this.tail] = evt;
		this.incrementTail();
		--this.emptySlots;
		this.notifyAll();
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
