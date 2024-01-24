package com.abm.mainet.common.utility;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Pranit.Mhatre
 */
public final class StringUtility implements Serializable {

    
    private static final long serialVersionUID = 3873955235984194177L;

    public StringUtility() {

    }

    public String removeDotPrefix(final String singleDotString) {
        final int firstPosition = singleDotString.indexOf(".");

        return singleDotString.substring(firstPosition + 1, singleDotString.length());

    }

    public String replaceAllDotPrefix(final String multipleDotString) {
        final int firstPosition = multipleDotString.indexOf(".");
        final int lastPosition = multipleDotString.lastIndexOf(".");

        if (firstPosition == lastPosition) {
            return removeDotPrefix(multipleDotString);
        } else {
            String s = multipleDotString.substring(lastPosition + 1, multipleDotString.length());

            s = s.replace("[", MainetConstants.BLANK);
            s = s.replace("]", MainetConstants.BLANK);
            return s;

        }
    }

    public String getStringAfterChar(final String afterChar, final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(afterChar);

        return sourceString.substring(lastIndex + 1, sourceString.length());
    }

    public static String getStringBeforeChar(final String beforeChar, final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(beforeChar);

        if (lastIndex <= 0) {
            return MainetConstants.BLANK;
        }

        return sourceString.substring(0, lastIndex);
    }

    public static String staticStringAfterChar(final String afterChar, final String sourceString) {
        if ((afterChar != null) && (sourceString != null)) {
            final int lastIndex = sourceString.lastIndexOf(afterChar);

            if (lastIndex <= 0) {
                return MainetConstants.BLANK;
            }

            return sourceString.substring(lastIndex + 1, sourceString.length());
        }
        return MainetConstants.BLANK;
    }

    public static String staticStringBeforeChar(final String beforeChar, final String sourceString) {
        if ((beforeChar != null) && (sourceString != null)) {
            final int lastIndex = sourceString.lastIndexOf(beforeChar);

            if (lastIndex <= 0) {
                return MainetConstants.BLANK;
            }

            return sourceString.substring(0, lastIndex);
        }
        return MainetConstants.BLANK;
    }

    public static String removeLastChar(final String lastChar, final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(lastChar);

        if (lastIndex <= 0) {
            return MainetConstants.BLANK;
        }

        return sourceString.substring(0, lastIndex);

    }

    public static String staticRemoveLastChar(final String lastChar, final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(lastChar);

        if (lastIndex <= 0) {
            return MainetConstants.BLANK;
        }

        return sourceString.substring(0, lastIndex);

    }

    // to get File type : suffix
    public String getDocType(final String file) {

        final String fileSuffix = getStringAfterChar(".", file);
        final String[] fileExt = { "PDF", "HTML", "PPT", "DOC", "DOCX", "JPG", "JPEG", "JAR", "RAR", "ZIP", "RTF",
                "TXT", "XHTML", "XML", "XPS", "GIF", "BMP", "PNG", "PPTX", "XLSX", "XLS", "DOTX",
                "JAVA", "JS", "PSD", "QT", "SQL", "TGA", "TGZ",
                "TIFF"
        };

        for (final String eachString : fileExt) {

            if (fileSuffix.equalsIgnoreCase(eachString)) {

                return "icon_" + fileSuffix.toLowerCase();
            }

        }

        if (fileSuffix == MainetConstants.BLANK) {
            return MainetConstants.BLANK;
        }

        return "icon_default";

    }

    // to get File Name :prefix
    public String getFileName(final String file) {

        final String filePrefix = getStringBeforeChar(".", file);
        if (filePrefix == MainetConstants.BLANK) {
            return file;
        }
        return filePrefix;

    }

    public static String getStringForUpload(final String s) {

        final int i = s.indexOf("]") - 1;
        final StringBuffer buffer = new StringBuffer(s);
        return buffer.insert(i, "[").toString();

    }

    public String getStringAfterChars(final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf('\\');

        return sourceString.substring(lastIndex + 1, sourceString.length());
    }

    public List<String> splitByComma(final String string) {
        final List<String> list = Arrays.asList(string.split(MainetConstants.operator.COMMA));
        return list;
    }

}
