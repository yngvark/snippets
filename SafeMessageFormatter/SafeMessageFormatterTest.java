import org.junit.Test;

import static org.junit.Assert.*;

public class SafeMessageFormatterTest {
    @Test
    public void should_format_correctly() throws Exception {
        // When
        String formatted = SafeMessageFormatter.format("hei {0} {1}", "på", "deg");

        // Then
        assertEquals("hei på deg", formatted);
    }

    @Test
    public void should_output_sensible_message_if_formatting_is_not_possible() throws Exception {
        // When
        String formatted = SafeMessageFormatter.format("Dato {0, dato} og tall {1, number}", "not a date", "*100*");

        // Then
        assertEquals("Dato {0, dato} og tall {1, number} [Could not format text. Args: [not a date, *100*]. Details: unknown format type: dato]", formatted);
    }
}
