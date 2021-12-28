package Prism.core;

/**
 * The dispatcher is the container of a set of worker threads and an associated
 * queue. This class initiates, controls and terminates worker threads.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class RRobinDispatcher implements AbstractDispatch {

	/**
	 * The default number of threads this scheduler will use to service the
	 * message queue. This number is used when the constructor is not passed a
	 * value for the number of threads to use.
	 */
	protected final static int DEFAULT_COUNT = 10;

	/**
	 * The scheduler uses a bunch of threads to distribute the message
	 * processing. A count of the threads is stored in threadCount
	 */
	private int threadCount;

	/**
	 * List of all the workerThreads being used. This list can be grown or
	 * shrunk dynamically.
	 */
	private WorkerThread[] workerThreads;

	/**
	 * The scheduler needs to know the messages that are to be dispatched. For a
	 * single architecture it is assumed that there will be just one message
	 * store.
	 */
	private AbstractScheduler events;

	public RRobinDispatcher() {
		this(new FIFOScheduler(), RRobinDispatcher.DEFAULT_COUNT);
	}

	/**
	 * Create a dispatcher that dispatches messages on a round robin basis with
	 * a default of <code>DEFAULT_COUNT</code> threads
	 * 
	 * @param sched
	 *            AbstractScheduler a reference to the event store.
	 */
	public RRobinDispatcher(AbstractScheduler sched) {
		this(sched, RRobinDispatcher.DEFAULT_COUNT);
	}

	/**
	 * Create a scheduler that dispatches messages on a round robin basis with a
	 * default of <code>DEFAULT_COUNT</code> threads
	 * 
	 * @param n
	 *            int number of threads in the scheduler.
	 */
	public RRobinDispatcher(int n) {
		this(new FIFOScheduler(), n);
	}

	/**
	 * Create a dispatcher that dispatches messages on a round robin basis with
	 * the given number of threads. The threads required should be in the range
	 * of allowable threads, otherwise a default of <code>DEFAULT_COUNT</code>
	 * threads are used.
	 * 
	 * @param sched
	 *            AbstractScheduler a reference to the message store.
	 */
	public RRobinDispatcher(AbstractScheduler sched, int n) {
		this.events = sched;
		this.threadCount = n < 1 ? RRobinDispatcher.DEFAULT_COUNT : n;
		this.workerThreads = new WorkerThread[this.threadCount];
		for (int i = 0; i < this.threadCount; i++) {
			this.workerThreads[i] = new WorkerThread(this, "" + i, this.events);
		}
		this.start();
	}

	/**
	 * This method sets the number of threads this architecture uses to service
	 * the message queue to <code>n</code>. If <code>n</code> is less than
	 * or greater than LOWER_BOUND or UPPER_BOUND respectively then it the
	 * function does nothing and returns.
	 * 
	 * @param n
	 *            int the new number of threads to use from now.
	 */
	public void setThreadCount(int n) {
		if (n < 1) {
			return;
		}
		if (n > this.threadCount) {
			WorkerThread newWorkerThreads[] = new WorkerThread[n];
			int i;
			for (i = 0; i < this.threadCount; i++) {
				newWorkerThreads[i] = this.workerThreads[i];
			}
			for (; i < n; i++) {
				newWorkerThreads[i] = new WorkerThread(this, "Prism Worker "
						+ i, this.events);
			}
			this.workerThreads = newWorkerThreads;
			this.threadCount = n;
		} else if (n < this.threadCount) {
			for (int i = this.threadCount - 1; i >= n; i--) {
				this.workerThreads[i].setKeepWorking(false);
			}
			this.threadCount = n;
			WorkerThread newWorkerThreads[] = new WorkerThread[this.threadCount];
			for (int i = 0; i < this.threadCount; i++) {
				newWorkerThreads[i] = this.workerThreads[i];
			}
			this.workerThreads = newWorkerThreads;
		}
	}

	/**
	 * This method returns the current count of the threads servicing the
	 * message queue.
	 * 
	 * @return int the number of threads servicing the message queue.
	 */
	public int getThreadCount() {
		return this.threadCount;
	}

	/**
	 * This method starts all the threads in the scheduler and allows the
	 * messages to be dispatched to the destination components.
	 */
	public void start() {
		for (int i = 0; i < this.threadCount; i++) {
			this.workerThreads[i].start();
		}
	}

	/**
	 * This method stops all the threads in the architecture and finally exits
	 * the JVM. The threads will stop only after completeling the current
	 * threadFunction() call that they are executing. To shutdown the scheduler
	 * and system.
	 * 
	 * @see Prism.framework.Architecture#shutdown
	 */
	public void stop() {
		for (int i = 0; i < this.threadCount; i++) {
			this.workerThreads[i].setKeepWorking(false);
		}
		this.shutdown();
	}

	/**
	 * This method exits the JVM ASAP without gently killing the threads. To
	 * halt or pause the threads use stop(). Restart the scheduler by calling
	 * start.
	 * 
	 * @see Prism.framework.Architecture#stop
	 */
	public void shutdown() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}

	/**
	 * Set the time the thread stops before dispatching another message
	 * 
	 * @param step
	 *            the number of milliseconds to wait
	 */
	public void setTimeStep(int step) {
		for (int i = 0; i < this.threadCount; i++) {
			this.workerThreads[i].setTimeStep(step);
		}
	}
}
