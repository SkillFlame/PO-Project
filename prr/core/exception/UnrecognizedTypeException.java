package prr.core.exception;

public class UnrecognizedTypeException extends Exception{
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	public UnrecognizedTypeException() {
		super("Tipo não reconhecido ");
		
	}
}
