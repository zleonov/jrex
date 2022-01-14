// Code adapted from Apache Harmony: https://github.com/apache/harmony/blob/trunk/classlib/modules/regex/src/test/java/org/apache/harmony/tests/java/util/regex/Matcher*Test.java

/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.leonov.regex.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatcherTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    //@formatter:off
    
    String[] testPatterns = {
            "(a|b)*abb",
            "(1*2*3*4*)*567",
            "(a|b|c|d)*aab",
            "(1|2|3|4|5|6|7|8|9|0)(1|2|3|4|5|6|7|8|9|0)*",
            "(abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ)*",
            "(a|b)*(a|b)*A(a|b)*lice.*",
            "(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)(a|b|c|d|e|f|g|h|"
                    + "i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)*(1|2|3|4|5|6|7|8|9|0)*|while|for|struct|if|do" };

    String[] groupPatterns = { "(a|b)*aabb", "((a)|b)*aabb", "((a|b)*)a(abb)",
            "(((a)|(b))*)aabb", "(((a)|(b))*)aa(b)b", "(((a)|(b))*)a(a(b)b)" };
    
    //@formatter:on

//@Test public void testRegionsIntInt() throws Throwable {
//        JDKRegularExpression p = JDKRegularExpression.compile("x*");
//        StringMatcher<Matcher> m = p.matcher("axxxxxa");
//        assertFalse(m.matches());
//        
//        m.region(1, 6);
//        assertEquals(1, m.regionStart());
//        assertEquals(6, m.regionEnd());
//        assertTrue(m.matches());
//        
//        try {
//            m.region(1, 0);
//            fail("expected an IOOBE");
//        } catch(IndexOutOfBoundsException e) {
//        }
//        
//        try {
//            m.region(-1, 2);
//            fail("expected an IOOBE");
//        } catch(IndexOutOfBoundsException e) {
//        }
//        
//        try {
//            m.region(10, 11);
//            fail("expected an IOOBE");
//        } catch(IndexOutOfBoundsException e) {
//        }
//        
//        try {
//            m.region(1, 10);
//            fail("expected an IOOBE");
//        } catch(IndexOutOfBoundsException e) {
//        }
//    }

    @Test
    public void testAppendReplacement() throws Throwable {
        JDKRegularExpression pat = JDKRegularExpression.compile("XX");
        InputMatcher m = pat.matcher("Today is XX-XX-XX ...");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; m.find(); i++) {
            m.appendReplacement(sb, new Integer(i * 10 + i).toString());
        }
        m.appendTail(sb);
        assertEquals("Today is 0-11-22 ...", sb.toString());
    }

    @Test
    public void testAppendReplacementRef() throws Throwable {
        JDKRegularExpression p = JDKRegularExpression.compile("xx (rur|\\$)");
        InputMatcher m = p.matcher("xx $ equals to xx rur.");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; m.find(); i *= 30) {
            String rep = new Integer(i).toString() + " $1";
            m.appendReplacement(sb, rep);
        }
        m.appendTail(sb);
        assertEquals("1 $ equals to 30 rur.", sb.toString());
    }

    @Test
    public void testReplaceAll() throws Throwable {
        String input = "aabfooaabfooabfoob";
        String pattern = "a*b";
        JDKRegularExpression pat = JDKRegularExpression.compile(pattern);
        InputMatcher mat = pat.matcher(input);

        assertEquals("-foo-foo-foo-", mat.replaceAll("-"));
    }

