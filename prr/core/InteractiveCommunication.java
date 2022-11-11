package prr.core;

/**
 * Specialization of a Communication by its interactibility
 */
public abstract class InteractiveCommunication extends Communication {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	int _duration;


	public InteractiveCommunication(Terminal sender, Terminal receiver) {
		super(sender, receiver);
		setIsOngoing(true);
	}


	@Override
	void setSize(int size) {
		_duration = size;
	}


	/** 
	 * Gets the duration of the Interactive Communication made
	 */
	@Override
	int getSize() {
		if (getIsOngoing()) {
			return 0;
		}
		return _duration;
	}

}
