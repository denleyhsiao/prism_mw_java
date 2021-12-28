package Prism.core;

public abstract class AbstractImplementation 
{
	protected IComponent associatedComp;
	public void send(Event e)
	{
		if (associatedComp != null)
		{
			associatedComp.send(e);
		}
	}

	public void setAssociatedComponent (IComponent comp)
	{
		associatedComp = comp;
	}
	
	public IComponent getAssociatedComponent ()
	{
		return associatedComp;
	}
	
	public void start()
	{		
	}
	public abstract void handle(Event e);
	
}
