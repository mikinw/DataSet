package com.mnw.dataset;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSet {
    String value() default "-+-";
    Class<?> testData() default DataSet.class;
    boolean expectedExceptionFirst() default false;
}
