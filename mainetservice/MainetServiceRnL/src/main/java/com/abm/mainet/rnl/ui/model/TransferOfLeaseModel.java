/**
 * 
 */
package com.abm.mainet.rnl.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.service.ITransferOfLeaseService;


/**
 * @author divya.marshettiwar
 *
 */
@Component
@Scope("session")
public class TransferOfLeaseModel extends AbstractFormModel{

	private static final long serialVersionUID = -2529602885347218272L;
	
	@Autowired
    private TbDepartmentService iTbDepartmentService;
	
	@Autowired
	private ITransferOfLeaseService transferOfLeaseService;

	private ContractMastDTO contractMasterDto = new ContractMastDTO();
	private List<TbAcVendormaster> vendorList = null;
	Long vendorId;
	Double appreciationRate;
	
	@Override
	public boolean saveForm() {
		boolean status = false;
		final UserSession session = UserSession.getCurrent();
		
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long deptId = dept.getDpDeptid();
		
		contractMasterDto.setOrgId(session.getOrganisation().getOrgid());
		contractMasterDto.setLgIpMacUpd(getClientIpAddress());
		contractMasterDto.setCreatedBy(session.getEmployee().getEmpId());
		contractMasterDto.setUpdatedDate(new Date());
		contractMasterDto.setUpdatedBy(session.getEmployee().getEmpId());
		contractMasterDto.setLgIpMac(getClientIpAddress());
		contractMasterDto.setLangId(session.getLanguageId());
		contractMasterDto.setContDept(deptId);
		vendorId = getVendorId();
		appreciationRate = getAppreciationRate();
		if(vendorId != null && appreciationRate != null) {
			if(contractMasterDto.getContNo().isEmpty() || contractMasterDto.getContNo() == null) {
				addValidationError(getAppSession().getMessage("rnl.enter.contNo"));
				return false;
			}
			transferOfLeaseService.updateTransferOfLeaseData(contractMasterDto, appreciationRate, vendorId);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.notice.contractNo") + " " +
					contractMasterDto.getContNo() + " " + 
					ApplicationSession.getInstance().getMessage("rnl.updatedSuccessfully"));
			status = true;
		}else {
			if(vendorId == null && appreciationRate == null && contractMasterDto.getContNo().isEmpty()) {
				addValidationError(getAppSession().getMessage("rnl.select.tenantName.appRate.contNo"));
				return false;
			}else if(vendorId == null && appreciationRate == null) {
				addValidationError(getAppSession().getMessage("rnl.select.tenantName.appRate"));
				return false;
			}else if(vendorId == null) {
				addValidationError(getAppSession().getMessage("rnl.select.tenantName"));
				return false;
			}else if(appreciationRate == null) {
				addValidationError(getAppSession().getMessage("rnl.select.appRate"));
				return false;
			}
		}
		return status;
	}

	public ContractMastDTO getContractMasterDto() {
		return contractMasterDto;
	}

	public void setContractMasterDto(ContractMastDTO contractMasterDto) {
		this.contractMasterDto = contractMasterDto;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public TbDepartmentService getiTbDepartmentService() {
		return iTbDepartmentService;
	}

	public void setiTbDepartmentService(TbDepartmentService iTbDepartmentService) {
		this.iTbDepartmentService = iTbDepartmentService;
	}

	public ITransferOfLeaseService getTransferOfLeaseService() {
		return transferOfLeaseService;
	}

	public void setTransferOfLeaseService(ITransferOfLeaseService transferOfLeaseService) {
		this.transferOfLeaseService = transferOfLeaseService;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Double getAppreciationRate() {
		return appreciationRate;
	}

	public void setAppreciationRate(Double appreciationRate) {
		this.appreciationRate = appreciationRate;
	}

}
