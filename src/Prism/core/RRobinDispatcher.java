package Prism.core;

/**
 * The dispatcher is the container of a set of worker threads and an associated queue.
 * This class initiates, controls and terminates worker threads. 
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class RRobinDispatcher implements IDispatch{
   	
   /**
    * The default number of threads this scheduler will
    * use to service the message queue. This number is used when the constructor
    * is not passed a value for the number of threads to use.
    */
    protected static int DEFAULT_COUNT = 10;

  /**
   * The scheduler uses a bunch of threads to distribute the message processing. A
   * count of the threads is stored in threadCount
   */
    private int threadCount;

  /**
   * List of all the workerThreads being used. This list can be grown or shrunk dynamically.
   */
    private WorkerThread[] workerThreads;

  /**
   * The scheduler needs to know the messages that are to be dispatched. For a single
   * architecture it is assumed that there will be just one message store.
   */
    private IScheduler events;

  /**
   * Create a dispatcher that dispatches messages on a round robin basis with a default of
   * <code>DEFAULT_COUNT</code> threads
   * @param sched		IScheduler a reference to the event store.
   */
    public RRobinDispatcher(IScheduler sched) {
	this(sched, DEFAULT_COUNT);
    }

  /**
   * Create a scheduler that dispatches messages on a round robin basis with a default of
   * <code>DEFAULT_COUNT</code> threads
   * @param n		int number of threads in the scheduler.
   */
    public RRobinDispatcher(int n) {
        this(null, n);
    }

  /**
   * Create a scheduler that dispatches messages on a round robin basis with the given number
   * of threads. The threads required should be in the range of allowable threads, otherwise
   * a default of <code>DEFAULT_COUNT</code> threads are used.
   * @param disp		IDispatch a reference to the message store.
   */
    public RRobinDispatcher(IScheduler sched, int n) {
    	events = sched;
	threadCount = ( n < 1) ? DEFAULT_COUNT : n;
	workerThreads = new WorkerThread[threadCount];
	for(int i=0; i < threadCount; i++ )
	{
            workerThreads[i] = new WorkerThread(this, ""+i, events);			
	}
    }

  /**
    * This method sets the number of threads this architecture uses to service the
    * message queue to <code>n</code>. If <code>n</code> is less than or greater than
    * LOWER_BOUND or UPPER_BOUND respectively then it the function does nothing and returns.
    * @param n 			int the new number of threads to use from now.
    */
    public void setThreadCount(int n)
    {
            if(n < 1 )
                    return;
            if (n > threadCount) {
                    WorkerThread newWorkerThreads[] = new WorkerThread[n];
                    int i;
                    for (i = 0; i < threadCount; i++)
                            newWorkerThreads[i] = workerThreads[i];
                    for (; i < n; i++)
                    {
//          removed for Prism port to Palm
//				newWorkerThreads[i] = new WorkerThread(Thread.currentThread().getThreadGroup(),"Prism Worker "+ i, messages);
                            newWorkerThreads[i] = new WorkerThread(this,"Prism Worker "+ i, events);
                    }
                    workerThreads = newWorkerThreads;
                    threadCount = n;
            }
            else if (n < threadCount) {
                    for (int i = threadCount - 1; i >= n; i--)
                            workerThreads[i].keepWorking = false;
                    threadCount = n;
                    WorkerThread newWorkerThreads[] = new WorkerThread[threadCount];
                    for (int i = 0; i < threadCount; i++)
                            newWorkerThreads[i] = workerThreads[i];
                    workerThreads = newWorkerThreads;
            }
    }

  /**
    * This method returns the current count of the threads servicing the message queue.
    * @return int		the number of threads servicing the message queue.
    */
    public int getThreadCount()
    {
            return threadCount;
    }

  /**
    * This method starts all the threads in the scheduler and allows the messages to be
    * dispatched to the destination components.
    */
    public void start() {
        for(int i=0; i < threadCount; i++ )
        {
                workerThreads[i].start();
        }
    }

  /**
    * This method stops all the threads in the architecture and finally exits the JVM.
    * The threads will stop only after completeling the current threadFunction() call
    * that they are executing. To shutdown the scheduler and system.
    * @see Prism.framework.Architecture#shutdown
    */
    public void stop()
    {
//	System.out.println("Thread Count " + threadCount);
        for(int i=0; i < threadCount; i++ )
        {
                workerThreads[i].keepWorking = false;
//			System.out.println("Thread " + i + "stopping");
        }
        shutdown();
    }

  /**
    * This method exits the JVM ASAP without gently killing the threads. To halt or pause the
    * threads use stop(). Restart the scheduler by calling start.
    * @see Prism.framework.Architecture#stop
    */
    public void shutdown()
    {
            try
            {
                    Thread.currentThread().sleep(2000);
            }catch(InterruptedException e){}
            System.exit(0);
    }

  /**
    * Set the time the thread stops before dispatching another message
    * @param step the number of milliseconds to wait
    */
    public void setTimeStep(int step)
    {
            for(int i=0; i < threadCount; i++ )
            {
                    workerThreads[i].setTimeStep(step);
            }
    }
}


