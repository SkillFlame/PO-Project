package prr.core.exception;

/**
 * Class for representing an already established action regarding Notifications problem.
 */
public class NotificationsAlreadyDisabledException extends Exception{
    
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public NotificationsAlreadyDisabledException(){
        super("Notifications already disabled ");
    }
}
