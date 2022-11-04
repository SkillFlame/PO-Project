package prr.core.exception;

public class ReceiverTerminalDoesNotSupportCommunicationException extends Exception{
		 /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private String _type;

	public ReceiverTerminalDoesNotSupportCommunicationException(String id, String type){
		super(id + " does not support communication type " + type);
		_id = id;
		_type = type;
	}

	/**
	 * @return the requested id
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @return the requested type
	 */
	public String getType() {
		return _type;
	}
	
}
