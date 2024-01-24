/*
 * filepaths.java
 *
 * Created on December 23, 2005, 11:43 AM
 */

package com.abm.mainet.common.ui.model;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Umesh.Gokhale
 * @author Pranit.Mhatre
 * @author Swapnil.shirke
 */
public class Filepaths {
    private static String file = MainetConstants.BLANK;

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
