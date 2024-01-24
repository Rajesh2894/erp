package com.abm.mainet.workManagement.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WorksRABillDto {

    private Long raId;
    private Long workId;
    private BigDecimal raTaxAmt;
    private String raStatus;
    private String raRemark;
    private BigDecimal raPaidAmt;
    private Date raGeneratedDate;
    private String raBillno;
    private Date raBillDate;
    private BigDecimal raBillAmt;
    private Long projId;
    private String raMbIds;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String projName;
    private String workName;
    private List<WmsRaBillTaxDetailsDto> raBillTaxDetails = new ArrayList<>();
    private WmsRaBillTaxDetailsDto raBillTaxDto=new WmsRaBillTaxDetailsDto();
    private List<WmsRaBillTaxDetailsDto> raBillTaxDtoWith = new ArrayList<>();
    private List<Long> mbId = new ArrayList<>();
    private BigDecimal raMbAmount;
    private String raCode;
    private BigDecimal totalTaxAmount;

    private String raDate;
    private String raBillStringDate;
    private String raBillAmtStr;
    private Long raSerialNo;
    
    private BigDecimal sanctionAmount;
    private BigDecimal netValue;
    
    private BigDecimal totalPreviosWithHeldAmount;
    private BigDecimal absoluteTaxAmt;
    private String raBillType;
    private Long levelCheck;
    
    public WmsRaBillTaxDetailsDto getRaBillTaxDto() {
		return raBillTaxDto;
	}

	public void setRaBillTaxDto(WmsRaBillTaxDetailsDto raBillTaxDto) {
		this.raBillTaxDto = raBillTaxDto;
	}

    public String getRaDate() {
        return raDate;
    }

    public void setRaDate(String raDate) {
        this.raDate = raDate;
    }

    public Long getRaId() {
        return raId;
    }

    public void setRaId(Long raId) {
        this.raId = raId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public BigDecimal getRaTaxAmt() {
        return raTaxAmt;
    }

    public void setRaTaxAmt(BigDecimal raTaxAmt) {
        this.raTaxAmt = raTaxAmt;
    }

    public String getRaStatus() {
        return raStatus;
    }

    public void setRaStatus(String raStatus) {
        this.raStatus = raStatus;
    }

    public String getRaRemark() {
        return raRemark;
    }

    public void setRaRemark(String raRemark) {
        this.raRemark = raRemark;
    }

    public BigDecimal getRaPaidAmt() {
        return raPaidAmt;
    }

    public void setRaPaidAmt(BigDecimal raPaidAmt) {
        this.raPaidAmt = raPaidAmt;
    }

    public Date getRaGeneratedDate() {
        return raGeneratedDate;
    }

    public void setRaGeneratedDate(Date raGeneratedDate) {
        this.raGeneratedDate = raGeneratedDate;
    }

    public String getRaBillno() {
        return raBillno;
    }

    public void setRaBillno(String raBillno) {
        this.raBillno = raBillno;
    }

    public Date getRaBillDate() {
        return raBillDate;
    }

    public void setRaBillDate(Date raBillDate) {
        this.raBillDate = raBillDate;
    }

    public BigDecimal getRaBillAmt() {
        return raBillAmt;
    }

    public void setRaBillAmt(BigDecimal raBillAmt) {
        this.raBillAmt = raBillAmt;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getRaMbIds() {
        return raMbIds;
    }

    public void setRaMbIds(String raMbIds) {
        this.raMbIds = raMbIds;
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

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public List<WmsRaBillTaxDetailsDto> getRaBillTaxDetails() {
        return raBillTaxDetails;
    }

    public void setRaBillTaxDetails(List<WmsRaBillTaxDetailsDto> raBillTaxDetails) {
        this.raBillTaxDetails = raBillTaxDetails;
    }

    public List<Long> getMbId() {
        return mbId;
    }

    public void setMbId(List<Long> mbId) {
        this.mbId = mbId;
    }

    public BigDecimal getRaMbAmount() {
        return raMbAmount;
    }

    public void setRaMbAmount(BigDecimal raMbAmount) {
        this.raMbAmount = raMbAmount;
    }

    public String getRaCode() {
        return raCode;
    }

    public void setRaCode(String raCode) {
        this.raCode = raCode;
    }

    public String getRaBillStringDate() {
        return raBillStringDate;
    }

    public void setRaBillStringDate(String raBillStringDate) {
        this.raBillStringDate = raBillStringDate;
    }

	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public String getRaBillAmtStr() {
		return raBillAmtStr;
	}

	public void setRaBillAmtStr(String raBillAmtStr) {
		this.raBillAmtStr = raBillAmtStr;
	}

	public BigDecimal getSanctionAmount() {
		return sanctionAmount;
	}

	public void setSanctionAmount(BigDecimal sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}

	public BigDecimal getNetValue() {
		return netValue;
	}

	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}

	public Long getRaSerialNo() {
		return raSerialNo;
	}

	public void setRaSerialNo(Long raSerialNo) {
		this.raSerialNo = raSerialNo;
	}

	public BigDecimal getTotalPreviosWithHeldAmount() {
		return totalPreviosWithHeldAmount;
	}

	public void setTotalPreviosWithHeldAmount(BigDecimal totalPreviosWithHeldAmount) {
		this.totalPreviosWithHeldAmount = totalPreviosWithHeldAmount;
	}

	public BigDecimal getAbsoluteTaxAmt() {
		return absoluteTaxAmt;
	}

	public void setAbsoluteTaxAmt(BigDecimal absoluteTaxAmt) {
		this.absoluteTaxAmt = absoluteTaxAmt;
	}

	public List<WmsRaBillTaxDetailsDto> getRaBillTaxDtoWith() {
		return raBillTaxDtoWith;
	}

	public void setRaBillTaxDtoWith(List<WmsRaBillTaxDetailsDto> raBillTaxDtoWith) {
		this.raBillTaxDtoWith = raBillTaxDtoWith;
	}

	public String getRaBillType() {
		return raBillType;
	}

	public void setRaBillType(String raBillType) {
		this.raBillType = raBillType;
	}

	public Long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(Long levelCheck) {
		this.levelCheck = levelCheck;
	}

}
