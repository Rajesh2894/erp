package com.abm.mainet.disastermanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.constant.DisasterConstant;
import com.abm.mainet.disastermanagement.domain.ComplainRegister;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IComplainRegisterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DepartmentalScrutinyModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IComplainRegisterService iComplainRegisterService;

	private ComplainRegisterDTO complainRegisterDTO = new ComplainRegisterDTO();
	
	private List<ComplainRegisterDTO> complainRegisterDTOList;
	
	private List<ComplainRegisterDTO> complainRegList;
	
	private ComplainRegister complainRegister = new ComplainRegister();
	
	private List<ComplainRegister> complainRegisterList;
	
	private ComplainRegisterDTO entity = new ComplainRegisterDTO();
	
	private List<ComplainRegisterDTO> entityList;
	
	private String saveMode;
	
	private String atdFname;
	
	private String atdPath;
	
	List<AttachDocs> attachDocs=new ArrayList<AttachDocs>();
	
	List<Employee> employeList = new ArrayList<>();
	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		
		String status = complainRegisterDTO.getComplaintStatus();
		if(status.equals("A"))
			status = DisasterConstant.APPROVE;
		else if(status.equals("R"))
			status = DisasterConstant.REJECT;
		else if(status.equals("F"))
			status = DisasterConstant.FORWARD;
		String remark = complainRegisterDTO.getRemark();
		String complainNo = complainRegisterDTO.getComplainNo();
		Long orgid = employee.getOrganisation().getOrgid();
		complainRegisterDTO.setUpdatedBy(employee.getEmpId());
		complainRegisterDTO.setUpdatedDate(new Date());
		complainRegisterDTO.setLgIpMac(employee.getEmppiservername());
		complainRegisterDTO.setLgIpMacUpd(employee.getEmppiservername());
		complainRegisterDTO.setOrgid(employee.getOrganisation().getOrgid());
		iComplainRegisterService.updateComplainRegData(complainNo, status, remark, orgid,employee.getEmpId(),complainRegisterDTO);
		if(status.equals(DisasterConstant.FORWARD)) {
			iComplainRegisterService.updateComplainRegistration(entity,complainRegisterDTO);
		}
		this.setSuccessMessage(getAppSession().getMessage("ComplainRegisterDTO.update.success"));
		return true;
	}

	
	public IComplainRegisterService getiComplainRegisterService() {
		return iComplainRegisterService;
	}

	public void setiComplainRegisterService(IComplainRegisterService iComplainRegisterService) {
		this.iComplainRegisterService = iComplainRegisterService;
	}

	public ComplainRegisterDTO getComplainRegisterDTO() {
		return complainRegisterDTO;
	}

	public void setComplainRegisterDTO(ComplainRegisterDTO complainRegisterDTO) {
		this.complainRegisterDTO = complainRegisterDTO;
	}

	public List<ComplainRegisterDTO> getComplainRegisterDTOList() {
		return complainRegisterDTOList;
	}

	public void setComplainRegisterDTOList(List<ComplainRegisterDTO> complainRegisterDTOList) {
		this.complainRegisterDTOList = complainRegisterDTOList;
	}

	public ComplainRegister getComplainRegister() {
		return complainRegister;
	}

	public void setComplainRegister(ComplainRegister complainRegister) {
		this.complainRegister = complainRegister;
	}

	public List<ComplainRegister> getComplainRegisterList() {
		return complainRegisterList;
	}

	public void setComplainRegisterList(List<ComplainRegister> complainRegisterList) {
		this.complainRegisterList = complainRegisterList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getAtdFname() {
		return atdFname;
	}


	public void setAtdFname(String atdFname) {
		this.atdFname = atdFname;
	}

	public String getAtdPath() {
		return atdPath;
	}

	public void setAtdPath(String atdPath) {
		this.atdPath = atdPath;
	}

	public List<AttachDocs> getAttachDocs() {
		return attachDocs;
	}

	public void setAttachDocs(List<AttachDocs> attachDocs) {
		this.attachDocs = attachDocs;
	}


	public List<Employee> getEmployeList() {
		return employeList;
	}


	public void setEmployeList(List<Employee> employeList) {
		this.employeList = employeList;
	}


	public ComplainRegisterDTO getEntity() {
		return entity;
	}


	public void setEntity(ComplainRegisterDTO entity) {
		this.entity = entity;
	}


	public List<ComplainRegisterDTO> getEntityList() {
		return entityList;
	}


	public void setEntityList(List<ComplainRegisterDTO> entityList) {
		this.entityList = entityList;
	}


	public List<ComplainRegisterDTO> getComplainRegList() {
		return complainRegList;
	}


	public void setComplainRegList(List<ComplainRegisterDTO> complainRegList) {
		this.complainRegList = complainRegList;
	}
}
