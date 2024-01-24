package com.abm.mainet.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vivek.Kumar
 * @since 17 Dec 2015
 */
@Entity
@Table(name = "VW_PREFIX_DETAILS")
public class ViewPrefixDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2651841916706864975L;

    @Column(name = "CPM_ID", precision = 12, scale = 0, nullable = true)
    private Long cpmId;

    @Column(name = "CPM_PREFIX", length = 3, nullable = true)
    private String cpmPrefix;

    @Column(name = "CPM_DESC", length = 3, nullable = true)
    private String cpmDesc;

    @Column(name = "CPM_TYPE", length = 1, nullable = true)
    private String cpmType;

    @Column(name = "COM_ID", precision = 0, scale = 0, nullable = true)
    private Long comId;

    @Column(name = "COM_LEVEL", precision = 0, scale = 0, nullable = true)
    private Integer comLevel;

    @Column(name = "COM_VALUE", length = 200, nullable = true)
    private String comValue;

    @Column(name = "COM_DESC", length = 200, nullable = true)
    private String comDesc;

    @Column(name = "COM_DESC_MAR", length = 400, nullable = true)
    private String comDescMar;

    @Id
    @Column(name = "COD_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long codCpdId;

    @Column(name = "COD_CPD_VALUE", length = 200, nullable = true)
    private String codCpdValue;

    @Column(name = "CPD_OTHERS", length = 400, nullable = true)
    private String cpdOthers;

    @Column(name = "COD_CPD_DESC", length = 200, nullable = true)
    private String codCpdDesc;

    @Column(name = "COD_CPD_DESC_MAR", length = 400, nullable = true)
    private String codCpdDescMar;

    @Column(name = "COD_CPD_DEFAULT", length = 400, nullable = true)
    private String codCpdDefault;

    @Column(name = "COD_CPD_PARENT_ID", precision = 0, scale = 0, nullable = true)
    private Long codCpdParentId;

    @Column(name = "ORGID", precision = 0, scale = 0, nullable = true)
    private Long orgid;

    @Column(name = "CPM_REPLICATE_FLAG")
    private String cpmReplicateFlag;

    public Long getCpmId() {
        return cpmId;
    }

    public void setCpmId(final Long cpmId) {
        this.cpmId = cpmId;
    }

    public String getCpmPrefix() {
        return cpmPrefix;
    }

    public void setCpmPrefix(final String cpmPrefix) {
        this.cpmPrefix = cpmPrefix;
    }

    public String getCpmType() {
        return cpmType;
    }

    public void setCpmType(final String cpmType) {
        this.cpmType = cpmType;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(final Long comId) {
        this.comId = comId;
    }

    public String getComValue() {
        return comValue;
    }

    public void setComValue(final String comValue) {
        this.comValue = comValue;
    }

    public String getComDesc() {
        return comDesc;
    }

    public void setComDesc(final String comDesc) {
        this.comDesc = comDesc;
    }

    public String getComDescMar() {
        return comDescMar;
    }

    public void setComDescMar(final String comDescMar) {
        this.comDescMar = comDescMar;
    }

    public Long getCodCpdId() {
        return codCpdId;
    }

    public void setCodCpdId(final Long codCpdId) {
        this.codCpdId = codCpdId;
    }

    public String getCodCpdValue() {
        return codCpdValue;
    }

    public void setCodCpdValue(final String codCpdValue) {
        this.codCpdValue = codCpdValue;
    }

    public String getCodCpdDesc() {
        return codCpdDesc;
    }

    public void setCodCpdDesc(final String codCpdDesc) {
        this.codCpdDesc = codCpdDesc;
    }

    public String getCodCpdDescMar() {
        return codCpdDescMar;
    }

    public void setCodCpdDescMar(final String codCpdDescMar) {
        this.codCpdDescMar = codCpdDescMar;
    }

    public Long getCodCpdParentId() {
        return codCpdParentId;
    }

    public void setCodCpdParentId(final Long codCpdParentId) {
        this.codCpdParentId = codCpdParentId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Integer getComLevel() {
        return comLevel;
    }

    public void setComLevel(final Integer comLevel) {
        this.comLevel = comLevel;
    }

    public String getCodCpdDefault() {
        return codCpdDefault;
    }

    public void setCodCpdDefault(final String codCpdDefault) {
        this.codCpdDefault = codCpdDefault;
    }

    public String getCpdOthers() {
        return cpdOthers;
    }

    public void setCpdOthers(final String cpdOthers) {
        this.cpdOthers = cpdOthers;
    }

    public String getCpmReplicateFlag() {
        return cpmReplicateFlag;
    }

    public void setCpmReplicateFlag(final String cpmReplicateFlag) {
        this.cpmReplicateFlag = cpmReplicateFlag;
    }

    /**
     * @return the cpmDesc
     */
    public String getCpmDesc() {
        return cpmDesc;
    }

    /**
     * @param cpmDesc the cpmDesc to set
     */
    public void setCpmDesc(final String cpmDesc) {
        this.cpmDesc = cpmDesc;
    }
}