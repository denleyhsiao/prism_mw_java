package Prism.test.style.clientserver;

import Prism.core.*;

public class Client extends AbstractImplementation implements Runnable
{    
    Thread myThread;
    
    public void start ()
    {
        myThread = new Thread(this);
        myThread.start();        
    }
    
    public void run() 
    {
        long currentTime;
        int i=1;
        while (true)  
        {        
            Prism.core.Event r = new Prism.core.Event ("ForServer"); 
            r.addParameter("EventNum", new Integer(i));
            r.eventType = PrismConstants.REQUEST;
            send(r);          
            System.out.println("Client sent event number: " + i);
            try
            {
                Thread.sleep(5000);
            }
            catch (Exception e) 
            { 
                System.err.println(e.getMessage()); 
            }            
            i++;
        }        
    }
    
    public void handle (Event e)
    {
        if (e.name.equalsIgnoreCase("ForClient"))
        {
            int responseNum = ((Integer)e.getParameter("EventNum")).intValue();
            System.out.println("Client received event number: " + responseNum);
        }
    }
    
}
