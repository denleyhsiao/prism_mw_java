package Prism.core;

/**
 * This interface needs to be implemented by any Prism dispatcher. The dispatcher is the container of all the worker threads in Prism.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IDispatch 
{
    /**
    * Start the dispatcher
    */
    public void start();	

    /**
    * Stop the dispatcher. It can be restarted.
    */
    public void stop();

    /**
    * Terminate all dispatching and abort processing.
    */
    public void shutdown();
}
