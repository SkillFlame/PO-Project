package prr.core.exception;

/**
 * Class for representing an already established action regarding Notifications problem.
 */
public class NotificationsAlreadyEnabledException extends Exception{
    
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public NotificationsAlreadyEnabledException(){
        super("Notifications already enabled");
    }
}
