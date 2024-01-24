/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Prasant.Sahu
 *
 */
public class AccountFunctionDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer level;

    private String parentLevel;
    private String parentCode;
    private String parentDesc;
    private String parentFinalCode;
    private Long childLevelCode;

    private String childLevel;
    private String childParentLevel;
    private String childParentCode;
    private String childCode;
    private String childDesc;
    private String childFinalCode;
    private Long childFunctionStatus;

    private Long childFunId;

    private Integer uniqueNummber;

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
     * @return the childFunId
     */
    public Long getChildFunId() {
        return childFunId;
    }

    /**
     * @param childFunId the childFunId to set
     */
    public void setChildFunId(final Long childFunId) {
        this.childFunId = childFunId;
    }

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

    public static Comparator<AccountFunctionDto> functionLevelComparator = (field1, field2) -> {

        final Integer level1 = Integer.valueOf(field1.getChildLevel().toString());
        final Integer level2 = Integer.valueOf(field2.getChildLevel().toString());

        if (!level1.equals(level2)) {
            return level1 - level2;
        }
        final String fieldCode = field1.getChildCode();
        final String fieldCode2 = field2.getChildCode();

        // ascending order
        return fieldCode.compareTo(fieldCode2);

    };

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
        final AccountFunctionDto other = (AccountFunctionDto) obj;
        if (childCode == null) {
            if (other.childCode != null) {
                return false;
            }
        } else if (!childCode.equals(other.childCode)) {
            return false;
        }
        if (childLevel == null) {
            if (other.childLevel != null) {
                return false;
            }
        } else if (!childLevel.equals(other.childLevel)) {
            return false;
        }
        return true;
    }

    /**
     * @return the childFunctionStatus
     */
    public Long getChildFunctionStatus() {
        return childFunctionStatus;
    }

    /**
     * @param childFunctionStatus the childFunctionStatus to set
     */
    public void setChildFunctionStatus(final Long childFunctionStatus) {
        this.childFunctionStatus = childFunctionStatus;
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

}
