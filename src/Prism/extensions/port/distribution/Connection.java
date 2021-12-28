package Prism.extensions.port.distribution;

import Prism.core.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;

/**
 *Connection object used by the SocketDistribution to connect.
 */ 

public class Connection     //extends Brick implements IPortNames
 {
   	/**
	 * 
	 */	
	 
        private AbstractDistribution parent;
	/**
	 * Socket belonging to this connection
	 */
        private Socket socket;

        private ObjectOutputStream writer;


	/**
        * Thread that sits at the socket for this connection and creates new events when
        * they arrive
        */
        private ReadConnectionThread readConn;
        
        public Connection (AbstractDistribution p, String hostName, int portNum)
        {
                parent=p;
                try
                {
                    socket=new Socket(hostName,portNum);
                    writer = new ObjectOutputStream(socket.getOutputStream());

                    System.out.println("local port:"+socket.getLocalPort());
                    readConn = new ReadConnectionThread(this,  socket);
                    readConn.start();
                }
                catch (Exception e)
                {
                    System.out.println("\nException in Connection: constructor ->" + e.toString());
                    //this.reconnect();
                }
	}

        public Connection( AbstractDistribution p, Socket s)
	{
		parent = p;
		socket=s;
		try
		{
                    writer = new ObjectOutputStream(socket.getOutputStream());

                    readConn = new ReadConnectionThread(this,  socket);
                    readConn.start();
                }
		catch (IOException e)
                {
			System.out.println("\nException in Connection: constructor ->" + e.toString());
			//this.reconnect();
                }
	}

	public InetAddress getHost()
	{
		return this.socket.getInetAddress();
	}
	public int getPort()
	{
		return socket.getPort();
	}

        public AbstractDistribution getParentDistribution ()
        {
                return parent;
        }

	public synchronized void writeEvent(Object eventObject)
	{
		try
		{
                    writer.writeObject(eventObject);
                    writer.flush();
                    writer.reset();
                    System.out.println("Sent event: " + ((Event)eventObject).name);
		}
		catch(IOException ioe)
		{
                    ioe.printStackTrace ();
                    this.close();
                    //this.reconnect();
		}
		catch(Exception e1)
		{
                    e1.printStackTrace();
                    System.out.println("%%%%%error in writing event");
                    this.close();
                    //this.reconnect();			
                }
	}
        
 	public void readEvent(Object eventObject)
 	{
                System.out.println("Received event: " + ((Event)eventObject).name);
                parent.readEvent(eventObject); 
	}        

    	public void close()
    	{
                try
		{
                    writer.close();
                    readConn.stopWorking();
                    parent.removeConnection(this);
		}
		catch (Exception e)
	    	{
                    System.out.println(" Connection: Caught exception in close() " + e.toString());
	    	}
    	}

	public void teardown()
    	{
    		try
		{
			writer.close();
			readConn.stopWorking();
		}
		catch (Exception e)
	    	{
	    		System.out.println("TBC Connection: Caught exception in teardown() " + e.toString());
	    	}
    	}



}



