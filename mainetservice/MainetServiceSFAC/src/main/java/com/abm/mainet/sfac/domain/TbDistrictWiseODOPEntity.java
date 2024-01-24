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
@Table(name="Tb_SFAC_District_Wise_ODOP")
public class TbDistrictWiseODOPEntity implements Serializable{


	private static final long serialVersionUID = 4159226332449998678L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "Odop_Id", unique = true, nullable = false)
	private Long odopId;
	
	
	@Column(name = "STATE")
	private String StateName;
	
	@Column(name = "DISTRICT_CODE")
	private Long districtCode; 

	@Column(name = "DISTRICT")
	private String districtName;
	
	
	@Column(name = "ODOP")
	private String odop;
	


	/**
	 * @return the odopId
	 */
	public Long getOdopId() {
		return odopId;
	}






	/**
	 * @param odopId the odopId to set
	 */
	public void setOdopId(Long odopId) {
		this.odopId = odopId;
	}






	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return StateName;
	}






	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		StateName = stateName;
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
	 * @return the odop
	 */
	public String getOdop() {
		return odop;
	}






	/**
	 * @param odop the odop to set
	 */
	public void setOdop(String odop) {
		this.odop = odop;
	}






	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_District_Wise_ODOP", "Odop_Id" };
	}

}
