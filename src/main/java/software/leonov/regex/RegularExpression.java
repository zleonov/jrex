package software.leonov.regex;

import java.util.regex.MatchResult;

/**
 * A representation of a compiled a regular-expression.
 * <p>
 * The Java API does not provide robust support for alternative regular-expression implementations. Except for the
 * {@link MatchResult} interface the JDK regular-expression facility consists exclusively of concrete classes.
 * <p>
 * This interface along with the abstract {@link StringMatcher} class is an attempt to provide a unified API friendly
 * towards alternative implementations while following the same general design principles as the {@code java.util.regex}
 * package.
 * 
 * @author Zhenya Leonov
 */
public interface RegularExpression {

    /**
     * Returns a {@code StringMatcher} object that will match the given string against this regular-expression.
     * 
     * @param input the string to use as an input
     * @return a {@code StringMatcher} object that will match the given string against this regular-expression
     */
    public InputMatcher<?> matcher(final String input);

    /**
     * Returns the expression from which this {@code RegularExpression} was compiled.
     * 
     * @return the expression from which this {@code RegularExpression} was compiled
     */
    public String pattern();

}
