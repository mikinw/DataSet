package com.mnw.dataset;

import org.junit.Rule;
import org.junit.Test;

/**
 * This example demonstrates how to write testVectors that expects certain Exception to be thrown.
 */
public class Example3_DataSetWithExpectedExceptionTest {

    @Rule
    public DataSetRule rule = new DataSetRule(); // <-- one Rule is enough, even though multiple datasets are used

    public static class MyWrongDataSet extends SimpleTestVectors {

        @Override
        protected Object[][] generateTestVectors() {
            return new Object[][] {
                    {NullPointerException.class, null},
                    {ArithmeticException.class, new Integer(0)},
                    {IllegalArgumentException.class, new Integer(-1)},
                    {IllegalArgumentException.class, new Integer(-2)},
            };
        }
    }

    @Test
    @DataSet(testData = MyWrongDataSet.class,
             expectedExceptionFirst = true) // <-- first element of any testVector is the expected Exception
    public void testClassWithWrongDataShouldThrowException() throws InvalidDataSetException {
        // init
        ClassDoesSomethingWithNumber sut = new ClassDoesSomethingWithNumber();
        final Integer param = (Integer) rule.getParameter(1); // <-- if expected exception is set, the first parameter index is 1

        // run
        sut.doTheThing(param);

        // verify
        // <-- verification is the thrown exception
    }

    // region This is the production code, but I added here for brevity

    public class ClassDoesSomethingWithNumber {
        public void doTheThing(Integer i) {
            if (i < 0) throw new IllegalArgumentException("not allowed parameter");
            String output = i.toString();
            float result = 5 / i;
        }
    }

    // endregion
}
