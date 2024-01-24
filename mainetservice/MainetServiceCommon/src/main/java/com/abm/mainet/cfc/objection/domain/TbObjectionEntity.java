/**
 * @author  : Harshit kumar
 * @since   : 07 May 2018
 * @comment : Entity class for Objection Application
 */
package com.abm.mainet.cfc.objection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_OBJECTION_MAST")
public class TbObjectionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OBJ_ID", nullable = false)
    private Long objectionId;

    @Column(name = "OBJ_NO", nullable = false)
    private String objectionNumber;

    @Column(name = "OBJ_DATE", nullable = false)
    private Date objectionDate;

    @Column(name = "APM_ADDRESS")
    private String address;

    @Column(name = "APM_EMAIL")
    private String eMail;

    @Column(name = "APM_FNAME")
    private String fName;

    @Column(name = "APM_LNAME")
    private String lName;

    @Column(name = "APM_MNAME")
    private String mName;

    @Column(name = "APM_MOBILNO")
    private String mobileNo;

    @Column(name = "APM_SEX", nullable = false)
    private Long gender;

    @Column(name = "APM_TITLE", nullable = false)
    private Long title;

    @Column(name = "APM_UIDNO", nullable = false)
    private Long uid;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long objectionDeptId;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "COD_ID_OPER_LEVEL1")
    private Long codIdOperLevel1;

    @Column(name = "COD_ID_OPER_LEVEL2")
    private Long codIdOperLevel2;

    @Column(name = "COD_ID_OPER_LEVEL3")
    private Long codIdOperLevel3;

    @Column(name = "COD_ID_OPER_LEVEL4")
    private Long codIdOperLevel4;

    @Column(name = "COD_ID_OPER_LEVEL5")
    private Long codIdOperLevel5;

    @Column(name = "SM_SERVICE_ID", nullable = false)
    private Long serviceId;

    @Column(name = "APM_APPLICATION_ID", nullable = false)
    private Long apmApplicationId;

    @Column(name = "OBJ_REFID", nullable = false)
    private String objectionReferenceNumber;

    @Column(name = "OBJ_ADDREFID")
    private String objectionAddReferenceNumber;

    @Column(name = "OBJ_DETAIL", nullable = false)
    private String objectionDetails;

    @Column(name = "NOTTICE_NO", nullable = false)
    private String noticeNo;

    @Column(name = "OBJ_TYPE", nullable = false)
    private Long objectionOn;

    @Column(name = "OBJ_REASON", nullable = false)
    private String objectionReason;

    @Column(name = "BILL_NO", nullable = false)
    private Long billNo;

    @Column(name = "BILLDUEDATE", nullable = false)
    private Date billDueDate;

    @Column(name = "OBJ_STATUS", nullable = false)
    private String objectionStatus;

    @Column(name = "ORGID", nullable = false)
    private long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "OBJ_TIME")
    private Long objTime;
    
    @Column(name = "FLAT_NO")
    private String flatNo;

    public Long getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(Long objectionId) {
        this.objectionId = objectionId;
    }

    public String getObjectionStatus() {
        return objectionStatus;
    }

    public void setObjectionStatus(String objectionStatus) {
        this.objectionStatus = objectionStatus;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getObjectionDetails() {
        return objectionDetails;
    }

    public void setObjectionDetails(String objectionDetails) {
        this.objectionDetails = objectionDetails;
    }

    public Long getObjectionDeptId() {
        return objectionDeptId;
    }

    public void setObjectionDeptId(Long objectionDeptId) {
        this.objectionDeptId = objectionDeptId;
    }

    public String getObjectionReferenceNumber() {
        return objectionReferenceNumber;
    }

    public void setObjectionReferenceNumber(String objectionReferenceNumber) {
        this.objectionReferenceNumber = objectionReferenceNumber;
    }

    public String getObjectionNumber() {
        return objectionNumber;
    }

    public void setObjectionNumber(String objectionNumber) {
        this.objectionNumber = objectionNumber;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Date getObjectionDate() {
        return objectionDate;
    }

    public void setObjectionDate(Date objectionDate) {
        this.objectionDate = objectionDate;
    }

    public Long getCodIdOperLevel1() {
        return codIdOperLevel1;
    }

    public void setCodIdOperLevel1(Long codIdOperLevel1) {
        this.codIdOperLevel1 = codIdOperLevel1;
    }

    public Long getCodIdOperLevel2() {
        return codIdOperLevel2;
    }

    public void setCodIdOperLevel2(Long codIdOperLevel2) {
        this.codIdOperLevel2 = codIdOperLevel2;
    }

    public Long getCodIdOperLevel3() {
        return codIdOperLevel3;
    }

    public void setCodIdOperLevel3(Long codIdOperLevel3) {
        this.codIdOperLevel3 = codIdOperLevel3;
    }

    public Long getCodIdOperLevel4() {
        return codIdOperLevel4;
    }

    public void setCodIdOperLevel4(Long codIdOperLevel4) {
        this.codIdOperLevel4 = codIdOperLevel4;
    }

    public Long getCodIdOperLevel5() {
        return codIdOperLevel5;
    }

    public void setCodIdOperLevel5(Long codIdOperLevel5) {
        this.codIdOperLevel5 = codIdOperLevel5;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_OBJECTION_MAST", "OBJ_ID" };
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public Long getObjectionOn() {
        return objectionOn;
    }

    public void setObjectionOn(Long objectionOn) {
        this.objectionOn = objectionOn;
    }

    public String getObjectionReason() {
        return objectionReason;
    }

    public void setObjectionReason(String objectionReason) {
        this.objectionReason = objectionReason;
    }

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public Date getBillDueDate() {
        return billDueDate;
    }

    public void setBillDueDate(Date billDueDate) {
        this.billDueDate = billDueDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(Long title) {
        this.title = title;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getObjectionAddReferenceNumber() {
        return objectionAddReferenceNumber;
    }

    public void setObjectionAddReferenceNumber(String objectionAddReferenceNumber) {
        this.objectionAddReferenceNumber = objectionAddReferenceNumber;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getObjTime() {
        return objTime;
    }

    public void setObjTime(Long objTime) {
        this.objTime = objTime;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
    
    

}
