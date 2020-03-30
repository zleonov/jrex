package software.leonov.regex.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.regex.MatchResult;

/**
 * Performs match and replace operations by interpreting a regular-expression.
 * <p>
 * Extending classes must implement all abstract methods by forwarding their calls to the underlying regular-expression
 * facility, throwing {@code UnsupportedOperationException}s where appropriate.
 * <p>
 * Instances of this class can be obtained by calling the {@link RegularExpression#matcher(String)} method.
 * 
 * @author Zhenya Leonov
 */
public abstract class InputMatcher<T> implements MatchResult {

    private boolean match = false;
    private int lastAppendPosition = 0;

    protected InputMatcher() {
    };

    // @formatter:off
    protected abstract int _end();
    protected abstract int _end(final int index);
    protected abstract int _start();
    protected abstract int _start(final int index);
    
    protected abstract boolean _find();
    protected abstract boolean _lookingAt();
    protected abstract boolean _matches();
    
    protected abstract String _group();
    protected abstract String _group(final int index);
    
    protected abstract void _reset();
    
    protected abstract CharSequence getInput();
    // @formatter:on

    @Override
    public abstract int groupCount();

    /**
     * Returns the regular-expression against which the input sequence is being matched.
     * 
     * @return the regular-expression against which the input sequence is being matched
     */
    public abstract RegularExpression pattern();

    /**
     * Returns the underlying <i>matcher</i> implementation.
     * <p>
     * The intention is to provide the ability to use any functionality of the underlying regular-expression library which
     * is not supported by this interface.
     * 
     * @return the underlying <i>matcher</i> implementation
     */
    public abstract T delegate();

    @Override
    public final int end(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return _end(index);
    }

    @Override
    public final int end() {
        checkState(match, "no match available");
        return _end();
    }

    @Override
    public final String group() {
        checkState(match, "no match available");
        return _group();
    }

    @Override
    public final String group(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return _group(index);
    }

    @Override
    public final int start() {
        checkState(match, "no match available");
        return _start();
    }

    @Override
    public final int start(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return _start(index);
    }

    /**
     * Attempts to find the next subsequence of the input sequence that matches the regular-expression.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#find()} this method will never timeout and cannot be interrupted.
     * Consider {@link #find(Duration)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, a subsequence of the input sequence is a match
     */
    public final boolean find() {
        return match = _find();
    }

    /**
     * Spends at most {@code duration} time attempting to find the next subsequence of the input sequence that matches the
     * regular-expression. If the specified duration is exceeded this method will throw a {@code TimeoutException}, and this
     * matcher will be {@link #reset()}.
     * 
     * @param duration the time to wait for the operation to complete before abandoning it, a value of {@link Duration#ZERO}
     *                 indicates to wait forever
     * @return {@code true} if, and only if, a subsequence of the input sequence is a match
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws ExecutionException   if any other errors occurs
     */
    public final boolean find(final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        return resultOf(find, duration);
    }

    /**
     * Attempts to match the beginning of input sequence against the regular-expression.
     * <p>
     * Unlike the {@link #matches()} method this method does not require that the entire input sequence be matched.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#lookingAt()} this method will never timeout and cannot be
     * interrupted. Consider {@link #lookingAt(Duration)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, the start of input sequence is a match
     */
    public final boolean lookingAt() {
        return match = _lookingAt();
    }

    /**
     * Spends at most {@code duration} time attempting to match the beginning of input sequence against the
     * regular-expression. If the specified duration is exceeded this method will throw a {@code TimeoutException}, and this
     * matcher will be {@link #reset()}.
     * <p>
     * Unlike the {@link #matches(Duration)} method this method does not require that the entire input sequence be matched.
     * <p>
     * 
     * @param duration the time to wait for the operation to complete before abandoning it, a value of {@link Duration#ZERO}
     *                 indicates to wait forever
     * @return {@code true} if, and only if, the start of input sequence is a match
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws ExecutionException   if any other errors occurs
     */
    public final boolean lookingAt(final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        return resultOf(lookingAt, duration);
    }

