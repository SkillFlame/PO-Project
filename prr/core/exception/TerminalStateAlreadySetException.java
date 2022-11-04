package prr.core.exception;

public class TerminalStateAlreadySetException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/**
	 * @param state the duplicated state
	 */
	public TerminalStateAlreadySetException() {
		super("State already set");

	}

}
