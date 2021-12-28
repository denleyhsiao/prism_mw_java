package Prism.extensions.architecture;

import Prism.core.*;
import Prism.extensions.architecture.topology.*;


public class ExtensibleArchitecture extends Architecture 
{
	private AbstractTopology topology;

	public ExtensibleArchitecture(String str) 
	{
	  super(str); 
	}	
	
	public ExtensibleArchitecture(String str, AbstractTopology pTopology)
	{
		super(str);
		topology = pTopology;
	}
	
	public ExtensibleArchitecture (String str, int pStyle)
	{
		super(str);
		style = pStyle;
	}
	
	public ExtensibleArchitecture(String str, AbstractTopology pTopology, int pStyle)
	{
		super(str);
		topology = pTopology;
		style = pStyle;
	}	
	
	public void weld(Brick b1, Brick b2) 
	{
		try
		{
			if (topology != null)
			{
				topology.weld(b1, b2);
			}
		}
		catch (PrismException e)
		{
				System.err.println(e.getMessage());

		}
	}

}
