package Prism.core;

/**
 * A scaffold provides a structure that allows for bricks to associate
 * themselves with it. This structure provides a set of common utilites that are
 * leveraged by all the bricks attached to it: 1)Scheduler: Scheduler determines
 * the ordering of events in the queue. Examples: FIFOScheduler, EDFScheduler,
 * user defined scheduler. 2)Dispatcher: Dispatcher determines the type of
 * queues and threads used. Examples: RRobinDispatcher, user defined dispatcher.
 * 3)Monitor: Allows for monitoring of events passed among components.
 * 
 * @see Prism.core.FIFOScheduler, Prism.core.EDFScheduler,
 *      Prism.core.RRobinDispatcher
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Scaffold implements AbstractScaffold {

	private AbstractScheduler scheduler;

	private AbstractDispatch dispatcher;

	/**
	 * Simple constructor for Scaffold. Creates a scaffold with a FIFO queue and
	 * a round-robin dispatcher.
	 */
	public Scaffold() {
		this.scheduler = new FIFOScheduler();
		this.dispatcher = new RRobinDispatcher(this.scheduler);
	}

	/**
	 * Sets the scheduler and dispatcher for this scaffold.
	 */
	public Scaffold(AbstractScheduler scheduler, AbstractDispatch dispatcher) {
		this.scheduler = scheduler;
		this.dispatcher = dispatcher;
	}

	/**
	 * If the value of parameter command is "add", it calls the "add" method of
	 * scheduler to add the event to the queue. Otherwise, if the value of
	 * parameter command is "see", it calls the "see" method of monitor.
	 */
	public void call(String command, Event param) {
		if (command.equals("add") && this.dispatcher != null) {
			this.scheduler.add(param);
		}
	}

	/**
	 * scheduler to be used for processing messages
	 */
	public void setScheduler(AbstractScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public AbstractScheduler getScheduler() {
		return this.scheduler;
	}

	/**
	 * store to be used for message processing
	 */
	public void setDispatcher(AbstractDispatch dispatcher) {
		this.dispatcher = dispatcher;
	}

	public AbstractDispatch getDispatcher() {
		return this.dispatcher;
	}
}
