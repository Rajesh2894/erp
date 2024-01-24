package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.ibm.icu.math.BigDecimal;

@Entity
@Table(name = "TB_LGL_ADV_EDUDETAILS")
public class AdvocateEducationDetails  implements Serializable{

    private static final long serialVersionUID = -761930300834661826L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EDU_ID", unique = true, nullable = false)
    private Long eduId;

    @Column(name = "QUALIFICATION_COURSE", length = 500)
    private String qualificationCourse;

    @Column(name = "INSTITUTE_STATE",length = 500)
    private String instituteState;

    @Column(name = "BOARD_UNIVERSITY",length = 500)
    private String boardUniversity;

    @Column(name = "RESULT",length = 50)
    private String result;

    @Column(name = "PASSING_YEAR",length = 100)
    private Long passingYear;
    
    @Column(name = "PERCENTAGE")
    private Double percentage;

  
    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    // bi-directional many-to-one association to tbLglAdvMaster
    @ManyToOne
    @JoinColumn(name = "ADV_ID", referencedColumnName = "ADV_ID")
    private AdvocateMaster tbLglAdvMaster;
    
    
    public Long getEduId() {
		return eduId;
	}

	public void setEduId(Long eduId) {
		this.eduId = eduId;
	}

	public String getQualificationCourse() {
		return qualificationCourse;
	}

	public void setQualificationCourse(String qualificationCourse) {
		this.qualificationCourse = qualificationCourse;
	}

	public String getInstituteState() {
		return instituteState;
	}

	public void setInstituteState(String instituteState) {
		this.instituteState = instituteState;
	}

	public String getBoardUniversity() {
		return boardUniversity;
	}

	public void setBoardUniversity(String boardUniversity) {
		this.boardUniversity = boardUniversity;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(Long passingYear) {
		this.passingYear = passingYear;
	}


	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public AdvocateMaster getTbLglAdvMaster() {
		return tbLglAdvMaster;
	}

	public void setTbLglAdvMaster(AdvocateMaster tbLglAdvMaster) {
		this.tbLglAdvMaster = tbLglAdvMaster;
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

 
    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_ADV_EDUDETAILS", "EDU_ID" };
    }
}
