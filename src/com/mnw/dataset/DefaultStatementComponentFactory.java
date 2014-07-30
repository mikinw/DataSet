package com.mnw.dataset;

import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public class DefaultStatementComponentFactory implements StatementComponentFactory {
    @Override
    public Statement createStatement() {
        return null;
    }

    @Override
    public ParameterProvider createParameterProvider(Object[] testVector) {
        return new DefaultParameterProvider(testVector);
    }
}
