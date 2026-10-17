package ua.kpi.comsys.dop;

public class NotificationProcessor {

    public String process(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }

        return switch (notification) {
            case TextMsg(String sender, String text) -> """
                    Notification: TEXT
                    From: %s
                    Message: %s""".formatted(sender, text);
            case ImageMsg(String sender, String url, int sizeKb) -> """
                    Notification: IMAGE
                    From: %s
                    URL: %s (%d KB)""".formatted(sender, url, sizeKb);
            case AudioMsg(String sender, int durationSeconds) -> """
                    Notification: AUDIO
                    From: %s
                    Duration: %d seconds""".formatted(sender, durationSeconds);
        };
    }
}
