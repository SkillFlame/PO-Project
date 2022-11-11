package prr.core.exception;

/**
 * Class for representing an action regarding Terminals problem.
 */
public class ReceiverIsSilentException extends Exception{

	 /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public ReceiverIsSilentException(){
		super("Receiver mode is silent ");
	}
	
}
