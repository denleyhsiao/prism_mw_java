package Prism.core;

/**
 * Brick is the abstract building block for architectures. It is never used directly, but instantiated as 
 * either a component, connector, or port. This class does not have a behavior of its own, but depends on sub classes
 * to handle messages. 
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class Brick implements java.io.Serializable 
{ 
   public transient AbstractScaffold scaffold=null;
   public String name;
   protected int style;
   
   /**
    * A simple constructor to store the brick  name.
    *
    * @param bName   Name of the brick
    */
   public Brick (String bName)
   {
	   name=bName;
   }

   public int getStyle()
   {
		return style;
   }
   
  /**
    * By default a brick does not do anything. The start method is called to activate this brick
    */
   public void start()
   {
   }
   
   /**
    * Add a message to the scaffold which results in a message getting added to the scaffold's queue.
    * @param event     Event object to be added
    */
    public void add(Event event) 
    {
		if (event.originatingBrick==null)
		{
	        	event.originatingBrick=this;
		}
		if (scaffold != null) 
		{
			scaffold.call("add", event);
	    }
    }

   /**
    * This abstract method needs to be overriden by any object that extends Brick. The subclassing object shall
    * provide the desired implementation to handle an Event.
    *
    * @param e      Event object to be handled
    */
   public abstract void handle(Event e);
         
}
