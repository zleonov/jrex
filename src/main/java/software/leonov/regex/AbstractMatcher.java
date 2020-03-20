package software.leonov.regex;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
public abstract class AbstractMatcher<T> implements InputMatcher<T> {

    protected AbstractMatcher() {
    };

    protected boolean match = false;
    protected int lastAppendPosition = 0;

    @Override
    public abstract int end();

    @Override
    public abstract int end(final int index);

    @Override
    public abstract String group();

    @Override
    public abstract String group(final int index);

    @Override
    public abstract int groupCount();

    @Override
    public abstract int start();

    @Override
    public abstract int start(final int index);

    protected abstract String getInput();

    /**
     * Attempts to match the entire string against the regular-expression.
     * 
     * @return {@code true} if, and only if, the entire input string matches
     * @throws Exception if any errors occurs
     */
    public abstract boolean find() throws Exception;

    /**
     * Attempts to find the next substring of the input string that matches the regular-expression.
     * 
     * @return {@code true} if, and only if, a substring of the input string is a match
     * @throws Exception if any errors occurs
     */
    public abstract boolean matches() throws Exception;

    /**
     * Returns the regular-expression against which the input string is being matched.
     * 
     * @return the regular-expression against which the input string is being matched
     */
    public abstract RegularExpression pattern();

    /**
     * Resets the current search position to zero.
     */
    public void reset() {
        match = false;
        lastAppendPosition = 0;
    }

    /**
     * Returns the underlying <i>matcher</i> implementation.
     * <p>
     * The intention is to provide the ability to use any functionality of the underlying regular-expression library which
     * is not supported by this interface.
     * 
     * @return the underlying <i>matcher</i> implementation
     */
    public abstract T delegate();

    /**
     * Reads the input string and appends to the specified string builder the substring starting from the last append
     * position to the last character immediately preceding the previous match, followed by the given replacement string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * This method is intended to be used in a loop together with the {@link #appendTail(StringBuilder)} and {@link #find()}
     * methods. For example, the following code writes {@code one dog two dogs in the
     * yard} to the standard out:
     * 
     * <pre>
     * final RegularExpression r = JDKRegularExpression.compile(&quot;cat&quot;);
     * final StringMatcher m = r.matcher(&quot;one cat two cats in the yard&quot;);
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
     * @param sb          the target string builder
     * @param replacement the replacement string
     * @return this {@code StringMatcher} object
     */
    public AbstractMatcher<T> appendReplacement(final StringBuilder sb, final String replacement) {
        checkNotNull(sb, "sb == null");
        checkNotNull(replacement, "replacement == null");
        checkArgument(match, "no match available");

        sb.append(getInput().substring(lastAppendPosition, start()));
        lastAppendPosition = end();

        int index = 0;
        final StringBuilder result = new StringBuilder();

        while (index < replacement.length()) {
            char ch = replacement.charAt(index);
            if (ch == '$') {
                index++;
                int groupId = getIntValue(replacement.charAt(index++));
                checkArgument(groupId >= 0 && groupId <= 9, "illegal group reference");

                while (index < replacement.length()) {
                    int n = getIntValue(replacement.charAt(index));

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
        sb.append(result);
        return this;
    }

    /**
     * Reads the input string and appends to the specified string builder the substring starting from the last append
     * position to the end of the input string.
     * <p>
     * This method is intended to be invoked after one or more {@link #appendReplacement(StringBuilder, String)} invocations
     * to copy the remainder of the input string.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#appendTail(StringBuffer)
     * Matcher.appendTail(StringBuffer)}.
     * 
     * @param sb the target string builder
     * @return the string representation of the target string builder
     */
    public String appendTail(final StringBuilder sb) {
        checkNotNull(sb, "sb == null");
        return sb.append(getInput().substring(lastAppendPosition)).toString();
    }

    /**
     * Replaces every substring of the input string that matches the regular expression with the given replacement string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceAll(String) Matcher.replaceAll(String)}.
     * 
     * @param replacement the replacement string
     * @return the string constructed by replacing each match with the replacement string
     * @throws Exception if any errors occurs
     */
    public String replaceAll(final String replacement) throws Exception {
        checkNotNull(replacement, "replacement == null");
        reset();
        final StringBuilder sb = new StringBuilder();
        while (find())
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Replaces the first substring of the input string that matches the regular expression with the given replacement
     * string.
     * <p>
     * The replacement string may contain references to captured groups. Each occurrence {@code $} followed by an integer
     * will be replaced by the {@link #group(int)} result. Named capturing groups are not supported.
     * <p>
     * This method is modeled after {@link java.util.regex.Matcher#replaceFirst(String) Matcher.replaceFirst(String)}.
     * 
     * @param replacement the replacement string
     * @return the string constructed by replacing the first match with the replacement string
     * @throws Exception if any errors occurs
     */
    public String replaceFirst(final String replacement) throws Exception {
        checkNotNull(replacement, "replacement == null");
        final StringBuilder sb = new StringBuilder();
        reset();
        if (find())
            appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    private static int getIntValue(final int c) {
        return c - '0';
    }

}
