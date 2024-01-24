package com.abm.mainet.disastermanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.dto.CallScrutinyDTO;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IComplainRegisterService;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class InjuryDetailsModel extends AbstractFormModel {

	@Autowired
	IComplainRegisterService service;
	private static final long serialVersionUID = -1729071896673067088L;

	private List<CallScrutinyDTO> call = new ArrayList<CallScrutinyDTO>();
	
	private String complainNo;
	
	private ComplainRegisterDTO complainRegisterDTO = new ComplainRegisterDTO();
	
	private String saveMode;
	
	private Long complainId;

	@Override
		public boolean saveForm() {
			
			
			if(complainRegisterDTO.getRemark() == null) {
				complainRegisterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				complainRegisterDTO.setOrgid(UserSession.getCurrent().getEmployee().getEmpId());
				complainRegisterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			/* complainRegisterDTO.setDepartment(1L); */
			}else {
				complainRegisterDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				complainRegisterDTO.setUpdatedDate(new Date());
			}
		
			
		
			service.save(complainRegisterDTO);
			this.setSuccessMessage(getAppSession().getMessage("ComplainRegisterDTO.form.Injurysave"));
			return true;
		}
	  
	public String getComplainNo() {
		return complainNo;
	}


	public void setComplainNo(String complainNo) {
		this.complainNo = complainNo;
	}


	public List<CallScrutinyDTO> getCall() {
		return call;
	}


	public void setCall(List<CallScrutinyDTO> call) {
		this.call = call;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public ComplainRegisterDTO getComplainRegisterDTO() {
		return complainRegisterDTO;
	}

	public void setComplainRegisterDTO(ComplainRegisterDTO complainRegisterDTO) {
		this.complainRegisterDTO = complainRegisterDTO;
	}
	
	public String getSaveMode() {
		return saveMode;
	}
	
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public Long getComplainId() {
		return complainId;
	}

	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}

}