package Prism.core;

import java.util.Vector;

/**
 * Event represents the basic message that is used by Bricks to communicate.
 *  
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Event  implements java.io.Serializable 
{
    public String name;
    public Vector parameterName = null;
    public Vector parameterValue = null;

    /**
    * Brick that created this event
    */
    public transient Brick originatingBrick;

    /**
    * Brick through which this event should be sent
    */
    public transient Brick handlingBrick;

    /**
    * Event type. Could be a request or reply.
    */
    public int eventType;

    public final static int EVENT_INITIAL_LENGTH = 2;

    public Event() 
    {
    } 

    public Event(String str, Brick issuer, IConnector rc)
    {
        name = str;
        originatingBrick=issuer;
        handlingBrick = (Brick)rc;
    }
   
    public Event(String str)
    {
        name = str;
    }

    /**
    * Adds a name-value pair to the event object.
    * @param name			String name of the value being stored
    * @param value			Object that contains a Java object for the value	
    */
    public void addParameter(String name, Object value)
    {
        if (parameterName==null){
                parameterName = new Vector(EVENT_INITIAL_LENGTH);
                parameterValue = new Vector(EVENT_INITIAL_LENGTH);
        }
        parameterName.addElement(name);
        parameterValue.addElement(value);
    }

    /**
    * Checks to see if the event contains a name-value pair identified by name.
    * @return Boolean 	true if the event contains parameter Name and false if not.
    */
    public boolean hasParameter(String name)
    {
        if (parameterName.contains(name))
            return true;
        else
            return false;
    }

    /**
    * Gets a parameter from the event object.
    * @param name			String identifier for the name-value pair being read
    * @return Object		The value being requested. If not found, null is returned
    */
    public Object getParameter(String name)
    {
        if (parameterName.contains(name))
            return parameterValue.elementAt(parameterName.indexOf(name));
        
        return null;
    }

    /**
    * Removes a name-value pair from the event object.
    * @param name			String the name of the value being removed from the message
    */
    public void removeParameter(String name)
    {		
        if (parameterName.contains(name))
        {
            parameterValue.removeElementAt(parameterName.indexOf(name));
            parameterName.removeElement(name);
        }
    }

    /**
     * Creates a new Event object that is the exact replica of this Event object.
     *@return Event     new cloned Event object
     */
    public Event replicate()
    {
        Event e1=new Event(name);
        e1.parameterName=parameterName;
        e1.parameterValue=parameterValue;
        e1.originatingBrick=originatingBrick;
        e1.eventType = eventType;
        return e1;
    }

}

