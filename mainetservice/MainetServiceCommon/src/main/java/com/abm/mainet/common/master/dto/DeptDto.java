package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dpDeptid;
    
    private String dpDeptdesc;
    

    public Long getDpDeptid() {
		return dpDeptid;
	}

	public void setDpDeptid(Long dpDeptid) {
		this.dpDeptid = dpDeptid;
	}

	public String getDpDeptdesc() {
		return dpDeptdesc;
	}

	public void setDpDeptdesc(String dpDeptdesc) {
		this.dpDeptdesc = dpDeptdesc;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dpDeptid == null) ? 0 : dpDeptid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeptDto other = (DeptDto) obj;
        if (dpDeptid == null) {
            if (other.getDpDeptid() != null)
                return false;
        } else if (!dpDeptid.equals(other.getDpDeptid()))
            return false;
        return true;
    }

}
