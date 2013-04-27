package com.mnw.dataset;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This example demonstrates how to extend the DataSetRule if you would like to have custom getters for your dataset.
 */
public class Example4_CustomNamesDataSetGettersTest {

    private class CarFeature extends DataSetRule {  // <-- custom Rule, which extends the DataSetRule
        protected int engine() throws InvalidDataSetException { // <-- custom type, custom name
            return super.getInteger(2); // <-- uses one of the getters provided by the DataSetRule
        }

        protected String customNamedGetter() throws InvalidDataSetException {
            return super.getString(1);
        }
    }

    @Rule
    public CarFeature car = new CarFeature(); // <-- use your custom Rule

    public static class GetValueAsStringData extends SimpleTestCaseable {
        @Override
        protected Object[][] generateTestVectors() {
            return new Object[][]  {
                    new Object[] {"Ford 1.6",    "Ford",    1600},
                    new Object[] {"BMW 1.6",     "BMW",     1600},
                    new Object[] {"BMW 2.0",     "BMW",     2000},
                    new Object[] {"Trabant 0.8", "Trabant",  800},
            };
        }
    }

    @Test
    @DataSet(testData = GetValueAsStringData.class)
    public void testGetValueAsStringForGalaxyRequests() throws InvalidDataSetException {
        // init
        ReadableCarName sut = new ReadableCarName(car.engine(), car.customNamedGetter()); // <-- use your custom getters

        // run
        String result = sut.getReadableCarName();

        // verify
        String expectedName = car.getString(0); // <-- still can access to elements of the testVector, you haven't defined custom getter for
        Assert.assertEquals(expectedName, result);
    }


    // region This is the production code, but I added here for brevity

    public class ReadableCarName {
        private int engine;
        private String type;

        public ReadableCarName(int engine, String type) {
            this.engine = engine;
            this.type = type;
        }

        public String getReadableCarName() {
            final DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setGroupingUsed(false);
            DecimalFormatSymbols formatSymbols =  new DecimalFormatSymbols(new Locale("en_GB"));
            decimalFormat.setDecimalFormatSymbols(formatSymbols);
            decimalFormat.setMaximumFractionDigits(1);
            decimalFormat.setMinimumFractionDigits(1);

            BigDecimal bigDecimal = new BigDecimal(engine);
            bigDecimal = bigDecimal.movePointLeft(3);
            return type + " " + decimalFormat.format(bigDecimal);
        }
    }

    // endregion
}
