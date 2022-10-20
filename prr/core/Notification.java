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

	/** Types of a Notification */
	enum NotificationType {
		O2S, O2I, B2S, B2I
	};

	Notification(String terminalId) {
		_type = null;
		_terminalId = terminalId;
	}

	/**
	 * toString implementaion of a Notification
	 * notificationType | idTerminal
	 */
	@Override
	public String toString() {

		return _type + "|" + _terminalId;
	}
}
