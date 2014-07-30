package com.mnw.dataset;

import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class ExceptionedStatementComponentFactory implements StatementComponentFactory {
    @Override
    public Statement createStatement() {
        return null;
    }

    @Override
    public ParameterProvider createParameterProvider(Object[] testVector) {
        return new ExceptionedParameterProvider(testVector);
    }
}
