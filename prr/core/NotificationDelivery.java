package prr.core;
/* Interface of the Notification Delivery Strategy Pattern */
public interface NotificationDelivery {
    void notifyTerminalO2S(Terminal target);
    void notifyTerminalO2I(Terminal target);
    void notifyTerminalB2I(Terminal target);
    void notifyTerminalS2I(Terminal target);
    
}
