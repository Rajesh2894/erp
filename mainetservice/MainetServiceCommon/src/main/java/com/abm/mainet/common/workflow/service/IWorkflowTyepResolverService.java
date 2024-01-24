package com.abm.mainet.common.workflow.service;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.common.workflow.domain.WorkflowMas;

/**
 * WorkflowTyepResolverService is the only service is which responsible for identification of appropriate workflow type configured
 * in workflow master. All methods of WorkflowTyepResolverService accept several parameters to identify unique workflow type. That
 * means there should be unique workflow has to be defined in workflow master to address workflow selection ambiguity at runtime.
 * 
 * All methods of WorkflowTyepResolverService will return instance of WorkflowMas or null in case workflow not defined for given
 * parameters.
 * 
 * Parameters required for resolving workflow are as follows.
 * 
 * Service Workflow - Organization ID, Department ID, Service ID, Amount and ward zone levels from 1 to 5
 * 
 * Complaint Workflow - Organization ID, Department ID, Complaint Type ID and either ward zone levels from 1 to 5 or Location ID
 * 
 * @author sanket.joshi
 *
 */
public interface IWorkflowTyepResolverService {

    /**
     * This method will identify service type workflow using orgId, deptIs, serviceId and code levels. If found then method will
     * return instance of WorkflowMas or else null.
     * 
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param serviceId Service Id
     * @param codIdOperLevel1 Ward/Zone level 1
     * @param codIdOperLevel2 Ward/Zone level 2
     * @param codIdOperLevel3 Ward/Zone level 3
     * @param codIdOperLevel4 Ward/Zone level 4
     * @param codIdOperLevel5 Ward/Zone level 5
     * @return
     */
    WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);

    /***
     * This method will identify service type workflow using orgId, deptIs, serviceId, amount and code levels.If found then method
     * will return instance of WorkflowMas or else null.
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param serviceId Service Id
     * @param amount Amount
     * @param codIdOperLevel1 Ward/Zone level 1
     * @param codIdOperLevel2 Ward/Zone level 2
     * @param codIdOperLevel3 Ward/Zone level 3
     * @param codIdOperLevel4 Ward/Zone level 4
     * @param codIdOperLevel5 Ward/Zone level 5
     * @return
     */
    WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount, Long sourceOfFund,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);
    
    /***
     * This method will identify service type workflow using orgId, deptIs, serviceId, amount and code levels.If found then method
     * will return instance of WorkflowMas or else null.
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param serviceId Service Id
     * @param amount Amount
     * @param getwmschcodeid2
     * @param codIdOperLevel1 Ward/Zone level 1
     * @param codIdOperLevel2 Ward/Zone level 2
     * @param codIdOperLevel3 Ward/Zone level 3
     * @param codIdOperLevel4 Ward/Zone level 4
     * @param codIdOperLevel5 Ward/Zone level 5
     * @return
     */
    //This method added by Suhel said to Rajesh Sir
    WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount, Long sourceOfFund,Long getwmschcodeid2,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);

    /**
     * This method will identify complaint type workflow using orgId, deptIs, compTypeId and code levels. If found then method
     * will return instance of WorkflowMas or else null.
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param compTypeId
     * @param codIdOperLevel1 Ward/Zone level 1
     * @param codIdOperLevel2 Ward/Zone level 2
     * @param codIdOperLevel3 Ward/Zone level 3
     * @param codIdOperLevel4 Ward/Zone level 4
     * @param codIdOperLevel5 Ward/Zone level 5
     * @return
     */
    WorkflowMas resolveComplaintWorkflowType(Long orgId, Long deptId, Long compTypeId,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);

    /**
     * This method will identify complaint type workflow using orgId, deptIs, compTypeId and locId. If found then method will
     * return instance of WorkflowMas or else null. This method will retrieve code levels hierarchy by using deptId and locId from
     * LocationOperationWZMapping.
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param compTypeId
     * @param locId
     * @return
     */
    WorkflowMas resolveComplaintWorkflowType(Long orgId, Long deptId, Long compTypeId, Long locId);

    /**
     * This method will identify complaint type workflow using orgId, compTypeId. If found then method will return instance of
     * WorkflowMas or else null.
     * 
     * @param orgId Organization Id
     * @param compTypeId
     * @return
     */
    WorkflowMas resolveComplaintWorkflowTypeDefination(Long orgId, Long compTypeId);

    /**
     * this method will fetch all Auto Escalation and Active Workflow
     * @param workflowMode
     * @return
     */
    List<WorkflowMas> resolveAutoEscalationWorkFlow(Long workflowMode);

    Boolean checkAmtBasedWorkflowExist(Long orgId, Long dpDeptid, Long smServiceId,Long sourceOfFund);
    
    /***
     * This method will identify service type workflow using orgId, deptIs, serviceId, amount and code levels.If found then method
     * will return instance of WorkflowMas or else null.
     * 
     * @param orgId Organization Id
     * @param deptId Department Id
     * @param serviceId Service Id
     * @param vehMainBy Vehicle Maintain By
     * @param codIdOperLevel1 Ward/Zone level 1
     * @param codIdOperLevel2 Ward/Zone level 2
     * @param codIdOperLevel3 Ward/Zone level 3
     * @param codIdOperLevel4 Ward/Zone level 4
     * @param codIdOperLevel5 Ward/Zone level 5
     * @return
     */
    WorkflowMas resolveExtWorkflowType(Long orgId, Long deptId, Long serviceId, Long extIdentifier,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);


   WorkflowMas checkgetwmschcodeid2BasedWorkflowExist(Long orgId, Long deptId, Long serviceId, Long getwmschcodeid2,
        Long codIdOperLevel1,
        Long codIdOperLevel2,
        Long codIdOperLevel3,
        Long codIdOperLevel4,
        Long codIdOperLevel5,
        BigDecimal amount);


}
