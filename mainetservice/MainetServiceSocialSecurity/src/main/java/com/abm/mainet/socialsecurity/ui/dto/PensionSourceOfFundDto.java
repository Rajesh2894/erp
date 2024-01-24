/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author satish.rathore
 *
 */
public class PensionSourceOfFundDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853500050502809229L;
	private Long sponcerId;
	private BigDecimal sharingAmt;
	private Long penFundId;
	private Long bdYearId;
	public Long getPenFundId() {
		return penFundId;
	}
	public void setPenFundId(Long penFundId) {
		this.penFundId = penFundId;
	}
	public Long getSponcerId() {
		return sponcerId;
	}
	public void setSponcerId(Long sponcerId) {
		this.sponcerId = sponcerId;
	}
	public BigDecimal getSharingAmt() {
		return sharingAmt;
	}
	public void setSharingAmt(BigDecimal sharingAmt) {
		this.sharingAmt = sharingAmt;
	}
	public Long getBdYearId() {
		return bdYearId;
	}
	public void setBdYearId(Long bdYearId) {
		this.bdYearId = bdYearId;
	}

}
