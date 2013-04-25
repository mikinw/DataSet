package com.mnw.dataset;

import org.junit.Assert;
import org.junit.runners.model.Statement;

/**
 * Specific implementation of {@link DataSetStatement}, that expects an Exception class on the first
 * place of the test vector. This Exception is the expected exception.
 */

public class ExceptionedDataSetStatement extends DataSetStatement {
    public ExceptionedDataSetStatement(Statement original, DataSet dataSet) {
        super(original, dataSet);
    }

    @Override
    Object getParameter(int i) throws InvalidDataSetException {
        if (mTestVector == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (i == 0) {
            throw new InvalidDataSetException(
                    "0th parameter requested, while expectedExceptionFirst is set. First requestable parameter is getParameter(1)");
        }
        if (mTestVector.length <= i || i < 1) {
            throw new InvalidDataSetException("Requested parameter is out of the array");
        }
        return mTestVector[i];
    }

    @Override
    protected void evaluateTestCase() throws OriginalExceptionWrapper, InvalidDataSetException {
        final Class<? extends Throwable> expectedExceptionClass = findExpectedExceptionClass();

        try {
            mOriginalStatement.evaluate();

            // we expect exception or else it is a failed run
            Assert.fail("Expected: " + expectedExceptionClass);

        } catch (Throwable evaluateException) {
            // gather more information on the exception
            // try to match the expected exception with the evaluated one (superclasses taken into account. It this is not what we expected, we add the row information and throw the exception further

            //noinspection StatementWithEmptyBody
            if (!expectedExceptionClass.isAssignableFrom(evaluateException.getClass())) {
                throw new OriginalExceptionWrapper(evaluateException, "Unexpected Exception: " + evaluateException + " instead of ");
            } else {
                // good, next step
            }
        }

    }

    // try to find out what should be the expected exception
    private Class<? extends Throwable> findExpectedExceptionClass() throws InvalidDataSetException {
        Class<? extends Throwable> expectedExceptionClass;
        try {
            expectedExceptionClass = (Class<? extends Throwable>) mTestVector[0];
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
