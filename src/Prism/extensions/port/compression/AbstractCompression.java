package Prism.extensions.port.compression;

import Prism.core.*;

/**
 * Any compression implementation in Prism needs to extend this abstract class.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public abstract class AbstractCompression
{
   /**
    * This method is called from the ExtensbilePort. Any initialization of compression module occurs here.
    */    
   public abstract void start();
   /** 
    * This method is called to compress or decomperss an event depending on the direction of the event.
    *
    *@param eventObj    event to be processed
    *@param direction   "IN" for decompression, "OUT" for compression
    */
   public abstract Object processEvent(Object eventObj, String direction); 

}
