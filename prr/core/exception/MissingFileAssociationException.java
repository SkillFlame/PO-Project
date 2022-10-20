package prr.core.exception;

/** Thrown when an application is not associated with a file. */
public class MissingFileAssociationException extends Exception {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public MissingFileAssociationException() {
		super("Ficheiro associado não encontrado ");

	}
}
