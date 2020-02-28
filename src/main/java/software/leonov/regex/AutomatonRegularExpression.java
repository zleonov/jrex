package software.leonov.regex;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;

import dk.brics.automaton.AutomatonMatcher;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.RunAutomaton;
import software.leonov.common.base.Str;

/**
 * An implementation of the {@code RegularExpression} interface using the <a href="http://www.brics.dk/automaton/" target="_blank">http://www.brics.dk/automaton</a> package.
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

    public static AutomatonRegularExpression compile(final String regex) {
        checkNotNull(regex, "regex == null");
        return new AutomatonRegularExpression(regex, 0);
    }

    public static AutomatonRegularExpression compile(final String regex, final int flags) {
        checkNotNull(regex, "regex == null");
        return new AutomatonRegularExpression(regex, flags);
    }

    @Override
    public StringMatcher<AutomatonMatcher> matcher(final String input) {

        final RunAutomaton automaton = new RunAutomaton(pattern.toAutomaton());

        return new StringMatcher<AutomatonMatcher>() {

            AutomatonMatcher matcher = automaton.newMatcher(input);

            private boolean find = false;

            @Override
            protected String getInput() {
                return input;
            }

            @Override
            public int start(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int start() {
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
            public boolean matches() {
                match = automaton.run(input);
                return match;
            }

            @Override
            public int groupCount() {
                return matcher.groupCount();

            }

            @Override
            public String group(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String group() {
                return matcher.group();
            }

            @Override
            public boolean find() {
                find = matcher.find();
                return find;
            }

            @Override
            public int end(final int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int end() {
                checkState(match | find, "no match available");
                if (match)
                    return input.length();
                else
                    return matcher.end();
            }

            @Override
            public void reset() {
                super.reset();
                matcher = automaton.newMatcher(input);
            }

            @Override
            public AutomatonMatcher delegate() {
                return matcher;
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
        return MoreObjects.toStringHelper(this).add("pattern()", Str.truncate(pattern(), 200, "...")).add("flags()", flags()).toString();
    }

}
