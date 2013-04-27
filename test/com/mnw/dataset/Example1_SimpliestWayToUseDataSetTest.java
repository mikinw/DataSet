package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This example demonstrates the simplest way to set up a dataset
 */
public class Example1_SimpliestWayToUseDataSetTest {

    @Rule
    public DataSetRule rule = new DataSetRule(); // <-- this is used to access to the testVectors from inside the tests

    public static class MyDataSet extends SimpleTestVectors {
        @Override
        protected Object[][] generateTestVectors() {
            return new Object[][] {
                    {true,  "alpha", new CustomProductionClass()}, // <-- this is a testVector
                    {true,  "bravo", new CustomProductionClass()},
                    {false, "alpha", new CustomProductionClass()},
                    {false, "bravo", new CustomProductionClass() }
            };
        }
    }

    @Test
    @DataSet(testData = MyDataSet.class) // <-- annotate the test with the dataset
    public void testFirst() throws InvalidDataSetException { // <-- any access to testData may result in Exception
        boolean myTextFixture = rule.getBoolean(0); // <-- this is how you access an element of the testVector. Indexing starts with 0
        String myAssertMessage = rule.getString(1); // <-- there are a couple of typed parameter getters
        CustomProductionClass myCustomObject = (CustomProductionClass) rule.getParameter(2); // <-- for other classes you need to cast
        Assert.assertTrue(myAssertMessage, true);
    }

    // region This is the production code, but I added here for brevity

    // this is static to be accessible from the dataset class. Normally the production class is in a separate
    // file and it doesn't need to be static
    public static class CustomProductionClass {
    }

    // endregion
}
