package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.PopulationMasterDTO;
import com.abm.mainet.swm.service.IPopulationMasterService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class PopulationMasterModel extends AbstractFormModel {
	private static final long serialVersionUID = -3148604369903287905L;
	@Autowired
	private IPopulationMasterService populationMasterService;
	private PopulationMasterDTO populationMasterDTO = new PopulationMasterDTO();
	private List<PopulationMasterDTO> populationslist = new ArrayList<PopulationMasterDTO>();
	private List<TbFinancialyear> faYears = new ArrayList<>();
	private Set<PopulationMasterDTO> populationSet = new HashSet<PopulationMasterDTO>();
	private String excelFilePath;
	private String saveMode;

	@Override
	public boolean saveForm() {
		boolean status = false;
		String mode = null;
		String error = " ";
		int errorCount = 1;
		// validateBean(populationMasterDTO, PopulationMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		} else {
			for (PopulationMasterDTO populationMasterDTO : this.populationslist) {

				if (populationMasterDTO.getPopId() == null) {
					populationMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					populationMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					populationMasterDTO.setCreatedDate(new Date());
					populationMasterDTO.setLgIpMac(Utility.getMacAddress());
					populationMasterDTO.setPopActive(MainetConstants.FlagY);
					populationMasterDTO.setPopYear(this.populationMasterDTO.getPopYear());
					status = populationMasterService.validatePopulationMaster(populationMasterDTO);
					mode = "save";
					if (status == false) {
						error = error + errorCount + MainetConstants.operator.COMMA;
					}
					errorCount++;
				} else {
					populationMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					populationMasterDTO.setUpdatedDate(new Date());
					populationMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
                    //D#140942
					if (this.getSaveMode() != null && this.getSaveMode().equals(MainetConstants.FlagE)
							&& populationMasterDTO.getDelete()==null) {
						
						populationMasterDTO.setPopActive(MainetConstants.FlagN);
					}
					status = populationMasterService.validatePopulationMaster(populationMasterDTO);
					mode = "update";
					if (status == false) {
						error = error + errorCount + MainetConstants.operator.COMMA;
					}
					errorCount++;
				}
			}
			if (status && mode.equals("save")) {
				populationMasterService.savePopulationMaster(this.populationslist);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("PopulationMasterDTO.save.add"));
				status = true;
			} else if (status && mode.equals("update")) {
				populationMasterService.savePopulationMaster(this.populationslist);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("PopulationMasterDTO.save.edit"));
				status = true;
			} else {
				addValidationError(
						ApplicationSession.getInstance().getMessage("PopulationMasterDTO.master.exists") + error);
				status = false;
			}
			this.setSaveMode("R");
			return status;
		}
	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		return MainetConstants.SOLID_WATSE_FLOWTYPE.SOLID_WASTE_TYPE_DTO_COD_ID_OPER_LEVEL;
	}

	public PopulationMasterDTO getPopulationMasterDTO() {
		return populationMasterDTO;
	}

	public void setPopulationMasterDTO(PopulationMasterDTO populationMasterDTO) {
		this.populationMasterDTO = populationMasterDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PopulationMasterDTO> getPopulationslist() {
		return populationslist;
	}

	public void setPopulationslist(List<PopulationMasterDTO> populationslist) {
		this.populationslist = populationslist;
	}

	public Set<PopulationMasterDTO> getPopulationSet() {
		return populationSet;
	}

	public void setPopulationSet(Set<PopulationMasterDTO> populationSet) {
		this.populationSet = populationSet;
	}

}
