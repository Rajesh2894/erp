/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;

/**
 * @author priti.singh
 *
 */
@WebService
public interface RenewalFormService {

    public RenewalFormDto findRenewalDetails(Long applicationId, String beneficiarynumber, Long orgId);

    String getbeneficiarynumber(String ulbName, String serviceSortCode, Long orgId);

    public RenewalFormDto saveRenewalDetails(final RenewalFormDto dto);

    public SocialSecurityApplicationForm fetchDataOnBenef(RenewalFormDto dto);

    void initiateWorkFlowForFreeService(RenewalFormDto dto);

    public boolean saveDecision(RenewalFormDto renewalFormDto, Long orgId, Employee emp,
            WorkflowTaskAction workFlowActionDto, RequestDTO requestDTO);

    RenewalFormDto prepareAndSaveApplicationMaster(RenewalFormDto dto);
    
    List<RenewalFormDto> getAllData(Long orgId);

}
