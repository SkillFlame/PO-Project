package prr.core.exception;

public class ReceiverIsNotIdleException extends Exception{
    
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public ReceiverIsNotIdleException(){
        super("Receiver mode is busy or silent ");
    }
}
