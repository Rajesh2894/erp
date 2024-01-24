package com.abm.mainet.firemanagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.firemanagement.dto.VehicleLogBookReportDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VehicleLogBookReportModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VehicleLogBookReportDTO vehicleLogBookReportDTO=new VehicleLogBookReportDTO();

	public VehicleLogBookReportDTO getVehicleLogBookReportDTO() {
		return vehicleLogBookReportDTO;
	}

	public void setVehicleLogBookReportDTO(VehicleLogBookReportDTO vehicleLogBookReportDTO) {
		this.vehicleLogBookReportDTO = vehicleLogBookReportDTO;
	}

	
	
}
