package com.mnw.dataset;

import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class DefaultTestCaseEvaluator implements TestCaseEvaluator {
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;
    private final Statement mBaseStatement;

    public DefaultTestCaseEvaluator(final OriginalExceptionWrapperFactory originalExceptionWrapperFactory, Statement base) {
        mOriginalExceptionWrapperFactory = originalExceptionWrapperFactory;
        mBaseStatement = base;
    }

    @Override
    public void evaluateTestCase(Object[] testVector) throws OriginalExceptionWrapper, PassedTestCaseException {
        try {
            mBaseStatement.evaluate();

        } catch (Throwable evaluateException) {
            // gather more information on the exception
            // if we didn't expect exception (no expectedExceptionFirst set) we add the row information and throw the exception further
            throw mOriginalExceptionWrapperFactory.create(evaluateException, ""); // TODO [mnw] change method to createRowInformation(?)
        }
        throw new PassedTestCaseException();
    }
}
