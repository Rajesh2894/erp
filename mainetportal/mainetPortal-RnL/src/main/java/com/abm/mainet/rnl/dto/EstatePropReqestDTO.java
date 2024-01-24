package com.abm.mainet.rnl.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class EstatePropReqestDTO implements Serializable {

    private static final long serialVersionUID = -4430603056589771792L;
    private Long orgId;
    private Integer categoryTypeId;
    private String prefixName;
    private String cpdValue;
    private Integer type;
    private Long propId;
    private String fromDate;
    private String toDate;
    private Long eventId;
    private Long capcityFrom;
    private Long capcityTo;
    private Double rentFrom;
    private Double rentTo;
    private Long booingId;

    public EstatePropReqestDTO() {
    }

    /**
     * @param orgId
     * @param categoryTypeId
     * @param prefixName
     * @param cpdValue
     * @param type
     * @param propId
     * @param fromDate
     * @param toDate
     */
    public EstatePropReqestDTO(final Long orgId, final Integer categoryTypeId,
            final String prefixName, final String cpdValue, final Integer type, final Long propId,
            final String fromDate, final String toDate) {
        super();
        this.orgId = orgId;
        this.categoryTypeId = categoryTypeId;
        this.prefixName = prefixName;
        this.cpdValue = cpdValue;
        this.type = type;
        this.propId = propId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the categoryTypeId
     */
    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    /**
     * @param categoryTypeId the categoryTypeId to set
     */
    public void setCategoryTypeId(final Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    /**
     * @return the prefixName
     */
    public String getPrefixName() {
        return prefixName;
    }

    /**
     * @param prefixName the prefixName to set
     */
    public void setPrefixName(final String prefixName) {
        this.prefixName = prefixName;
    }

    /**
     * @return the cpdValue
     */
    public String getCpdValue() {
        return cpdValue;
    }

    /**
     * @param cpdValue the cpdValue to set
     */
    public void setCpdValue(final String cpdValue) {
        this.cpdValue = cpdValue;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final Integer type) {
        this.type = type;
    }

    /**
     * @return the propId
     */
    public Long getPropId() {
        return propId;
    }

    /**
     * @param propId the propId to set
     */
    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCapcityFrom() {
		return capcityFrom;
	}

	public void setCapcityFrom(Long capcityFrom) {
		this.capcityFrom = capcityFrom;
	}

	public Long getCapcityTo() {
		return capcityTo;
	}

	public void setCapcityTo(Long capcityTo) {
		this.capcityTo = capcityTo;
	}

	public Double getRentFrom() {
		return rentFrom;
	}

	public void setRentFrom(Double rentFrom) {
		this.rentFrom = rentFrom;
	}

	public Double getRentTo() {
		return rentTo;
	}

	public void setRentTo(Double rentTo) {
		this.rentTo = rentTo;
	}

	public Long getBooingId() {
        return booingId;
    }

    public void setBooingId(Long booingId) {
        this.booingId = booingId;
    }

}