    /**
     * Attempts to match the entire input sequence against the regular-expression.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#matches()} this method will never timeout and cannot be interrupted.
     * Consider {@link #matches(Duration)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, the entire input sequence matches
     */
    public final boolean matches() {
        return match = _matches();
    }

    /**
     * Spends at most {@code duration} time attempting to match the entire input sequence against the regular-expression. If
     * the specified duration is exceeded this method will throw a {@code TimeoutException}, and this matcher will be
     * {@link #reset()}.
     * 
     * @param duration the time to wait for the operation to complete before abandoning it, a value of {@link Duration#ZERO}
     *                 indicates to wait forever
     * @return {@code true} if, and only if, the entire input sequence matches
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws ExecutionException   if any other errors occurs
     */
    public final boolean matches(final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        return resultOf(matches, duration);
    }

    /**
     * Resets the current search position to zero.
     */
    public final void reset() {
        match = false;
        lastAppendPosition = 0;
        _reset();
    }

    /**
     * Reads the input sequence and appends to the target {@code StringBuilder} the subsequence starting from the last
     * append position to the last character immediately preceding the previous match, followed by the given replacement
     * string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. <b>Named capturing groups are not supported.</b>
     * <p>
     * This method is intended to be used in a loop together with the {@link #appendTail(StringBuilder)} and {@link #find()}
     * methods. For example, the following code writes {@code one dog two dogs in the
     * yard} to the standard out:
     * 
     * <pre>
     * final RegularExpression r = JDKRegularExpression.compile(&quot;cat&quot;);
     * final InputMatcher m = r.matcher(&quot;one cat two cats in the yard&quot;);
     * final StringBuilder sb = new StringBuilder();
     * while (m.find()) {
     *     m.appendReplacement(sb, &quot;dog&quot;);
     * }
     * m.appendTail(sb);
     * System.out.println(sb);
     * </pre>
     * 
     * This method is modeled after {@link java.util.regex.Matcher#appendReplacement(StringBuffer, String)
     * Matcher.appendReplacement(StringBuffer, String)}.
     * 
     * @param buff        the target {@code StringBuilder}
     * @param replacement the replacement string
     * @return this {@code InputMatcher} object
     */
    public final InputMatcher<T> appendReplacement(final StringBuilder buff, final String replacement) {
        checkNotNull(buff, "buff == null");
        checkNotNull(replacement, "replacement == null");
        checkArgument(match, "no match available");

        buff.append(getInput().subSequence(lastAppendPosition, start()));
        lastAppendPosition = end();

        int index = 0;
        final StringBuilder result = new StringBuilder();

        while (index < replacement.length()) {
            char ch = replacement.charAt(index);
            if (ch == '$') {
                checkArgument(++index < replacement.length(), "group index missing");
                int groupId = replacement.charAt(index++) - '0';

                checkArgument(groupId >= 0 && groupId <= 9, "illegal group reference");

                while (index < replacement.length()) {
                    int n = replacement.charAt(index) - '0';

                    if (n < 0 || n > 9)
                        break;

                    int count = groupId * 10 + n;

                    if (count < groupCount()) {
                        groupId = count;
                        index++;
                    } else
                        break;
                }

                if (group(groupId) != null)
                    result.append(group(groupId));

            } else if (ch == '\\') {
                index++;
                result.append(replacement.charAt(index++));
            } else {
                result.append(ch);
                index++;
            }
        }

        buff.append(result);

        return this;
    }

