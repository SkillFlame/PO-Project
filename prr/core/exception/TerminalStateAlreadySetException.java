package prr.core.exception;


public class TerminalStateAlreadySetException extends Exception{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _mode;
    
    /**
	 * @param state the duplicated state
	 */
	public TerminalStateAlreadySetException(String mode) {
		super("State already set:" + mode);
		_mode = mode;
	}

    /**
	 * @return the requested state
	 */
	public String getState() {
		return _mode;
	}
}
