package com.abm.mainet.common.util;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Pranit.Mhatre
 */
public final class StringUtility implements Serializable {

    private static final String TIFF = "TIFF";
    private static final String TGZ = "TGZ";
    private static final String TGA = "TGA";
    private static final String SQL = "SQL";
    private static final String QT = "QT";
    private static final String PSD = "PSD";
    private static final String JS = "JS";
    private static final String JAVA = "JAVA";
    private static final String DOTX = "DOTX";
    private static final String XLS = "XLS";
    private static final String XLSX = "XLSX";
    private static final String PPTX = "PPTX";
    private static final String PNG = "PNG";
    private static final String BMP = "BMP";
    private static final String GIF = "GIF";
    private static final String XPS = "XPS";
    private static final String XML = "XML";
    private static final String XHTML = "XHTML";
    private static final String TXT = "TXT";
    private static final String RTF = "RTF";
    private static final String ZIP = "ZIP";
    private static final String RAR = "RAR";
    private static final String JAR = "JAR";
    private static final String JPEG = "JPEG";
    private static final String JPG = "JPG";
    private static final String DOCX = "DOCX";
    private static final String DOC = "DOC";
    private static final String PPT = "PPT";
    private static final String HTML = "HTML";
    private static final String PDF = "PDF";
    private static final String ICON_DEFAULT = "icon_default";
    private static final long serialVersionUID = 3873955235984194177L;

    public StringUtility() {

    }

    private String removeDotPrefix(final String singleDotString) {
        final int firstPosition = singleDotString.indexOf(MainetConstants.operator.DOT);

        return singleDotString.substring(firstPosition + 1, singleDotString.length());

    }

    public String replaceAllDotPrefix(final String multipleDotString) {
        final int firstPosition = multipleDotString.indexOf(MainetConstants.operator.DOT);
        final int lastPosition = multipleDotString.lastIndexOf(MainetConstants.operator.DOT);

        if (firstPosition == lastPosition) {
            return removeDotPrefix(multipleDotString);
        } else {
            String s = multipleDotString.substring(lastPosition + 1, multipleDotString.length());

            s = s.replace(MainetConstants.operator.RIGHT_SQUARE_BRACKET, MainetConstants.BLANK);
            s = s.replace(MainetConstants.operator.LEFT_SQUARE_BRACKET, MainetConstants.BLANK);
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

        final String fileSuffix = getStringAfterChar(MainetConstants.operator.DOT, file);
        final String[] fileExt = { PDF, HTML, PPT, DOC, DOCX, JPG, JPEG, JAR, RAR, ZIP, RTF,
                TXT, XHTML, XML, XPS, GIF, BMP, PNG, PPTX, XLSX, XLS, DOTX,
                JAVA, JS, PSD, QT, SQL, TGA, TGZ,
                TIFF
        };

        for (final String eachString : fileExt) {

            if (fileSuffix.equalsIgnoreCase(eachString)) {

                return "icon_" + fileSuffix.toLowerCase();
            }

        }

        if (fileSuffix == MainetConstants.BLANK) {
            return MainetConstants.BLANK;
        }

        return ICON_DEFAULT;

    }

    // to get File Name :prefix
    public String getFileName(final String file) {

        final String filePrefix = getStringBeforeChar(MainetConstants.operator.DOT, file);
        if (filePrefix == MainetConstants.BLANK) {
            return file;
        }
        return filePrefix;

    }

    public static String getStringForUpload(final String s) {

        final int i = s.indexOf(MainetConstants.operator.LEFT_SQUARE_BRACKET) - 1;

        final StringBuffer buffer = new StringBuffer(s);

        return buffer.insert(i, MainetConstants.operator.RIGHT_SQUARE_BRACKET).toString();

    }

    public String getStringAfterChars(final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(File.separator);

        return sourceString.substring(lastIndex + 1, sourceString.length());
    }
    
    public List<String> splitByComma(final String string){
    	List<String> list = Arrays.asList(string.split(","));
		return list;
    }

}
