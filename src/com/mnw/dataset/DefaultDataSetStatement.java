package com.mnw.dataset;

import org.junit.runners.model.Statement;

/** TODO description of this class is missing */
public class DefaultDataSetStatement extends DataSetStatement {

    public DefaultDataSetStatement(Statement original, DataSet dataSet) {
        super(original, dataSet);
    }

    @Override
    Object getParameter(int i) throws InvalidDataSetException {
        if (mTestCase == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (mTestCase.length < i || i < 0) {
            throw new InvalidDataSetException("Requested parameter is out of the array");
        }
        return mTestCase[i];
    }

    @Override
    protected void evaluateTestCase() throws OriginalExceptionWrapper, InvalidDataSetException {
        try {
            mOriginalStatement.evaluate();

        } catch (Throwable evaluateException) {
        // gather more information on the exception
        // if we didn't expect exception (no expectedExceptionFirst set) we add the row information and throw the exception further
            throw new OriginalExceptionWrapper(evaluateException, "");
        }
    }
}
