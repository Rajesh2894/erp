package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class EstatePropResponseDTO implements Serializable {

    private static final long serialVersionUID = -3658450754551776495L;
    private Long propId;
    private String propName;
    private String category;
    private String categoryReg;
    private String location;
    private String locationReg;
    private String estateCode;
    private String estateName;
    private String estateNameReg;
    private String propertyNo;
    private String occupancy;
    private String usage;
    private String unit;
    private String floor;
    private String totalArea;
    private String type;
    private String subType;
    private String roadType;
    private String occupancyForm;
    private String usageForm;
    private String floorForm;
    private String typeForm;
    private String subTypeForm;
    private String roadTypeForm;
    private double totalRent;
    private Long capacity;
    private Date fromDate;
    private Date toDate;
    private String applicantName;
    private Long bookingId;
    private Long applicationId;
    private Long receiptId;
    private  Long receiptNo;
    private Long serviceId;
    private Long orgId;
    
    private List<EstatePropertyEventDTO> eventDTOList;
    private List<EstatePropertyShiftDTO> shiftDTOsList;
    private Long noOfDaysAllowed;
    private List<EstatePropertyAmenityDTO> amenityDTOsList;
    private List<EstatePropertyAmenityDTO> facilityDtoList;
    
    private String fDate;
    private String tDate;
    private String apaMobilno;

    public EstatePropResponseDTO() {
        super();
    }

    /**
     * @param propId
     * @param propName
     * @param category
     * @param categoryReg
     * @param location
     * @param locationReg
     */
    public EstatePropResponseDTO(final Long propId, final String propName, final String category, final String categoryReg,
            final String location,
            final String locationReg) {
        super();
        this.propId = propId;
        this.propName = propName;
        this.category = category;
        this.categoryReg = categoryReg;
        this.location = location;
        this.locationReg = locationReg;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(final String propName) {
        this.propName = propName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * @return the categoryReg
     */
    public String getCategoryReg() {
        return categoryReg;
    }

    /**
     * @param categoryReg the categoryReg to set
     */
    public void setCategoryReg(final String categoryReg) {
        this.categoryReg = categoryReg;
    }

    /**
     * @return the locationReg
     */
    public String getLocationReg() {
        return locationReg;
    }

    /**
     * @param locationReg the locationReg to set
     */
    public void setLocationReg(final String locationReg) {
        this.locationReg = locationReg;
    }

    /**
     * @return the estateCode
     */
    public String getEstateCode() {
        return estateCode;
    }

    /**
     * @param estateCode the estateCode to set
     */
    public void setEstateCode(final String estateCode) {
        this.estateCode = estateCode;
    }

    /**
     * @return the estateName
     */
    public String getEstateName() {
        return estateName;
    }

    /**
     * @param estateName the estateName to set
     */
    public void setEstateName(final String estateName) {
        this.estateName = estateName;
    }

    /**
     * @return the propertyNo
     */
    public String getPropertyNo() {
        return propertyNo;
    }

    /**
     * @param propertyNo the propertyNo to set
     */
    public void setPropertyNo(final String propertyNo) {
        this.propertyNo = propertyNo;
    }

    /**
     * @return the occupancy
     */
    public String getOccupancy() {
        return occupancy;
    }

    /**
     * @param occupancy the occupancy to set
     */
    public void setOccupancy(final String occupancy) {
        this.occupancy = occupancy;
    }

    /**
     * @return the usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @param usage the usage to set
     */
    public void setUsage(final String usage) {
        this.usage = usage;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }

    /**
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(final String floor) {
        this.floor = floor;
    }

    /**
     * @return the totalArea
     */
    public String getTotalArea() {
        return totalArea;
    }

    /**
     * @param totalArea the totalArea to set
     */
    public void setTotalArea(final String totalArea) {
        this.totalArea = totalArea;
    }

    /**
     * @return the estateNameReg
     */
    public String getEstateNameReg() {
        return estateNameReg;
    }

    /**
     * @param estateNameReg the estateNameReg to set
     */
    public void setEstateNameReg(final String estateNameReg) {
        this.estateNameReg = estateNameReg;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType the subType to set
     */
    public void setSubType(final String subType) {
        this.subType = subType;
    }

    /**
     * @return the roadType
     */
    public String getRoadType() {
        return roadType;
    }

    /**
     * @param roadType the roadType to set
     */
    public void setRoadType(final String roadType) {
        this.roadType = roadType;
    }

    /**
     * @return the occupancyForm
     */
    public String getOccupancyForm() {
        return occupancyForm;
    }

    /**
     * @param occupancyForm the occupancyForm to set
     */
    public void setOccupancyForm(final String occupancyForm) {
        this.occupancyForm = occupancyForm;
    }

    /**
     * @return the usageForm
     */
    public String getUsageForm() {
        return usageForm;
    }

    /**
     * @param usageForm the usageForm to set
     */
    public void setUsageForm(final String usageForm) {
        this.usageForm = usageForm;
    }

    /**
     * @return the floorForm
     */
    public String getFloorForm() {
        return floorForm;
    }

    /**
     * @param floorForm the floorForm to set
     */
    public void setFloorForm(final String floorForm) {
        this.floorForm = floorForm;
    }

    /**
     * @return the typeForm
     */
    public String getTypeForm() {
        return typeForm;
    }

    /**
     * @param typeForm the typeForm to set
     */
    public void setTypeForm(final String typeForm) {
        this.typeForm = typeForm;
    }

    /**
     * @return the subTypeForm
     */
    public String getSubTypeForm() {
        return subTypeForm;
    }

    /**
     * @param subTypeForm the subTypeForm to set
     */
    public void setSubTypeForm(final String subTypeForm) {
        this.subTypeForm = subTypeForm;
    }

    /**
     * @return the roadTypeForm
     */
    public String getRoadTypeForm() {
        return roadTypeForm;
    }

    /**
     * @param roadTypeForm the roadTypeForm to set
     */
    public void setRoadTypeForm(final String roadTypeForm) {
        this.roadTypeForm = roadTypeForm;
    }

    public double getTotalRent() {
        return totalRent;
    }

    public void setTotalRent(double totalRent) {
        this.totalRent = totalRent;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public List<EstatePropertyEventDTO> getEventDTOList() {
        return eventDTOList;
    }

    public void setEventDTOList(List<EstatePropertyEventDTO> eventDTOList) {
        this.eventDTOList = eventDTOList;
    }

    public List<EstatePropertyShiftDTO> getShiftDTOsList() {
        return shiftDTOsList;
    }

    public void setShiftDTOsList(List<EstatePropertyShiftDTO> shiftDTOsList) {
        this.shiftDTOsList = shiftDTOsList;
    }

    public Long getNoOfDaysAllowed() {
        return noOfDaysAllowed;
    }

    public void setNoOfDaysAllowed(Long noOfDaysAllowed) {
        this.noOfDaysAllowed = noOfDaysAllowed;
    }

    public List<EstatePropertyAmenityDTO> getAmenityDTOsList() {
        return amenityDTOsList;
    }

    public void setAmenityDTOsList(List<EstatePropertyAmenityDTO> amenityDTOsList) {
        this.amenityDTOsList = amenityDTOsList;
    }

    public List<EstatePropertyAmenityDTO> getFacilityDtoList() {
        return facilityDtoList;
    }

    public void setFacilityDtoList(List<EstatePropertyAmenityDTO> facilityDtoList) {
        this.facilityDtoList = facilityDtoList;
    }

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getfDate() {
		return fDate;
	}

	public void setfDate(String fDate) {
		this.fDate = fDate;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

	public String getApaMobilno() {
		return apaMobilno;
	}

	public void setApaMobilno(String apaMobilno) {
		this.apaMobilno = apaMobilno;
	}

    
}
