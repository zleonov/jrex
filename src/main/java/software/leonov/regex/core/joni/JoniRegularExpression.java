package software.leonov.regex.core.joni;

import static com.google.common.base.Preconditions.checkNotNull;

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
import software.leonov.regex.core.InputMatcher;
import software.leonov.regex.core.RegularExpression;

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
        pattern = new Regex(regex.getBytes(StandardCharsets.UTF_8), 0, regex.length(), flags, Config.ENC_CASE_FOLD_DEFAULT, UTF8Encoding.INSTANCE, Syntax.Java, WarnCallback.DEFAULT);
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
    public InputMatcher<Matcher> matcher(final CharSequence input) {
        checkNotNull(input, "input == null");

        final byte[] bytes = input.toString().getBytes(StandardCharsets.UTF_8);
        final int end = bytes.length;

        final Matcher matcher = pattern.matcher(bytes);

        return new InputMatcher<Matcher>() {
            private int start = 0;

            @Override
            protected CharSequence getInput() {
                return input;
            }

            @Override
            public int startImpl(final int index) {
                return matcher.getEagerRegion().beg[index];
            }

            @Override
            public int startImpl() {
                return matcher.getBegin();
            }

            @Override
            public JoniRegularExpression pattern() {
                return JoniRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                return matcher.match(0, end, Option.DEFAULT) != -1;
            }

            @Override
            public int groupCount() {
                final int count = matcher.getEagerRegion().numRegs;
                if (count > 0)
                    return count - 1;
                return count;
            }

            @Override
            public String groupImpl(final int index) {
                try {
                    return input.subSequence(matcher.getEagerRegion().beg[index], matcher.getEagerRegion().end[index]).toString();
                } catch (final StringIndexOutOfBoundsException e) {
                    return null;
                }
            }

            @Override
            public String groupImpl() {
                try {
                    return input.subSequence(matcher.getEagerRegion().beg[0], matcher.getEagerRegion().end[0]).toString();
                } catch (final StringIndexOutOfBoundsException e) {
                    return null;
                }
            }

            @Override
            public boolean findImpl() {
                final int findIndex = matcher.search(start, end, Option.DEFAULT);
                start = matcher.getEnd();
                return findIndex != -1;
            }

            @Override
            public int endImpl(final int index) {
                return matcher.getEagerRegion().end[index];
            }

            @Override
            public int endImpl() {
                return matcher.getEnd();
            }

            @Override
            public void resetImpl() {
                start = 0;
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
        return regex;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("pattern()", Str.truncate(pattern(), 200, "...")).add("flags()", flags()).toString();
    }

}
