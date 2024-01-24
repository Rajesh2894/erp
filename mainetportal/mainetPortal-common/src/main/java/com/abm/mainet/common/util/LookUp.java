package com.abm.mainet.common.util;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Transient;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * This class use for to set and get all look up related data for application.
 * @author Pranit.Mhatre
 */
public class LookUp implements Serializable, Comparable<LookUp> {
    private static final long serialVersionUID = -3818986783455402310L;

    private long lookUpId;
    private String lookUpCode;
    private String lookUpDesc;
    private String lookUpType;

    private long lookUpParentId;
    private String descLangFirst;
    private String descLangSecond;
    private String defaultVal = MainetConstants.Common_Constant.NO;
    @Transient
    private String extraStringField1;
    private String docDescription;
    

    /**
     * Extra field which represent other than main data of the look up.
     */
    private String otherField;

    @Transient
    private long lookUpExtraLongOne;

    @Transient
    private long lookUpExtraLongTwo;
    
    @Transient
    private String extraStringField2;
    
    private String dmsDocId;
    

    public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

	public LookUp() {
    }

    public LookUp(final String lookUpCode) {
        this.lookUpCode = lookUpCode;
    }

    public LookUp(final long id, final String description) {
        lookUpId = id;
        descLangFirst = descLangSecond = description;
    }

    public LookUp(final String lookUpCode, final String description) {
        this.lookUpCode = lookUpCode;
        descLangFirst = descLangSecond = description;
    }

    public long getLookUpId() {
        return lookUpId;
    }

    public void setLookUpId(final long lookUpId) {
        this.lookUpId = lookUpId;
    }

    public String getLookUpCode() {
        return lookUpCode;
    }

    public void setLookUpCode(final String lookUpCode) {
        this.lookUpCode = lookUpCode;
    }

    public String getExtraStringField1() {
        return extraStringField1;
    }

    public void setExtraStringField1(final String extraStringField1) {
        this.extraStringField1 = extraStringField1;
    }

