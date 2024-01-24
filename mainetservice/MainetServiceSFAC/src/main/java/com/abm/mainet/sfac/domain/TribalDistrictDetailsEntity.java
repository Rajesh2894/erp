/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name="Tb_SFAC_Tribal_District")
public class TribalDistrictDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159226332449998678L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "Tri_Dist_Id", unique = true, nullable = false)
	private Long triDistId;
	
	
	@Column(name = "State_Name")
	private String stateName;
	
	@Column(name = "District_Code")
	private Long districtCode; 

	@Column(name = "DISTRICT_NAME")
	private String districtName;
	
	
	@Column(name = "Is_Tribal_District")
	private String isTribalDistrict;
	
	
	
	/**
	 * @return the triDistId
	 */
	public Long getTriDistId() {
		return triDistId;
	}



	/**
	 * @param triDistId the triDistId to set
	 */
	public void setTriDistId(Long triDistId) {
		this.triDistId = triDistId;
	}



	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}



	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}



	/**
	 * @return the districtCode
	 */
	public Long getDistrictCode() {
		return districtCode;
	}



	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}



	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}



	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}



	/**
	 * @return the isTribalDistrict
	 */
	public String getIsTribalDistrict() {
		return isTribalDistrict;
	}



	/**
	 * @param isTribalDistrict the isTribalDistrict to set
	 */
	public void setIsTribalDistrict(String isTribalDistrict) {
		this.isTribalDistrict = isTribalDistrict;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Tribal_District", "CBBO_ID" };
	}
}
