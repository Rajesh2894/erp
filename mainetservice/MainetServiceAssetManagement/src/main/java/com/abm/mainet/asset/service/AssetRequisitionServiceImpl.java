package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.dao.AssetFunctionalLocationMasterDAO;
import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetRequisition;
import com.abm.mainet.asset.repository.AssetInformationRepo;
import com.abm.mainet.asset.repository.AssetRequisitionRepo;
import com.abm.mainet.asset.ui.dto.AssetRequisitionDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class AssetRequisitionServiceImpl implements IAssetRequisitionService {

    private static final Logger LOGGER = Logger.getLogger(AssetRequisitionServiceImpl.class);

    @Autowired
    AssetRequisitionRepo assetRequisitionRepo;
    @Autowired
    private IAssetWorkflowService assetWorkFlowService;

    @Autowired
    private AssetFunctionalLocationMasterDAO masterDAO;
    
    @Autowired
    private ILocationMasService locationMasService;
    
    @Autowired
    private TbDepartmentService tbDepartmentService;
    
   
    @Autowired
    private AssetInformationRepo assetRepo;

    @Override
    public AssetRequisitionDTO getAstRequisitionDataById(Long astReqId) {
        AssetRequisitionDTO reqDto = new AssetRequisitionDTO();
        AssetRequisition astRequisition = assetRequisitionRepo.findByAssetRequisitionId(astReqId);
        
        
        if (astRequisition != null)
            BeanUtils.copyProperties(astRequisition, reqDto);

        return reqDto;
    }

    @Transactional
    public String saveInRequisition(AssetRequisitionDTO assetRequisitionDTO, WorkflowTaskAction workflowActionDto,
            String sendBackflag, Long orgId, String moduleDeptCode, String serviceCodeDeptWise) {
    	if(locationMasService.findByLocationList(assetRequisitionDTO.getAstLoc())) {
        AssetRequisition requisition = new AssetRequisition();
        
        
        if(assetRequisitionDTO.getAstLoc() != null &&  assetRequisitionDTO.getAstDept()!= null) {
			
         LocOperationWZMappingDto wzMapping = locationMasService.findOperLocationAndDeptId(assetRequisitionDTO.getAstLoc(),assetRequisitionDTO.getAstDept());
			if(wzMapping==null) {
				return null;
			}
			else if (wzMapping.getCodIdOperLevel1()==null) {
				String prefixName = tbDepartmentService.findDepartmentPrefixName(assetRequisitionDTO.getAstDept(),orgId);
					if (prefixName == null || prefixName.isEmpty()) {
						TbDepartment deptObj = ApplicationContextProvider.getApplicationContext()
								.getBean(TbDepartmentService.class).findDeptByCode(orgId, MainetConstants.FlagA, "CFC");
						wzMapping = locationMasService.findOperLocationAndDeptId(assetRequisitionDTO.getAstLoc(),
								deptObj.getDpDeptid());
					} else if(wzMapping == null){
						return null;
					}
		 }
		  
			
            if (wzMapping != null) {
                if (wzMapping.getCodIdOperLevel1() != null) {
                    assetRequisitionDTO.setWard1(wzMapping.getCodIdOperLevel1());
                }
                if (wzMapping.getCodIdOperLevel2() != null) {
                	assetRequisitionDTO.setWard2(wzMapping.getCodIdOperLevel2());
                }
                if (wzMapping.getCodIdOperLevel3() != null) {
                	assetRequisitionDTO.setWard3(wzMapping.getCodIdOperLevel3());
                }
                if (wzMapping.getCodIdOperLevel4() != null) {
                	assetRequisitionDTO.setWard4(wzMapping.getCodIdOperLevel4());
                }
                if (wzMapping.getCodIdOperLevel5() != null) {
                	assetRequisitionDTO.setWard5(wzMapping.getCodIdOperLevel5());
                }
            }
            else {
            	return null;
            }
			         }
        
        
        BeanUtils.copyProperties(assetRequisitionDTO, requisition);
        try {
            requisition = assetRequisitionRepo.save(requisition);
            ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(serviceCodeDeptWise, orgId);
            String astIdStr = moduleDeptCode + "/" + sm.getSmShortdesc() + "/" + requisition.getAssetRequisitionId();
            assetRequisitionDTO.setRequisitionNumber(astIdStr);
            assetRequisitionRepo.updateRequisitionNumber(requisition.getAssetRequisitionId(),assetRequisitionDTO.getRequisitionNumber(),requisition.getOrgId());
            // Used to initiate Workflow
            initiateRequisitionMakerCheckerWorkflow(workflowActionDto, requisition.getAssetRequisitionId(), orgId,
                    sendBackflag, moduleDeptCode, serviceCodeDeptWise,assetRequisitionDTO.getAstDept() ,
                    assetRequisitionDTO.getWard1(),assetRequisitionDTO.getWard2(),
                    assetRequisitionDTO.getWard3(),assetRequisitionDTO.getWard4(),
                    assetRequisitionDTO.getWard5());
            return workflowActionDto.getReferenceId();
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Requisition ", exception);
            return null;
            //throw new FrameworkException("Exception occur while saving asset Requisition ", exception);
        }
    	}
    	else {
    		LOGGER.error("Exception occur while saving asset Requisition ");
    		return null;
            //throw new FrameworkException("Exception occur while saving asset Requisition ");
    	}

    }

    public boolean initiateRequisitionMakerCheckerWorkflow(WorkflowTaskAction workflowActionDto, Long astRequisitionId,
            Long orgId,
            String sendBackflag, String moduleDeptCode, String serviceCodeDeptWise, Long astDept,Long ward1,Long ward2,Long ward3,Long ward4,Long ward5 ) {
        TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDeptByCode(orgId, MainetConstants.FlagA, moduleDeptCode);
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(serviceCodeDeptWise, orgId);
        
        
        // Code related to work flow
        WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                .checkgetwmschcodeid2BasedWorkflowExist(orgId, deptObj.getDpDeptid(),
                        sm.getSmServiceId(),astDept, ward1, ward2, ward3, ward4, ward5,null);
        // create reference Id
        String astIdStr = moduleDeptCode + "/" + sm.getSmShortdesc() + "/" + astRequisitionId;
        WorkflowTaskAction prepareWorkFlowTaskAction = null;
        // prepareWorkFlowTaskAction = prepareWorkFlowTaskActionCreate(astIdStr);
        prepareWorkFlowTaskAction = workflowActionDto;
        prepareWorkFlowTaskAction.setReferenceId(astIdStr);
        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(
                prepareWorkFlowTaskAction,
                workFlowMas.getWfId(), "AssetRequisition.html", sendBackflag, sm.getSmShortdesc());
        if (response != null) {
            // D#89521
            workflowActionDto.setReferenceId(astIdStr);
            return true;
        } else {
            return false;
        }

        // It updates the flag if and only if task is created in workflow
        /*
         * if (response != null) { infoService.updateAppStatusFlag(assetId, orgId,
         * MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING); }
         */
    }

    // D#89523
    @Override
    public List<AssetRequisitionDTO> fetchSearchData(Long astCategory, Long locId, Long deptId, Long orgId,
            String moduelDeptCode) {
        List<AssetRequisitionDTO> requisitionDTOList = new ArrayList<AssetRequisitionDTO>();
        List<AssetRequisition> requisitions = masterDAO.searchAssetRequisitionData(astCategory, locId, deptId, orgId,
                moduelDeptCode);
        String modulePrfiex = moduelDeptCode.equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL" : "ICL";
        requisitions.forEach(requisition -> {
            AssetRequisitionDTO dto = new AssetRequisitionDTO();
            BeanUtils.copyProperties(requisition, dto);
            // get asset category from prefix ACL/ICL based on module DEPT code
            LookUp astCategoryLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.getAstCategory(), orgId,
                    modulePrfiex);
            dto.setAstCategoryDesc(astCategoryLookup.getLookUpDesc());
            dto.setAstDeptDesc(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                    .fetchDepartmentDescById(dto.getAstDept()));
            LocationMasEntity locEntity = ApplicationContextProvider.getApplicationContext()
                    .getBean(ILocationMasService.class)
                    .findbyLocationId(dto.getAstLoc());
            dto.setAstLocDesc(locEntity != null ? locEntity.getLocNameEng() : "NA");
            dto.setStatus(StringUtils.isNotEmpty(requisition.getStatus()) ? requisition.getStatus() : "NA");
            requisitionDTOList.add(dto);
        });

        return requisitionDTOList;
    }

    @Override
    @Transactional
    public void updateRequisition(AssetRequisitionDTO assetRequisitionDTO) {
        AssetRequisition requisition = new AssetRequisition();
        BeanUtils.copyProperties(assetRequisitionDTO, requisition);
        requisition = assetRequisitionRepo.save(requisition);

    }
    
    @Override
    public List<AssetInformationDTO> getAstRequisitionById(Long astReqId) {
        AssetRequisitionDTO reqDto = new AssetRequisitionDTO();
        List<AssetInformationDTO> assestDtoList = new ArrayList<>(0);
        AssetRequisition astRequisition = assetRequisitionRepo.findByAssetRequisitionId(astReqId);
        if (astRequisition != null)
            BeanUtils.copyProperties(astRequisition, reqDto);
       if(reqDto.getAssetId() != null ) {
    	   //List<Long> assetIdList = new ArrayList<>();
    	   String fileArray[] = reqDto.getAssetId().split(MainetConstants.operator.COMMA);
    	   for(String fields : fileArray) {
    		   AssetInformationDTO aasestDto = new AssetInformationDTO();
    		   AssetInformation entity = assetRepo.findInfoByAssetId(Long.valueOf(fields));
    		   if (entity != null)
    			   
    	            BeanUtils.copyProperties(entity, aasestDto);
    		   if(aasestDto.getSerialNo().isEmpty())
    		   {
    			   aasestDto.setSerialNo(aasestDto.getAssetModelIdentifier());
    		   }
    		   assestDtoList.add(aasestDto);
    		   
    	   }
       }
       return assestDtoList;
    }
    

	
}
