package com.mnw.dataset;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class DefaultTestCaseEvaluator implements TestCaseEvaluator {
    private final ResultFactory mResultFactory;
    private final Statement mBaseStatement;

    public DefaultTestCaseEvaluator(final ResultFactory resultFactory,
                                    final Statement base) {
        mResultFactory = resultFactory;
        mBaseStatement = base;
    }

    @Override
    public Result evaluateTestCase(Object[] testVector) {
        try {
            mBaseStatement.evaluate();

        } catch (AssumptionViolatedException ave) {
            return mResultFactory.createSkippedResult(ave);
        } catch (AssertionError ae) {
            return mResultFactory.createAssertionFailureResult(ae);
        // TODO [mnw] catch InvalidDataSetException ?
        } catch (Throwable t) {
            return mResultFactory.createSeriousResult(t);
        }

        return mResultFactory.createPassResult();
    }
}
