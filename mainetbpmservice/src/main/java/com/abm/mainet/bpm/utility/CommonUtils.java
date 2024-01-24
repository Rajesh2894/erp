package com.abm.mainet.bpm.utility;

/**
 * Common Utilities
 * 
 * @author sanket.joshi
 *
 */
public class CommonUtils {

    /**
     * <p>
     * Checks if the specified name is a valid enum for the class.
     * </p>
     *
     * <p>
     * This method differs from {@link Enum#valueOf} in that checks if the name is a valid enum without needing to catch the
     * exception.
     * </p>
     *
     * @param <E> the type of the enumeration
     * @param enumClass the class of the enum to query, not null
     * @param enumName the enum name, null returns false
     * @return true if the enum name is valid, otherwise false
     */
    public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
        if (enumName == null) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, enumName);
            return true;
        } catch (final IllegalArgumentException ex) {
            return false;
        }
    }

}
