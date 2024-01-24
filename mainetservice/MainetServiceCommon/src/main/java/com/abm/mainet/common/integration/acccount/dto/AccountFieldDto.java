/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author tejas.kotekar
 *
 */
public class AccountFieldDto implements Serializable {

    private static final long serialVersionUID = -1677851844038384367L;

    private Integer level;
    private String parentLevel;
    private String parentCode;
    private String parentDesc;
    private String parentFinalCode;

    private String childLevel;
    private Long childLevelCode;
    private String childParentLevel;
    private Long childParentLevelCode;
    private String childParentCode;
    private String childCode;
    private String childDesc;
    private String childFinalCode;
    private Long childFieldStatus;
    private Long hiddenChildFieldStatus;

    private Long childFieldId;
    private Integer uniqueNummber;

    /**
     * @return the uniqueNummber
     */
    public Integer getUniqueNummber() {
        return uniqueNummber;
    }

    /**
     * @param uniqueNummber the uniqueNummber to set
     */
    public void setUniqueNummber(final Integer uniqueNummber) {
        this.uniqueNummber = uniqueNummber;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(final Integer level) {
        this.level = level;
    }

    /**
     * @return the parentLevel
     */
    public String getParentLevel() {
        return parentLevel;
    }

    /**
     * @param parentLevel the parentLevel to set
     */
    public void setParentLevel(final String parentLevel) {
        this.parentLevel = parentLevel;
    }

    /**
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode the parentCode to set
     */
    public void setParentCode(final String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * @return the parentDesc
     */
    public String getParentDesc() {
        return parentDesc;
    }

    /**
     * @param parentDesc the parentDesc to set
     */
    public void setParentDesc(final String parentDesc) {
        this.parentDesc = parentDesc;
    }

    /**
     * @return the parentFinalCode
     */
    public String getParentFinalCode() {
        return parentFinalCode;
    }

    /**
     * @param parentFinalCode the parentFinalCode to set
     */
    public void setParentFinalCode(final String parentFinalCode) {
        this.parentFinalCode = parentFinalCode;
    }

    /**
     * @return the childLevel
     */
    public String getChildLevel() {
        return childLevel;
    }

    /**
     * @param childLevel the childLevel to set
     */
    public void setChildLevel(final String childLevel) {
        this.childLevel = childLevel;
    }

    /**
     * @return the childParentLevel
     */
    public String getChildParentLevel() {
        return childParentLevel;
    }

    /**
     * @param childParentLevel the childParentLevel to set
     */
    public void setChildParentLevel(final String childParentLevel) {
        this.childParentLevel = childParentLevel;
    }

    /**
     * @return the childParentCode
     */
    public String getChildParentCode() {
        return childParentCode;
    }

    /**
     * @param childParentCode the childParentCode to set
     */
    public void setChildParentCode(final String childParentCode) {
        this.childParentCode = childParentCode;
    }

    /**
     * @return the childCode
     */
    public String getChildCode() {
        return childCode;
    }

    /**
     * @param childCode the childCode to set
     */
    public void setChildCode(final String childCode) {
        this.childCode = childCode;
    }

    /**
     * @return the childDesc
     */
    public String getChildDesc() {
        return childDesc;
    }

    /**
     * @param childDesc the childDesc to set
     */
    public void setChildDesc(final String childDesc) {
        this.childDesc = childDesc;
    }

    /**
     * @return the childFinalCode
     */
    public String getChildFinalCode() {
        return childFinalCode;
    }

    /**
     * @param childFinalCode the childFinalCode to set
     */
    public void setChildFinalCode(final String childFinalCode) {
        this.childFinalCode = childFinalCode;
    }

    /**
     * @return the childFieldId
     */
    public Long getChildFieldId() {
        return childFieldId;
    }

    /**
     * @param childFieldId the childFieldId to set
     */
    public void setChildFieldId(final Long childFieldId) {
        this.childFieldId = childFieldId;
    }

    /**
     * @return the childLevelCode
     */
    public Long getChildLevelCode() {
        return childLevelCode;
    }

    /**
     * @param childLevelCode the childLevelCode to set
     */
    public void setChildLevelCode(final Long childLevelCode) {
        this.childLevelCode = childLevelCode;
    }

    /**
     * @return the childParentLevelCode
     */
    public Long getChildParentLevelCode() {
        return childParentLevelCode;
    }

    /**
     * @param childParentLevelCode the childParentLevelCode to set
     */
    public void setChildParentLevelCode(final Long childParentLevelCode) {
        this.childParentLevelCode = childParentLevelCode;
    }

    /**
     * @return the childFieldStatus
     */
    public Long getChildFieldStatus() {
        return childFieldStatus;
    }

    /**
     * @param childFieldStatus the childFieldStatus to set
     */
    public void setChildFieldStatus(final Long childFieldStatus) {
        this.childFieldStatus = childFieldStatus;
    }

    public static Comparator<AccountFieldDto> fieldLevelComparator = (field1, field2) -> {

        final Integer level1 = Integer.valueOf(field1.getChildLevelCode().toString());
        final Integer level2 = Integer.valueOf(field2.getChildLevelCode().toString());

        if (!level1.equals(level2)) {
            return level1 - level2;
        }
        final String fieldCode = field1.getChildCode();
        final String fieldCode2 = field2.getChildCode();

        // ascending order
        return fieldCode.compareTo(fieldCode2);

    };

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /*
     * @Override public int hashCode() { final int prime = 31; int result = 1; result = prime * result + ((childCode == null) ? 0
     * : childCode.hashCode()); result = prime * result + ((childLevelCode == null) ? 0 : childLevelCode.hashCode()); return
     * result; }
     */

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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountFieldDto other = (AccountFieldDto) obj;
        if (childCode == null) {
            if (other.childCode != null) {
                return false;
            }
        } else if (!childCode.equals(other.childCode)) {
            return false;
        }
        if (childLevelCode == null) {
            if (other.childLevelCode != null) {
                return false;
            }
        } else if (!childLevelCode.equals(other.childLevelCode)) {
            return false;
        }
        return true;
    }

    /**
     * @return the hiddenChildFieldStatus
     */
    public Long getHiddenChildFieldStatus() {
        return hiddenChildFieldStatus;
    }

    /**
     * @param hiddenChildFieldStatus the hiddenChildFieldStatus to set
     */
    public void setHiddenChildFieldStatus(final Long hiddenChildFieldStatus) {
        this.hiddenChildFieldStatus = hiddenChildFieldStatus;
    }

}
