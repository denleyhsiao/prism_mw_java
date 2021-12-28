package Prism.extensions.port;

import Prism.extensions.port.distribution.*;
import Prism.extensions.port.compression.*;
import Prism.core.*;

/**
 * A subclass of Port provides extra functionality on top of Port object. 
 * Extra capability can be selected by installing the appropriate extension. 
 * Installation of appropriate extension can be done by done by setting the
 * appropriate interface to the implementation of extensions. There are access
 * methods provided to allow installation of these extensions.
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class ExtensiblePort extends Port
{

   private AbstractDistribution theDistribution = null;
   private AbstractCompression theCompression = null;   

   /**
    * This constructor instantiates an ExtensiblePort object and sets its 
    * parent's pointer to itself.
    * @param pPortType      Type of the port. For example: Request, Reply, ..
    * @param pBrick         Parent Brick of this port. For example: component,..
    */ 
   public ExtensiblePort(int pPortType, Brick pBrick)
   {
      super(pPortType, pBrick);
      super.setExtensiblePort (this);
   }

   /**
    * Returns the installed Compression extension.
    * @return AbstractCompression       installed compression extension
    */
   public AbstractCompression getCompressionModule ()
   {
      return theCompression;
   }

   /**
    * Calls the start method of all the implemented interfaces in the appropriate order.
    */
   public void start()
   {
      if (theDistribution !=null)
      {
              theDistribution.start();
      }

   }

   /**
    * The default handler, determines the type of processing needed to
    * be done. There are two types of processing. Processing event that is
    * incoming and processing event that is outgoing. 
    * @param e      event
    */
   public void handle (Event e)
   {
       if (portType == PrismConstants.REQUEST)
       {
          if (e.eventType == PrismConstants.REQUEST)
             this.handle(e, "OUT");
          else if (e.eventType == PrismConstants.REPLY)
              this.handle(e, "IN");
       }
       else if (portType == PrismConstants.REPLY)
       {
          if (e.eventType == PrismConstants.REPLY)
              this.handle(e, "OUT");
          else if (e.eventType == PrismConstants.REQUEST)
              this.handle(e, "IN");
       }
   }
   
   /**
    * Checks the type of extensions installed. Depending on the 
    * extensions installed the appropriate processing is done.
    *
    *@param eventObject     represents the event
    *@param direction       incoming vs outgoing
    */
   public void handle (Object eventObject, String direction)
   {
      if (direction.equals("IN"))
      {
          if (theCompression != null)
              eventObject = theCompression.processEvent(eventObject, direction);

          Event e = (Event) eventObject;
          e.originatingBrick = this;
          e.handlingBrick = parentBrick;
          add(e);
      }
      else
      {
          if (theCompression != null) {
                eventObject = theCompression.processEvent(eventObject, direction); }
          if (theDistribution != null) {
                theDistribution.writeEvent(eventObject); }
          else {                
                IPort tempMutualPort = (IPort)(this.getPorts()).getNext(); 
                ExtensiblePort mutualExtPort = tempMutualPort.getExtensiblePort();
                if (mutualExtPort != null)
                    mutualExtPort.handle(eventObject, direction);}
            }
        }


   /**
    * Installs the distribution extension.
    *
    *@param dist    distribution extension.
    */
   public void addDistributionModule(AbstractDistribution dist)
   {
	    theDistribution=dist;
   }
   
   /**
    * Installs the compression extension.
    *
    *@param compression    compression extension.
    */
   public void addCompressionModule (AbstractCompression compression)
   {
      theCompression = compression;
   }

   /**
    * Method for connecting to a remote host. Also initiates security key exchange
    * if security extension is installed.
    * 
    *@param hostName    host name or IP address of destination
    *@param portNum     port num of destination
    */ 
   public void connect(String hostName, int portNum)
   {
      if (theDistribution !=null)
      {
                Connection conn = theDistribution.connect(hostName,portNum);
      }
      else
      {
            System.out.println("Usage Error: Add Distribution module before connecting.");
            System.exit(0);
      }
   }
   
   public void disconnect(String hostName, int portNum)
   {
      if (theDistribution !=null)
      {
                 if (theDistribution.disconnect(hostName,portNum))
                 {
                     System.out.println("disconnected from: " + hostName + " " + portNum);
                 }
                 else
                     System.out.println("disconnect was unsuccessful");
      }
      else
      {
            System.out.println("Usage Error: Add Distribution module before connecting.");
            System.exit(0);
      }       
   }

}
