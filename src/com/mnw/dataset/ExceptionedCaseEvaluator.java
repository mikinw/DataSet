package com.mnw.dataset;

import org.junit.Assert;
import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class ExceptionedCaseEvaluator implements TestCaseEvaluator {
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;
    private final Statement mBaseStatement;

    public ExceptionedCaseEvaluator(OriginalExceptionWrapperFactory originalExceptionWrapperFactory, Statement base) {
        mOriginalExceptionWrapperFactory = originalExceptionWrapperFactory;
        mBaseStatement = base;
    }

    @Override
    public void evaluateTestCase(Object[] testVector) throws InvalidDataSetException, OriginalExceptionWrapper, PassedTestCaseException {
        final Class<? extends Throwable> expectedExceptionClass = findExpectedExceptionClass(testVector);

        try {
            mBaseStatement.evaluate();

            // we expect exception or else it is a failed run
            Assert.fail("Expected: " + expectedExceptionClass);

        } catch (Throwable evaluateException) {
            // gather more information on the exception
            // try to match the expected exception with the evaluated one (superclasses taken into account. It this is not what we expected, we add the row information and throw the exception further

            //noinspection StatementWithEmptyBody
            if (!expectedExceptionClass.isAssignableFrom(evaluateException.getClass())) {
                throw mOriginalExceptionWrapperFactory.create(evaluateException, "Unexpected Exception: " + evaluateException + " instead of ");
            } else {
                throw new PassedTestCaseException();
            }
        }
    }

    // try to find out what should be the expected exception
    private Class<? extends Throwable> findExpectedExceptionClass(Object[] testVector) throws InvalidDataSetException {
        Class<? extends Throwable> expectedExceptionClass;
        try {
            expectedExceptionClass = (Class<? extends Throwable>) testVector[0];
        } catch (Throwable e) {
            throw new InvalidDataSetException(
                    "Expected exception class is not properly defined in the provided DataSet (make sure it is a Class type, is descendant of Exception and is the 0th element");
        }

        if (expectedExceptionClass == null) {
            throw new InvalidDataSetException(
                    "Expected exception class is null. It should never be if you set expectedExceptionFirst to true in the annotation");
        }
        return expectedExceptionClass;
    }
}
