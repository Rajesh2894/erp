/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.dao.FPOMasterDao;
import com.abm.mainet.sfac.domain.BlockAllocationDetailEntity;
import com.abm.mainet.sfac.domain.FPOAdministrativeDetHistory;
import com.abm.mainet.sfac.domain.FPOAdministrativeDetailEntity;
import com.abm.mainet.sfac.domain.FPOBankDetailEntity;
import com.abm.mainet.sfac.domain.FPOBankDetailHistory;
import com.abm.mainet.sfac.domain.FPOMasterDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterDetailHistory;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterHistory;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.FPOAdministrativeDto;
import com.abm.mainet.sfac.dto.FPOBankDetailDto;
import com.abm.mainet.sfac.dto.FPOMasterDetailDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.repository.AllocationOfBlocksRepository;
import com.abm.mainet.sfac.repository.FPOAdministrativeDetailRpository;
import com.abm.mainet.sfac.repository.FPOBankDetailRepository;
import com.abm.mainet.sfac.repository.FPOMasterDetailRepository;
import com.abm.mainet.sfac.repository.FPOMasterHistoryRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.ui.model.FpoMasterApprovalModel;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author pooja.maske
 *
 */
@Service
public class FPOMasterServiceImpl implements FPOMasterService {
	private static final Logger logger = Logger.getLogger(FPOMasterServiceImpl.class);

	@Autowired
	FPOMasterRepository fpoMasterRepository;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	private BankMasterService bankMasterService;

	@Autowired
	private FPOMasterDao fpoMasterDao;

	@Autowired
	private FPOAdministrativeDetailRpository adminRepo;

	@Autowired
	private FPOMasterDetailRepository fpoDetRepo;

	@Autowired
	private FPOBankDetailRepository bankRepo;

	@Autowired
	private FPOMasterHistoryRepository fpoMasHistRepo;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private AllocationOfBlocksRepository allocationOfBlocksRepo;

	@Override
	@Transactional
	public FPOMasterDto saveAndUpdateApplication(FPOMasterDto fpoMasterDto) {
		try {
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			logger.info("saveAndUpdateApplication started");
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.FPO,
					org.getOrgid());
			Long applicationId = null;
			RequestDTO requestDto = setApplicantRequestDto(fpoMasterDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				fpoMasterDto.setApplicationId(applicationId);
			FPOMasterEntity masEntity = mapDtoToEntity(fpoMasterDto);
			masEntity = fpoMasterRepository.save(masEntity);
			saveHistoryData(masEntity);
			initiateWorkflow(fpoMasterDto, sm, requestDto);
		} catch (Exception e) {
			logger.error("Error occured while saving fpo master details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return fpoMasterDto;
	}

	/**
	 * @param fpoMasterDto
	 * @param sm
	 * @return
	 */
	private RequestDTO setApplicantRequestDto(FPOMasterDto masDto, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());
		requestDto.setOrgId(masDto.getOrgId());
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(masDto.getOrgId());
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		requestDto.setEmail(masDto.getEmail());
		requestDto.setMobileNo(masDto.getMobileNo());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;

	}

	private FPOMasterEntity mapDtoToEntity(FPOMasterDto fpoMasterDto) {
		FPOMasterEntity masEntity = new FPOMasterEntity();
		List<FPOMasterDetailEntity> detailsList = new ArrayList<>();
		List<FPOBankDetailEntity> banKDetailtList = new ArrayList<>();
		List<FPOAdministrativeDetailEntity> administrativeList = new ArrayList<>();
		if (StringUtils.isEmpty(fpoMasterDto.getFpoRegNo())) {
			String regNo = getNewGeneratedRegistrationNo(fpoMasterDto);
			if (regNo != null)
				fpoMasterDto.setFpoRegNo(regNo);
		}
		fpoMasterDto.setFpoTanNo(fpoMasterDto.getFpoTanNo().toUpperCase());
		fpoMasterDto.setFpoPanNo(fpoMasterDto.getFpoPanNo().toUpperCase());
		fpoMasterDto.setGstin(fpoMasterDto.getGstin().toUpperCase());
		fpoMasterDto.setCompanyRegNo(fpoMasterDto.getCompanyRegNo().toUpperCase());
		BeanUtils.copyProperties(fpoMasterDto, masEntity);
		fpoMasterDto.getFpoMasterDetailDto().forEach(dto -> {
			FPOMasterDetailEntity entity = new FPOMasterDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masEntity);
			detailsList.add(entity);
		});
		fpoMasterDto.getFpoBankDetailDto().forEach(bankDto -> {
			FPOBankDetailEntity entity = new FPOBankDetailEntity();
			BeanUtils.copyProperties(bankDto, entity);
			try {
				logger.info("bank id " + bankDto.getIfscCode() + bankDto.getBankName());
				if (bankDto.getIfscCode() != null) {
					String ifscCode = bankMasterService.getIfscCodeById(Long.valueOf(bankDto.getIfscCode()));
					logger.info("ifsc code" + ifscCode);
					if (StringUtils.isNotEmpty(ifscCode))
						entity.setIfscCode(ifscCode);
				}
				entity.setMasterEntity(masEntity);
				entity.setBankName(Long.valueOf(bankDto.getIfscCode()));
			} catch (Exception e) {
				logger.error("Error ocurred while fetching ifsc code and bank id" + e);
			}
			banKDetailtList.add(entity);
		});

		fpoMasterDto.getFpoAdministrativeDto().forEach(admDto -> {
			FPOAdministrativeDetailEntity entity = new FPOAdministrativeDetailEntity();
			BeanUtils.copyProperties(admDto, entity);
			entity.setMasterEntity(masEntity);
			administrativeList.add(entity);
		});
		if (CollectionUtils.isNotEmpty(fpoMasterDto.getFpoMasterDetailDto())
				&& StringUtils.isNotEmpty(fpoMasterDto.getFpoMasterDetailDto().get(0).getCropName()))
			masEntity.setFpoMasterDetail(detailsList);
		if (CollectionUtils.isNotEmpty(fpoMasterDto.getFpoMasterDetailDto())
				&& StringUtils.isNotEmpty(fpoMasterDto.getFpoBankDetailDto().get(0).getIfscCode()))
			masEntity.setFpoBankMasterDetail(banKDetailtList);
		if (CollectionUtils.isNotEmpty(fpoMasterDto.getFpoAdministrativeDto())
				&& StringUtils.isNotEmpty(fpoMasterDto.getFpoAdministrativeDto().get(0).getName()))
			masEntity.setFpoAdministrativeDetail(administrativeList);
		return masEntity;
	}

