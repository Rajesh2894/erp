package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;

@Component
@Scope("session")
public class SLRMEmpModel extends AbstractFormModel {

    @Autowired
    ISLRMEmployeeMasterService sLRMEmployeeMasterService;

    private static final long serialVersionUID = 1L;

    private String saveMode;

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    private List<DesignationBean> designationList = new ArrayList<>();

    SLRMEmployeeMasterDTO sLRMEmployeeMasterDto;
    
    Long duplicate;

    private List<SLRMEmployeeMasterDTO> sLRMEmployeeMasterDtoList = new ArrayList<>();
    
    private List<Object[]> deptObjectList;
        
    private List<Object[]> employeeObjectList;
    

    @Override
    public boolean saveForm() {
        boolean status=false;
        if (sLRMEmployeeMasterDto.getEmpId() == null) {
            sLRMEmployeeMasterDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            sLRMEmployeeMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            sLRMEmployeeMasterDto.setCreatedDate(new Date());
            sLRMEmployeeMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            duplicate = sLRMEmployeeMasterService.checkDuplicateMobileNo(sLRMEmployeeMasterDto.getOrgid(), sLRMEmployeeMasterDto.getEmpMobNo());
            if(duplicate == 0) {
            sLRMEmployeeMasterService.saveEmployeeDetails(sLRMEmployeeMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("swm.SLRM.employee.save"));
            status = true;
            }else {
                addValidationError(ApplicationSession.getInstance().getMessage("swm.SLRM.employee.duplicate.mobile.no"));
            }
        } else {
            sLRMEmployeeMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            sLRMEmployeeMasterDto.setUpdatedDate(new Date());
            sLRMEmployeeMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            sLRMEmployeeMasterService.updateEmployeeDetails(sLRMEmployeeMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("swm.SLRM.employee.edit"));
            status = true;
        }

        return status;

    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public List<DesignationBean> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<DesignationBean> designationList) {
        this.designationList = designationList;
    }

    public SLRMEmployeeMasterDTO getsLRMEmployeeMasterDto() {
        return sLRMEmployeeMasterDto;
    }

    public void setsLRMEmployeeMasterDto(SLRMEmployeeMasterDTO sLRMEmployeeMasterDto) {
        this.sLRMEmployeeMasterDto = sLRMEmployeeMasterDto;
    }

    public List<SLRMEmployeeMasterDTO> getsLRMEmployeeMasterDtoList() {
        return sLRMEmployeeMasterDtoList;
    }

    public void setsLRMEmployeeMasterDtoList(List<SLRMEmployeeMasterDTO> sLRMEmployeeMasterDtoList) {
        this.sLRMEmployeeMasterDtoList = sLRMEmployeeMasterDtoList;
    }

	public List<Object[]> getEmployeeObjectList() {
		return employeeObjectList;
	}

	public void setEmployeeObjectList(List<Object[]> employeeObjectList) {
		this.employeeObjectList = employeeObjectList;
	}

	public List<Object[]> getDeptObjectList() {
		return deptObjectList;
	}

	public void setDeptObjectList(List<Object[]> deptObjectList) {
		this.deptObjectList = deptObjectList;
	}


}
