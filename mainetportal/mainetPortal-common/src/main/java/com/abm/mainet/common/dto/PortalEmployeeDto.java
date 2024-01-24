package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * this DTO used to get default employee details (Administrator) from service while creating organization
 * @author hiren.poriya
 * @Since 14-Sep-2018
 */
public class PortalEmployeeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String empname;
    private String empmname;
    private String emplname;
    private String emploginname;
    private String emppassword;
    private String empmobno;
    private String empemail;

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpmname() {
        return empmname;
    }

    public void setEmpmname(String empmname) {
        this.empmname = empmname;
    }

    public String getEmplname() {
        return emplname;
    }

    public void setEmplname(String emplname) {
        this.emplname = emplname;
    }

    public String getEmploginname() {
        return emploginname;
    }

    public void setEmploginname(String emploginname) {
        this.emploginname = emploginname;
    }

    public String getEmppassword() {
        return emppassword;
    }

    public void setEmppassword(String emppassword) {
        this.emppassword = emppassword;
    }

    public String getEmpmobno() {
        return empmobno;
    }

    public void setEmpmobno(String empmobno) {
        this.empmobno = empmobno;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(String empemail) {
        this.empemail = empemail;
    }

}
