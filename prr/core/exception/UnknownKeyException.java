package prr.core.exception;

/**
 * Exception for unrecognized Key entries.
 */
public class UnknownKeyException extends Exception{
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _key;

	// @param key Unknown key to report. */
	public UnknownKeyException(String key) {
		super("Unknown key:" + key);
		_key = key;
	}

	/**
	 * @return the requested key
	 */
	public String getKey() {
		return _key;
	}
}

