package prr.core;

import java.io.Serializable;

/**
 * Implementation of a Notification and its possible types
 */
public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private NotificationType _type;
	private String _terminalId;
	private boolean _hasNotified;


	

	Notification(String terminalId, NotificationType type){
		_type = type;
		_terminalId = terminalId;
	}

	NotificationType getType(){
		return _type;
	}

	/**
	 * toString implementaion of a Notification
	 * notificationType | idTerminal
	 */
	@Override
	public String toString() {

		return _type.typeToString() + "|" + _terminalId;
	}
}
