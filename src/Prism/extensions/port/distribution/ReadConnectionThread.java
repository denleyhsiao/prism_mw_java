
package Prism.extensions.port.distribution;
import Prism.core.*;
import java.io.*;
import java.lang.*;
import java.net.*;

import java.util.*;

/**
 * ReadConnectionThread class is used to read the socket continuosly.
 *
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */ 
 class ReadConnectionThread extends Thread
{
    /**
    * ReadConnectionThread will keep on running as long as keepWorking is true.
    */
     protected boolean keepWorking;
    
    /**
    * Connection to which this thread belongs
    */
     private Connection conn;
    
    /**
     * The StreamConnection class is used to instantiate a socket on a particular port
     */	
    private Socket streamConn;
		 
    private ObjectInputStream ir;
    
    /** 
    * This constructor instantiates a ReadConnectionThread on a particular socket.
    * @params parent  A connector that drives the ReadConnectionThread
    * @params socket  The socket on which the connection is made
    */
    public ReadConnectionThread(Connection conn,  Socket sc)
    {
        super();
	keepWorking = true;
	this.conn = conn;
	streamConn = sc;
	this.conn = conn;
	keepWorking = true;

	try
	{
            ir = new ObjectInputStream(streamConn.getInputStream());
	}
	catch (IOException e)
	{
            e.printStackTrace ();
	}

    }


    public void run()
    {
        Object eventObject = null;
    	try
    	{
            while (keepWorking)
            {
                eventObject = (Object) ir.readObject();
       		conn.readEvent(eventObject);
            } //while ends

            finalize1();
        }
  	catch (Exception e1)
  	{
            System.out.println("Caught Exception in ReadConnectionThread " + e1.toString());
            conn.close();
  	} // catch ends
    }

    public void finalize1()
    {
	try
	{
            ir.close();
            streamConn.close();
	}
	catch (IOException e){}        
        
    }
    public void stopWorking()
    {
            keepWorking = false;                    
    }
}
