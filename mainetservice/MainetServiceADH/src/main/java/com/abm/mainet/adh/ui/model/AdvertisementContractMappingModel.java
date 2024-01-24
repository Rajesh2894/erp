
package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.ADHContractMappingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.service.IAdvertisementContractMappingService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 04 November
 */

@Component
@Scope("session")
public class AdvertisementContractMappingModel extends AbstractFormModel {

    @Autowired
    private IAdvertisementContractMappingService advertisementContractMappingService;

    private static final long serialVersionUID = 3165715755462420517L;

    private ContractMastDTO contractMasterDto = new ContractMastDTO();

    private ADHContractMappingDto contractMappingDto = new ADHContractMappingDto();

    private List<String[]> contractNoList = new ArrayList<>();

    private List<String[]> hoardingNoList = new ArrayList<>();

    private List<ContractMappingDTO> contractMappingList = new ArrayList<>();

    private List<HoardingMasterDto> hoardingDtoList = new ArrayList<>();

    private TbDepartment tbDepartment = new TbDepartment();

    private String saveMode;

    private String duplicateContractFlag;

    @Override
    public boolean saveForm() {
	boolean status = false;

	if (!validateForm()) {
	    return false;
	}
	if (hasValidationErrors()) {
	    return false;
	}

	ADHContractMappingDto adhContractMappingDto = getContractMappingDto();

	adhContractMappingDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	adhContractMappingDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	adhContractMappingDto.setCreatedDate(new Date());
	adhContractMappingDto.setLgIpMac(getClientIpAddress());
	status = advertisementContractMappingService.saveADHContractMapping(adhContractMappingDto);
	setSuccessMessage(ApplicationSession.getInstance().getMessage("Select Hoarding has been successfully Mapped"));
	return status;
    }

    boolean validateForm() {
	boolean error = false;
	if (StringUtils.equals(getDuplicateContractFlag(), MainetConstants.FlagN)) {
	    addValidationError(getAppSession().getMessage("Already Contract Mapped"));
	}

	if (getContractMappingDto().getContractId() == null || getContractMappingDto().getContractId() == 0) {
	    addValidationError(getAppSession().getMessage("contract.validate.contract.no"));
	}
	List<Long> hoardingIdList = new ArrayList<>();
	List<HoardingMasterDto> hoardingList = getContractMappingDto().getHoardingMasterList();
	for (HoardingMasterDto hoarding : hoardingList) {
	    hoardingIdList.add(hoarding.getHoardingId());

	}
	boolean duplicateFlag = false;
	Set<Long> ccnSet = new HashSet<>();
	for (Long hoarding : hoardingIdList) {
	    if (ccnSet.add(hoarding) == false) {
		duplicateFlag = true;
	    }
	}
	if (duplicateFlag) {
	    addValidationError(getAppSession().getMessage("contract.duplicate.hoardings.not.allowed"));
	}

	if (!hasValidationErrors()) {
	    error = true;
	}
	return error;
    }

    public ContractMastDTO getContractMasterDto() {
	return contractMasterDto;
    }

    public void setContractMasterDto(ContractMastDTO contractMasterDto) {
	this.contractMasterDto = contractMasterDto;
    }

    public ADHContractMappingDto getContractMappingDto() {
	return contractMappingDto;
    }

    public void setContractMappingDto(ADHContractMappingDto contractMappingDto) {
	this.contractMappingDto = contractMappingDto;
    }

    public List<String[]> getContractNoList() {
	return contractNoList;
    }

    public void setContractNoList(List<String[]> contractNoList) {
	this.contractNoList = contractNoList;
    }

    public List<String[]> getHoardingNoList() {
	return hoardingNoList;
    }

    public void setHoardingNoList(List<String[]> hoardingNoList) {
	this.hoardingNoList = hoardingNoList;
    }

    public List<HoardingMasterDto> getHoardingDtoList() {
	return hoardingDtoList;
    }

    public void setHoardingDtoList(List<HoardingMasterDto> hoardingDtoList) {
	this.hoardingDtoList = hoardingDtoList;
    }

    public List<ContractMappingDTO> getContractMappingList() {
	return contractMappingList;
    }

    public void setContractMappingList(List<ContractMappingDTO> contractMappingList) {
	this.contractMappingList = contractMappingList;
    }

    public TbDepartment getTbDepartment() {
	return tbDepartment;
    }

    public void setTbDepartment(TbDepartment tbDepartment) {
	this.tbDepartment = tbDepartment;
    }

    public String getSaveMode() {
	return saveMode;
    }

    public void setSaveMode(String saveMode) {
	this.saveMode = saveMode;
    }

    public String getDuplicateContractFlag() {
	return duplicateContractFlag;
    }

    public void setDuplicateContractFlag(String duplicateContractFlag) {
	this.duplicateContractFlag = duplicateContractFlag;
    }

}
