package prr.core.exception;

/**
 * Class for representing an a duplicate key problem.
 */
public class DuplicateNotificationException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/**
	 * @param key the duplicated key
	 */
	public DuplicateNotificationException() {
		super("Notification already exists");
	}
}