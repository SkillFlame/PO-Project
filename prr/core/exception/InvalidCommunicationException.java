package prr.core.exception;

/**
 * Class for representing an invalid Communication problem.
 */
public class InvalidCommunicationException extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public InvalidCommunicationException(){
        super("Communication is Invalid ");
    }
}
