package com.abm.mainet.property.rest.dto;

import java.io.Serializable;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardBucketDTO implements Serializable {


	private static final long serialVersionUID = -6302709822967685417L;


	private String name;
	private Long value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
}
