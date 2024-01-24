package com.abm.mainet.common.domain;

import java.io.Serializable;

/**
 * @author nirmal.mahanta
 *
 */
public class ScrutinyServiceDto implements Serializable {
    private static final long serialVersionUID = 3571234425406988116L;

    private Long smServiceId;
    private String smServiceName;
    private String smShortDesc;
    private String smServiceNameMar;
    private String departmentName;
    private String departmentNameMar;
    private Long triCod1;
    private String triDesc;

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the smServiceName
     */
    public String getSmServiceName() {
        return smServiceName;
    }

    /**
     * @param smServiceName the smServiceName to set
     */
    public void setSmServiceName(final String smServiceName) {
        this.smServiceName = smServiceName;
    }

    /**
     * @return the smServiceNameMar
     */
    public String getSmServiceNameMar() {
        return smServiceNameMar;
    }

    /**
     * @param smServiceNameMar the smServiceNameMar to set
     */
    public void setSmServiceNameMar(final String smServiceNameMar) {
        this.smServiceNameMar = smServiceNameMar;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the departmentNameMar
     */
    public String getDepartmentNameMar() {
        return departmentNameMar;
    }

    /**
     * @param departmentNameMar the departmentNameMar to set
     */
    public void setDepartmentNameMar(final String departmentNameMar) {
        this.departmentNameMar = departmentNameMar;
    }

	public String getSmShortDesc() {
		return smShortDesc;
	}

	public void setSmShortDesc(String smShortDesc) {
		this.smShortDesc = smShortDesc;
	}

	public Long getTriCod1() {
		return triCod1;
	}

	public void setTriCod1(Long triCod1) {
		this.triCod1 = triCod1;
	}

	public String getTriDesc() {
		return triDesc;
	}

	public void setTriDesc(String triDesc) {
		this.triDesc = triDesc;
	}
    
    
}
