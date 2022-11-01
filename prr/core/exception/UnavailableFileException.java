package prr.core.exception;

/**
 * Exception for an unavailable File
 */
public class UnavailableFileException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** The requested filename. */
	private String _filename;

	/**
	 * @param filename name of the unavailable file
	 */
	public UnavailableFileException(String filename) {
		super("Erro a processar ficheiro " + filename);
		_filename = filename;
	}

	/**
	 * @return the requested filename
	 */
	public String getFilename() {
		return _filename;
	}
}
