package com.abm.mainet.water.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author deepika.pimpale
 *
 */
@Entity
@Table(name = "TB_WT_CSMR_ADDITIONAL_OWNER")
public class AdditionalOwnerInfo {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CAO_ID", precision = 12, scale = 0, nullable = false)
    private Long cao_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_IDN", nullable = false)
    private TbKCsmrInfoMH csIdn;

    @Column(name = "CAO_TITLE", length = 15, nullable = false)
    private String ownerTitle;

    @Column(name = "CAO_FNAME", length = 300, nullable = false)
    private String ownerFirstName;

    @Column(name = "CAO_MNAME", length = 300, nullable = true)
    private String ownerMiddleName;

    @Column(name = "CAO_LNAME", length = 300, nullable = true)
    private String ownerLastName;

    @Column(name = "CAO_ADDRESS", length = 1000, nullable = true)
    private String cao_address;

    @Column(name = "CAO_CONTACTNO", length = 50, nullable = true)
    private String cao_contactno;

    @Column(name = "CAO_NEW_TITLE", length = 10, nullable = false)
    private Long caoNewTitle;

    @Column(name = "CAO_NEW_FNAME", length = 300, nullable = false)
    private String caoNewFName;

    @Column(name = "CAO_NEW_MNAME", length = 300, nullable = true)
    private String caoNewMName;

    @Column(name = "CAO_NEW_LNAME", length = 300, nullable = true)
    private String caoNewLName;

    @Column(name = "CAO_NEW_ADDRESS", length = 1000, nullable = true)
    private String caoNewAddress;

    @Column(name = "CAO_NEW_CONTACTNO", length = 50, nullable = true)
    private String caoNewContactno;

    @Column(name = "CAO_NEW_GENDER", length = 10, nullable = true)
    private Long caoNewGender;

    @Column(name = "CAO_NEW_UID", length = 12, nullable = true)
    private Long caoNewUID;

    @Column(name = "APM_APPLICATION_ID", length = 16, nullable = true)
    private Long apmApplicationId;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Organization id
    private Long orgid;

    @Column(name = "USER_ID", precision = 7, scale = 0, nullable = true)
    // comments : User id
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // comments : Language id
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMacUpd;

    @Column(name = "CAO_GENDER", nullable = true)
    // comments : stores ip information
    private Long gender;

    @Column(name = "CAO_UID", length = 12, nullable = true)
    // comments : stores ip information
    private Long caoUID;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_CSMR_ADDITIONAL_OWNER", "CAO_ID" };
    }

    public Long getCao_id() {
        return cao_id;
    }

    public void setCao_id(final Long cao_id) {
        this.cao_id = cao_id;
    }

    public String getCao_address() {
        return cao_address;
    }

    public void setCao_address(final String cao_address) {
        this.cao_address = cao_address;
    }

    public String getCao_contactno() {
        return cao_contactno;
    }

    public void setCao_contactno(final String cao_contactno) {
        this.cao_contactno = cao_contactno;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public TbKCsmrInfoMH getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final TbKCsmrInfoMH csIdn) {
        this.csIdn = csIdn;
    }

    public String getOwnerTitle() {
        return ownerTitle;
    }

    public void setOwnerTitle(final String ownerTitle) {
        this.ownerTitle = ownerTitle;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(final String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public Long getCaoUID() {
        return caoUID;
    }

    public void setCaoUID(final Long caoUID) {
        this.caoUID = caoUID;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(final Long gender) {
        this.gender = gender;
    }

    public String getOwnerMiddleName() {
        return ownerMiddleName;
    }

    public void setOwnerMiddleName(final String ownerMiddleName) {
        this.ownerMiddleName = ownerMiddleName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(final String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public Long getCaoNewTitle() {
        return caoNewTitle;
    }

    public void setCaoNewTitle(final Long caoNewTitle) {
        this.caoNewTitle = caoNewTitle;
    }

    public String getCaoNewFName() {
        return caoNewFName;
    }

    public void setCaoNewFName(final String caoNewFName) {
        this.caoNewFName = caoNewFName;
    }

    public String getCaoNewMName() {
        return caoNewMName;
    }

    public void setCaoNewMName(final String caoNewMName) {
        this.caoNewMName = caoNewMName;
    }

    public String getCaoNewLName() {
        return caoNewLName;
    }

    public void setCaoNewLName(final String caoNewLName) {
        this.caoNewLName = caoNewLName;
    }

    public String getCaoNewAddress() {
        return caoNewAddress;
    }

    public void setCaoNewAddress(final String caoNewAddress) {
        this.caoNewAddress = caoNewAddress;
    }

    public String getCaoNewContactno() {
        return caoNewContactno;
    }

    public void setCaoNewContactno(final String caoNewContactno) {
        this.caoNewContactno = caoNewContactno;
    }

    public Long getCaoNewGender() {
        return caoNewGender;
    }

    public void setCaoNewGender(final Long caoNewGender) {
        this.caoNewGender = caoNewGender;
    }

    public Long getCaoNewUID() {
        return caoNewUID;
    }

    public void setCaoNewUID(final Long caoNewUID) {
        this.caoNewUID = caoNewUID;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
