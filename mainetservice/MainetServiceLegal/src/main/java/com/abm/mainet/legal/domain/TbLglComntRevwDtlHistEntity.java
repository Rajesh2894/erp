package com.abm.mainet.legal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_COMNT_REVW_DTL_HIST")
public class TbLglComntRevwDtlHistEntity {

    private static final long serialVersionUID = -3357117866630998107L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "COMNT_ID_HIS")
    private Long comntIdHis;

    @Column(name = "COMNT_ID")
    private Long comntId ;

    @Column(name = "CSE_ID", nullable = false)
    private Long cseId;

    @Column(name = "COMNT", length = 400)
    private String comnt;

    @Column(name = "REVIEW", length = 400)
    private String revw;

    @Column(name = "CR_FLAG", length = 5)
    private String crFlag;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "HR_ID")
    private Long hrId ;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "HR_DATE")
    private Date hrDate ;
    
    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

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

	public Long getComntIdHis() {
		return comntIdHis;
	}

	public void setComntIdHis(Long comntIdHis) {
		this.comntIdHis = comntIdHis;
	}

	public Long getComntId() {
		return comntId;
	}

	public void setComntId(Long comntId) {
		this.comntId = comntId;
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
	
	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_COMNT_REVW_DTL_HIST", "COMNT_ID_HIS" };
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
}
