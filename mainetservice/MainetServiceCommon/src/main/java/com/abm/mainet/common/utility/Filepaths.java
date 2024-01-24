package com.abm.mainet.common.utility;

/**
 * @author Umesh.Gokhale
 * @author Pranit.Mhatre
 * @author Swapnil.shirke
 */
public class Filepaths {
    private static String file = "";

    /** Creates a new instance of filepath */
    public Filepaths() {

    }

    public static void setfilepath(final String filep) {

        file = filep;
    }

    public static String getfilepath() {
        return file;
    }
}