//    /*
//     * Class under test for StringMatcher<Matcher> reset(CharSequence)
//     */
//@Test public void testResetCharSequence() throws Throwable {
//        JDKRegularExpression p = JDKRegularExpression.compile("abcd");
//        StringMatcher<Matcher> m = p.matcher("abcd");
//        assertTrue(m.matches());
//        m.reset("efgh");
//        assertFalse(m.matches());
//        
//        try {
//            m.reset(null);
//            fail("expected a NPE");
//        } catch (NullPointerException e) {
//        }
//    }

    @Test
    public void testAppendSlashes() throws Throwable {
        JDKRegularExpression p = JDKRegularExpression.compile("\\\\");
        InputMatcher m = p.matcher("one\\cat\\two\\cats\\in\\the\\yard");
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            m.appendReplacement(sb, "\\\\");
        }
        m.appendTail(sb);
        assertEquals("one\\cat\\two\\cats\\in\\the\\yard", sb.toString());

    }

    @Test
    public void testReplaceFirst() throws Throwable {
        String input = "zzzdogzzzdogzzz";
        String pattern = "dog";
        JDKRegularExpression pat = JDKRegularExpression.compile(pattern);
        InputMatcher mat = pat.matcher(input);

        assertEquals("zzzcatzzzdogzzz", mat.replaceFirst("cat"));
    }

