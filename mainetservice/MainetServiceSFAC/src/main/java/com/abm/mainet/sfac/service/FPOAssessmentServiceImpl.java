/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.dao.FpoAssessmentMasterDao;
import com.abm.mainet.sfac.domain.FPOAssessmentKeyParamEntity;
import com.abm.mainet.sfac.domain.FPOAssessmentKeyParamHistory;
import com.abm.mainet.sfac.domain.FPOAssessmentMasterEntity;
import com.abm.mainet.sfac.domain.FPOAssessmentMasterHistory;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamDetail;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamDetailHistory;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamEntity;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamHistory;
import com.abm.mainet.sfac.dto.FpoAssKeyParameterDto;
import com.abm.mainet.sfac.dto.FpoAssSubParamDetailDto;
import com.abm.mainet.sfac.dto.FpoAssSubParameterDto;
import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.repository.FPOAssKeyParamHistRepository;
import com.abm.mainet.sfac.repository.FPOAssKeyParamRepository;
import com.abm.mainet.sfac.repository.FPOAssMasterHistoryRepository;
import com.abm.mainet.sfac.repository.FPOAssMasterRepository;
import com.abm.mainet.sfac.repository.FPOAssSubParamDetHistRepository;
import com.abm.mainet.sfac.repository.FPOAssSubParamDetRepository;
import com.abm.mainet.sfac.repository.FPOAssSubParamHistRepository;
import com.abm.mainet.sfac.repository.FPOAssSubParamRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.ui.model.FPOAssApprovalModel;
import com.abm.mainet.sfac.ui.model.FPOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
@Service
public class FPOAssessmentServiceImpl implements FPOAssessmentService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOAssessmentService#saveDetails(com.abm.mainet.
	 * sfac.dto.FpoAssessmentMasterDto,
	 * com.abm.mainet.sfac.ui.model.FPOAssessmentEntryModel)
	 */
	private static final Logger log = Logger.getLogger(FPOAssessmentServiceImpl.class);

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private FPOAssMasterRepository assRepo;

	@Autowired
	private FPOAssKeyParamRepository keyPramRepo;

	@Autowired
	private FPOAssSubParamRepository subPramRepo;

	@Autowired
	private FPOAssSubParamDetRepository subParamDetRepo;

	@Autowired
	private CommonService commonService;

	@Autowired
	private FPOAssMasterHistoryRepository assMastHistRepo;

	@Autowired
	private FPOAssKeyParamHistRepository assKeyHistRepo;

	@Autowired
	private FPOAssSubParamHistRepository assSubParamHistRepo;

	@Autowired
	private FPOAssSubParamDetHistRepository asssubParamDetHistRepo;

	@Autowired
	private FpoAssessmentMasterDao assessmentMasterDao;

	@Autowired
	private TbFinancialyearService financialyearService;
	

	@Autowired
	FPOMasterRepository fpoMasterRepository;

	@SuppressWarnings("deprecation")
	@Override
	public FpoAssessmentMasterDto saveDetails(FpoAssessmentMasterDto masDto,
			FPOAssessmentEntryModel fpoAssessmentEntryModel) {
		FPOAssessmentMasterEntity masterEntity = new FPOAssessmentMasterEntity();
		Long applicationId = null;
		try {
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.FAE,
					masDto.getOrgId());
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				masDto.setApplicationId(applicationId);
			// to generate assessment no
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(MainetConstants.Sfac.SFAC,
							MainetConstants.Sfac.TB_SFAC_FPO_ASS_MASTER, MainetConstants.Sfac.FPO_ASSESSMENT_NO,
							masDto.getOrgId(), MainetConstants.FlagC, masDto.getOrgId());
			final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.PADDING_THREE,
					Long.parseLong(sequence.toString()));
			String assessmentNo = MainetConstants.Sfac.FPO_ASSNO + paddingAppNo;
			masDto.setAssessmentNo(assessmentNo);

			log.info("saveDetails Started");
			masterEntity = mapDtoToEntity(masDto);
			log.info("saveDetails Ended");

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masDto.getApplicationId());
				applicationData.setOrgId(masDto.getOrgId());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				masDto.getApplicantDetailDto().setUserId(masDto.getCreatedBy());
				masDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				masDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				if (requestDto != null && requestDto.getMobileNo() != null) {
					masDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				Long cbboId = fpoMasterRepository.findOne(masDto.getFpoId()).getCbboId();
				masDto.getApplicantDetailDto().setExtIdentifier(cbboId);
				commonService.initiateWorkflowfreeService(applicationData, masDto.getApplicantDetailDto());
			}
		} catch (Exception e) {
			log.error("Error occured While saving assement details" + e);
			throw new FrameworkException("Error occured While saving assement details", e);
		}
		log.info("saveDetails ended");
		return masDto;
	}

	/**
	 * @param masDto
	 * @param sm
	 * @return
	 */
	private RequestDTO setApplicantRequestDto(FpoAssessmentMasterDto masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());
		requestDto.setOrgId(masDto.getOrgId());
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(masDto.getUserName());
		if (StringUtils.isNotEmpty(masDto.getEmail()))
			requestDto.setEmail(masDto.getEmail());
		if (StringUtils.isNotEmpty(masDto.getMobileNo()))
			requestDto.setMobileNo(masDto.getMobileNo());
		return requestDto;
	}

	/**
	 * @param masDto
	 * @return
	 */
	@Transactional
	private FPOAssessmentMasterEntity mapDtoToEntity(FpoAssessmentMasterDto masDto) {
		FPOAssessmentMasterEntity masEntity = new FPOAssessmentMasterEntity();
		FPOAssessmentMasterHistory histEntity = new FPOAssessmentMasterHistory();
		BeanUtils.copyProperties(masDto, masEntity);
		masEntity = assRepo.save(masEntity);

		for (FpoAssKeyParameterDto key : masDto.getAssKeyDtoList()) {
			FPOAssessmentKeyParamEntity keyEntity = new FPOAssessmentKeyParamEntity();
			BeanUtils.copyProperties(key, keyEntity);
			keyEntity.setFpoMasterEntity(masEntity);
			keyEntity = keyPramRepo.save(keyEntity);

			for (FpoAssSubParameterDto subDto : key.getFpoSubParamDtoList()) {
				FPOAssessmentSubParamEntity subEntity = new FPOAssessmentSubParamEntity();
				BeanUtils.copyProperties(subDto, subEntity);
				subEntity.setFpoKeyMasterEntity(keyEntity);
				subEntity = subPramRepo.save(subEntity);

				for (FpoAssSubParamDetailDto det : subDto.getFpoSubParamDetailDtoList()) {
					FPOAssessmentSubParamDetail subDetEntity = new FPOAssessmentSubParamDetail();
					BeanUtils.copyProperties(det, subDetEntity);
					subDetEntity.setFpoSubParamEntity(subEntity);
					subDetEntity = subParamDetRepo.save(subDetEntity);
				}
			}
		}
		// save records in history table
		BeanUtils.copyProperties(masEntity, histEntity);
		histEntity = assMastHistRepo.save(histEntity);
		List<FPOAssessmentKeyParamEntity> keyEntities = keyPramRepo.findByFpoMasterEntity(masEntity);
		for (FPOAssessmentKeyParamEntity key : keyEntities) {
			FPOAssessmentKeyParamHistory keyHistEntity = new FPOAssessmentKeyParamHistory();
			BeanUtils.copyProperties(key, keyHistEntity);
			keyHistEntity.setFpoMasterHistEntity(histEntity);
			keyHistEntity.setHistoryStatus(MainetConstants.FlagA);
			keyHistEntity = assKeyHistRepo.save(keyHistEntity);

			List<FPOAssessmentSubParamEntity> subEnties = subPramRepo.findByFpoKeyMasterEntity(key);
			for (FPOAssessmentSubParamEntity sub : subEnties) {
				FPOAssessmentSubParamHistory subHistEntity = new FPOAssessmentSubParamHistory();
				BeanUtils.copyProperties(sub, subHistEntity);
				subHistEntity.setFpoKeyParamHistEntity(keyHistEntity);
				subHistEntity.setHistoryStatus(MainetConstants.FlagA);
				subHistEntity = assSubParamHistRepo.save(subHistEntity);

				List<FPOAssessmentSubParamDetail> subParamEntities = subParamDetRepo.findByFpoSubParamEntity(sub);
				for (FPOAssessmentSubParamDetail det : subParamEntities) {
					FPOAssessmentSubParamDetailHistory subDetHistEntity = new FPOAssessmentSubParamDetailHistory();
					BeanUtils.copyProperties(det, subDetHistEntity);
					subDetHistEntity.setFpoSubParamHistEntity(subHistEntity);
					subDetHistEntity.setHistoryStatus(MainetConstants.FlagA);
					subDetHistEntity = asssubParamDetHistRepo.save(subDetHistEntity);
				}
			}
		}

		return masEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOAssessmentService#fetchAssessmentDetByAppId(
	 * java.lang.Long)
	 */
	@Override
	public FpoAssessmentMasterDto fetchAssessmentDetByAppId(Long applicationId) {
		FpoAssessmentMasterDto dto = new FpoAssessmentMasterDto();
		FPOAssessmentMasterEntity entity = new FPOAssessmentMasterEntity();
		try {
			entity = assRepo.findByApplicationId(applicationId);
			if (entity != null) {
				dto = setDetails(entity);
			}
		} catch (Exception e) {
			log.error("Error Occured while fetching assessment details in fetchAssessmentDetByAppId()" + e);
		}
		return dto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.FPOAssessmentService#
	 * updateApprovalStatusAndRemark(com.abm.mainet.sfac.dto.FpoAssessmentMasterDto,
	 * com.abm.mainet.sfac.ui.model.FPOAssApprovalModel)
	 */
	@Override
	@Transactional
	public void updateApprovalStatusAndRemark(FpoAssessmentMasterDto dto, FPOAssApprovalModel fpoAssApprovalModel) {
		if (dto.getAssStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			dto.setAssStatus(MainetConstants.FlagA);
		else if (dto.getAssStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			dto.setAssStatus(MainetConstants.FlagR);
		assRepo.updateApprovalStatusAndRemark(dto.getAssId(), dto.getAssStatus(), dto.getRemark());
		assMastHistRepo.updateApprovalStatusAndRemark(dto.getAssId(), dto.getAssStatus(), dto.getRemark(),
				MainetConstants.FlagU);
		List<FPOAssessmentSubParamDetail> detList = new ArrayList<>();
		List<FPOAssessmentSubParamDetailHistory> detHistList = new ArrayList<>();
		for (FpoAssKeyParameterDto keyDto : dto.getAssKeyDtoList()) {
			for (FpoAssSubParameterDto subDto : keyDto.getFpoSubParamDtoList()) {
				FPOAssessmentSubParamEntity subDetEntity = new FPOAssessmentSubParamEntity();
				BeanUtils.copyProperties(subDto, subDetEntity);
				//FPOAssessmentSubParamHistory histEntity = assSubParamHistRepo.findByAssSId(subDto.getAssSId());
				for (FpoAssSubParamDetailDto subDet : subDto.getFpoSubParamDetailDtoList()) {
					FPOAssessmentSubParamDetail ent = new FPOAssessmentSubParamDetail();
					BeanUtils.copyProperties(subDet, ent);
					ent.setFpoSubParamEntity(subDetEntity);
					detList.add(ent);
				/*	FPOAssessmentSubParamDetailHistory detHist = new FPOAssessmentSubParamDetailHistory();
					BeanUtils.copyProperties(ent, detHist);
					detHist.setHistoryStatus(MainetConstants.FlagU);
					detHist.setFpoSubParamHistEntity(histEntity);
					detHistList.add(detHist);*/
				}
			}
		}
		subParamDetRepo.save(detList);
		//asssubParamDetHistRepo.save(detHistList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOAssessmentService#findByFpoIdAndAssStatus(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public List<FpoAssessmentMasterDto> findByFpoIdAndAssStatus(Long fpoId, String assStatus) {
		List<FpoAssessmentMasterDto> dtoList = new ArrayList<>();
		List<FPOAssessmentMasterEntity> entity = assessmentMasterDao.findByFpoIdAndAssStatus(fpoId, assStatus);
		for (FPOAssessmentMasterEntity fpo : entity) {
			FpoAssessmentMasterDto dto = new FpoAssessmentMasterDto();
			BeanUtils.copyProperties(fpo, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.FPOAssessmentService#findAll()
	 */
	@Override
	public List<FpoAssessmentMasterDto> findAll() {
		List<FpoAssessmentMasterDto> dtoList = new ArrayList<>();
		try {
			List<FPOAssessmentMasterEntity> entity = assRepo.findAll();
			for (FPOAssessmentMasterEntity ass : entity) {
				FpoAssessmentMasterDto dto = new FpoAssessmentMasterDto();
				BeanUtils.copyProperties(ass, dto);
				if (ass.getAssStatus().equals(MainetConstants.FlagP)) {
					dto.setAssStatus(MainetConstants.TASK_STATUS_PENDING);
					dto.setStatus(MainetConstants.FlagP);
				} else if (ass.getAssStatus().equals(MainetConstants.FlagA)) {
					dto.setAssStatus(MainetConstants.TASK_STATUS_APPROVED);
					dto.setStatus(MainetConstants.FlagA);
				} else if (ass.getAssStatus().equals(MainetConstants.FlagR)) {
					dto.setAssStatus(MainetConstants.TASK_STATUS_REJECTED);
					dto.setStatus(MainetConstants.FlagR);
				}
				dtoList.add(dto);
			}
		} catch (Exception e) {
			log.error("Error occurred while fetching details in findAll" + e);
		}
		return dtoList;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.FPOAssessmentService#fetchAssessmentDetByAssId(java.lang.Long)
	 */
	@Override
	public FpoAssessmentMasterDto fetchAssessmentDetByAssId(Long assId) {
		FpoAssessmentMasterDto dto = new FpoAssessmentMasterDto();
		FPOAssessmentMasterEntity entity = new FPOAssessmentMasterEntity();
		try {
			entity = assRepo.findByAssId(assId);
			if (entity != null) {
				dto = setDetails(entity);
			}
		} catch (Exception e) {
			log.error("Error Occured while fetching assessment details in fetchAssessmentDetByAppId()" + e);
		}
		return dto;

	}
	
	private FpoAssessmentMasterDto setDetails(FPOAssessmentMasterEntity entity) {
		FpoAssessmentMasterDto dto = new FpoAssessmentMasterDto();
		List<FpoAssKeyParameterDto> keyDtoList = new ArrayList<>();

		Long totalMarks = 0l;
		BigDecimal totalScore = new BigDecimal(0);
		
		BeanUtils.copyProperties(entity, dto);
		if (entity.getFinYrId() != null) {
			TbFinancialyear financialYear = financialyearService.findYearById(entity.getFinYrId(), entity.getOrgId());
			try {
				dto.setAlcYearDesc(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
			} catch (Exception e) {
				log.error("Error occured while fetching financial year " + e);
			}
		}
		int keyCount = 0;
		List<FPOAssessmentKeyParamEntity> keyList = keyPramRepo.findByFpoMasterEntity(entity);
		for (FPOAssessmentKeyParamEntity key : keyList) {
			FpoAssKeyParameterDto keyDto = new FpoAssKeyParameterDto();
			BeanUtils.copyProperties(key, keyDto);
			keyDtoList.add(keyDto);

			int subCount = 0;
			List<FpoAssSubParameterDto> subDtoList = new ArrayList<>();
			List<FPOAssessmentSubParamEntity> subList = subPramRepo.findByFpoKeyMasterEntity(key);
			for (FPOAssessmentSubParamEntity sub : subList) {
				FpoAssSubParameterDto subDto = new FpoAssSubParameterDto();
				BeanUtils.copyProperties(sub, subDto);
				subDtoList.add(subDto);
				keyDtoList.get(keyCount).setFpoSubParamDtoList(subDtoList);

				List<FpoAssSubParamDetailDto> subDetDtoList = new ArrayList<>();
				List<FPOAssessmentSubParamDetail> subDetList = subParamDetRepo.findByFpoSubParamEntity(sub);
				for (FPOAssessmentSubParamDetail subDet : subDetList) {
					FpoAssSubParamDetailDto detDto = new FpoAssSubParamDetailDto();
					BeanUtils.copyProperties(subDet, detDto);

					if (null != subDet.getMarksAwarded())
						totalMarks = (totalMarks + subDet.getMarksAwarded());
					if (null != subDet.getScore())
						totalScore = totalScore.add(subDet.getScore());
					subDetDtoList.add(detDto);
					subDtoList.get(subCount).setFpoSubParamDetailDtoList(subDetDtoList);

				}
				subCount++;
			}
			keyCount++;
		}
		dto.setAssKeyDtoList(keyDtoList);
		if (null != totalMarks)
			dto.setTotalMarksAwarded(totalMarks);
		if (null != totalScore)
			dto.setTotalScore(totalScore);
		return dto;

	}

}
