package prr.core.exception;

/**
 * Class for representing an action regarding Terminals problem.
 */
public class ReceiverIsBusyException extends Exception{

	 /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public ReceiverIsBusyException(){
		super("Receiver mode is busy ");
	}
	
}
