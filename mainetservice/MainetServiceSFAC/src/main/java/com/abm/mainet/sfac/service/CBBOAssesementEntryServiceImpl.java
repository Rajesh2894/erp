/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.abm.mainet.sfac.dao.AssessmentMasterDao;
import com.abm.mainet.sfac.domain.AssessmentKeyParameterEntity;
import com.abm.mainet.sfac.domain.AssessmentKeyParameterHist;
import com.abm.mainet.sfac.domain.AssessmentMasterEntity;
import com.abm.mainet.sfac.domain.AssessmentMasterHist;
import com.abm.mainet.sfac.domain.AssessmentSubParameterDetail;
import com.abm.mainet.sfac.domain.AssessmentSubParameterDetailHistory;
import com.abm.mainet.sfac.domain.AssessmentSubParameterEntity;
import com.abm.mainet.sfac.domain.AssessmentSubParameterHist;
import com.abm.mainet.sfac.dto.AssessmentKeyParameterDto;
import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.dto.AssessmentSubParamDetailDto;
import com.abm.mainet.sfac.dto.AssessmentSubParameterDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.repository.AssesementMasterRepository;
import com.abm.mainet.sfac.repository.AssessmentKeyParameterHistRepository;
import com.abm.mainet.sfac.repository.AssessmentKeyParameterRepo;
import com.abm.mainet.sfac.repository.AssessmentMasterHistRepository;
import com.abm.mainet.sfac.repository.AssessmentSubParaDetHistRepository;
import com.abm.mainet.sfac.repository.AssessmentSubParameterDetailRepo;
import com.abm.mainet.sfac.repository.AssessmentSubParameterHistRepository;
import com.abm.mainet.sfac.repository.AssessmentSubParameterRepo;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.ui.model.AssessmentApprovalModel;
import com.abm.mainet.sfac.ui.model.CBBOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
@Service
public class CBBOAssesementEntryServiceImpl implements CBBOAssesementEntryService {

	private static final Logger log = Logger.getLogger(CBBOAssesementEntryServiceImpl.class);

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private AssesementMasterRepository assRepo;

	@Autowired
	private AssessmentKeyParameterRepo keyPramRepo;

	@Autowired
	private AssessmentSubParameterRepo subPramRepo;

	@Autowired
	private AssessmentSubParameterDetailRepo subParamDetRepo;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private AssessmentMasterHistRepository assMastHistRepo;
	
	@Autowired
	private AssessmentKeyParameterHistRepository assKeyHistRepo;
	
	@Autowired
	private AssessmentSubParameterHistRepository assSubParamHistRepo;
	
	@Autowired
	private AssessmentSubParaDetHistRepository asssubParamDetHistRepo;

	@Autowired
	private AssessmentMasterDao assessmentMasterDao;
	
