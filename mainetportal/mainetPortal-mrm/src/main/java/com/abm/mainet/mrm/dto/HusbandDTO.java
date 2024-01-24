package com.abm.mainet.mrm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonBackReference;


public class HusbandDTO implements Serializable {

    private static final long serialVersionUID = 5049138665466304816L;

    private Long husbandId;

    
    @JsonBackReference
    private MarriageDTO marId;

    private String firstNameEng;

    private String firstNameReg;

    private String middleNameEng;

    private String middleNameReg;

    private String lastNameEng;

    private String lastNameReg;

    private String otherName;

    private String uidNo;

    private Date dob;

    private int year;

    private int month;

    private Long religionBirth;  // Prefix RLG

    private Long religionAdopt;  // Prefix RLG

    private Long occupation; // Prefix OCU

    private Long statusMarTime; // Prefix STA

    private String fullAddrEng;

    private String fullAddrReg;

    private String capturePhotoName;

    private String capturePhotoPath;

    private String captureFingerprintName;

    private String captureFingerprintPath;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;
    
    private Long caste1;
    private Long caste2;
    private Long caste3;
    private Long caste4;
    private Long caste5;
    
    private String nri;

    public Long getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(Long husbandId) {
        this.husbandId = husbandId;
    }

    public MarriageDTO getMarId() {
        return marId;
    }

    public void setMarId(MarriageDTO marId) {
        this.marId = marId;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getFirstNameReg() {
        return firstNameReg;
    }

    public void setFirstNameReg(String firstNameReg) {
        this.firstNameReg = firstNameReg;
    }

    public String getMiddleNameEng() {
        return middleNameEng;
    }

    public void setMiddleNameEng(String middleNameEng) {
        this.middleNameEng = middleNameEng;
    }

    public String getMiddleNameReg() {
        return middleNameReg;
    }

    public void setMiddleNameReg(String middleNameReg) {
        this.middleNameReg = middleNameReg;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getLastNameReg() {
        return lastNameReg;
    }

    public void setLastNameReg(String lastNameReg) {
        this.lastNameReg = lastNameReg;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    

    public String getUidNo() {
		return uidNo;
	}

	public void setUidNo(String uidNo) {
		this.uidNo = uidNo;
	}

	public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getReligionBirth() {
        return religionBirth;
    }

    public void setReligionBirth(Long religionBirth) {
        this.religionBirth = religionBirth;
    }

    public Long getReligionAdopt() {
        return religionAdopt;
    }

    public void setReligionAdopt(Long religionAdopt) {
        this.religionAdopt = religionAdopt;
    }

    public Long getOccupation() {
        return occupation;
    }

    public void setOccupation(Long occupation) {
        this.occupation = occupation;
    }

    public Long getStatusMarTime() {
        return statusMarTime;
    }

    public void setStatusMarTime(Long statusMarTime) {
        this.statusMarTime = statusMarTime;
    }

    public String getFullAddrEng() {
        return fullAddrEng;
    }

    public void setFullAddrEng(String fullAddrEng) {
        this.fullAddrEng = fullAddrEng;
    }

    public String getFullAddrReg() {
        return fullAddrReg;
    }

    public void setFullAddrReg(String fullAddrReg) {
        this.fullAddrReg = fullAddrReg;
    }

    public String getCapturePhotoName() {
        return capturePhotoName;
    }

    public void setCapturePhotoName(String capturePhotoName) {
        this.capturePhotoName = capturePhotoName;
    }

    public String getCapturePhotoPath() {
        return capturePhotoPath;
    }

    public void setCapturePhotoPath(String capturePhotoPath) {
        this.capturePhotoPath = capturePhotoPath;
    }

    public String getCaptureFingerprintName() {
        return captureFingerprintName;
    }

    public void setCaptureFingerprintName(String captureFingerprintName) {
        this.captureFingerprintName = captureFingerprintName;
    }

    public String getCaptureFingerprintPath() {
        return captureFingerprintPath;
    }

    public void setCaptureFingerprintPath(String captureFingerprintPath) {
        this.captureFingerprintPath = captureFingerprintPath;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

	public Long getCaste1() {
		return caste1;
	}

	public void setCaste1(Long caste1) {
		this.caste1 = caste1;
	}

	public Long getCaste2() {
		return caste2;
	}

	public void setCaste2(Long caste2) {
		this.caste2 = caste2;
	}

	public Long getCaste3() {
		return caste3;
	}

	public void setCaste3(Long caste3) {
		this.caste3 = caste3;
	}

	public Long getCaste4() {
		return caste4;
	}

	public void setCaste4(Long caste4) {
		this.caste4 = caste4;
	}

	public Long getCaste5() {
		return caste5;
	}

	public void setCaste5(Long caste5) {
		this.caste5 = caste5;
	}

	
	public String getNri() {
		return nri;
	}

	public void setNri(String nri) {
		this.nri = nri;
	}

	@Override
	public String toString() {
		return "HusbandDTO [husbandId=" + husbandId + ", marId=" + marId + ", firstNameEng=" + firstNameEng
				+ ", firstNameReg=" + firstNameReg + ", middleNameEng=" + middleNameEng + ", middleNameReg="
				+ middleNameReg + ", lastNameEng=" + lastNameEng + ", lastNameReg=" + lastNameReg + ", otherName="
				+ otherName + ", uidNo=" + uidNo + ", dob=" + dob + ", year=" + year + ", month=" + month
				+ ", religionBirth=" + religionBirth + ", religionAdopt=" + religionAdopt + ", occupation=" + occupation
				+ ", statusMarTime=" + statusMarTime + ", fullAddrEng=" + fullAddrEng + ", fullAddrReg=" + fullAddrReg
				+ ", capturePhotoName=" + capturePhotoName + ", capturePhotoPath=" + capturePhotoPath
				+ ", captureFingerprintName=" + captureFingerprintName + ", captureFingerprintPath="
				+ captureFingerprintPath + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + ", caste1=" + caste1 + ", caste2=" + caste2 + ", caste3=" + caste3
				+ ", caste4=" + caste4 + ", caste5=" + caste5 + "]";
	}

    

}
