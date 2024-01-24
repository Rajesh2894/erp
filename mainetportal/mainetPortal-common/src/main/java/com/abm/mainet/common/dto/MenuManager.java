package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author pabitra.raulo
 */

public class MenuManager implements Serializable {

    private static final long serialVersionUID = 3138083925327355500L;
    private String moduleName;
    private String serviceType;
    private String deptMenuHTML;
    private String onLineCznMenuHtml;
    private String noONLCznMenuHtml;

    public MenuManager() {
        super();
    }

    public MenuManager(final String moduleName) {
        super();
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDeptMenuHTML() {
        return deptMenuHTML;
    }

    public void setDeptMenuHTML(final String deptMenuHTML) {
        this.deptMenuHTML = deptMenuHTML;
    }

    public String getOnLineCznMenuHtml() {
        return onLineCznMenuHtml;
    }

    public void setOnLineCznMenuHtml(final String onLineCznMenuHtml) {
        this.onLineCznMenuHtml = onLineCznMenuHtml;
    }

    public String getNoONLCznMenuHtml() {
        return noONLCznMenuHtml;
    }

    public void setNoONLCznMenuHtml(final String noONLCznMenuHtml) {
        this.noONLCznMenuHtml = noONLCznMenuHtml;
    }

}
