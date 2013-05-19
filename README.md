DataSet
=======

JUnit test Rule to verify a test method with a bunch of testVectors. Similar to TestNG DataSet
 annotation. The main advantage over the Parametrized runner is that, this can be used together
 with any other runner (eg. with Robolectric runner, if you are writing tests for Android). 
Blogpost: http://mikinw.blogspot.co.uk/2013/04/parametrized-unit-test-for-junit-dataset.html

_Note_: at least JUnit 4 is needed, since Rules were introduced there.

Features:
---------
 * can be used with any TestRunner (eg. with Robolectric Runner)
 * can have multiple datasets in the same test class (and different test methods can use either of them)
 * can set expected exceptions (and they can be different for each testVector)
 * can have run time generated dataset

Simple Usage:
-------------

        public class Example1_SimpliestWayToUseDataSetTest {
            @Rule
            public DataSetRule rule = new DataSetRule(); // <-- this is used to access to the testVectors from inside the tests

            public static class MyDataSet extends SimpleTestVectors {
                @Override
                protected Object[][] generateTestVectors() {
                    return new Object[][] {
                            {true,  "alpha", new CustomProductionClass()}, // <-- this is a testVector
                            {true,  "bravo", new CustomProductionClass()},
                            {false, "alpha", new CustomProductionClass()},
                            {false, "bravo", new CustomProductionClass() }
                    };
                }
            }

            @Test
            @DataSet(testData = MyDataSet.class) // <-- annotate the test with the dataset
            public void testFirst() throws InvalidDataSetException { // <-- any access to testData may result in Exception
                boolean myTextFixture = rule.getBoolean(0); // <-- this is how you access an element of the testVector. Indexing starts with 0
                String myAssertMessage = rule.getString(1); // <-- there are a couple of typed parameter getters
                CustomProductionClass myCustomObject = (CustomProductionClass) rule.getParameter(2); // <-- for other classes you need to cast
                Assert.assertTrue(myAssertMessage, true);
            }
        }


Examples:
---------
You can find more examples for usage in the `test/com/mnw/dataset` package.
