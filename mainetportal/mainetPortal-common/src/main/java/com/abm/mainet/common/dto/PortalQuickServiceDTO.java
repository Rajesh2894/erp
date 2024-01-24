package com.abm.mainet.common.dto;

import java.io.Serializable;

public class PortalQuickServiceDTO implements Serializable {

    private static final long serialVersionUID = -2059692481646888764L;

    private long serviceId;
    private String serviceurl;
    private String serviceCode;
    private String serviceName;
    private String serviceNamemar;

    private long departmentid;

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the serviceurl
     */
    public String getServiceurl() {
        return serviceurl;
    }

    /**
     * @param serviceurl the serviceurl to set
     */
    public void setServiceurl(final String serviceurl) {
        this.serviceurl = serviceurl;
    }

    /**
     * @return the departmentid
     */
    public long getDepartmentid() {
        return departmentid;
    }

    /**
     * @param departmentid the departmentid to set
     */
    public void setDepartmentid(final long departmentid) {
        this.departmentid = departmentid;
    }

    public String getServiceNamemar() {
        return serviceNamemar;
    }

    public void setServiceNamemar(final String serviceNamemar) {
        this.serviceNamemar = serviceNamemar;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

}