//    @Test
//@Test     public void testPattern() throws Throwable {
//        for (String element : testPatterns) {
//            JDKRegularExpression test = JDKRegularExpression.compile(element);
//            assertEquals(test, test.matcher("aaa").pattern());
//        }
//
//        for (String element : testPatterns) {
//            assertEquals(element, JDKRegularExpression.compile(element).matcher("aaa").pattern().toString());
//        }
//    }

    /*
     * Class under test for StringMatcher<Matcher> reset()
     */
    @Test
    public void testReset() throws Throwable {
    }

    /*
     * Class under test for String group(int)
     */
    @Test
    public void testGroupint() throws Throwable {
        String positiveTestString = "ababababbaaabb";

        // test IndexOutOfBoundsException
        // //
        for (int i = 0; i < groupPatterns.length; i++) {
            JDKRegularExpression test = JDKRegularExpression.compile(groupPatterns[i]);
            InputMatcher mat = test.matcher(positiveTestString);
            mat.matches();
            try {
                // groupPattern <index + 1> equals to number of groups
                // of the specified pattern
                // //
                mat.group(i + 2);
                fail("IndexOutBoundsException expected");
                mat.group(i + 100);
                fail("IndexOutBoundsException expected");
                mat.group(-1);
                fail("IndexOutBoundsException expected");
                mat.group(-100);
                fail("IndexOutBoundsException expected");
            } catch (IndexOutOfBoundsException iobe) {
            }
        }

        String[][] groupResults = { { "a" }, { "a", "a" }, { "ababababba", "a", "abb" }, { "ababababba", "a", "a", "b" }, { "ababababba", "a", "a", "b", "b" }, { "ababababba", "a", "a", "b", "abb", "b" }, };

        for (int i = 0; i < groupPatterns.length; i++) {
            JDKRegularExpression test = JDKRegularExpression.compile(groupPatterns[i]);
            InputMatcher mat = test.matcher(positiveTestString);
            mat.matches();
            for (int j = 0; j < groupResults[i].length; j++) {
                assertEquals(groupResults[i][j], mat.group(j + 1), "i: " + i + " j: " + j);
            }

        }

    }

    @Test
    public void testGroup() throws Throwable {
        String positiveTestString = "ababababbaaabb";
        String negativeTestString = "gjhfgdsjfhgcbv";
        for (String element : groupPatterns) {
            JDKRegularExpression test = JDKRegularExpression.compile(element);
            InputMatcher mat = test.matcher(positiveTestString);
            mat.matches();
            // test result
            assertEquals(positiveTestString, mat.group());

            // test equal to group(0) result
            assertEquals(mat.group(0), mat.group());
        }

        for (String element : groupPatterns) {
            JDKRegularExpression test = JDKRegularExpression.compile(element);
            InputMatcher mat = test.matcher(negativeTestString);
            mat.matches();
            try {
                mat.group();
                fail("IllegalStateException expected for <false> matches result");
            } catch (IllegalStateException ise) {
            }
        }
    }

    @Test
    public void testGroupPossessive() throws Throwable {
        JDKRegularExpression pat = JDKRegularExpression.compile("((a)|(b))++c");
        InputMatcher mat = pat.matcher("aac");

        mat.matches();
        assertEquals("a", mat.group(1));
    }

    /*
     * Class under test for boolean find(int)
     */
    @Test
    public void testFindint() throws Throwable {
    }

    /*
     * Class under test for int start(int)
     */
    @Test
    public void testStartint() throws Throwable {
    }

    /*
     * Class under test for int end(int)
     */
    @Test
    public void testEndint() throws Throwable {
    }

    @Test
    public void testMatchesMisc() throws Throwable {
        String[][] posSeq = { { "abb", "ababb", "abababbababb", "abababbababbabababbbbbabb" }, { "213567", "12324567", "1234567", "213213567", "21312312312567", "444444567" }, { "abcdaab", "aab", "abaab", "cdaab", "acbdadcbaab" },
                { "213234567", "3458", "0987654", "7689546432", "0398576", "98432", "5" },
                { "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" },
                { "ababbaAabababblice", "ababbaAliceababab", "ababbAabliceaaa", "abbbAbbbliceaaa", "Alice" }, { "a123", "bnxnvgds156", "for", "while", "if", "struct" }

        };

        for (int i = 0; i < testPatterns.length; i++) {
            JDKRegularExpression pat = JDKRegularExpression.compile(testPatterns[i]);
            for (int j = 0; j < posSeq[i].length; j++) {
                InputMatcher mat = pat.matcher(posSeq[i][j]);
                assertTrue(mat.matches(), "Incorrect match: " + testPatterns[i] + " vs " + posSeq[i][j]);
            }
        }
    }

    @Test
    public void testMatchesQuantifiers() throws Throwable {
        String[] testPatternsSingles = { "a{5}", "a{2,4}", "a{3,}" };
        String[] testPatternsMultiple = { "((a)|(b)){1,2}abb", "((a)|(b)){2,4}", "((a)|(b)){3,}" };

        String[][] stringSingles = { { "aaaaa", "aaa" }, { "aa", "a", "aaa", "aaaaaa", "aaaa", "aaaaa" }, { "aaa", "a", "aaaa", "aa" }, };

        String[][] stringMultiples = { { "ababb", "aba" }, { "ab", "b", "bab", "ababa", "abba", "abababbb" }, { "aba", "b", "abaa", "ba" }, };

        for (int i = 0; i < testPatternsSingles.length; i++) {
            JDKRegularExpression pat = JDKRegularExpression.compile(testPatternsSingles[i]);
            for (int j = 0; j < stringSingles.length / 2; j++) {
                assertTrue(pat.matcher(stringSingles[i][j * 2]).matches(), "Match expected, but failed: " + pat.pattern() + " : " + stringSingles[i][j]);
                assertFalse(pat.matcher(stringSingles[i][j * 2 + 1]).matches(), "Match failure expected, but match succeed: " + pat.pattern() + " : " + stringSingles[i][j * 2 + 1]);
            }
        }

        for (int i = 0; i < testPatternsMultiple.length; i++) {
            JDKRegularExpression pat = JDKRegularExpression.compile(testPatternsMultiple[i]);
            for (int j = 0; j < stringMultiples.length / 2; j++) {
                assertTrue(pat.matcher(stringMultiples[i][j * 2]).matches(), "Match expected, but failed: " + pat.pattern() + " : " + stringMultiples[i][j]);
                assertFalse(

                        pat.matcher(stringMultiples[i][j * 2 + 1]).matches(), "Match failure expected, but match succeed: " + pat.pattern() + " : " + stringMultiples[i][j * 2 + 1]);
            }
        }
    }

    @Test
    public void testQuantVsGroup() throws Throwable {
        String patternString = "(d{1,3})((a|c)*)(d{1,3})((a|c)*)(d{1,3})";
        String testString = "dacaacaacaaddaaacaacaaddd";

        JDKRegularExpression pat = JDKRegularExpression.compile(patternString);
        InputMatcher mat = pat.matcher(testString);

        mat.matches();
        assertEquals("dacaacaacaaddaaacaacaaddd", mat.group());
        assertEquals("d", mat.group(1));
        assertEquals("acaacaacaa", mat.group(2));
        assertEquals("dd", mat.group(4));
        assertEquals("aaacaacaa", mat.group(5));
        assertEquals("ddd", mat.group(7));
    }

    @Test
    public void testLookingAt() throws Throwable {
    }

    /*
     * Class under test for boolean find()
     */
    @Test
    public void testFind() throws Throwable {
        String testPattern = "(abb)";
        String testString = "cccabbabbabbabbabb";
        JDKRegularExpression pat = JDKRegularExpression.compile(testPattern);
        InputMatcher mat = pat.matcher(testString);
        int start = 3;
        int end = 6;
        while (mat.find()) {
            assertEquals(start, mat.start(1));
            assertEquals(end, mat.end(1));

            start = end;
            end += 3;
        }

        testPattern = "(\\d{1,3})";
        testString = "aaaa123456789045";

        JDKRegularExpression pat2 = JDKRegularExpression.compile(testPattern);
        InputMatcher mat2 = pat2.matcher(testString);
        start = 4;
        int length = 3;
        while (mat2.find()) {
            assertEquals(testString.substring(start, start + length), mat2.group(1));
            start += length;
        }
    }

    @Test
    public void testSEOLsymbols() throws Throwable {
        JDKRegularExpression pat = JDKRegularExpression.compile("^a\\(bb\\[$");
        InputMatcher mat = pat.matcher("a(bb[");

        assertTrue(mat.matches());
    }

    /*
     * Class under test for int start()
     */
    @Test
    public void testStart() throws Throwable {
    }

    @Test
    public void testGroupCount() throws Throwable {
        for (int i = 0; i < groupPatterns.length; i++) {
            JDKRegularExpression test = JDKRegularExpression.compile(groupPatterns[i]);
            InputMatcher mat = test.matcher("ababababbaaabb");
            mat.matches();
            assertEquals(i + 1, mat.groupCount());

        }
    }

    @Test
    public void testRelactantQuantifiers() throws Throwable {
        JDKRegularExpression pat = JDKRegularExpression.compile("(ab*)*b");
        InputMatcher mat = pat.matcher("abbbb");

        if (mat.matches()) {
            assertEquals("abbb", mat.group(1));
        } else {
            fail("Match expected: (ab*)*b vs abbbb");
        }
    }

    @Test
    public void testEnhancedFind() throws Throwable {
        String input = "foob";
        String pattern = "a*b";
        JDKRegularExpression pat = JDKRegularExpression.compile(pattern);
        InputMatcher mat = pat.matcher(input);

        mat.find();
        assertEquals("b", mat.group());
    }

    @Test
    public void testPosCompositeGroup() throws Throwable {
        String[] posExamples = { "aabbcc", "aacc", "bbaabbcc" };
        String[] negExamples = { "aabb", "bb", "bbaabb" };
        JDKRegularExpression posPat = JDKRegularExpression.compile("(aa|bb){1,3}+cc");
        JDKRegularExpression negPat = JDKRegularExpression.compile("(aa|bb){1,3}+bb");

        InputMatcher mat;
        for (String element : posExamples) {
            mat = posPat.matcher(element);
            assertTrue(mat.matches());
        }

        for (String element : negExamples) {
            mat = negPat.matcher(element);
            assertFalse(mat.matches());
        }

        assertTrue(JDKRegularExpression.compile("(aa|bb){1,3}+bb").matcher("aabbaabb").matches());

    }

    @Test
    public void testPosAltGroup() throws Throwable {
        String[] posExamples = { "aacc", "bbcc", "cc" };
        String[] negExamples = { "bb", "aa" };
        JDKRegularExpression posPat = JDKRegularExpression.compile("(aa|bb)?+cc");
        JDKRegularExpression negPat = JDKRegularExpression.compile("(aa|bb)?+bb");

        InputMatcher mat;
        for (String element : posExamples) {
            mat = posPat.matcher(element);
            assertTrue(mat.matches(), posPat.toString() + " vs: " + element);
        }

        for (String element : negExamples) {
            mat = negPat.matcher(element);
            assertFalse(mat.matches());
        }

        assertTrue(JDKRegularExpression.compile("(aa|bb)?+bb").matcher("aabb").matches());
    }

    @Test
    public void testRelCompGroup() throws Throwable {

        InputMatcher mat;
        JDKRegularExpression pat;
        String res = "";
        for (int i = 0; i < 4; i++) {
            pat = JDKRegularExpression.compile("((aa|bb){" + i + ",3}?).*cc");
            mat = pat.matcher("aaaaaacc");
            assertTrue(mat.matches(), pat.toString() + " vs: " + "aaaaaacc");
            assertEquals(res, mat.group(1));
            res += "aa";
        }
    }

    @Test
    public void testRelAltGroup() throws Throwable {

        InputMatcher mat;
        JDKRegularExpression pat;

        pat = JDKRegularExpression.compile("((aa|bb)??).*cc");
        mat = pat.matcher("aacc");
        assertTrue(mat.matches(), pat.toString() + " vs: " + "aacc");
        assertEquals("", mat.group(1));

        pat = JDKRegularExpression.compile("((aa|bb)??)cc");
        mat = pat.matcher("aacc");
        assertTrue(mat.matches(), pat.toString() + " vs: " + "aacc");
        assertEquals("aa", mat.group(1));
    }

    @Test
    public void testIgnoreCase() throws Throwable {
        JDKRegularExpression pat = JDKRegularExpression.compile("(aa|bb)*", Pattern.CASE_INSENSITIVE);
        InputMatcher mat = pat.matcher("aAbb");

        assertTrue(mat.matches());

        pat = JDKRegularExpression.compile("(a|b|c|d|e)*", Pattern.CASE_INSENSITIVE);
        mat = pat.matcher("aAebbAEaEdebbedEccEdebbedEaedaebEbdCCdbBDcdcdADa");
        assertTrue(mat.matches());

        pat = JDKRegularExpression.compile("[a-e]*", Pattern.CASE_INSENSITIVE);
        mat = pat.matcher("aAebbAEaEdebbedEccEdebbedEaedaebEbdCCdbBDcdcdADa");
        assertTrue(mat.matches());

    }

