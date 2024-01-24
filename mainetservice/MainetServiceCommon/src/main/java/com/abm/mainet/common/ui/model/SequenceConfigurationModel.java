package com.abm.mainet.common.ui.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.SequenceConfigDetDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.ISequenceConfigMasterService;
import com.abm.mainet.common.ui.validator.SequenceConfigurationValidator;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sadik.shaikh
 *
 */
@Component
@Scope("session")
public class SequenceConfigurationModel extends AbstractFormModel {

	private static final long serialVersionUID = -6314475931571415924L;

	private String saveMode;

	private SequenceConfigMasterDTO configMasterDTO = new SequenceConfigMasterDTO();

	private Map<Long, String> depList = null;

	private String formMode;

	private List<SequenceConfigMasterDTO> configMasterDTOs;

	private SequenceConfigDetDTO configDetDTO;

	private List<TbDepartment> departmentList;

	private List<TbComparamMas> comparamMas;

	@Autowired
	private ISequenceConfigMasterService configMasterService;

	public List<TbComparamMas> getComparamMas() {
		return comparamMas;
	}

	public void setComparamMas(List<TbComparamMas> comparamMas) {
		this.comparamMas = comparamMas;
	}

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public SequenceConfigDetDTO getConfigDetDTO() {
		return configDetDTO;
	}

	public void setConfigDetDTO(SequenceConfigDetDTO configDetDTO) {
		this.configDetDTO = configDetDTO;
	}

	public List<SequenceConfigMasterDTO> getConfigMasterDTOs() {
		return configMasterDTOs;
	}

	public void setConfigMasterDTOs(List<SequenceConfigMasterDTO> configMasterDTOs) {
		this.configMasterDTOs = configMasterDTOs;
	}

	public ISequenceConfigMasterService getConfigMasterService() {
		return configMasterService;
	}

	public void setConfigMasterService(ISequenceConfigMasterService configMasterService) {
		this.configMasterService = configMasterService;
	}

	public String getFormMode() {
		return formMode;
	}

	public void setFormMode(String formMode) {
		this.formMode = formMode;
	}

	@Override
	public boolean saveForm() {

		String editFlag = this.getConfigMasterDTO().getEditFlag();
		Long seqConfigId = this.getConfigMasterDTO().getSeqConfigId();

		if (configMasterDTO.getSeqConfigId() == null) {

			validateBean(this, SequenceConfigurationValidator.class);
			if (hasValidationErrors()) {
				return false;

			}
		}

		if (editFlag == "S") {

			if (seqConfigId == null) {
				configMasterDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				configMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				configMasterDTO.setCreationDate(new Date());
				configMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

				Long i = 1L;

				for (SequenceConfigDetDTO configDetDTO : configMasterDTO.getConfigDetDTOs()) {
					if (configDetDTO.getSeqDetId() == null) {
						configDetDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						configDetDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						configDetDTO.setCreationDate(new Date());
						configDetDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

						if (i == 1) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.STATE_CODE);
						} else if (i == 2) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.DIVISION_CODE);
						} else if (i == 3) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.DISTRICT_CODE);
						} else if (i == 4) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.ORG_ID);
						} else if (i == 5) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.SERVICE_CODE);
						} else if (i == 6) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.BUSINESS_UNIT_CODE);
						} else if (i == 7) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.DEPARTMENT_CODE);
						} else if (i == 8) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.DEPARTMENT_PREFIX);
						} else if (i == 9) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.LEVEL);
						} else if (i == 10) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.USAGE_TYPE);
						} else if (i == 11) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.FINANCIAL_YEAR_WISE);
						} else if (i == 12) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.DESIGNATION);
						} else if (i == 13) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.TRADE_CAT);
						}else if (i == 14) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.Calendar_Year_wise);
						}else if (i == 15) {
							configDetDTO.setSeqFactId(MainetConstants.SQ_FACT_ID.FIELD);
						}
						i++;
					}
				}

				configMasterService.saveSequenceConfig(configMasterDTO);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("Sequence entry added successfully"));
			}
		} else if (editFlag == "E") {
			configMasterDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

			configMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			configMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			configMasterDTO.setUpdatedDate(new Date());
			configMasterDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

			for (SequenceConfigDetDTO configDetDTO : configMasterDTO.getConfigDetDTOs()) {

				if (configDetDTO.getCreatedBy() == null) {
					configDetDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					configDetDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					configDetDTO.setCreationDate(new Date());
					configDetDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

				} else {
					configDetDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					configDetDTO.setUpdatedDate(new Date());
					configDetDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				}
			}
			configMasterService.saveSequenceConfig(configMasterDTO);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Sequence entry updated successfully"));
		}

		return true;
	}

	public SequenceConfigMasterDTO getConfigMasterDTO() {
		return configMasterDTO;
	}

	public void setConfigMasterDTO(SequenceConfigMasterDTO configMasterDTO) {
		this.configMasterDTO = configMasterDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

}
