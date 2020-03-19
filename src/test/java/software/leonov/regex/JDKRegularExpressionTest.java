package software.leonov.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static software.leonov.regex.Utilities.doSpecialChars;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDKRegularExpressionTest {

    @Test
    public void test___a__a() throws Throwable {
        final String pattern = "^(a)?a";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test___aa_bb_____() throws Throwable {
        final String pattern = "^(aa(bb)?)+$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aabbaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aabbaa", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("aa", matcher.group(1));
        assertEquals("bb", matcher.group(2));
    }

    @Test
    public void test___a_b__b__() throws Throwable {
        final String pattern = "((a|b)?b)+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("b", matcher.group(1));
    }

    @Test
    public void test__aaa__aaa() throws Throwable {
        final String pattern = "(aaa)?aaa";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test___a_b_____() throws Throwable {
        final String pattern = "^(a(b)?)+$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aba";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aba", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("a", matcher.group(1));
        assertEquals("b", matcher.group(2));
    }

    @Test
    public void test___a_b_c______abc() throws Throwable {
        final String pattern = "^(a(b(c)?)?)?abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test___a_b_c_____() throws Throwable {
        final String pattern = "^(a(b(c))).*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(3, matcher.groupCount());
        assertEquals("abc", matcher.group(1));
        assertEquals("bc", matcher.group(2));
        assertEquals("c", matcher.group(3));
    }

    @Test
    public void test_abc__x_blah() throws Throwable {
        final String pattern = "abc(?x)blah";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc__x___blah() throws Throwable {
        final String pattern = "abc(?x)  blah";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc__x___blah__blech() throws Throwable {
        final String pattern = "abc(?x)  blah  blech";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcblahblech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblahblech", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc__x___blah___ignore_comment() throws Throwable {
        final String pattern = "abc(?x)  blah # ignore comment";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b() throws Throwable {
        final String pattern = "a|b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b2() throws Throwable {
        final String pattern = "a|b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b3() throws Throwable {
        final String pattern = "a|b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b_cd() throws Throwable {
        final String pattern = "a|b|cd";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "cd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("cd", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_ad() throws Throwable {
        final String pattern = "a|ad";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ad";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_z_a_ac_b() throws Throwable {
        final String pattern = "z(a|ac)b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zacb";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("zacb", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ac", matcher.group(1));
    }

    @Test
    public void test__abc__() throws Throwable {
        final String pattern = "[abc]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc__2() throws Throwable {
        final String pattern = "[abc]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "defg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc___def___ghi__() throws Throwable {
        final String pattern = "[abc]+[def]+[ghi]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzaaddggzzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaddgg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_g__() throws Throwable {
        final String pattern = "[a-g]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzggg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ggg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_g__2() throws Throwable {
        final String pattern = "[a-g]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a___() throws Throwable {
        final String pattern = "[a-]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "za-9z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a-", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_____u4444__() throws Throwable {
        final String pattern = doSpecialChars("[a-\\\\u4444]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "za-9z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("za", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___abc__() throws Throwable {
        final String pattern = "[^abc]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___abc__2() throws Throwable {
        final String pattern = "[^abc]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaabbbcccdefg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("defg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_b_() throws Throwable {
        final String pattern = "[abc^b]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_b_2() throws Throwable {
        final String pattern = "[abc^b]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def__() throws Throwable {
        final String pattern = "[abc[def]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def__2() throws Throwable {
        final String pattern = "[abc[def]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_d_0_9__m_p__() throws Throwable {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_d_0_9__m_p__2() throws Throwable {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "o";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("o", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_d_0_9__m_p__3() throws Throwable {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_d_0_9__m_p__4() throws Throwable {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_d_0_9__m_p__5() throws Throwable {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "u";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_d__0_9__m_p__() throws Throwable {
        final String pattern = "[[a-d][0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_d__0_9__m_p__2() throws Throwable {
        final String pattern = "[[a-d][0-9][m-p]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c_d_f_g_i___() throws Throwable {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c_d_f_g_i___2() throws Throwable {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c_d_f_g_i___3() throws Throwable {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "h";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("h", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c_d_f_g_i___4() throws Throwable {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c_d_f_g_i__m_() throws Throwable {
        final String pattern = "[a-c[d-f[g-i]]m]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def_ghi_() throws Throwable {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def_ghi_2() throws Throwable {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def_ghi_3() throws Throwable {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "h";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("h", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc_def_ghi_4() throws Throwable {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "w";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c___d_f__() throws Throwable {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c___d_f__2() throws Throwable {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c___d_f__3() throws Throwable {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____d_f__() throws Throwable {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____d_f__2() throws Throwable {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____d_f__3() throws Throwable {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c__d_f_() throws Throwable {
        final String pattern = "[a-c&&d-f]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_m__m_z_() throws Throwable {
        final String pattern = "[a-m&&m-z]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_m__m_z__a_c_() throws Throwable {
        final String pattern = "[a-m&&m-z&&a-c]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_m__m_z__a_z_() throws Throwable {
        final String pattern = "[a-m&&m-z&&a-z]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_m____m_z__() throws Throwable {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_m____m_z__2() throws Throwable {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_m____m_z__3() throws Throwable {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_m_____a_c__() throws Throwable {
        final String pattern = "[[a-m]&&[^a-c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_m_____a_c__2() throws Throwable {
        final String pattern = "[[a-m]&&[^a-c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_m____a_c__() throws Throwable {
        final String pattern = "[a-m&&[^a-c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_m____a_c__2() throws Throwable {
        final String pattern = "[a-m&&[^a-c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_cd_f___d_f__() throws Throwable {
        final String pattern = "[a-cd-f&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_cd_f___d_f__2() throws Throwable {
        final String pattern = "[a-cd-f&&[d-f]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c___d_fa_c_() throws Throwable {
        final String pattern = "[[a-c]&&d-fa-c]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____d_f__a_c__() throws Throwable {
        final String pattern = "[[a-c]&&[d-f][a-c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c__d_f___abc_() throws Throwable {
        final String pattern = "[[a-c][d-f]&&abc]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c__d_f___abc_def__() throws Throwable {
        final String pattern = "[[a-c][d-f]&&abc[def]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____b_d____c_e__() throws Throwable {
        final String pattern = "[[a-c]&&[b-d]&&[c-e]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____b_d____c_e__2() throws Throwable {
        final String pattern = "[[a-c]&&[b-d]&&[c-e]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_c____b_d__c_e____u_z__() throws Throwable {
        final String pattern = "[[a-c]&&[b-d][c-e]&&[u-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc__bcd__() throws Throwable {
        final String pattern = "[abc[^bcd]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc__bcd__2() throws Throwable {
        final String pattern = "[abc[^bcd]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c__a_d__a_eghi_() throws Throwable {
        final String pattern = "[a-c&&a-d&&a-eghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_c__a_d__a_eghi_2() throws Throwable {
        final String pattern = "[a-c&&a-d&&a-eghi]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "g";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a_b_____b_a___() throws Throwable {
        final String pattern = "[[a[b]]&&[b[a]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a____b__c__a_____d__() throws Throwable {
        final String pattern = "[[a]&&[b][c][a]&&[^d]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___a____b__c__a_____d__2() throws Throwable {
        final String pattern = "[[a]&&[b][c][a]&&[^d]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f___() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f___2() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f_____c__() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f_____c___c_() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f_____c___c__c_() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c&&c]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____a_d____c_f_____c___c___cde__() throws Throwable {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c&&[cde]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__z_abc__bcd__() throws Throwable {
        final String pattern = "[z[abc&&bcd]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__z_abc__bcd____u_z__() throws Throwable {
        final String pattern = "[z[abc&&bcd]&&[u-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__x_abc__bcd_z_____u_z__() throws Throwable {
        final String pattern = "[x[abc&&bcd[z]]&&[u-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__x__wz_abc__bcd_z_____u_z__() throws Throwable {
        final String pattern = "[x[[wz]abc&&bcd[z]]&&[u-z]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___abc____def_abc_() throws Throwable {
        final String pattern = "[[abc]&&[def]abc]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___abc____def_xyz_abc__() throws Throwable {
        final String pattern = "[[abc]&&[def]xyz[abc]]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL() throws Throwable {
        final String pattern = "\\pL";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL2() throws Throwable {
        final String pattern = "\\pL";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "7";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L_() throws Throwable {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_LC_() throws Throwable {
        final String pattern = "\\p{LC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_LC_2() throws Throwable {
        final String pattern = "\\p{LC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "A";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("A", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsL_() throws Throwable {
        final String pattern = "\\p{IsL}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsLC_() throws Throwable {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsLC_2() throws Throwable {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "A";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("A", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsLC_3() throws Throwable {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "9";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___P_IsLC_() throws Throwable {
        final String pattern = "\\P{IsLC}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "9";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("9", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_Pi_() throws Throwable {
        final String pattern = "\\p{Pi}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00ab");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00ab"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___P_Pi_() throws Throwable {
        final String pattern = "\\P{Pi}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00ac");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00ac"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsPf_() throws Throwable {
        final String pattern = "\\p{IsPf}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_P_() throws Throwable {
        final String pattern = "\\p{P}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_P__() throws Throwable {
        final String pattern = "\\p{P}+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___P_IsPf_() throws Throwable {
        final String pattern = "\\P{IsPf}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___P_IsP_() throws Throwable {
        final String pattern = "\\P{IsP}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L1_() throws Throwable {
        final String pattern = "\\p{L1}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L1__() throws Throwable {
        final String pattern = "\\p{L1}+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L1_2() throws Throwable {
        final String pattern = "\\p{L1}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u02bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_ASCII_() throws Throwable {
        final String pattern = "\\p{ASCII}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsASCII_() throws Throwable {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsASCII_2() throws Throwable {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pLbc() throws Throwable {
        final String pattern = "\\pLbc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_r__p_InGreek__c() throws Throwable {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__p_InGreek_() throws Throwable {
        final String pattern = "a\\p{InGreek}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__P_InGreek_() throws Throwable {
        final String pattern = "a\\P{InGreek}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__P_InGreek_2() throws Throwable {
        final String pattern = "a\\P{InGreek}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__InGreek_() throws Throwable {
        try {
            final String pattern = "a{^InGreek}";
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_a__p__InGreek_() throws Throwable {
        try {
            final String pattern = "a\\p{^InGreek}";
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_a__P__InGreek_() throws Throwable {
        try {
            final String pattern = "a\\P{^InGreek}";
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_a__p_InGreek_2() throws Throwable {
        final String pattern = "a\\p{InGreek}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_InGreek__c() throws Throwable {
        final String pattern = "a[\\p{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___P_InGreek__c() throws Throwable {
        final String pattern = "a[\\P{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___P_InGreek__c2() throws Throwable {
        final String pattern = "a[\\P{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___InGreek__c() throws Throwable {
        final String pattern = "a[{^InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "anc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("anc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___InGreek__c2() throws Throwable {
        final String pattern = "a[{^InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "azc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p__InGreek__c() throws Throwable {
        try {
            final String pattern = "a[\\p{^InGreek}]c";
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_a___P__InGreek__c() throws Throwable {
        try {
            final String pattern = "a[\\P{^InGreek}]c";
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_a___p_InGreek__() throws Throwable {
        final String pattern = "a[\\p{InGreek}]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_r__p_InGreek__c2() throws Throwable {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_InGreek_r_c() throws Throwable {
        final String pattern = "a[\\p{InGreek}r]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_r__p_InGreek__c3() throws Throwable {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a____p_InGreek__c() throws Throwable {
        final String pattern = "a[^\\p{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a____P_InGreek__c() throws Throwable {
        final String pattern = "a[^\\P{InGreek}]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_InGreek_______u0370__c() throws Throwable {
        final String pattern = doSpecialChars("a[\\p{InGreek}&&[^\\u0370]]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_c__() throws Throwable {
        final String pattern = "a.c.+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a#c%&";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a#c%&", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_ab_() throws Throwable {
        final String pattern = "ab.";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("ab\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s_ab_() throws Throwable {
        final String pattern = "(?s)ab.";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("ab\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("ab\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_L______P_InGreek___c() throws Throwable {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u6000c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u6000c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_L______P_InGreek___c2() throws Throwable {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a___p_L______P_InGreek___c3() throws Throwable {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__p_InGreek_c() throws Throwable {
        final String pattern = "a\\p{InGreek}c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__p_Sc_() throws Throwable {
        final String pattern = "a\\p{Sc}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a$";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a$", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_ab__wc() throws Throwable {
        final String pattern = "ab\\wc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___W__w__W() throws Throwable {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "#r#";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#r#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___W__w__W2() throws Throwable {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "rrrr#ggg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___w_() throws Throwable {
        final String pattern = "abc[\\w]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcd", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___sdef__() throws Throwable {
        final String pattern = "abc[\\sdef]*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc  def";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc  def", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___sy_z__() throws Throwable {
        final String pattern = "abc[\\sy-z]*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abc y z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc y z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc_a_d__sm_p__() throws Throwable {
        final String pattern = "abc[a-d\\sm-p]*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcaa mn  p";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcaa mn  p", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_ab__sc() throws Throwable {
        final String pattern = "ab\\sc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ab c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s__s__s() throws Throwable {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blah  err";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___S__S__s() throws Throwable {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blah  err";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ah ", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_ab__dc() throws Throwable {
        final String pattern = "ab\\dc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ab9c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab9c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___d__d__d() throws Throwable {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blah45";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc() throws Throwable {
        final String pattern = "^abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcdef";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__abc2() throws Throwable {
        final String pattern = "^abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "bcdabc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b4() throws Throwable {
        final String pattern = "a?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b5() throws Throwable {
        final String pattern = "a?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b6() throws Throwable {
        final String pattern = "a?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___b() throws Throwable {
        final String pattern = ".?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b() throws Throwable {
        final String pattern = "a??b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b2() throws Throwable {
        final String pattern = "a??b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b3() throws Throwable {
        final String pattern = "a??b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b() throws Throwable {
        final String pattern = ".??b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b4() throws Throwable {
        final String pattern = "a?+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b5() throws Throwable {
        final String pattern = "a?+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b6() throws Throwable {
        final String pattern = "a?+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b2() throws Throwable {
        final String pattern = ".?+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b7() throws Throwable {
        final String pattern = "a+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b8() throws Throwable {
        final String pattern = "a+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b9() throws Throwable {
        final String pattern = "a+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___b2() throws Throwable {
        final String pattern = ".+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b7() throws Throwable {
        final String pattern = "a+?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b8() throws Throwable {
        final String pattern = "a+?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b9() throws Throwable {
        final String pattern = "a+?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b3() throws Throwable {
        final String pattern = ".+?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b10() throws Throwable {
        final String pattern = "a++b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b11() throws Throwable {
        final String pattern = "a++b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b12() throws Throwable {
        final String pattern = "a++b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b4() throws Throwable {
        final String pattern = ".++b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3_() throws Throwable {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3_2() throws Throwable {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3_3() throws Throwable {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3_4() throws Throwable {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_3__() throws Throwable {
        final String pattern = "a{3,}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzaaaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_3__2() throws Throwable {
        final String pattern = "a{3,}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3__() throws Throwable {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3__2() throws Throwable {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3__3() throws Throwable {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_2_3__4() throws Throwable {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___d_() throws Throwable {
        final String pattern = "abc(?=d)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzabcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___d_2() throws Throwable {
        final String pattern = "abc(?=d)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzabced";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___d_3() throws Throwable {
        final String pattern = "abc(?!d)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzabcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_abc___d_4() throws Throwable {
        final String pattern = "abc(?!d)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzabced";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___w____a_() throws Throwable {
        final String pattern = "\\w(?<=a)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "###abc###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___w____a_2() throws Throwable {
        final String pattern = "\\w(?<=a)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "###ert###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____a___w() throws Throwable {
        final String pattern = "(?<!a)\\w";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "###abc###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____a_c() throws Throwable {
        final String pattern = "(?<!a)c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "bc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____a_c2() throws Throwable {
        final String pattern = "(?<!a)c";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ac";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a_b__() throws Throwable {
        final String pattern = "(a+b)+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test__a_b__2() throws Throwable {
        final String pattern = "(a|b)+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ccccd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test__ab__() throws Throwable {
        final String pattern = "(ab)+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test__ab__2() throws Throwable {
        final String pattern = "(ab)+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "accccd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test__ab__3() throws Throwable {
        final String pattern = "(ab)*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test__ab__cd__() throws Throwable {
        final String pattern = "(ab)(cd*)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzabczzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
        assertEquals("c", matcher.group(2));
    }

    @Test
    public void test_abc_d__abc() throws Throwable {
        final String pattern = "abc(d)*abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "abcdddddabc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcdddddabc", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("d", matcher.group(1));
    }

    @Test
    public void test____() throws Throwable {
        final String pattern = "\\*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "*";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("*", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____() throws Throwable {
        final String pattern = "\\\\";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____2() throws Throwable {
        final String pattern = "\\\\";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "\\\\\\\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a__bc__1() throws Throwable {
        final String pattern = "(a*)bc\\1";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzaabcaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aabcaa", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("aa", matcher.group(1));
    }

    @Test
    public void test__a__bc__12() throws Throwable {
        final String pattern = "(a*)bc\\1";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzaabcazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abca", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("a", matcher.group(1));
    }

    @Test
    public void test__gt___dde___yu___1__3_vv_() throws Throwable {
        final String pattern = "(gt*)(dde)*(yu)\\1\\3(vv)";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "zzzgttddeddeyugttyuvvzzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("gttddeddeyugttyuvv", matcher.group());
        assertEquals(4, matcher.groupCount());
        assertEquals("gtt", matcher.group(1));
        assertEquals("dde", matcher.group(2));
        assertEquals("yu", matcher.group(3));
        assertEquals("vv", matcher.group(4));
    }

    @Test
    public void test_a_b10() throws Throwable {
        final String pattern = "a*b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b11() throws Throwable {
        final String pattern = "a*b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a_b12() throws Throwable {
        final String pattern = "a*b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___b3() throws Throwable {
        final String pattern = ".*b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b13() throws Throwable {
        final String pattern = "a*?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b14() throws Throwable {
        final String pattern = "a*?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b15() throws Throwable {
        final String pattern = "a*?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b5() throws Throwable {
        final String pattern = ".*?b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b16() throws Throwable {
        final String pattern = "a*+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b17() throws Throwable {
        final String pattern = "a*+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__b18() throws Throwable {
        final String pattern = "a*+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____b6() throws Throwable {
        final String pattern = ".*+b";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___i_foobar() throws Throwable {
        final String pattern = "(?i)foobar";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("fOobAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_f__i_oobar() throws Throwable {
        final String pattern = "f(?i)oobar";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("fOobAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_foo__i_bar() throws Throwable {
        final String pattern = "foo(?i)bar";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___i_foo_bar__() throws Throwable {
        final String pattern = "(?i)foo[bar]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "foObAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("foObAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___i_foo_a_r__() throws Throwable {
        final String pattern = "(?i)foo[a-r]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "foObAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("foObAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____Eabc() throws Throwable {
        final String pattern = "\\Q***\\Eabc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_bl__Q_____Eabc() throws Throwable {
        final String pattern = "bl\\Q***\\Eabc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "bl***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("bl***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q___abc() throws Throwable {
        final String pattern = "\\Q***abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_blah__Q_____Eabc() throws Throwable {
        final String pattern = "blah\\Q***\\Eabc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blah***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blah***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q___abc2() throws Throwable {
        final String pattern = "\\Q***abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_ab() throws Throwable {
        final String pattern = "\\Q*ab";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "*ab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("*ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_blah__Q___abc() throws Throwable {
        final String pattern = "blah\\Q***abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blah***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blah***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_bla__Q___abc() throws Throwable {
        final String pattern = "bla\\Q***abc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "bla***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("bla***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__ab__Qdef__E_() throws Throwable {
        final String pattern = "[ab\\Qdef\\E]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__ab__Q___E_() throws Throwable {
        final String pattern = "[ab\\Q[\\E]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "[";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("[", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____Q___E_() throws Throwable {
        final String pattern = "[\\Q]\\E]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "]";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("]", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____Q____E_() throws Throwable {
        final String pattern = "[\\Q\\\\E]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____Q___E_2() throws Throwable {
        final String pattern = "[\\Q(\\E]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "(";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("(", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____n___() throws Throwable {
        final String pattern = doSpecialChars("[\\n-#]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "!";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("!", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____n___2() throws Throwable {
        final String pattern = doSpecialChars("[\\n-#]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "-";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____w___() throws Throwable {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "!";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____w___2() throws Throwable {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____w___3() throws Throwable {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "-";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("-", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____w___4() throws Throwable {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "#";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____043__() throws Throwable {
        final String pattern = "[\\043]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____042___044__() throws Throwable {
        final String pattern = "[\\042-\\044]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u1234___u1236_() throws Throwable {
        final String pattern = doSpecialChars("[\\u1234-\\u1236]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("blahblah\\u1235blech");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1235"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____043__() throws Throwable {
        final String pattern = "[^\\043]*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blahblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___f___() throws Throwable {
        final String pattern = "(|f)?+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "foo";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61____ud800__udc61() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61)?\\ud800\\udc61");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud800____ud800__udc61__ud800() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800)?\\ud800\\udc61\\ud800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud800__udc61___ud800__udc62__ud800__udc62_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800\\udc61(\\ud800\\udc62\\ud800\\udc62)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc62\\ud800\\udc62\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc62\\ud800\\udc62\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800\\udc62"), matcher.group(2));
    }

    @Test
    public void test_____ud800__udc61__ud800__udc61__ud800___ud800__udc62__ud800__udc62__ud800_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_____ud800__udc61___ud800__udc62____ud800__udc62__() throws Throwable {
        final String pattern = doSpecialChars("((\\ud800\\udc61|\\ud800\\udc62)?\\ud800\\udc62)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(1));
    }

    @Test
    public void test_____ud800___ud800__udc62____ud800__udc62__() throws Throwable {
        final String pattern = doSpecialChars("((\\ud800|\\ud800\\udc62)?\\ud800\\udc62)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61__ud800__udc61__ud800__udc61____ud800__udc61__ud800__udc61__ud800__udc61() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61)?\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud800__udc61__ud800__ud800__udc61____ud800__udc61__ud800__udc61__ud800__ud800__udc61() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61)?\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud800___ud800__udc62__ud800_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_____ud800__udc61___ud800__udc62_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc62)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc61"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(2));
    }

    @Test
    public void test_____ud800__udc61__ud800___ud800__udc62__ud800_____2() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_____ud800__udc61___ud800__udc62___ud800__udc63________ud800__udc61__ud800__udc62__ud800__udc63() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc62(\\ud800\\udc63)?)?)?\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud800___ud800__udc62___ud800__udc63________ud800__udc61__ud800__ud800__udc62__ud800__udc63() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62(\\ud800\\udc63)?)?)?\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61___ud800__udc02___ud800__udc63_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc02(\\ud800\\udc63))).*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc02\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02\\ud800\\udc63"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc02\\ud800\\udc63"), matcher.group(2));
        assertEquals(doSpecialChars("\\ud800\\udc63"), matcher.group(3));
    }

    @Test
    public void test_____ud800__udc61___ud800___ud800__udc63_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800(\\ud800\\udc63))).*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc63"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\ud800\\udc63"), matcher.group(2));
        assertEquals(doSpecialChars("\\ud800\\udc63"), matcher.group(3));
    }

    @Test
    public void test_______a__xyz() throws Throwable {
        final String pattern = "(.)([^a])xyz";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00xyz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\ud800\\udc00xyz"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud801"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group(2));
    }

    @Test
    public void test___a_z___() throws Throwable {
        final String pattern = "[^a-z]..";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00xyz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\ud800\\udc00x"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___() throws Throwable {
        final String pattern = ".$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___2() throws Throwable {
        final String pattern = ".$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___3() throws Throwable {
        final String pattern = ".$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud800\\udc00\\udcff");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udcff"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___x___uffff___y___uffff_() throws Throwable {
        final String pattern = doSpecialChars("[^x-\\uffff][^y-\\uffff]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00pqr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00p"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___x___uffff__() throws Throwable {
        final String pattern = doSpecialChars("[^x-\\uffff]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00pqrx");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00pqr"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc__x_bl__ud800__udc61h() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)bl\\ud800\\udc61h");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc__x___bl__ud800__udc61h() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc__x___bl__ud800__udc61h__blech() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h  blech");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61hblech");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61hblech"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc__x___bl__ud800__udc61h___ignore_comment() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h # ignore comment");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc62() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc62");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc62___ud800() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc62|\\ud800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc62___ud800() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc62|\\ud800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud802__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud802__udc02___ud803__udc03__ud804__udc04() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02|\\ud803\\udc03\\ud804\\udc04");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc61d() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc61d");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_z___ud800__udc61___ud800__udc61c___ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars("z(\\ud800\\udc61|\\ud800\\udc61c)\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\ud800\\udc61c\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("z\\ud800\\udc61c\\ud802\\udc02"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61c"), matcher.group(1));
    }

    @Test
    public void test_z___ud800__udc61___ud800__udc61c___udc61c___ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars("z(\\ud800\\udc61|\\ud800\\udc61c|\\udc61c)\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\udc61c\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("z\\udc61c\\ud802\\udc02"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\udc61c"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61__ud802__udc02c__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02c__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02c__ud800__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c\\ud800]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61bc__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61bc]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("d\\ud800\\udc62fg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61bc_____ud804__udc04ef_____ud807__udc07hi__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61bc]+[\\ud804\\udc04ef]+[\\ud807\\udc07hi]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud804\\udc04\\ud804\\udc04\\ud807\\udc07\\ud807\\udc07zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud804\\udc04\\ud804\\udc04\\ud807\\udc07\\ud807\\udc07"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud807__udc07__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud807\\udc07]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud8ff\\udcff\\ud8ff\\udcff\\ud8ff\\udcff\\ud807\\udc07\\ud807\\udc07\\ud807\\udc07");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud807\\udc07\\ud807\\udc07\\ud807\\udc07"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud807__udc07__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud807\\udc07]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61___() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61-]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\ud800\\udc61-9z");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61-"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud802__udc02c__() throws Throwable {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud802__udc02__ud803__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02\\ud802\\udc02\\ud802\\udc02\\ud803\\udc03\\ud803\\udc03\\ud803\\udc03\\ud804\\udc04efg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04efg"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61__ud802__udc02__ud803__udc03__ud800__() throws Throwable {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud800]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02\\ud802\\udc02\\ud802\\udc02\\ud803\\udc03\\ud803\\udc03\\ud803\\udc03\\ud804\\udc04efg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04efg"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01__ud802__udc02__ud803__udc03___ud802__udc02_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03^\\ud802\\udc02]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01__ud802__udc02__ud803__udc03___ud802__udc02_2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03^\\ud802\\udc02]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud804__udc04_0_9____ud80b__udc0b___ud80d__udc0d__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud804__udc04_0_9____ud80b__udc0b___ud80d__udc0d__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80c\\udc0c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80c\\udc0c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud804__udc04_0_9____ud80b__udc0b___ud80d__udc0d__3() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud804__udc04_0_9____ud80b__udc0b___ud80d__udc0d__4() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud804__udc04_0_9____ud80b__udc0b___ud80d__udc0d__5() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud816\\udc16");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud804__udc04__0_9____ud80b__udc0b___ud80d__udc0d__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud804\\udc04][0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud804__udc04__0_9____ud80b__udc0b___ud80d__udc0d__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud804\\udc04][0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03___ud804__udc04___ud806__udc06___ud807__udc07___ud809__udc09___() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03___ud804__udc04___ud806__udc06___ud807__udc07___ud809__udc09___2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03___ud804__udc04___ud806__udc06___ud807__udc07___ud809__udc09___3() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud808\\udc08");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud808\\udc08"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03___ud804__udc04___ud806__udc06___ud807__udc07___ud809__udc09___4() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03___ud804__udc04___ud806__udc06___ud807__udc07___ud809__udc09____ud80d__udc0d_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]\\ud80d\\udc0d]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06___ud807__udc07__ud808__udc08__ud809__udc09_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06___ud807__udc07__ud808__udc08__ud809__udc09_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06___ud807__udc07__ud808__udc08__ud809__udc09_2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud808\\udc08");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud808\\udc08"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06___ud807__udc07__ud808__udc08__ud809__udc09_3() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud816\\udc16");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03_____ud804__udc04___ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03_____ud804__udc04___ud806__udc06__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03_____ud804__udc04___ud806__udc06__3() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud804__udc04___ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud804__udc04___ud806__udc06__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud804__udc04___ud806__udc06__3() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03____ud804__udc04___ud806__udc06_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud804\\udc04-\\ud806\\udc06]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud80d__udc0d____ud80d__udc0d___ud81a__udc1a_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud80d__udc0d____ud80d__udc0d___ud81a__udc1a____ud801__udc01___ud803__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a&&\\ud801\\udc01-\\ud803\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud80d__udc0d____ud80d__udc0d___ud81a__udc1a____ud801__udc01___ud81a__udc1a_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a&&\\ud801\\udc01-\\ud81a\\udc1a]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud80d__udc0d______ud80d__udc0d___ud81a__udc1a__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud80d__udc0d______ud80d__udc0d___ud81a__udc1a__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud80d__udc0d______ud80d__udc0d___ud81a__udc1a__3() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud80d__udc0d_______ud801__udc01___ud803__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud80d__udc0d_______ud801__udc01___ud803__udc03__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud80d__udc0d______ud801__udc01___ud803__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud80d__udc0d______ud801__udc01___ud803__udc03__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03__ud804__udc04___ud806__udc06_____ud804__udc04___ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03\\ud804\\udc04-\\ud806\\udc06&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03__ud804__udc04___ud806__udc06_____ud804__udc04___ud806__udc06__2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03\\ud804\\udc04-\\ud806\\udc06&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03_____ud804__udc04___ud806__udc06__ud801__udc01___ud803__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&\\ud804\\udc04-\\ud806\\udc06\\ud801\\udc01-\\ud803\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud804__udc04___ud806__udc06____ud801__udc01___ud803__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06][\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03____ud804__udc04___ud806__udc06_____ud801__udc01__ud802__udc02__ud803__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03][\\ud804\\udc04-\\ud806\\udc06]&&\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03____ud804__udc04___ud806__udc06_____ud801__udc01__ud802__udc02__ud803__udc03___ud804__udc04__ud805__udc05__ud806__udc06__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03][\\ud804\\udc04-\\ud806\\udc06]&&\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud802__udc02___ud804__udc04______ud803__udc03___ud805__udc05__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04]&&[\\ud803\\udc03-\\ud805\\udc05]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud802__udc02___ud804__udc04______ud803__udc03___ud805__udc05__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04]&&[\\ud803\\udc03-\\ud805\\udc05]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud803__udc03______ud802__udc02___ud804__udc04____ud803__udc03___ud805__udc05______ud815__udc15___ud81a__udc1a__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04][\\ud803\\udc03-\\ud805\\udc05]&&[\\ud815\\udc15-\\ud81a\\udc1a]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01__ud802__udc02__ud803__udc03____ud802__udc02__ud803__udc03__ud804__udc04__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[^\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__udc02__ud803__udc03____ud802__udc02__ud803__udc03__ud804__udc04__() throws Throwable {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[^\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03____ud801__udc01___ud804__udc04____ud801__udc01___ud805__udc05__ud807__udc07__ud808__udc08__ud809__udc09_() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud801\\udc01-\\ud804\\udc04&&\\ud801\\udc01-\\ud805\\udc05\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud801__udc01___ud803__udc03____ud801__udc01___ud804__udc04____ud801__udc01___ud805__udc05__ud807__udc07__ud808__udc08__ud809__udc09_2() throws Throwable {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud801\\udc01-\\ud804\\udc04&&\\ud801\\udc01-\\ud805\\udc05\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud807\\udc07");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud801__udc01___ud802__udc02_______ud802__udc02___ud801__udc01___() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud801\\udc01[\\ud802\\udc02]]&&[\\ud802\\udc02[\\ud801\\udc01]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61____b__c____ud800__udc61_____d__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][c][\\ud800\\udc61]&&[^d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61______ud802__udc02____ud800____ud800__udc61_______ud804__udc04__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[\\ud802\\udc02][\\ud800][\\ud800\\udc61]&&[^\\ud804\\udc04]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61____b____ud800____ud800__udc61_______ud804__udc04__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][\\ud800][\\ud800\\udc61]&&[^\\ud804\\udc04]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61____b__c____ud800__udc61_____d__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][c][\\ud800\\udc61]&&[^d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06___() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06___2() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06_______ud800__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06_______ud800__udc03_____ud800__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06_______ud800__udc03_____ud800__udc03____ud800__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03&&\\ud800\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc01___ud800__udc04______ud800__udc03___ud800__udc06_______ud800__udc03_____ud800__udc03_____ud800__udc03__ud800__udc04__ud800__udc05__() throws Throwable {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03&&[\\ud800\\udc03\\ud800\\udc04\\ud800\\udc05]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__z___ud800__udc61b__ud800__udc03__b__ud800__udc03__ud800__udc04__() throws Throwable {
        final String pattern = doSpecialChars("[z[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__z___ud800__udc61b__ud800__udc03__b__ud800__udc03__ud800__udc04____u_z__() throws Throwable {
        final String pattern = doSpecialChars("[z[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04]&&[u-z]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__x___ud800__udc61b__ud800__udc03__b__ud800__udc03__ud800__udc04_z_____u_z__() throws Throwable {
        final String pattern = doSpecialChars("[x[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04[z]]&&[u-z]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__x__wz___ud800__udc61b__ud800__udc03__b__ud800__udc03__ud800__udc04_z_____u_z__() throws Throwable {
        final String pattern = doSpecialChars("[x[[wz]\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04[z]]&&[u-z]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61b__ud800__udc03______ud800__udc04__ud800__udc05f___ud800__udc61b__ud800__udc03_() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61b\\ud800\\udc03]&&[\\ud800\\udc04\\ud800\\udc05f]\\ud800\\udc61b\\ud800\\udc03]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc61b__ud800__udc03______ud800__udc04__ud800__udc05f_xyz___ud800__udc61b__ud800__udc03__() throws Throwable {
        final String pattern = doSpecialChars("[[\\ud800\\udc61b\\ud800\\udc03]&&[\\ud800\\udc04\\ud800\\udc05f]xyz[\\ud800\\udc61b\\ud800\\udc03]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL3() throws Throwable {
        final String pattern = "\\pL";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsASCII_3() throws Throwable {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pLbc2() throws Throwable {
        final String pattern = "\\pLbc";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_r__p_InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__p_InGreek_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__P_InGreek_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\P{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__P_InGreek_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\P{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___ud800__udc61__p__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61\\p{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___ud800__udc61__P__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61\\P{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___ud800__udc61__p_InGreek_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___P_InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\P{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___P_InGreek__c2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\P{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[{^InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61nc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61nc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___InGreek__c2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[{^InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61zc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p__InGreek__c() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61[\\p{^InGreek}]c");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___ud800__udc61___P__InGreek__c() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61[\\P{^InGreek}]c");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___ud800__udc61___p_InGreek__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_r__p_InGreek__c2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_InGreek_r_c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}r]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_r__p_InGreek__c3() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____p_InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[^\\p{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____P_InGreek__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[^\\P{InGreek}]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_InGreek_______u0370__c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}&&[^\\u0370]]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_c__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61.c.+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61#c%&");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61#c%&"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61b_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61b.");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s___ud800__udc61b_() throws Throwable {
        final String pattern = doSpecialChars("(?s)\\ud800\\udc61b.");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_L______P_InGreek___c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u6000c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u6000c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_L______P_InGreek___c2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___p_L______P_InGreek___c3() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__p_InGreek_c() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}c");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__p_Sc_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{Sc}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61$");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61$"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L_2() throws Throwable {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf1e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf1e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a__p_L_z_() throws Throwable {
        final String pattern = "^a\\p{L}z$";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\ud800\\udf1ez");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud800\\udf1ez"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udf00__p_L__2_3___P_L__supp____ud900__udc00____P_InDeseret_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\P{InDeseret}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1esupp->\\ud900\\udc00<-\\ud901\\udf00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1esupp->\\ud900\\udc00<-\\ud901\\udf00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udf00__p_L__2_3___P_L__supp____ud900__udc00____P_InDeseret_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\P{InDeseret}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud901\\udf00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud901\\udf00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udf00__p_L__2_3___P_L__supp____ud900__udc00____p_InDeseret_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\p{InDeseret}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud801\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud801\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61b__wc() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\wc");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc___w_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\w]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcd");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcd"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc___sdef__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\sdef]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc  def");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc  def"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61bc___sy_z__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\sy-z]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc y z");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc y z"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc01bc___ud800__udc01___ud800__udc04__sm_p__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc01bc[\\ud800\\udc01-\\ud800\\udc04\\sm-p]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc01bc\\ud800\\udc01\\ud800\\udc01 mn  p");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc01bc\\ud800\\udc01\\ud800\\udc01 mn  p"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61b__s__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\s\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b \\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b \\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s__s__s2() throws Throwable {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h  err");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___S__S__s2() throws Throwable {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h  err");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61h "), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61b__d__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\d\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b9\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b9\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___d__d__d2() throws Throwable {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h45");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61bc() throws Throwable {
        final String pattern = doSpecialChars("^\\ud800\\udc61bc");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcdef");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61bc2() throws Throwable {
        final String pattern = doSpecialChars("^\\ud800\\udc61bc");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("bcd\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___udc61___ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800___ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc023() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc03\\ud800\\udc03\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars(".?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800____ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\ud800\\ud8001\\ud800\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800____ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc023() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars(".??\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc024() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc025() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc026() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars(".?+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc024() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___udc61___ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc025() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800___ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud800__udc026() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc022() throws Throwable {
        final String pattern = doSpecialChars(".+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud800__udc023() throws Throwable {
        final String pattern = doSpecialChars(".+\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc027() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___udc61____ud800__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc028() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800____ud800__udc023() throws Throwable {
        final String pattern = doSpecialChars("\\ud800+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc029() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc023() throws Throwable {
        final String pattern = doSpecialChars(".+?\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc0210() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc0211() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud800__udc0212() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud800__udc024() throws Throwable {
        final String pattern = doSpecialChars(".++\\ud800\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3_3() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3_4() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_3__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{3,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_3__2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{3,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3__() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3__2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3__3() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61_2_3__4() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____ud804__udc04_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____ud804__udc04_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03e\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____udcff__ud804__udc04_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\udcff\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\udcff\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____udcff__ud804__udc04_2() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\udcff\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud8ff\\udcff\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____ud804__udc04_3() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__ud802__udc02__ud803__udc03_____ud804__udc04_() throws Throwable {
        final String pattern = doSpecialChars("a\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zza\\ud802\\udc02\\ud803\\udc03\\udc04\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61__ud802__udc02__ud803__udc03_____ud804__udc04__ud8ff_() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04\\ud8ff)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04\\ud8ffX");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__ud802__udc02__ud803__udc03_____ud804__udc04__ud8ff_() throws Throwable {
        final String pattern = doSpecialChars("a\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04\\ud8ff)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zza\\ud802\\udc02\\ud803\\udc03e\\ud804\\udc04\\ud8ff\\udcff");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______ud801__udc01__ud802__udc02___ud803__udc03() throws Throwable {
        final String pattern = doSpecialChars("(?<=\\ud801\\udc01\\ud802\\udc02)\\ud803\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______ud801__udc01___ud802__udc02__ud803__udc03() throws Throwable {
        final String pattern = doSpecialChars("(?<!\\ud801\\udc01)\\ud802\\udc02\\ud803\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\ud800\\udc00\\ud802\\udc02\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test________ud801__udc01__ud802__udc02____ud803__udc03_() throws Throwable {
        final String pattern = doSpecialChars("(?<![\\ud801\\udc01\\ud802\\udc02])\\ud803\\udc03.");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud803\\udc03x\\ud800\\udc00\\ud803\\udc03y");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03y"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______ud801__udc01___ud803__udc03() throws Throwable {
        final String pattern = doSpecialChars("(?<!\\ud801\\udc01)\\ud803\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61___ud802__() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61+\\ud802)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61___ud802__2() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61|\\ud802)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\ud802\\udc61\\ud803\\ud802\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61__ud802__2() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61ccccd");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test____ud800__udc61__ud802__3() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61b__cd__() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61b)(cd*)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61bczzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61b"), matcher.group(1));
        assertEquals("c", matcher.group(2));
    }

    @Test
    public void test___ud800__udc61bc___ud804__udc04____ud800__udc61bc() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(\\ud804\\udc04)*\\ud800\\udc61bc");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud800\\udc61bc"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61____ud802__udc02c__1() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61*)\\ud802\\udc02c\\1");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc61____ud802__udc02c__12() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc61*)\\ud802\\udc02c\\1");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group(1));
    }

    @Test
    public void test____ud800__udc07__ud800__udc14_____ud804__udc04__ud804__udc04e___yu___1__3_vv_() throws Throwable {
        final String pattern = doSpecialChars("(\\ud800\\udc07\\ud800\\udc14*)(\\ud804\\udc04\\ud804\\udc04e)*(yu)\\1\\3(vv)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc07\\ud800\\udc14\\ud800\\udc14\\ud804\\udc04\\ud804\\udc04e\\ud804\\udc04\\ud804\\udc04eyu\\ud800\\udc07\\ud800\\udc14\\ud800\\udc14yuvvzzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc07\\ud800\\udc14\\ud800\\udc14\\ud804\\udc04\\ud804\\udc04e\\ud804\\udc04\\ud804\\udc04eyu\\ud800\\udc07\\ud800\\udc14\\ud800\\udc14yuvv"), matcher.group());
        assertEquals(4, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc07\\ud800\\udc14\\ud800\\udc14"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud804\\udc04\\ud804\\udc04e"), matcher.group(2));
        assertEquals("yu", matcher.group(3));
        assertEquals("vv", matcher.group(4));
    }

    @Test
    public void test___ud800__udc61___ud802__udc023() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud802__udc024() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61___ud802__udc025() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars(".*\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc023() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars(".*?\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc024() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc025() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud800__udc61____ud802__udc026() throws Throwable {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______ud802__udc022() throws Throwable {
        final String pattern = doSpecialChars(".*+\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu___ud801__udc00__ud801__udc01__ud801__udc02x() throws Throwable {
        final String pattern = doSpecialChars("(?iu)\\ud801\\udc00\\ud801\\udc01\\ud801\\udc02x");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2aX");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2aX"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud801__udc00__iu___ud801__udc01__ud801__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\ud801\\udc00(?iu)\\ud801\\udc01\\ud801\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc00\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc00\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud801__udc00__iu___ud801__udc01__ud801__udc022() throws Throwable {
        final String pattern = doSpecialChars("\\ud801\\udc00(?iu)\\ud801\\udc01\\ud801\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu___ud801__udc00___ud801__udc01__ud801__udc02__() throws Throwable {
        final String pattern = doSpecialChars("(?iu)\\ud801\\udc00[\\ud801\\udc01\\ud801\\udc02]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu____ud801__udc00___ud801__udc02__() throws Throwable {
        final String pattern = doSpecialChars("(?iu)[\\ud801\\udc00-\\ud801\\udc02]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____E__ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud802__udc02l__Q_____E__ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud802__udc02l__ud801__udc01h__Q_____E__ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____ud801__udc01__ud802__udc02__ud800__udc032() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q___ud801__udc01__ud802__udc02() throws Throwable {
        final String pattern = doSpecialChars("\\Q*\\ud801\\udc01\\ud802\\udc02");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("*\\ud801\\udc01\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("*\\ud801\\udc01\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud802__udc02l__ud801__udc01h__Q_____ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___ud802__udc02l__ud801__udc01__Q_____ud801__udc01__ud802__udc02__ud800__udc03() throws Throwable {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uD801__uDFF1__uDB00__uDC00() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uD801__uDFF1__uDB00__uDC002() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u1000\\uD801\\uDFF1\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uD801__uDFF1__uDB00__uDC003() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uFFFF\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uD801__uDFF1__uDB00__uDC004() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uFFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u1000___uFFFF() throws Throwable {
        final String pattern = doSpecialChars("\\u1000.\\uFFFF");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u1000\\uD800\\uDFFF\\uFFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1000\\uD800\\uDFFF\\uFFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a___uD800__uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a___uD800__uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__a___uD800__uDFFF_3() throws Throwable {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____uD800__uDC00___uDBFF__uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("[\\uD800\\uDC00-\\uDBFF\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDBFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____uD800__uDC00___uDBFF__uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("[\\uD800\\uDC00-\\uDBFF\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____uD800___uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("[\\uD800-\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____uD800___uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("[\\uD800-\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_foo____uD800___uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("foo[^\\uD800-\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("foo\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("foo\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_foo____uD800___uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("foo[^\\uD800-\\uDFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("foo\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__ab__uD800__uDFFFcd_at() throws Throwable {
        final String pattern = doSpecialChars("[ab\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800at");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test__ab__uD800__uDFFFcd_at2() throws Throwable {
        final String pattern = doSpecialChars("[ab\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFFat");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFFat"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____uD800__uDFFFcd_at() throws Throwable {
        final String pattern = doSpecialChars("[^\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800at");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800at"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____uD800__uDFFFcd_at2() throws Throwable {
        final String pattern = doSpecialChars("[^\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFFat");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFFat"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF___uFFFF_() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF-\\uFFFF]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800___uDFFF___uFFFF__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800[\\uDFFF-\\uFFFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uFFFF_____uD800__uDFFF__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uD800\\uDFFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uFFFF_____uD800__uDFFF__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uD800\\uDFFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uFFFF_____uDFFF__uD800__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uDFFF\\uD800]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uFFFF_____uDFFF__uD800__uDC00__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uDFFF\\uD800\\uDC00]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uDFFF_____uD800___uFFFF__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uDFFF&&[\\uD800-\\uFFFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uDFFF_____uD800___uFFFF__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uDFFF&&[\\uD800-\\uFFFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF______uD800__uDC00__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF______uD800__uDC00__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF______uD800__uDC00__3() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF______uD800__uDBFF__uDC00__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDBFF\\uDC00]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u0000___uD800__uDFFF______uDC00__uD800__uDBFF__() throws Throwable {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uDC00\\uD800\\uDBFF]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_3() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uDFFF__uD800_() throws Throwable {
        final String pattern = doSpecialChars("a\\uDFFF\\uD800?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uDFFF__uD800_2() throws Throwable {
        final String pattern = doSpecialChars("a\\uDFFF\\uD800?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uDC00_() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uDC00?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF__uDC00_2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uDC00?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF__() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF??");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_4() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_5() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800___uDFFF() throws Throwable {
        final String pattern = doSpecialChars("\\uD800*\\uDFFF");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_a__uD800__uDFFF_6() throws Throwable {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_2() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_4() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_5() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_6() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_3() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800___uDFFF2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800+\\uDFFF");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800___uDFFF3() throws Throwable {
        final String pattern = doSpecialChars("\\uD800+\\uDFFF");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF___uD800() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF+\\uD800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF___uD8002() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF+\\uD800");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_3_() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_3_2() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_2__() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_2__2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_2__3() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_2__() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{2,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_2__2() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{2,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_4_() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_4_2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_4_3() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uDFFF__uD800_3_5_() throws Throwable {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3,5}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800\\uD800\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_5_() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,5}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uD800__uDFFF_3_5_2() throws Throwable {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,5}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____uD800___uDFFF__() throws Throwable {
        final String pattern = doSpecialChars("(\\uD800(\\uDFFF))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test____uD800___uDC00____uDFFF__() throws Throwable {
        final String pattern = doSpecialChars("(\\uD800(\\uDC00)(\\uDFFF))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDC00\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_____uD800____uDFFF__() throws Throwable {
        final String pattern = doSpecialChars("((\\uD800)(\\uDFFF))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test____uD800___uDFFF___uDFFF_() throws Throwable {
        final String pattern = doSpecialChars("(\\uD800(\\uDFFF)\\uDFFF)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test____uDFFF___uD800____uDBFF__() throws Throwable {
        final String pattern = doSpecialChars("(\\uDFFF(\\uD800)(\\uDBFF))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDBFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uDBFF"), matcher.group());
        assertEquals(3, matcher.groupCount());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uDBFF"), matcher.group(1));
        assertEquals(doSpecialChars("\\uD800"), matcher.group(2));
        assertEquals(doSpecialChars("\\uDBFF"), matcher.group(3));
    }

    @Test
    public void test____uDFFF___uD800____uDC00__() throws Throwable {
        final String pattern = doSpecialChars("(\\uDFFF(\\uD800)(\\uDC00))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test____uDFFF__uD800___uDC00__uDBFF__() throws Throwable {
        final String pattern = doSpecialChars("(\\uDFFF\\uD800(\\uDC00\\uDBFF))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDC00\\uDBFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test____uD800__uDFFF___uDBFF____uDC00__() throws Throwable {
        final String pattern = doSpecialChars("(\\uD800\\uDFFF(\\uDBFF)(\\uDC00))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test____uD800__uDFFF___uDBFF__uDC00__() throws Throwable {
        final String pattern = doSpecialChars("(\\uD800\\uDFFF(\\uDBFF\\uDC00))");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00"), matcher.group(1));
        assertEquals(doSpecialChars("\\uDBFF\\uDC00"), matcher.group(2));
    }

    @Test
    public void test_____u3042____u3042() throws Throwable {
        final String pattern = doSpecialChars("^(\\u3042)?\\u3042");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____u3042__u3042___u3043__u3043_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\u3042\\u3042(\\u3043\\u3043)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3043\\u3043\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3043\\u3043\\u3042\\u3042"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3043\\u3043"), matcher.group(2));
    }

    @Test
    public void test_____u3042___u3043____u3043__() throws Throwable {
        final String pattern = doSpecialChars("((\\u3042|\\u3043)?\\u3043)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3043"), matcher.group(1));
    }

    @Test
    public void test____u3042__u3042__u3042____u3042__u3042__u3042() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042\\u3042\\u3042)?\\u3042\\u3042\\u3042");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3043_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043)?)+$");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3043"), matcher.group(2));
    }

    @Test
    public void test_____u3042___u3043___u3044________u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043(\\u3044)?)?)?\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3043___u3044_____() throws Throwable {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043(\\u3044))).*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(3, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3043\\u3044"), matcher.group(2));
        assertEquals(doSpecialChars("\\u3044"), matcher.group(3));
    }

    @Test
    public void test___u3042__u3043__u3044__x___u3043la__u3049() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)\\u3043la\\u3049");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3043la\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3043la\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044__x___bla__u3049() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044__x___bla__u3049__ble__u3044__u3049() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049  ble\\u3044\\u3049");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049ble\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049ble\\u3044\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044__x___bla__u3049___ignore_comment() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049 # ignore comment");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u3043() throws Throwable {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30432() throws Throwable {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30433() throws Throwable {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u3043___u3044__u3045() throws Throwable {
        final String pattern = doSpecialChars("\\u3042|\\u3043|\\u3044\\u3045");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u3042__u3045() throws Throwable {
        final String pattern = doSpecialChars("\\u3042|\\u3042\\u3045");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u305B___u3042___u3042__u3044___u3043() throws Throwable {
        final String pattern = doSpecialChars("\\u305B(\\u3042|\\u3042\\u3044)\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042\\u3044\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B\\u3042\\u3044\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3044"), matcher.group(1));
    }

    @Test
    public void test____u3042__u3043__u3044__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045\\u3046\\u3047\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044_____u3045__u3046__u3047_____u3048__u3049__u304A__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+[\\u3045\\u3046\\u3047]+[\\u3048\\u3049\\u304A]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3045\\u3045\\u3048\\u3048\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3045\\u3045\\u3048\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3048__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3048]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3048\\u3048\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3048\\u3048\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3048__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3048]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042-9\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042-"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042_____u4444__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\\\u4444]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042-9\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042__u3043__u3044__() throws Throwable {
        final String pattern = doSpecialChars("[^\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042__u3043__u3044__2() throws Throwable {
        final String pattern = doSpecialChars("[^\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3043\\u3043\\u3043\\u3044\\u3044\\u3044\\u3045\\u3046\\u3047\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045\\u3046\\u3047\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3043_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044^\\u3043]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3043_2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044^\\u3043]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3045_0_9____u304e___u3051__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3045_0_9____u304e___u3051__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3050");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3050"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3045_0_9____u304e___u3051__3() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3045_0_9____u304e___u3051__4() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3045_0_9____u304e___u3051__5() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3056");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3045__0_9____u304e___u3051__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3045][0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3045__0_9____u304e___u3051__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3045][0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044___u3045___u3047___u3048___u304A___() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044___u3045___u3047___u3048___u304A___2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044___u3045___u3047___u3048___u304A___3() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044___u3045___u3047___u3048___u304A___4() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044___u3045___u3047___u3048___u304A__m_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]m]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047___u3048__u3049__u304A_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047___u3048__u3049__u304A_2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047___u3048__u3049__u304A_3() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044___u3045__u3046__u3047___u3048__u3049__u304A_4() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "w";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044_____u3045___u3047__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044_____u3045___u3047__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044_____u3045___u3047__3() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3045___u3047__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3045___u3047__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3045___u3047__3() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044____u3045___u3047_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3045-\\u3047]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u304e____u304e___u305B_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u304e____u304e___u305B____u3042___u3044_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B&&\\u3042-\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u304e____u304e___u305B____u3042___u305B_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B&&\\u3042-\\u305B]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u304e______u304e___u305B__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u304e______u304e___u305B__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u304e______u304e___u305B__3() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u304e_______u3042___u3044__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u304e_______u3042___u3044__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u304e______u3042___u3044__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u304e______u3042___u3044__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044__u3045___u3047_____u3045___u3047__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044\\u3045-\\u3047&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044__u3045___u3047_____u3045___u3047__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044\\u3045-\\u3047&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044_____u3045___u3047__u3042___u3044_() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&\\u3045-\\u3047\\u3042-\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3045___u3047____u3042___u3044__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047][\\u3042-\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044____u3045___u3047_____u3042__u3043__u3044_() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044][\\u3045-\\u3047]&&\\u3042\\u3043\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044____u3045___u3047_____u3042__u3043__u3044___u3045__u3046__u3047__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044][\\u3045-\\u3047]&&\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3043___u3045______u3044___u3046__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045]&&[\\u3044-\\u3046]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3043___u3045______u3044___u3046__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045]&&[\\u3044-\\u3046]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3044______u3043___u3045____u3044___u3046______u3056___u305B__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045][\\u3044-\\u3046]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044____u3043__u3044__u3045__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[^\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044____u3043__u3044__u3045__2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[^\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044____u3042___u3045____u3042___u3046__u3048__u3049__u304A_() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3042-\\u3045&&\\u3042-\\u3046\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3044____u3042___u3045____u3042___u3046__u3048__u3049__u304A_2() throws Throwable {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3042-\\u3045&&\\u3042-\\u3046\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042___u3043_______u3043___u3042___() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042[\\u3043]]&&[\\u3043[\\u3042]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042______u3043____u3044____u3042_______u3045__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042]&&[\\u3043][\\u3044][\\u3042]&&[^\\u3045]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042____b__c____u3042_____d__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042]&&[b][c][\\u3042]&&[^d]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042______u3043____u3044____u3042_______u3045__2() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042]&&[\\u3043][\\u3044][\\u3042]&&[^\\u3045]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047___() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047___2() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047_______u3044__() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047_______u3044_____u3044_() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047_______u3044_____u3044____u3044_() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044&&\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3042___u3045______u3044___u3047_______u3044_____u3044_____u3044__u3045__u3046__() throws Throwable {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044&&[\\u3044\\u3045\\u3046]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u305B___u3042__u3043__u3044____u3043__u3044__u3045__() throws Throwable {
        final String pattern = doSpecialChars("[\\u305B[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u305B___u3042__u3043__u3044____u3043__u3044__u3045______u3056___u305B__() throws Throwable {
        final String pattern = doSpecialChars("[\\u305B[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3059___u3042__u3043__u3044____u3043__u3044__u3045___u305B_______u3056___u305B__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3059[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045[\\u305B]]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3059__w__u305B___u3042__u3043__u3044____u3043__u3044__u3045___u305B_______u3056___u305B__() throws Throwable {
        final String pattern = doSpecialChars("[\\u3059[[w\\u305B]\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045[\\u305B]]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042__u3043__u3044______u3045__u3046__u3047___u3042__u3043__u3044_() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042\\u3043\\u3044]&&[\\u3045\\u3046\\u3047]\\u3042\\u3043\\u3044]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3042__u3043__u3044______u3045__u3046__u3047___u3059__u305A__u305B___u3042__u3043__u3044__() throws Throwable {
        final String pattern = doSpecialChars("[[\\u3042\\u3043\\u3044]&&[\\u3045\\u3046\\u3047]\\u3059\\u305A\\u305B[\\u3042\\u3043\\u3044]]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL4() throws Throwable {
        final String pattern = "\\pL";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL5() throws Throwable {
        final String pattern = "\\pL";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = "7";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_L_3() throws Throwable {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_IsL_2() throws Throwable {
        final String pattern = "\\p{IsL}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_InHiragana_() throws Throwable {
        final String pattern = "\\p{InHiragana}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___p_InHiragana_2() throws Throwable {
        final String pattern = "\\p{InHiragana}";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___pL__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\pL\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_r__p_InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__p_InGreek_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__P_InGreek_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\P{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__P_InGreek_2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\P{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\u3042{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___u3042__p__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\u3042\\p{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___u3042__P__InGreek_() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\u3042\\P{^InGreek}");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___u3042__p_InGreek_2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___P_InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\P{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___P_InGreek____u30442() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\P{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[{^InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042n\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042n\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___InGreek____u30442() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[{^InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u305B\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p__InGreek____u3044() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\u3042[\\p{^InGreek}]\\u3044");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___u3042___P__InGreek____u3044() throws Throwable {
        try {
            final String pattern = doSpecialChars("\\u3042[\\P{^InGreek}]\\u3044");
            JDKRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test___u3042___p_InGreek__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_r__p_InGreek____u30442() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_InGreek_r___u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}r]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_r__p_InGreek____u30443() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____p_InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[^\\p{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____P_InGreek____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[^\\P{InGreek}]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_InGreek_______u0370____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}&&[^\\u0370]]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u3044__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042.\\u3044.+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042#\\u3044%&");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042#\\u3044%&"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043.");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s___u3042__u3043_() throws Throwable {
        final String pattern = doSpecialChars("(?s)\\u3042\\u3043.");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_L______P_InGreek_____u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u6000\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u6000\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_L______P_InGreek_____u30442() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___p_L______P_InGreek_____u30443() throws Throwable {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__p_InGreek___u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__p_Sc_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\p{Sc}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042$");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042$"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___W__w__W3() throws Throwable {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("rrrr#\\u3048\\u3048\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044___s__u3045__u3046__u3047__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\s\\u3045\\u3046\\u3047]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044  \\u3045\\u3046\\u3047");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044  \\u3045\\u3046\\u3047"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044___s__u305A___u305B__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\s\\u305A-\\u305B]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044 \\u305A \\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044 \\u305A \\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044___u3042___u3045__s__u304e___u3051__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\u3042-\\u3045\\s\\u304e-\\u3051]*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3042\\u3042 \\u304e\\u304f  \\u3051");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3042\\u3042 \\u304e\\u304f  \\u3051"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__s__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\s\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043 \\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043 \\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___s__s__s3() throws Throwable {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049  \\u3046rr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___S__S__s3() throws Throwable {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049  \\u3046rr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3049 "), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__d__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\d\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u30439\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u30439\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___d__d__d3() throws Throwable {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u304945");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("^\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3046\\u3047");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__u30442() throws Throwable {
        final String pattern = doSpecialChars("^\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043\\u3044\\u3045\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30434() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30435() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30436() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u3043() throws Throwable {
        final String pattern = doSpecialChars(".?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u3043() throws Throwable {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30432() throws Throwable {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30433() throws Throwable {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u3043() throws Throwable {
        final String pattern = doSpecialChars(".??\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30434() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30435() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30436() throws Throwable {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u30432() throws Throwable {
        final String pattern = doSpecialChars(".?+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30437() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30438() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u30439() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u30432() throws Throwable {
        final String pattern = doSpecialChars(".+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30437() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30438() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u30439() throws Throwable {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u30433() throws Throwable {
        final String pattern = doSpecialChars(".+?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304310() throws Throwable {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304311() throws Throwable {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304312() throws Throwable {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u30434() throws Throwable {
        final String pattern = doSpecialChars(".++\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3_2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3_3() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3_4() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_3__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{3,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_3__2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{3,}");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3__() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3__2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3__3() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042_2_3__4() throws Throwable {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044_____u3045_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?=\\u3045)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044_____u3045_2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?=\\u3045)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u3046\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044_____u3045_3() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?!\\u3045)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u3042\\u3043\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042__u3043__u3044_____u3045_4() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?!\\u3045)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u3042\\u3043\\u3044\\u3046\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042______u3042_() throws Throwable {
        final String pattern = doSpecialChars("\\u3042(?<=\\u3042)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042______u3042_2() throws Throwable {
        final String pattern = doSpecialChars("\\u3042(?<=\\u3042)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3043\\u3044###");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______u3042___w() throws Throwable {
        final String pattern = doSpecialChars("(?<!\\u3042)\\w");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3042\\u3043\\u3044a###");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______u3042___u3044() throws Throwable {
        final String pattern = doSpecialChars("(?<!\\u3042)\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_______u3042___u30442() throws Throwable {
        final String pattern = doSpecialChars("(?<!\\u3042)\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u3042___u3043__() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042+\\u3043)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test____u3042___u3043__2() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042|\\u3043)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044\\u3044\\u3044\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042\\u3043)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test____u3042__u3043__2() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042\\u3043)+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3044\\u3044\\u3044\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test____u3042__u3043__3() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042\\u3043)*");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test____u3042__u3043____u3044__u3045__() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042\\u3043)(\\u3044\\u3045*)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3044"), matcher.group(2));
    }

    @Test
    public void test___u3042__u3043__u3044___u3045____u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(\\u3045)*\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3045\\u3045\\u3045\\u3045\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3045\\u3045\\u3045\\u3045\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3045"), matcher.group(1));
    }

    @Test
    public void test____u3042____u3043__u3044__1() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042*)\\u3043\\u3044\\1");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3043\\u3044\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3043\\u3044\\u3042\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group(1));
    }

    @Test
    public void test____u3042____u3043__u3044__12() throws Throwable {
        final String pattern = doSpecialChars("(\\u3042*)\\u3043\\u3044\\1");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3043\\u3044\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042"), matcher.group(1));
    }

    @Test
    public void test____u3048t_____u3045__u3045__u3046_____u305A__u3056___1__3___u3057__u3057_() throws Throwable {
        final String pattern = doSpecialChars("(\\u3048t*)(\\u3045\\u3045\\u3046)*(\\u305A\\u3056)\\1\\3(\\u3057\\u3057)");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3048tt\\u3045\\u3045\\u3046\\u3045\\u3045\\u3046\\u305A\\u3056\\u3048tt\\u305A\\u3056\\u3057\\u3057\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3048tt\\u3045\\u3045\\u3046\\u3045\\u3045\\u3046\\u305A\\u3056\\u3048tt\\u305A\\u3056\\u3057\\u3057"), matcher.group());
        assertEquals(4, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3048tt"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3045\\u3045\\u3046"), matcher.group(2));
        assertEquals(doSpecialChars("\\u305A\\u3056"), matcher.group(3));
        assertEquals(doSpecialChars("\\u3057\\u3057"), matcher.group(4));
    }

    @Test
    public void test___u3042___u304310() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u304311() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042___u304312() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____u30433() throws Throwable {
        final String pattern = doSpecialChars(".*\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304313() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304314() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304315() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u30435() throws Throwable {
        final String pattern = doSpecialChars(".*?\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304316() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304317() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3042____u304318() throws Throwable {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test______u30436() throws Throwable {
        final String pattern = doSpecialChars(".*+\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu___uFF46__uFF4F__uFF4F__uFF42__uFF41__uFF52() throws Throwable {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uFF46__iu___uFF4F__uFF4F__uFF42__uFF41__uFF52() throws Throwable {
        final String pattern = doSpecialChars("\\uFF46(?iu)\\uFF4F\\uFF4F\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___uFF46__uFF4F__uFF4F__iu___uFF42__uFF41__uFF52() throws Throwable {
        final String pattern = doSpecialChars("\\uFF46\\uFF4F\\uFF4F(?iu)\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu___uFF46__uFF4F__uFF4F___uFF42__uFF41__uFF52__() throws Throwable {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F[\\uFF42\\uFF41\\uFF52]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___iu___uFF46__uFF4F__uFF4F___uFF41___uFF52__() throws Throwable {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F[\\uFF41-\\uFF52]+");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____E__u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3043l__Q_____E__u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3043l\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3043l__u3042__u3049__Q_____E__u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\u3049\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q_____u3042__u3043__u30442() throws Throwable {
        final String pattern = doSpecialChars("\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___Q___u3042__u3043() throws Throwable {
        final String pattern = doSpecialChars("\\Q*\\u3042\\u3043");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("*\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("*\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3043l__u3042__u3049__Q_____u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\u3049\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test___u3043l__u3042__Q_____u3042__u3043__u3044() throws Throwable {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____043__2() throws Throwable {
        final String pattern = "[\\043]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____042___044__2() throws Throwable {
        final String pattern = "[\\042-\\044]+";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test____u1234___u1236_2() throws Throwable {
        final String pattern = doSpecialChars("[\\u1234-\\u1236]");
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049\\u1235\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1235"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_____043__2() throws Throwable {
        final String pattern = "[^\\043]*";
        final RegularExpression regex = JDKRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

}