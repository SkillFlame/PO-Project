package prr.core.exception;

import prr.core.TerminalMode;

public class StateAlreadySetException extends Exception{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private TerminalMode _mode;
    
    /**
	 * @param state the duplicated state
	 */
	public StateAlreadySetException(TerminalMode mode) {
		super("State already set:" + mode);
		_mode = mode;
	}

    /**
	 * @return the requested state
	 */
	public TerminalMode getState() {
		return _mode;
	}
}
