package software.leonov.regex.core.tclre;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.EnumSet;

import com.basistech.tclre.ExecFlags;
import com.basistech.tclre.HsrePattern;
import com.basistech.tclre.PatternFlags;
import com.basistech.tclre.ReMatcher;
import com.basistech.tclre.RePattern;
import com.basistech.tclre.RegexException;

import software.leonov.regex.core.InputMatcher;
import software.leonov.regex.core.RegularExpression;

/**
 * An implementation of the {@code RegularExpression} interface using the
 * <a href="https://github.com/basis-technology-corp/tcl-regex-java" target="_blank">Java port of the TCL Regular
 * Expression (TCLRE) library</a>.
 * <p>
 * See <a href="https://www.rosette.com/blog/a-better-pure-java-regex-engine/" target="_blank">A Better Pure Java RegEx
 * Engine</a> and
 * <a href="http://www.regular-expressions.info/tcl.html" target="_blank">http://www.regular-expressions.info/tcl.html
 * </a> for more information.
 * 
 * @author Zhenya Leonov
 */
public final class TCLRegularExpression implements RegularExpression {

    private final RePattern pattern;
    private static final ExecFlags[] NO_EXEC_FLAGS = {};

    private TCLRegularExpression(final String regex, final PatternFlags... flags) throws RegexException {
        pattern = HsrePattern.compile(regex, flags);
    }

    /**
     * Compiles the given regular-expression.
     * 
     * @param regex the expression to be compiled
     * @throws RegexException if the expression's syntax is invalid
     * @return a new {@code TCLRegularExpression} instance
     */
    public static TCLRegularExpression compile(final String regex) throws RegexException {
        checkNotNull(regex, "regex == null");
        return compile(regex, PatternFlags.ADVANCED);
    }

    /**
     * Compiles the given regular-expression with the specified flags.
     * 
     * @param regex the expression to be compiled
     * @param flags match flags that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link PatternFlags#BASIC}</li>
     *              <li>{@link PatternFlags#EXTENDED}</li>
     *              <li>{@link PatternFlags#ADVANCED}</li>
     *              <li>{@link PatternFlags#QUOTE}</li>
     *              <li>{@link PatternFlags#ICASE}</li>
     *              <li>{@link PatternFlags#NOSUB}</li>
     *              <li>{@link PatternFlags#EXPANDED}</li>
     *              <li>{@link PatternFlags#NLSTOP}</li>
     *              <li>{@link PatternFlags#NLANCH}</li>
     *              </ul>
     * @throws RegexException if the expression's syntax is invalid
     * @return a new {@code TCLRegularExpression} instance
     */
    public static TCLRegularExpression compile(final String regex, final PatternFlags... flags) throws RegexException {
        checkNotNull(regex, "regex == null");
        checkNotNull(flags, "flags == null");
        return new TCLRegularExpression(regex, flags);
    }

    @Override
    public InputMatcher matcher(final CharSequence input) {
        return matcher(input, NO_EXEC_FLAGS);
    }

    /**
     * Returns a {@code InputMatcher} object that will match the given string against this regular-expression.
     * 
     * @param input the string to use as an input
     * @param flags optional flags which control the runtime behavior of the {@code InputMatcher} that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link ExecFlags#LOOKING_AT LOOKING_AT}</li>
     *              <li>{@link ExecFlags#NOTBOL NOTBOL}</li>
     *              <li>{@link ExecFlags#NOTEOL NOTEOL}</li>
     *              </ul>
     * @return a {@code InputMatcher} object that will match the given string against this regular-expression
     */
    public InputMatcher matcher(final CharSequence input, final ExecFlags... flags) {
        checkNotNull(input, "input == null");
        checkNotNull(flags, "flags == null");

        final ReMatcher matcher = pattern.matcher(input, flags);

        return new InputMatcher() {

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
            public TCLRegularExpression pattern() {
                return TCLRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                return matcher.matches();
            }

            @Override
            public int groupCount() {
                return matcher.groupCount();
            }

            @Override
            public String groupImpl(final int index) {
                return matcher.group(index);
            }

            @Override
            public String groupImpl() {
                return matcher.group();
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
                matcher.reset();
            }

            @Override
            public boolean lookingAtImpl() {
                return matcher.lookingAt();
            }
        };
    }

    @Override
    public String pattern() {
        return pattern.pattern();
    }

    /**
     * Returns the match {@link #compile(String, PatternFlags...) flags} specified when this {@code RegularExpression} was
     * compiled.
     *
     * @return the match {@link #compile(String, PatternFlags...) flags} specified when this {@code RegularExpression} was
     *         compiled
     */
    public EnumSet<PatternFlags> flags() {
        return pattern.flags();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "pattern: [" + pattern() + "] flags: [" + flags() + "]";
    }

}
