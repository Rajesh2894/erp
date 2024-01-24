package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDetDTO;
import com.abm.mainet.vehiclemanagement.service.IOEMWarrantyService;

@Component
@Scope("session")
public class OEMWarrantyModel extends AbstractFormModel {

	private static final long serialVersionUID = -7937577758172328877L;

	private OEMWarrantyDTO oemWarrantyDto;
	private OEMWarrantyDTO oemWarrantyDtos = new OEMWarrantyDTO();
	private List<OEMWarrantyDTO> oemList = new ArrayList<>();
	private List<OEMWarrantyDetDTO> oemWarrantyDetDTOs = new ArrayList<>();

	private OEMWarrantyDetDTO oemWarrantyDetDTO = null;

	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();

	private String saveMode;
	private Long DeptId;

	@Autowired
	private IOEMWarrantyService oemService;

	@Override
	public boolean saveForm() {
		boolean status = false;
		// if (saveMode != "E")
		// validateBean(oemWarrantyDto, VehicleScheduleValidator.class);
		/*
		 * if (!hasValidationErrors()) { checkForValidCollectionAndTime(oemWarrantyDto);
		 * }
		 */
		//Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
	//	Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
	//	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		if (hasValidationErrors()) {
			return false;
		} else {
			if (saveMode != "E") {
				// checkVehicleSheduleDay(oemWarrantyDto);
			}
			List<OEMWarrantyDetDTO> detList = oemWarrantyDto.getTbvmoemwarrantydetails();
			if (detList != null && !detList.isEmpty()) {
				for (OEMWarrantyDetDTO oemDetails : detList) {
					if (oemDetails.getOemDetId() == null) {
						oemDetails.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
						oemDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						oemDetails.setCreatedDate(new Date());
						oemDetails.setLgIpMac(lgIpMac);
						oemDetails.setTboemwarranty(oemWarrantyDto);
					}
					else {
						oemDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						oemDetails.setUpdatedDate(new Date());
						oemDetails.setLgIpMacUpd(lgIpMac);
						oemDetails.setTboemwarranty(oemWarrantyDto);
						oemService.saveOemWarranty(oemWarrantyDto);
						this.setSuccessMessage(getAppSession().getMessage("oem.warranty.edit.success"));
					}
				}
				oemWarrantyDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				oemWarrantyDto.setCreatedDate(new Date());
				oemWarrantyDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				oemService.saveOemWarranty(oemWarrantyDto);
				this.setSuccessMessage(getAppSession().getMessage("vehiecle.schedule.save.success"));
				
			}

			if (oemWarrantyDto.getOemId() == null) {
				oemWarrantyDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				oemWarrantyDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				oemWarrantyDto.setCreatedDate(new Date());
				oemWarrantyDto.setLgIpMac(lgIpMac);

			} else {
				oemWarrantyDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				oemWarrantyDto.setUpdatedDate(new Date());
				oemWarrantyDto.setLgIpMacUpd(lgIpMac);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("oem.warranty.edit.success"));
			}

			/*
			 * if (saveMode != "E") { if (oemWarrantyDto.getOemId() == null) {
			 * oemService.saveOemWarranty(oemWarrantyDto); } else {
			 * oemService.saveOemWarranty(oemWarrantyDto);
			 * //oemService.updateOemWarranty(oemWarrantyDto); }
			 * setSuccessMessage(ApplicationSession.getInstance().getMessage(
			 * "oemWarrantyDto.add.success")); status = true; }
			 */
		}
		this.setSuccessMessage(getAppSession().getMessage("oem.warranty.save.success"));
		return true;

	}

	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public OEMWarrantyDTO getOemWarrantyDto() {
		return oemWarrantyDto;
	}

	public void setOemWarrantyDto(OEMWarrantyDTO oemWarrantyDto) {
		this.oemWarrantyDto = oemWarrantyDto;
	}

	public OEMWarrantyDTO getOemWarrantyDtos() {
		return oemWarrantyDtos;
	}

	public void setOemWarrantyDtos(OEMWarrantyDTO oemWarrantyDtos) {
		this.oemWarrantyDtos = oemWarrantyDtos;
	}

	public List<OEMWarrantyDetDTO> getOemWarrantyDetDTOs() {
		return oemWarrantyDetDTOs;
	}

	public void setOemWarrantyDetDTOs(List<OEMWarrantyDetDTO> oemWarrantyDetDTOs) {
		this.oemWarrantyDetDTOs = oemWarrantyDetDTOs;
	}

	public OEMWarrantyDetDTO getOemWarrantyDetDTO() {
		return oemWarrantyDetDTO;
	}

	public void setOemWarrantyDetDTO(OEMWarrantyDetDTO oemWarrantyDetDTO) {
		this.oemWarrantyDetDTO = oemWarrantyDetDTO;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public List<OEMWarrantyDTO> getOemList() {
		return oemList;
	}

	public void setOemList(List<OEMWarrantyDTO> oemList) {
		this.oemList = oemList;
	}

	public IOEMWarrantyService getOemService() {
		return oemService;
	}

	public void setOemService(IOEMWarrantyService oemService) {
		this.oemService = oemService;
	}

}
