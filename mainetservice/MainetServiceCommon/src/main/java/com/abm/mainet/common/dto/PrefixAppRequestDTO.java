package com.abm.mainet.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author vishnu.jagdale
 *
 */
@JsonFormat
public class PrefixAppRequestDTO {

    private Long orgId;
    private String lookUpCode;
    private int level;

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
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
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(final int level) {
        this.level = level;
    }

}
