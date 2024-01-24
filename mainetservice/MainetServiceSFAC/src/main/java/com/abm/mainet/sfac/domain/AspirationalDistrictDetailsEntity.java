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
@Table(name="Tb_SFAC_Aspirational_District")
public class AspirationalDistrictDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1603060630631088466L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "Aspi_dist_id", unique = true, nullable = false)
	private Long aspiDistId;
	
	@Column(name = "State_Name")
	private String stateName;
	
	@Column(name = "District_Code")
	private Long districtCode; 

	@Column(name = "DISTRICT_NAME")
	private String districtName;
	
	
	@Column(name = "COVERED")
	private String Covered;
	
	
	
	

	/**
	 * @return the aspiDistId
	 */
	public Long getAspiDistId() {
		return aspiDistId;
	}





	/**
	 * @param aspiDistId the aspiDistId to set
	 */
	public void setAspiDistId(Long aspiDistId) {
		this.aspiDistId = aspiDistId;
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
	 * @return the covered
	 */
	public String getCovered() {
		return Covered;
	}





	/**
	 * @param covered the covered to set
	 */
	public void setCovered(String covered) {
		Covered = covered;
	}





	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Aspirational_District", "Aspi_dist_id" };
	}

}