	/**
	 * @param fpoMasterDto
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getNewGeneratedRegistrationNo(FPOMasterDto fpoMasterDto) {
		String registrationNo = null;
		Long deptId = departmentService.getDepartmentIdByDeptCode("NPMA", PrefixConstants.STATUS_ACTIVE_PREFIX);

		try {
			logger.info("new registration no generation started");
			String seqVar = "";
			SimpleDateFormat simpleformat = new SimpleDateFormat("yy");
			String strYear = simpleformat.format(fpoMasterDto.getDateIncorporation());
			String typeOfFPO = "";
			String typeOfPromotionAgency = "";
			String regNo = null;
			String stateCode = "";

			LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(fpoMasterDto.getSdb1(), fpoMasterDto.getOrgId());
			stateCode = getPrefixOtherValue(lookUp.getLookUpId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			LookUp regAct = CommonMasterUtility.getNonHierarchicalLookUpObject(fpoMasterDto.getRegAct());
			if (regAct != null)
				typeOfFPO = regAct.getOtherField();

			if (fpoMasterDto.getTypeofPromAgen() != null) {
				LookUp typeofAgen = CommonMasterUtility
						.getNonHierarchicalLookUpObject(fpoMasterDto.getTypeofPromAgen());
				if (typeofAgen != null)
					typeOfPromotionAgency = typeofAgen.getLookUpCode();
			}
			Long sequence = 0L;
			sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
					.getNumericSeqNo(seqVar, MainetConstants.Sfac.TB_FPO_MASTER, MainetConstants.Sfac.FPO_REG_NO,
							fpoMasterDto.getOrgId(), "CNT", deptId.toString(), 1l, 999999l);
			regNo = String.format(MainetConstants.CommonMasterUi.PADDING_SIX, Integer.parseInt(sequence.toString()));
			registrationNo = strYear + typeOfFPO + typeOfPromotionAgency + stateCode + regNo;
			logger.info("new registration no generation generated" + registrationNo);
		} catch (Exception e) {
			logger.error("Error occured while creating new registration Number" + e);
		}
		return registrationNo;
	}

	/**
	 * @param lookUpId
	 * @param orgId
	 * @return
	 */
	@Override
	public String getPrefixOtherValue(Long lookUpId, Long orgId) {
		Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.NPMA);
		return fpoMasterRepository.getPrefixOtherValue(lookUpId, org.getOrgid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#checkSpecialCateExist(java.lang.
	 * Long)
	 */
	@Override
	public boolean checkSpecialCateExist(Long sdb3, Long allocationCat) {
		Boolean result = false;
		try {
			result = fpoMasterRepository.getFpoDetailsBySdbId(sdb3, allocationCat);
			/*
			 * if (entity != null && entity.getAllocationCategory() != null) { List<LookUp>
			 * lookup = CommonMasterUtility.lookUpListByPrefix("ALC", orgId);
			 * Optional<LookUp> findAny = lookup.stream() .filter(p -> p.getLookUpId() ==
			 * entity.getAllocationCategory() && p.getLookUpCode().equals("SPC"))
			 * .findAny(); if (findAny.isPresent()) return true; }
			 */
		} catch (Exception e) {
			logger.error(
					"Error occured while checking special category present or not in  checkSpecialCateExist()" + e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.FPOMasterService#findAllFpo()
	 */
	@Override
	public List<FPOMasterDto> findAllFpo() {

		List<FPOMasterDto> fPOMasterDtoList = new ArrayList<FPOMasterDto>();
		try {
			logger.info("findAllFpo Started");
			List<Object[]> detailsList = fpoMasterRepository.findAllFpo();
			for (Object[] obj : detailsList) {
				FPOMasterDto fpoMasterDto = new FPOMasterDto();
				if ((Long) obj[0] != null)
					fpoMasterDto.setFpoId((Long) obj[0]);
				if (StringUtils.isNotEmpty((String) obj[1]))
					fpoMasterDto.setFpoName((String) obj[1]);
				if (StringUtils.isNotEmpty((String) obj[2]))
					fpoMasterDto.setFpoRegNo((String) obj[2]);
				fPOMasterDtoList.add(fpoMasterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Fpo details" + e);
		}
		logger.info("findAllFpo Ended");
		return fPOMasterDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#getDetailById(java.lang.Long)
	 */

	@Override
	public FPOMasterDto getDetailById(Long fpoId) {
		FPOMasterDto dto = new FPOMasterDto();
		List<FPOMasterDetailDto> fpoMasterDetailDtoList = new ArrayList<>();
		List<FPOAdministrativeDto> fpoAdministrativeDtoList = new ArrayList<>();
		List<FPOBankDetailDto> fpoBankDetailDtoList = new ArrayList<>();

		FPOMasterEntity entity = fpoMasterRepository.getDetailById(fpoId);
		BeanUtils.copyProperties(entity, dto);
		/*
		 * LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("NAP", "FMA");
		 * if (entity.getApprovedByIa() != null) { LookUp IaLookUp =
		 * CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getApprovedByIa());
		 * dto.setAppByIaDesc(IaLookUp.getDescLangFirst()); } else {
		 * dto.setAppByIaDesc(lookUp.getDescLangFirst()); }
		 * 
		 * if (entity.getApprovedByCbbo() != null) { LookUp cbboLookUp =
		 * CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getApprovedByCbbo()
		 * ); dto.setAppByCbboDesc(cbboLookUp.getDescLangFirst()); } else {
		 * dto.setAppByCbboDesc(lookUp.getDescLangFirst()); }
		 */

		/*
		 * if (entity.getApprovedByFpo() != null) { LookUp fpoLookUp =
		 * CommonMasterUtility
		 * .getNonHierarchicalLookUpObject(entity.getApprovedByFpo());
		 * dto.setAppByFpoDesc(fpoLookUp.getDescLangFirst()); } else {
		 * dto.setAppByFpoDesc(lookUp.getDescLangFirst()); }
		 */

		List<FPOMasterDetailEntity> detEntity = fpoDetRepo.getDetails(entity);
		detEntity.forEach(detDto -> {
			FPOMasterDetailDto detailDto = new FPOMasterDetailDto();
			BeanUtils.copyProperties(detDto, detailDto);
			fpoMasterDetailDtoList.add(detailDto);
		});
		List<FPOAdministrativeDetailEntity> adminEntity = adminRepo.getAdminDetails(entity);
		adminEntity.forEach(admin -> {
			FPOAdministrativeDto adminDto = new FPOAdministrativeDto();
			BeanUtils.copyProperties(admin, adminDto);
			fpoAdministrativeDtoList.add(adminDto);
		});
		List<FPOBankDetailEntity> bankDet = bankRepo.getBankDetails(entity);
		bankDet.forEach(bank -> {
			FPOBankDetailDto bankDto = new FPOBankDetailDto();
			BeanUtils.copyProperties(bank, bankDto);
			String bankName = bankMasterService.getBankById(bank.getBankName());
			bankDto.setBankName(bankName);
			bankDto.setIfscCode(bank.getBkId().toString());
			bankDto.setBankMasterList(bankMasterService.getBankListByName(bankName));
			bankDto.setIfscCodeId(bank.getIfscCode());
			bankDto.setSelectedBankId(bank.getBkId());
			fpoBankDetailDtoList.add(bankDto);
		});

		dto.setFpoMasterDetailDto(fpoMasterDetailDtoList);
		dto.setFpoAdministrativeDto(fpoAdministrativeDtoList);
		dto.setFpoBankDetailDto(fpoBankDetailDtoList);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#findFpoByMasId(java.lang.Long)
	 */

	@Override
	public List<FPOMasterDto> findFpoByMasId(Long masId, String orgShortNm, String uniqueId) {
		List<FPOMasterEntity> detailsList = new ArrayList<>();
		List<FPOMasterDto> fPOMasterDtoList = new ArrayList<FPOMasterDto>();
		try {
			logger.info("findFpoByMasId Started");
			detailsList = fpoMasterDao.findFpoByMasId(masId, orgShortNm, uniqueId);

			for (FPOMasterEntity fpoMasterEntity : detailsList) {
				FPOMasterDto fpoMasterDto = new FPOMasterDto();
				BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
				// fpoMasterDto.setIaName(iaMasterRepository.fetchNameById(fpoMasterEntity.getIaId()));
				if (fpoMasterEntity.getDateIncorporation() != null) {
					fpoMasterDto
							.setFpoRegDate(Utility.getFinancialYearFromDate(fpoMasterEntity.getDateIncorporation()));
				}
				if (fpoMasterEntity.getAppStatus() != null
						&& fpoMasterEntity.getAppStatus().equals(MainetConstants.FlagA)) {
					fpoMasterDto.setAppByIaDesc(MainetConstants.TASK_STATUS_APPROVED);
				} else if (fpoMasterEntity.getAppStatus() != null
						&& fpoMasterEntity.getAppStatus().equals(MainetConstants.FlagR)) {
					fpoMasterDto.setAppByIaDesc(MainetConstants.TASK_STATUS_REJECTED);
				} else if (fpoMasterEntity.getAppStatus() != null
						&& fpoMasterEntity.getAppStatus().equals(MainetConstants.FlagP)) {
					fpoMasterDto.setAppByIaDesc(MainetConstants.TASK_STATUS_PENDING);
				} else {
					fpoMasterDto.setAppByIaDesc(MainetConstants.CommonConstants.NA);
					fpoMasterDto.setAppStatus(MainetConstants.CommonConstants.NA);
				}
				if (StringUtils.isNotEmpty(fpoMasterDto.getCompanyRegNo()))
				fpoMasterDto.setCompanyRegNo(fpoMasterDto.getCompanyRegNo().toUpperCase());

				if (StringUtils.isNotEmpty(fpoMasterEntity.getActiveInactiveStatus()) && fpoMasterEntity.getActiveInactiveStatus().equals(MainetConstants.FlagI)) 
					fpoMasterDto.setSummaryStatus(MainetConstants.Common_Constant.INACTIVE);
				else if(StringUtils.isNotEmpty(fpoMasterEntity.getActiveInactiveStatus()) && fpoMasterEntity.getActiveInactiveStatus().equals(MainetConstants.FlagA)) {
					fpoMasterDto.setSummaryStatus(MainetConstants.Common_Constant.ACTIVE);
				}else {
					fpoMasterDto.setSummaryStatus(MainetConstants.CommonConstants.NA);
				}
				
				if (fpoMasterEntity.getApproved() != null
						&& fpoMasterEntity.getApproved().equals(MainetConstants.FlagA))
					fpoMasterDto.setAppByCbboDesc(MainetConstants.TASK_STATUS_APPROVED);
				else
					fpoMasterDto.setAppByCbboDesc(MainetConstants.CommonConstants.NA);

				/*
				 * if (fpoMasterEntity.getApprovedByFpo() != null) { LookUp fpoLookUp =
				 * CommonMasterUtility
				 * .getNonHierarchicalLookUpObject(fpoMasterEntity.getApprovedByFpo());
				 * fpoMasterDto.setAppByFpoDesc(fpoLookUp.getDescLangFirst()); } else {
				 * fpoMasterDto.setAppByFpoDesc(lookUp.getDescLangFirst()); }
				 */
				fPOMasterDtoList.add(fpoMasterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching fpo details" + e);
		}
		logger.info("findFpoByMasId Ended");
		return fPOMasterDtoList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#getfpoByIdAndRegNo(java.lang.
	 * Long, java.lang.String)
	 */

	@Override
	public List<FPOMasterDto> getfpoByIdAndRegNo(Long fpoId, String fpoRegNo, Long iaId, Long masId, String orgShortNm,
			String uniqueId) {
		List<FPOMasterDto> dtoList = new ArrayList<>();
		try {
			logger.info("getfpoByIdAndRegNo Started");
			List<FPOMasterEntity> EnitityList = fpoMasterDao.getfpoByIdAndRegNo(fpoId, fpoRegNo, iaId, masId,
					orgShortNm, uniqueId);
			for (FPOMasterEntity entity : EnitityList) {
				FPOMasterDto dto = new FPOMasterDto();
				BeanUtils.copyProperties(entity, dto);
				if (entity.getDateIncorporation() != null) {
					dto.setFpoRegDate(Utility.getFinancialYearFromDate(entity.getDateIncorporation()));
				}

				if (entity.getAppStatus() != null && entity.getAppStatus().equals(MainetConstants.FlagA)) {
					dto.setAppByIaDesc(MainetConstants.TASK_STATUS_APPROVED);
				} else if (entity.getAppStatus() != null && entity.getAppStatus().equals(MainetConstants.FlagR)) {
					dto.setAppByIaDesc(MainetConstants.TASK_STATUS_REJECTED);
				} else if (entity.getAppStatus() != null && entity.getAppStatus().equals(MainetConstants.FlagP)) {
					dto.setAppByIaDesc(MainetConstants.TASK_STATUS_PENDING);
				} else {
					dto.setAppByIaDesc(MainetConstants.CommonConstants.NA);
					dto.setAppStatus(MainetConstants.CommonConstants.NA);
				}
				
				
				if (StringUtils.isNotEmpty(entity.getActiveInactiveStatus()) && entity.getActiveInactiveStatus().equals(MainetConstants.FlagI)) 
					dto.setSummaryStatus(MainetConstants.Common_Constant.INACTIVE);
				else if(StringUtils.isNotEmpty(entity.getActiveInactiveStatus()) && entity.getActiveInactiveStatus().equals(MainetConstants.FlagA)) {
					dto.setSummaryStatus(MainetConstants.Common_Constant.ACTIVE);
				}else {
					dto.setSummaryStatus(MainetConstants.CommonConstants.NA);
				}
				
				if (entity.getApproved() != null
						&& entity.getApproved().equals(MainetConstants.FlagA))
					dto.setAppByCbboDesc(MainetConstants.TASK_STATUS_APPROVED);
				else
					dto.setAppByCbboDesc(MainetConstants.CommonConstants.NA);

				/*
				 * LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("NAP", "FMA");
				 * if (entity.getApprovedByIa() != null) { LookUp IaLookUp =
				 * CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getApprovedByIa());
				 * dto.setAppByIaDesc(IaLookUp.getDescLangFirst()); } else {
				 * dto.setAppByIaDesc(lookUp.getDescLangFirst()); }
				 * 
				 * if (entity.getApprovedByCbbo() != null) { LookUp cbboLookUp =
				 * CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getApprovedByCbbo()
				 * ); dto.setAppByCbboDesc(cbboLookUp.getDescLangFirst()); } else {
				 * dto.setAppByCbboDesc(lookUp.getDescLangFirst()); }
				 * 
				 * if (entity.getApprovedByFpo() != null) { LookUp fpoLookUp =
				 * CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getApprovedByFpo())
				 * ; dto.setAppByFpoDesc(fpoLookUp.getDescLangFirst()); } else {
				 * dto.setAppByFpoDesc(lookUp.getDescLangFirst()); }
				 */
				
				dtoList.add(dto);
			}

		} catch (Exception e) {
			logger.error("Error occured while fetching fpo details in service" + e);
		}
		logger.info("getfpoByIdAndRegNo Ended");
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#getFpoName(java.lang.String)
	 */
	@Override
	public String getFpoName(String frmFPORegNo) {
		String fponame = fpoMasterRepository.getFpoNameBy(frmFPORegNo);
		return fponame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#findFPOByIds(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<FPOMasterDto> findFPOByIds(Long masId) {

		List<FPOMasterDto> fPOMasterDtoList = new ArrayList<FPOMasterDto>();
		try {
			logger.info("findFPOByIds Started");
			List<Object[]> detailsList = fpoMasterRepository.findFPOByIds(masId);
			for (Object[] obj : detailsList) {
				FPOMasterDto fpoMasterDto = new FPOMasterDto();
				if ((Long) obj[0] != null)
					fpoMasterDto.setFpoId((Long) obj[0]);
				if (StringUtils.isNotEmpty((String) obj[1]))
					fpoMasterDto.setFpoName((String) obj[1]);
				if (StringUtils.isNotEmpty((String) obj[2]))
					fpoMasterDto.setFpoRegNo((String) obj[2]);
				fPOMasterDtoList.add(fpoMasterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Fpo details in findFPOByIds()" + e);
		}
		logger.info("findFPOByIds Ended");
		return fPOMasterDtoList;
	}

	/**
	 * @param masEntity
	 */
	private void saveHistoryData(FPOMasterEntity masEntity) {
		FPOMasterHistory histEn = new FPOMasterHistory();
		try {
			logger.info("saveHistoryData Started");
			BeanUtils.copyProperties(masEntity, histEn);
			if (histEn.getUpdatedBy() == null)
				histEn.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
			else
				histEn.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			List<FPOMasterDetailHistory> detHist = new ArrayList<>();
			List<FPOAdministrativeDetHistory> adminHist = new ArrayList<>();
			List<FPOBankDetailHistory> bankList = new ArrayList<>();

			masEntity.getFpoMasterDetail().forEach(det -> {
				FPOMasterDetailHistory hist = new FPOMasterDetailHistory();
				BeanUtils.copyProperties(det, hist);
				if (det.getUpdatedBy() == null)
					hist.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
				else
					hist.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				hist.setMasterHistEntity(histEn);
				hist.setFpocId(det.getFpocId());
				detHist.add(hist);
			});
			masEntity.getFpoAdministrativeDetail().forEach(admin -> {
				FPOAdministrativeDetHistory hist = new FPOAdministrativeDetHistory();
				BeanUtils.copyProperties(admin, hist);
				if (admin.getUpdatedBy() == null)
					hist.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
				else
					hist.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				hist.setMasterHistEntity(histEn);
				hist.setAdId(admin.getAdId());
				adminHist.add(hist);
			});

			masEntity.getFpoBankMasterDetail().forEach(bank -> {
				FPOBankDetailHistory hist = new FPOBankDetailHistory();
				BeanUtils.copyProperties(bank, hist);
				if (bank.getUpdatedBy() == null)
					hist.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
				else
					hist.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				hist.setMasterHistEntity(histEn);
				hist.setBkId(bank.getBkId());
				bankList.add(hist);
			});

			histEn.setFpoDetHist(detHist);
			histEn.setFpoBankDetHist(bankList);
			histEn.setFpoAdminDetHist(adminHist);
			fpoMasHistRepo.save(histEn);
			logger.info("saveHistoryData Ended");
		} catch (Exception e) {
			logger.error("Error Occured while saving fpo master history data" + e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#updateFpoDetails(com.abm.mainet.
	 * sfac.dto.FPOMasterDto)
	 */
	@Override
	@Transactional
	public FPOMasterDto updateFpoDetails(FPOMasterDto mastDto) {

		try {
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			logger.info("updateFpoDetails started");
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.FPO,
					org.getOrgid());
			Long applicationId = null;
			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationId(applicationId);
			FPOMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = fpoMasterRepository.save(masEntity);
			saveHistoryData(masEntity);
			initiateWorkflow(mastDto, sm, requestDto);
			logger.info("updateFpoDetails Ended");
		} catch (Exception e) {
			logger.error("Error occured while updating fpo master details" + e);
			throw new FrameworkException("error occured while updating fpo master details" + e);
		}
		return mastDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#getFpoDetByAppId(java.lang.Long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public FPOMasterDto getFpoDetByAppId(Long applicationId) {
		FPOMasterDto dto = new FPOMasterDto();
		List<FPOMasterDetailDto> fpoMasterDetailDtoList = new ArrayList<>();
		List<FPOAdministrativeDto> fpoAdministrativeDtoList = new ArrayList<>();
		List<FPOBankDetailDto> fpoBankDetailDtoList = new ArrayList<>();

		FPOMasterEntity entity = fpoMasterRepository.getDetailByAppId(applicationId);
		BeanUtils.copyProperties(entity, dto);

		List<FPOMasterDetailEntity> detEntity = fpoDetRepo.getDetails(entity);
		detEntity.forEach(detDto -> {
			FPOMasterDetailDto detailDto = new FPOMasterDetailDto();
			BeanUtils.copyProperties(detDto, detailDto);
			if (null != detDto.getCropSeason()) {
				LookUp cropSeason = CommonMasterUtility.getNonHierarchicalLookUpObject(detDto.getCropSeason());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					detailDto.setCropSeasonDesc(cropSeason.getDescLangFirst());
				else
					detailDto.setCropSeasonDesc(cropSeason.getDescLangSecond());
			}
			if (null != detDto.getCropType()) {
				LookUp cropType = CommonMasterUtility.getNonHierarchicalLookUpObject(detDto.getCropType());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					detailDto.setCropTypeDesc(cropType.getDescLangFirst());
				else
					detailDto.setCropTypeDesc(cropType.getDescLangSecond());
			}
			if (null != detDto.getPriSecCrop()) {
				LookUp priSecCrop = CommonMasterUtility.getNonHierarchicalLookUpObject(detDto.getPriSecCrop());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					detailDto.setPriSecCropDesc(priSecCrop.getDescLangFirst());
				else
					detailDto.setPriSecCropDesc(priSecCrop.getDescLangSecond());
			}
			if (null != detDto.getApprovedByDmc()) {
				LookUp approvedByDmc = CommonMasterUtility.getNonHierarchicalLookUpObject(detDto.getApprovedByDmc());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					detailDto.setApprovedByDmcDesc(approvedByDmc.getDescLangFirst());
				else
					detailDto.setApprovedByDmcDesc(approvedByDmc.getDescLangSecond());
			}
			fpoMasterDetailDtoList.add(detailDto);
		});
		List<FPOAdministrativeDetailEntity> adminEntity = adminRepo.getAdminDetails(entity);
		adminEntity.forEach(admin -> {
			FPOAdministrativeDto adminDto = new FPOAdministrativeDto();
			BeanUtils.copyProperties(admin, adminDto);
			if (null != admin.getDateOfJoining())
				adminDto.setDofJoiningDesc(Utility.dateToString(admin.getDateOfJoining()));
			if (null != admin.getDsgId()) {
				DesignationBean desg = designationService.findById(admin.getDsgId());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					adminDto.setDesignation(desg.getDsgname());
				else
					adminDto.setDesignation(desg.getDsgnameReg());
			}

			if (null != admin.getTitleId()) {
				LookUp title = CommonMasterUtility.getNonHierarchicalLookUpObject(admin.getTitleId());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					adminDto.setTitleDesc(title.getDescLangFirst());
				else
					adminDto.setTitleDesc(title.getDescLangSecond());
			}

			if (null != admin.getNameOfBoard()) {
				LookUp name = CommonMasterUtility.getNonHierarchicalLookUpObject(admin.getNameOfBoard());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					adminDto.setNameOfBoardDesc(name.getDescLangFirst());
				else
					adminDto.setNameOfBoardDesc(name.getDescLangSecond());
			}
			fpoAdministrativeDtoList.add(adminDto);
		});
		List<FPOBankDetailEntity> bankDet = bankRepo.getBankDetails(entity);
		bankDet.forEach(bank -> {
			FPOBankDetailDto bankDto = new FPOBankDetailDto();
			BeanUtils.copyProperties(bank, bankDto);
			String bankName = bankMasterService.getBankById(bank.getBankName());
			bankDto.setBankName(bankName);
			bankDto.setIfscCode(bank.getIfscCode());
			if (bank.getBankName() != 0)
				fpoBankDetailDtoList.add(bankDto);
		});

		dto.setFpoMasterDetailDto(fpoMasterDetailDtoList);
		dto.setFpoAdministrativeDto(fpoAdministrativeDtoList);
		dto.setFpoBankDetailDto(fpoBankDetailDtoList);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#updateApprovalStatusAndRemark(
	 * com.abm.mainet.sfac.dto.FPOMasterDto,
	 * com.abm.mainet.sfac.ui.model.FpoMasterApprovalModel)
	 */
	@Override
	@Transactional
	public void updateApprovalStatusAndRemark(FPOMasterDto dto, FpoMasterApprovalModel fpoMasterApprovalModel) {
		if (dto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			dto.setAppStatus(MainetConstants.FlagA);
		else if (dto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			dto.setAppStatus(MainetConstants.FlagR);
		fpoMasterRepository.updateApprovalStatusAndRemark(dto.getFpoId(), dto.getAppStatus(), dto.getRemark());
		fpoMasHistRepo.updateApprovalStatusAndRemark(dto.getFpoId(), dto.getAppStatus(), dto.getRemark());
	}

	@Override
	public Long getFPOCount(Long cbboId) {
		// TODO Auto-generated method stub
		return fpoMasterRepository.countByCbboId(cbboId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.FPOMasterService#findByIaId(java.lang.Long)
	 */
	@Override
	public List<FPOMasterDto> findByIaId(Long masId) {
		List<FPOMasterDto> dtoList = new ArrayList<>();
		List<FPOMasterEntity> entity = fpoMasterRepository.findByIaId(masId);
		for (FPOMasterEntity fpoMasterEntity : entity) {
			FPOMasterDto dto = new FPOMasterDto();
			BeanUtils.copyProperties(fpoMasterEntity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@SuppressWarnings("deprecation")
	private void initiateWorkflow(FPOMasterDto fpoMasterDto, ServiceMaster sm, RequestDTO requestDto) {
		try {
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(fpoMasterDto.getApplicationId());
				applicationData.setOrgId(org.getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				fpoMasterDto.getApplicantDetailDto().setUserId(fpoMasterDto.getCreatedBy());
				fpoMasterDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				fpoMasterDto.getApplicantDetailDto().setExtIdentifier(fpoMasterDto.getIaId());
				fpoMasterDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				if (requestDto != null && requestDto.getMobileNo() != null) {
					fpoMasterDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, fpoMasterDto.getApplicantDetailDto());
			}
		} catch (Exception e) {
			logger.error("error occured while initiating  fpo master workflow" + e);
			throw new FrameworkException("error occured while initiating  fpo master workflow" + e);
		}
	}

	@Override
	public List<FPOMasterDto> findByCbboId(Long cbboId) {
		List<FPOMasterDto> dtoList = new ArrayList<>();
		List<FPOMasterEntity> entity = fpoMasterRepository.findByCbboId(cbboId);
		for (FPOMasterEntity fpoMasterEntity : entity) {
			FPOMasterDto dto = new FPOMasterDto();
			BeanUtils.copyProperties(fpoMasterEntity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FPOMasterService#findBlockDetailsByMasIdAndYr(
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<BlockAllocationDetailDto> findBlockDetailsByMasIdAndYr(Long iaId, Long masId) {
		List<BlockAllocationDetailDto> dtoList = new ArrayList<>();
		try {
			List<BlockAllocationDetailEntity> blockDetEntity = allocationOfBlocksRepo.findBlockDetByYrAndMasId(iaId,
					masId);
			for (BlockAllocationDetailEntity entity : blockDetEntity) {
				BlockAllocationDetailDto dto = new BlockAllocationDetailDto();
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			logger.error("error occured while fetching block details in findBlockDetailsByMasIdAndYr"+e);
		}
		return dtoList;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.FPOMasterService#checkComRegNoExist(java.lang.String)
	 */
	@Override
	public boolean checkComRegNoExist(String companyRegNo) {
		Boolean result = false;
		try {
			result = fpoMasterRepository.findByCompanyRegNo(companyRegNo);
		} catch (Exception e) {
			logger.error(
					"Error occured while checking company Registration no  present or not in checkComRegNoExist()" + e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.FPOMasterService#checkFpoNameExist(java.lang.String)
	 */
	@Override
	public boolean checkFpoNameExist(String fpoName) {
		Boolean result = false;
		try {
			result = fpoMasterRepository.checkFpoNameExist(fpoName);
		} catch (Exception e) {
			logger.error(
					"Error occured while checking Fpo name present or not in checkFpoNameExist()" + e);
		}
		return result;
	}

}
