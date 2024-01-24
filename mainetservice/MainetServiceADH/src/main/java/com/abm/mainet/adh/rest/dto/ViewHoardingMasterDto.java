/**
 * 
 */
package com.abm.mainet.adh.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author vishwanath.s
 *
 */
public class ViewHoardingMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String hoardingNumber;
    private String hoardingOldNumber;
    private Date hoardingRegDate;
    private String hrdFmtDate;
    private String gisId;
    private String location;
    private String hoardingZone1;
    private String hoardingZone2;
    private String hoardingZone3;
    private String hoardingZone4;
    private String hoardingZone5;
    private String hoardingTypeId1;
    private String hoardingTypeId2;
    private String hoardingTypeId3;
    private String hoardingTypeId4;
    private String hoardingTypeId5;
    private String hoardingTypeDesc;
   // private Long displayTypeId;
    private String displayDirection;
    private String hoardingDescription;
    private BigDecimal hoardingLength;
    private BigDecimal hoardingHeight;
    private BigDecimal hoardingArea;
   // private Long hoardingPropTypeId;
    private String hoardingPropertyOwnership;
    private String propOwnerName;
    private Long uidNo;
    private String hoardingFlag;
    private String hoardingStatus;
    private String hoardingRemark;
    private Long deptId;

    private String displayIdDesc;
    private String hoardingTypeIdDesc;


    public String getHoardingNumber() {
        return hoardingNumber;
    }

    public void setHoardingNumber(String hoardingNumber) {
        this.hoardingNumber = hoardingNumber;
    }

    public String getHoardingOldNumber() {
        return hoardingOldNumber;
    }

    public void setHoardingOldNumber(String hoardingOldNumber) {
        this.hoardingOldNumber = hoardingOldNumber;
    }

    public Date getHoardingRegDate() {
        return hoardingRegDate;
    }

    public void setHoardingRegDate(Date hoardingRegDate) {
        this.hoardingRegDate = hoardingRegDate;
    }

    public String getHrdFmtDate() {
        return hrdFmtDate;
    }

    public void setHrdFmtDate(String hrdFmtDate) {
        this.hrdFmtDate = hrdFmtDate;
    }


    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHoardingZone1() {
		return hoardingZone1;
	}

	public void setHoardingZone1(String hoardingZone1) {
		this.hoardingZone1 = hoardingZone1;
	}

	public String getHoardingZone2() {
		return hoardingZone2;
	}

	public void setHoardingZone2(String hoardingZone2) {
		this.hoardingZone2 = hoardingZone2;
	}

	public String getHoardingZone3() {
		return hoardingZone3;
	}

	public void setHoardingZone3(String hoardingZone3) {
		this.hoardingZone3 = hoardingZone3;
	}

	public String getHoardingZone4() {
		return hoardingZone4;
	}

	public void setHoardingZone4(String hoardingZone4) {
		this.hoardingZone4 = hoardingZone4;
	}

	public String getHoardingZone5() {
		return hoardingZone5;
	}

	public void setHoardingZone5(String hoardingZone5) {
		this.hoardingZone5 = hoardingZone5;
	}


    public String getHoardingTypeId1() {
		return hoardingTypeId1;
	}

	public void setHoardingTypeId1(String hoardingTypeId1) {
		this.hoardingTypeId1 = hoardingTypeId1;
	}

	public String getHoardingTypeId2() {
		return hoardingTypeId2;
	}

	public void setHoardingTypeId2(String hoardingTypeId2) {
		this.hoardingTypeId2 = hoardingTypeId2;
	}

	public String getHoardingTypeId3() {
		return hoardingTypeId3;
	}

	public void setHoardingTypeId3(String hoardingTypeId3) {
		this.hoardingTypeId3 = hoardingTypeId3;
	}

	public String getHoardingTypeId4() {
		return hoardingTypeId4;
	}

	public void setHoardingTypeId4(String hoardingTypeId4) {
		this.hoardingTypeId4 = hoardingTypeId4;
	}

	public String getHoardingTypeId5() {
		return hoardingTypeId5;
	}

	public void setHoardingTypeId5(String hoardingTypeId5) {
		this.hoardingTypeId5 = hoardingTypeId5;
	}

	public String getHoardingTypeDesc() {
        return hoardingTypeDesc;
    }

    public void setHoardingTypeDesc(String hoardingTypeDesc) {
        this.hoardingTypeDesc = hoardingTypeDesc;
    }

   

    public String getDisplayDirection() {
		return displayDirection;
	}

	public void setDisplayDirection(String displayDirection) {
		this.displayDirection = displayDirection;
	}

	public String getHoardingDescription() {
        return hoardingDescription;
    }

    public void setHoardingDescription(String hoardingDescription) {
        this.hoardingDescription = hoardingDescription;
    }

    public BigDecimal getHoardingLength() {
        return hoardingLength;
    }

    public void setHoardingLength(BigDecimal hoardingLength) {
        this.hoardingLength = hoardingLength;
    }

    public BigDecimal getHoardingHeight() {
        return hoardingHeight;
    }

    public void setHoardingHeight(BigDecimal hoardingHeight) {
        this.hoardingHeight = hoardingHeight;
    }

    public BigDecimal getHoardingArea() {
        return hoardingArea;
    }

    public void setHoardingArea(BigDecimal hoardingArea) {
        this.hoardingArea = hoardingArea;
    }

   
    public String getHoardingPropertyOwnership() {
		return hoardingPropertyOwnership;
	}

	public void setHoardingPropertyOwnership(String hoardingPropertyOwnership) {
		this.hoardingPropertyOwnership = hoardingPropertyOwnership;
	}

	public String getPropOwnerName() {
        return propOwnerName;
    }

    public void setPropOwnerName(String propOwnerName) {
        this.propOwnerName = propOwnerName;
    }

    public Long getUidNo() {
        return uidNo;
    }

    public void setUidNo(Long uidNo) {
        this.uidNo = uidNo;
    }

    public String getHoardingFlag() {
        return hoardingFlag;
    }

    public void setHoardingFlag(String hoardingFlag) {
        this.hoardingFlag = hoardingFlag;
    }

   
    public String getHoardingStatus() {
		return hoardingStatus;
	}

	public void setHoardingStatus(String hoardingStatus) {
		this.hoardingStatus = hoardingStatus;
	}

	public String getHoardingRemark() {
        return hoardingRemark;
    }

    public void setHoardingRemark(String hoardingRemark) {
        this.hoardingRemark = hoardingRemark;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDisplayIdDesc() {
        return displayIdDesc;
    }

    public void setDisplayIdDesc(String displayIdDesc) {
        this.displayIdDesc = displayIdDesc;
    }

    public String getHoardingTypeIdDesc() {
        return hoardingTypeIdDesc;
    }

    public void setHoardingTypeIdDesc(String hoardingTypeIdDesc) {
        this.hoardingTypeIdDesc = hoardingTypeIdDesc;
    }

	public String getGisId() {
		return gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

}
