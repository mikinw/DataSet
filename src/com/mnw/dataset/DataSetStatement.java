package com.mnw.dataset;

/**
 * Wraps a {@link TestCaseEvaluator} (which has a {@link org.junit.runners.model.Statement}), a test case vector
 * ({@link ParameterProvider}) and an orderNumber.
 */
public class DataSetStatement {

    final Object[] mTestVector;
    final int mOrderNumber;


    public DataSetStatement(final Object[] testVector,
                            final int orderNumber) {
        mTestVector = testVector;
        mOrderNumber = orderNumber;
    }

}
