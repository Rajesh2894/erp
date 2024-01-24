/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;

import javax.ws.rs.HeaderParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AuditDetails")
public class AuditDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6326381699255927181L;
	@HeaderParam("empId")
	private Long empId;
	@HeaderParam("empIpMac")
	private String empIpMac;

	/**
	 * @return the empId
	 */
	public Long getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	/**
	 * @return the empIpMac
	 */
	public String getEmpIpMac() {
		return empIpMac;
	}

	/**
	 * @param empIpMac
	 *            the empIpMac to set
	 */
	public void setEmpIpMac(String empIpMac) {
		this.empIpMac = empIpMac;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuditDetailsDTO [empId=" + empId + ", empIpMac=" + empIpMac + "]";
	}

}
