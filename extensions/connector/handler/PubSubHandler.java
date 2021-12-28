package Prism.extensions.connector.handler;

import Prism.core.*;
import java.util.Vector;
import java.util.Hashtable;

public class PubSubHandler  extends AbstractHandler {
	private Vector ports;
	private IConnector parentConnector;
	private Hashtable subscriptions;
	public PubSubHandler()
	{
		subscriptions=new java.util.Hashtable();	
	}
	public synchronized void handle(Event e)
	{		
		String evtName = null;
		if (e.name.equals("Subscription"))
		{
			evtName = (String)e.getParameter("EventName");
			Vector v=(Vector)subscriptions.get(evtName);
			if (v==null) 
				v=new Vector(10);
			v.add(e.originatingBrick);
			subscriptions.put(evtName,v);
		}
		else if (e.name.equals("Unsubscription"))
		{
			evtName = (String)e.getParameter("EventName");			
			Vector v=(Vector)subscriptions.get(evtName);
			int i=v.indexOf(e.originatingBrick);
			v.remove(i);
			subscriptions.put(evtName,v);
		}
		else
		{
			Vector v=(Vector)subscriptions.get(e.name);
			if (v!=null)
			{
				for (int i=0;i<v.size();i++)
				{
					((Brick)v.elementAt(i)).handle(e);
				}
			}
		}    
    }
	/**
	 * Sets the connector to which this handler belongs
	 * @param conn		 an IConnector  to which the handler belongs
	 */
	public void setParentConnector(IConnector conn){parentConnector=conn;}
}
