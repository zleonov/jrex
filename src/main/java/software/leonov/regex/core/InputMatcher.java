package software.leonov.regex.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.MatchResult;

/**
 * Performs match and replace operations by interpreting a regular-expression.
 * <p>
 * <b>Warning: This class uses {@link Thread#stop()} to terminate match operations if they timeout or if this thread is
 * interrupted. If calls {@link #find(long, TimeUnit)}, {@link #lookingAt(long, TimeUnit)} or
 * {@link #matches(long, TimeUnit)} result in a {@link TimeoutException} or an {@link InterruptedException} this matcher
 * will be {@link #reset()} to avoid its private objects being damaged or left in an inconsistent state.</b>
 * <p>
 * Extending classes must implement all abstract methods by forwarding their calls to the underlying regular-expression
 * facility, throwing {@code UnsupportedOperationException}s where appropriate.
 * <p>
 * Instances of this class can be obtained by calling the {@link RegularExpression#matcher(CharSequence)} method.
 * 
 * @author Zhenya Leonov
 */
public abstract class InputMatcher implements MatchResult {

    private boolean match = false;
    private int lastAppendPosition = 0;

    protected InputMatcher() {
    };

    protected abstract int endImpl();

    protected abstract int endImpl(final int index);

    protected abstract int startImpl();

    protected abstract int startImpl(final int index);

    protected abstract boolean findImpl();

    protected abstract boolean lookingAtImpl();

    protected abstract boolean matchesImpl();

    protected abstract String groupImpl();

    protected abstract String groupImpl(final int index);

    protected abstract void resetImpl();

    protected abstract CharSequence getInput();

    @Override
    public abstract int groupCount();

    /**
     * Returns the regular-expression against which the input sequence is being matched.
     * 
     * @return the regular-expression against which the input sequence is being matched
     */
    public abstract RegularExpression pattern();

//    /**
//     * Returns the underlying <i>matcher</i> implementation.
//     * <p>
//     * The intention is to provide the ability to use any functionality of the underlying regular-expression library which
//     * is not supported by this interface.
//     * 
//     * @return the underlying <i>matcher</i> implementation
//     */
//    public abstract T delegate();

    @Override
    public final int end(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return endImpl(index);
    }

    @Override
    public final int end() {
        checkState(match, "no match available");
        return endImpl();
    }

    @Override
    public final String group() {
        checkState(match, "no match available");
        return groupImpl();
    }

    @Override
    public final String group(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return groupImpl(index);
    }

    @Override
    public final int start() {
        checkState(match, "no match available");
        return startImpl();
    }

    @Override
    public final int start(final int index) {
        checkState(match, "no match available");
        if (index < 0 || index > groupCount())
            throw new IndexOutOfBoundsException("no group " + index);
        return startImpl(index);
    }

    /**
     * Attempts to find the next matching subsequence of the input sequence.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#find()} this method will never timeout and cannot be interrupted.
     * Consider {@link #find(long, TimeUnit)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, a subsequence of the input sequence is a match
     */
    public final boolean find() {
        return match = findImpl();
    }

    /**
     * Spends at most the specified amount of time attempting to find the next matching subsequence of the input sequence.
     * If the specified {@code timeout} is exceeded this method will throw a {@code TimeoutException}, and this matcher will
     * be {@link #reset()}.
     * 
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return {@code true} if, and only if, a subsequence of the input sequence is a match
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     */
    public final boolean find(final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        return resultOf(find, timeout, unit);
    }

    /**
     * Attempts to match the beginning of input sequence.
     * <p>
     * Unlike the {@link #matches()} method this method does not require that the entire input sequence be matched.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#lookingAt()} this method will never timeout and cannot be
     * interrupted. Consider {@link #lookingAt(long, TimeUnit)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, the start of input sequence is a match
     */
    public final boolean lookingAt() {
        return match = lookingAtImpl();
    }

    /**
     * Spends at most the specified amount of time attempting to match the beginning of input sequence. If the specified
     * {@code timeout} is exceeded this method will throw a {@code TimeoutException}, and this matcher will be
     * {@link #reset()}.
     * <p>
     * Unlike the {@link #matches(long, TimeUnit)} method this method does not require that the entire input sequence be
     * matched.
     * <p>
     * 
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return {@code true} if, and only if, the start of input sequence is a match
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     */
    public final boolean lookingAt(final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        return resultOf(lookingAt, timeout, unit);
    }

