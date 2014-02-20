package com.mnw.dataset;

import com.google.common.collect.Collections2;
import org.junit.internal.AssumptionViolatedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO description of this class is missing
 */
public class MultipleFailureOrSkipException extends Exception {

    private static final long serialVersionUID= 1L;
    private final List<Throwable> mErrorList;
    private final List<Throwable> mSkippedList;

    public MultipleFailureOrSkipException(List<Throwable> errors) {
        mErrorList= new ArrayList<Throwable>();
        mSkippedList= new ArrayList<Throwable>();
        for (Throwable error : errors) {
            if (error instanceof OriginalExceptionWrapper) {
                if (((OriginalExceptionWrapper)error).isSkipped()) {
                    mSkippedList.add(error);
                }
                if (((OriginalExceptionWrapper)error).isSerious() || ((OriginalExceptionWrapper)error).isAssertionError()) {
                    mErrorList.add(error);
                }
            }
        }
    }

    public List<Throwable> getFailures() {
        return Collections.unmodifiableList(mErrorList);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(String.format("There were %d errors:", mErrorList.size()));
        for (Throwable e : mErrorList) {
            sb.append(String.format("\n  %s(%s)", e.getClass().getName(), e.getMessage()));
        }
        return sb.toString();
    }

    @SuppressWarnings("deprecation")
    public static void assertEmpty(List<Throwable> errors) throws Throwable {
        if (errors.isEmpty()) return;
        if (errors.size() == 1) throw errors.get(0);
		/*
		 * Many places in the code are documented to throw
		 * org.junit.internal.runners.model.MultipleFailureException.
		 * That class now extends this one, so we throw the internal
		 * exception in case developers have tests that catch
		 * MultipleFailureException.
		 */
        throw new org.junit.internal.runners.model.MultipleFailureException(errors);
    }

    @SuppressWarnings("deprecation")
    public static void assertOnlyFailureOrError(List<Throwable> errors) throws Throwable {
        if (errors.size() >= 1 && errors.get(errors.size()-1) instanceof AssumptionViolatedException) {
            throw new AssumptionViolatedException("All test cases were skipped");
        }
        final List<Throwable> errorList = new ArrayList<Throwable>();
        final List<Throwable> skippedList = new ArrayList<Throwable>();
        for (Throwable error : errors) {
            if (error instanceof OriginalExceptionWrapper) {

                if (((OriginalExceptionWrapper)error).isSkipped()) {
                    skippedList.add(error);
                }

                if (((OriginalExceptionWrapper)error).isSerious() || ((OriginalExceptionWrapper)error).isAssertionError()) {
                    errorList.add(error);
                }
            } else {
                errorList.add(error);
            }
        }

//        if (errorList.isEmpty()) {
//            if (skippedList.isEmpty()) {
//                return;
//            }
//            return; //throw new AssumptionViolatedException("All test cases were skipped");
//        }
        if (errorList.size() == 1) throw errorList.get(0);
		/*
		 * Many places in the code are documented to throw
		 * org.junit.internal.runners.model.MultipleFailureException.
		 * That class now extends this one, so we throw the internal
		 * exception in case developers have tests that catch
		 * MultipleFailureException.
		 */
        throw new org.junit.internal.runners.model.MultipleFailureException(errorList);
    }

}
