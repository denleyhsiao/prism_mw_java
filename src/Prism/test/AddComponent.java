package Prism.test;

import Prism.core.*;

public class AddComponent extends Component implements java.io.Serializable 
{
	
	public AddComponent(String name) 
	{
		super(name);
	}
	
	public void handle(Event r)
	{
		if (r.name.equals("add"))
		{
			String num1String = (String) r.getParameter ("num1");
			String num2String = (String) r.getParameter ("num2");
			
			int num1 = (Integer.parseInt(num1String));
			int num2 = (Integer.parseInt(num2String));
			
			int result = num1+num2;
			
			Event n=new Event("Result");
			n.addParameter ("result", new Integer (result));
                        n.eventType = PrismConstants.REPLY;
			send(n);
		}
	}
}
