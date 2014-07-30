package com.mnw.dataset;

/**
 * Creates an array of testVectors out of a DataSet.
 */
public class TestCaseableCreator {

    // try to instantiate and set the test data
    public TestCaseable createTestData(final DataSet dataSet) {
        TestCaseable testCases;
        try {
            Class<?> testDataClass = dataSet.testData();
            testCases = (TestCaseable) testDataClass.newInstance();

        } catch (InstantiationException ie) {
            String details =
                    "Can't instantiate given DataSet. It may be an interface, abstract class, a primitive type, void, \n" +
                            " or it isn't static class," +
                            " or it has no nullary constructor," +
                            " or it may reference some abstract method.";
            throw new RuntimeException(details);
        } catch (IllegalAccessException iae) {
            String details = "Can't instantiate given DataSet. Nullary constructor can't be accessed.";
            throw new RuntimeException(details);
        }
        return testCases;
    }
}
