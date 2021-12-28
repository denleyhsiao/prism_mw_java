package Prism.core;

/**
* A simple FIFO based message store which allows a dynamic set of queue to be handled in the architecture. 
* Messages are always added to the end of the queue whereas they are removed at the head of the queue. 
* There is no knowledge of priorities.
*
*@version 2.0
*@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
*/
public class FIFOScheduler implements AbstractScheduler {
   
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
    * @see C3.conn.FIFODispatch#DEFAULT_SIZE
    */

   public FIFOScheduler()
   {
      this(DEFAULT_SIZE);
   }
   /**
    * Create the event store
    */
   public FIFOScheduler(int FIFOSize)
   {
      size = FIFOSize;
      queue = new Object[size + 1];
      head = 0;
      tail = 0;
      emptySlots = FIFOSize;
   }

   /**
    * Add an event to the beginning of the queue. The method is synchronized so that simultaneous
    * additions and removals are not possible. This is a potential major bottleneck.
    * @param m		Event to be added
    */

   public synchronized void add(Event evt)
   {
	  try
	  {
         while (isQueueFull()) wait();
	  }
      catch (InterruptedException e)

      {
         System.out.println("Caught exception while waiting for queue to empty" + e);
         e.printStackTrace();
      }
      queue[tail] = evt;
      incrementTail();
      --emptySlots;
      notifyAll();
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



