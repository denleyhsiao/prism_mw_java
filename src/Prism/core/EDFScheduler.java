package Prism.core;

import Prism.extensions.evt.RealTime.*;
import Prism.extensions.evt.*;

/**    
 *A Priority based message store which allows a dynamic set of queue to be handled. Messages are scheduled based on their priority.
 * Higher priority messages are added to the head of the queue, while lower priority messages are added to the end of the queue.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class EDFScheduler implements IScheduler {

    /**
    * Store for the queue in the FIFO organized as a circular queue
    */
   Object queue[];
   
   /**
    * By default a 250 event queue is assumed.
    */
   final static int DEFAULT_SIZE = 250;

   /**
    * Current capacity of the message queue
    */
   int size;

   /**
    * Keep track of the number of empty slots in the queue
    */
   private int emptySlots;

   /**
    * The location of the head of the FIFO
    */
   int head;

   /**
    * The location of the tail of the FIFO
    */
   
   int tail;
   
   /**
    * Create the event store with DEFAULT_SIZE elements  
    */
   public EDFScheduler()
   {
      this(DEFAULT_SIZE);
   }
   
   /**
    * Create the event store
    */
   public EDFScheduler(int pSize)
   {
      size = pSize;
      queue = new Object[size + 1];
      head = 0;
      tail = 0;
      emptySlots = size;
   }

    /**
    * Increments the circular queue by one. This is a potential major bottleneck.
    * @param i  index of location to be incremented
    */
        private synchronized int increment(int i)
        {

                if(i == size-1)
                        return 0;
                else
                        return ++i;

        }

       /**
        * Decrements the circular queue by one. This is a potential major bottleneck.
        * @param i  index of location to be decremented
        */
	private synchronized int decrement(int i)
	{
		int temp = i;
		if(i == 0){
			temp = size-1;
		}
		else{
			temp=i-1;
		}
		//System.out.println("TEMP In Decrement " + temp );
		return temp;
	}

        
    /**
     * Adds a new event to the queue. Location of new event is determined 
     * according to its priority.
     *@param evt    Event to be added
     */
       public synchronized void add(Event evt)
       {
          int i;
              try
              {
               while (isQueueFull())
               {
                  wait();
                  System.out.println("waiting for the queue to get empty");
               }
              }
          catch (InterruptedException e)
          {
               System.out.println("Caught exception while waiting for queue to empty" + e);
               e.printStackTrace();
          }

          if (evt instanceof ExtensibleEvent)   // extensible event
          {
              if ( ((ExtensibleEvent)evt).getIRealTime() == null)   // if Real-Time module isn't set, it must be non real-time
              {
                  i=tail;
              }
              else // real-time
              {
                  i=head;
                  while (i != tail)
                  {
                      if (queue[i] instanceof ExtensibleEvent)
                      {
                          ExtensibleEvent thisEvent = (ExtensibleEvent) queue[i];
                          if (thisEvent.getIRealTime() != null)
                          {
                              if ( deadlineGT(thisEvent, (ExtensibleEvent)evt) )   // if thisEvent has longer deadline than evt
                                  {
                                      break;
                                  }
                          }
                          else    // Real-Time module isn't set, it must be non real-time
                              break;
                      }
                      else  // regular event and by default non real-time
                          break;

                      i = increment(i);
                  }
              }
          }
          else    // regular event and by default non real-time
          {
              i=tail;
          }

                            int j = tail;
                            while(j != i)
          {
                      int k = j;
                      k = decrement(k);
                      queue[j] = queue[k];
                      j = decrement(j);
            }
            queue[i] = evt;

          incrementTail();
          --emptySlots;
          notifyAll();
       }

	private  boolean deadlineGT(ExtensibleEvent queueEvt, ExtensibleEvent newEvt)
	{

		long totalTime1 = 0;
		long totalTime2 = 0;

                IRealTimeEvent evt1 = queueEvt.getIRealTime();
                IRealTimeEvent evt2 = newEvt.getIRealTime();

		totalTime1 = evt1.getTimeStamp() + evt1.getDeadlineTime() + evt1.getBufferTime();
                totalTime2 = evt2.getTimeStamp() + evt2.getDeadlineTime() + evt2.getBufferTime();

		if(totalTime1 > totalTime2)
			return true;
		else
			return false;
	}

   /**
    * Get a message from the head of the queue and remove it from the message store, although the
    * message object itself is not deleted.
    * @return Message		The message object at the head of the FIFO
    */
   public synchronized Event getEvent()
   {
      Event m = null;
      try
      {
         while (isQueueEmpty())
            wait();
      }
      catch (Exception e)
      {
         System.out.println("Caught exception while waiting for queue to fill" + e);
         e.printStackTrace();
      }
      m = (Event) queue[head];
      queue[head]=null;
      incrementHead();
      ++emptySlots;
      notifyAll();
      return m;
   }   

   /**
    * Get the number of queued events waiting to be dispatched
    * @return int		Number of queue remaining
    */
   public synchronized int getWaitingLength()
   {
      return size - emptySlots;
   }
   
   /**
    * Get a string representation of the message queue. This is used primarily for debugging
    * @return String 	The stringified representation of the queue.
    */
   public String toString()
   {
      String msg = new String();
      if (head == tail)
         return "Empty";
      else
         msg = ((Event) queue[head]).name;
      int start = head + 1;
      if (start > size)
         start = 0;
      for (int i = start; ; i++)
      {
         if (i == tail)
            break;
         msg += ", " + ((Event) queue[i]).name;
         if (i == size)
            i = -1;
      }
      return msg;
   }
   
   /**
    * Set the capacity of messages that can be stored before being dispatched
    * @param n		int the required capacity of the message store
    */
   public void setEventCapacity(int n)
   {
      // do not do anything as we have hardcoded number of messages for now
   }

   private boolean isQueueEmpty()
   {
      return (emptySlots == size);
   }

   private boolean isQueueFull()
   {
      return (emptySlots == 1);
   }

   private void incrementTail()
   {
     if (tail == size - 1)
         tail = 0;
     else
        ++tail;
  }

   private void incrementHead()
   {
      if (head == size - 1)
         head = 0;
      else
         ++head;
   }

}



