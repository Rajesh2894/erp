package com.abm.mainet.authentication.agency.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.authentication.agency.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.authentication.agency.dto.AgencyEmployeeResDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.IDesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AgencyRegistrationProcessService implements IAgencyRegistrationProcessService {

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IDesignationService iDesignationService;

    @Override
    @Transactional
    public Employee saveCitizenRegistrationForm(final AgencyEmployeeReqDTO agencyEmpReqDTO, final Organisation organisation,
            final String newOTPPassword, final String citizen_LOCATION, Designation designation,
            final Long employee_TYPE, final Long userId) {
        Employee registeredCitizen = null;
        final Department department = getCitizenDepartment(organisation);

        if (designation == null) {

            final List<Designation> designationList = iDesignationService
                    .getAllDesignationByDesgName(MainetConstants.AGENCY.NAME.CITIZEN);
            designation = designationList.get(0);
            agencyEmpReqDTO.setDesignationId(designation.getDsgid());
        }

        agencyEmpReqDTO.setDpDeptid(department.getDpDeptid());
        agencyEmpReqDTO.setEmplType(employee_TYPE);
        agencyEmpReqDTO.setEmploginname(agencyEmpReqDTO.getEmpmobno());// Mobile_Number_AS_LoginName
        agencyEmpReqDTO.setEmppassword(newOTPPassword);
        agencyEmpReqDTO.setOrganisation(organisation.getOrgid());
        agencyEmpReqDTO.setOndate(new Date());
        agencyEmpReqDTO.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        agencyEmpReqDTO.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);// Default_value_is_"N"
        agencyEmpReqDTO.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
        agencyEmpReqDTO
                .setEmppassword(Utility.encryptPassword(agencyEmpReqDTO.getEmploginname(), agencyEmpReqDTO.getEmppassword()));

        agencyEmpReqDTO.setDpDeptid(department.getDpDeptid());
        agencyEmpReqDTO.setUserId(userId);
        agencyEmpReqDTO.setLangaugeId(UserSession.getCurrent().getLanguageId());
        agencyEmpReqDTO.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);

        try {
            @SuppressWarnings("unchecked")
            final LinkedHashMap<Long, Object> response = (LinkedHashMap<Long, Object>) JersyCall
                    .callRestTemplateClient(agencyEmpReqDTO, ServiceEndpoints.JercyCallURL.AGENCY_SAVE_EMP);
            final String jsonString = new JSONObject(response).toString();
            final AgencyEmployeeResDTO responseDTO = new ObjectMapper().readValue(jsonString, AgencyEmployeeResDTO.class);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {
                agencyEmpReqDTO.setEmpId(responseDTO.getEmpId());
                final Employee agencyEmployee = new Employee();
                agencyEmployee.setEmpId(responseDTO.getEmpId());
                agencyEmployee.setAgencyName(agencyEmpReqDTO.getAgencyName());
                agencyEmployee.setEmplType(agencyEmpReqDTO.getEmplType());
                agencyEmployee.setTitle(agencyEmpReqDTO.getTitle());
                agencyEmployee.setEmpname(agencyEmpReqDTO.getEmpname());
                agencyEmployee.setEmpMName(agencyEmpReqDTO.getEmpMName());
                agencyEmployee.setEmpLName(agencyEmpReqDTO.getlName());
                agencyEmployee.setEmpGender(agencyEmpReqDTO.getEmpGender());
                agencyEmployee.setEmpdob(agencyEmpReqDTO.getEmpdob());
                agencyEmployee.setEmpAddress(agencyEmpReqDTO.getEmpAddress());
                agencyEmployee.setEmpAddress1(agencyEmpReqDTO.getEmpAddress1());
                agencyEmployee.setEmpmobno(agencyEmpReqDTO.getEmpmobno());
                agencyEmployee.setEmpemail(agencyEmpReqDTO.getEmail());
                agencyEmployee.setPanCardNo(agencyEmpReqDTO.getPanCardNo());
                agencyEmployee.setTbDepartment(department);
                agencyEmployee.setEmplType(employee_TYPE);
                agencyEmployee.setEmploginname(agencyEmpReqDTO.getEmpmobno());// Mobile_Number_AS_LoginName
                agencyEmployee.setEmppassword(agencyEmpReqDTO.getEmppassword());
                agencyEmployee.setOrganisation(organisation);
                agencyEmployee.setOndate(new Date());
                agencyEmployee.setIsdeleted(MainetConstants.IsDeleted.ZERO);
                agencyEmployee.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);// Default_value_is_"N"
                agencyEmployee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
                agencyEmployee.setEmppassword(agencyEmpReqDTO.getEmppassword());
                agencyEmployee.setUserId(agencyEmployee);
                agencyEmployee.setLangId(UserSession.getCurrent().getLanguageId());
                agencyEmployee.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
                agencyEmployee.setDesignation(designation);
                registeredCitizen = iEmployeeService.saveAgencyEmployeeDetails(agencyEmployee, organisation,
                        designation, department, userId);

            }
        } catch (final Exception ex) {
            throw new FrameworkException(
                    "Error Occurred while making Jersy call: AgencyRegistrationProcessService.saveCitizenRegistrationForm()", ex);
        }

        return registeredCitizen;

    }

    @Override
    @Transactional
    public Department getCitizenDepartment(final Organisation organisation) {
        return iDepartmentService.getDepartment(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE,
                MainetConstants.STATUS.ACTIVE);
    }

}
