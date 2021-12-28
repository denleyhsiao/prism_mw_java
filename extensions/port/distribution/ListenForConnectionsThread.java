package Prism.extensions.port.distribution;

import java.net.*;

/**
 * Listens for incoming connections. It is associated with a Parent distribution object. The distribution object instantiates this
 * to listen for incoming connections.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
 class ListenForConnectionsThread extends Thread  
{
    protected boolean keepWorking;
    private AbstractDistribution parentDistribution;
    private ServerSocket ss;
    	  
    /** 
     * Simple constructor.
     * @param parent Parent distribution object
     */
    public ListenForConnectionsThread(AbstractDistribution parent)
    {
		super();		
		parentDistribution =parent;
		keepWorking = true;
	
	}
    
    /**
     * Starts the thread that listens for incoming connections.
     */
    public void run() 
    {
    	try 
		{
			ss = new ServerSocket(parentDistribution.getListeningPortNum());
		}
		catch (Exception e) 
		{
			System.out.println("\n Exception in ServerSocket(): " + e.toString());
		}


		System.out.println("Ready to accept connections");

		Socket sock;
		
		while (keepWorking) 
		{
			try
			{
				sock = ss.accept();
				System.out.println("\n** Connect from: " + sock.getInetAddress() + ":" + sock.getPort());
						
				parentDistribution.addConnection(new Connection(parentDistribution, sock));

			}
			catch (Exception e) 
			{
				System.out.println("Exception while listening for connections:  " + e.toString());
			}
		 }    
	}

}