    /**
     * Reads the input sequence and appends to the target {@code StringBuilder} the subsequence starting from the last
     * append position to the end of the input sequence.
     * <p>
     * This method is intended to be invoked after one or more {@link #appendReplacement(StringBuilder, String)} invocations
     * to copy the remainder of the input sequence.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#appendTail(StringBuffer)
     * Matcher.appendTail(StringBuffer)}.
     * 
     * @param buff the target {@code StringBuilder}
     * @return the string representation of the target {@code StringBuilder}
     */
    public final String appendTail(final StringBuilder buff) {
        checkNotNull(buff, "buff == null");
        return buff.append(getInput().subSequence(lastAppendPosition, getInput().length())).toString();
    }

    /**
     * Replaces every subsequence of the input sequence that matches the regular expression with the given replacement
     * string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#replaceAll(String)} this method will never timeout and cannot be
     * interrupted. Consider {@link #replaceAll(String, Duration)} if interruptibility is preferred.
     * 
     * @param replacement the replacement string
     * @return the string constructed by replacing each match with the replacement string
     */
    public final String replaceAll(final String replacement) {
        checkNotNull(replacement, "replacement == null");
        reset();
        final StringBuilder sb = new StringBuilder();
        while (find())
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Spends at most {@code duration} time attempting to replace every subsequence of the input sequence that matches the
     * regular-expression with the given replacement string. If the specified duration is exceeded this method will throw a
     * {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceAll(String) Matcher.replaceAll(String)}.
     * 
     * @param replacement the replacement string
     * @param duration    the time to wait for the operation to complete before abandoning it, a value of
     *                    {@link Duration#ZERO} indicates to wait forever
     * @return the string constructed by replacing each match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws ExecutionException   if any other errors occurs
     */
    public final String replaceAll(final String replacement, final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        checkNotNull(replacement, "replacement == null");
        reset();
        final StringBuilder sb = new StringBuilder();
        while (find(duration))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Replaces the first subsequence of the input sequence that matches the regular expression with the given replacement
     * string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceFirst(String) Matcher.replaceFirst(String)}.
     * 
     * @param replacement the replacement string
     * @return the string constructed by replacing the first match with the replacement string
     */
    public final String replaceFirst(final String replacement) {
        checkNotNull(replacement, "replacement == null");
        final StringBuilder sb = new StringBuilder();
        reset();
        if (find())
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Spends at most {@code duration} time attempting to replace the first subsequence of the input sequence that matches
     * the regular-expression with the given replacement string. If the specified duration is exceeded this method will
     * throw a {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#replaceFirst(String)} this method will never timeout and cannot be
     * interrupted. Consider {@link #replaceFirst(String, Duration)} if interruptibility is preferred.
     * 
     * @param replacement the replacement string
     * @param duration    the time to wait for the operation to complete before abandoning it, a value of
     *                    {@link Duration#ZERO} indicates to wait forever
     * @return the string constructed by replacing the first match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws ExecutionException   if any other errors occurs
     */
    public final String replaceFirst(final String replacement, final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        checkNotNull(replacement, "replacement == null");
        final StringBuilder sb = new StringBuilder();
        reset();
        if (find(duration))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    private final MatchOperation matches = new MatchOperation(() -> matches());
    private final MatchOperation find = new MatchOperation(() -> find());
    private final MatchOperation lookingAt = new MatchOperation(() -> lookingAt());

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

        public boolean result() throws ExecutionException {
            if (t != null)
                throw new ExecutionException(t);
            return result;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean resultOf(final MatchOperation operation, final Duration duration) throws TimeoutException, InterruptedException, ExecutionException {
        checkNotNull(duration, "duration == null");
        checkArgument(!duration.isNegative(), "duration < 0");

        final Thread t = new Thread(operation);
        t.start();

        InterruptedException x = null;

        try {
            t.join(duration.toMillis());
        } catch (final InterruptedException e) {
            x = e;
        }

        if (x != null || t.isAlive()) {

            t.stop();

            reset();

            if (x != null)
                throw x;
            else
                throw new TimeoutException("match operation exceeded " + duration);
        }

        return operation.result();
    }

}