    public String getLookUpDesc() {
        if (UserSession.getCurrent() != null) {
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.MARATHI) {
                lookUpDesc = descLangSecond;
            } else if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                lookUpDesc = descLangFirst;
            }
        } else {
            lookUpDesc = descLangSecond;
        }

        return lookUpDesc;
    }

    public void setLookUpDesc(final String lookUpDesc) {
        this.lookUpDesc = lookUpDesc;
    }

    public String getLookUpType() {
        return lookUpType;
    }

    public void setLookUpType(final String lookUpType) {
        this.lookUpType = lookUpType;
    }

    public long getLookUpParentId() {
        return lookUpParentId;
    }

    public void setLookUpParentId(final long lookUpParentId) {
        this.lookUpParentId = lookUpParentId;
    }

    public String getDescLangFirst() {
        return descLangFirst;
    }

    public void setDescLangFirst(final String descLangFirst) {
        this.descLangFirst = descLangFirst;
    }

    public String getDescLangSecond() {
        return descLangSecond;
    }

    public void setDescLangSecond(final String descLangSecond) {
        this.descLangSecond = descLangSecond;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(final String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(final String otherField) {
        this.otherField = otherField;
    }

    @Override
    public LookUp clone() {
        try {
            return (LookUp) super.clone();
        } catch (final Exception ex) {
            throw new FrameworkException("Unable to create clone because of following exception ", ex);
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((defaultVal == null) ? 0 : defaultVal.hashCode());
        result = (prime * result) + ((descLangFirst == null) ? 0 : descLangFirst.hashCode());
        result = (prime * result) + ((descLangSecond == null) ? 0 : descLangSecond.hashCode());
        result = (prime * result) + ((lookUpCode == null) ? 0 : lookUpCode.hashCode());
        result = (prime * result) + ((lookUpDesc == null) ? 0 : lookUpDesc.hashCode());
        result = (prime * result) + (int) (lookUpId ^ (lookUpId >>> 32));
        result = (prime * result) + (int) (lookUpParentId ^ (lookUpParentId >>> 32));
        result = (prime * result) + ((lookUpType == null) ? 0 : lookUpType.hashCode());
        result = (prime * result) + ((otherField == null) ? 0 : otherField.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LookUp other = (LookUp) obj;
        if (defaultVal == null) {
            if (other.defaultVal != null) {
                return false;
            }
        } else if (!defaultVal.equals(other.defaultVal)) {
            return false;
        }
        if (descLangFirst == null) {
            if (other.descLangFirst != null) {
                return false;
            }
        } else if (!descLangFirst.equals(other.descLangFirst)) {
            return false;
        }
        if (descLangSecond == null) {
            if (other.descLangSecond != null) {
                return false;
            }
        } else if (!descLangSecond.equals(other.descLangSecond)) {
            return false;
        }
        if (lookUpCode == null) {
            if (other.lookUpCode != null) {
                return false;
            }
        } else if (!lookUpCode.equals(other.lookUpCode)) {
            return false;
        }
        if (lookUpDesc == null) {
            if (other.lookUpDesc != null) {
                return false;
            }
        } else if (!lookUpDesc.equals(other.lookUpDesc)) {
            return false;
        }
        if (lookUpId != other.lookUpId) {
            return false;
        }
        if (lookUpParentId != other.lookUpParentId) {
            return false;
        }
        if (lookUpType == null) {
            if (other.lookUpType != null) {
                return false;
            }
        } else if (!lookUpType.equals(other.lookUpType)) {
            return false;
        }
        if (otherField == null) {
            if (other.otherField != null) {
                return false;
            }
        } else if (!otherField.equals(other.otherField)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("LookUp [lookUpId=");
        builder.append(lookUpId);
        builder.append(", lookUpCode=");
        builder.append(lookUpCode);
        builder.append(", lookUpDesc=");
        builder.append(lookUpDesc);
        builder.append(", lookUpType=");
        builder.append(lookUpType);
        builder.append(", lookUpParentId=");
        builder.append(lookUpParentId);
        builder.append(", descLangFirst=");
        builder.append(descLangFirst);
        builder.append(", descLangSecond=");
        builder.append(descLangSecond);
        builder.append(", defaultVal=");
        builder.append(defaultVal);
        builder.append(", otherField=");
        builder.append(otherField);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

    public long getLookUpExtraLongOne() {
        return lookUpExtraLongOne;
    }

    public void setLookUpExtraLongOne(final long lookUpExtraLongOne) {
        this.lookUpExtraLongOne = lookUpExtraLongOne;
    }

    public long getLookUpExtraLongTwo() {
        return lookUpExtraLongTwo;
    }

    public void setLookUpExtraLongTwo(final long lookUpExtraLongTwo) {
        this.lookUpExtraLongTwo = lookUpExtraLongTwo;
    }

    @Override
    public int compareTo(final LookUp lookup) {
        return getLookUpDesc().compareToIgnoreCase(lookup.getLookUpDesc());
    }

    private static boolean isDigit(final char ch) {
        return (ch >= 48) && (ch <= 57);
    }

    /** Length of string is passed in for improved efficiency (only need to calculate it once) **/
    private static String getChunk(final String s, final int slength, int marker) {
        final StringBuilder chunk = new StringBuilder();
        char c = s.charAt(marker);
        chunk.append(c);
        marker++;
        if (isDigit(c)) {
            while (marker < slength) {
                c = s.charAt(marker);
                if (!isDigit(c)) {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        } else {
            while (marker < slength) {
                c = s.charAt(marker);
                if (isDigit(c)) {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        }
        return chunk.toString();
    }

    public static Comparator<LookUp> alphanumericComparator = (lookUp1, lookUp2) -> {

        final String s1 = null != lookUp1.getLookUpDesc() ? lookUp1.getLookUpDesc().toUpperCase() : MainetConstants.BLANK;
        final String s2 = null != lookUp2.getLookUpDesc() ? lookUp2.getLookUpDesc().toUpperCase() : MainetConstants.BLANK;

        int thisMarker = 0;
        int thatMarker = 0;
        final int s1Length = s1.length();
        final int s2Length = s2.length();

        while ((thisMarker < s1Length) && (thatMarker < s2Length)) {
            final String thisChunk = getChunk(s1, s1Length, thisMarker); // ward //2
            thisMarker += thisChunk.length();  // 5 //6

            final String thatChunk = getChunk(s2, s2Length, thatMarker); // ward //1
            thatMarker += thatChunk.length();  // 5 //6

            // If both chunks contain numeric characters, sort them numerically
            int result = 0;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                // Simple chunk comparison by length.
                final int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                // If equal, the first different number counts
                if (result == 0) {
                    for (int i = 0; i < thisChunkLength; i++) {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                result = thisChunk.compareToIgnoreCase(thatChunk);
                result = thisChunk.trim().compareTo(thatChunk.trim());
            }

            if (result != 0) {
                return result;
            }
        }
        return s1Length - s2Length;
    };

	public String getExtraStringField2() {
		return extraStringField2;
	}

	public void setExtraStringField2(String extraStringField2) {
		this.extraStringField2 = extraStringField2;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

}
