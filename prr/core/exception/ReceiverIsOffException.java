package prr.core.exception;

public class ReceiverIsOffException extends Exception{

     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public ReceiverIsOffException(){
        super("Receiver mode is off ");
    }
    
}
