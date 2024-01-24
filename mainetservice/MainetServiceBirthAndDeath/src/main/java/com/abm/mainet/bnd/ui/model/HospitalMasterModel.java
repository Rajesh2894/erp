package com.abm.mainet.bnd.ui.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.domain.HospitalMaster;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.ui.validator.HospitalMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class HospitalMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IHospitalMasterService service;

	private HospitalMasterDTO hospitalMasterDTO = new HospitalMasterDTO();
	private List<HospitalMasterDTO> hospitalMaster;

	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String saveMode;

	@Override
	public boolean saveForm() {

		validateBean(hospitalMasterDTO, HospitalMasterValidator.class);
		//validateAllHospitalCode();
		if (hasValidationErrors()) {
			return false;
		} else {
			if (hospitalMasterDTO.getHiId() == null) {
				hospitalMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				hospitalMasterDTO.setLmoddate(new Date());
				hospitalMasterDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				hospitalMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				hospitalMasterDTO.setLangId(UserSession.getCurrent().getLanguageId());
			} else {
				hospitalMasterDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			service.saveHospital(hospitalMasterDTO);
			this.setSuccessMessage(getAppSession().getMessage("HospitalMasterDTO.form.save"));
		}

		return true;
	}

	private void validateAllHospitalCode() {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<HospitalMaster> hospitalCodeList = null;

		if ((hospitalMasterDTO.getHiCode() != null)
				&& (hospitalMasterDTO.getHiCode() != MainetConstants.CommonConstant.BLANK)
				&& !hospitalMasterDTO.getHiCode().isEmpty()) {
			hospitalCodeList = service.getAllHospitalCode(hospitalMasterDTO.getHiCode(), orgId);
		}

		if (getSaveMode().equals(MainetConstants.CommonConstants.ADD)) {
			if (CollectionUtils.isNotEmpty(hospitalCodeList)) {
				addValidationError(getAppSession().getMessage("HospitalMasterDTO.form.Code"));
			}
		} else {
			// Code Validation
			if ((hospitalCodeList != null) && !hospitalCodeList.isEmpty()) {
				Boolean isduplicateHospitalcode = false;
				for (final HospitalMaster master : hospitalCodeList) {
					if (!hospitalMasterDTO.getHiId().equals(master.getHiId())) {
						isduplicateHospitalcode = true;
						break;
					}
				}
				if (isduplicateHospitalcode) {
					addValidationError(getAppSession().getMessage("Hospital Code not Valid"));
				}
			}
		}

	}

	public HospitalMasterDTO getHospitalMasterDTO() {
		return hospitalMasterDTO;
	}

	public void setHospitalMasterDTO(HospitalMasterDTO hospitalMasterDTO) {
		this.hospitalMasterDTO = hospitalMasterDTO;
	}

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<HospitalMasterDTO> getHospitalMaster() {
		return hospitalMaster;
	}

	public void setHospitalMaster(List<HospitalMasterDTO> hospitalMaster) {
		this.hospitalMaster = hospitalMaster;
	}

}
