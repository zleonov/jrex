// Code adapted from Apache Harmony: https://github.com/apache/harmony/blob/trunk/classlib/modules/regex/src/test/java/org/apache/harmony/tests/java/util/regex/ModeTest.java

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

import java.util.regex.Matcher;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReplaceTest {

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

    @Test
    public void testSimpleReplace() throws Throwable {
        String target, pattern, repl;

        target = "foobarfobarfoofo1";
        pattern = "fo[^o]";
        repl = "xxx";

        JDKRegularExpression p = JDKRegularExpression.compile(pattern);
        InputMatcher<Matcher> m = p.matcher(target);

        assertEquals("foobarxxxarfoofo1", m.replaceFirst(repl));
        assertEquals("foobarxxxarfooxxx", m.replaceAll(repl));
    }

    @Test
    public void testCaptureReplace() throws Throwable {
        String target, pattern, repl, s;
        JDKRegularExpression p = null;
        InputMatcher<Matcher> m;

        target = "[31]foo;bar[42];[99]xyz";
        pattern = "\\[([0-9]+)\\]([a-z]+)";
        repl = "$2[$1]";

        p = JDKRegularExpression.compile(pattern);
        m = p.matcher(target);
        s = m.replaceFirst(repl);
        assertEquals("foo[31];bar[42];[99]xyz", s);
        s = m.replaceAll(repl);
        assertEquals("foo[31];bar[42];xyz[99]", s);

        target = "[31]foo(42)bar{63}zoo;[12]abc(34)def{56}ghi;{99}xyz[88]xyz(77)xyz;";
        pattern = "\\[([0-9]+)\\]([a-z]+)\\(([0-9]+)\\)([a-z]+)\\{([0-9]+)\\}([a-z]+)";
        repl = "[$5]$6($3)$4{$1}$2";

        p = JDKRegularExpression.compile(pattern);
        m = p.matcher(target);
        s = m.replaceFirst(repl);
        assertEquals("[63]zoo(42)bar{31}foo;[12]abc(34)def{56}ghi;{99}xyz[88]xyz(77)xyz;", s);
        s = m.replaceAll(repl);
        assertEquals("[63]zoo(42)bar{31}foo;[56]ghi(34)def{12}abc;{99}xyz[88]xyz(77)xyz;", s);
    }

    @Test
    public void testEscapeReplace() {
        String target, pattern, repl, s;

        target = "foo'bar''foo";
        pattern = "'";
        repl = "\\'";
        s = target.replaceAll(pattern, repl);
        assertEquals("foo'bar''foo", s);
        repl = "\\\\'";
        s = target.replaceAll(pattern, repl);
        assertEquals("foo\\'bar\\'\\'foo", s);
        repl = "\\$3";
        s = target.replaceAll(pattern, repl);
        assertEquals("foo$3bar$3$3foo", s);
    }

}
