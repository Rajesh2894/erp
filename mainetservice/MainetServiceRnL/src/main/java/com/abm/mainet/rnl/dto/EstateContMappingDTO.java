package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.master.dto.ContractMappingDTO;

public class EstateContMappingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long mappingId;
    private Long contractId;
    private Long esId;
    private List<ContractPropListDTO> contractPropListDTO;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String code;
    private String wfRefno;
    private String nameEng;
    private String nameReg;
    private Long propId;
    private String propName;
    private String assesmentPropId;
    private Integer usage;
    private String usageDesc;
    private Integer unitNo;
    private Integer floor;
    private String floorDesc;
    private Double totalArea;
    private String remark;
    private Long langId;
    private ContractMappingDTO contractMappingDTO = new ContractMappingDTO();

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(final Long mappingId) {
        this.mappingId = mappingId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(final Long contractId) {
        this.contractId = contractId;
    }

    public Long getEsId() {
        return esId;
    }

    public void setEsId(final Long esId) {
        this.esId = esId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLgIpMacUp() {
		return lgIpMacUp;
	}

	public void setLgIpMacUp(String lgIpMacUp) {
		this.lgIpMacUp = lgIpMacUp;
	}

	public List<ContractPropListDTO> getContractPropListDTO() {
        return contractPropListDTO;
    }

    public void setContractPropListDTO(final List<ContractPropListDTO> contractPropListDTO) {
        this.contractPropListDTO = contractPropListDTO;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWfRefno() {
		return wfRefno;
	}

	public void setWfRefno(String wfRefno) {
		this.wfRefno = wfRefno;
	}

	public ContractMappingDTO getContractMappingDTO() {
		return contractMappingDTO;
	}

	public void setContractMappingDTO(ContractMappingDTO contractMappingDTO) {
		this.contractMappingDTO = contractMappingDTO;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public String getNameReg() {
		return nameReg;
	}

	public void setNameReg(String nameReg) {
		this.nameReg = nameReg;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getAssesmentPropId() {
		return assesmentPropId;
	}

	public void setAssesmentPropId(String assesmentPropId) {
		this.assesmentPropId = assesmentPropId;
	}

	public Integer getUsage() {
		return usage;
	}

	public void setUsage(Integer usage) {
		this.usage = usage;
	}

	public Integer getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}

	public String getUsageDesc() {
		return usageDesc;
	}

	public void setUsageDesc(String usageDesc) {
		this.usageDesc = usageDesc;
	}

	public String getFloorDesc() {
		return floorDesc;
	}

	public void setFloorDesc(String floorDesc) {
		this.floorDesc = floorDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	
	
}
