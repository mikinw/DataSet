package com.mnw.dataset;

import java.util.ArrayList;

/**
 * Its purpose is to construct a DataSetMultiStatement with a collection of { statement, testvector, orderNumber }
 */
public class DataSetMultiStatementFactory {

    public ArrayList<DataSetStatement> createDataSetMultiStatementFrom(final TestCaseable mTestCases) {

        int testCaseNumber = 0;
        final ArrayList<DataSetStatement> dataSetStatements = new ArrayList<DataSetStatement>();
        for (int to = mTestCases.getCount(); testCaseNumber < to; testCaseNumber++) {
            final Object[] testVector = mTestCases.getTestCase(testCaseNumber);
            final DataSetStatement dataSetStatement = new DataSetStatement(testVector, testCaseNumber);
            dataSetStatements.add(dataSetStatement);
        }

        return dataSetStatements;
    }
}
