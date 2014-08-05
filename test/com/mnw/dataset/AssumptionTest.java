package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AssumptionTest {
    @Rule
    public DataSetRule rule = new DataSetRule();
    private StubSut sut;

    @Test
    public void dummy_FIXME() {
        // init

        // run


        // verify
    }

//    @Before
//    public void setUp() {
//        sut = new StubSut();
//    }
//
//    public static class InvalidPasswordsTestVectors extends SimpleTestVectors {
//        @Override
//        protected Object[][] generateTestVectors() {
//            return new Object[][] {
//                    {"aaaa"},
//                    {"bbb"},
//                    {"c"},
//            };
//        }
//    }
//
//    @Test
//    @DataSet(testData = InvalidPasswordsTestVectors.class)
//    public void canSkipAllTestCases() throws InvalidDataSetException{
//        org.junit.Assume.assumeTrue(false);
//        // init
//        // run
//        final boolean result = sut.doSomeTask(rule.getString(0));
//
//        // verify
//        Assert.assertTrue(result);
//    }
//
//    public static class SkipWithAssumptionTestVectors extends SimpleTestVectors {
//        @Override
//        protected Object[][] generateTestVectors() {
//            return new Object[][] {
//                    { false },
//                    { true },
//                    { false },
//            };
//        }
//    }
//
//    @Test
//    @DataSet(testData = SkipWithAssumptionTestVectors.class)
//    public void canSkipSomeTestCases() throws InvalidDataSetException{
//        org.junit.Assume.assumeTrue(rule.getBoolean(0));
//        Assert.assertTrue(true);
//    }
//
//    public static class FailTestVectors extends SimpleTestVectors {
//        @Override
//        protected Object[][] generateTestVectors() {
//            return new Object[][] {
//                    { false },
//                    { true },
//                    { false },
//            };
//        }
//    }
//
//    @Test
//    @DataSet(testData = FailTestVectors.class)
//    public void canFailSomeTestCases() throws InvalidDataSetException{
//        Assert.assertTrue(rule.getBoolean(0));
//    }
//
//    @Test
//    @DataSet(testData = FailTestVectors.class)
//    public void canFailSomeTestCasesWithException() throws InvalidDataSetException{
//        if (rule.getBoolean(0)) throw new RuntimeException("runtime exception");
//    }
//
//
//    @Test
//    @DataSet(testData = FailTestVectors.class)
//    public void canFailSomeTestCasesWithMixed() throws InvalidDataSetException{
//        if (rule.getBoolean(0)) throw new RuntimeException("runtime exception");
//        else org.junit.Assume.assumeTrue(false);
//    }

    private static class StubSut {
        public boolean doSomeTask(String s) {
            return s.equals("aaaa");
        }
    }
}
