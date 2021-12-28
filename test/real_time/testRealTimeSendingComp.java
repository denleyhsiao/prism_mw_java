package Prism.test.real_time;

import Prism.core.*;
import Prism.extensions.evt.*;
import Prism.extensions.evt.RealTime.*;

public class testRealTimeSendingComp extends AbstractImplementation
{
    public void test()
    {
        int i;
        double randomValue;
        int nrtCount = 0, rtCount = 0;
        for (i=0; i<50; i++)
        {
            randomValue=100*(Math.random());
            if (randomValue<50)
            {
                Event evt = new Event ("nonRealTime");
                evt.addParameter("timeStamp", new Long(System.currentTimeMillis()));
                evt.eventType = PrismConstants.REQUEST;
                send(evt);
                nrtCount++;
            }
            else if (randomValue>=50)
            {

                ExtensibleEvent extEvt = new ExtensibleEvent("realTime");
                extEvt.addRealTimeModule(new RealTimeEvent( (long) (5000*(Math.random())), (long) (500)) );
                extEvt.addParameter("timeStamp", new Long (System.currentTimeMillis()));
                extEvt.eventType = PrismConstants.REQUEST;
                send(extEvt);
                rtCount++;
            }
            System.out.print(".");

            /*          try {
                Thread.sleep(500);
              }
              catch (Exception e)
              {}
            */          

        } // end of for

        System.out.println();
        System.out.println("nrtcount = " + nrtCount + "   rtCount = " + rtCount);
        System.out.println();
    }
    
    public void handle(Event e)
    {}
  
}
