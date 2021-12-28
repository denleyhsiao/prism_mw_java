package Prism.core;

public class PrismException extends Exception {
	
	/**
	 * Each serializable class needs a serial UID.  
	 */
	private static final long serialVersionUID = 390335869386511290L;

	public PrismException() {
	}

	public PrismException(String errorMsg) {
		super(errorMsg);
	}
}
