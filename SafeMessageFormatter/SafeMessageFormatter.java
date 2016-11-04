import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

public class SafeMessageFormatter {
    protected static final Logger log = LoggerFactory.getLogger(SafeMessageFormatter.class);

    public static String format(String text, Object... args) {
        try {
            return MessageFormat.format(text, args); // Can throw IllegalArgumentException.
        } catch (Throwable e) {
            String argsJoined = Arrays.toString(args);
            log.error("Could not format text: " + text + " with args " + argsJoined);
            return text + " [Could not format text. Args: " + argsJoined + ". Details: " + e.getMessage() + "]";
        }
    }
}
