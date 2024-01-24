/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author pooja.maske
 *
 */
public class CommonMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210279574908190264L;

	private Long id;

	private String name;

	private String iaName;

	private String shortCode;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the iaName
	 */
	public String getIaName() {
		return iaName;
	}

	/**
	 * @param iaName the iaName to set
	 */
	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

}
