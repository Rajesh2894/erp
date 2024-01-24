package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.service.AdvocateMasterService;
import com.abm.mainet.legal.ui.validator.AdvocateMasterValidator;

@Component
@Scope("session")
public class AdvocateMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	private String saveMode;

	private AdvocateMasterDTO advocateMasterDTO;
	private List<AdvocateMasterDTO> advocateMasterDTOList;
	private List<OrganisationDTO> org = new ArrayList<>();

	private String envFlag;

	@Autowired
	private AdvocateMasterService advocateMasterService;

	@Override
	public boolean saveForm() {
		validateBean(advocateMasterDTO, AdvocateMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		} else {
			advocateMasterDTO.setOrgid(advocateMasterDTO.getOrgid());
			List<AdvocateMasterDTO> list = advocateMasterService.validateAdvocateMaster(advocateMasterDTO);
			// Defect #105841
			if (CollectionUtils.isNotEmpty(list)) {
				for (AdvocateMasterDTO dto : list) {
					if (dto.getAdvMobile().equals(advocateMasterDTO.getAdvMobile())) {
						this.addValidationError(
								ApplicationSession.getInstance().getMessage("lgl.validation.phone.exist"));
					}
					if (dto.getAdvEmail().equalsIgnoreCase(advocateMasterDTO.getAdvEmail())) {
						this.addValidationError(
								ApplicationSession.getInstance().getMessage("lgl.validation.email.exist"));
					}
					if (StringUtils.isNotEmpty(dto.getAdvPanno())) {
						if (dto.getAdvPanno().equalsIgnoreCase(advocateMasterDTO.getAdvPanno()))
							this.addValidationError(
									ApplicationSession.getInstance().getMessage("lgl.validation.panNo.exist"));
					}
					if (StringUtils.isNotEmpty(dto.getAdvUid())) {
						if (dto.getAdvUid().equalsIgnoreCase(advocateMasterDTO.getAdvUid()))
							this.addValidationError(
									ApplicationSession.getInstance().getMessage("lgl.validation.adhar.exist"));
					}
					if (hasValidationErrors()) {
						return false;
					}
				}

			} else {
				Employee employee = UserSession.getCurrent().getEmployee();
			     AdvocateMasterDTO masterDTO = getAdvocateMasterDTO();
					advocateMasterDTO.setAdvStatus(MainetConstants.FlagN);
					advocateMasterDTO.setCreatedBy(employee.getEmpId());
					advocateMasterDTO.setCreatedDate(new Date());
					advocateMasterDTO.setLgIpMac(employee.getEmppiservername());
					advocateMasterDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
					
					advocateMasterDTO.getAdvEducationDetailDTOList().forEach(eduDet -> {
											eduDet.setCreatedBy(employee.getEmpId());
											eduDet.setCreatedDate(new Date());
											eduDet.setOrgid(advocateMasterDTO.getOrgid());
											eduDet.setLgIpMac(employee.getEmppiservername());
									});
					masterDTO = advocateMasterService.saveAdvocateMaster(advocateMasterDTO);
					if (masterDTO != null && masterDTO.getApplicationId() != null)
					setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveAdvocateMaster") + masterDTO.getApplicationId());
					else {
						this.addValidationError(ApplicationSession.getInstance().getMessage("lgl.errorMessage"));
					}
			}
		}

		return true;

	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<AdvocateMasterDTO> getAdvocateMasterDTOList() {
		return advocateMasterDTOList;
	}

	public void setAdvocateMasterDTOList(List<AdvocateMasterDTO> advocateMasterDTOList) {
		this.advocateMasterDTOList = advocateMasterDTOList;
	}

	public AdvocateMasterDTO getAdvocateMasterDTO() {
		return advocateMasterDTO;
	}

	public void setAdvocateMasterDTO(AdvocateMasterDTO advocateMasterDTO) {
		this.advocateMasterDTO = advocateMasterDTO;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public List<OrganisationDTO> getOrg() {
		return org;
	}

	public void setOrg(List<OrganisationDTO> org) {
		this.org = org;
	}

	


	

}
