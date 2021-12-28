package Prism.test.components;

import Prism.core.AbstractImplementation;
import Prism.core.Event;
import Prism.core.PrismConstants;

public class Addition extends AbstractImplementation {
	public void handle(Event r) {
		if (r.getName().equals("add")) {
			String num1String = (String) r.getParameter("num1");
			String num2String = (String) r.getParameter("num2");

			int num1 = Integer.parseInt(num1String);
			int num2 = Integer.parseInt(num2String);

			int result = num1 + num2;

			Event n = new Event("Result", PrismConstants.REPLY);
			n.addParameter("result", new Integer(result));
			this.send(n);
		}
	}
}
