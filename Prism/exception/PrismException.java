package Prism.exception;

public class PrismException extends java.lang.Exception {

	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = -6524685855216071617L;

	private String message;

	/**
	 * Creates a new <code>PrismException</code> with no detail message.
	 */
	public PrismException() {
		this("");
	}

	/**
	 * Creates a new <code>PrismException</code> with the given detail
	 * message.
	 * 
	 * @param message
	 *            Detail message.
	 */
	public PrismException(String message) {
		this.message = message;
	}

	/**
	 * Gets the detail message for this exception.
	 * 
	 * @return Detail message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets a String representation of this error.
	 * 
	 * @return String representation of this error.
	 */
	public String toString() {
		return "PrismException:"
				+ (this.message == null ? "[no message]" : this.message);
	}

}
