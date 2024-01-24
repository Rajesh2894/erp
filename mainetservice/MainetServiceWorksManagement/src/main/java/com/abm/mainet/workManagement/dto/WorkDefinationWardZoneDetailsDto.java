package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WorkDefinationWardZoneDetailsDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2876931256935527269L;

    private Long wardZoneId;
    private Long codId1;
    private Long codId2;
    private Long codId3;
    private Long codId4;
    private Long codId5;
    private String codId1Desc;
    private String codId2Desc;
    private String codId3Desc;
    private String codId4Desc;
    private String codId5Desc;
    
    private String level1;
    private String level2;
    private String level3;
    private String level4;
    private String level5;
    
    public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getLevel3() {
		return level3;
	}

	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	public String getLevel4() {
		return level4;
	}

	public void setLevel4(String level4) {
		this.level4 = level4;
	}

	public String getLevel5() {
		return level5;
	}

	public void setLevel5(String level5) {
		this.level5 = level5;
	}

	public String getCodId1Desc() {
		return codId1Desc;
	}

	public void setCodId1Desc(String codId1Desc) {
		this.codId1Desc = codId1Desc;
	}

	public String getCodId2Desc() {
		return codId2Desc;
	}

	public void setCodId2Desc(String codId2Desc) {
		this.codId2Desc = codId2Desc;
	}

	public String getCodId3Desc() {
		return codId3Desc;
	}

	public void setCodId3Desc(String codId3Desc) {
		this.codId3Desc = codId3Desc;
	}

	public String getCodId4Desc() {
		return codId4Desc;
	}

	public void setCodId4Desc(String codId4Desc) {
		this.codId4Desc = codId4Desc;
	}

	public String getCodId5Desc() {
		return codId5Desc;
	}

	public void setCodId5Desc(String codId5Desc) {
		this.codId5Desc = codId5Desc;
	}

	private WorkDefinitionDto workDefinitionDto;
    private Long orgId;
    private Long createdBy;
    private Long updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;

    public Long getWardZoneId() {
        return wardZoneId;
    }

    public void setWardZoneId(Long wardZoneId) {
        this.wardZoneId = wardZoneId;
    }

    public Long getCodId1() {
        return codId1;
    }

    public void setCodId1(Long codId1) {
        this.codId1 = codId1;
    }

    public Long getCodId2() {
        return codId2;
    }

    public void setCodId2(Long codId2) {
        this.codId2 = codId2;
    }

    public Long getCodId3() {
        return codId3;
    }

    public void setCodId3(Long codId3) {
        this.codId3 = codId3;
    }

    public Long getCodId4() {
        return codId4;
    }

    public void setCodId4(Long codId4) {
        this.codId4 = codId4;
    }

    public Long getCodId5() {
        return codId5;
    }

    public void setCodId5(Long codId5) {
        this.codId5 = codId5;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public WorkDefinitionDto getWorkDefinitionDto() {
        return workDefinitionDto;
    }

    public void setWorkDefinitionDto(WorkDefinitionDto workDefinitionDto) {
        this.workDefinitionDto = workDefinitionDto;
    }

}
