package com.abm.mainet.tradeLicense.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationAddressDAO;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Component
@Transactional
public class ContractorApplicantServiceImpl implements IContractorApplicantService {
	private static final Logger LOGGER = Logger.getLogger(ContractorApplicantServiceImpl.class);
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Autowired
	private ICFCApplicationAddressDAO cfcApplicationAddressDAO;
	
	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;
	
	@Override
	public RequestDTO getContractorApplicant(Long applicationId, Long orgid) {
		// TODO Auto-generated method stub
		RequestDTO masterDTO = new RequestDTO();
		masterDTO.setApplicationId(applicationId);
		try {
			TbCfcApplicationMstEntity masEntity =  iCFCApplicationMasterDAO.getCFCApplicationMasterByApplicationId(applicationId, orgid);
			CFCApplicationAddressEntity	addressEntity = cfcApplicationAddressDAO.getApplicationAddressByAppId(applicationId, orgid);
			LOGGER.info("Contractor Data fetched for application id " + applicationId);
			if (masEntity != null) {
				
				if(masEntity.getApmFname() != null) {
					masterDTO.setfName(masEntity.getApmFname());
				}
				
				if(masEntity.getApmMname() != null) {
					masterDTO.setmName(masEntity.getApmMname());
				}
				if(masEntity.getApmLname() != null) {
					masterDTO.setlName(masEntity.getApmLname());
				}
				if(masEntity.getApmTitle() != null) {
					masterDTO.setTitleId(masEntity.getApmTitle());
				}
				if(masEntity.getApmSex() != null) {
					masterDTO.setGender(masEntity.getApmSex());
				}
				if(masEntity.getTbOrganisation() != null) {
					masterDTO.setOrgId(masEntity.getTbOrganisation().getOrgid());
				}
				if(masEntity.getTbServicesMst() != null) {
					masterDTO.setServiceId(masEntity.getTbServicesMst().getSmServiceId());
				}
				if(masEntity.getUserId() != null) {
					masterDTO.setUserId(masEntity.getUserId());
				}
				if(masEntity.getLangId() != null) {
					masterDTO.setLangId(masEntity.getLangId());
				}
				if(masEntity.getCtzCitizenid() != null) {
					masterDTO.setVendorCode(masEntity.getCtzCitizenid());
				}
				
				if(addressEntity.getApaBldgnm() != null)
					masterDTO.setBldgName(addressEntity.getApaBldgnm());
				if(addressEntity.getApaBlockName() != null)
					masterDTO.setBlockName(addressEntity.getApaBlockName());
				if(addressEntity.getApaRoadnm() != null)
					masterDTO.setRoadName(addressEntity.getApaRoadnm());
				if(addressEntity.getApaCityName() != null)
					masterDTO.setCityName(addressEntity.getApaCityName());
				if(addressEntity.getApaWardNo() != null)
					masterDTO.setWardNo(addressEntity.getApaWardNo());
				if(addressEntity.getApaMobilno() != null)
					masterDTO.setMobileNo(addressEntity.getApaMobilno());
				if(addressEntity.getApaPincode() != null)
					masterDTO.setPincodeNo(addressEntity.getApaPincode());
				if(addressEntity.getApaEmail() != null)
					masterDTO.setEmail(addressEntity.getApaEmail());
				
				
					
				//BeanUtils.copyProperties(masEntity, masterDTO);
				//BeanUtils.copyProperties(addressEntity, masterDTO);
			}
				
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return masterDTO;
	}

	@Override
	@Transactional
	public String updateWorkFlowSecurityService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER_OBJ_LOI);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception Occured when Update Workflow methods", exception);
		}
		return null;
	}

}
