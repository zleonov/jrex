package software.leonov.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static software.leonov.regex.Utilities.doSpecialChars;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JRegexRegularExpressionTest {
    
    @Test
    public void test_001_a_a() {
        final String pattern = "^(a)?a";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_002_aa_bb_() {
        final String pattern = "^(aa(bb)?)+$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aabbaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aabbaa", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("aa", matcher.group(1));
        assertEquals("bb", matcher.group(2));
    }

    @Test
    public void test_003_a_b_b_() {
        final String pattern = "((a|b)?b)+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("b", matcher.group(1));
    }

    @Test
    public void test_004_aaa_aaa() {
        final String pattern = "(aaa)?aaa";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_005_a_b_() {
        final String pattern = "^(a(b)?)+$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aba";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aba", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("a", matcher.group(1));
        assertEquals("b", matcher.group(2));
    }

    @Test
    public void test_006_a_b_c_abc() {
        final String pattern = "^(a(b(c)?)?)?abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_007_a_b_c_() {
        final String pattern = "^(a(b(c))).*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_008_abc_x_blah() {
        final String pattern = "abc(?x)blah";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_009_abc_x_blah() {
        final String pattern = "abc(?x)  blah";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_010_abc_x_blah_blech() {
        final String pattern = "abc(?x)  blah  blech";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcblahblech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblahblech", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_011_abc_x_blah_ignore_comment() {
        final String pattern = "abc(?x)  blah # ignore comment";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcblah";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_012_a_b() {
        final String pattern = "a|b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_013_a_b() {
        final String pattern = "a|b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_014_a_b() {
        final String pattern = "a|b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_015_a_b_cd() {
        final String pattern = "a|b|cd";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "cd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("cd", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_016_a_ad() {
        final String pattern = "a|ad";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ad";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_017_z_a_ac_b() {
        final String pattern = "z(a|ac)b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zacb";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("zacb", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ac", matcher.group(1));
    }

    @Test
    public void test_018_abc_() {
        final String pattern = "[abc]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_019_abc_() {
        final String pattern = "[abc]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "defg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_020_abc_def_ghi_() {
        final String pattern = "[abc]+[def]+[ghi]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzaaddggzzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaddgg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_021_a_g_() {
        final String pattern = "[a-g]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzggg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ggg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_022_a_g_() {
        final String pattern = "[a-g]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_023_a_() {
        final String pattern = "[a-]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "za-9z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a-", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_024_a_u4444_() {
        final String pattern = doSpecialChars("[a-\\\\u4444]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "za-9z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("za", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_025_abc_() {
        final String pattern = "[^abc]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_026_abc_() {
        final String pattern = "[^abc]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaabbbcccdefg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("defg", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_027_abc_b_() {
        final String pattern = "[abc^b]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_028_abc_b_() {
        final String pattern = "[abc^b]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_029_abc_def_() {
        final String pattern = "[abc[def]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_030_abc_def_() {
        final String pattern = "[abc[def]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_031_a_d_0_9_m_p_() {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_032_a_d_0_9_m_p_() {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "o";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("o", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_033_a_d_0_9_m_p_() {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_034_a_d_0_9_m_p_() {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_035_a_d_0_9_m_p_() {
        final String pattern = "[a-d[0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "u";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_036_a_d_0_9_m_p_() {
        final String pattern = "[[a-d][0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_037_a_d_0_9_m_p_() {
        final String pattern = "[[a-d][0-9][m-p]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_038_a_c_d_f_g_i_() {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_039_a_c_d_f_g_i_() {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_040_a_c_d_f_g_i_() {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "h";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("h", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_041_a_c_d_f_g_i_() {
        final String pattern = "[a-c[d-f[g-i]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_042_a_c_d_f_g_i_m_() {
        final String pattern = "[a-c[d-f[g-i]]m]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_043_abc_def_ghi_() {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_044_abc_def_ghi_() {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_045_abc_def_ghi_() {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "h";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("h", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_046_abc_def_ghi_() {
        final String pattern = "[abc[def]ghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "w";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_047_a_c_d_f_() {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_048_a_c_d_f_() {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_049_a_c_d_f_() {
        final String pattern = "[a-c&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_050_a_c_d_f_() {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_051_a_c_d_f_() {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_052_a_c_d_f_() {
        final String pattern = "[[a-c]&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_053_a_c_d_f_() {
        final String pattern = "[a-c&&d-f]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_054_a_m_m_z_() {
        final String pattern = "[a-m&&m-z]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_055_a_m_m_z_a_c_() {
        final String pattern = "[a-m&&m-z&&a-c]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_056_a_m_m_z_a_z_() {
        final String pattern = "[a-m&&m-z&&a-z]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_057_a_m_m_z_() {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_058_a_m_m_z_() {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_059_a_m_m_z_() {
        final String pattern = "[[a-m]&&[m-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_060_a_m_a_c_() {
        final String pattern = "[[a-m]&&[^a-c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_061_a_m_a_c_() {
        final String pattern = "[[a-m]&&[^a-c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_062_a_m_a_c_() {
        final String pattern = "[a-m&&[^a-c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_063_a_m_a_c_() {
        final String pattern = "[a-m&&[^a-c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_064_a_cd_f_d_f_() {
        final String pattern = "[a-cd-f&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_065_a_cd_f_d_f_() {
        final String pattern = "[a-cd-f&&[d-f]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_066_a_c_d_fa_c_() {
        final String pattern = "[[a-c]&&d-fa-c]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_067_a_c_d_f_a_c_() {
        final String pattern = "[[a-c]&&[d-f][a-c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_068_a_c_d_f_abc_() {
        final String pattern = "[[a-c][d-f]&&abc]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_069_a_c_d_f_abc_def_() {
        final String pattern = "[[a-c][d-f]&&abc[def]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "e";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("e", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_070_a_c_b_d_c_e_() {
        final String pattern = "[[a-c]&&[b-d]&&[c-e]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_071_a_c_b_d_c_e_() {
        final String pattern = "[[a-c]&&[b-d]&&[c-e]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_072_a_c_b_d_c_e_u_z_() {
        final String pattern = "[[a-c]&&[b-d][c-e]&&[u-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_073_abc_bcd_() {
        final String pattern = "[abc[^bcd]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_074_abc_bcd_() {
        final String pattern = "[abc[^bcd]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_075_a_c_a_d_a_eghi_() {
        final String pattern = "[a-c&&a-d&&a-eghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_076_a_c_a_d_a_eghi_() {
        final String pattern = "[a-c&&a-d&&a-eghi]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "g";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_077_a_b_b_a_() {
        final String pattern = "[[a[b]]&&[b[a]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_078_a_b_c_a_d_() {
        final String pattern = "[[a]&&[b][c][a]&&[^d]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_079_a_b_c_a_d_() {
        final String pattern = "[[a]&&[b][c][a]&&[^d]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_080_a_d_c_f_() {
        final String pattern = "[[[a-d]&&[c-f]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_081_a_d_c_f_() {
        final String pattern = "[[[a-d]&&[c-f]]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_082_a_d_c_f_c_() {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_083_a_d_c_f_c_c_() {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_084_a_d_c_f_c_c_c_() {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c&&c]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_085_a_d_c_f_c_c_cde_() {
        final String pattern = "[[[a-d]&&[c-f]]&&[c]&&c&&[cde]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_086_z_abc_bcd_() {
        final String pattern = "[z[abc&&bcd]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_087_z_abc_bcd_u_z_() {
        final String pattern = "[z[abc&&bcd]&&[u-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_088_x_abc_bcd_z_u_z_() {
        final String pattern = "[x[abc&&bcd[z]]&&[u-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_089_x_wz_abc_bcd_z_u_z_() {
        final String pattern = "[x[[wz]abc&&bcd[z]]&&[u-z]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_090_abc_def_abc_() {
        final String pattern = "[[abc]&&[def]abc]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_091_abc_def_xyz_abc_() {
        final String pattern = "[[abc]&&[def]xyz[abc]]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_092_pL() {
        final String pattern = "\\pL";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_093_pL() {
        final String pattern = "\\pL";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "7";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_094_p_L_() {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_095_p_LC_() {
        final String pattern = "\\p{LC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_096_p_LC_() {
        final String pattern = "\\p{LC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "A";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("A", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_097_p_IsL_() {
        final String pattern = "\\p{IsL}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_098_p_IsLC_() {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_099_p_IsLC_() {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "A";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("A", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_100_p_IsLC_() {
        final String pattern = "\\p{IsLC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "9";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_101_P_IsLC_() {
        final String pattern = "\\P{IsLC}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "9";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("9", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_102_p_Pi_() {
        final String pattern = "\\p{Pi}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00ab");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00ab"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_103_P_Pi_() {
        final String pattern = "\\P{Pi}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00ac");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00ac"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_104_p_IsPf_() {
        final String pattern = "\\p{IsPf}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_105_p_P_() {
        final String pattern = "\\p{P}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_106_p_P_() {
        final String pattern = "\\p{P}+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bb");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bb"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_107_P_IsPf_() {
        final String pattern = "\\P{IsPf}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_108_P_IsP_() {
        final String pattern = "\\P{IsP}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_109_p_L1_() {
        final String pattern = "\\p{L1}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_110_p_L1_() {
        final String pattern = "\\p{L1}+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_111_p_L1_() {
        final String pattern = "\\p{L1}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u02bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_112_p_ASCII_() {
        final String pattern = "\\p{ASCII}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_113_p_IsASCII_() {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_114_p_IsASCII_() {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_115_pLbc() {
        final String pattern = "\\pLbc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_116_a_r_p_InGreek_c() {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_117_a_p_InGreek_() {
        final String pattern = "a\\p{InGreek}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_118_a_P_InGreek_() {
        final String pattern = "a\\P{InGreek}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_119_a_P_InGreek_() {
        final String pattern = "a\\P{InGreek}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_120_a_InGreek_() {
        try {
            final String pattern = "a{^InGreek}";
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_121_a_p_InGreek_() {
        try {
            final String pattern = "a\\p{^InGreek}";
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_122_a_P_InGreek_() {
        try {
            final String pattern = "a\\P{^InGreek}";
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_123_a_p_InGreek_() {
        final String pattern = "a\\p{InGreek}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_124_a_p_InGreek_c() {
        final String pattern = "a[\\p{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_125_a_P_InGreek_c() {
        final String pattern = "a[\\P{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_126_a_P_InGreek_c() {
        final String pattern = "a[\\P{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_127_a_InGreek_c() {
        final String pattern = "a[{^InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "anc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("anc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_128_a_InGreek_c() {
        final String pattern = "a[{^InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "azc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_129_a_p_InGreek_c() {
        try {
            final String pattern = "a[\\p{^InGreek}]c";
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_130_a_P_InGreek_c() {
        try {
            final String pattern = "a[\\P{^InGreek}]c";
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_131_a_p_InGreek_() {
        final String pattern = "a[\\p{InGreek}]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_132_a_r_p_InGreek_c() {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_133_a_p_InGreek_r_c() {
        final String pattern = "a[\\p{InGreek}r]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_134_a_r_p_InGreek_c() {
        final String pattern = "a[r\\p{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_135_a_p_InGreek_c() {
        final String pattern = "a[^\\p{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_136_a_P_InGreek_c() {
        final String pattern = "a[^\\P{InGreek}]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_137_a_p_InGreek_u0370_c() {
        final String pattern = doSpecialChars("a[\\p{InGreek}&&[^\\u0370]]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_138_a_c_() {
        final String pattern = "a.c.+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a#c%&";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a#c%&", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_139_ab_() {
        final String pattern = "ab.";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("ab\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_140_s_ab_() {
        final String pattern = "(?s)ab.";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("ab\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("ab\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_141_a_p_L_P_InGreek_c() {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u6000c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u6000c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_142_a_p_L_P_InGreek_c() {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "arc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("arc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_143_a_p_L_P_InGreek_c() {
        final String pattern = "a[\\p{L}&&[\\P{InGreek}]]c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_144_a_p_InGreek_c() {
        final String pattern = "a\\p{InGreek}c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_145_a_p_Sc_() {
        final String pattern = "a\\p{Sc}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a$";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a$", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_146_ab_wc() {
        final String pattern = "ab\\wc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_147_W_w_W() {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "#r#";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#r#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_148_W_w_W() {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "rrrr#ggg";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_149_abc_w_() {
        final String pattern = "abc[\\w]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcd", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_150_abc_sdef_() {
        final String pattern = "abc[\\sdef]*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abc  def";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc  def", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_151_abc_sy_z_() {
        final String pattern = "abc[\\sy-z]*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abc y z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc y z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_152_abc_a_d_sm_p_() {
        final String pattern = "abc[a-d\\sm-p]*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcaa mn  p";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcaa mn  p", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_153_ab_sc() {
        final String pattern = "ab\\sc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ab c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_154_s_s_s() {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blah  err";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_155_S_S_s() {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blah  err";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ah ", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_156_ab_dc() {
        final String pattern = "ab\\dc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ab9c";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab9c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_157_d_d_d() {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blah45";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_158_abc() {
        final String pattern = "^abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcdef";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_159_abc() {
        final String pattern = "^abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "bcdabc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_160_a_b() {
        final String pattern = "a?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_161_a_b() {
        final String pattern = "a?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_162_a_b() {
        final String pattern = "a?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_163_b() {
        final String pattern = ".?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_164_a_b() {
        final String pattern = "a??b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_165_a_b() {
        final String pattern = "a??b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_166_a_b() {
        final String pattern = "a??b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_167_b() {
        final String pattern = ".??b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_168_a_b() {
        final String pattern = "a?+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_169_a_b() {
        final String pattern = "a?+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_170_a_b() {
        final String pattern = "a?+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_171_b() {
        final String pattern = ".?+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_172_a_b() {
        final String pattern = "a+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_173_a_b() {
        final String pattern = "a+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_174_a_b() {
        final String pattern = "a+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_175_b() {
        final String pattern = ".+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_176_a_b() {
        final String pattern = "a+?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_177_a_b() {
        final String pattern = "a+?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_178_a_b() {
        final String pattern = "a+?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_179_b() {
        final String pattern = ".+?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_180_a_b() {
        final String pattern = "a++b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_181_a_b() {
        final String pattern = "a++b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_182_a_b() {
        final String pattern = "a++b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_183_b() {
        final String pattern = ".++b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_184_a_2_3_() {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_185_a_2_3_() {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_186_a_2_3_() {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_187_a_2_3_() {
        final String pattern = "a{2,3}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_188_a_3_() {
        final String pattern = "a{3,}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzaaaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_189_a_3_() {
        final String pattern = "a{3,}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_190_a_2_3_() {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_191_a_2_3_() {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_192_a_2_3_() {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_193_a_2_3_() {
        final String pattern = "a{2,3}?";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaa";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aa", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_194_abc_d_() {
        final String pattern = "abc(?=d)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzabcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_195_abc_d_() {
        final String pattern = "abc(?=d)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzabced";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_196_abc_d_() {
        final String pattern = "abc(?!d)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzabcd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_197_abc_d_() {
        final String pattern = "abc(?!d)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzabced";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_198_w_a_() {
        final String pattern = "\\w(?<=a)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "###abc###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_199_w_a_() {
        final String pattern = "\\w(?<=a)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "###ert###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_200_a_w() {
        final String pattern = "(?<!a)\\w";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "###abc###";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_201_a_c() {
        final String pattern = "(?<!a)c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "bc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("c", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_202_a_c() {
        final String pattern = "(?<!a)c";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ac";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_203_a_b_() {
        final String pattern = "(a+b)+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test_204_a_b_() {
        final String pattern = "(a|b)+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ccccd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_205_ab_() {
        final String pattern = "(ab)+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test_206_ab_() {
        final String pattern = "(ab)+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "accccd";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_207_ab_() {
        final String pattern = "(ab)*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "ababab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("ababab", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
    }

    @Test
    public void test_208_ab_cd_() {
        final String pattern = "(ab)(cd*)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzabczzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abc", matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals("ab", matcher.group(1));
        assertEquals("c", matcher.group(2));
    }

    @Test
    public void test_209_abc_d_abc() {
        final String pattern = "abc(d)*abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "abcdddddabc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abcdddddabc", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("d", matcher.group(1));
    }

    @Test
    public void test_210_() {
        final String pattern = "\\*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "*";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("*", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_211_() {
        final String pattern = "\\\\";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_212_() {
        final String pattern = "\\\\";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "\\\\\\\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_213_a_bc_1() {
        final String pattern = "(a*)bc\\1";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzaabcaazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aabcaa", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("aa", matcher.group(1));
    }

    @Test
    public void test_214_a_bc_1() {
        final String pattern = "(a*)bc\\1";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "zzzaabcazzz";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("abca", matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals("a", matcher.group(1));
    }

    @Test
    public void test_215_gt_dde_yu_1_3_vv_() {
        final String pattern = "(gt*)(dde)*(yu)\\1\\3(vv)";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_216_a_b() {
        final String pattern = "a*b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_217_a_b() {
        final String pattern = "a*b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_218_a_b() {
        final String pattern = "a*b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_219_b() {
        final String pattern = ".*b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_220_a_b() {
        final String pattern = "a*?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_221_a_b() {
        final String pattern = "a*?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_222_a_b() {
        final String pattern = "a*?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_223_b() {
        final String pattern = ".*?b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_224_a_b() {
        final String pattern = "a*+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("aaaab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_225_a_b() {
        final String pattern = "a*+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "b";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("b", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_226_a_b() {
        final String pattern = "a*+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaccc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_227_b() {
        final String pattern = ".*+b";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "aaaab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_228_i_foobar() {
        final String pattern = "(?i)foobar";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("fOobAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_229_f_i_oobar() {
        final String pattern = "f(?i)oobar";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("fOobAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_230_foo_i_bar() {
        final String pattern = "foo(?i)bar";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "fOobAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_231_i_foo_bar_() {
        final String pattern = "(?i)foo[bar]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "foObAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("foObAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_232_i_foo_a_r_() {
        final String pattern = "(?i)foo[a-r]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "foObAr";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("foObAr", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_233_Q_Eabc() {
        final String pattern = "\\Q***\\Eabc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_234_bl_Q_Eabc() {
        final String pattern = "bl\\Q***\\Eabc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "bl***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("bl***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_235_Q_abc() {
        final String pattern = "\\Q***abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_236_blah_Q_Eabc() {
        final String pattern = "blah\\Q***\\Eabc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blah***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blah***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_237_Q_abc() {
        final String pattern = "\\Q***abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_238_Q_ab() {
        final String pattern = "\\Q*ab";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "*ab";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("*ab", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_239_blah_Q_abc() {
        final String pattern = "blah\\Q***abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blah***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blah***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_240_bla_Q_abc() {
        final String pattern = "bla\\Q***abc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "bla***abc";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("bla***abc", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_241_ab_Qdef_E_() {
        final String pattern = "[ab\\Qdef\\E]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("d", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_242_ab_Q_E_() {
        final String pattern = "[ab\\Q[\\E]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "[";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("[", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_243_Q_E_() {
        final String pattern = "[\\Q]\\E]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "]";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("]", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_244_Q_E_() {
        final String pattern = "[\\Q\\\\E]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "\\";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("\\", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_245_Q_E_() {
        final String pattern = "[\\Q(\\E]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "(";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("(", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_246_n_() {
        final String pattern = doSpecialChars("[\\n-#]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "!";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("!", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_247_n_() {
        final String pattern = doSpecialChars("[\\n-#]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "-";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_248_w_() {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "!";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_249_w_() {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_250_w_() {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "-";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("-", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_251_w_() {
        final String pattern = "[\\w-#]";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "#";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_252_043_() {
        final String pattern = "[\\043]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_253_042_044_() {
        final String pattern = "[\\042-\\044]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_254_u1234_u1236_() {
        final String pattern = doSpecialChars("[\\u1234-\\u1236]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("blahblah\\u1235blech");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1235"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_255_043_() {
        final String pattern = "[^\\043]*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "blahblah#blech";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("blahblah", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_256_f_() {
        final String pattern = "(|f)?+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "foo";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("", matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_257_ud800_udc61_ud800_udc61() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61)?\\ud800\\udc61");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_258_ud800_udc61_ud800_ud800_udc61_ud800() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800)?\\ud800\\udc61\\ud800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_259_ud800_udc61_ud800_udc61_ud800_udc62_ud800_udc62_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800\\udc61(\\ud800\\udc62\\ud800\\udc62)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc62\\ud800\\udc62\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc62\\ud800\\udc62\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800\\udc62"), matcher.group(2));
    }

    @Test
    public void test_260_ud800_udc61_ud800_udc61_ud800_ud800_udc62_ud800_udc62_ud800_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_261_ud800_udc61_ud800_udc62_ud800_udc62_() {
        final String pattern = doSpecialChars("((\\ud800\\udc61|\\ud800\\udc62)?\\ud800\\udc62)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(1));
    }

    @Test
    public void test_262_ud800_ud800_udc62_ud800_udc62_() {
        final String pattern = doSpecialChars("((\\ud800|\\ud800\\udc62)?\\ud800\\udc62)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(1));
    }

    @Test
    public void test_263_ud800_udc61_ud800_udc61_ud800_udc61_ud800_udc61_ud800_udc61_ud800_udc61() {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61)?\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_264_ud800_udc61_ud800_udc61_ud800_ud800_udc61_ud800_udc61_ud800_udc61_ud800_ud800_udc61() {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61)?\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_265_ud800_udc61_ud800_ud800_udc62_ud800_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_266_ud800_udc61_ud800_udc62_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc62)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc61"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62"), matcher.group(2));
    }

    @Test
    public void test_267_ud800_udc61_ud800_ud800_udc62_ud800_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62\\ud800)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\ud800\\udc61\\ud800"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc62\\ud800"), matcher.group(2));
    }

    @Test
    public void test_268_ud800_udc61_ud800_udc62_ud800_udc63_ud800_udc61_ud800_udc62_ud800_udc63() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc62(\\ud800\\udc63)?)?)?\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc62\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_269_ud800_udc61_ud800_ud800_udc62_ud800_udc63_ud800_udc61_ud800_ud800_udc62_ud800_udc63() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61\\ud800(\\ud800\\udc62(\\ud800\\udc63)?)?)?\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\ud800\\udc62\\ud800\\udc63"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_270_ud800_udc61_ud800_udc02_ud800_udc63_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800\\udc02(\\ud800\\udc63))).*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_271_ud800_udc61_ud800_ud800_udc63_() {
        final String pattern = doSpecialChars("^(\\ud800\\udc61(\\ud800(\\ud800\\udc63))).*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_272_a_xyz() {
        final String pattern = "(.)([^a])xyz";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00xyz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\ud800\\udc00xyz"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud801"), matcher.group(1));
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group(2));
    }

    @Test
    public void test_273_a_z_() {
        final String pattern = "[^a-z]..";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00xyz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\ud800\\udc00x"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_274_() {
        final String pattern = ".$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_275_() {
        final String pattern = ".$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_276_() {
        final String pattern = ".$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud800\\udc00\\udcff");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udcff"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_277_x_uffff_y_uffff_() {
        final String pattern = doSpecialChars("[^x-\\uffff][^y-\\uffff]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00pqr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00p"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_278_x_uffff_() {
        final String pattern = doSpecialChars("[^x-\\uffff]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00pqrx");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00pqr"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_279_ud800_udc61bc_x_bl_ud800_udc61h() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)bl\\ud800\\udc61h");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_280_ud800_udc61bc_x_bl_ud800_udc61h() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_281_ud800_udc61bc_x_bl_ud800_udc61h_blech() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h  blech");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61hblech");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61hblech"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_282_ud800_udc61bc_x_bl_ud800_udc61h_ignore_comment() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(?x)  bl\\ud800\\udc61h # ignore comment");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcbl\\ud800\\udc61h"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_283_ud800_udc61_ud800_udc62() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc62");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_284_ud800_udc61_ud800_udc62_ud800() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc62|\\ud800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_285_ud800_udc61_ud800() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc62");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_286_ud800_udc62_ud800() {
        final String pattern = doSpecialChars("\\ud800\\udc62|\\ud800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_287_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_288_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_289_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud802\\udc02|\\ud803\\udc03\\ud804\\udc04");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_290_ud800_udc61_ud800_udc61d() {
        final String pattern = doSpecialChars("\\ud800\\udc61|\\ud800\\udc61d");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_291_z_ud800_udc61_ud800_udc61c_ud802_udc02() {
        final String pattern = doSpecialChars("z(\\ud800\\udc61|\\ud800\\udc61c)\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\ud800\\udc61c\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("z\\ud800\\udc61c\\ud802\\udc02"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61c"), matcher.group(1));
    }

    @Test
    public void test_292_z_ud800_udc61_ud800_udc61c_udc61c_ud802_udc02() {
        final String pattern = doSpecialChars("z(\\ud800\\udc61|\\ud800\\udc61c|\\udc61c)\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\udc61c\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("z\\udc61c\\ud802\\udc02"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\udc61c"), matcher.group(1));
    }

    @Test
    public void test_293_ud800_udc61_ud802_udc02c_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_294_ud800_udc61_ud802_udc02c_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_295_ud800_udc61_ud802_udc02c_ud800_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02c\\ud800]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_296_ud800_udc61bc_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61bc]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("d\\ud800\\udc62fg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_297_ud800_udc61bc_ud804_udc04ef_ud807_udc07hi_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61bc]+[\\ud804\\udc04ef]+[\\ud807\\udc07hi]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud804\\udc04\\ud804\\udc04\\ud807\\udc07\\ud807\\udc07zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud804\\udc04\\ud804\\udc04\\ud807\\udc07\\ud807\\udc07"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_298_ud801_udc01_ud807_udc07_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud807\\udc07]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud8ff\\udcff\\ud8ff\\udcff\\ud8ff\\udcff\\ud807\\udc07\\ud807\\udc07\\ud807\\udc07");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud807\\udc07\\ud807\\udc07\\ud807\\udc07"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_299_ud801_udc01_ud807_udc07_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud807\\udc07]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_300_ud800_udc61_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61-]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("z\\ud800\\udc61-9z");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61-"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_301_ud800_udc61_ud802_udc02c_() {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02c]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_302_ud800_udc61_ud802_udc02_ud803_udc03_() {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02\\ud802\\udc02\\ud802\\udc02\\ud803\\udc03\\ud803\\udc03\\ud803\\udc03\\ud804\\udc04efg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04efg"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_303_ud800_udc61_ud802_udc02_ud803_udc03_ud800_() {
        final String pattern = doSpecialChars("[^\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud800]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02\\ud802\\udc02\\ud802\\udc02\\ud803\\udc03\\ud803\\udc03\\ud803\\udc03\\ud804\\udc04efg");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04efg"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_304_ud801_udc01_ud802_udc02_ud803_udc03_ud802_udc02_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03^\\ud802\\udc02]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_305_ud801_udc01_ud802_udc02_ud803_udc03_ud802_udc02_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03^\\ud802\\udc02]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_306_ud801_udc01_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_307_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_308_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_309_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80c\\udc0c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80c\\udc0c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_310_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_311_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_312_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud804\\udc04[0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud816\\udc16");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_313_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud804\\udc04][0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_314_ud801_udc01_ud804_udc04_0_9_ud80b_udc0b_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud804\\udc04][0-9][\\ud80b\\udc0b-\\ud80d\\udc0d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_315_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud807_udc07_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_316_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud807_udc07_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_317_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud807_udc07_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud808\\udc08");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud808\\udc08"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_318_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud807_udc07_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_319_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud807_udc07_ud809_udc09_ud80d_udc0d_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03[\\ud804\\udc04-\\ud806\\udc06[\\ud807\\udc07-\\ud809\\udc09]]\\ud80d\\udc0d]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_320_ud801_udc01_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_321_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_322_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud808\\udc08");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud808\\udc08"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_323_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud816\\udc16");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_324_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_325_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_326_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_327_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_328_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_329_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_330_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud804\\udc04-\\ud806\\udc06]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_331_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_332_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a&&\\ud801\\udc01-\\ud803\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_333_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_ud801_udc01_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&\\ud80d\\udc0d-\\ud81a\\udc1a&&\\ud801\\udc01-\\ud81a\\udc1a]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_334_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_335_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud80d\\udc0d");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud80d\\udc0d"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_336_ud801_udc01_ud80d_udc0d_ud80d_udc0d_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[\\ud80d\\udc0d-\\ud81a\\udc1a]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud81a\\udc1a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_337_ud801_udc01_ud80d_udc0d_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_338_ud801_udc01_ud80d_udc0d_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud80d\\udc0d]&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_339_ud801_udc01_ud80d_udc0d_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_340_ud801_udc01_ud80d_udc0d_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud80d\\udc0d&&[^\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_341_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03\\ud804\\udc04-\\ud806\\udc06&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_342_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud804_udc04_ud806_udc06_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03\\ud804\\udc04-\\ud806\\udc06&&[\\ud804\\udc04-\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_343_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&\\ud804\\udc04-\\ud806\\udc06\\ud801\\udc01-\\ud803\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_344_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud801_udc01_ud803_udc03_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud804\\udc04-\\ud806\\udc06][\\ud801\\udc01-\\ud803\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_345_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud801_udc01_ud802_udc02_ud803_udc03_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03][\\ud804\\udc04-\\ud806\\udc06]&&\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_346_ud801_udc01_ud803_udc03_ud804_udc04_ud806_udc06_ud801_udc01_ud802_udc02_ud803_udc03_ud804_udc04_ud805_udc05_ud806_udc06_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03][\\ud804\\udc04-\\ud806\\udc06]&&\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[\\ud804\\udc04\\ud805\\udc05\\ud806\\udc06]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud805\\udc05");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud805\\udc05"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_347_ud801_udc01_ud803_udc03_ud802_udc02_ud804_udc04_ud803_udc03_ud805_udc05_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04]&&[\\ud803\\udc03-\\ud805\\udc05]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_348_ud801_udc01_ud803_udc03_ud802_udc02_ud804_udc04_ud803_udc03_ud805_udc05_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04]&&[\\ud803\\udc03-\\ud805\\udc05]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_349_ud801_udc01_ud803_udc03_ud802_udc02_ud804_udc04_ud803_udc03_ud805_udc05_ud815_udc15_ud81a_udc1a_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01-\\ud803\\udc03]&&[\\ud802\\udc02-\\ud804\\udc04][\\ud803\\udc03-\\ud805\\udc05]&&[\\ud815\\udc15-\\ud81a\\udc1a]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_350_ud801_udc01_ud802_udc02_ud803_udc03_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03[^\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_351_ud800_udc61_ud802_udc02_ud803_udc03_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("[\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03[^\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_352_ud801_udc01_ud803_udc03_ud801_udc01_ud804_udc04_ud801_udc01_ud805_udc05_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud801\\udc01-\\ud804\\udc04&&\\ud801\\udc01-\\ud805\\udc05\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_353_ud801_udc01_ud803_udc03_ud801_udc01_ud804_udc04_ud801_udc01_ud805_udc05_ud807_udc07_ud808_udc08_ud809_udc09_() {
        final String pattern = doSpecialChars("[\\ud801\\udc01-\\ud803\\udc03&&\\ud801\\udc01-\\ud804\\udc04&&\\ud801\\udc01-\\ud805\\udc05\\ud807\\udc07\\ud808\\udc08\\ud809\\udc09]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud807\\udc07");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_354_ud801_udc01_ud802_udc02_ud802_udc02_ud801_udc01_() {
        final String pattern = doSpecialChars("[[\\ud801\\udc01[\\ud802\\udc02]]&&[\\ud802\\udc02[\\ud801\\udc01]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc01"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_355_ud800_udc61_b_c_ud800_udc61_d_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][c][\\ud800\\udc61]&&[^d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_356_ud800_udc61_ud802_udc02_ud800_ud800_udc61_ud804_udc04_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[\\ud802\\udc02][\\ud800][\\ud800\\udc61]&&[^\\ud804\\udc04]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_357_ud800_udc61_b_ud800_ud800_udc61_ud804_udc04_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][\\ud800][\\ud800\\udc61]&&[^\\ud804\\udc04]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_358_ud800_udc61_b_c_ud800_udc61_d_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61]&&[b][c][\\ud800\\udc61]&&[^d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "d";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_359_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc01");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_360_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_361_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_ud800_udc03_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_362_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_ud800_udc03_ud800_udc03_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_363_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_ud800_udc03_ud800_udc03_ud800_udc03_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03&&\\ud800\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_364_ud800_udc01_ud800_udc04_ud800_udc03_ud800_udc06_ud800_udc03_ud800_udc03_ud800_udc03_ud800_udc04_ud800_udc05_() {
        final String pattern = doSpecialChars("[[[\\ud800\\udc01-\\ud800\\udc04]&&[\\ud800\\udc03-\\ud800\\udc06]]&&[\\ud800\\udc03]&&\\ud800\\udc03&&[\\ud800\\udc03\\ud800\\udc04\\ud800\\udc05]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_365_z_ud800_udc61b_ud800_udc03_b_ud800_udc03_ud800_udc04_() {
        final String pattern = doSpecialChars("[z[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_366_z_ud800_udc61b_ud800_udc03_b_ud800_udc03_ud800_udc04_u_z_() {
        final String pattern = doSpecialChars("[z[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04]&&[u-z]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_367_x_ud800_udc61b_ud800_udc03_b_ud800_udc03_ud800_udc04_z_u_z_() {
        final String pattern = doSpecialChars("[x[\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04[z]]&&[u-z]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_368_x_wz_ud800_udc61b_ud800_udc03_b_ud800_udc03_ud800_udc04_z_u_z_() {
        final String pattern = doSpecialChars("[x[[wz]\\ud800\\udc61b\\ud800\\udc03&&b\\ud800\\udc03\\ud800\\udc04[z]]&&[u-z]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "z";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("z", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_369_ud800_udc61b_ud800_udc03_ud800_udc04_ud800_udc05f_ud800_udc61b_ud800_udc03_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61b\\ud800\\udc03]&&[\\ud800\\udc04\\ud800\\udc05f]\\ud800\\udc61b\\ud800\\udc03]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_370_ud800_udc61b_ud800_udc03_ud800_udc04_ud800_udc05f_xyz_ud800_udc61b_ud800_udc03_() {
        final String pattern = doSpecialChars("[[\\ud800\\udc61b\\ud800\\udc03]&&[\\ud800\\udc04\\ud800\\udc05f]xyz[\\ud800\\udc61b\\ud800\\udc03]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_371_pL() {
        final String pattern = "\\pL";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_372_p_IsASCII_() {
        final String pattern = "\\p{IsASCII}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_373_pLbc() {
        final String pattern = "\\pLbc";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc00bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc00bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_374_ud800_udc61_r_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_375_ud800_udc61_p_InGreek_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_376_ud800_udc61_P_InGreek_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\P{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_377_ud800_udc61_P_InGreek_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\P{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_378_ud800_udc61_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_379_ud800_udc61_p_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61\\p{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_380_ud800_udc61_P_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61\\P{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_381_ud800_udc61_p_InGreek_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_382_ud800_udc61_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_383_ud800_udc61_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\P{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_384_ud800_udc61_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\P{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_385_ud800_udc61_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[{^InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61nc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61nc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_386_ud800_udc61_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[{^InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61zc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_387_ud800_udc61_p_InGreek_c() {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61[\\p{^InGreek}]c");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_388_ud800_udc61_P_InGreek_c() {
        try {
            final String pattern = doSpecialChars("\\ud800\\udc61[\\P{^InGreek}]c");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_389_ud800_udc61_p_InGreek_() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_390_ud800_udc61_r_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_391_ud800_udc61_p_InGreek_r_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}r]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_392_ud800_udc61_r_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[r\\p{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_393_ud800_udc61_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[^\\p{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_394_ud800_udc61_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[^\\P{InGreek}]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_395_ud800_udc61_p_InGreek_u0370_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{InGreek}&&[^\\u0370]]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_396_ud800_udc61_c_() {
        final String pattern = doSpecialChars("\\ud800\\udc61.c.+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61#c%&");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61#c%&"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_397_ud800_udc61b_() {
        final String pattern = doSpecialChars("\\ud800\\udc61b.");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_398_s_ud800_udc61b_() {
        final String pattern = doSpecialChars("(?s)\\ud800\\udc61b.");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_399_ud800_udc61_p_L_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u6000c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u6000c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_400_ud800_udc61_p_L_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61rc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61rc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_401_ud800_udc61_p_L_P_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61[\\p{L}&&[\\P{InGreek}]]c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_402_ud800_udc61_p_InGreek_c() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{InGreek}c");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\u0370c");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\u0370c"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_403_ud800_udc61_p_Sc_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\p{Sc}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61$");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61$"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_404_p_L_() {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf1e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf1e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_405_a_p_L_z_() {
        final String pattern = "^a\\p{L}z$";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\ud800\\udf1ez");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud800\\udf1ez"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_406_ud800_udf00_p_L_2_3_P_L_supp_ud900_udc00_P_InDeseret_() {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\P{InDeseret}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1esupp->\\ud900\\udc00<-\\ud901\\udf00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1esupp->\\ud900\\udc00<-\\ud901\\udf00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_407_ud800_udf00_p_L_2_3_P_L_supp_ud900_udc00_P_InDeseret_() {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\P{InDeseret}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud901\\udf00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud901\\udf00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_408_ud800_udf00_p_L_2_3_P_L_supp_ud900_udc00_p_InDeseret_() {
        final String pattern = doSpecialChars("\\ud800\\udf00\\p{L}{2,3}\\P{L}*supp->\\ud900\\udc00<-\\p{InDeseret}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud801\\udc00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udf00\\ud800\\udf1e\\ud800\\udf1e\\ud901\\udf00supp->\\ud900\\udc00<-\\ud801\\udc00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_409_ud800_udc61b_wc() {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\wc");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_410_ud800_udc61bc_w_() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\w]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcd");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bcd"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_411_ud800_udc61bc_sdef_() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\sdef]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc  def");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc  def"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_412_ud800_udc61bc_sy_z_() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc[\\sy-z]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc y z");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc y z"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_413_ud800_udc01bc_ud800_udc01_ud800_udc04_sm_p_() {
        final String pattern = doSpecialChars("\\ud800\\udc01bc[\\ud800\\udc01-\\ud800\\udc04\\sm-p]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc01bc\\ud800\\udc01\\ud800\\udc01 mn  p");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc01bc\\ud800\\udc01\\ud800\\udc01 mn  p"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_414_ud800_udc61b_s_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\s\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b \\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b \\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_415_s_s_s() {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h  err");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_416_S_S_s() {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h  err");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61h "), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_417_ud800_udc61b_d_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud800\\udc61b\\d\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61b9\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61b9\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_418_d_d_d() {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("bl\\ud800\\udc61h45");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_419_ud800_udc61bc() {
        final String pattern = doSpecialChars("^\\ud800\\udc61bc");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bcdef");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_420_ud800_udc61bc() {
        final String pattern = doSpecialChars("^\\ud800\\udc61bc");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("bcd\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_421_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_422_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_423_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_424_ud800_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_425_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc03\\ud800\\udc03\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_426_ud800_udc02() {
        final String pattern = doSpecialChars(".?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_427_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_428_ud800_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\ud800\\ud8001\\ud800\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_429_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_430_ud800_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_431_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_432_ud800_udc02() {
        final String pattern = doSpecialChars(".??\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_433_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_434_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_435_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61?+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_436_ud800_udc02() {
        final String pattern = doSpecialChars(".?+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_437_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_438_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_439_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_440_ud800_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_441_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_442_ud800_udc02() {
        final String pattern = doSpecialChars(".+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_443_ud800_udc02() {
        final String pattern = doSpecialChars(".+\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_444_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_445_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\udc61\\udc61\\udc61\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_446_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_447_ud800_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_448_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_449_ud800_udc02() {
        final String pattern = doSpecialChars(".+?\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_450_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_451_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_452_ud800_udc61_ud800_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61++\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_453_ud800_udc02() {
        final String pattern = doSpecialChars(".++\\ud800\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_454_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_455_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_456_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_457_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_458_ud800_udc61_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{3,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_459_ud800_udc61_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{3,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_460_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_461_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_462_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_463_ud800_udc61_2_3_() {
        final String pattern = doSpecialChars("\\ud800\\udc61{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_464_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_465_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03e\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_466_ud800_udc61_ud802_udc02_ud803_udc03_udcff_ud804_udc04_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\udcff\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\udcff\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_467_ud800_udc61_ud802_udc02_ud803_udc03_udcff_ud804_udc04_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?=\\udcff\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud8ff\\udcff\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_468_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_469_a_ud802_udc02_ud803_udc03_ud804_udc04_() {
        final String pattern = doSpecialChars("a\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zza\\ud802\\udc02\\ud803\\udc03\\udc04\\ud804\\udc04");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_470_ud800_udc61_ud802_udc02_ud803_udc03_ud804_udc04_ud8ff_() {
        final String pattern = doSpecialChars("\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04\\ud8ff)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zz\\ud800\\udc61\\ud802\\udc02\\ud803\\udc03\\ud804\\udc04\\ud8ffX");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_471_a_ud802_udc02_ud803_udc03_ud804_udc04_ud8ff_() {
        final String pattern = doSpecialChars("a\\ud802\\udc02\\ud803\\udc03(?!\\ud804\\udc04\\ud8ff)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zza\\ud802\\udc02\\ud803\\udc03e\\ud804\\udc04\\ud8ff\\udcff");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_472_ud801_udc01_ud802_udc02_ud803_udc03() {
        final String pattern = doSpecialChars("(?<=\\ud801\\udc01\\ud802\\udc02)\\ud803\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud802\\udc02\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_473_ud801_udc01_ud802_udc02_ud803_udc03() {
        final String pattern = doSpecialChars("(?<!\\ud801\\udc01)\\ud802\\udc02\\ud803\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\ud800\\udc00\\ud802\\udc02\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02\\ud803\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_474_ud801_udc01_ud802_udc02_ud803_udc03_() {
        final String pattern = doSpecialChars("(?<![\\ud801\\udc01\\ud802\\udc02])\\ud803\\udc03.");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud803\\udc03x\\ud800\\udc00\\ud803\\udc03y");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud803\\udc03y"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_475_ud801_udc01_ud803_udc03() {
        final String pattern = doSpecialChars("(?<!\\ud801\\udc01)\\ud803\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc01\\ud803\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_476_ud800_udc61_ud802_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61+\\ud802)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test_477_ud800_udc61_ud802_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61|\\ud802)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\ud802\\udc61\\ud803\\ud802\\udc61");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_478_ud800_udc61_ud802_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test_479_ud800_udc61_ud802_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61ccccd");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_480_ud800_udc61_ud802_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61\\ud802)*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802\\ud800\\udc61\\ud802"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802"), matcher.group(1));
    }

    @Test
    public void test_481_ud800_udc61b_cd_() {
        final String pattern = doSpecialChars("(\\ud800\\udc61b)(cd*)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61bczzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61b"), matcher.group(1));
        assertEquals("c", matcher.group(2));
    }

    @Test
    public void test_482_ud800_udc61bc_ud804_udc04_ud800_udc61bc() {
        final String pattern = doSpecialChars("\\ud800\\udc61bc(\\ud804\\udc04)*\\ud800\\udc61bc");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61bc\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud800\\udc61bc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61bc\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud804\\udc04\\ud800\\udc61bc"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud804\\udc04"), matcher.group(1));
    }

    @Test
    public void test_483_ud800_udc61_ud802_udc02c_1() {
        final String pattern = doSpecialChars("(\\ud800\\udc61*)\\ud802\\udc02c\\1");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61"), matcher.group(1));
    }

    @Test
    public void test_484_ud800_udc61_ud802_udc02c_1() {
        final String pattern = doSpecialChars("(\\ud800\\udc61*)\\ud802\\udc02c\\1");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("zzz\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61zzz");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud802\\udc02c\\ud800\\udc61"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\ud800\\udc61"), matcher.group(1));
    }

    @Test
    public void test_485_ud800_udc07_ud800_udc14_ud804_udc04_ud804_udc04e_yu_1_3_vv_() {
        final String pattern = doSpecialChars("(\\ud800\\udc07\\ud800\\udc14*)(\\ud804\\udc04\\ud804\\udc04e)*(yu)\\1\\3(vv)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_486_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_487_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_488_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_489_ud802_udc02() {
        final String pattern = doSpecialChars(".*\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_490_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_491_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_492_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*?\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_493_ud802_udc02() {
        final String pattern = doSpecialChars(".*?\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_494_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_495_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_496_ud800_udc61_ud802_udc02() {
        final String pattern = doSpecialChars("\\ud800\\udc61*+\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61ccc");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_497_ud802_udc02() {
        final String pattern = doSpecialChars(".*+\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud800\\udc61\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_498_iu_ud801_udc00_ud801_udc01_ud801_udc02x() {
        final String pattern = doSpecialChars("(?iu)\\ud801\\udc00\\ud801\\udc01\\ud801\\udc02x");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2aX");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2aX"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_499_ud801_udc00_iu_ud801_udc01_ud801_udc02() {
        final String pattern = doSpecialChars("\\ud801\\udc00(?iu)\\ud801\\udc01\\ud801\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc00\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc00\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_500_ud801_udc00_iu_ud801_udc01_ud801_udc02() {
        final String pattern = doSpecialChars("\\ud801\\udc00(?iu)\\ud801\\udc01\\ud801\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_501_iu_ud801_udc00_ud801_udc01_ud801_udc02_() {
        final String pattern = doSpecialChars("(?iu)\\ud801\\udc00[\\ud801\\udc01\\ud801\\udc02]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_502_iu_ud801_udc00_ud801_udc02_() {
        final String pattern = doSpecialChars("(?iu)[\\ud801\\udc00-\\ud801\\udc02]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud801\\udc28\\ud801\\udc29\\ud801\\udc2a"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_503_Q_E_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_504_ud802_udc02l_Q_E_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_505_Q_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_506_ud802_udc02l_ud801_udc01h_Q_E_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h\\Q***\\E\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_507_Q_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_508_Q_ud801_udc01_ud802_udc02() {
        final String pattern = doSpecialChars("\\Q*\\ud801\\udc01\\ud802\\udc02");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("*\\ud801\\udc01\\ud802\\udc02");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("*\\ud801\\udc01\\ud802\\udc02"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_509_ud802_udc02l_ud801_udc01h_Q_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01h***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_510_ud802_udc02l_ud801_udc01_Q_ud801_udc01_ud802_udc02_ud800_udc03() {
        final String pattern = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01\\Q***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\ud802\\udc02l\\ud801\\udc01***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\ud802\\udc02l\\ud801\\udc01***\\ud801\\udc01\\ud802\\udc02\\ud800\\udc03"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_511_uD800_uDFFF_uD801_uDFF1_uDB00_uDC00() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_512_uD800_uDFFF_uD801_uDFF1_uDB00_uDC00() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u1000\\uD801\\uDFF1\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_513_uD800_uDFFF_uD801_uDFF1_uDB00_uDC00() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uFFFF\\uDB00\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_514_uD800_uDFFF_uD801_uDFF1_uDB00_uDC00() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uDB00\\uDC00");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD801\\uDFF1\\uFFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_515_u1000_uFFFF() {
        final String pattern = doSpecialChars("\\u1000.\\uFFFF");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u1000\\uD800\\uDFFF\\uFFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1000\\uD800\\uDFFF\\uFFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_516_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_517_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_518_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[a-\\uD800\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_519_uD800_uDC00_uDBFF_uDFFF_() {
        final String pattern = doSpecialChars("[\\uD800\\uDC00-\\uDBFF\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDBFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_520_uD800_uDC00_uDBFF_uDFFF_() {
        final String pattern = doSpecialChars("[\\uD800\\uDC00-\\uDBFF\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_521_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[\\uD800-\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_522_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[\\uD800-\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_523_foo_uD800_uDFFF_() {
        final String pattern = doSpecialChars("foo[^\\uD800-\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("foo\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("foo\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_524_foo_uD800_uDFFF_() {
        final String pattern = doSpecialChars("foo[^\\uD800-\\uDFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("foo\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_525_ab_uD800_uDFFFcd_at() {
        final String pattern = doSpecialChars("[ab\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800at");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_526_ab_uD800_uDFFFcd_at() {
        final String pattern = doSpecialChars("[ab\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFFat");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFFat"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_527_uD800_uDFFFcd_at() {
        final String pattern = doSpecialChars("[^\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800at");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800at"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_528_uD800_uDFFFcd_at() {
        final String pattern = doSpecialChars("[^\\uD800\\uDFFFcd]at");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFFat");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFFat"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_529_u0000_uD800_uDFFF_uFFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF-\\uFFFF]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_530_u0000_uD800_uDFFF_uFFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800[\\uDFFF-\\uFFFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_531_u0000_uFFFF_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uD800\\uDFFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_532_u0000_uFFFF_uD800_uDFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uD800\\uDFFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_533_u0000_uFFFF_uDFFF_uD800_() {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uDFFF\\uD800]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_534_u0000_uFFFF_uDFFF_uD800_uDC00_() {
        final String pattern = doSpecialChars("[\\u0000-\\uFFFF&&[\\uDFFF\\uD800\\uDC00]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_535_u0000_uDFFF_uD800_uFFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uDFFF&&[\\uD800-\\uFFFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_536_u0000_uDFFF_uD800_uFFFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uDFFF&&[\\uD800-\\uFFFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_537_u0000_uD800_uDFFF_uD800_uDC00_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_538_u0000_uD800_uDFFF_uD800_uDC00_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_539_u0000_uD800_uDFFF_uD800_uDC00_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDC00]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_540_u0000_uD800_uDFFF_uD800_uDBFF_uDC00_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uD800\\uDBFF\\uDC00]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_541_u0000_uD800_uDFFF_uDC00_uD800_uDBFF_() {
        final String pattern = doSpecialChars("[\\u0000-\\uD800\\uDFFF&&[^\\uDC00\\uD800\\uDBFF]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDC00"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_542_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_543_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_544_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_545_a_uDFFF_uD800_() {
        final String pattern = doSpecialChars("a\\uDFFF\\uD800?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("a\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_546_a_uDFFF_uD800_() {
        final String pattern = doSpecialChars("a\\uDFFF\\uD800?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_547_uD800_uDFFF_uDC00_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uDC00?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_548_uD800_uDFFF_uDC00_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF\\uDC00?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_549_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF??");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_550_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "a";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_551_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_552_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_553_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_554_uD800_uDFFF() {
        final String pattern = doSpecialChars("\\uD800*\\uDFFF");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_555_a_uD800_uDFFF_() {
        final String pattern = doSpecialChars("a\\uD800\\uDFFF*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("a\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_556_uDFFF_uD800_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_557_uDFFF_uD800_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_558_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_559_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_560_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_561_uD800_uDFFF_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_562_uDFFF_uD800_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_563_uD800_uDFFF() {
        final String pattern = doSpecialChars("\\uD800+\\uDFFF");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_564_uD800_uDFFF() {
        final String pattern = doSpecialChars("\\uD800+\\uDFFF");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_565_uDFFF_uD800() {
        final String pattern = doSpecialChars("\\uDFFF+\\uD800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_566_uDFFF_uD800() {
        final String pattern = doSpecialChars("\\uDFFF+\\uD800");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_567_uD800_uDFFF_3_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_568_uD800_uDFFF_3_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_569_uDFFF_uD800_3_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_570_uDFFF_uD800_3_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_571_uD800_uDFFF_2_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_572_uD800_uDFFF_2_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_573_uD800_uDFFF_2_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{2,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_574_uDFFF_uD800_2_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{2,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_575_uDFFF_uD800_2_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{2,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_576_uD800_uDFFF_3_4_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_577_uD800_uDFFF_3_4_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_578_uD800_uDFFF_3_4_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,4}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_579_uDFFF_uD800_3_5_() {
        final String pattern = doSpecialChars("\\uDFFF\\uD800{3,5}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800\\uD800\\uD800\\uD800\\uD800");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uDFFF\\uD800\\uD800\\uD800\\uD800\\uD800"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_580_uD800_uDFFF_3_5_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,5}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDFFF\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_581_uD800_uDFFF_3_5_() {
        final String pattern = doSpecialChars("\\uD800\\uDFFF{3,5}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uD800\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uD800\\uDFFF\\uD800\\uDFFF"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_582_uD800_uDFFF_() {
        final String pattern = doSpecialChars("(\\uD800(\\uDFFF))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test_583_uD800_uDC00_uDFFF_() {
        final String pattern = doSpecialChars("(\\uD800(\\uDC00)(\\uDFFF))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDC00\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_584_uD800_uDFFF_() {
        final String pattern = doSpecialChars("((\\uD800)(\\uDFFF))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_585_uD800_uDFFF_uDFFF_() {
        final String pattern = doSpecialChars("(\\uD800(\\uDFFF)\\uDFFF)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test_586_uDFFF_uD800_uDBFF_() {
        final String pattern = doSpecialChars("(\\uDFFF(\\uD800)(\\uDBFF))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_587_uDFFF_uD800_uDC00_() {
        final String pattern = doSpecialChars("(\\uDFFF(\\uD800)(\\uDC00))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_588_uDFFF_uD800_uDC00_uDBFF_() {
        final String pattern = doSpecialChars("(\\uDFFF\\uD800(\\uDC00\\uDBFF))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uDFFF\\uD800\\uDC00\\uDBFF");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(2, matcher.groupCount());
    }

    @Test
    public void test_589_uD800_uDFFF_uDBFF_uDC00_() {
        final String pattern = doSpecialChars("(\\uD800\\uDFFF(\\uDBFF)(\\uDC00))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_590_uD800_uDFFF_uDBFF_uDC00_() {
        final String pattern = doSpecialChars("(\\uD800\\uDFFF(\\uDBFF\\uDC00))");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\uD800\\uDFFF\\uDBFF\\uDC00"), matcher.group(1));
        assertEquals(doSpecialChars("\\uDBFF\\uDC00"), matcher.group(2));
    }

    @Test
    public void test_591_u3042_u3042() {
        final String pattern = doSpecialChars("^(\\u3042)?\\u3042");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_592_u3042_u3042_u3043_u3043_() {
        final String pattern = doSpecialChars("^(\\u3042\\u3042(\\u3043\\u3043)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3043\\u3043\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3043\\u3043\\u3042\\u3042"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3043\\u3043"), matcher.group(2));
    }

    @Test
    public void test_593_u3042_u3043_u3043_() {
        final String pattern = doSpecialChars("((\\u3042|\\u3043)?\\u3043)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3043"), matcher.group(1));
    }

    @Test
    public void test_594_u3042_u3042_u3042_u3042_u3042_u3042() {
        final String pattern = doSpecialChars("(\\u3042\\u3042\\u3042)?\\u3042\\u3042\\u3042");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_595_u3042_u3043_() {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043)?)+$");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3043"), matcher.group(2));
    }

    @Test
    public void test_596_u3042_u3043_u3044_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043(\\u3044)?)?)?\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(3, matcher.groupCount());
    }

    @Test
    public void test_597_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("^(\\u3042(\\u3043(\\u3044))).*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_598_u3042_u3043_u3044_x_u3043la_u3049() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)\\u3043la\\u3049");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3043la\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3043la\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_599_u3042_u3043_u3044_x_bla_u3049() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_600_u3042_u3043_u3044_x_bla_u3049_ble_u3044_u3049() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049  ble\\u3044\\u3049");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049ble\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049ble\\u3044\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_601_u3042_u3043_u3044_x_bla_u3049_ignore_comment() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?x)  bla\\u3049 # ignore comment");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044bla\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_602_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_603_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_604_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042|\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_605_u3042_u3043_u3044_u3045() {
        final String pattern = doSpecialChars("\\u3042|\\u3043|\\u3044\\u3045");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_606_u3042_u3042_u3045() {
        final String pattern = doSpecialChars("\\u3042|\\u3042\\u3045");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_607_u305B_u3042_u3042_u3044_u3043() {
        final String pattern = doSpecialChars("\\u305B(\\u3042|\\u3042\\u3044)\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042\\u3044\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B\\u3042\\u3044\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3044"), matcher.group(1));
    }

    @Test
    public void test_608_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_609_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045\\u3046\\u3047\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_610_u3042_u3043_u3044_u3045_u3046_u3047_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044]+[\\u3045\\u3046\\u3047]+[\\u3048\\u3049\\u304A]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3045\\u3045\\u3048\\u3048\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3045\\u3045\\u3048\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_611_u3042_u3048_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3048]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3048\\u3048\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3048\\u3048\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_612_u3042_u3048_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3048]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "mmm";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_613_u3042_() {
        final String pattern = doSpecialChars("[\\u3042-]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042-9\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042-"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_614_u3042_u4444_() {
        final String pattern = doSpecialChars("[\\u3042-\\\\u4444]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u3042-9\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_615_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[^\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_616_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[^\\u3042\\u3043\\u3044]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3043\\u3043\\u3043\\u3044\\u3044\\u3044\\u3045\\u3046\\u3047\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045\\u3046\\u3047\\u3048"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_617_u3042_u3043_u3044_u3043_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044^\\u3043]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_618_u3042_u3043_u3044_u3043_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044^\\u3043]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "^";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("^", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_619_u3042_u3043_u3044_u3045_u3046_u3047_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_620_u3042_u3043_u3044_u3045_u3046_u3047_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_621_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_622_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3050");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3050"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_623_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "4";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("4", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_624_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_625_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3045[0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3056");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_626_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3045][0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_627_u3042_u3045_0_9_u304e_u3051_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3045][0-9][\\u304e-\\u3051]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_628_u3042_u3044_u3045_u3047_u3048_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_629_u3042_u3044_u3045_u3047_u3048_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_630_u3042_u3044_u3045_u3047_u3048_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_631_u3042_u3044_u3045_u3047_u3048_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_632_u3042_u3044_u3045_u3047_u3048_u304A_m_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044[\\u3045-\\u3047[\\u3048-\\u304A]]m]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "m";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("m", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_633_u3042_u3043_u3044_u3045_u3046_u3047_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_634_u3042_u3043_u3044_u3045_u3046_u3047_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_635_u3042_u3043_u3044_u3045_u3046_u3047_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_636_u3042_u3043_u3044_u3045_u3046_u3047_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "w";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_637_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_638_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_639_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_640_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_641_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_642_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_643_u3042_u3044_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3045-\\u3047]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_644_u3042_u304e_u304e_u305B_() {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_645_u3042_u304e_u304e_u305B_u3042_u3044_() {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B&&\\u3042-\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_646_u3042_u304e_u304e_u305B_u3042_u305B_() {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&\\u304e-\\u305B&&\\u3042-\\u305B]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_647_u3042_u304e_u304e_u305B_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_648_u3042_u304e_u304e_u305B_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u304e");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u304e"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_649_u3042_u304e_u304e_u305B_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[\\u304e-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_650_u3042_u304e_u3042_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_651_u3042_u304e_u3042_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u304e]&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_652_u3042_u304e_u3042_u3044_() {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_653_u3042_u304e_u3042_u3044_() {
        final String pattern = doSpecialChars("[\\u3042-\\u304e&&[^\\u3042-\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3045"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_654_u3042_u3044_u3045_u3047_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044\\u3045-\\u3047&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_655_u3042_u3044_u3045_u3047_u3045_u3047_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044\\u3045-\\u3047&&[\\u3045-\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_656_u3042_u3044_u3045_u3047_u3042_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&\\u3045-\\u3047\\u3042-\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_657_u3042_u3044_u3045_u3047_u3042_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3045-\\u3047][\\u3042-\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_658_u3042_u3044_u3045_u3047_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044][\\u3045-\\u3047]&&\\u3042\\u3043\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_659_u3042_u3044_u3045_u3047_u3042_u3043_u3044_u3045_u3046_u3047_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044][\\u3045-\\u3047]&&\\u3042\\u3043\\u3044[\\u3045\\u3046\\u3047]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3046");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3046"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_660_u3042_u3044_u3043_u3045_u3044_u3046_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045]&&[\\u3044-\\u3046]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_661_u3042_u3044_u3043_u3045_u3044_u3046_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045]&&[\\u3044-\\u3046]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_662_u3042_u3044_u3043_u3045_u3044_u3046_u3056_u305B_() {
        final String pattern = doSpecialChars("[[\\u3042-\\u3044]&&[\\u3043-\\u3045][\\u3044-\\u3046]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_663_u3042_u3043_u3044_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[^\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_664_u3042_u3043_u3044_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("[\\u3042\\u3043\\u3044[^\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_665_u3042_u3044_u3042_u3045_u3042_u3046_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3042-\\u3045&&\\u3042-\\u3046\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_666_u3042_u3044_u3042_u3045_u3042_u3046_u3048_u3049_u304A_() {
        final String pattern = doSpecialChars("[\\u3042-\\u3044&&\\u3042-\\u3045&&\\u3042-\\u3046\\u3048\\u3049\\u304A]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_667_u3042_u3043_u3043_u3042_() {
        final String pattern = doSpecialChars("[[\\u3042[\\u3043]]&&[\\u3043[\\u3042]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_668_u3042_u3043_u3044_u3042_u3045_() {
        final String pattern = doSpecialChars("[[\\u3042]&&[\\u3043][\\u3044][\\u3042]&&[^\\u3045]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_669_u3042_b_c_u3042_d_() {
        final String pattern = doSpecialChars("[[\\u3042]&&[b][c][\\u3042]&&[^d]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_670_u3042_u3043_u3044_u3042_u3045_() {
        final String pattern = doSpecialChars("[[\\u3042]&&[\\u3043][\\u3044][\\u3042]&&[^\\u3045]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_671_u3042_u3045_u3044_u3047_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_672_u3042_u3045_u3044_u3047_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_673_u3042_u3045_u3044_u3047_u3044_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_674_u3042_u3045_u3044_u3047_u3044_u3044_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_675_u3042_u3045_u3044_u3047_u3044_u3044_u3044_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044&&\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_676_u3042_u3045_u3044_u3047_u3044_u3044_u3044_u3045_u3046_() {
        final String pattern = doSpecialChars("[[[\\u3042-\\u3045]&&[\\u3044-\\u3047]]&&[\\u3044]&&\\u3044&&[\\u3044\\u3045\\u3046]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_677_u305B_u3042_u3043_u3044_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("[\\u305B[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_678_u305B_u3042_u3043_u3044_u3043_u3044_u3045_u3056_u305B_() {
        final String pattern = doSpecialChars("[\\u305B[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_679_u3059_u3042_u3043_u3044_u3043_u3044_u3045_u305B_u3056_u305B_() {
        final String pattern = doSpecialChars("[\\u3059[\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045[\\u305B]]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_680_u3059_w_u305B_u3042_u3043_u3044_u3043_u3044_u3045_u305B_u3056_u305B_() {
        final String pattern = doSpecialChars("[\\u3059[[w\\u305B]\\u3042\\u3043\\u3044&&\\u3043\\u3044\\u3045[\\u305B]]&&[\\u3056-\\u305B]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_681_u3042_u3043_u3044_u3045_u3046_u3047_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042\\u3043\\u3044]&&[\\u3045\\u3046\\u3047]\\u3042\\u3043\\u3044]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_682_u3042_u3043_u3044_u3045_u3046_u3047_u3059_u305A_u305B_u3042_u3043_u3044_() {
        final String pattern = doSpecialChars("[[\\u3042\\u3043\\u3044]&&[\\u3045\\u3046\\u3047]\\u3059\\u305A\\u305B[\\u3042\\u3043\\u3044]]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_683_pL() {
        final String pattern = "\\pL";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_684_pL() {
        final String pattern = "\\pL";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = "7";
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_685_p_L_() {
        final String pattern = "\\p{L}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_686_p_IsL_() {
        final String pattern = "\\p{IsL}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_687_p_InHiragana_() {
        final String pattern = "\\p{InHiragana}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_688_p_InHiragana_() {
        final String pattern = "\\p{InHiragana}";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_689_pL_u3043_u3044() {
        final String pattern = doSpecialChars("\\pL\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_690_u3042_r_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_691_u3042_p_InGreek_() {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_692_u3042_P_InGreek_() {
        final String pattern = doSpecialChars("\\u3042\\P{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_693_u3042_P_InGreek_() {
        final String pattern = doSpecialChars("\\u3042\\P{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_694_u3042_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\u3042{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_695_u3042_p_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\u3042\\p{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_696_u3042_P_InGreek_() {
        try {
            final String pattern = doSpecialChars("\\u3042\\P{^InGreek}");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_697_u3042_p_InGreek_() {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_698_u3042_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_699_u3042_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\P{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_700_u3042_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\P{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_701_u3042_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[{^InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042n\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042n\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_702_u3042_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[{^InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u305B\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_703_u3042_p_InGreek_u3044() {
        try {
            final String pattern = doSpecialChars("\\u3042[\\p{^InGreek}]\\u3044");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_704_u3042_P_InGreek_u3044() {
        try {
            final String pattern = doSpecialChars("\\u3042[\\P{^InGreek}]\\u3044");
            JRegexRegularExpression.compile(pattern);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_705_u3042_p_InGreek_() {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_706_u3042_r_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_707_u3042_p_InGreek_r_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}r]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_708_u3042_r_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[r\\p{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_709_u3042_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[^\\p{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_710_u3042_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[^\\P{InGreek}]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_711_u3042_p_InGreek_u0370_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{InGreek}&&[^\\u0370]]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_712_u3042_u3044_() {
        final String pattern = doSpecialChars("\\u3042.\\u3044.+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042#\\u3044%&");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042#\\u3044%&"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_713_u3042_u3043_() {
        final String pattern = doSpecialChars("\\u3042\\u3043.");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_714_s_u3042_u3043_() {
        final String pattern = doSpecialChars("(?s)\\u3042\\u3043.");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\n");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\n"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_715_u3042_p_L_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u6000\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u6000\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_716_u3042_p_L_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042r\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042r\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_717_u3042_p_L_P_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042[\\p{L}&&[\\P{InGreek}]]\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_718_u3042_p_InGreek_u3044() {
        final String pattern = doSpecialChars("\\u3042\\p{InGreek}\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u0370\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u0370\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_719_u3042_p_Sc_() {
        final String pattern = doSpecialChars("\\u3042\\p{Sc}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042$");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042$"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_720_W_w_W() {
        final String pattern = "\\W\\w\\W";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("rrrr#\\u3048\\u3048\\u3048");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_721_u3042_u3043_u3044_s_u3045_u3046_u3047_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\s\\u3045\\u3046\\u3047]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044  \\u3045\\u3046\\u3047");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044  \\u3045\\u3046\\u3047"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_722_u3042_u3043_u3044_s_u305A_u305B_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\s\\u305A-\\u305B]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044 \\u305A \\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044 \\u305A \\u305B"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_723_u3042_u3043_u3044_u3042_u3045_s_u304e_u3051_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044[\\u3042-\\u3045\\s\\u304e-\\u3051]*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3042\\u3042 \\u304e\\u304f  \\u3051");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3042\\u3042 \\u304e\\u304f  \\u3051"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_724_u3042_u3043_s_u3044() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\s\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043 \\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043 \\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_725_s_s_s() {
        final String pattern = "\\s\\s\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049  \\u3046rr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_726_S_S_s() {
        final String pattern = "\\S\\S\\s";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049  \\u3046rr");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3049 "), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_727_u3042_u3043_d_u3044() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\d\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u30439\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u30439\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_728_d_d_d() {
        final String pattern = "\\d\\d\\d";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u304945");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_729_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("^\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3046\\u3047");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_730_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("^\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043\\u3044\\u3045\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_731_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_732_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_733_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_734_u3043() {
        final String pattern = doSpecialChars(".?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_735_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_736_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_737_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042??\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_738_u3043() {
        final String pattern = doSpecialChars(".??\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_739_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_740_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_741_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042?+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_742_u3043() {
        final String pattern = doSpecialChars(".?+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_743_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_744_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_745_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_746_u3043() {
        final String pattern = doSpecialChars(".+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_747_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_748_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_749_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042+?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_750_u3043() {
        final String pattern = doSpecialChars(".+?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_751_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_752_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_753_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042++\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_754_u3043() {
        final String pattern = doSpecialChars(".++\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_755_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_756_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_757_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_758_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_759_u3042_3_() {
        final String pattern = doSpecialChars("\\u3042{3,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_760_u3042_3_() {
        final String pattern = doSpecialChars("\\u3042{3,}");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_761_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_762_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_763_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_764_u3042_2_3_() {
        final String pattern = doSpecialChars("\\u3042{2,3}?");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_765_u3042_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?=\\u3045)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_766_u3042_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?=\\u3045)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u3046\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_767_u3042_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?!\\u3045)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u3042\\u3043\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_768_u3042_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(?!\\u3045)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u3042\\u3043\\u3044\\u3046\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_769_u3042_u3042_() {
        final String pattern = doSpecialChars("\\u3042(?<=\\u3042)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_770_u3042_u3042_() {
        final String pattern = doSpecialChars("\\u3042(?<=\\u3042)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3043\\u3044###");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_771_u3042_w() {
        final String pattern = doSpecialChars("(?<!\\u3042)\\w");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("###\\u3042\\u3043\\u3044a###");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("a", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_772_u3042_u3044() {
        final String pattern = doSpecialChars("(?<!\\u3042)\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_773_u3042_u3044() {
        final String pattern = doSpecialChars("(?<!\\u3042)\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_774_u3042_u3043_() {
        final String pattern = doSpecialChars("(\\u3042+\\u3043)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test_775_u3042_u3043_() {
        final String pattern = doSpecialChars("(\\u3042|\\u3043)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3044\\u3044\\u3044\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_776_u3042_u3043_() {
        final String pattern = doSpecialChars("(\\u3042\\u3043)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test_777_u3042_u3043_() {
        final String pattern = doSpecialChars("(\\u3042\\u3043)+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3044\\u3044\\u3044\\u3044\\u3045");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(1, matcher.groupCount());
    }

    @Test
    public void test_778_u3042_u3043_() {
        final String pattern = doSpecialChars("(\\u3042\\u3043)*");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3042\\u3043\\u3042\\u3043"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
    }

    @Test
    public void test_779_u3042_u3043_u3044_u3045_() {
        final String pattern = doSpecialChars("(\\u3042\\u3043)(\\u3044\\u3045*)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3043\\u3044\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(2, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3043"), matcher.group(1));
        assertEquals(doSpecialChars("\\u3044"), matcher.group(2));
    }

    @Test
    public void test_780_u3042_u3043_u3044_u3045_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\u3042\\u3043\\u3044(\\u3045)*\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3045\\u3045\\u3045\\u3045\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3045\\u3045\\u3045\\u3045\\u3045\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3045"), matcher.group(1));
    }

    @Test
    public void test_781_u3042_u3043_u3044_1() {
        final String pattern = doSpecialChars("(\\u3042*)\\u3043\\u3044\\1");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3043\\u3044\\u3042\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3043\\u3044\\u3042\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042\\u3042"), matcher.group(1));
    }

    @Test
    public void test_782_u3042_u3043_u3044_1() {
        final String pattern = doSpecialChars("(\\u3042*)\\u3043\\u3044\\1");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u305B\\u305B\\u305B\\u3042\\u3042\\u3043\\u3044\\u3042\\u305B\\u305B\\u305B");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3043\\u3044\\u3042"), matcher.group());
        assertEquals(1, matcher.groupCount());
        assertEquals(doSpecialChars("\\u3042"), matcher.group(1));
    }

    @Test
    public void test_783_u3048t_u3045_u3045_u3046_u305A_u3056_1_3_u3057_u3057_() {
        final String pattern = doSpecialChars("(\\u3048t*)(\\u3045\\u3045\\u3046)*(\\u305A\\u3056)\\1\\3(\\u3057\\u3057)");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
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
    public void test_784_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_785_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_786_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_787_u3043() {
        final String pattern = doSpecialChars(".*\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_788_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_789_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_790_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_791_u3043() {
        final String pattern = doSpecialChars(".*?\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_792_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_793_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_794_u3042_u3043() {
        final String pattern = doSpecialChars("\\u3042*+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3044\\u3044\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_795_u3043() {
        final String pattern = doSpecialChars(".*+\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3042\\u3042\\u3042\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_796_iu_uFF46_uFF4F_uFF4F_uFF42_uFF41_uFF52() {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_797_uFF46_iu_uFF4F_uFF4F_uFF42_uFF41_uFF52() {
        final String pattern = doSpecialChars("\\uFF46(?iu)\\uFF4F\\uFF4F\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_798_uFF46_uFF4F_uFF4F_iu_uFF42_uFF41_uFF52() {
        final String pattern = doSpecialChars("\\uFF46\\uFF4F\\uFF4F(?iu)\\uFF42\\uFF41\\uFF52");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF2F\\uFF4F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertFalse(matcher.find());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_799_iu_uFF46_uFF4F_uFF4F_uFF42_uFF41_uFF52_() {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F[\\uFF42\\uFF41\\uFF52]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_800_iu_uFF46_uFF4F_uFF4F_uFF41_uFF52_() {
        final String pattern = doSpecialChars("(?iu)\\uFF46\\uFF4F\\uFF4F[\\uFF41-\\uFF52]+");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\uFF46\\uFF4F\\uFF2F\\uFF42\\uFF21\\uFF52"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_801_Q_E_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_802_u3043l_Q_E_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\u3043l\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_803_Q_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_804_u3043l_u3042_u3049_Q_E_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\u3049\\Q***\\E\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_805_Q_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_806_Q_u3042_u3043() {
        final String pattern = doSpecialChars("\\Q*\\u3042\\u3043");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("*\\u3042\\u3043");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("*\\u3042\\u3043"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_807_u3043l_u3042_u3049_Q_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\u3049\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_808_u3043l_u3042_Q_u3042_u3043_u3044() {
        final String pattern = doSpecialChars("\\u3043l\\u3042\\Q***\\u3042\\u3043\\u3044");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042***\\u3042\\u3043\\u3044");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042***\\u3042\\u3043\\u3044"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_809_043_() {
        final String pattern = "[\\043]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_810_042_044_() {
        final String pattern = "[\\042-\\044]+";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals("#", matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_811_u1234_u1236_() {
        final String pattern = doSpecialChars("[\\u1234-\\u1236]");
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049\\u1235\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u1235"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

    @Test
    public void test_812_043_() {
        final String pattern = "[^\\043]*";
        final RegularExpression regex = JRegexRegularExpression.compile(pattern);
        final String input = doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049#\\u3043le\\u3044\\u3049");
        final StringMatcher<?> matcher = regex.matcher(input);
        assertTrue(matcher.find());
        assertEquals(doSpecialChars("\\u3043l\\u3042\\u3049\\u3043l\\u3042\\u3049"), matcher.group());
        assertEquals(0, matcher.groupCount());
    }

}
