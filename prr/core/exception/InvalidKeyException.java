package prr.core.exception;

/**
 * Class for representing an invalid Key problem.
 */
public class InvalidKeyException extends Exception{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private String _key;

	/**
	 * @param key invalid key
	 */
	public InvalidKeyException(String key) {
		super("Key is invalid:" + key);
		_key = key;
	}

	/**
	 * @return the requested key
	 */
	public String getKey(){
		return _key;
	}
}
