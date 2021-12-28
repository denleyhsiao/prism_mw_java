package Prism.core;

/**
 * A <code>WorkerThread</code> continuously scans the event list to process any available
 * event. The worker thread waits when there are no more messages otherwise runs through
 * retreives the next available event to be processed and dispatches it to the appropriate
 * processing <code>Brick</code>. The threads execution is controlled by the dispatcher. An
 * example of a dispatcher is the RRobinDispatcher which creates a set of threads all with
 * the same priority level.
 * @see Prism.core.RRobinDispatcher
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
class WorkerThread extends Thread {

  /**
   * The thread continues to process from start until this flag is turned off. Once the flag
	* is turned off, the <code>WorkerThread</code> needs to be restarted.
	*/
	public boolean keepWorking;

  /**
   * WorkerThread accesses events directly from the event store. This is the well defined
	* store for that architecture.
	*/
	private IScheduler events;

  /**
   * number of milliseconds to wait between processing two events in a sequence. By default
	* the thread does not wait between events as long there is more processing to be done.
	*/
	private int timeStep = 0;

  /**
   * A WorkerThread is created based on the event store. The current ThreadGroup is used
	* by default
	* @param name		String Name of the worker thread
	* @param sched		IScheduler the event store to be used.
	*/

	private IDispatch myDispatcher;
	private String nameofThread;
	
	private Event e;
	


 /**
   * A WorkerThread is created based on the event store. The current ThreadGroup is used
	* by default
	* @param disp       IDispatch that owns the thread.
	* @param name		String Name of the worker thread
	* @param sched		IScheduler the event store to be used.
	*/
	WorkerThread(IDispatch disp,String name, IScheduler sched) {
		//super(grp, name);
		super();
		events = sched;
		keepWorking = true;
		myDispatcher = disp;
		nameofThread = name;
	}
	
  /**
   * Start the thread's execution so that it starts processing events
	*/
	public void start() {
		super.start();
	}

  /**
   * The main processing loop in a WorkerThread where events are dispatched. A thread
	* keeps running till it is asked to. When no events are available to dispatch, the
	* thread waits and does not spin idle cycles. An assumption is that the events
	* originate only in components, although it can be extended to apply to events
	* originating in connectors.
	*/
	public void run() {

		while(keepWorking)
		{
			try
			{
				e = events.getEvent();
				if(e!=null) {
                                        e.handlingBrick.handle(e);
					
					if(e.name.equals("Terminate"))
					{
						System.out.println("Leaving ");
						myDispatcher.stop();
						return;
					}
					e=null;				
				}
				else
				{
					System.out.println("null Message in Worker Thread " + nameofThread); 
				}
			} 
			catch (OutOfMemoryError em)
			{
				System.out.println("Caught exception " +em.toString() + " in Worker Thread " + nameofThread); 
			}
			catch (Exception e1) // Catch exceptions while dispatching the message
			{
				System.out.println("Caught exception " + e1.toString() + " in " + toString()+ "while dispatching the event "+ e.name); 
				e1.printStackTrace();
			}
			yield();
			
			if (timeStep > 0)
			{
				try
				{
					sleep(timeStep);
				}
				catch (Exception e2) // Timer Interrupt
				{
				}
			}
		}

	}

  /**
   * Change the amount of delay between processing two messages
	* @param delay			int milliseconds to wait
	*/
	public void setTimeStep(int delay) {
		timeStep = delay;
	}
}
