package com.mnw.dataset;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class ExceptionedCaseEvaluator implements TestCaseEvaluator {
    private final Statement mBaseStatement;
    private final ResultFactory mResultFactory;

    public ExceptionedCaseEvaluator(final ResultFactory resultFactory, final Statement base) {
        mBaseStatement = base;
        mResultFactory = resultFactory;
    }

    @Override
    public Result evaluateTestCase(Object[] testVector) {
        try {
            final Class<? extends Throwable> expectedExceptionClass = findExpectedExceptionClass(testVector);

            try {

                mBaseStatement.evaluate();

                // we expect exception or else it is a failed run
                Assert.fail("Expected: " + expectedExceptionClass);

            } catch (AssumptionViolatedException ave) {
                return mResultFactory.createSkippedResult(ave);
            } catch (AssertionError ae) {
                return mResultFactory.createAssertionFailureResult(ae);
                // TODO [mnw] catch InvalidDataSetException ?
            } catch (Throwable evaluateException) {
                return mResultFactory.createSeriousResult(t);
                // gather more information on the exception
                // try to match the expected exception with the evaluated one (superclasses taken into account. It this is not what we expected, we add the row information and throw the exception further

                //noinspection StatementWithEmptyBody
                // TODO [mnw] continue from here
                if (!expectedExceptionClass.isAssignableFrom(evaluateException.getClass())) {
                    throw mOriginalExceptionWrapperFactory.create(evaluateException, "Unexpected Exception: " + evaluateException + " instead of ");
                } else {
                    return mResultFactory.createPassResult();
                }
            }

        } catch (InvalidDataSetException idse) {
            return mResultFactory.createSeriousResult(idse);
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
