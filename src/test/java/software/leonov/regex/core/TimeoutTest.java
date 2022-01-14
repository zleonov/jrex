package software.leonov.regex.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

class TimeoutTest {

    @Test
    public void testMatchesWithTimeout() throws Throwable {

        try {
            JDKRegularExpression regex = JDKRegularExpression.compile("(a*)*");
            InputMatcher m = regex.matcher("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
            m.matches(3L, TimeUnit.SECONDS);
            fail("TimeoutException expected");
        } catch (final TimeoutException e) {
        }
    }

}
