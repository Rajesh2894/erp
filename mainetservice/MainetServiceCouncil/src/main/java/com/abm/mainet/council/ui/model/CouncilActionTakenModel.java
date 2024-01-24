package com.abm.mainet.council.ui.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.domain.CouncilActionTakenEntity;
import com.abm.mainet.council.dto.CouncilActionTakenDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.service.ICouncilActionTakenService;
import com.abm.mainet.council.ui.validator.ActionTakenValidator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@Component
@Scope("session")
public class CouncilActionTakenModel extends AbstractFormModel {

    private static final long serialVersionUID = -6872574335805073855L;

    private CouncilProposalMasterDto couProposalMasterDto;
    private CouncilActionTakenDto actionTakenDto = new CouncilActionTakenDto();
    @Autowired
    private ICouncilActionTakenService service;

    private String saveMode;
    private List<TbDepartment> departmentsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
    private List<Department> departments = new ArrayList<>();

    private List<CouncilActionTakenDto> councilActionTakenDto = new ArrayList<>(0);
    private List<EmployeeBean> employee = new ArrayList<>();
    private List<EmployeeBean> listOfAllemployee = new ArrayList<>();

    // save action taken details
    @Override
    public boolean saveForm() {
        validateBean(councilActionTakenDto, ActionTakenValidator.class);
        if (hasValidationErrors()) {
            return false;
        }

        List<CouncilActionTakenEntity> entities = new ArrayList<>();
        councilActionTakenDto.stream().forEach(councilAction -> {
            if (councilAction.getPatId() != null) {
                councilAction.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                councilAction.setUpdatedDate(new Date());
                councilAction.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

            } else {
                councilAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                councilAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                councilAction.setCreatedDate(new Date());
                councilAction.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                councilAction.setProposalId(getCouProposalMasterDto().getProposalId());
                if (councilAction.getPropDate() != null) {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                    Date propDate;
                    try {
                        propDate = format.parse(councilAction.getPropDate());
                        councilAction.setPatDate(propDate);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                }
            }
            CouncilActionTakenEntity entity = new CouncilActionTakenEntity();
            BeanUtils.copyProperties(councilAction, entity);
            entities.add(entity);
        });

        service.saveAction(entities);
        setSuccessMessage(ApplicationSession.getInstance().getMessage("council.action.savesuccessmsg"));
        return true;
    }

    public CouncilProposalMasterDto getCouProposalMasterDto() {
        return couProposalMasterDto;
    }

    public void setCouProposalMasterDto(CouncilProposalMasterDto couProposalMasterDto) {
        this.couProposalMasterDto = couProposalMasterDto;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<LookUp> getLookupListLevel1() {
        return lookupListLevel1;
    }

    public void setLookupListLevel1(List<LookUp> lookupListLevel1) {
        this.lookupListLevel1 = lookupListLevel1;
    }

    public List<CouncilActionTakenDto> getCouncilActionTakenDto() {
        return councilActionTakenDto;
    }

    public void setCouncilActionTakenDto(List<CouncilActionTakenDto> councilActionTakenDto) {
        this.councilActionTakenDto = councilActionTakenDto;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<EmployeeBean> getEmployee() {
        return employee;
    }

    public void setEmployee(List<EmployeeBean> employee) {
        this.employee = employee;
    }

    public CouncilActionTakenDto getActionTakenDto() {
        return actionTakenDto;
    }

    public void setActionTakenDto(CouncilActionTakenDto actionTakenDto) {
        this.actionTakenDto = actionTakenDto;
    }

    public ICouncilActionTakenService getService() {
        return service;
    }

    public void setService(ICouncilActionTakenService service) {
        this.service = service;
    }

    public List<EmployeeBean> getListOfAllemployee() {
        return listOfAllemployee;
    }

    public void setListOfAllemployee(List<EmployeeBean> listOfAllemployee) {
        this.listOfAllemployee = listOfAllemployee;
    }

}
