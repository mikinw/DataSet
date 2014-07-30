package com.mnw.dataset;

import com.google.common.base.Preconditions;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;

public class DataSetRule implements TestRule {

    /**
     * Holds the currently evaluated Statement
     */
    private DataSetMultiStatement mStatement;
    private final TestCaseableCreator mTestCaseableCreator;
    private final DataSetMultiStatementFactory mDataSetMultiStatementFactory;
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;

    public DataSetRule() {
        mTestCaseableCreator = new TestCaseableCreator();
        mDataSetMultiStatementFactory = new DataSetMultiStatementFactory();
        mOriginalExceptionWrapperFactory = new OriginalExceptionWrapperFactory();
    }

    //region Public api calls from the test methods

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
        if (mStatement == null) {
            throw new NullPointerException("DataSetRule is not properly initialised (TestVector seems to be null)." + 
                " DataSetRule should be initialised directly in the test class (not in the setUp()/@Before method).");
        }
        return mStatement.getParameter(i);
    }

    public boolean getBoolean(int i) throws InvalidDataSetException {
        final Boolean parameter;
        try {
            parameter = (Boolean) mStatement.getParameter(i);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + i + " can't be casted to Boolean.", e);
        }
        return parameter;
    }

    public int getInt(int i) throws InvalidDataSetException {
        final Integer parameter;
        try {
            parameter = (Integer)mStatement.getParameter(i);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + i + " can't be casted to Integer.", e);
        }
        return parameter;
    }

    public String getString(int i) throws InvalidDataSetException {
        final String parameter;
        try {
            parameter = (String)mStatement.getParameter(i);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + i + " can't be casted to String.", e);
        }
        return parameter;
    }

    public long getLong(int i) throws InvalidDataSetException {
        Object o = mStatement.getParameter(i);
        if (o instanceof Long) {
            return (Long)o;
        } else if (o instanceof Integer) {
            return (Integer)o;
        }
        throw new InvalidDataSetException("Parameter " + i + " can't be casted to Long.");
    }

    //endregion Public api calls from the test methods

    @Override
    public final Statement apply(final Statement base, final Description description) {
        final DataSet dataSet = description.getAnnotation(DataSet.class);

        // if no dataset present or it is default (no actual dataset set), just run evaluate as usual
        // DataSet.class marks the default meaning no dataset has been given
        if (dataSet == null || dataSet.testData() == DataSet.class) {
            return base;
        }

        Preconditions.checkState(mStatement == null, "Statement is already set for this Rule instance " +
                    "It is possible, you are using the DataSetRule in a parallel run environment. It was hardly possible, when the" +
                    "Rule was written, but the developer is happy to extend it. Please provide the way, you reached this exception.");

        final TestCaseable testData = mTestCaseableCreator.createTestData(dataSet);

        TestCaseEvaluator testCaseEvaluator;
        StatementComponentFactory statementComponentFactory;
        if (dataSet.expectedExceptionFirst()) {
            testCaseEvaluator = new ExceptionedCaseEvaluator(mOriginalExceptionWrapperFactory, base);
            statementComponentFactory = new ExceptionedStatementComponentFactory();
        } else {
            testCaseEvaluator = new DefaultTestCaseEvaluator(mOriginalExceptionWrapperFactory, base);
            statementComponentFactory = new DefaultStatementComponentFactory();
        }
        final ArrayList<DataSetStatement> dataSetMultiStatements = mDataSetMultiStatementFactory
                .createDataSetMultiStatementFrom(testData);

        mStatement = new DataSetMultiStatement(dataSetMultiStatements,
                                               testCaseEvaluator,
                                               statementComponentFactory,
                                               mOriginalExceptionWrapperFactory,
                                               new ErrorReportDecoratorImpl(),
                                               new FailureVerifier());

        return mStatement;

    }

}
