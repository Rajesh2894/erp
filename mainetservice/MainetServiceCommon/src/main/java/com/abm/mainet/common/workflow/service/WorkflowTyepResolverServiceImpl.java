package com.abm.mainet.common.workflow.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;

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
 * Service Workflow - Organization ID, Department ID, Service ID, Amount and either ward zone levels from 1 to 5
 * 
 * Complaint Workflow - Organization ID, Department ID, Complaint Type ID and either ward zone levels from 1 to 5 or Location ID
 * 
 * 
 * @author sanket.joshi
 *
 */
@Service
public class WorkflowTyepResolverServiceImpl implements IWorkflowTyepResolverService {

    @Autowired
    private WorkFlowTypeRepository iWorkflowRepository;

    @Autowired
    private IWorkflowTypeDAO workflowTypeDAO;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private ServiceMasterService serviceMasterService;

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTyepResolverServiceImpl.class);

    @Override
    public WorkflowMas resolveServiceWorkflowType(final Long orgId, final Long deptId, final Long serviceId,
            final Long codIdOperLevel1, final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4, final Long codIdOperLevel5) {
        WorkflowMas workflowType = null;
        // Search for work-flow defined for all ward and zone by OGR, DEPARTMENT,
        // SERVICE
        if ((orgId != null) && (deptId != null) && (serviceId != null)) {
            workflowType = iWorkflowRepository.getWorkFlowTypeByOrgDepartmentAndServiceIdForAllWardZone(orgId, deptId,
                    serviceId);
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // SERVICE
        if (workflowType == null) {
            if ((orgId != null) && (deptId != null) && (serviceId != null) && (codIdOperLevel1 != null)) {
                workflowType = workflowTypeDAO.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, null,
                        codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4, codIdOperLevel5);
            }
        }

        // check All option present in workflow
        if (workflowType == null) {
            // by using deptId ,serviceId,orgId,wt.type = 'N'
            // WorkflowMas workflowTypeAll =
            // workflowTypeDAO.getServiceWorkFlowForWardZone(orgId,deptId,serviceId,null,codIdOperLevel1,null,null,null,null);
            // D#90817
            List<WorkflowMas> workflows = workflowTypeDAO.getAllWorkFlows(orgId, deptId, serviceId);

            // iterate workflow with status='Y' and type='N' for all and check one by one
            for (WorkflowMas wf : workflows) {
                if (wf.getStatus() != null && wf.getType() != null && wf.getStatus().equalsIgnoreCase("Y")
                        && wf.getType().equalsIgnoreCase("N")) {
                    workflowType = checkAllOptionSelectedAtAnyLevel(wf, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3,
                            codIdOperLevel4, codIdOperLevel5, orgId);
                    if (workflowType != null) {
                        break;
                    }
                }
            }

        }

        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }

    @Override
    public WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
            Long codIdOperLevel4, Long codIdOperLevel5) {
        WorkflowMas workflowType = null;
        ServiceMaster serviceMaster = null;
        if(serviceId != null && orgId != null) {
            serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
        }
        // Search for work-flow defined for specific DIV, WARD ZONE by ORG, DEPARTMENT,
        // SERVICE, AND CONNECTION SIZE
        if(serviceMaster!=null && MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(serviceMaster.getSmShortdesc())) {
	        if (deptId != null && amount != null && codIdOperLevel1 != null) {
	        	 
	        workflowType = workflowTypeDAO.getWorkFlowByDivWardZoneAndConnSizeSpecification(
	        			orgId, deptId, serviceId, amount, codIdOperLevel1);
	        	
	        }
        }   
        // Search for work-flow defined for all ward and zone by OGR, DEPARTMENT,
        // SERVICE, AMOUNT
        else if (orgId != null && deptId != null && serviceId != null && amount != null) {
            workflowType = workflowTypeDAO.getServiceWorkFlowForAllWardZone(orgId, deptId, serviceId, amount,
                    sourceOfFund);
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // SERVICE, AMOUNT,SOURCEOFFUND
        if (workflowType == null) {
            if (orgId != null && deptId != null && serviceId != null && codIdOperLevel1 != null) {
                workflowType = workflowTypeDAO.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, amount,
                        codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4, codIdOperLevel5);
            }
        }
        // check All option present in workflow
        if (workflowType == null) {
            // by using deptId ,serviceId,orgId,wt.type = 'N'
            // WorkflowMas workflowTypeAll = workflowTypeDAO.getServiceWorkFlowForWardZone(orgId, deptId, serviceId,
            // amount,codIdOperLevel1, null, null, null, null);
            // D#90817
            List<WorkflowMas> workflows = workflowTypeDAO.getAllWorkFlows(orgId, deptId, serviceId);

            // iterate workflow with status='Y' and type='N' for all and check one by one
            for (WorkflowMas wf : workflows) {
                if (wf.getStatus() != null && wf.getType() != null && wf.getStatus().equalsIgnoreCase("Y")
                        && wf.getType().equalsIgnoreCase("N") && wf.getFromAmount()!=null && wf.getToAmount()!=null
                        && wf.getFromAmount().compareTo(amount) <= 0 && wf.getToAmount().compareTo(amount) >= 0) {
                    workflowType = checkAllOptionSelectedAtAnyLevel(wf, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3,
                            codIdOperLevel4, codIdOperLevel5, orgId);
                    if (workflowType != null) {
                        break;
                    }
                }
            }
        }
        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }

    @Override
    public WorkflowMas resolveComplaintWorkflowType(final Long orgId, final Long deptId, final Long compTypeId,
            final Long codIdOperLevel1, final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4, final Long codIdOperLevel5) {

        WorkflowMas workflowType = null;
        // Search for work-flow defined for all ward and zone by OGR, DEPARTMENT,
        // COMPLAINT TYPE
        if (orgId != null && compTypeId != null) {
            workflowType = iWorkflowRepository.getWorkFlowTypeByOrgDepartmentAndComplaintTypeForAllWardZone(orgId,
                    compTypeId);
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // COMPLAINT TYPE
        if (workflowType == null) {
            if ((orgId != null) && (compTypeId != null)) {
                workflowType = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(orgId,
                        compTypeId, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4,
                        codIdOperLevel5);
            }
        }
        // check All option present in workflow
        if (workflowType == null) {
            // by using orgId,wt.type = 'N'
            WorkflowMas workflowTypeAll = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(orgId,
                    compTypeId, codIdOperLevel1, null, null, null, null);
            if (workflowTypeAll != null) {
                workflowType = checkAllOptionSelectedAtAnyLevel(workflowTypeAll, codIdOperLevel1, codIdOperLevel2,
                        codIdOperLevel3,
                        codIdOperLevel4, codIdOperLevel5, orgId);
            }
        }

        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }

        return workflowType;
    }

    @Override
    public WorkflowMas resolveComplaintWorkflowType(final Long orgId, final Long deptId, final Long compTypeId,
            final Long locId) {

        /*
         * D#117825 if need location ward zone mapping workflow than uncomment below code final LocationOperationWZMapping
         * cocationOperationWZMapping = iLocationMasService .findbyLocationAndDepartment(locId, deptId);
         */
        WorkflowMas workflowType = null;

        /*
         * if (cocationOperationWZMapping != null) { workflowType = resolveComplaintWorkflowType(orgId, deptId, compTypeId,
         * cocationOperationWZMapping.getCodIdOperLevel1(), cocationOperationWZMapping.getCodIdOperLevel2(),
         * cocationOperationWZMapping.getCodIdOperLevel3(), cocationOperationWZMapping.getCodIdOperLevel4(),
         * cocationOperationWZMapping.getCodIdOperLevel5()); }
         */
        if (workflowType == null) {
            workflowType = resolveComplaintWorkflowType(orgId, deptId, compTypeId, null, null, null, null, null);
        }
        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }

    @Override
    public WorkflowMas resolveComplaintWorkflowTypeDefination(Long orgId, Long compTypeId) {
        WorkflowMas workflowType = null;
        if (orgId != null && compTypeId != null) {
            // Search for work-flow defined for all ward and zone by OGR, COMPLAINT TYPE
            workflowType = iWorkflowRepository.getWorkFlowTypeByOrgDepartmentAndComplaintTypeForAllWardZone(orgId,
                    compTypeId);
        }

        if (workflowType == null) {
            // Search for work-flow defined for ward and zone by OGR, COMPLAINT TYPE
            workflowType = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(orgId, compTypeId,
                    null, null, null, null, null);
        }
        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowMas> resolveAutoEscalationWorkFlow(Long workflowMode) {
        return iWorkflowRepository.resolveAutoEscalationWorkFlow(workflowMode);
    }

    // ADDINNG NEW METHOD FOR getwmschcodeid2
    @Override
    public WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long getwmschcodeid2, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
            Long codIdOperLevel4, Long codIdOperLevel5) {
        WorkflowMas workflowType = null;
        // Search for work-flow defined for all ward and zone by OGR, DEPARTMENT,
        // SERVICE, AMOUNT,SOURCEOFFUND,SCHENAME
        if (orgId != null && deptId != null && serviceId != null && amount != null) {
            workflowType = workflowTypeDAO.getServiceWorkFlowForAllWardZone(orgId, deptId, serviceId, amount,
                    sourceOfFund, getwmschcodeid2);
        }
        //if amount based workflow not configured for works module
        if (workflowType == null) {
        if (orgId != null && deptId != null && serviceId != null && getwmschcodeid2 != null) {
            workflowType = workflowTypeDAO.getServiceWorkFlowForAllWardZone(orgId, deptId, serviceId, null,
                    sourceOfFund, getwmschcodeid2);
         }
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // SERVICE, AMOUNT,SOURCEOFFUND
        if (workflowType == null) {
            if (orgId != null && deptId != null && serviceId != null && codIdOperLevel1 != null) {
                workflowType = workflowTypeDAO.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, amount,
                        codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4, codIdOperLevel5);
            }
        }
        if (workflowType == null) {
            // check level is selected All option at workflow
            // by using deptId ,serviceId,orgId,wt.type = 'N'
            WorkflowMas workflowTypeAll = workflowTypeDAO.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, amount, null,
                    null, null, null, null);
            if (workflowTypeAll != null) {
                workflowType = checkAllOptionSelectedAtAnyLevel(workflowTypeAll, codIdOperLevel1, codIdOperLevel2,
                        codIdOperLevel3,
                        codIdOperLevel4, codIdOperLevel5, orgId);
            }
        }
        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }

    public Boolean checkAllOptionPresent(Long level, Long orgId) {
        // here code added because D#79918 (All value is -1) impact on current code
        if (level != null) {
            if (level == -1) {
                return true;
            }
            LookUp lookUp2 = CommonMasterUtility.getHierarchicalLookUp(level, orgId);
            if (lookUp2 != null && lookUp2.getLookUpDesc() != null
                    && lookUp2.getLookUpDesc().equalsIgnoreCase(MainetConstants.All)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    // common method for check All option selected or not at the time of workflow create ward-zone wise
    public WorkflowMas checkAllOptionSelectedAtAnyLevel(WorkflowMas workflowTypeAll, final Long codIdOperLevel1,
            final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4, final Long codIdOperLevel5, Long orgId) {

        if (workflowTypeAll.getCodIdOperLevel1() != null && workflowTypeAll.getCodIdOperLevel1().equals(codIdOperLevel1)) {
            // go for 2nd level
            if (workflowTypeAll.getCodIdOperLevel2() != null && workflowTypeAll.getCodIdOperLevel2().equals(codIdOperLevel2)) {
                // go for 3rd level
                if (workflowTypeAll.getCodIdOperLevel3() != null
                        && workflowTypeAll.getCodIdOperLevel3().equals(codIdOperLevel3)) {
                    // go for 4th level
                    if (workflowTypeAll.getCodIdOperLevel4() != null
                            && workflowTypeAll.getCodIdOperLevel4().equals(codIdOperLevel4)) {
                        // go for 5th level
                        if (workflowTypeAll.getCodIdOperLevel5() != null
                                && !workflowTypeAll.getCodIdOperLevel5().equals(codIdOperLevel5)) {
                            // check for All option select
                            if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel5(), orgId)) {
                                workflowTypeAll = null;
                            }
                        }
                    } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel4(), orgId)) {
                        workflowTypeAll = null;
                    }
                } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel3(), orgId)) {
                    // check for All option select
                    workflowTypeAll = null;
                }
            } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel2(), orgId)) {
                workflowTypeAll = null;
            }
        } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel1(), orgId)) {
            workflowTypeAll = null;
        }

        return workflowTypeAll;

    }
    //#134188->to check amount based workflow is defined or not
	@Override
	public Boolean checkAmtBasedWorkflowExist(Long orgId, Long dpDeptid, Long smServiceId,Long getwmschcodeid2) {
		boolean result = iWorkflowRepository.checkAmtBasedWorkflowExits(orgId, dpDeptid, smServiceId,getwmschcodeid2);
		return result;
	}
	
	@Override
    public WorkflowMas resolveExtWorkflowType(final Long orgId, final Long deptId, final Long serviceId,
    		final Long extIdentifier, final Long codIdOperLevel1, final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4, final Long codIdOperLevel5) {

        WorkflowMas workflowType = null;
        // Search for work-flow defined for all ward and zone by OGR, DEPARTMENT,
        // Workflow External Identifier
        if (orgId != null && extIdentifier != null && serviceId !=null) {
            workflowType = iWorkflowRepository.getWorkFlowTypeByOrgDepartmentAndExtForAllWardZone(orgId,
            		extIdentifier, serviceId);
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // Workflow External Identifier
        if (workflowType == null) {
            if ((orgId != null) && (extIdentifier != null)) {
                workflowType = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndExtForWardZone(orgId,
                		extIdentifier, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4,
                        codIdOperLevel5,serviceId);
            }
        }
        // check All option present in workflow
        if (workflowType == null) {
            // by using orgId,wt.type = 'N'
            WorkflowMas workflowTypeAll = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndExtForWardZone(orgId,
            		extIdentifier, codIdOperLevel1, null, null, null, null,serviceId);
            if (workflowTypeAll != null) {
                workflowType = checkAllOptionSelectedAtAnyLevel(workflowTypeAll, codIdOperLevel1, codIdOperLevel2,
                        codIdOperLevel3,
                        codIdOperLevel4, codIdOperLevel5, orgId);
            }
        }

        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }

        return workflowType;
    }
	
	// ADDINNG NEW METHOD FOR getwmschcodeid2
    @Override
    public WorkflowMas checkgetwmschcodeid2BasedWorkflowExist(Long orgId, Long deptId, Long serviceId,Long getwmschcodeid2, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
            Long codIdOperLevel4, Long codIdOperLevel5,BigDecimal amount) {
        WorkflowMas workflowType = null;
         if ((orgId != null) && (deptId != null) && (serviceId != null)) {
            workflowType = workflowTypeDAO.getServiceWorkFlowForAllWardZone(orgId, deptId,
                    serviceId,amount, null, getwmschcodeid2);
        }
		 
        if (workflowType == null && (orgId != null) && (getwmschcodeid2 != null)) {
            workflowType = workflowTypeDAO.getWorkFlowTypeByOrgDepartmentAndWmsForWardZone(orgId,
            		getwmschcodeid2, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4,
                    codIdOperLevel5,serviceId, amount);
        }
        if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowType;
    }
}
