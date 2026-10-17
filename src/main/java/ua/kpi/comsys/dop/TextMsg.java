package ua.kpi.comsys.dop;

public record TextMsg(String sender, String text) implements Notification {
    public TextMsg {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender cannot be null or blank");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be null or blank");
        }
    }
}
