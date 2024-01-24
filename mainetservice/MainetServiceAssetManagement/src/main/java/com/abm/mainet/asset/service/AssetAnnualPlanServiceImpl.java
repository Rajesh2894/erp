package com.abm.mainet.asset.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.dao.AssetFunctionalLocationMasterDAO;
import com.abm.mainet.asset.domain.AssetAnnualPlan;
import com.abm.mainet.asset.domain.AssetAnnualPlanDetails;
import com.abm.mainet.asset.repository.AssetAnnualPlanRepo;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDTO;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDetailsDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class AssetAnnualPlanServiceImpl implements IAssetAnnualPlanService {

    private static final Logger LOGGER = Logger.getLogger(AssetAnnualPlanServiceImpl.class);

    @Autowired
    AssetAnnualPlanRepo annualPlanRepo;

    @Autowired
    private IAssetWorkflowService assetWorkFlowService;

    @Autowired
    private AssetFunctionalLocationMasterDAO masterDAO;

    @Transactional
    public AssetAnnualPlanDTO getAstAnnualPlanDataById(Long astAnnualPlanId) {
        AssetAnnualPlanDTO annualPlanDto = new AssetAnnualPlanDTO();
        AssetAnnualPlan astAnnualPlan = annualPlanRepo.findOne(astAnnualPlanId);
        List<AssetAnnualPlanDetailsDTO> astAnnualPlanDetailsDTO = new ArrayList<>();
        if (astAnnualPlan != null) {
            Department dept = new Department();
            dept.setDpDeptid(astAnnualPlan.getDepartment().getDpDeptid());
            astAnnualPlan.setDepartment(dept);
            LocationMasEntity locationMas = new LocationMasEntity();
            locationMas.setLocId(astAnnualPlan.getLocationMas().getLocId());
            astAnnualPlan.setLocationMas(locationMas);
            BeanUtils.copyProperties(astAnnualPlan, annualPlanDto);
            astAnnualPlan.getAnnualPlanDetails().forEach(astAnnualDet -> {
                AssetAnnualPlanDetailsDTO annualPlanDetailsDto = new AssetAnnualPlanDetailsDTO();
                BeanUtils.copyProperties(astAnnualDet, annualPlanDetailsDto);
                astAnnualPlanDetailsDTO.add(annualPlanDetailsDto);
            });
            annualPlanDto.setAstAnnualPlanDetailsDTO(astAnnualPlanDetailsDTO);
        }
        return annualPlanDto;
    }

    @Transactional
    public String saveInAssetInventoryPlan(AssetAnnualPlanDTO annualPlanDTO, WorkflowTaskAction workflowActionDto,
            Long orgId, String moduleDeptCode, String serviceCodeDeptWise) {
        AssetAnnualPlan assetAnnualPlan = new AssetAnnualPlan();
        BeanUtils.copyProperties(annualPlanDTO, assetAnnualPlan);
        List<AssetAnnualPlanDetails> annualPlanDetailList = new ArrayList<>();
        for (AssetAnnualPlanDetailsDTO astAnnualPlanDetails : annualPlanDTO.getAstAnnualPlanDetailsDTO()) {
            AssetAnnualPlanDetails annualPlanDetails = new AssetAnnualPlanDetails();
            BeanUtils.copyProperties(astAnnualPlanDetails, annualPlanDetails);
            annualPlanDetails.setAssetAnnualPlan(assetAnnualPlan);
            annualPlanDetailList.add(annualPlanDetails);
        }

        assetAnnualPlan.setAnnualPlanDetails(annualPlanDetailList);

        try {
            assetAnnualPlan = annualPlanRepo.save(assetAnnualPlan);
            // Used to initiate Workflow
            // D#85166
            TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                    .findDeptByCode(orgId, MainetConstants.FlagA,
                            moduleDeptCode);
            ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(serviceCodeDeptWise, orgId);
            // create reference Id
            String astIdStr = moduleDeptCode + "/" + sm.getSmShortdesc() + "/"
                    + assetAnnualPlan.getAstAnnualPlanId();
            boolean success = initiateRequisitionMakerCheckerWorkflow(workflowActionDto, sm.getSmServiceId(),
                    deptObj.getDpDeptid(), orgId,
                    astIdStr,
                    sm.getSmShortdesc(), MainetConstants.FlagA);

            if (success) {
                return astIdStr;
            } else {
                return null;
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Requisition ", exception);
            throw new FrameworkException("Exception occur while saving asset Requisition ", exception);
        }

    }

    public boolean initiateRequisitionMakerCheckerWorkflow(WorkflowTaskAction workflowActionDto, Long smServiceId, Long dpDeptid,
            Long orgId, String astIdStr, String smShortDesc, String sendBackflag) {

        // Code related to work flow
        WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                .resolveServiceWorkflowType(orgId, dpDeptid,
                        smServiceId, null, null, null, null, null);

        WorkflowTaskAction prepareWorkFlowTaskAction = null;
        // prepareWorkFlowTaskAction = prepareWorkFlowTaskActionCreate(astIdStr);
        prepareWorkFlowTaskAction = workflowActionDto;
        prepareWorkFlowTaskAction.setReferenceId(astIdStr);
        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(
                prepareWorkFlowTaskAction,
                workFlowMas.getWfId(), "AssetAnnualPlan.html", sendBackflag, smShortDesc);
        if (response != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<AssetAnnualPlanDTO> fetchAnnualPlanSearchData(Long finYearId, Long deptId, Long locId, Long orgId,
            String moduleDeptCode) {
        List<AssetAnnualPlanDTO> annualPlanDTOs = new ArrayList<AssetAnnualPlanDTO>();
        List<AssetAnnualPlan> annualPlans = masterDAO.searchAssetAnnualPlanData(finYearId, deptId, locId, orgId,
                moduleDeptCode);
        annualPlans.forEach(annualPlan -> {
            AssetAnnualPlanDTO dto = new AssetAnnualPlanDTO();
            BeanUtils.copyProperties(annualPlan, dto);
            dto.setLocationDesc(annualPlan.getLocationMas().getLocNameEng());
            dto.setDepartDesc(annualPlan.getDepartment().getDpDeptdesc());
            dto.setLocationMas(null);
            dto.setDepartment(null);
            dto.setStatus(StringUtils.isNotEmpty(annualPlan.getStatus()) ? annualPlan.getStatus() : "NA");
            dto.setDateDesc(Utility.dateToString(annualPlan.getCreatedDate()) + " "
                    + new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(annualPlan.getCreatedDate()));
            annualPlanDTOs.add(dto);
        });

        return annualPlanDTOs;
    }

    @Override
    @Transactional
    public void updateAnnualPlan(AssetAnnualPlanDTO annualPlanDTO) {
        AssetAnnualPlan assetAnnualPlan = new AssetAnnualPlan();
        BeanUtils.copyProperties(annualPlanDTO, assetAnnualPlan);
        assetAnnualPlan = annualPlanRepo.save(assetAnnualPlan);
    }

}
