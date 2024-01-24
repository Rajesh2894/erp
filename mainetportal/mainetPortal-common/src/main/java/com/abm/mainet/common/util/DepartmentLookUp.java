package com.abm.mainet.common.util;

import java.io.Serializable;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Pranit.Mhatre
 * @since 06 February, 2013
 */
public final class DepartmentLookUp implements Serializable, Comparable<DepartmentLookUp> {

    private static final long serialVersionUID = 7797406158968413414L;

    private long lookUpId;
    private String lookUpCode;
    private String prefixVal;
    private String lookUpDesc;
    /**
     * Secondary language for e.g. English
     */
    private String descLangFirst;
    /**
     * Regional language
     */
    private String descLangSecond;
    private String defaultVal = MainetConstants.Common_Constant.NO;

    public DepartmentLookUp() {
        super();
    }

    /**
     * @return the lookUpId
     */
    public long getLookUpId() {
        return lookUpId;
    }

    /**
     * @param lookUpId the lookUpId to set
     */
    public void setLookUpId(final long lookUpId) {
        this.lookUpId = lookUpId;
    }

    /**
     * @return the lookUpCode
     */
    public String getLookUpCode() {
        return lookUpCode;
    }

    /**
     * @param lookUpCode the lookUpCode to set
     */
    public void setLookUpCode(final String lookUpCode) {
        this.lookUpCode = lookUpCode;
    }

    /**
     * @return the prefixVal
     */
    public String getPrefixVal() {
        return prefixVal;
    }

    /**
     * @param prefixVal the prefixVal to set
     */
    public void setPrefixVal(final String prefixVal) {
        this.prefixVal = prefixVal;
    }

    /**
     * @return the lookUpDesc
     */
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

    /**
     * @param lookUpDesc the lookUpDesc to set
     */
    public void setLookUpDesc(final String lookUpDesc) {
        this.lookUpDesc = lookUpDesc;
    }

    /**
     * @return the descLangFirst
     */
    public String getDescLangFirst() {
        return descLangFirst;
    }

    /**
     * @param descLangFirst the descLangFirst to set
     */
    public void setDescLangFirst(final String descLangFirst) {
        this.descLangFirst = descLangFirst;
    }

    /**
     * @return the descLangSecond
     */
    public String getDescLangSecond() {
        return descLangSecond;
    }

    /**
     * @param descLangSecond the descLangSecond to set
     */
    public void setDescLangSecond(final String descLangSecond) {
        this.descLangSecond = descLangSecond;
    }

    /**
     * @return the defaultVal
     */
    public String getDefaultVal() {
        return defaultVal;
    }

    /**
     * @param defaultVal the defaultVal to set
     */
    public void setDefaultVal(final String defaultVal) {
        this.defaultVal = defaultVal;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
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
        result = (prime * result) + ((prefixVal == null) ? 0 : prefixVal.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DepartmentLookUp)) {
            return false;
        }
        final DepartmentLookUp other = (DepartmentLookUp) obj;
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
        if (prefixVal == null) {
            if (other.prefixVal != null) {
                return false;
            }
        } else if (!prefixVal.equals(other.prefixVal)) {
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DepartmentLookUp [lookUpId=");
        builder.append(lookUpId);
        builder.append(", lookUpCode=");
        builder.append(lookUpCode);
        builder.append(", prefixVal=");
        builder.append(prefixVal);
        builder.append(", lookUpDesc=");
        builder.append(lookUpDesc);
        builder.append(", descLangFirst=");
        builder.append(descLangFirst);
        builder.append(", descLangSecond=");
        builder.append(descLangSecond);
        builder.append(", shortName=");
        builder.append(", defaultVal=");
        builder.append(defaultVal);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

    @Override
    public int compareTo(final DepartmentLookUp lookup) {

        int compareResult = 0;
        if ((lookup.getLookUpDesc() != null) && (getLookUpDesc() != null)) {
            compareResult = getLookUpDesc().compareToIgnoreCase(lookup.getLookUpDesc());
        }

        // *********************to fullfill the contrect of compareTo Methof in case of junk data*************************
        if ((compareResult == -1) || (compareResult == 0) || (compareResult == 1)) {
            return compareResult;
        }
        return 0;
    }

}
