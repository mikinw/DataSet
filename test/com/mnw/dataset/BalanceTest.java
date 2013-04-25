package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by: mikinw
 * Date: 11/12/2012
 */

public class BalanceTest {

    @Rule
    public CarFeature car = new CarFeature();


    @SuppressWarnings("MagicNumber")
    public static class GetValueAsStringData implements TestCaseable {

        private Object[][] testCases = {
                new Object[] {0, "0.00"},
                new Object[] {1, "0.01"},
                new Object[] {2, "0.02"},
                new Object[] {3, "0.03"},
                new Object[] {4, "0.04"},
                new Object[] {5, "0.05"},
                new Object[] {6, "0.06"},
                new Object[] {100, "1.00"},
                new Object[] {200, "2.00"},
                new Object[] {101, "1.01"},
                new Object[] {111, "1.11"},
                new Object[] {123456789, "1234567.89"},
        };

        @Override
        public Object[] getTestCase(int i) {
            return testCases[i];
        }

        @Override
        public int getCount() {
            return testCases.length;
        }
    }

    @Test
    @DataSet(testData = GetValueAsStringData.class)
    public void testGetValueAsStringForGalaxyRequests() throws InvalidDataSetException {
        Balance sut = new Balance(car.weight(), "");

        String s = sut.getValueAsString();

        Assert.assertEquals(car.name(), s);
    }

    private class CarFeature extends DataSetRule {
        protected int weight() throws InvalidDataSetException {
            return super.getInteger(0);
        }

        protected String name() throws InvalidDataSetException {
            return super.getString(1);
        }
    }

    //region

    //exmples:
    //
    //simple dataset
    //expected exception dataset
    //even simplier dataset (SimpleTestCaseable)
    //generated dataset (type, every days of month)
    //typed parameter accessors
    //named parameter accessors

    //endregion
}
