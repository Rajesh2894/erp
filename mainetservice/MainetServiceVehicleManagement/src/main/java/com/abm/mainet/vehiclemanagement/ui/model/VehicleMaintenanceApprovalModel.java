package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VehicleMaintenanceApprovalModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;
	
	 private VehicleMaintenanceDTO vehicleMaintenanceDTO = new VehicleMaintenanceDTO();
	 
	 private List<TbAcVendormaster> vendors;
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private long levelcheck;



	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}

	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}

	public VehicleMaintenanceDTO getVehicleMaintenanceDTO() {
		return vehicleMaintenanceDTO;
	}

	public void setVehicleMaintenanceDTO(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
		this.vehicleMaintenanceDTO = vehicleMaintenanceDTO;
	}
	
}
