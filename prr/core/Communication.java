package prr.core;

import java.io.Serializable;

public abstract class Communication implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private int _id;
	private boolean _isPaid;
	private double _cost;
	private boolean _isOngoing;
}
