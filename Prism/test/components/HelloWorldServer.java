package Prism.test.components;

import Prism.core.Component;
import Prism.core.Event;
import Prism.core.PrismConstants;

public class HelloWorldServer extends Component {
	/**
	 * Each serializable class needs a serial UID.
	 */
	private static final long serialVersionUID = 684663704506757675L;

	public static final int EVT_HELLO_WORLD = 101;

	public static final int HELLO_MESSAGE = 203;

	public HelloWorldServer(String name) {
		super(name);
		this.sendMessage();
	}

	public void sendMessage() {
		String msg = "Hello World!";

		for (int i = 0; i < 5; i++) {
			Event e = new Event(PrismConstants.REPLY);
			e.setName(Integer.toString(HelloWorldServer.EVT_HELLO_WORLD));
			e.addParameter(Integer.toString(HelloWorldServer.HELLO_MESSAGE), msg);

			this.send(e);
		}
	}
}
