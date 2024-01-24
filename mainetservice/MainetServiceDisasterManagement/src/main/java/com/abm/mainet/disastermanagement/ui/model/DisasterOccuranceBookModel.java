package com.abm.mainet.disastermanagement.ui.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.dto.DisasterOccuranceBookDTO;
import com.abm.mainet.disastermanagement.service.IDisasterOccuranceBookService;



@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DisasterOccuranceBookModel extends AbstractFormModel {

	private static final long serialVersionUID = -303720367713937908L;

	@Autowired
	IDisasterOccuranceBookService occurancebookservice;

	private DisasterOccuranceBookDTO occuranceBookDTO = new DisasterOccuranceBookDTO();

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public String saveMode;

	Date date = new Date();

	@Override
	public boolean saveForm() {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
	
		if (occuranceBookDTO.getOccId() == null) {

			occuranceBookDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			occuranceBookDTO.setCreatedBy(createdBy);
			occuranceBookDTO.setCreatedDate(createdDate);
			occuranceBookDTO.setLgIpMac(lgIpMac);
			occurancebookservice.save(occuranceBookDTO);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
		} else {
			occuranceBookDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			occuranceBookDTO.setUpdatedBy(createdBy);
			occuranceBookDTO.setUpdatedDate(createdDate);
			occuranceBookDTO.setLgIpMacUpd(lgIpMac);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
			occurancebookservice.save(occuranceBookDTO);
		}
		return true;
	}

	public DisasterOccuranceBookDTO getOccuranceBookDTO() {
		return occuranceBookDTO;
	}

	public void setOccuranceBookDTO(DisasterOccuranceBookDTO occuranceBookDTO) {
		this.occuranceBookDTO = occuranceBookDTO;
	}

	public IDisasterOccuranceBookService getService() {
		return occurancebookservice;
	}

	public void setService(IDisasterOccuranceBookService service) {
		this.occurancebookservice = service;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public IDisasterOccuranceBookService getOccurancebookservice() {
		return occurancebookservice;
	}

	public void setOccurancebookservice(IDisasterOccuranceBookService occurancebookservice) {
		this.occurancebookservice = occurancebookservice;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}