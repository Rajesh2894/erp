package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author swapnil.shirke
 */

@Entity
@Table(name = "TB_EIP_SEO_MAS")
public class SEOKeyWordMaster implements Serializable {

    private static final long serialVersionUID = -2865257214075474132L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SEO_ID", nullable = false, precision = 12, scale = 0)
    private long seoId;

    @Column(name = "ORGID", nullable = false)
    private long orgId;

    @Column(name = "KEY_WORD", nullable = false)
    private String keyWord;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "CREATED_BY")
    private long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgipmac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgipmacupd;

    public long getSeoId() {
        return seoId;
    }

    public void setSeoId(long seoId) {
        this.seoId = seoId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgipmac() {
        return lgipmac;
    }

    public void setLgipmac(String lgipmac) {
        this.lgipmac = lgipmac;
    }

    public String getLgipmacupd() {
        return lgipmacupd;
    }

    public void setLgipmacupd(String lgipmacupd) {
        this.lgipmacupd = lgipmacupd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SEO_MAS", "SEO_ID" };
    }

}
