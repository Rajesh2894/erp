/**
 *
 */
package com.abm.mainet.common.integration.dms.mapper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Vikrant.Thakur
 * @since 4 October 2014. This class is used for validation and Utility required for File Upload.
 *
 */
public class FileUploadValidator {

    /**
     * Used to Validate File extension
     *
     * @param fileName : file name
     * @param fileNameFormSet : validtion constant name which is to be read from FileUploadConstant class
     * @return true if validation error didn't arise alse false
     */
    public static boolean applyFileExtensionValidation(final String fileName, final String fileNameFormSet) {
        if ((fileNameFormSet != null) && (fileNameFormSet.equals(MainetConstants.operator.EMPTY))) {
            return true;
        } else {
            try {
                final Field field = MainetConstants.Validation_Constant.class.getField(fileNameFormSet);

                final String[] fieldValue = (String[]) field.get(MainetConstants.Validation_Constant.class);

                final String extension = getPrefixSuffixFromString(fileName, MainetConstants.operator.DOT,
                        MainetConstants.OperationMode.SUFFIX, MainetConstants.OperationMode.END);

                for (final String name : fieldValue) {
                    if (extension.equalsIgnoreCase(name)) {
                        return true;
                    }

                }
            } catch (final Exception e) {
            }

            return false;
        }

    }

    /**
     * Use to Validate File Size Validation
     *
     * @param fileSize : current file Size
     * @param maxFileSize : maximum file Size.
     * @return true if validation error didn't arise alse false
     */
    public static boolean applyFileSizeValidation(final int fileSize, final int maxFileSize) {
        if (maxFileSize != 0) {
            if (fileSize > maxFileSize) {
                return false;
            }
        }

        return true;

    }

    /**
     * Used to check file exists in current fileUpload tag
     *
     * @param fileDetails : set of file details
     * @param fileName : newly added file name
     * @return true if validation error didn't arise alse false
     */
    public static boolean applyFileExistsValidation(final Set<File> fileDetails, final String fileName) {
        final Iterator<File> iterator = fileDetails.iterator();

        while (iterator.hasNext()) {
            final File file = iterator.next();

            if (file.getName().equalsIgnoreCase(fileName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Used to maximum number of file to be upload in current file Upload tag.
     *
     * @param fileDetails : set of file
     * @param maxFilecount : current file count
     * @return true if validation error didn't arise alse false
     */
    public static boolean applyFileMaxCountValidation(final Set<File> fileDetails, final int maxFilecount) {
        if (maxFilecount != 0) {
            if (fileDetails.size() >= maxFilecount) {
                return false;
            }
        }

        return true;
    }

    // Added below method for U#90800

    /**
     * Used to check if files uploaded in current tag added is less than maximum number of files allowed
     *
     * @param fileDetails : set of files
     * @param requestFileCount : current file count in request
     * @param maxFilecount : Max allowed file count
     * @return true if validation error doesn't arise else false
     */
    public static boolean applyMultipleFileMaxCountValidation(Set<File> fileDetails, int requestFileCount,
            int maxFileCount) {
        if (maxFileCount != 0 && requestFileCount != 0) {
            if ((fileDetails.size() + requestFileCount) > maxFileCount) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param Code of filetag
     * @return id of filetag
     */
    public static Long getFileIDFromCode(final String name) {
        return Long.parseLong(name.substring(name.indexOf(MainetConstants.operator.UNDER_SCORE) + 1));
    }

    /**
     * This is genertic mathod used to split the String from any side depends on required parameters
     *
     * @param name : String name (File name)
     * @param delimiter : file seperator delimiter.
     * @param prefixSuffix : Scan the string from Prefix or Suffix
     * @param scanningStartFrom : After Sacnning String Divide that String form Start or End.
     * @return new String which is generated.
     *
     */
    public static String getPrefixSuffixFromString(String name, final String delimiter, final String prefixSuffix,
            final String scanningStartFrom) {
        int pocn;

        if (scanningStartFrom.equals(MainetConstants.OperationMode.START)) {
            pocn = name.indexOf(delimiter);
        } else {
            pocn = name.lastIndexOf(delimiter);
        }

        if (prefixSuffix.equals(MainetConstants.OperationMode.PREFIX)) {
            name = name.substring(0, pocn);
        } else {
            name = name.substring(pocn + 1);
        }

        return name;
    }

    /**
     * Used to get Labels from Properties file
     *
     * @return Value from Propeties file
     */
    public static String getFieldLabel(final String field) {
        return ApplicationSession.getInstance()
                .getMessage(MainetConstants.ValidationMessageCode.CLASS_NAME + MainetConstants.operator.DOT + field);
    }

    public static String getFieldLabel(final String field, final String param1[]) {
        return ApplicationSession.getInstance().getMessage(
                MainetConstants.ValidationMessageCode.CLASS_NAME + MainetConstants.operator.DOT + field,
                new Object[] { param1 });
    }

    public static String getFieldLabel(final String field, final String param1) {
        return ApplicationSession.getInstance().getMessage(
                MainetConstants.ValidationMessageCode.CLASS_NAME + MainetConstants.operator.DOT + field,
                new Object[] { param1 });
    }

    private static List<LookUp> getLookUps(final String lookUpCode, final Organisation organisation) {
        return ApplicationSession.getInstance().getHierarchicalLookUp(organisation, lookUpCode).get(lookUpCode);
    }

    public static List<DepartmentLookUp> getDepartmentLookUp() {
        return ApplicationSession.getInstance().getDepartments(UserSession.getCurrent().getOrganisation());
    }

    public static Long getValueFromPrefix(final String value, final String prefix) {
        final Iterator<LookUp> lookup = getLookUps(prefix, UserSession.getCurrent().getOrganisation()).iterator();

        while (lookup.hasNext()) {

            final LookUp lookUp2 = lookup.next();
            if (lookUp2.getLookUpCode().equals(value)) {
                return lookUp2.getLookUpId();
            }
        }

        return null;
    }
}
