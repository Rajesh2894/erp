package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardRequestDTO  implements Serializable{

	private static final long serialVersionUID = 3837851814405889468L;

	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
