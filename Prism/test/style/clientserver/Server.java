package Prism.test.style.clientserver;

import Prism.core.*;

public class Server extends AbstractImplementation
{    
    public void handle(Event e)
    {
        if (e.name.equalsIgnoreCase("ForServer"))
        {
            int requestNum = ((Integer)e.getParameter("EventNum")).intValue();            
            System.out.println("Server received event num: " + requestNum);
            Double eventID = (Double)e.getParameter("EventID");
            
            try
            {
                Thread.sleep(15000);
            }
            catch (Exception exc) 
            { 
                System.err.println(exc.getMessage()); 
            }                        
            
            Event r = new Event ("ForClient"); 
            r.addParameter("EventID", eventID); 
            r.addParameter("EventNum", new Integer(requestNum));
            r.eventType = PrismConstants.REPLY;
            send(r);                                  
        }
    }
}
