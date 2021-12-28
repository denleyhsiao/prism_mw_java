package Prism.test.real_time;

import Prism.core.*;

public class testRealTimeReceivingComp extends AbstractImplementation
{
    public void handle(Event evt)
    {
        if (evt.name.equals("realTime"))
        {
                Long timeStamp = (Long) evt.getParameter ("timeStamp");
                System.out.println("Real Time Event with original time stamp: " + timeStamp.toString() +
                     " received at: " + System.currentTimeMillis());
        }
        else if (evt.name.equals("nonRealTime"))
        {
                Long timeStamp = (Long) evt.getParameter ("timeStamp");
                System.out.println("Non Real Time Event with original time stamp: " + timeStamp.toString() +
                         " received at: " + System.currentTimeMillis());
        }
    }
  
}
