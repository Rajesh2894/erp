package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author Harsha.Ramachandran
 *
 */
public class TbDepartmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dpDeptcode;
    private String dpDeptDesc;
    private String dpNameMar;

    /*
     * Getters and setters
     */
    public String getDpDeptcode() {
        return dpDeptcode;
    }

    public void setDpDeptcode(final String dpDeptcode) {
        this.dpDeptcode = dpDeptcode;
    }

    public String getDpDeptDesc() {
        return dpDeptDesc;
    }

    public void setDpDeptDesc(final String dpDeptDesc) {
        this.dpDeptDesc = dpDeptDesc;
    }

    public String getDpNameMar() {
        return dpNameMar;
    }

    public void setDpNameMar(final String dpNameMar) {
        this.dpNameMar = dpNameMar;
    }

}
