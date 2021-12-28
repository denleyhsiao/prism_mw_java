package Prism.test.components;

import Prism.core.Component;
import Prism.core.Event;

public class HelloWorldClient extends Component {
	/**
	 * Each serializable class needs a serial UID.
	 */
	private static final long serialVersionUID = 3113279103482112358L;

	public static final int EVT_HELLO_WORLD = 101;

	public static final int HELLO_MESSAGE = 203;

	public HelloWorldClient(String name) {
		super(name);
	}

	public void handle(Event evt) {
		String msg = (String) evt.getParameter(Integer.toString(HelloWorldClient.HELLO_MESSAGE));
		System.out.println("Received message: " + msg);
	}
}
