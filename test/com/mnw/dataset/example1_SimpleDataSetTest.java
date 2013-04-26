package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 */
public class example1_SimpleDataSetTest {

    @Rule
    public DataSetRule rule = new DataSetRule();

    public static class MyDataSet extends SimpleTestCaseable {
        @Override
        protected Object[][] generateTestVectors() {
            return new Object[][] {
                    {"alpha", true},
                    {"bravo", true},
                    {"alpha", false},
                    {"bravo", false}
            };
        }
    }

    @Test
    @DataSet(testData = MyDataSet.class)
    public void testFirst() throws InvalidDataSetException {
        Assert.assertTrue(rule.getString(0), rule.getBoolean(1));
    }
}
