package Prism.core;
/**
 * scaffold provides a structure that allows for Bricks to associate themselves with it. This structure provides a set of common 
 * utilites that are leveraged by all the bricks attached to it: 
 * 1)Scheduler: Scheduler determines the ordering of events in the queue. Examples: FIFOScheduler, EDFScheduler, user defined scheduler.
 * 2)Dispatcher: Dispatcher determines the type of queues and threads used. Examples: RRobinDispatcher, user defined dispatcher.
 * 3)Monitor: Allows for monitoring of events passed among components.
 *@see Prism.core.FIFOScheduler, Prism.core.EDFScheduler, Prism.core.RRobinDispatcher
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Scaffold implements AbstractScaffold
{
    /**
    * scheduler to be used for processing messages
    */
    public AbstractScheduler scheduler;
   
    /**
    * store to be used for message processing
    */
    public AbstractDispatch dispatcher;
   
    /**
    * For monitoring the messages being reported
    */    
    //public Prism.scaffold.monitor.IMonitor  monitor;

    /**
     * Simple constructor for Scaffold. Doesn't do anything.
     */
    public Scaffold() 
    {
    }
    
    /**
     * If the value of parameter command is "add", it calls the "add" method of 
     * scheduler to add the event to the queue. Otherwise, if the value of
     * parameter command is "see", it calls the "see" method of monitor.
     */
    public void call(String command, Event param)
    {
        if (command.equals("add") && (dispatcher) != null)
        {
             scheduler.add(param);
        }
/*        else if (command.equals("see") && (monitor) != null)
        {
            monitor.see( param);
        }
 */
    }
}
