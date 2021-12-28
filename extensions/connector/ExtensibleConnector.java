package Prism.extensions.connector;

import Prism.core.*;
import Prism.extensions.connector.handler.*;


public class ExtensibleConnector extends Connector 
{
	private AbstractHandler evtHandler;
	
	public ExtensibleConnector(String name) 
	{
		super(name);
	}
	public ExtensibleConnector (String name, AbstractHandler handler, int style)
	{
		super(name, style);
		evtHandler = handler;
		evtHandler.setParentConnector(this);   		
	}

	/**
	 * Sets the IHandler of this connector.
	 * @param pHndl       IHandler object that determines the distribution policy
	 */
	public void setEvtHandler(AbstractHandler pHndl)
	{
	  evtHandler = pHndl;
	  evtHandler.setParentConnector(this);
	}
   
	/**
	 * Gets the IHandler of this connector.
	 * @return IHandler   IHandler object that determines the distribution policy
	 */
	public AbstractHandler getEvtHandler()
	{
		return evtHandler;
	}
	
	/**
	 * This method distributes the incoming event to connected Bricks. Distribution
	 * policy depends on the type of IHandler that is installed.
	 * @param e      Incoming Event
	 */
	public void handle (Event e)
	{
		if (evtHandler != null)
		{
			evtHandler.handle(e);
		}
	}	
		
}
