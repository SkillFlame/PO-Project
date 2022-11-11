package prr.core.exception;

/**
 * Class for representing a non-existent ongoing Communication problem.
 */
public class NoOngoingCommunicationException extends Exception{
    
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public NoOngoingCommunicationException(){
        super("No ongoing communication ");
    }
}
