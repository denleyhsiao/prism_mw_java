package Prism.core;

/**
 * This interface is implemented by any Scaffold in Prism. Scaffold provides a structure that allows for Bricks to associate themselves
 * with it. This structure provides a set of common utilites that are leveraged by all the bricks attached to it: 
 * 1)Scheduler: Scheduler determines the ordering of events in the queue. Examples: FIFOScheduler, EDFScheduler, user defined scheduler.
 * 2)Dispatcher: Dispatcher determines the type of queues and threads used. Examples: RRobinDispatcher, user defined dispatcher.
 * 3)Monitor: Allows for monitoring of events passed among components.
 *@see Prism.core.FIFOScheduler, Prism.core.EDFScheduler, Prism.core.RRobinDispatcher 
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IScaffold 
{	
   /**
    * Make an asynchronous call 
    * @param method     Name of the method to be called
    * @param param      parameters to be passed
    */
    public void call(String method, Event param);
  
}