	@Autowired
	private CBBOMasterRepository cbboRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOAssesementEntryService#saveDetails(java.util.
	 * List, com.abm.mainet.sfac.ui.model.CBBOAssesementEntryModel)
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public AssessmentMasterDto saveDetails(AssessmentMasterDto masDto,
			CBBOAssessmentEntryModel cbboAssesementEntryModel) {
		AssessmentMasterEntity masterEntity = new AssessmentMasterEntity();
		Long applicationId = null;
		try {
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.CAE,
					masDto.getOrgId());
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				masDto.setApplicationId(applicationId);
			// to generate assessment no
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(MainetConstants.Sfac.SFAC,
							MainetConstants.Sfac.TB_SFAC_ASSESSMENT_MASTER, MainetConstants.Sfac.ASSESSMENT_NO,
							masDto.getOrgId(), MainetConstants.FlagC, masDto.getOrgId());
			final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.PADDING_THREE,
					Long.parseLong(sequence.toString()));
			String assessmentNo = MainetConstants.Sfac.ASSNO + paddingAppNo;
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
				masDto.getApplicantDetailDto().setExtIdentifier(masDto.getCbboId());
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
	 * @return
	 */
	@Transactional
	private AssessmentMasterEntity mapDtoToEntity(AssessmentMasterDto masDto) {
		AssessmentMasterEntity masEntity = new AssessmentMasterEntity();
		AssessmentMasterHist histEntity = new AssessmentMasterHist();
		BeanUtils.copyProperties(masDto, masEntity);
		masEntity = assRepo.save(masEntity);

		for (AssessmentKeyParameterDto key : masDto.getAssementKeyParamDtoList()) {
			AssessmentKeyParameterEntity keyEntity = new AssessmentKeyParameterEntity();
			BeanUtils.copyProperties(key, keyEntity);
			keyEntity.setMasterEntity(masEntity);
			keyEntity = keyPramRepo.save(keyEntity);

			for (AssessmentSubParameterDto subDto : key.getAssSubParamDtoList()) {
				AssessmentSubParameterEntity subEntity = new AssessmentSubParameterEntity();
				BeanUtils.copyProperties(subDto, subEntity);
				subEntity.setKeyMasterEntity(keyEntity);
				subEntity = subPramRepo.save(subEntity);

				for (AssessmentSubParamDetailDto det : subDto.getAssSubParamDetailDtoList()) {
					AssessmentSubParameterDetail subDetEntity = new AssessmentSubParameterDetail();
					BeanUtils.copyProperties(det, subDetEntity);
					subDetEntity.setAssSubParamEntity(subEntity);
					subDetEntity = subParamDetRepo.save(subDetEntity);
				}
			}
		}
		// save records in history table
		BeanUtils.copyProperties(masEntity, histEntity);
		histEntity = assMastHistRepo.save(histEntity);
		 List<AssessmentKeyParameterEntity> keyEntities = keyPramRepo.findByMasterEntity(masEntity);
		for (AssessmentKeyParameterEntity key :keyEntities) {
			AssessmentKeyParameterHist keyHistEntity = new AssessmentKeyParameterHist();
			BeanUtils.copyProperties(key, keyHistEntity);
			keyHistEntity.setMasterHistEntity(histEntity);
			keyHistEntity.setHistoryStatus(MainetConstants.FlagA);
			keyHistEntity = assKeyHistRepo.save(keyHistEntity);

		List<AssessmentSubParameterEntity> subEnties = subPramRepo.findByKeyMasterEntity(key);
			for (AssessmentSubParameterEntity sub : subEnties) {
				AssessmentSubParameterHist subHistEntity = new AssessmentSubParameterHist();
				BeanUtils.copyProperties(sub, subHistEntity);
				subHistEntity.setMasterKeyParamHistEntity(keyHistEntity);
				subHistEntity.setHistoryStatus(MainetConstants.FlagA);
				subHistEntity = assSubParamHistRepo.save(subHistEntity);
				
				List<AssessmentSubParameterDetail> subParamEntities = subParamDetRepo.findByAssSubParamEntity(sub);
				for (AssessmentSubParameterDetail det : subParamEntities) {
					AssessmentSubParameterDetailHistory subDetHistEntity = new AssessmentSubParameterDetailHistory();
					BeanUtils.copyProperties(det, subDetHistEntity);
					subDetHistEntity.setMasterSubParamHistEntity(subHistEntity);
					subDetHistEntity.setHistoryStatus(MainetConstants.FlagA);
					subDetHistEntity = asssubParamDetHistRepo.save(subDetHistEntity);
				}
			}
		}
		
		return masEntity;
	}

	/**
	 * @param masDto
	 * @param sm
	 * @return
	 */
	private RequestDTO setApplicantRequestDto(AssessmentMasterDto masDto, ServiceMaster sm) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOAssesementEntryService#saveAssMastDetail(com.
	 * abm.mainet.sfac.dto.AssessmentMasterDto,
	 * com.abm.mainet.sfac.ui.model.CBBOAssesementEntryModel)
	 */
	/*
	 * @Override public AssessmentMasterDto saveAssMastDetail(AssessmentMasterDto
	 * dto, CBBOAssessmentEntryModel cbboAssesementEntryModel) {
	 * AssessmentMasterEntity entity = new AssessmentMasterEntity(); try {
	 * log.info("saveAssMastDetail started"); final Long sequence =
	 * ApplicationContextProvider.getApplicationContext()
	 * .getBean(SeqGenFunctionUtility.class).generateSequenceNo(MainetConstants.Sfac
	 * .SFAC, MainetConstants.Sfac.TB_SFAC_ASSESSMENT_MASTER,
	 * MainetConstants.Sfac.ASSESSMENT_NO, dto.getOrgId(), MainetConstants.FlagC,
	 * dto.getOrgId()); final String paddingAppNo =
	 * String.format(MainetConstants.CommonMasterUi.PADDING_THREE,
	 * Long.parseLong(sequence.toString())); String assessmentNo =
	 * MainetConstants.Sfac.ASSNO + paddingAppNo;
	 * entity.setAssessmentNo(assessmentNo); dto.setAssessmentNo(assessmentNo);
	 * BeanUtils.copyProperties(dto, entity); assRepo.save(entity); } catch
	 * (Exception e) { log.
	 * error("Error occured while saving assement master details in saveAssMastDetail service"
	 * + e); } log.info("saveAssMastDetail ended"); return dto; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOAssesementEntryService#updateAssementStatus(
	 * java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public void updateAssementStatus(Long assId, String flag) {
		assRepo.updateAssementStatus(assId, flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOAssesementEntryService#getCbboAssNo(java.lang
	 * .String)
	 */
	@Override
	public String getCbboAssNo(String assessmentNo, Long orgId) {
		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo(MainetConstants.Sfac.SFAC, MainetConstants.Sfac.TB_SFAC_ASSESSMENT_MASTER,
						MainetConstants.Sfac.ASSESSMENT_NO, orgId, MainetConstants.FlagC, orgId);
		final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.PADDING_TWO,
				Long.parseLong(sequence.toString()));
		String cbboAssNo = assessmentNo + "-" + "CBAS" + paddingAppNo;
		return cbboAssNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOAssesementEntryService#findAll()
	 */
	@Override
	public List<AssessmentMasterDto> findAll() {
		List<AssessmentMasterDto> dtoList = new ArrayList<>();
		try {
			List<AssessmentMasterEntity> entity = assRepo.findAll();
			for (AssessmentMasterEntity ass : entity) {
				AssessmentMasterDto dto = new AssessmentMasterDto();
				BeanUtils.copyProperties(ass, dto);
				//to get cbbo name by id
				if (ass.getCbboId() != null) {
					String name = cbboRepo.fetchNameById(ass.getCbboId());
					dto.setCbboName(name);
				}
				 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOAssesementEntryService#
	 * fetchAssessmentDetByAppId(java.lang.Long)
	 */
	@Override
	public AssessmentMasterDto fetchAssessmentDetByAppId(Long applcationId) {
		AssessmentMasterDto dto = new AssessmentMasterDto();
		AssessmentMasterEntity entity = new AssessmentMasterEntity();
		List<AssessmentKeyParameterDto> keyDtoList = new ArrayList<>();
		
	BigDecimal totaloverAllScore = new BigDecimal(0);
	Long totalRegFullfilling =0l;
	BigDecimal totalScore = new BigDecimal(0);
		try {
			entity = assRepo.fetchAssessmentDetByAppId(applcationId);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);
				if (entity.getCbboId() != null) {
					CBBOMasterDto cbboDto = cbboMasterService.findById(entity.getCbboId());
					dto.setFpoAllcTarget(cbboDto.getFpoAllocationTarget());
					dto.setFpoRegCount(cbboDto.getRegCount());
					dto.setFpoRegPendCount(cbboDto.getRegPendCount());
					dto.setCbboUniqueId(cbboDto.getCbboUniqueId());
					dto.setCbboName(cbboDto.getCbboName());
					dto.setIaName(cbboDto.getIAName());
				}
				dto.setAssessmentDate(Utility.dateToString(entity.getCreatedDate()));
				if (entity.getFinYrId() != null) {
					TbFinancialyear financialYear = financialyearService.findYearById(entity.getFinYrId(),
							entity.getOrgId());
					try {
						dto.setAlcYearDesc(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
					} catch (Exception e) {
						log.error("Error occured while fetching financial year " + e);
					}
				}
				int keyCount =0;
				List<AssessmentKeyParameterEntity> keyList = keyPramRepo.findByAssId(entity.getAssId());
				for (AssessmentKeyParameterEntity key : keyList) {
					AssessmentKeyParameterDto keyDto = new AssessmentKeyParameterDto();
					BeanUtils.copyProperties(key, keyDto);
					keyDtoList.add(keyDto);
				
					int subCount = 0;
					List<AssessmentSubParameterDto> subDtoList = new ArrayList<>();
					List<AssessmentSubParameterEntity> subList = subPramRepo.findByAsskId(key.getAssKId());
					for (AssessmentSubParameterEntity sub : subList) {
						AssessmentSubParameterDto subDto = new AssessmentSubParameterDto();
						BeanUtils.copyProperties(sub, subDto);
						subDtoList.add(subDto);
						keyDtoList.get(keyCount).setAssSubParamDtoList(subDtoList);
						
						List<AssessmentSubParamDetailDto> subDetDtoList = new ArrayList<>();
						List<AssessmentSubParameterDetail> subDetList = subParamDetRepo.findByAssSId(sub.getAssSId());
						for (AssessmentSubParameterDetail subDet : subDetList) {
							AssessmentSubParamDetailDto detDto = new AssessmentSubParamDetailDto();
							BeanUtils.copyProperties(subDet,detDto);
							if (null != subDet.getOverallScore())
								totaloverAllScore = totaloverAllScore.add(subDet.getOverallScore());
							if (null != subDet.getRegiFpoCriteria())
								totalRegFullfilling = (totalRegFullfilling + subDet.getRegiFpoCriteria());
							if (null != subDet.getScore())
								totalScore = totalScore.add(subDet.getScore());
							subDetDtoList.add(detDto);
							subDtoList.get(subCount).setAssSubParamDetailDtoList(subDetDtoList);
							
						}
						subCount++;
					}
					keyCount++;
				}
				dto.setAssementKeyParamDtoList(keyDtoList);
				if (null != totaloverAllScore)
					dto.setTotalOverallScore(totaloverAllScore);
				if (null != totalRegFullfilling)
					dto.setTotalFpoFullFilling(totalRegFullfilling);
				if (null != totalScore)
					dto.setTotalScore(totalScore);
			}

		} catch (Exception e) {
			log.error("Error Occured while fetching assessment details in fetchAssessmentDetByAppId()" + e);
		}

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOAssesementEntryService#
	 * updateApprovalStatusAndRemark(com.abm.mainet.sfac.dto.AssessmentMasterDto,
	 * com.abm.mainet.sfac.ui.model.AssessmentApprovalModel)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateApprovalStatusAndRemark(AssessmentMasterDto dto,
			AssessmentApprovalModel assessmentApprovalModel) {
		if (dto.getAssStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			dto.setAssStatus(MainetConstants.FlagA);
		else if (dto.getAssStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			dto.setAssStatus(MainetConstants.FlagR);
		assRepo.updateApprovalStatusAndRemark(dto.getAssId(), dto.getAssStatus(), dto.getRemark());
		assMastHistRepo.updateApprovalStatusAndRemark(dto.getAssId(), dto.getAssStatus(), dto.getRemark(),MainetConstants.FlagU);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOAssesementEntryService#
	 * fetchAssessmentDetByAssId(java.lang.Long)
	 */
	@Override
	public AssessmentMasterDto fetchAssessmentDetByAssId(Long assId) {
		AssessmentMasterDto dto = new AssessmentMasterDto();
		AssessmentMasterEntity entity = new AssessmentMasterEntity();
		List<AssessmentKeyParameterDto> keyDtoList = new ArrayList<>();
		BigDecimal totaloverAllScore = new BigDecimal(0);
		Long totalRegFullfilling =0l;
		BigDecimal totalScore = new BigDecimal(0);
		
		try {
			log.info("fetchAssessmentDetByAssId started");
			entity = assRepo.fetchAssessmentDetByAssId(assId);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);
				if (entity.getCbboId() != null) {
					CBBOMasterDto cbboDto = cbboMasterService.findById(entity.getCbboId());
					dto.setFpoAllcTarget(cbboDto.getFpoAllocationTarget());
					dto.setFpoRegCount(cbboDto.getRegCount());
					dto.setFpoRegPendCount(cbboDto.getRegPendCount());
					dto.setCbboUniqueId(cbboDto.getCbboUniqueId());
					dto.setCbboName(cbboDto.getCbboName());
					dto.setIaName(cbboDto.getIAName());
				}
				dto.setAssessmentDate(Utility.dateToString(entity.getCreatedDate()));
				if (entity.getFinYrId() != null) {
					TbFinancialyear financialYear = financialyearService.findYearById(entity.getFinYrId(),
							entity.getOrgId());
					try {
						dto.setAlcYearDesc(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
					} catch (Exception e) {
						log.error("Error occured while fetching financial year " + e);
					}
				}
				int keyCount =0;
				List<AssessmentKeyParameterEntity> keyList = keyPramRepo.findByAssId(entity.getAssId());
				for (AssessmentKeyParameterEntity key : keyList) {
					AssessmentKeyParameterDto keyDto = new AssessmentKeyParameterDto();
					BeanUtils.copyProperties(key, keyDto);
					keyDtoList.add(keyDto);

					int subCount = 0;
					List<AssessmentSubParameterDto> subDtoList = new ArrayList<>();
					List<AssessmentSubParameterEntity> subList = subPramRepo.findByAsskId(key.getAssKId());
					for (AssessmentSubParameterEntity sub : subList) {
						AssessmentSubParameterDto subDto = new AssessmentSubParameterDto();
						BeanUtils.copyProperties(sub, subDto);
						subDtoList.add(subDto);
						keyDtoList.get(keyCount).setAssSubParamDtoList(subDtoList);
						
						List<AssessmentSubParamDetailDto> subDetDtoList = new ArrayList<>();
						List<AssessmentSubParameterDetail> subDetList = subParamDetRepo.findByAssSId(sub.getAssSId());
						for (AssessmentSubParameterDetail subDet : subDetList) {
							AssessmentSubParamDetailDto detDto = new AssessmentSubParamDetailDto();
							BeanUtils.copyProperties(subDet,detDto);
							if (null != subDet.getOverallScore())
								totaloverAllScore = totaloverAllScore.add(subDet.getOverallScore());
							if (null != subDet.getRegiFpoCriteria())
								totalRegFullfilling = (totalRegFullfilling + subDet.getRegiFpoCriteria());
							if (null != subDet.getScore())
								totalScore = totalScore.add(subDet.getScore());
							subDetDtoList.add(detDto);
							subDtoList.get(subCount).setAssSubParamDetailDtoList(subDetDtoList);
						}
						subCount++;
					}
					keyCount++;
				}
				dto.setAssementKeyParamDtoList(keyDtoList);
				if (null != totaloverAllScore)
					dto.setTotalOverallScore(totaloverAllScore);
				if (null != totalRegFullfilling)
					dto.setTotalFpoFullFilling(totalRegFullfilling);
				if (null != totalScore)
					dto.setTotalScore(totalScore);
			}

		} catch (Exception e) {
			log.error("Error Occured while fetching assessment details in fetchAssessmentDetByAssId()" + e);
		}
		log.info("fetchAssessmentDetByAssId ended");
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOAssesementEntryService#findByIds(java.lang.
	 * Long, java.lang.String, java.util.Date)
	 */
	@Override
	public List<AssessmentMasterDto> findByIds(Long cbboId, String assStatus, Date assDate) {
		List<AssessmentMasterDto> assMasDtoList = new ArrayList<>();
		List<AssessmentMasterEntity> entityList = assessmentMasterDao.findDetByIds(cbboId, assStatus, assDate);
		for (AssessmentMasterEntity ass : entityList) {
			AssessmentMasterDto dto = new AssessmentMasterDto();
			BeanUtils.copyProperties(ass, dto);
			assMasDtoList.add(dto);
		}
		return assMasDtoList;
	}

}
