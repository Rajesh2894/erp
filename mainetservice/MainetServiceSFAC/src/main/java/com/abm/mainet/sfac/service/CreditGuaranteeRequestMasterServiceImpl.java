package com.abm.mainet.sfac.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.domain.CreditGuaranteeCGFMasterEntity;
import com.abm.mainet.sfac.domain.EquityGrantDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantFuctionalCommitteeDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;
import com.abm.mainet.sfac.domain.EquityGrantShareHoldingDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.dto.CreditGuaranteeCGFMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantFunctionalCommitteeDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantShareHoldingDetailDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.repository.CreditGuaranteeRequestRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;

@Service
public class CreditGuaranteeRequestMasterServiceImpl implements CreditGuaranteeRequestMasterService {

	private static final Logger logger = Logger.getLogger(CreditGuaranteeRequestMasterServiceImpl.class);

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired CreditGuaranteeRequestRepository creditGuaranteeRequestRepository;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Override
	public FPOMasterDto getFPODetails(Long masId) {
		// TODO Auto-generated method stub
		FPOMasterDto fpoMasterDto = new FPOMasterDto();
		FPOMasterEntity fpoMasterEntity =   fpoMasterRepository.findOne(masId);
		BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);

		return fpoMasterDto;
	}

	@Override
	public List<CreditGuaranteeCGFMasterDto> getAppliacationDetails(Long fpoId, String status) {
		// TODO Auto-generated method stub
		List<CreditGuaranteeCGFMasterDto> creditGuaranteeCGFMasterDtos = new ArrayList<>();
		List<CreditGuaranteeCGFMasterEntity> creditGuaranteeCGFMasterEntities = new ArrayList<>();
		if((fpoId!=null && fpoId != 0)  && (status!=null && !status.isEmpty())) {


			creditGuaranteeCGFMasterEntities = creditGuaranteeRequestRepository.findByFpoIdAndAppStatus(fpoId, status);
		}else {
			creditGuaranteeCGFMasterEntities = creditGuaranteeRequestRepository.findByFpoId(fpoId);
		}

		FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(fpoId);

		for (CreditGuaranteeCGFMasterEntity entity : creditGuaranteeCGFMasterEntities) {
			CreditGuaranteeCGFMasterDto dto = new CreditGuaranteeCGFMasterDto();

			BeanUtils.copyProperties(entity, dto);
			FPOMasterDto fpoMasterDto = new FPOMasterDto();
			BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
			dto.setFpoMasterDto(fpoMasterDto);

			creditGuaranteeCGFMasterDtos.add(dto);
		}

		return creditGuaranteeCGFMasterDtos;
	}

	@Override
	public CreditGuaranteeCGFMasterDto saveAndUpdateApplication(CreditGuaranteeCGFMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("EGR",
							orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());

			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);

			if ((mastDto.getDocumentList() != null) && !mastDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(applicationId);
				fileUploadService.doFileUpload(mastDto.getDocumentList(), requestDto);
			}

			CreditGuaranteeCGFMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = creditGuaranteeRequestRepository.save(masEntity);
			mastDto.setCgfID(masEntity.getCgfID());

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(mastDto.getApplicationNumber());
				applicationData.setOrgId(orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				mastDto.getApplicantDetailDto().setUserId(mastDto.getCreatedBy());
				mastDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				mastDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				mastDto.getApplicantDetailDto().setExtIdentifier(mastDto.getFpoMasterDto().getCbboId());
				if (requestDto != null && requestDto.getMobileNo() != null) {
					mastDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, mastDto.getApplicantDetailDto());
			}



		} catch (Exception e) {
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private CreditGuaranteeCGFMasterEntity mapDtoToEntity(CreditGuaranteeCGFMasterDto mastDto) {

		CreditGuaranteeCGFMasterEntity masEntity = new CreditGuaranteeCGFMasterEntity();



		BeanUtils.copyProperties(mastDto, masEntity);
		masEntity.setFpoId(mastDto.getFpoMasterDto().getFpoId());
		masEntity.setState(mastDto.getFpoMasterDto().getSdb1());
		masEntity.setFpoAddress(mastDto.getFpoMasterDto().getFpoOffAddr());
		masEntity.setRegistrationNo(mastDto.getFpoMasterDto().getFpoRegNo());
		masEntity.setRegistrationDate(mastDto.getFpoMasterDto().getDateIncorporation());
		masEntity.setFpoPanNo(mastDto.getFpoMasterDto().getFpoPanNo());
		if(mastDto.getFpoMasterDto().getFpoTanNo()!=null)
			masEntity.setFpoTanNo(mastDto.getFpoMasterDto().getFpoTanNo());
		if(mastDto.getFpoMasterDto().getGstin()!=null)
			masEntity.setGstin(mastDto.getFpoMasterDto().getGstin());

		return masEntity;
	}

	private RequestDTO setApplicantRequestDto(CreditGuaranteeCGFMasterDto masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());

		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());

		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;
	}

	@Override
	public CreditGuaranteeCGFMasterDto fetchCreditGauranteebyAppId(Long valueOf) {
		// TODO Auto-generated method stub
		CreditGuaranteeCGFMasterDto creditGuaranteeCGFMasterDto = new CreditGuaranteeCGFMasterDto();
		CreditGuaranteeCGFMasterEntity creditGuaranteeCGFMasterEntity =  creditGuaranteeRequestRepository.findByApplicationNumber(valueOf);
		
		FPOMasterDto fpoMasterDto = getFPODetails(creditGuaranteeCGFMasterEntity.getFpoId());
		BeanUtils.copyProperties(creditGuaranteeCGFMasterEntity, creditGuaranteeCGFMasterDto);
		creditGuaranteeCGFMasterDto.setFpoMasterDto(fpoMasterDto);
		
	
		return creditGuaranteeCGFMasterDto;
	}

	@Override
	public void updateApprovalStatusAndRemark(CreditGuaranteeCGFMasterDto oldMasDto, String lastDecision, String status) {
		
		CreditGuaranteeCGFMasterEntity creditGuaranteeCGFMasterEntity = creditGuaranteeRequestRepository.findOne(oldMasDto.getCgfID());
		
		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			creditGuaranteeCGFMasterEntity.setAppStatus(lastDecision);
			

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			creditGuaranteeCGFMasterEntity.setAppStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED))  {
			creditGuaranteeCGFMasterEntity.setAppStatus(lastDecision);

		}
		
		
		
	
		creditGuaranteeCGFMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());
		
		creditGuaranteeRequestRepository.save(creditGuaranteeCGFMasterEntity);
		
		/*equityGrantMasterRepository.updateApprovalStatusAndRemark(oldMasDto.getEgId(), 
				oldMasDto.getAppStatus());*/
		/*allocationOfBlocksHistRepo.updateApprovalStatusAndRemHist(oldMasDto.getBlockId(), oldMasDto.getApplicationId(),
				oldMasDto.getStatus(), oldMasDto.getAuthRemark());*/
		
	
}

}