//@Test public void testQuoteReplacement() throws Throwable {
//        assertEquals("\\\\aaCC\\$1", StringMatcher<Matcher>.quoteReplacement("\\aaCC$1"));
//    }

    @Test
    public void testOverFlow() throws Throwable {
        JDKRegularExpression tp = JDKRegularExpression.compile("(a*)*");
        InputMatcher tm = tp.matcher("aaa");
        assertTrue(tm.matches());
        assertEquals("", tm.group(1));

        assertTrue(JDKRegularExpression.compile("(1+)\\1+").matcher("11").matches());
        assertTrue(JDKRegularExpression.compile("(1+)(2*)\\2+").matcher("11").matches());

        JDKRegularExpression pat = JDKRegularExpression.compile("(1+)\\1*");
        InputMatcher mat = pat.matcher("11");

        assertTrue(mat.matches());
        assertEquals("11", mat.group(1));

        pat = JDKRegularExpression.compile("((1+)|(2+))(\\2+)");
        mat = pat.matcher("11");

        assertTrue(mat.matches());
        assertEquals("1", mat.group(2));
        assertEquals("1", mat.group(1));
        assertEquals("1", mat.group(4));
        assertNull(mat.group(3));

    }

    @Test
    public void testUnicode() throws Throwable {

        assertTrue(JDKRegularExpression.compile("\\x61a").matcher("aa").matches());
        assertTrue(JDKRegularExpression.compile("\\u0061a").matcher("aa").matches());
        assertTrue(JDKRegularExpression.compile("\\0141a").matcher("aa").matches());
        assertTrue(JDKRegularExpression.compile("\\0777").matcher("?7").matches());

    }

    @Test
    public void testUnicodeCategory() throws Throwable {
        assertTrue(JDKRegularExpression.compile("\\p{Ll}").matcher("k").matches()); // Unicode lower case
        assertTrue(JDKRegularExpression.compile("\\P{Ll}").matcher("K").matches()); // Unicode non-lower
        // case
        assertTrue(JDKRegularExpression.compile("\\p{Lu}").matcher("K").matches()); // Unicode upper case
        assertTrue(JDKRegularExpression.compile("\\P{Lu}").matcher("k").matches()); // Unicode non-upper
        // case
        // combinations
        assertTrue(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Lu}]]").matcher("k").matches());
        assertTrue(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Ll}]]").matcher("K").matches());
        assertFalse(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Lu}]]").matcher("K").matches());
        assertFalse(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Ll}]]").matcher("k").matches());

        // category/character combinations
        assertFalse(JDKRegularExpression.compile("[\\p{L}&&[^a-z]]").matcher("k").matches());
        assertTrue(JDKRegularExpression.compile("[\\p{L}&&[^a-z]]").matcher("K").matches());

        assertTrue(JDKRegularExpression.compile("[\\p{Lu}a-z]").matcher("k").matches());
        assertTrue(JDKRegularExpression.compile("[a-z\\p{Lu}]").matcher("k").matches());

        assertFalse(JDKRegularExpression.compile("[\\p{Lu}a-d]").matcher("k").matches());
        assertTrue(JDKRegularExpression.compile("[a-d\\p{Lu}]").matcher("K").matches());

        // assertTrue(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Lu}&&[^K]]]").matcher("K").matches());
        assertFalse(JDKRegularExpression.compile("[\\p{L}&&[^\\p{Lu}&&[^G]]]").matcher("K").matches());

    }

