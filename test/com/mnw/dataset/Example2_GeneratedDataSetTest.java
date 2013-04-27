package com.mnw.dataset;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This example demonstrates how to generate a dataset more dynamically. With this you can iterate through
 * all items of an enum, generate a large dataset (eg. you want to test numbers from 1 to 10000).
 */
public class Example2_GeneratedDataSetTest {

    @Rule
    public DataSetRule rule = new DataSetRule(); // <-- one Rule is enough, even though multiple datasets are used

    public static class GeneratedDataSetEnum implements TestCaseable { // <-- this interface is implemented

        @Override
        public Object[] getTestCase(int i) {
            return new Object[] { MyEnum.values()[i] }; // <-- testVector, which contains only one element.
        }

        @Override
        public int getCount() {
            return MyEnum.values().length;
        }
    }

    public static class OtherGeneratedDataSet implements TestCaseable {

        @Override
        public Object[] getTestCase(int i) {
            final int generatedTestParameterX = i / 10;
            final int generatedTestParameterY = i % 10;
            return new Object[] { generatedTestParameterX, generatedTestParameterY };
        }

        @Override
        public int getCount() {
            return 100; // <-- sum of testVectors
        }
    }

    @Test
    @DataSet(testData = GeneratedDataSetEnum.class)
    public void testClassConstructedWithMyEnumCanBeConstructedWithAnyElementOfMyEnum() throws InvalidDataSetException {
        // init
        int ANY_INT = 3;
        final MyEnum myEnum = (MyEnum) rule.getParameter(0); // <-- custom parameter needs to be casted

        // run
        new ClassConstructedWithMyEnum(myEnum, ANY_INT);
    }

    @Test
    @DataSet(testData = OtherGeneratedDataSet.class)
    public void testCalculation() throws InvalidDataSetException {
        // init
        int paramX = rule.getInt(0);
        int paramY = rule.getInt(1);
        SophisticatedCalculatorClass sut = new SophisticatedCalculatorClass();

        // run
        int testCalculation = sut.calculateSomething(paramX, paramY);

        // verify
        assertTrue("Calculation result should be less then 100 for any input less then 10", testCalculation < 100);
    }

    // region This is the production code, but I added here for brevity

    enum MyEnum {
        ALPHA,
        BRAVO,
        CHARLIE,
        DELTA
    }

    public class ClassConstructedWithMyEnum {
        public ClassConstructedWithMyEnum(MyEnum myEnum, int i) {}
    }

    public class SophisticatedCalculatorClass {
        public int calculateSomething(int paramX, int paramY) {
            return (paramX * 2 + paramY * 3) / 4 - 20;
        }
    }

    // endregion
}
