package com.mnw.dataset;

/**
 * Compared to {@link com.mnw.dataset.Result} it contains additional information about the test run:
 * the original test vector.
 */
public class ResultDetail {

    private final Result mResult;

    private final Object[] mTestVector;

    public ResultDetail(Result result, Object[] testVector) {
        mResult = result;
        mTestVector = testVector;
    }
}
