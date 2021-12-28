package Prism.core;
/**
 * AbstractScheduler defines an abstract class for performing architectural level scheduling of events. This basically comes down to the ordering
 * policy of events in the queue.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface AbstractScheduler 
{
    /**
    * Add a message to the list in an arbitrary order.
    * @param e		Event object to be added to the list
    */
    public void add(Event e);

    /**
    * Get the number of messages waiting to be dispatched.
    * @return int		Number of waiting messages
    */
    public int getWaitingLength();

    /**
    * Get the next message to be dispatched in an arbitrary order. The order is decided by the 
    * class implementing this interface
    * @return Event		the event object for dispatched event
    */
    public Event getEvent();

    /**
    * Set the capacity of messages that can be stored before being dispatched
    * @param n		int the required capacity of the message store 
    */
    public void setEventCapacity(int n);
}
