package com.mnw.dataset;

import com.google.common.base.Preconditions;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;

/**
 * Entry point for the Runner. It constructs the {@link org.junit.runners.model.Statement} which in this case would contain
 * multiple evaluation runs.
 * Also this is the entry point for getting parameters from a specific testVector during an evaluation run.
 */
public class DataSetRule implements TestRule {

    // TODO [mnw] make it possible to add description to a given test vector


    /**
     * Holds the currently evaluated Statement
     */
    private DataSetMultiStatement mStatement;
    private final TestCaseableCreator mTestCaseableCreator;
    private final DataSetMultiStatementFactory mDataSetMultiStatementFactory;
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;

    private final ParameterPotImpl mParameterPot;
    private final ResultFactory mResultFactory;

    public DataSetRule() {
        mTestCaseableCreator = new TestCaseableCreator();
        mDataSetMultiStatementFactory = new DataSetMultiStatementFactory();
        mOriginalExceptionWrapperFactory = new OriginalExceptionWrapperFactory();
        mParameterPot = new ParameterPotImpl();
        mResultFactory = new ResultFactory();
    }

    //region Public api calls from the test methods

    /**
     * Returns the row number of the dataset that is being tested.
     * @return The current test iteration (number of row the test runs with)
     */
    public int getTestCaseNumber() {
        return mStatement.getTestCaseNumber();
    }


    public Object getParameter(int i) throws InvalidDataSetException {
        return mParameterPot.getParameter(i);
    }

    public boolean getBoolean(int i) throws InvalidDataSetException {
        return mParameterPot.getBoolean(i);
    }

    public int getInt(int i) throws InvalidDataSetException {
        return mParameterPot.getInt(i);
    }

    public String getString(int i) throws InvalidDataSetException {
        return mParameterPot.getString(i);
    }

    public long getLong(int i) throws InvalidDataSetException {
        return mParameterPot.getLong(i);
    }

    public short getShort(int i) throws InvalidDataSetException {
        return mParameterPot.getShort(i);
    }

    public byte getByte(int i) throws InvalidDataSetException {
        return mParameterPot.getByte(i);
    }

    public float getFloat(int i) throws InvalidDataSetException {
        return mParameterPot.getFloat(i);
    }

    public double getDouble(int i) throws InvalidDataSetException {
        return mParameterPot.getDouble(i);
    }

    //endregion Public api calls from the test methods

    @Override
    public final Statement apply(final Statement base, final Description description) {
        final DataSet dataSet = description.getAnnotation(DataSet.class);

        // if no dataset present or it is default (no actual dataset set), just run evaluate as usual
        // DataSet.class marks the default meaning no dataset has been given
        if (dataSetIsNullOrDefault(dataSet)) {
            return base;
        }

        Preconditions.checkState(mStatement == null, "Statement is already set for this Rule instance " +
                    "It is possible, you are using the DataSetRule in a parallel run environment. It was hardly possible, when the" +
                    "Rule was written, but the developer is happy to extend it. Please provide the way, you reached this exception.");

        final TestCaseable testData = mTestCaseableCreator.createTestData(dataSet);

        final TestCaseEvaluator testCaseEvaluator;
        final StatementComponentFactory statementComponentFactory;
        if (dataSet.expectedExceptionFirst()) {
            testCaseEvaluator = new ExceptionedCaseEvaluator(mResultFactory, base);
            statementComponentFactory = new ExceptionedStatementComponentFactory();
        } else {
            testCaseEvaluator = new DefaultTestCaseEvaluator(mResultFactory, base);
            statementComponentFactory = new DefaultStatementComponentFactory();
        }
        final ArrayList<DataSetStatement> dataSetMultiStatements = mDataSetMultiStatementFactory
                .createDataSetMultiStatementFrom(testData);

        mStatement = new DataSetMultiStatement(dataSetMultiStatements,
                                               testCaseEvaluator,
                                               statementComponentFactory,
                                               resultFactory, mParameterPot,
                                               mOriginalExceptionWrapperFactory,
                                               new ErrorReportDecoratorImpl(),
                                               new FailureVerifier(),
                                               new ResultAnalyser());

        return mStatement;

    }

    private boolean dataSetIsNullOrDefault(DataSet dataSet) {
        return dataSet == null || dataSet.testData() == DataSet.class;
    }

}
