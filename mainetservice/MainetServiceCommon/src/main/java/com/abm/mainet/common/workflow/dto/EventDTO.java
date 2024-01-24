package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class EventDTO implements Serializable {

    private static final long serialVersionUID = 2909676960079814154L;
    private long eventId;
    private String eventDesc;
    private String eventName;
    private String eventNameReg;
    private String serviceUrl;
    private long serviceEventId;
    private String serviceName;
    private String serviceNameReg;
    private Long serviceId;
    private String isdeleted;

    /**
     * @return the serviceNameReg
     */
    public String getServiceNameReg() {
        return serviceNameReg;
    }

    /**
     * @param serviceNameReg the serviceNameReg to set
     */
    public void setServiceNameReg(final String serviceNameReg) {
        this.serviceNameReg = serviceNameReg;
    }

    /**
     * @return the eventId
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(final long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the eventDesc
     */
    public String getEventDesc() {
        return eventDesc;
    }

    /**
     * @param eventDesc the eventDesc to set
     */
    public void setEventDesc(final String eventDesc) {
        this.eventDesc = eventDesc;
    }

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the eventNameReg
     */
    public String getEventNameReg() {
        return eventNameReg;
    }

    /**
     * @param eventNameReg the eventNameReg to set
     */
    public void setEventNameReg(final String eventNameReg) {
        this.eventNameReg = eventNameReg;
    }

    /**
     * @return the serviceUrl
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl the serviceUrl to set
     */
    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * @return the serviceEventId
     */
    public long getServiceEventId() {
        return serviceEventId;
    }

    /**
     * @param serviceEventId the serviceEventId to set
     */
    public void setServiceEventId(final long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

}
