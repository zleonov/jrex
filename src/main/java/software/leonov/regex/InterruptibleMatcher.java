package software.leonov.regex;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static software.leonov.common.util.Unchecked.unchecked;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import software.leonov.common.util.Unchecked;

/**
 * Performs match and replace operations by interpreting a regular-expression.
 * <p>
 * Extending classes are expected to wrap the <i>matching</i> functionality of the underlying regular-expression
 * implementation, throwing {@code UnsupportedOperationException}s if a certain functionality is not supported.
 * <p>
 * Instances of this class can be obtained by calling the {@link RegularExpression#matcher(String)} method.
 * 
 * @author Zhenya Leonov
 */
public class InterruptibleMatcher<T> implements InputMatcher<T> {

    private final InputMatcher<T> matcher;
    private final MatchOperation matches;
    private final MatchOperation find;

    protected InterruptibleMatcher(final InputMatcher<T> matcher) {
        checkNotNull(matcher, "matcher == null");
        this.matcher = matcher;

        matches = new MatchOperation(Unchecked.eval(() -> matcher.find()));
        find = new MatchOperation(Unchecked.eval(() -> matcher.find()));
    };

    @Override
    public int end() {
        return matcher.end();
    }

    @Override
    public int end(final int index) {
        return matcher.end(index);
    }

    @Override
    public String group() {
        return matcher.group();
    }

    @Override
    public String group(final int index) {
        return matcher.group(index);
    }

    @Override
    public int groupCount() {
        return matcher.groupCount();
    }

    @Override
    public int start() {
        return matcher.start();
    }

    @Override
    public int start(final int index) {
        return matcher.start(index);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public boolean matches() throws Exception {
        return matches(Duration.ZERO);
    }

    /**
     * Spends at most {@code duration} time attempting to match the entire string against the regular-expression. If the
     * specified duration is exceeded this method will throw a {@code TimeoutException}.
     * 
     * @param duration the time to wait for the operation to complete before abandoning it, a value of {@code 0} indicates
     *                 to wait forever
     * @return {@code true} if, and only if, the entire input string matches
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws Exception            if any other errors occurs
     */
    public boolean matches(final Duration duration) throws Exception {
        return resultOf(matches, duration);
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code true} if, and only if, a substring of the input string is a match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public boolean find() throws Exception {
        return find(Duration.ZERO);
    }

    /**
     * Spends at most {@code duration} time attempting to find the next substring of the input string that matches the
     * regular-expression. If the specified duration is exceeded this method will throw a {@code TimeoutException}.
     * 
     * @param duration the time to wait for the operation to complete before abandoning it, a value of {@code 0} indicates
     *                 to wait forever
     * @return {@code true} if, and only if, a substring of the input string is a match
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public boolean find(final Duration duration) throws Exception {
        return resultOf(find, duration);
    }

    @Override
    public RegularExpression pattern() {
        return matcher.pattern();
    }

    @Override
    public void reset() {
        matcher.reset();
    }

    @Override
    public T delegate() {
        return matcher.delegate();
    }

    @Override
    public InterruptibleMatcher<T> appendReplacement(final StringBuilder sb, final String replacement) {
        matcher.appendReplacement(sb, replacement);
        return this;
    }

    @Override
    public String appendTail(final StringBuilder sb) {
        return matcher.appendTail(sb);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public String replaceAll(final String replacement) throws Exception {
        return replaceAll(replacement, Duration.ZERO);
    }

    /**
     * Spends at most {@code duration} time attempting to replace every substring of the input string that matches the
     * regular-expression with the given replacement string. If the specified duration is exceeded this method will throw a
     * {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceAll(String) Matcher.replaceAll(String)}.
     * 
     * @param replacement the replacement string
     * @param duration    the time to wait for the operation to complete before abandoning it, a value of {@code 0}
     *                    indicates to wait forever
     * @return the string constructed by replacing each match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public String replaceAll(final String replacement, final Duration duration) throws Exception {
        checkNotNull(replacement, "replacement == null");
        reset();
        final StringBuilder sb = new StringBuilder();
        while (find(duration))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public String replaceFirst(final String replacement) throws Exception {
        return replaceFirst(replacement, Duration.ZERO);
    }

    /**
     * Spends at most {@code duration} time attempting to replace the first substring of the input string that matches the
     * regular-expression with the given replacement string. If the specified duration is exceeded this method will throw a
     * {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceFirst(String) Matcher.replaceFirst(String)}.
     * 
     * @param replacement the replacement string
     * @param duration    the time to wait for the operation to complete before abandoning it, a value of {@code 0}
     *                    indicates to wait forever
     * @return the string constructed by replacing the first match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws Exception            if any other errors occurs
     */
    public String replaceFirst(final String replacement, final Duration duration) throws Exception {
        checkNotNull(replacement, "replacement == null");
        final StringBuilder sb = new StringBuilder();
        reset();
        if (find(duration))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    private final class MatchOperation implements Runnable {

        private final Supplier<Boolean> supplier;
        private boolean result = false;
        private Throwable t = null;

        public MatchOperation(final Supplier<Boolean> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void run() {
            try {
                result = supplier.get();
            } catch (final Throwable x) {
                t = x;
            }
        }

        public boolean result() throws Exception {
            if (t != null)
                throw t instanceof Exception ? (Exception) t : unchecked(t);
            return result;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean resultOf(final MatchOperation operation, final Duration duration) throws Exception {
        checkNotNull(duration, "duration == null");
        checkArgument(!duration.isNegative(), "duration < 0");

        InterruptedException x = null;

        final Thread t = new Thread(operation);
        t.start();

        try {
            t.join(duration.toMillis());
        } catch (final InterruptedException e) {
            x = e;
        }

        if (t.isAlive()) {
            t.stop();

            throw x != null ? x : new TimeoutException("match operation exceeded " + duration);
        } else
            return operation.result();
    }

}
