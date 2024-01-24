package com.abm.mainet.common.util;

import com.abm.mainet.common.constant.MainetConstants;

public class NumberToWord {

    private static final String TWENTY = "twenty";
    private static final String THIRTY = "thirty";
    private static final String FOURTY = "fourty";
    private static final String FIFTY = "fifty";
    private static final String SIXTY = "sixty";
    private static final String SEVENTY = "seventy";
    private static final String EIGHTY = "eighty";
    private static final String NINETY = "ninety";
    private static final String TEN = "ten";
    private static final String ELEVEN = "eleven";
    private static final String TWELVE = "twelve";
    private static final String THIRTEEN = "thirteen";
    private static final String FOURTEEN = "fourteen";
    private static final String FIFTEEN = "fifteen";
    private static final String SIXTEEN = "sixteen";
    private static final String SEVENTEEN = "seventeen";
    private static final String EIGHTEEN = "eighteen";
    private static final String NINTEEN = "ninteen";
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";
    private static final String LAKH = "lakh";
    private static final String CRORE = "crore";
    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";
    private static final String FOUR = "four";
    private static final String FIVE = "five";
    private static final String SIX = "six";
    private static final String SEVEN = "seven";
    private static final String EIGHT = "eight";
    private static final String NINE = "nine";

    private String string;
    private final String st1[] = { MainetConstants.BLANK, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
            EIGHT, NINE, };
    private final String st2[] = { HUNDRED, THOUSAND, LAKH, CRORE };
    private final String st3[] = { TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN,
            FIFTEEN, SIXTEEN, SEVENTEEN, EIGHTEEN, NINTEEN, };
    private final String st4[] = { TWENTY, THIRTY, FOURTY, FIFTY, SIXTY, SEVENTY,
            EIGHTY, NINETY };

    public String convert(int number) {
        int n = 1;
        int word;
        string = MainetConstants.BLANK;
        while (number != 0) {
            switch (n) {
            case 1:
                word = number % 100;
                pass(word);
                if ((number > 100) && ((number % 100) != 0)) {
                    show(MainetConstants.WHITE_SPACE);
                }
                number /= 100;
                break;

            case 2:
                word = number % 10;
                if (word != 0) {
                    show(MainetConstants.WHITE_SPACE);
                    show(st2[0]);
                    show(MainetConstants.WHITE_SPACE);
                    pass(word);
                }
                number /= 10;
                break;

            case 3:
                word = number % 100;
                if (word != 0) {
                    show(MainetConstants.WHITE_SPACE);
                    show(st2[1]);
                    show(MainetConstants.WHITE_SPACE);
                    pass(word);
                }
                number /= 100;
                break;

            case 4:
                word = number % 100;
                if (word != 0) {
                    show(MainetConstants.WHITE_SPACE);
                    show(st2[2]);
                    show(MainetConstants.WHITE_SPACE);
                    pass(word);
                }
                number /= 100;
                break;

            case 5:
                word = number % 100;
                if (word != 0) {
                    show(MainetConstants.WHITE_SPACE);
                    show(st2[3]);
                    show(MainetConstants.WHITE_SPACE);
                    pass(word);
                }
                number /= 100;
                break;

            }
            n++;
        }
        return string;
    }

    private void pass(final int number) {
        int word, q;
        if (number < 10) {
            show(st1[number]);
        }
        if ((number > 9) && (number < 20)) {
            show(st3[number - 10]);
        }
        if (number > 19) {
            word = number % 10;
            if (word == 0) {
                q = number / 10;
                show(st4[q - 2]);
            } else {
                q = number / 10;
                show(st1[word]);
                show(MainetConstants.operator.HIPHEN);
                show(st4[q - 2]);
            }
        }
    }

    private void show(final String s) {
        String st;
        st = string;
        string = s;
        string += st;
    }

}