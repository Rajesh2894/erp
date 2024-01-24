/*This is a utility class to convert amount to words*
 * 
 */
package com.abm.mainet.water.utility;

/**
 * @author akshata.bhat
 *
 */
public class NumberToWordsConverter {


    public static final String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight","Nine", "Ten", 
    		"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };

    public static final String[] tens  = { "",
    		"",                                                                                                                                                                                // 1
            "Twenty",                                                                                                                                                                          // 2
            "Thirty",                                                                                                                                                                          // 3
            "Forty",                                                                                                                                                                           // 4
            "Fifty",                                                                                                                                                                           // 5
            "Sixty",                                                                                                                                                                           // 6
            "Seventy",                                                                                                                                                                         // 7
            "Eighty",                                                                                                                                                                          // 8
            "Ninety"                                                                                                                                                                           // 9
    };


    public String convert(final int n) {

        if (n == 0) {
            return "Zero ";
        }
        if (n < 0) {
            return "Minus " + convertN(-n);
        }
        if (n < 20) {
            return units[n];
        }
        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }
        if (n < 1000) {
            return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convertN(n % 100);
        }
        if (n < 100000) {
            return convertN(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convertN(n % 1000);
        }
        if (n < 10000000) {
            return convertN(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convertN(n % 100000);
        }
        return convertN(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convertN(n % 10000000);
    }



    public String convertN(final int n) {
        if (n < 0) {
            return "Minus " + convertN(-n);
        }
        if (n < 20) {
            return units[n];
        }
        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }
        if (n < 1000) {
            return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convertN(n % 100);
        }
        if (n < 100000) {
            return convertN(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convertN(n % 1000);
        }
        if (n < 10000000) {
            return convertN(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convertN(n % 100000);
        }
        return convertN(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convertN(n % 10000000);
    }

    /**
     * This method gives words in decimal format (includes precision upto paisa)
     * @param number
     * @return String
     */

    public String convert(final double number) {
        int net = (int) number;
        String value = String.valueOf(number);
        String identifier = value.substring(value.indexOf('.') + 1, value.length());
        int part = Integer.parseInt(identifier);
        String words = convert(net);
        if (part > 0) {
            words += " and ";
            words += convert(part);
            words += " Paisa ";
        }
        return words;
    }
}
