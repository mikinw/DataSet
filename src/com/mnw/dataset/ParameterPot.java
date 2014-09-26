package com.mnw.dataset;

/**
 * Provides typed parameters for a given test run.
 */
public interface ParameterPot {

    Object getParameter(int parameterOrder) throws InvalidDataSetException;

    String getString(int parameterOrder) throws InvalidDataSetException;

    boolean getBoolean(int parameterOrder) throws InvalidDataSetException;

    int getInt(int parameterOrder) throws InvalidDataSetException;

    long getLong(int parameterOrder) throws InvalidDataSetException;

    double getDouble(int parameterOrder) throws InvalidDataSetException;

    float getFloat(int parameterOrder) throws InvalidDataSetException;

    byte getByte(int parameterOrder) throws InvalidDataSetException;

    short getShort(int parameterOrder) throws InvalidDataSetException;
}
