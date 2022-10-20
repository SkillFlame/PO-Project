package prr.core.exception;

public class UnknownKeyException extends Exception{
	/** Serial number (serialization) */
	private static final long serialVersionUID = 202208091753L;

	private String _key;

	/** @param key Unknown key to report. */
	public UnknownKeyException(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}
}
