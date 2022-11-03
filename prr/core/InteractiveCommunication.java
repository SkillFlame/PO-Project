package prr.core;

/**
 * Specialization of a Communication by its interactibility
 */
public abstract class InteractiveCommunication extends Communication{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	int _duration;

	@Override
	int getSize() {
		if(getCommunicationState()){
			return 0;
		}
		return _duration;
	}

}