//@Test public void testSplitEmpty() throws Throwable {
//
//        JDKRegularExpression pat = JDKRegularExpression.compile("");
//        String[] s = pat.split("", -1);
//
//        assertEquals(1, s.length);
//        assertEquals("", s[0]);
//    }

    @Test
    public void testFindDollar() throws Throwable {
        InputMatcher mat = JDKRegularExpression.compile("a$").matcher("a\n");
        assertTrue(mat.find());
        assertEquals("a", mat.group());
    }

//    /*
//     * Verify if the StringMatcher<Matcher> can match the input when region is changed
//     */
//@Test public void testMatchesRegionChanged() throws Throwable {
//        // Regression for HARMONY-610
//        String input = " word ";
//        JDKRegularExpression pattern = JDKRegularExpression.compile("\\w+");
//        StringMatcher<Matcher> matcher = pattern.matcher(input);
//        matcher.region(1, 5);
//        assertTrue(matcher.matches());
//    }

//    @Test
//    public void testAllCodePoints() throws Throwable {
//        // Regression for HARMONY-3145
//        int[] codePoint = new int[1];
//        JDKRegularExpression p = JDKRegularExpression.compile("(\\p{all})+");
//        boolean res = true;
//        int cnt = 0;
//        String s;
//        for (int i = 0; i < 0x110000; i++) {
//            codePoint[0] = i;
//            s = new String(codePoint, 0, 1);
//            if (!s.matches(p.toString())) {
//                cnt++;
//                res = false;
//            }
//        }
//        assertTrue(res);
//        assertEquals(0, cnt);
//
//        p = JDKRegularExpression.compile("(\\P{all})+");
//        res = true;
//        cnt = 0;
//
//        for (int i = 0; i < 0x110000; i++) {
//            codePoint[0] = i;
//            s = new String(codePoint, 0, 1);
//            if (!s.matches(p.toString())) {
//                cnt++;
//                res = false;
//            }
//        }
//
//        assertFalse(res);
//        assertEquals(0x110000, cnt);
//    }

