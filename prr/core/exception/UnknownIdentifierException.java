package prr.core.exception;

/*Exception for Unknown identifiers on a List */

public class UnknownIdentifierException extends Exception{
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** Bad bad id specification. */
	private final int _idNotFound;

	/**
	 * @param entrySpecification
	 */
	public UnknownIdentifierException(int idNotFound) {
		_idNotFound = idNotFound;
	}

	/**
	 * @param idNotFound
	 * @param cause
	 */
	public UnknownIdentifierException(int idNotFound, Exception cause) {
		super(cause);
		_idNotFound = idNotFound;
	}

	/**
	 * @return the bad entry specification.
	 */
	public int getIdSpecification() {
		return _idNotFound;
	}

	//FIXME Finish this
}
