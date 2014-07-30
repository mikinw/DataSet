package com.mnw.dataset;

/**
 * Allows to set the testVector for the current statement
 */
public interface TestVectorStore {

    void setCurrentTestVector(final ParameterProvider parameterProvider);
    void clearCurrentTestVector();
}
