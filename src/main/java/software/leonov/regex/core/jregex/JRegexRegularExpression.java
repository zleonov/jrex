package software.leonov.regex.core.jregex;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;

import jregex.Matcher;
import jregex.Pattern;
import jregex.PatternSyntaxException;
import jregex.REFlags;
import software.leonov.common.base.Str;
import software.leonov.regex.core.InputMatcher;
import software.leonov.regex.core.RegularExpression;

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
    public InputMatcher<Matcher> matcher(final CharSequence input) {
        checkNotNull(input, "input == null");

        final String string = input.toString();
        final Matcher matcher = pattern.matcher(string);

        return new InputMatcher<Matcher>() {

            @Override
            protected CharSequence getInput() {
                return input;
            }

            @Override
            public int startImpl(final int index) {
                return matcher.start(index);
            }

            @Override
            public int startImpl() {
                return matcher.start();
            }

            @Override
            public JRegexRegularExpression pattern() {
                return JRegexRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                return matcher.matches();
            }

            @Override
            public int groupCount() {
                final int count = matcher.groupCount();
                if (count > 0)
                    return count - 1;
                return count;
            }

            @Override
            public String groupImpl(final int index) {
                return matcher.group(index);
            }

            @Override
            public String groupImpl() {
                return matcher.group(0);
            }

            @Override
            public boolean findImpl() {
                return matcher.find();
            }

            @Override
            public int endImpl(final int index) {
                return matcher.end(index);
            }

            @Override
            public int endImpl() {
                return matcher.end();
            }

            @Override
            public void resetImpl() {
                matcher.setPosition(0);
            }

            @Override
            public Matcher delegate() {
                return matcher;
            }

            @Override
            public boolean lookingAtImpl() {
                throw new UnsupportedOperationException();
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