//    @Test
//    public void testAllCodePoints_Java() throws Throwable {
//        // Regression for HARMONY-3145
//        int[] codePoint = new int[1];
//        JDKRegularExpression p = JDKRegularExpression.compile("(\\p{all})+");
//        boolean res = true;
//        int cnt = 0;
//        String s;
//        for (int i = 0; i < 0x110000; i++) {
//            codePoint[0] = i;
//            s = new String(codePoint, 0, 1);
//            if (!s.matches(p.toString())) {
//                cnt++;
//                res = false;
//            }
//        }
//        assertTrue(res);
//        assertEquals(0, cnt);
//
//        p = JDKRegularExpression.compile("(\\P{all})+");
//        res = true;
//        cnt = 0;
//
//        for (int i = 0; i < 0x110000; i++) {
//            codePoint[0] = i;
//            s = new String(codePoint, 0, 1);
//            if (!s.matches(p.toString())) {
//                cnt++;
//                res = false;
//            }
//        }
//
//        assertFalse(res);
//        assertEquals(0x110000, cnt);
//    }

//    /*
//     * Verify if the StringMatcher<Matcher> behaves correct when region is changed
//     */
//@Test public void testFindRegionChanged() throws Throwable {
//        // Regression for HARMONY-625
//        JDKRegularExpression pattern = JDKRegularExpression.compile("(?s).*");
//        StringMatcher<Matcher> matcher = pattern.matcher("abcde");
//        matcher.find();
//        assertEquals("abcde", matcher.group());
//
//        matcher = pattern.matcher("abcde");
//        matcher.region(0, 2);
//        matcher.find();
//        assertEquals("ab", matcher.group());
//
//    }

