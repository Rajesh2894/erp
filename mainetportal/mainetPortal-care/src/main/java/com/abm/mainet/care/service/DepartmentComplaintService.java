package com.abm.mainet.care.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author sanket.joshi
 *
 */
@Service
public class DepartmentComplaintService implements IDepartmentComplaintService {
	
	@Autowired
	private IOrganisationService organisatonService;
	

    @Override
    public List<DepartmentComplaintDTO> getDepartmentComplaintByOrgId(final Long orgId) throws Exception {
        @SuppressWarnings("unchecked")
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null,
                        ServiceEndpoints.CARE_SERVICE_DEPARTMENTS_BY_ORG_ID_URL + MainetConstants.WINDOWS_SLASH + orgId);
        final List<DepartmentComplaintDTO> departmentList = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final DepartmentComplaintDTO dcdto = new ObjectMapper().readValue(d, DepartmentComplaintDTO.class);
                departmentList.add(dcdto);
            } catch (final Exception e) {
                throw new FrameworkException(e);
            }
        });
        return departmentList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.care.service.IDepartmentComplaintService#getCareWorkflowMasterDefinedDepartmentsListByOrgId(java.lang.Long)
     */
    @Override
    public List<DepartmentComplaintDTO> getCareWorkflowMasterDefinedDepartmentsListByOrgId(final Long orgId) throws Exception {
        @SuppressWarnings("unchecked")
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null,
                        ServiceEndpoints.CARE_SERVICE_WORKFLOW_MASTER_DEFINED_DEPARTMENTS_BY_ORG_ID_URL
                                + MainetConstants.WINDOWS_SLASH + orgId);
        
        Organisation org = organisatonService.getOrganisationById(orgId);
        /*boolean isDSCLENV = isDSCLEnvPresent();*/
        //##163366
        final List<DepartmentComplaintDTO> departmentList = new ArrayList<>();
        if(org!=null && org.getONlsOrgname().equalsIgnoreCase("Dehradun Smart City Ltd.") && null!=requestList){
        	requestList.forEach(obj -> {
                final String d = new JSONObject(obj).toString();
                try {
                    final DepartmentComplaintDTO dcdto = new ObjectMapper().readValue(d, DepartmentComplaintDTO.class);
                    if(null!=dcdto.getDepartment().getDpDeptcode() && dcdto.getDepartment().getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPARTMENT.CFC_CODE)){
                    departmentList.add(dcdto);
                    }
                } catch (final Exception e) {
                    throw new FrameworkException(e);
                }
            });
        }
        
        else {
        
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final DepartmentComplaintDTO dcdto = new ObjectMapper().readValue(d, DepartmentComplaintDTO.class);
                departmentList.add(dcdto);
            } catch (final Exception e) {
                throw new FrameworkException(e);
            }
        });
        }
        return departmentList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.care.service.IDepartmentComplaintService#getDepartmentComplaintByDepartmentId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public DepartmentComplaintDTO getDepartmentComplaintByDepartmentId(final Long depId, final Long orgId) throws Exception {
        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_DEPARTMENTS_COMPLAINT_BY_DEPARTMENT_ID_URL + MainetConstants.WINDOWS_SLASH + depId
                        + MainetConstants.WINDOWS_SLASH + orgId);
        final String d = new JSONObject(responseVo).toString();
        final DepartmentComplaintDTO departmentComplaint = new ObjectMapper().readValue(d, DepartmentComplaintDTO.class);
        return departmentComplaint;

    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.care.service.IDepartmentComplaintService#getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(
     * java.lang.Long, java.lang.Long)
     */
    @Override
    public List<DepartmentComplaintTypeDTO> getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(final Long depId,
            final Long orgId) throws Exception {
        @SuppressWarnings("unchecked")
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null,
                        ServiceEndpoints.CARE_SERVICE_WORKFLOW_MASTER_DEFINED_DEPARTMENT_COMPLAINT_TYPE_BY_DEPARTMENT_ID_URL
                                + MainetConstants.WINDOWS_SLASH
                                + depId + MainetConstants.WINDOWS_SLASH + orgId);

        final List<DepartmentComplaintTypeDTO> departmentComplaintTypeList = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final DepartmentComplaintTypeDTO dcdto = new ObjectMapper().readValue(d, DepartmentComplaintTypeDTO.class);
                departmentComplaintTypeList.add(dcdto);
            } catch (final Exception e) {
                throw new FrameworkException(e);
            }
        });
        return departmentComplaintTypeList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.care.service.IDepartmentComplaintService#getDepartmentComplaintTypeById(java.lang.Long)
     */
    @Override
    public DepartmentComplaintTypeDTO getDepartmentComplaintTypeById(final Long id) throws Exception {
        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_COMPLAINT_TYPE_BY_ID_URL + MainetConstants.WINDOWS_SLASH + id);
        final String d = new JSONObject(responseVo).toString();
        final DepartmentComplaintTypeDTO complaintType = new ObjectMapper().readValue(d, DepartmentComplaintTypeDTO.class);
        return complaintType;
    }
    
    
    public boolean isDSCLEnvPresent() {
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		return envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals("DSCL")
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		// return true;
	}

}
