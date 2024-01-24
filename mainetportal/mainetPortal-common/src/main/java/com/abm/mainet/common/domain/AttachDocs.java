
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
 * @author Jugnu Pandey
 *
 * Without application and service id related attachments
 *
 */
@Entity
@Table(name = "TB_ATTACH_DOCUMENT")
public class AttachDocs implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6118141154003895689L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ATD_ID", nullable = false)
    private Long attId;

    @Column(name = "ATD_DATE", nullable = false)
    private Date attDate;

    @Column(name = "ATD_PATH", length = 255)
    private String attPath;

    @Column(name = "ATD_FNAME", length = 500)
    private String attFname;

    @Column(name = "ATD_BY", length = 255)
    private String attBy;

    @Column(name = "ATD_FROM_PATH", length = 255)
    private String attFromPath;

    @Column(name = "ATD_DEPTID", nullable = true)
    private Long dept;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = true)
    private Long userid;

    @Column(name = "CREATED_DATE")
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ATD_STATUS")
    private String status;

    /**
     * Document Identification number(Estate Code,Property code,Tenant Code)
     */
    @Column(name = "ATD_IDF_ID")
    private String idfId;

    @Column(name = "ATD_SRNO")
    private Integer serialNo;

    @Column(name = "DMS_DOC_ID", nullable = true)
    private String dmsDocId;

    @Column(name = "DMS_FOLDER_PATH", nullable = true)
    private String dmsFolderPath;

    @Column(name = "DMS_DOC_NAME", nullable = true)
    private String dmsDocName;

    @Column(name = "DMS_DOC_VERSION", nullable = true)
    private String dmsDocVersion;
    
    @Column(name = "DOC_DESCRIPTION",length = 50,  nullable = true)
    private String docDescription;

    /**
     * @return the dmsDocId
     */
    public String getDmsDocId() {
        return dmsDocId;
    }

    /**
     * @param dmsDocId the dmsDocId to set
     */
    public void setDmsDocId(String dmsDocId) {
        this.dmsDocId = dmsDocId;
    }

    /**
     * @return the dmsFolderPath
     */
    public String getDmsFolderPath() {
        return dmsFolderPath;
    }

    /**
     * @param dmsFolderPath the dmsFolderPath to set
     */
    public void setDmsFolderPath(String dmsFolderPath) {
        this.dmsFolderPath = dmsFolderPath;
    }

    /**
     * @return the dmsDocName
     */
    public String getDmsDocName() {
        return dmsDocName;
    }

    /**
     * @param dmsDocName the dmsDocName to set
     */
    public void setDmsDocName(String dmsDocName) {
        this.dmsDocName = dmsDocName;
    }

    /**
     * @return the dmsDocVersion
     */
    public String getDmsDocVersion() {
        return dmsDocVersion;
    }

    /**
     * @param dmsDocVersion the dmsDocVersion to set
     */
    public void setDmsDocVersion(String dmsDocVersion) {
        this.dmsDocVersion = dmsDocVersion;
    }

    /**
     * @return the attId
     */
    public Long getAttId() {
        return attId;
    }

    /**
     * @param attId the attId to set
     */
    public void setAttId(final Long attId) {
        this.attId = attId;
    }

    /**
     * @return the attDate
     */
    public Date getAttDate() {
        return attDate;
    }

    /**
     * @param attDate the attDate to set
     */
    public void setAttDate(final Date attDate) {
        this.attDate = attDate;
    }

    /**
     * @return the attPath
     */
    public String getAttPath() {
        return attPath;
    }

    /**
     * @param attPath the attPath to set
     */
    public void setAttPath(final String attPath) {
        this.attPath = attPath;
    }

    /**
     * @return the attFname
     */
    public String getAttFname() {
        return attFname;
    }

    /**
     * @param attFname the attFname to set
     */
    public void setAttFname(final String attFname) {
        this.attFname = attFname;
    }

    /**
     * @return the attBy
     */
    public String getAttBy() {
        return attBy;
    }

    /**
     * @param attBy the attBy to set
     */
    public void setAttBy(final String attBy) {
        this.attBy = attBy;
    }

    /**
     * @return the attFromPath
     */
    public String getAttFromPath() {
        return attFromPath;
    }

    /**
     * @param attFromPath the attFromPath to set
     */
    public void setAttFromPath(final String attFromPath) {
        this.attFromPath = attFromPath;
    }

    /**
     * @return the dept
     */
    public Long getDept() {
        return dept;
    }

    /**
     * @param dept the dept to set
     */
    public void setDept(final Long dept) {
        this.dept = dept;
    }

    /**
     * @return the orgid
     */

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getIdfId() {
        return idfId;
    }

    public void setIdfId(final String idfId) {
        this.idfId = idfId;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
 

    public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String[] getPkValues() {

        return new String[] { "COM", "TB_ATTACH_DOCUMENT", "ATD_ID" };
    }

}
