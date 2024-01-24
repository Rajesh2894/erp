package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 30 April 2018
 */
public class ViewWorkDefinitionSancDetDto implements Serializable {

    private static final long serialVersionUID = 609452834699325828L;

    private Long workSancId;

    private Long workId;

    private Long deptId;

    private String workSancNo;

    private Date workSancDate;

    private String workSancBy;

    private String workDesignBy;


    public Long getWorkSancId() {
        return workSancId;
    }

    public void setWorkSancId(Long workSancId) {
        this.workSancId = workSancId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getWorkSancNo() {
        return workSancNo;
    }

    public void setWorkSancNo(String workSancNo) {
        this.workSancNo = workSancNo;
    }

    public Date getWorkSancDate() {
        return workSancDate;
    }

    public void setWorkSancDate(Date workSancDate) {
        this.workSancDate = workSancDate;
    }

    public String getWorkSancBy() {
        return workSancBy;
    }

    public void setWorkSancBy(String workSancBy) {
        this.workSancBy = workSancBy;
    }

    public String getWorkDesignBy() {
        return workDesignBy;
    }

    public void setWorkDesignBy(String workDesignBy) {
        this.workDesignBy = workDesignBy;
    }


	@Override
	public String toString() {
		return "ViewWorkDefinitionSancDetDto [workSancId=" + workSancId + ", workId=" + workId + ", deptId=" + deptId
				+ ", workSancNo=" + workSancNo + ", workSancDate=" + workSancDate + ", workSancBy=" + workSancBy
				+ ", workDesignBy=" + workDesignBy + "]";
	}

    

}
