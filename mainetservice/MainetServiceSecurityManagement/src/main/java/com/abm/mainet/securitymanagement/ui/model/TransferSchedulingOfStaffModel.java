package com.abm.mainet.securitymanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.dto.TransferSchedulingOfStaffDTO;
import com.abm.mainet.securitymanagement.service.ITransferSchedulingOfStaffService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TransferSchedulingOfStaffModel extends AbstractFormModel {
	private List<LookUp> lookup= new ArrayList<LookUp>();

	/**
	 * 
	 */
	@Autowired
	ITransferSchedulingOfStaffService transferSchedulingOfStaffService;

	private static final long serialVersionUID = 1L;

	private TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO = new TransferSchedulingOfStaffDTO();
	private List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList = new ArrayList<>();
	private List<TbLocationMas> location=new ArrayList<TbLocationMas>();
	private List<ContractualStaffMasterDTO> empNameList=new ArrayList<ContractualStaffMasterDTO>();
	private List<TbAcVendormaster> vendorList=new ArrayList<TbAcVendormaster>();
	String mode;

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		transferSchedulingOfStaffDTO.setOrgid(orgId);
		transferSchedulingOfStaffDTO.setCreatedBy(createdBy);
		transferSchedulingOfStaffDTO.setCreatedDate(createdDate);
		transferSchedulingOfStaffDTO.setLgIpMac(lgIpMac);

		List<TransferSchedulingOfStaffDTO> staffList = transferSchedulingOfStaffService
				.checkIfStaffExists(transferSchedulingOfStaffDTOList, transferSchedulingOfStaffDTO);
		Long countNew = 0l;
		String messageNew = null;
		for (int i = 0; i < staffList.size(); i++) {
			if (staffList.get(i).getMessageDate() == "true") {
				messageNew = "t";
				addValidationError(getAppSession().getMessage("TransferSchedulingOfStaffDTO.Validation.fromToAndAppointmentDate"));
			} else if (staffList.get(i).getCount() != null && staffList.get(i).getCount() != 0) {
				countNew = 1l;
				addValidationError(getAppSession().getMessage("TransferSchedulingOfStaffDTO.Validation.presentEmployee"));
			}
		}
		if (countNew ==0 && messageNew==null) {
			transferSchedulingOfStaffService.saveOrUpdate(transferSchedulingOfStaffDTOList,transferSchedulingOfStaffDTO);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("TransferSchedulingOfStaffDTO.Validation.save"));
		}
		if (hasValidationErrors()) {
			return false;
		} else {
			return true;
		}
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<TbLocationMas> getLocation() {
		return location;
	}
	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}
	public List<ContractualStaffMasterDTO> getEmpNameList() {
		return empNameList;
	}
	public void setEmpNameList(List<ContractualStaffMasterDTO> empNameList) {
		this.empNameList = empNameList;
	}
	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}
	public List<TransferSchedulingOfStaffDTO> getTransferSchedulingOfStaffDTOList() {
		return transferSchedulingOfStaffDTOList;
	}
	public void setTransferSchedulingOfStaffDTOList(
			List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList) {
		this.transferSchedulingOfStaffDTOList = transferSchedulingOfStaffDTOList;
	}
	public TransferSchedulingOfStaffDTO getTransferSchedulingOfStaffDTO() {
		return transferSchedulingOfStaffDTO;
	}
	public void setTransferSchedulingOfStaffDTO(TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO) {
		this.transferSchedulingOfStaffDTO = transferSchedulingOfStaffDTO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<LookUp> getLookup() {
		return lookup;
	}
	public void setLookup(List<LookUp> lookup) {
		this.lookup = lookup;
	}
}
