package ua.kpi.comsys.dop;

public record ImageMsg(String sender, String url, int sizeKb) implements Notification {
    public ImageMsg {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender cannot be null or blank");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or blank");
        }
        if (sizeKb <= 0) {
            throw new IllegalArgumentException("Size in KB must be greater than 0");
        }
    }
}
