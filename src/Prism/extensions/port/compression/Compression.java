package Prism.extensions.port.compression;

import Prism.core.*;
import java.util.zip.*;
import java.io.*;

/**
 * This class provide a utility for compression of outgoing event and decompression of incoming event.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Compression extends AbstractCompression
{

   /**
    * An empty constructor.
    */
   public Compression() 
   {
   }

   /**
    * This method is called from the ExtensiblePort. In this implementation, this method doesn't do anything.
    */     
   public void start()
   {
   }

   /** 
    * This method is called to compress or decomperss an event depending on the direction of the event.
    *
    *@param eventObj    event to be processed
    *@param direction   "IN" for decompression, "OUT" for compression
    */   
   public Object processEvent (Object eventObj, String direction)
   {
        if (direction.equals("IN"))
            return this.decompress(eventObj);
        else
            return this.compress(eventObj);
   }

   private byte [] compress (Object obj)
   {
      ByteArrayOutputStream tempbyte = null;
      try {

              tempbyte = new ByteArrayOutputStream();
              GZIPOutputStream tempgzip =  new GZIPOutputStream(tempbyte);
              ObjectOutputStream tempout = new ObjectOutputStream (tempgzip);
              tempout.writeObject(obj);
              tempgzip.finish();

              tempgzip.close();
              tempout.close();
          }
      catch (Exception exc){
              System.out.println("Exception in compression.compress: ");
          }

      return tempbyte.toByteArray();

   }

   private Event decompress (Object obj)
   {
      Event e = null;
      try {
              ByteArrayInputStream tempbyte = new ByteArrayInputStream( (byte[]) obj);
              GZIPInputStream tempgzip = new GZIPInputStream(tempbyte);
              ObjectInputStream tempir = new ObjectInputStream(tempgzip);
              e = (Event) tempir.readObject();

              tempbyte.close();
              tempgzip.close();
              tempir.close();
          }
      catch (Exception exc) {
              System.out.println("Exception in compression.decompress: ");
          }

      return e;
   }

}
