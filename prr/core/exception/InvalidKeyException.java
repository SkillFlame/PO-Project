package prr.core.exception;

public class InvalidKeyException extends Exception{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private String _key;

	public InvalidKeyException(String key) {
		super("Key is invalid:" + key);
		_key = key;
	}

	public String getKey(){
		return _key;
	}
}
