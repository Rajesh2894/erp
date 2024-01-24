package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.domain.Employee;
import org.codehaus.jackson.annotate.JsonIgnore;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ParentDetailDTO implements Serializable {

	private static final long serialVersionUID = -4100833798784520499L;

	private String pdFathername;
	private String pdMothername;
	private String pdAddress;
	@JsonIgnore
	private Long cpdId1;
	@JsonIgnore
	private Long cpdId2;
	@JsonIgnore
	private Long cpdId3;
	@JsonIgnore
	private Long cpdId4;
	@JsonIgnore
	private Long cpdId5;
	private Long cpdReligionId;
	private Long cpdFEducnId;
	private Long cpdMEducnId;
	private Long cpdFOccuId;
	private Long cpdMOccuId;
	private Long pdAgeAtMarry;
	private Long pdAgeAtBirth;
	private Long pdLiveChildn;
	@JsonIgnore
	private String pdStatus;
	@JsonIgnore
	private String pdGfathername;
	@JsonIgnore
	private String pdGmothername;
	@JsonIgnore
	private Employee updatedBy;
	@JsonIgnore
	private Date updatedDate;
	private String pdFathernameMar;
	private String pdMothernameMar;
	private String pdAddressMar;
	
	private Long pdRegUnitId;
	private String pdParaddress;
	private String pdParaddressMar;
	@JsonIgnore
	private String otherReligion;
	@JsonIgnore
	private String pdUidF;
	@JsonIgnore
	private String pdUidM;
	@JsonIgnore
	private String bplNo;
	private String motheraddress;
	@JsonIgnore
	private String addressflag;
	private String motheraddressMar;

	
	private String country;
	private String state;
	private String district;
	private String taluka;
	private String religion;
	private String regUnit;           //pdRegUnitId
	private String fatherOccupation;
	private String motherOccupation;
	private String fatherEdu;
	private String motherEdu;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTaluka() {
		return taluka;
	}

	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getRegUnit() {
		return regUnit;
	}

	public void setRegUnit(String regUnit) {
		this.regUnit = regUnit;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getFatherEdu() {
		return fatherEdu;
	}

	public void setFatherEdu(String fatherEdu) {
		this.fatherEdu = fatherEdu;
	}

	public String getMotherEdu() {
		return motherEdu;
	}

	public void setMotherEdu(String motherEdu) {
		this.motherEdu = motherEdu;
	}

	public String getPdFathername() {
		return pdFathername;
	}

	public void setPdFathername(String pdFathername) {
		this.pdFathername = pdFathername;
	}

	public String getPdMothername() {
		return pdMothername;
	}

	public void setPdMothername(String pdMothername) {
		this.pdMothername = pdMothername;
	}

	public String getPdAddress() {
		return pdAddress;
	}

	public void setPdAddress(String pdAddress) {
		this.pdAddress = pdAddress;
	}

	public Long getCpdId3() {
		return cpdId3;
	}

	public void setCpdId3(Long cpdId3) {
		this.cpdId3 = cpdId3;
	}

	public Long getCpdId2() {
		return cpdId2;
	}

	public void setCpdId2(Long cpdId2) {
		this.cpdId2 = cpdId2;
	}

	public Long getCpdReligionId() {
		return cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}

	public Long getCpdId1() {
		return cpdId1;
	}

	public void setCpdId1(Long cpdId1) {
		this.cpdId1 = cpdId1;
	}

	public Long getCpdFEducnId() {
		return cpdFEducnId;
	}

	public void setCpdFEducnId(Long cpdFEducnId) {
		this.cpdFEducnId = cpdFEducnId;
	}

	public Long getCpdMEducnId() {
		return cpdMEducnId;
	}

	public void setCpdMEducnId(Long cpdMEducnId) {
		this.cpdMEducnId = cpdMEducnId;
	}

	public Long getCpdFOccuId() {
		return cpdFOccuId;
	}

	public void setCpdFOccuId(Long cpdFOccuId) {
		this.cpdFOccuId = cpdFOccuId;
	}

	public Long getCpdMOccuId() {
		return cpdMOccuId;
	}

	public void setCpdMOccuId(Long cpdMOccuId) {
		this.cpdMOccuId = cpdMOccuId;
	}

	public Long getPdAgeAtMarry() {
		return pdAgeAtMarry;
	}

	public void setPdAgeAtMarry(Long pdAgeAtMarry) {
		this.pdAgeAtMarry = pdAgeAtMarry;
	}

	public Long getPdAgeAtBirth() {
		return pdAgeAtBirth;
	}

	public void setPdAgeAtBirth(Long pdAgeAtBirth) {
		this.pdAgeAtBirth = pdAgeAtBirth;
	}

	public Long getPdLiveChildn() {
		return pdLiveChildn;
	}

	public void setPdLiveChildn(Long pdLiveChildn) {
		this.pdLiveChildn = pdLiveChildn;
	}

	public Long getCpdId4() {
		return cpdId4;
	}

	public void setCpdId4(Long cpdId4) {
		this.cpdId4 = cpdId4;
	}

	public String getPdStatus() {
		return pdStatus;
	}

	public void setPdStatus(String pdStatus) {
		this.pdStatus = pdStatus;
	}

	public String getPdGfathername() {
		return pdGfathername;
	}

	public void setPdGfathername(String pdGfathername) {
		this.pdGfathername = pdGfathername;
	}

	public String getPdGmothername() {
		return pdGmothername;
	}

	public void setPdGmothername(String pdGmothername) {
		this.pdGmothername = pdGmothername;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPdFathernameMar() {
		return pdFathernameMar;
	}

	public void setPdFathernameMar(String pdFathernameMar) {
		this.pdFathernameMar = pdFathernameMar;
	}

	public String getPdMothernameMar() {
		return pdMothernameMar;
	}

	public void setPdMothernameMar(String pdMothernameMar) {
		this.pdMothernameMar = pdMothernameMar;
	}

	public String getPdAddressMar() {
		return pdAddressMar;
	}

	public void setPdAddressMar(String pdAddressMar) {
		this.pdAddressMar = pdAddressMar;
	}

	public Long getCpdId5() {
		return cpdId5;
	}

	public void setCpdId5(Long cpdId5) {
		this.cpdId3 = cpdId5;
	}

	public Long getPdRegUnitId() {
		return pdRegUnitId;
	}

	public void setPdRegUnitId(Long pdRegUnitId) {
		this.pdRegUnitId = pdRegUnitId;
	}

	public String getPdParaddress() {
		return pdParaddress;
	}

	public void setPdParaddress(String pdParaddress) {
		this.pdParaddress = pdParaddress;
	}

	public String getPdParaddressMar() {
		return pdParaddressMar;
	}

	public void setPdParaddressMar(String pdParaddressMar) {
		this.pdParaddressMar = pdParaddressMar;
	}

	public String getOtherReligion() {
		return otherReligion;
	}

	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}

	public String getPdUidF() {
		return pdUidF;
	}

	public void setPdUidF(String pdUidF) {
		this.pdUidF = pdUidF;
	}

	public String getPdUidM() {
		return pdUidM;
	}

	public void setPdUidM(String pdUidM) {
		this.pdUidM = pdUidM;
	}

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public String getMotheraddress() {
		return motheraddress;
	}

	public void setMotheraddress(String motheraddress) {
		this.motheraddress = motheraddress;
	}

	public String getAddressflag() {
		return addressflag;
	}

	public void setAddressflag(String addressflag) {
		this.addressflag = addressflag;
	}

	public String getMotheraddressMar() {
		return motheraddressMar;
	}

	public void setMotheraddressMar(String motheraddressMar) {
		this.motheraddressMar = motheraddressMar;
	}
 
}
