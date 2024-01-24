package com.abm.mainet.property.rest.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardRequestDTO  implements Serializable{

	private static final long serialVersionUID = 3837851814405889468L;

	private Date date;
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

