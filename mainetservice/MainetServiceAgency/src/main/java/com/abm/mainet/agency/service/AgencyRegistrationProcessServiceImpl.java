package com.abm.mainet.agency.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.agency.authentication.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.agency.authentication.dto.AgencyEmployeeResDTO;
import com.abm.mainet.agency.authentication.mapper.AgencyRegistrationMapper;
import com.abm.mainet.agency.dao.AgencyRegistrationProcessDao;
import com.abm.mainet.agency.dto.TPAgencyReqDTO;
import com.abm.mainet.agency.dto.TPAgencyResDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ApplicationService;

/**
 * @author Arun.Chavda
 *
 */
@Service
public class AgencyRegistrationProcessServiceImpl implements AgencyRegistrationProcessService {

    @Resource
    private AgencyRegistrationProcessDao agencyRegProcessDao;

    @Resource
    private AgencyRegistrationMapper serviceMapper;

    @Resource
    IFileUploadService fileUploadService;

    @Resource
    private ApplicationService applicationService;
    @Override

    @Transactional
    public AgencyEmployeeResDTO saveAgnEmployeeDetails(final AgencyEmployeeReqDTO requestDto) {

        final Employee employee = serviceMapper.mapAgencyEmployeeReqDTOToTbEmployee(requestDto);
        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDto.getOrganisation());
        employee.setOrganisation(organisation);
        final Designation designation = new Designation();
        designation.setDsgid(requestDto.getDesignationId());
        employee.setDesignation(designation);
        final LocationMasEntity departmentLocation = new LocationMasEntity();
        departmentLocation.setLocId(requestDto.getDeptLocId());
        employee.setTbLocationMas(departmentLocation);
        employee.setUserId(requestDto.getUserId());
        employee.setUpdatedBy(requestDto.getUpdatedBy());
        employee.setLmodDate(new Date());
        employee.setIsDeleted(MainetConstants.Common_Constant.ZERO_SEC);
        employee.setCpdTtlId(requestDto.getTitleId());
        employee.setAuthStatus(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER);
        final Employee tbEmployee = agencyRegProcessDao.saveAgnEmployeeDetails(employee);
        final AgencyEmployeeResDTO agencyEmployeeResDTO = serviceMapper.mapTbEmployeeToAgencyEmployeeResDTO(tbEmployee);
        if (null != tbEmployee) {

            fileUploadService.doFileUpload(requestDto.getDocumentList(), requestDto);
            agencyEmployeeResDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
        }

        return agencyEmployeeResDTO;
    }
    @Override
    @Transactional(readOnly = true)
    public TPAgencyResDTO getAuthStatus(final TPAgencyReqDTO requestDTO) {
        return agencyRegProcessDao.getAuthStatus(requestDTO);
    }
    @Override
    @Transactional
    public TPAgencyResDTO saveReUploadDocuments(final TPAgencyReqDTO requestDTO) {

        final TPAgencyResDTO agencyResDTO = new TPAgencyResDTO();
        final boolean isUploaded = fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
        if (isUploaded) {
            agencyResDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
            final Long empId = requestDTO.getEmpId();
            final Long orgId = requestDTO.getOrgId();
            final String flag = MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER;
            agencyRegProcessDao.updatedAuthStatus(empId, orgId, flag);
        }
        return agencyResDTO;
    }

}
