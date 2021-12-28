
package Prism.extensions.component;

import Prism.core.*;
import Prism.extensions.component.synchronism.*;

public class ExtensibleComponent extends Component
{
	AbstractCompSynchronism compSynchronism = null;
    	
	public ExtensibleComponent(String name) 
	{
		super(name);
	}
	
	public ExtensibleComponent(String name, int style, AbstractImplementation pImplementation)
	{
		super(name);
		super.style = style;
		implementation = pImplementation;
		implementation.setAssociatedComponent(this);		
	}
	
	public void addCompSynchronism (AbstractCompSynchronism pCompSynchronism)
	{
		compSynchronism = pCompSynchronism;
	}
    
	public void send(Event e)
	{		
		if (compSynchronism != null)
		{
			e = compSynchronism.lock(e);
			if (e == null)
				return;
		}		
		super.send(e);
	}
	
	public void handle (Event e)
	{
		if (compSynchronism != null)
		{	
			if (compSynchronism.unlock(e))
				implementation.handle(e);
		}
		else
			implementation.handle(e);
	}

}