//    /*
//     * Verify if the StringMatcher<Matcher> behaves correct with pattern "c" when region is
//     * changed
//     */
//@Test public void testFindRegionChanged2() throws Throwable {
//        // Regression for HARMONY-713
//        JDKRegularExpression pattern = JDKRegularExpression.compile("c");
//
//        String inputStr = "aabb.c";
//        StringMatcher<Matcher> matcher = pattern.matcher(inputStr);
//        matcher.region(0, 3);
//
//        assertFalse(matcher.find());
//    }

    /*
     * Regression test for HARMONY-674
     */
    @Test
    public void testPatternMatcher() throws Throwable {
        JDKRegularExpression pattern = JDKRegularExpression.compile("(?:\\d+)(?:pt)");
        assertTrue(pattern.matcher("14pt").matches());
    }

    /**
     * Inspired by HARMONY-3360
     */
    @Test
    public void test3360() throws Throwable {
        String str = "!\"#%&'(),-./";
        JDKRegularExpression p = JDKRegularExpression.compile("\\s");
        InputMatcher m = p.matcher(str);

        assertFalse(m.find());
    }

    /**
     * Regression test for HARMONY-3360
     */
    @Test
    public void testGeneralPunctuationCategory() throws Throwable {
        String[] s = { ",", "!", "\"", "#", "%", "&", "'", "(", ")", "-", ".", "/" };
        String regexp = "\\p{P}";

        for (int i = 0; i < s.length; i++) {
            JDKRegularExpression pattern = JDKRegularExpression.compile(regexp);
            InputMatcher matcher = pattern.matcher(s[i]);
            assertTrue(matcher.find());
        }
    }

//    /**
//     * Regression test for HARMONY-4396
//     */
//@Test public void testHitEndAfterFind() throws Throwable {
//        hitEndTest(true, "#01.0", "r((ege)|(geg))x", "regexx", false);
//        hitEndTest(true, "#01.1", "r((ege)|(geg))x", "regex", false);
//        hitEndTest(true, "#01.2", "r((ege)|(geg))x", "rege", true);
//        hitEndTest(true, "#01.2", "r((ege)|(geg))x", "xregexx", false);
//
//        hitEndTest(true, "#02.0", "regex", "rexreger", true);
//        hitEndTest(true, "#02.1", "regex", "raxregexr", false);
//
//        String floatRegex = getHexFloatRegex();
//        hitEndTest(true, "#03.0", floatRegex, Double.toHexString(-1.234d), true);
//        hitEndTest(true, "#03.1", floatRegex, "1 ABC"
//                + Double.toHexString(Double.NaN) + "buhuhu", false);
//        hitEndTest(true, "#03.2", floatRegex, Double.toHexString(-0.0) + "--",
//                false);
//        hitEndTest(true, "#03.3", floatRegex, "--"
//                + Double.toHexString(Double.MIN_VALUE) + "--", false);
//
//        hitEndTest(true, "#04.0", "(\\d+) fish (\\d+) fish (\\w+) fish (\\d+)",
//                "1 fish 2 fish red fish 5", true);
//        hitEndTest(true, "#04.1", "(\\d+) fish (\\d+) fish (\\w+) fish (\\d+)",
//                "----1 fish 2 fish red fish 5----", false);
//    }

