package software.leonov.regex.core.brics.dk.automaton;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import dk.brics.automaton.AutomatonMatcher;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.RunAutomaton;
import software.leonov.regex.core.InputMatcher;
import software.leonov.regex.core.RegularExpression;

/**
 * An implementation of the {@code RegularExpression} interface using the
 * <a href="http://www.brics.dk/automaton/" target="_blank">http://www.brics.dk/automaton</a> package.
 * 
 * @author Zhenya Leonov
 */
public final class AutomatonRegularExpression implements RegularExpression {

    private final RegExp pattern;
    private final String regex;
    private final int flags;

    private AutomatonRegularExpression(final String regex, final int flags) {
        this.flags = flags;
        this.regex = regex;
        pattern = new RegExp(regex, flags);
    }

    /**
     * Compiles the given regular-expression.
     * <p>
     * Shorthand for {@link #compile(String, int) compile(regex, RegExp.ALL)}.
     * 
     * @param regex the expression to be compiled
     * @throws IllegalArgumentException if the expression's syntax is invalid
     * @return a new {@code AutomatonRegularExpression} instance
     */
    public static AutomatonRegularExpression compile(final String regex) {
        checkNotNull(regex, "regex == null");
        return new AutomatonRegularExpression(regex, 0);
    }

    /**
     * Compiles the given regular-expression with the specified flags.
     * 
     * @param regex the expression to be compiled
     * @param flags match flags, a bit mask that may include:
     *              <ul style="list-style-type:none">
     *              <li>{@link RegExp#ALL}</li>
     *              <li>{@link RegExp#ANYSTRING}</li>
     *              <li>{@link RegExp#AUTOMATON}</li>
     *              <li>{@link RegExp#COMPLEMENT}</li>
     *              <li>{@link RegExp#EMPTY}</li>
     *              <li>{@link RegExp#INTERSECTION}</li>
     *              <li>{@link RegExp#INTERVAL}</li>
     *              <li>{@link RegExp#NONE}</li>
     *              </ul>
     * @throws IllegalArgumentException if the expression's syntax is invalid
     * @return a new {@code AutomatonRegularExpression} instance
     */
    public static AutomatonRegularExpression compile(final String regex, final int flags) {
        checkNotNull(regex, "regex == null");
        return new AutomatonRegularExpression(regex, flags);
    }

    @Override
    public InputMatcher matcher(final CharSequence input) {
        checkNotNull(input, "input == null");

        final RunAutomaton automaton = new RunAutomaton(pattern.toAutomaton());

        return new InputMatcher() {

            private AutomatonMatcher matcher = automaton.newMatcher(input);

            private boolean match = false;
            private boolean find = false;

            @Override
            protected CharSequence getInput() {
                return input;
            }

            @Override
            public int startImpl(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int startImpl() {
                checkState(match | find, "no match available");
                if (match)
                    return 0;
                else
                    return matcher.start();
            }

            @Override
            public AutomatonRegularExpression pattern() {
                return AutomatonRegularExpression.this;
            }

            @Override
            public boolean matchesImpl() {
                match = automaton.run(input.toString());
                return match;
            }

            @Override
            public int groupCount() {
                return matcher.groupCount();

            }

            @Override
            public String groupImpl(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String groupImpl() {
                return matcher.group();
            }

            @Override
            public boolean findImpl() {
                find = matcher.find();
                return find;
            }

            @Override
            public int endImpl(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int endImpl() {
                checkState(match | find, "no match available");
                if (match)
                    return input.length();
                else
                    return matcher.end();
            }

            @Override
            public void resetImpl() {
                match = false;
                find = false;
                matcher = automaton.newMatcher(input);
            }

            @Override
            public boolean lookingAtImpl() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String pattern() {
        return regex;
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
    public String toString() {
        return this.getClass().getSimpleName() + "pattern: [" + pattern() + "] flags: [" + flags() + "]";
    }

}
