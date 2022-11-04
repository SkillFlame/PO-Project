package prr.core.exception;

public class ReceiverIsSilentException extends Exception{

	 /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public ReceiverIsSilentException(){
		super("Receiver mode is silent ");
	}
	
}
