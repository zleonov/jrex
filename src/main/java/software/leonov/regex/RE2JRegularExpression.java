package software.leonov.regex;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import com.google.re2j.PatternSyntaxException;

import software.leonov.common.base.Str;

/**
 * An implementation of the {@code RegularExpression} interface using
 * <a href="https://github.com/google/re2j" target="_blank">Google's RE2/J library</a>.
 * 
 * @author Zhenya Leonov
 */
public final class RE2JRegularExpression implements RegularExpression {

    private final Pattern pattern;

    private RE2JRegularExpression(final String regex, final int flags) {
        pattern = Pattern.compile(regex, flags);
    }

    /**
     * Compiles the given regular-expression.
     * 
     * @param regex the expression to be compiled
     * @throws PatternSyntaxException if the expression's syntax is invalid
     * @return a new {@code RE2JRegularExpression} instance
     */
    public static RE2JRegularExpression compile(final String regex) {
        checkNotNull(regex, "regex == null");
        return new RE2JRegularExpression(regex, 0);
    }

    /**
     * Compiles the given regular-expression with the specified flags.
     * 
     * @param regex the expression to be compiled
     * @param flags match flags, a bit mask that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link Pattern#CASE_INSENSITIVE com.google.re2j.Pattern.CASE_INSENSITIVE}</li>
     *              <li>{@link Pattern#DOTALL com.google.re2j.Pattern.DOTALL}</li>
     *              <li>{@link Pattern#MULTILINE com.google.re2j.Pattern.MULTILINE}</li>
     *              <li>{@link Pattern#DISABLE_UNICODE_GROUPS com.google.re2j.Pattern.DISABLE_UNICODE_GROUPS}</li>
     *              </ul>
     * @throws IllegalArgumentException if bit values other than those corresponding to the defined match flags are provided
     * @throws PatternSyntaxException   if the expression's syntax is invalid
     * @return a new {@code RE2JRegularExpression} instance
     */
    public static RE2JRegularExpression compile(final String regex, final int flags) {
        checkNotNull(regex, "regex == null");
        return new RE2JRegularExpression(regex, flags);
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
            public RE2JRegularExpression pattern() {
                return RE2JRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                match = matcher.matches();
                return match;
            }

            @Override
            public int groupCount() {
                return matcher.groupCount();
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
                checkState(match, "no match available");
                return matcher.group();
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
                matcher.reset();
            }

            @Override
            public Matcher delegate() {
                return matcher;
            }
        };
    }

    @Override
    public String pattern() {
        return pattern.pattern();
    }

    /**
     * Returns the match {@link #compile(String, int) flags} specified when this {@code RegularExpression} was compiled.
     *
     * @return the match {@link #compile(String, int) flags} specified when this {@code RegularExpression} was compiled
     */
    public int flags() {
        return pattern.flags();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("pattern()", Str.truncate(pattern(), 200, "...")).add("flags()", flags()).toString();
    }

}
