package com.abm.mainet.agency.authentication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.agency.authentication.dao.AgencyAuthorizationDao;
import com.abm.mainet.agency.authentication.mapper.AgencyRegistrationMapper;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Arun.Chavda
 *
 */
@Service
public class AgencyAuthorizationServiceImpl implements AgencyAuthorizationService {

    @Autowired
    AgencyAuthorizationDao agencyAuthorizationDao;

    @Autowired
    IEmployeeService iEmployeeService;

    @Autowired
    ISMSAndEmailService iSMSAndEmailService;

    @Resource
    private AgencyRegistrationMapper serviceMapper;

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.authentication.agency.service.AgencyAuthorizationService#saveApprovalStatus(com.abm.mainetservice
     * .web.common.entity.Employee)
     */
    @Override
    @Transactional
    public boolean saveApprovalStatus(final EmployeeDTO employee) {

        final List<CFCAttachmentsDTO> listOfAttach = employee.getCfcAttachments();

        final Employee employee2 = serviceMapper.mapEmployeeDTOTOEmployee(employee);

        final Designation designation = new Designation();
        designation.setDsgid(employee.getDesignationId());
        employee2.setDesignation(designation);
        employee2.setCpdTtlId(employee.getTitle());
        final Department department = new Department();
        department.setDpDeptid(employee.getDpDeptid());
        employee2.setTbDepartment(department);
        final LocationMasEntity locatioMas = new LocationMasEntity();
        locatioMas.setLocId(employee.getLocaDeptId());
        employee2.setTbLocationMas(locatioMas);
        employee2.setLmodDate(new Date());
        employee2.setIsDeleted(MainetConstants.ZERO);
        iEmployeeService.saveEmployeeForAgency(employee2);

        int langId = Utility.getDefaultLanguageId(employee2.getOrganisation());

        for (final CFCAttachmentsDTO cfcAttachment : listOfAttach) {

            final CFCAttachment attachment = serviceMapper.mapCFCAttachmentsDTOTOCFCAttachment(cfcAttachment);

            agencyAuthorizationDao.saveAgencyCFCAttachment(attachment);
        }

        if (employee.getAuthStatus().equals(MainetConstants.Common_Constant.ACTIVE_FLAG)) {

            final SMSAndEmailDTO dto = new SMSAndEmailDTO();

            dto.setAppName(employee.getEmpname());
            dto.setAppNo(String.valueOf(employee.getEmpId()));
            dto.setEmail(employee.getEmpemail());
            dto.setMobnumber(employee.getEmpmobno());
            if (UserSession.getCurrent().getLanguageId() == 1) {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            } else {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
            }

            iSMSAndEmailService.sendEmailSMS("AUT", "AgencyAuthorization.html", PrefixConstants.SMS_EMAIL.APPROVAL, dto,
                    employee2.getOrganisation(), langId);

        } else if (employee.getAuthStatus().equals(MainetConstants.Common_Constant.ROLE)) {

            final SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setAppName(employee.getEmpname());
            dto.setAppNo(String.valueOf(employee.getEmpId()));
            dto.setEmail(employee.getEmpemail());
            dto.setMobnumber(employee.getEmpmobno());
            if (UserSession.getCurrent().getLanguageId() == 1) {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            } else {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
            }

            iSMSAndEmailService.sendEmailSMS(MainetConstants.AGENCY.AUT, "AgencyAuthorization.html",
                    PrefixConstants.SMS_EMAIL.REJECTED, dto, employee2.getOrganisation(), langId);

        } else if (employee.getAuthStatus().equals(MainetConstants.FlagH)) {
            final SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setAppName(employee.getEmpname());
            dto.setAppNo(String.valueOf(employee.getEmpId()));
            dto.setEmail(employee.getEmpemail());
            dto.setMobnumber(employee.getEmpmobno());
            dto.setAppName(employee.getEmpname());

            if (UserSession.getCurrent().getLanguageId() == 1) {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            } else {
                dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
            }
            iSMSAndEmailService.sendEmailSMS(MainetConstants.AGENCY.AUT, "AgencyAuthorization.html",
                    PrefixConstants.SMS_EMAIL.HOLD, dto, employee2.getOrganisation(), langId);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.authentication.agency.service.AgencyAuthorizationService#getAttachmentsByRowidForAgency(long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<CFCAttachmentsDTO> getAgencyAttachmentsByRowId(final Long rowId, final Long orgId) {
        final List<CFCAttachmentsDTO> attachmentsDTOs = new ArrayList<>();
        final List<CFCAttachment> list = agencyAuthorizationDao.getAgencyAttachmentsByRowId(rowId, orgId);

        CFCAttachmentsDTO dto = new CFCAttachmentsDTO();
        for (final CFCAttachment attachment : list) {

            dto = serviceMapper.mapCFCAttachmentToCFCAttachmentDTO(attachment);

            attachmentsDTOs.add(dto);
        }
        return attachmentsDTOs;
    }

}
