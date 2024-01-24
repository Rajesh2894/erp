package com.abm.mainet.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PORTAL_PFIX_MAPPING_MASTER")
public class PortalPrifixMappingMaster {
    @Id
    @Column(name = "pfm_id", precision = 12, scale = 0, nullable = false)
    private long id;

    @Column(name = "prefix_type")
    private String prefixType;

    @Column(name = "portal_prifix")
    private Long portalPrefix;

    @Column(name = "kdmc_prifix")
    private Long kdmcPrefix;

    @Column(name = "marriage_prifix")
    private Long marriagePrefix;

    public String getPrefixType() {
        return prefixType;
    }

    public void setPrefixType(final String prefixType) {
        this.prefixType = prefixType;
    }

    public Long getPortalPrefix() {
        return portalPrefix;
    }

    public void setPortalPrefix(final Long portalPrefix) {
        this.portalPrefix = portalPrefix;
    }

    public Long getKdmcPrefix() {
        return kdmcPrefix;
    }

    public void setKdmcPrefix(final Long kdmcPrefix) {
        this.kdmcPrefix = kdmcPrefix;
    }

    public Long getMarriagePrefix() {
        return marriagePrefix;
    }

    public void setMarriagePrefix(final Long marriagePrefix) {
        this.marriagePrefix = marriagePrefix;
    }

}
