package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class TbLglComntRevwDtlDTO implements Serializable{

    private static final long serialVersionUID = 6685950501936381343L;

    private Long comntId ;

    private Long cseId;

    private String comnt;

    private String revw;

    private String crFlag;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;
    
    private String activeStatus;
    
    private Long hrId ;
    
    private Date hrDate;
    
    private boolean disableField;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

	public Long getComntId() {
		return comntId;
	}

	public void setComntId(Long comntId) {
		this.comntId = comntId;
	}

	public Long getCseId() {
		return cseId;
	}

	public void setCseId(Long cseId) {
		this.cseId = cseId;
	}

	public String getComnt() {
		return comnt;
	}

	public void setComnt(String comnt) {
		this.comnt = comnt;
	}

	public String getRevw() {
		return revw;
	}

	public void setRevw(String revw) {
		this.revw = revw;
	}

	public String getCrFlag() {
		return crFlag;
	}

	public void setCrFlag(String crFlag) {
		this.crFlag = crFlag;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getHrId() {
		return hrId;
	}

	public void setHrId(Long hrId) {
		this.hrId = hrId;
	}

	public Date getHrDate() {
		return hrDate;
	}

	public void setHrDate(Date hrDate) {
		this.hrDate = hrDate;
	}

	public boolean isDisableField() {
		return disableField;
	}

	public void setDisableField(boolean disableField) {
		this.disableField = disableField;
	}
	
	
}
