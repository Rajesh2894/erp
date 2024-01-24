package com.abm.mainet.bnd.ui.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Bhagyashri.Dongardive
 * @since 31 Aug 2021
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DataEntryForDeathRegModel extends AbstractFormModel{
 
	private static final long serialVersionUID = 8781883119215329604L;
	
	private static final Logger LOG = Logger.getLogger(DataEntryForDeathRegModel.class);
	
	@Autowired
	private IDeathRegistrationService iDeathRegistrationService;

	private TbDeathregDTO tbDeathregDTO= new TbDeathregDTO();
	private List<TbDeathregDTO> tbDeathregDTOList;
	private List<HospitalMasterDTO> hospitalMasterDTOList;
	private String hospitalList;
	private List<CemeteryMasterDTO> cemeteryMasterDTOList;
	private String saveMode;
	
	@Override
	public boolean saveForm() {
		TbDeathregDTO dto = iDeathRegistrationService.saveDataEntryDeathRegDet(tbDeathregDTO);
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDr") + dto.getApmApplicationId());
		return true;
	}

	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}

	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}

	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public void setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		this.hospitalMasterDTOList = hospitalMasterDTOList;
	}

	public String getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
		return cemeteryMasterDTOList;
	}

	public void setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
		this.cemeteryMasterDTOList = cemeteryMasterDTOList;
	}
	
	
}
