package com.mnw.dataset;

import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public interface TestCaseEvaluator {
    void evaluateTestCase(Object[] testVector) throws OriginalExceptionWrapper, InvalidDataSetException, PassedTestCaseException;
}