//    /*
//     * Test if StringMatcher<Matcher>'s toString conatain pattern information
//     */
//    @Test
//@Test     public void testToString() throws Throwable {
//        String result = JDKRegularExpression.compile("(\\d{1,3})").matcher("aaaa123456789045").toString();
//        assertTrue(result.contains("(\\d{1,3})"), "The result doesn't contain pattern info");
//    }

//    private void hitEndTest(boolean callFind, String testNo, String regex,
//            String input, boolean hit) {
//        JDKRegularExpression pattern = JDKRegularExpression.compile(regex);
//        StringMatcher<Matcher> matcher = pattern.matcher(input);
//        if (callFind) {
//            matcher.find();
//        } else {
//            matcher.matches();
//        }
//        boolean h = matcher.hitEnd();
//
//        assertTrue(h == hit, testNo);
//    }

//    private String getHexFloatRegex() throws Throwable {
//        String hexDecimal = "(-|\\+)?0[xX][0-9a-fA-F]*\\.[0-9a-fA-F]+([pP](-|\\+)?[0-9]+)?";
//        String notANumber = "((-|\\+)?Infinity)|([nN]a[nN])";
//        return new StringBuilder("((").append(hexDecimal).append(")|(").append(notANumber).append("))").toString();
//    }

    @Test
    public void test_toString() throws Throwable {
        JDKRegularExpression p = JDKRegularExpression.compile("foo");
        InputMatcher m = p.matcher("bar");
        assertNotNull(m.toString());
    }

    @Test
    public void testErrorConditions() throws Throwable {
        // Test match cursors in absence of a match
        JDKRegularExpression p = JDKRegularExpression.compile("foo");
        InputMatcher m = p.matcher("bar");
        assertFalse(m.matches());

        try {
            m.start();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.end();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.group();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.start(1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.end(1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.group(1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

//        // regression test for HARMONY-2418
//        try {
//            m.usePattern(null);
//            fail("IllegalArgumentException expected");
//        } catch (IllegalArgumentException e) {
//            // PASSED
//        }
    }

    @Test
    public void testErrorConditions2() throws Throwable {
        // Test match cursors in absence of a match
        JDKRegularExpression p = JDKRegularExpression.compile("(foo[0-9])(bar[a-z])");
        InputMatcher m = p.matcher("foo1barzfoo2baryfoozbar5");

        assertTrue(m.find());
        assertEquals(0, m.start());
        assertEquals(8, m.end());
        assertEquals(0, m.start(1));
        assertEquals(4, m.end(1));
        assertEquals(4, m.start(2));
        assertEquals(8, m.end(2));

        try {
            m.start(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.end(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.group(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.start(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.end(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.group(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        assertTrue(m.find());
        assertEquals(8, m.start());
        assertEquals(16, m.end());
        assertEquals(8, m.start(1));
        assertEquals(12, m.end(1));
        assertEquals(12, m.start(2));
        assertEquals(16, m.end(2));

        try {
            m.start(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.end(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.group(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.start(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.end(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            m.group(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
        }

        assertFalse(m.find());

        try {
            m.start(3);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.end(3);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.group(3);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.start(-1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.end(-1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }

        try {
            m.group(-1);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
        }
    }

    /*
     * Regression test for HARMONY-997
     */
    @Test
    public void testReplacementBackSlash() throws Throwable {
        String str = "replace me";
        String replacedString = "me";
        String substitutionString = "\\";
        JDKRegularExpression pat = JDKRegularExpression.compile(replacedString);
        InputMatcher mat = pat.matcher(str);
        try {
            mat.replaceAll(substitutionString);
            fail("IndexOutOfBoundsException should be thrown");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
