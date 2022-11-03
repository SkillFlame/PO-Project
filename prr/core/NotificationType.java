package prr.core;

public interface NotificationType {
    void notifyTerminal(Terminal target);
    String typeToString();
}
