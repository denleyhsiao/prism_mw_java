package Prism.test.style.c2;

import Prism.core.*;

public class Subtract extends AbstractImplementation
{	
	public void handle(Event r)
	{
		if (r.name.equals("sub"))
		{
			String num1String = (String) r.getParameter ("num1");
			String num2String = (String) r.getParameter ("num2");
			
			int num1 = (Integer.parseInt(num1String));
			int num2 = (Integer.parseInt(num2String));
			
			int result = num1-num2;
			
			Event n=new Event("Result");
			n.addParameter ("result", new Integer (result));
			n.eventType = PrismConstants.REPLY;
			send(n);
		}
	}
}
