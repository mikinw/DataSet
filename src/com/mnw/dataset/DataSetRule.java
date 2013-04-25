package com.mnw.dataset;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DataSetRule implements TestRule {

    /**
     * Holds the currently evaluated Statement
     */
    private DataSetStatement mStatement;

    /**
     * Returns the row number of the dataset that is being tested.
     * @return The current test iteration (number of row the test runs with)
     */
    public int getTestCaseNumber() {
        return mStatement.getTestCaseNumber();
    }

    /**
     * returns an element from the mTestCases 2 dimensional array. The first dimension is set by a
     * loop that calls evaluate as many times as the length of the array (first dimension). The
     * second dimension is provided by the parameter. The function checks if the requested Object is
     * out of boundaries or not. It is the callers responsibility to cast the returned Object to the
     * desired class.
     * @param i represents the i.th element in the defined dataset row. This value should be returned.
     * @return The i.th element of the dataset row of the current test iteration.
     * @throws InvalidDataSetException if something goes wrong
     */
    public Object getParameter(int i) throws InvalidDataSetException {
        return mStatement.getParameter(i);
    }

    public int getInteger(int i) throws InvalidDataSetException {
        return (Integer)mStatement.getParameter(i);
    }

    public String getString(int i) throws InvalidDataSetException {
        return (String)mStatement.getParameter(i);
    }

    public long getLong(int i) throws InvalidDataSetException {
        Object o = mStatement.getParameter(i);
        if (o instanceof Long) {
            return (Long)o;
        } else if (o instanceof Integer) {
            return (Integer)o;
        }
        return (Long)o;
    }

    @Override
    public final Statement apply(final Statement statement, final Description description) {
        DataSet dataSet = description.getAnnotation(DataSet.class);
        

        // if no dataset present or it is default (no actual dataset set), just run evaluate as usual
        // DataSet.class marks the default meaning no dataset has been given
        if (dataSet == null || dataSet.testData() == DataSet.class) {
            return statement;
        }

        if (mStatement != null) {
            throw new IllegalStateException("statement is already set for this Rule instance");
        }

        if (dataSet.expectedExceptionFirst()) {
            mStatement = new ExceptionedDataSetStatement(statement, dataSet);
        } else {
            mStatement = new DefaultDataSetStatement(statement, dataSet);
        }

        return mStatement;

    }

}
