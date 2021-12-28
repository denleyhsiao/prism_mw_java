package Prism.extensions.evt;

import Prism.extensions.evt.RealTime.*;
import Prism.core.*;

/**
 * A subclass of Event provides extra capability on top of Event object. 
 * Extra capability can be selected by installing the appropriate extension. 
 * Installation of appropriate extension can be done by setting the
 * appropriate interface to the implementation of extensions. There are access
 * methods provided to allow installation of these extensions.
 */
public class ExtensibleEvent extends Event implements java.io.Serializable 
{
    private IRealTimeEvent theIRealTimeEvent = null;
    //private IDeliveryGuaranteeEvent theIDeliveryGuaranteeEvent = null;
    //private IXMLRepresentation theIXMLRepresentation = null;

    /**
     * Simple constructor that instantiates an Event object.
     *@param name name of the event
     */
    public ExtensibleEvent(String name)
    {
      super(name);
    }
    
    /**
     * Installs the real time extension.
     *@param realtimeEvent  The real time extension object
     */

    public void addRealTimeModule (IRealTimeEvent realTimeEvent)
    {
      theIRealTimeEvent = realTimeEvent;
    }

    /**
     * Return the installed real time extension object.
     *@return IRealTime The real time extension object
     */
    public IRealTimeEvent getIRealTime()
    {
      return theIRealTimeEvent;
    }

    /**
     * Creates a clone of this ExtensibleEvent.
     *@return Event Clone of this event
     */
    public Event replicate()
    {
      ExtensibleEvent eventClone = new ExtensibleEvent(super.name);

      eventClone.parameterName=super.parameterName;
      eventClone.parameterValue=super.parameterValue;
      eventClone.originatingBrick=super.originatingBrick;
      eventClone.handlingBrick=super.handlingBrick;
      eventClone.eventType=super.eventType;
      eventClone.addRealTimeModule(theIRealTimeEvent);
      //eventClone.addIDeliveryGuaranteeModule(theIDeliveryGuaranteeEvent);
      //evnetClone.addIXMLRepresentation(theIXMLRepresentation);

      return eventClone;
    }

}
