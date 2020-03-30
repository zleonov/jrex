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

        return new InputMatcher<Matcher>() {

            final Matcher matcher = pattern.matcher(string);
            
            @Override
            protected CharSequence getInput() {
                return input;
            }

            @Override
            public int _start(final int index) {
                return matcher.start(index);
            }

            @Override
            public int _start() {
                return matcher.start();
            }

            @Override
            public JRegexRegularExpression pattern() {
                return JRegexRegularExpression.this;
            }

            @Override
            public boolean _matches() {
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
            public String _group(final int index) {
                return matcher.group(index);
            }

            @Override
            public String _group() {
                return matcher.group(0);
            }

            @Override
            public boolean _find() {
                return matcher.find();
            }

            @Override
            public int _end(final int index) {
                return matcher.end(index);
            }

            @Override
            public int _end() {
                return matcher.end();
            }

            @Override
            public void _reset() {
                matcher.setPosition(0); 
                // Matcher matcher = pattern.matcher(string);
            }

            @Override
            public Matcher delegate() {
                return matcher;
            }

            @Override
            public boolean _lookingAt() {
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
