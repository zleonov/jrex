package software.leonov.regex;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;

import jregex.Matcher;
import jregex.Pattern;
import jregex.PatternSyntaxException;
import jregex.REFlags;
import software.leonov.common.base.Str;

/**
 * An implementation of the {@code RegularExpression} interface using the
 * <a href="http://jregex.sourceforge.net" target="_blank">JRegex library</a>.
 * 
 * @author Zhenya Leonov
 */
public final class JRegexRegularExpression implements RegularExpression {

    private final Pattern pattern;
    private final int flags;

    private JRegexRegularExpression(final String regex, final int flags) {
        this.flags = flags;
        pattern = new Pattern(regex, flags);
    }

    /**
     * Compiles the given regular-expression.
     * 
     * @param regex the expression to be compiled
     * @throws PatternSyntaxException if the expression's syntax is invalid
     * @return a new {@code JRegexRegularExpression} instance
     */
    public static JRegexRegularExpression compile(final String regex) {
        checkNotNull(regex, "regex == null");
        return new JRegexRegularExpression(regex, 0);
    }

    /**
     * Compiles the given regular-expression with the specified flags.
     * 
     * @param regex the expression to be compiled
     * @param flags match flags, a bit mask that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link REFlags#DEFAULT}</li>
     *              <li>{@link REFlags#DOTALL}</li>
     *              <li>{@link REFlags#IGNORE_CASE}</li>
     *              <li>{@link REFlags#IGNORE_SPACES}</li>
     *              <li>{@link REFlags#MULTILINE}</li>
     *              <li>{@link REFlags#UNICODE}</li>
     *              <li>{@link REFlags#XML_SCHEMA}
     *              </ul>
     * @throws PatternSyntaxException if the expression's syntax is invalid
     * @return a new {@code JRegexRegularExpression} instance
     */
    public static JRegexRegularExpression compile(final String regex, final int flags) {
        checkNotNull(regex, "regex == null");
        return new JRegexRegularExpression(regex, flags);
    }

    @Override
    public StringMatcher<Matcher> matcher(final String input) {
        checkNotNull(input, "input == null");

        final Matcher matcher = pattern.matcher(input);

        return new StringMatcher<Matcher>() {

            @Override
            protected String getInput() {
                return input;
            }

            @Override
            public int start(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                return matcher.start(index);
            }

            @Override
            public int start() {
                checkState(match, "no match available");
                return matcher.start();
            }

            @Override
            public JRegexRegularExpression pattern() {
                return JRegexRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                match = matcher.matches();
                return match;
            }

            @Override
            public int groupCount() {
                final int count = matcher.groupCount();
                if (count > 0)
                    return count - 1;
                return count;
            }

            @Override
            public String group(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                return matcher.group(index);
            }

            @Override
            public String group() {
                return matcher.group(0);
            }

            @Override
            public boolean findImpl() {
                match = matcher.find();
                return match;
            }

            @Override
            public int end(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                return matcher.end(index);
            }

            @Override
            public int end() {
                checkState(match, "no match available");
                return matcher.end();
            }

            @Override
            public void reset() {
                super.reset();
                matcher.setPosition(0);
            }

            @Override
            public Matcher delegate() {
                return matcher;
            }
        };
    }

    /**
     * Returns the match {@link #compile(String, int) flags} specified when this {@code RegularExpression} was compiled.
     *
     * @return the match {@link #compile(String, int) flags} specified when this {@code RegularExpression} was compiled
     */
    public int flags() {
        return flags;
    }

    @Override
    public String pattern() {
        return pattern.toString(); // test this
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("pattern()", Str.truncate(pattern(), 200, "...")).add("flags()", flags()).toString();
    }

}
