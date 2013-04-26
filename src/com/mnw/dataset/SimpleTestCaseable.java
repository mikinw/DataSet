package com.mnw.dataset;

/**
 * Simple implementation of {@link TestCaseable}. It takes a testVector array in its constructor. Each line of
 * this 2 dimensional array represents a testVector. The DataSet Rule will run the test method with each
 * line.
 */
public abstract class SimpleTestCaseable implements TestCaseable {

    private Object[][] mTestVectors;

    public SimpleTestCaseable () {
        mTestVectors = generateTestVectors();
    }

    protected abstract Object[][] generateTestVectors();

    @Override
    public Object[] getTestCase(int i) {
        return mTestVectors[i];
    }

    @Override
    public int getCount() {
        return mTestVectors.length;
    }
}
