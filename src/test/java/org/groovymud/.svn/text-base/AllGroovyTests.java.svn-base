package org.groovymud;

import groovy.util.GroovyTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllGroovyTests extends TestSuite {

    private static final String TEST_ROOT = "src/test/groovy/";

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite();
        GroovyTestSuite gsuite = new GroovyTestSuite();

        suite.addTestSuite(gsuite.compile(TEST_ROOT + "utils/GenericSyntaxParserTest.groovy"));
        suite.addTestSuite(gsuite.compile(TEST_ROOT + "utils/MatchedObjectTest.groovy"));
        return suite;
    }
}
