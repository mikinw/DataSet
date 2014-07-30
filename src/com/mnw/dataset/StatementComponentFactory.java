package com.mnw.dataset;

import org.junit.runners.model.Statement;

/**
 * TODO description of this class is missing
 */
public interface StatementComponentFactory {
    Statement createStatement();
    ParameterProvider createParameterProvider(Object[] testVector);
}
