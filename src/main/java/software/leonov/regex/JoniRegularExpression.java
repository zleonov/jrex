package software.leonov.regex;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.base.Preconditions.checkState;

import java.nio.charset.StandardCharsets;

import org.jcodings.specific.UTF8Encoding;
import org.joni.Config;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.Regex;
import org.joni.Syntax;
import org.joni.WarnCallback;

import com.google.common.base.MoreObjects;

import software.leonov.common.base.Str;

/**
 * An implementation of the {@code RegularExpression} interface using the
 * <a href="https://github.com/jruby/joni" target="_blank">Java port of the Oniguruma regexp library</a>.
 * 
 * @author Zhenya Leonov
 */
public final class JoniRegularExpression implements RegularExpression {

    private final Regex pattern;
    private final String regex;
    private final int flags;

    private JoniRegularExpression(final String regex, final int flags) {
        pattern = new Regex(regex.getBytes(StandardCharsets.UTF_8), 0, regex.length(), flags, Config.ENC_CASE_FOLD_MIN, UTF8Encoding.INSTANCE, Syntax.Java, WarnCallback.DEFAULT);
        this.regex = regex;
        this.flags = flags;
    }

    /**
     * Compiles the given regular-expression.
     * 
     * @param regex the expression to be compiled
     * @return a new {@code JoniRegularExpression} instance
     */
    public static JoniRegularExpression compile(final String regex) {
        checkNotNull(regex, "regex == null");
        return new JoniRegularExpression(regex, Option.DEFAULT);
    }

    /**
     * Compiles the given regular-expression with the specified flags.
     * 
     * @param regex the expression to be compiled
     * @param flags match flags, a bit mask that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link Option#CAPTURE_GROUP}</li>
     *              <li>{@link Option#DONT_CAPTURE_GROUP}</li>
     *              <li>{@link Option#EXTEND}</li>
     *              <li>{@link Option#FIND_LONGEST}</li>
     *              <li>{@link Option#FIND_NOT_EMPTY}</li>
     *              <li>{@link Option#IGNORECASE}</li>
     *              <li>{@link Option#MAXBIT}</li>
     *              <li>{@link Option#MULTILINE}</li>
     *              <li>{@link Option#NEGATE_SINGLELINE}</li>
     *              <li>{@link Option#MAXBIT}</li>
     *              <li>{@link Option#NOTBOL}</li>
     *              <li>{@link Option#NOTEOL}</li>
     *              <li>{@link Option#POSIX_REGION}</li>
     *              <li>{@link Option#SINGLELINE}</li>
     *              </ul>
     * @return a new {@code JoniRegularExpression} instance
     */
    public static JoniRegularExpression compile(final String regex, final int flags) {
        checkNotNull(regex, "regex == null");
        return new JoniRegularExpression(regex, flags);
    }

    @Override
    public StringMatcher<Matcher> matcher(final String input) {
        checkNotNull(input, "input == null");
        
        final byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        final int end = bytes.length;

        final Matcher matcher = pattern.matcher(bytes);

        return new StringMatcher<Matcher>() {
            int start = 0;

            @Override
            protected String getInput() {
                return input;
            }

            @Override
            public int start(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                return matcher.getEagerRegion().beg[index];
            }

            @Override
            public int start() {
                checkState(match, "no match available");
                return matcher.getBegin();
            }

            @Override
            public JoniRegularExpression pattern() {
                return JoniRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                match = matcher.match(0, end, Option.DEFAULT) != -1;
                return match;
            }

            @Override
            public int groupCount() {
                final int count = matcher.getEagerRegion().numRegs;
                if (count > 0)
                    return count - 1;
                return count;
            }

            @Override
            public String group(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                try {
                    return input.substring(matcher.getEagerRegion().beg[index], matcher.getEagerRegion().end[index]);
                } catch (final StringIndexOutOfBoundsException e) {
                    return null;
                }
            }

            @Override
            public String group() {
                try {
                    return input.substring(matcher.getEagerRegion().beg[0], matcher.getEagerRegion().end[0]);
                } catch (final StringIndexOutOfBoundsException e) {
                    return null;
                }
            }

            @Override
            public boolean findImpl() {
                final int findIndex = matcher.search(start, end, Option.DEFAULT);
                start = matcher.getEnd();
                match = findIndex != -1;
                return match;
            }

            @Override
            public int end(final int index) {
                checkState(match, "no match available");
                checkArgument(index >= 0, "index < 0");
                checkPositionIndex(index, groupCount(), "index > groupCount()");
                return matcher.getEagerRegion().end[index];
            }

            @Override
            public int end() {
                checkState(match, "no match available");
                return matcher.getEnd();
            }

            @Override
            public void reset() {
                super.reset();
                start = 0;
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
        return regex;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("pattern()", Str.truncate(pattern(), 200, "...")).add("flags()", flags()).toString();
    }

}
