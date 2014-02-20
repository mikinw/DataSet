package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AssumptionTest {
    @Rule
    public DataSetRule rule = new DataSetRule();
    private StubSut sut;

    @Before
    public void setUp() {
        sut = new StubSut();
    }

    public static class InvalidPasswordsTestVectors extends SimpleTestVectors {
        @Override
        protected Object[][] generateTestVectors() {
            return new Object[][] {
                    {"aaaa"},
                    {"bbb"},
                    {"c"},
            };
        }
    }

    @Test
    @DataSet(testData = InvalidPasswordsTestVectors.class)
    public void disallowInvalidPasswordsInTropicana() throws InvalidDataSetException{
        org.junit.Assume.assumeTrue(false);
        // init
        // run
        final boolean result = sut.doSomeTask(rule.getString(0));

        // verify
        Assert.assertTrue(result);
    }

    private static class StubSut {
        public boolean doSomeTask(String s) {
            return s.equals("aaaa");
        }
    }
}
