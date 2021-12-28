package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.evt.*;

public class testRealTimeReceivingComp extends Component 
{
    public testRealTimeReceivingComp(String name)
    {
        super(name);
    }

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
