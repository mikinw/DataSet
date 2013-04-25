package com.mnw.dataset;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** <p>Class to represent the value of an account's balance<p> */
public class Balance {

    /** The account's balance in the specified currency (in pence, cents etc.) */
    private long mValue;

    /** Currency code for the balance. Possible value GBP */
    private String mCurrencyCode = "";

    /**
     *
     * @param value amount value. For £1.23 you should give 123.
     * @param currencyCode typically "GBP".
     */
    public Balance(long value, String currencyCode) {
        this.mValue = value;
        this.mCurrencyCode = currencyCode;
    }

    /**
     * Converts value to string (eg. "23.45").
     *
     * @return the value as string adding the decimal separator, the two digits after that.
     */
    public String getValueAsString() {
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(false);
        DecimalFormatSymbols formatSymbols =  new DecimalFormatSymbols(new Locale("en_GB"));
        decimalFormat.setDecimalFormatSymbols(formatSymbols);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        BigDecimal bigDecimal = new BigDecimal(mValue);
        bigDecimal = bigDecimal.movePointLeft(2);
        return decimalFormat.format(bigDecimal);
    }

}
