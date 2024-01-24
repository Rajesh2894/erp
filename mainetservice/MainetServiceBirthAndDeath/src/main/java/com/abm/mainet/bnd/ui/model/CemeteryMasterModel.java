
package com.abm.mainet.bnd.ui.model;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.service.ICemeteryMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

	@Component
	@Scope(value = WebApplicationContext.SCOPE_SESSION)
	public class CemeteryMasterModel extends AbstractFormModel{

		
		private static final long serialVersionUID = 1L;

		@Autowired
		private ICemeteryMasterService service;

		private CemeteryMasterDTO cemeteryMasterDTO = new CemeteryMasterDTO();

		private List<CemeteryMasterDTO> cemeteryMasterDTOList;
		
		

		private String saveMode;
		
		public String getSaveMode() {
			return saveMode;
		}

		public void setSaveMode(String saveMode) {
			this.saveMode = saveMode;
		}
		
		@Override
		public boolean saveForm() {
			
			
			if(cemeteryMasterDTO.getCeId() == null) {
				cemeteryMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				cemeteryMasterDTO.setLmoddate(new Date());
				cemeteryMasterDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				cemeteryMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				cemeteryMasterDTO.setLangId(1L);
				
			}else {
				cemeteryMasterDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());				
			}
			service.saveCemetry(cemeteryMasterDTO);
			this.setSuccessMessage(getAppSession().getMessage("CemeteryMasterDTO.form.save"));
			return true;
		}

		public CemeteryMasterDTO getCemeteryMasterDTO() {
			return cemeteryMasterDTO;
		}

		public void setCemeteryMasterDTO(CemeteryMasterDTO cemeteryMasterDTO) {
			this.cemeteryMasterDTO = cemeteryMasterDTO;
		}

		
		public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
			return cemeteryMasterDTOList;
		}

		public List<CemeteryMasterDTO> setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
			return this.cemeteryMasterDTOList = cemeteryMasterDTOList;
		}

	}

	

