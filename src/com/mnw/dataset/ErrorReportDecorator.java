package com.mnw.dataset;

/** TODO description of this class is missing */
public interface ErrorReportDecorator {
    Throwable decorate(Throwable originalThrowable, int testCaseNo, String hint);

    void wrap(Throwable originalThrowable, int testCaseNo, String hint);

    Throwable decorate();
}
