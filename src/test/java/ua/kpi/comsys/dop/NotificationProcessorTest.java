package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NotificationProcessorTest {

    private NotificationProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new NotificationProcessor();
    }

    @Test
    @DisplayName("process() returns formatted Text Block for TextMsg")
    void processTextMsgSuccess() {
        Notification msg = new TextMsg("Alice", "Hello, world!");
        String expected = """
                Notification: TEXT
                From: Alice
                Message: Hello, world!""";

        assertEquals(expected, processor.process(msg));
    }

    @Test
    @DisplayName("process() returns formatted Text Block for ImageMsg")
    void processImageMsgSuccess() {
        Notification msg = new ImageMsg("Bob", "https://example.com/photo.png", 512);
        String expected = """
                Notification: IMAGE
                From: Bob
                URL: https://example.com/photo.png (512 KB)""";

        assertEquals(expected, processor.process(msg));
    }

    @Test
    @DisplayName("process() returns formatted Text Block for AudioMsg")
    void processAudioMsgSuccess() {
        Notification msg = new AudioMsg("Charlie", 45);
        String expected = """
                Notification: AUDIO
                From: Charlie
                Duration: 45 seconds""";

        assertEquals(expected, processor.process(msg));
    }

    @Test
    @DisplayName("process(null) throws IllegalArgumentException with exact message")
    void processNullThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> processor.process(null)
        );
        assertEquals("Notification cannot be null", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("TextMsg throws IllegalArgumentException for invalid sender")
    void textMsgInvalidSender(String invalidSender) {
        assertThrows(IllegalArgumentException.class, () -> new TextMsg(invalidSender, "Valid text"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("TextMsg throws IllegalArgumentException for invalid text")
    void textMsgInvalidText(String invalidText) {
        assertThrows(IllegalArgumentException.class, () -> new TextMsg("Alice", invalidText));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("ImageMsg throws IllegalArgumentException for invalid sender")
    void imageMsgInvalidSender(String invalidSender) {
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg(invalidSender, "https://example.com/img.jpg", 100));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("ImageMsg throws IllegalArgumentException for invalid url")
    void imageMsgInvalidUrl(String invalidUrl) {
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", invalidUrl, 100));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("ImageMsg throws IllegalArgumentException for non-positive sizeKb")
    void imageMsgInvalidSize(int invalidSize) {
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", "https://example.com/img.jpg", invalidSize));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("AudioMsg throws IllegalArgumentException for invalid sender")
    void audioMsgInvalidSender(String invalidSender) {
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg(invalidSender, 30));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -60})
    @DisplayName("AudioMsg throws IllegalArgumentException for non-positive durationSeconds")
    void audioMsgInvalidDuration(int invalidDuration) {
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg("Charlie", invalidDuration));
    }
}
