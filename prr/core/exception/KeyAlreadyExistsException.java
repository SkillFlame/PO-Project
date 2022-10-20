package prr.core.exception;

/**
 * Class for representing an a duplicate key problem.
 */
public class KeyAlreadyExistsException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _key;

	/**
	 * @param key the duplicated key
	 */
	public KeyAlreadyExistsException(String key) {
		super("Key already exists:" + key);
		_key = key;
	}

	/**
	 * @return the requested key
	 */
	public String getKey() {
		return _key;
	}
}
