package prr.core.exception;

public class StateAlreadySetException extends Exception{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _state;
    
    /**
	 * @param state the duplicated state
	 */
	public StateAlreadySetException(String state) {
		super("State already set:" + state);
		_state = state;
	}

    /**
	 * @return the requested state
	 */
	public String getState() {
		return _state;
	}
}