    /**
     * Attempts to match the entire input sequence.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#matches()} this method will never timeout and cannot be interrupted.
     * Consider {@link #matches(long, TimeUnit)} if interruptibility is preferred.
     * 
     * @return {@code true} if, and only if, the entire input sequence matches
     */
    public final boolean matches() {
        return match = matchesImpl();
    }

    /**
     * Spends at most the specified amount of time attempting to match the entire input sequence. If the specified
     * {@code timeout} is exceeded this method will throw a {@code TimeoutException}, and this matcher will be
     * {@link #reset()}.
     * 
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return {@code true} if, and only if, the entire input sequence matches
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     */
    public final boolean matches(final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        return resultOf(matches, timeout, unit);
    }

    /**
     * Resets the current search position to zero.
     */
    public final void reset() {
        match = false;
        lastAppendPosition = 0;
        resetImpl();
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
    public final InputMatcher appendReplacement(final StringBuilder buff, final String replacement) {
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
     * Replaces every matching subsequence of the input sequence with a replacement string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#replaceAll(String)} this method will never timeout and cannot be
     * interrupted. Consider {@link #replaceAll(String, long, TimeUnit)} if interruptibility is preferred.
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
     * Spends at most the specified amount of time replacing every matching subsequence of the input sequence a replacement
     * string. If the {@code timeout} is exceeded this method will throw a {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceAll(String) Matcher.replaceAll(String)}.
     * 
     * @param replacement the replacement string
     * @param timeout     the maximum time to wait
     * @param unit        the time unit of the timeout argument
     * @return the string constructed by replacing each match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     */
    public final String replaceAll(final String replacement, final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        checkNotNull(replacement, "replacement == null");
        reset();
        final StringBuilder sb = new StringBuilder();
        while (find(timeout, unit))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Replaces the first matching subsequence of the input sequence with a replacement string.
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
     * Spends at most the specified amount of time replacing the first matching subsequence of the input sequence with a
     * replacement string. If the specified {@code timeout} is exceeded this method will throw a {@code TimeoutException}.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence of {@code $} followed by an integer
     * will be replaced by the result of calling {@link #group(int)}. Named capturing groups are not supported.
     * <p>
     * <b>Note:</b> Like {@link java.util.regex.Matcher#replaceFirst(String)} this method will never timeout and cannot be
     * interrupted. Consider {@link #replaceFirst(String, long, TimeUnit)} if interruptibility is preferred.
     * 
     * @param replacement the replacement string
     * @param timeout     the maximum time to wait
     * @param unit        the time unit of the timeout argument
     * @return the string constructed by replacing the first match with the replacement string
     * @throws TimeoutException     if the time out has been exceeded while attempting to find the next match
     * @throws InterruptedException if current thread is interrupted (the interrupted status of the current thread is
     *                              cleared when this exception is thrown)
     */
    public final String replaceFirst(final String replacement, final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        checkNotNull(replacement, "replacement == null");
        final StringBuilder sb = new StringBuilder();
        reset();
        if (find(timeout, unit))
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    final private FutureTask<Boolean> matches = new FutureTask<>(() -> matches());
    final private FutureTask<Boolean> find = new FutureTask<>(() -> find());
    final private FutureTask<Boolean> lookingAt = new FutureTask<>(() -> lookingAt());

    @SuppressWarnings("deprecation")
    private boolean resultOf(final FutureTask<Boolean> task, final long timeout, final TimeUnit unit) throws TimeoutException, InterruptedException {
        checkArgument(timeout >= 0, "timeout < 0");
        checkNotNull(unit, "unit == null");

        final Thread t = new Thread(task);
        t.start();

        try {
            return task.get(timeout, unit);
        } catch (final InterruptedException | TimeoutException e) {
            reset();
            throw e;
        } catch (final ExecutionException e) {
            final Throwable cause = e.getCause();
            throwIfUnchecked(cause);
            throw new RuntimeException(cause);
        } finally {
            if (t.isAlive())
                t.stop();
        }
    }

    private static void throwIfUnchecked(final Throwable t) {
        if (t instanceof RuntimeException)
            throw (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
    }

}
