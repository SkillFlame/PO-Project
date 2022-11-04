package prr.core;

import java.io.Serializable;

/**
 * Implementation of a Notification and its possible types
 */
public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private NotificationDelivery _method;
	private Terminal _target;
	private String _type;

	Notification(Terminal target, NotificationDelivery method, String type){
		_method = method;
		_target = target;
		_type = type;
	}

	NotificationDelivery getMethod(){
		return _method;
	}

	String getType(){
		return _type;
	}


	/**
	 * toString implementaion of a Notification
	 * notificationType | IdTerminal
	 */
	@Override
	public String toString() {

		return _type + "|" + _target.getId();
	}
}
