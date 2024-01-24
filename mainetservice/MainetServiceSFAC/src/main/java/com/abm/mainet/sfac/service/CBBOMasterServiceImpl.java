/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.dao.CBBOMasterDao;
import com.abm.mainet.sfac.domain.CBBOMastDetailEntity;
import com.abm.mainet.sfac.domain.CBBOMastDetailHistory;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.CBBOMasterHistory;
import com.abm.mainet.sfac.domain.CbMasterEntity;
import com.abm.mainet.sfac.domain.StateAreaZoneCategoryEntity;
import com.abm.mainet.sfac.dto.CBBOMastDetailDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.StateAreaZoneCategoryDto;
import com.abm.mainet.sfac.repository.CBBOMastDetailRepository;
import com.abm.mainet.sfac.repository.CBBOMasterHistRepo;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.CbMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.ui.model.CBBOMasterApprovalModel;

/**
 * @author pooja.maske
 *
 */
@Service
public class CBBOMasterServiceImpl implements CBBOMasterService {

	private static final Logger logger = Logger.getLogger(CBBOMasterServiceImpl.class);

	@Autowired
	private CBBOMasterDao cbboMasterDao;

	@Autowired
	private CBBOMasterRepository cbboMasterRepository;

	@Autowired
	private CBBOMasterHistRepo histRepo;

	@Autowired
	private FPOMasterRepository fpoMastRepo;

	@Autowired
	private CBBOMastDetailRepository cbboDetRepo;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CommonService commonService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private CbMasterRepository cbMasRepository;

	@Override
	@Transactional
	public CBBOMasterDto saveCbboMasterDetails(CBBOMasterDto masterDto) {

		try {
			logger.info("saveCbboMasterDetails Started");
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.CBBO,
					org.getOrgid());
			if (masterDto.getSdb1() != null) {
				LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(masterDto.getSdb1(), masterDto.getOrgId());
				String cbboUniqueId = lookUp.getLookUpCode() + masterDto.getPanNo().toUpperCase();
				if (cbboUniqueId != null)
					masterDto.setCbboUniqueId(cbboUniqueId);
			}

			Long applicationId = null;
			RequestDTO requestDto = setApplicantRequestDto(masterDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				masterDto.setApplicationId(applicationId);
			CBBOMasterEntity entity = mapDtoToEntity(masterDto);
			entity = cbboMasterRepository.save(entity);
			if (null == masterDto.getCbId()) {
				entity.setCbId(entity.getCbboId());
				entity = cbboMasterRepository.save(entity);
			}

			CbMasterEntity cbEntity = cbMasRepository.findOne(entity.getCbId());
			if (null == cbEntity) {
				CbMasterEntity en = new CbMasterEntity();
				en.setCbId(entity.getCbId());
				en.setCbboName(entity.getCbboName());
				en.setCbboUniqueId(entity.getCbboUniqueId());
				en.setPanNo(entity.getPanNo());
				en.setCbboEmpanelmentYear(entity.getCbboAppoitmentYr());
				en.setCbRegDate(entity.getAlcYearToCBBO());
				en.setIa_id(entity.getIaId());
				en.setCreatedBy(entity.getCreatedBy());
				en.setOrgId(entity.getOrgId());
				en.setCreatedDate(entity.getCreatedDate());
				en.setSdb1(entity.getSdb1());
				cbMasRepository.save(en);
			}

			CBBOMasterHistory histEntity = new CBBOMasterHistory();
			BeanUtils.copyProperties(entity, histEntity);
			histEntity.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
			List<CBBOMastDetailHistory> detHistList = new ArrayList<>();
			for (CBBOMastDetailEntity det : entity.getCbboMasterDetail()) {
				CBBOMastDetailHistory detHist = new CBBOMastDetailHistory();
				BeanUtils.copyProperties(det, detHist);
				detHist.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
				detHist.setCbboDId(det.getCbboDId());
				detHist.setMasterHistEntity(histEntity);
				detHistList.add(detHist);
			}
			histEntity.setCbboMasterDetailHist(detHistList);
			histRepo.save(histEntity);

			initiateWorkflow(masterDto, sm, requestDto);
		} catch (Exception e) {
			logger.error("Error occured while saving cbbo master details" + e);
			throw new FrameworkException("Error occured while saving cbbo master details" + e);
		}
		logger.info("saveCbboMasterDetails Ended");
		return masterDto;
	}

	/**
	 * @param masterDto
	 * @param sm
	 * @return
	 */
	private RequestDTO setApplicantRequestDto(CBBOMasterDto masterDto, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masterDto.getCreatedBy());
		requestDto.setOrgId(masterDto.getOrgId());
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(masterDto.getOrgId());
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		requestDto.setEmail(masterDto.getEmail());
		requestDto.setMobileNo(masterDto.getMobileNo());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;

	}

	/**
	 * @param masterDto
	 * @return
	 */
	private CBBOMasterEntity mapDtoToEntity(CBBOMasterDto masterDto) {
		CBBOMasterEntity masEntity = new CBBOMasterEntity();
		List<CBBOMastDetailEntity> detailsList = new ArrayList<>();
		BeanUtils.copyProperties(masterDto, masEntity);
		masEntity.setPanNo(masterDto.getPanNo().toUpperCase());
		masterDto.getCbboDetDto().forEach(det -> {
			CBBOMastDetailEntity entity = new CBBOMastDetailEntity();
			BeanUtils.copyProperties(det, entity);
			entity.setMasterEntity(masEntity);
			detailsList.add(entity);
		});
		masEntity.setCbboMasterDetail(detailsList);
		return masEntity;
	}

	/**
	 * @param masterDto
	 * @return
	 *//*
		 * private String generatedCbboUniqueId(CBBOMasterDto masterDto) {
		 * 
		 * String cbboUniqueId = null; try {
		 * logger.info("new registration no generation started"); String stateCode = "";
		 * String pan = "";
		 * 
		 * cbboUniqueId = stateCode + pan; logger.info("new generated cbboUniqueId  " +
		 * cbboUniqueId); } catch (Exception e) {
		 * logger.error("Error occured while creating new registration Number" + e); }
		 * return cbboUniqueId;
		 * 
		 * }
		 */

	@Override
	public List<CBBOMasterDto> getCBBODetailsByIds(Long cbboId, Date alcYearToCBBO, Long orgId, Long iaId) {
		List<CBBOMasterDto> cbboMasterDtolist = new ArrayList<CBBOMasterDto>();
		try {
			logger.info("getCBBODetailsByIdsO Started");
			List<CBBOMasterEntity> detailsList = cbboMasterDao.getCBBODetailsByIds(cbboId, alcYearToCBBO, iaId);
			for (CBBOMasterEntity cbboMasterEntity : detailsList) {
				CBBOMasterDto cbboMasterDto = new CBBOMasterDto();
				BeanUtils.copyProperties(cbboMasterEntity, cbboMasterDto);
				if (cbboMasterEntity.getAppStatus() != null
						&& cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagA)) {
					cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_APPROVED);
				} else if (cbboMasterEntity.getAppStatus() != null
						&& cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagR)) {
					cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_REJECTED);
				} else if (cbboMasterEntity.getAppStatus() != null
						&& cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagP)) {
					cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_PENDING);
				} else {
					cbboMasterDto.setStatusDesc(MainetConstants.CommonConstants.NA);
					cbboMasterEntity.setAppStatus(MainetConstants.CommonConstants.NA);
				}
				if (StringUtils.isNotEmpty(cbboMasterEntity.getActiveInactiveStatus())
						&& cbboMasterEntity.getActiveInactiveStatus().equals(MainetConstants.FlagI))
					cbboMasterDto.setSummaryStatus(MainetConstants.Common_Constant.INACTIVE);
				else if (StringUtils.isNotEmpty(cbboMasterEntity.getActiveInactiveStatus())
						&& cbboMasterEntity.getActiveInactiveStatus().equals(MainetConstants.FlagA)) {
					cbboMasterDto.setSummaryStatus(MainetConstants.Common_Constant.ACTIVE);
				} else {
					cbboMasterDto.setSummaryStatus(MainetConstants.CommonConstants.NA);
				}
				cbboMasterDtolist.add(cbboMasterDto);
			}

		} catch (Exception e) {
			logger.error("Error occured while fetching cbbo details" + e);
			throw new FrameworkException("Error occured while fetching cbbo details" + e);
		}
		logger.info("getCBBODetailsByIdsO Ended");
		return cbboMasterDtolist;
	}

	@Override
	public List<CBBOMasterDto> getCBBODetailsByorgId(Long orgId) {
		List<CBBOMasterDto> cbboMasterDtolist = new ArrayList<CBBOMasterDto>();
		try {
			logger.info("getCBBODetailsByIdsO Started");
			List<CBBOMasterEntity> detailsList = cbboMasterRepository.getCBBODetailsByorgId(orgId);
			for (CBBOMasterEntity cbboMasterEntity : detailsList) {
				CBBOMasterDto cbboMasterDto = new CBBOMasterDto();
				BeanUtils.copyProperties(cbboMasterEntity, cbboMasterDto);
				cbboMasterDtolist.add(cbboMasterDto);
			}

		} catch (Exception e) {
			logger.error("Error occured while fetching cbbo details" + e);
			throw new FrameworkException("Error occured while fetching cbbo details" + e);
		}
		logger.info("getCBBODetailsByIdsO Ended");
		return cbboMasterDtolist;
	}

	@Override
	public List<CBBOMasterDto> findAllCBBO() {
		List<CBBOMasterDto> cbboMasterDtolist = new ArrayList<CBBOMasterDto>();
		try {
			logger.info("findAllCBBO Started");
			List<CbMasterEntity> detailsList = cbMasRepository.findAll();
			for (CbMasterEntity cbboMasterEntity : detailsList) {
				CBBOMasterDto cbboMasterDto = new CBBOMasterDto();
				BeanUtils.copyProperties(cbboMasterEntity, cbboMasterDto);
				cbboMasterDtolist
						.add(cbboMasterDto);/*
											 * if (cbboMasterEntity.getAppStatus() != null &&
											 * cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagA)) {
											 * cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_APPROVED); } else
											 * if (cbboMasterEntity.getAppStatus() != null &&
											 * cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagR)) {
											 * cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_REJECTED); } else
											 * if (cbboMasterEntity.getAppStatus() != null &&
											 * cbboMasterEntity.getAppStatus().equals(MainetConstants.FlagP)) {
											 * cbboMasterDto.setStatusDesc(MainetConstants.TASK_STATUS_PENDING); } else
											 * { cbboMasterDto.setStatusDesc(MainetConstants.CommonConstants.NA);
											 * cbboMasterEntity.setAppStatus(MainetConstants.CommonConstants.NA); }
											 */
			}

		} catch (Exception e) {
			logger.error("Error occured while fetching cbbo details" + e);
			throw new FrameworkException("Error occured while fetching cbbo details" + e);
		}
		logger.info("findAllCBBO Ended");
		return cbboMasterDtolist;
	}

	@Override
	public StateAreaZoneCategoryDto fetchAreaAndZoneByStateCode(String stateCode) {
		StateAreaZoneCategoryDto dto = new StateAreaZoneCategoryDto();
		try {
			StateAreaZoneCategoryEntity entity = cbboMasterRepository.fetchAreaAndZoneByStateCode(stateCode);
			BeanUtils.copyProperties(entity, dto);
		} catch (Exception e) {
			logger.error("Error occured while fetching Area and zone in fetchAreaAndZoneByStateCode()" + e);
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#checkIsAispirationalDist(java.
	 * lang.Long)
	 */
	@Override
	public boolean checkIsAispirationalDist(Long sdb3) {
		Boolean isAispirationalDist = cbboMasterRepository.checkIsAispirationalDist(sdb3);
		if (isAispirationalDist) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIsTribalDist(Long sdb3) {
		Boolean isTribalDist = cbboMasterRepository.checkIsTribalDist(sdb3);
		if (isTribalDist) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#getOdopListByDist(java.lang.
	 * Long)
	 */
	@Override
	public String getOdopByDist(Long sdb2) {
		String odopName = "";
		odopName = cbboMasterRepository.getOdopByDist(sdb2);
		return odopName;
	}

	@Override
	public CBBOMasterDto getCbboDetailsByIAId(Long iaId) {
		CBBOMasterDto dto = new CBBOMasterDto();
		CBBOMasterEntity entity = cbboMasterRepository.getCbboDetailsByIAId(iaId);
		if (null != entity)
			BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#findBycbboId(java.lang.Long)
	 */
	@Override
	@Transactional
	public CBBOMasterDto findBycbboId(Long cbboId) {
		CBBOMasterDto dto = new CBBOMasterDto();
		List<CBBOMastDetailDto> detailDtoList = new ArrayList<>();
		try {
			logger.info("findBycbboId Started");
			CBBOMasterEntity entity = cbboMasterRepository.findOne(cbboId);
			BeanUtils.copyProperties(entity, dto);
			List<CBBOMastDetailEntity> detEntity = cbboMasterRepository.getChildDetails(entity);
			if (CollectionUtils.isNotEmpty(detEntity)) {
				detEntity.forEach(d -> {
					CBBOMastDetailDto detDto = new CBBOMastDetailDto();
					BeanUtils.copyProperties(d, detDto);
					if (null != d.getStatus() && d.getStatus().equals(MainetConstants.FlagA)) {
						detailDtoList.add(detDto);
					}
				});
			}
			dto.setCbboDetDto(detailDtoList);
		} catch (Exception e) {
			logger.error("Error Ocurred while fetching Cbbo details by cbboId  " + e);
			throw new FrameworkException("Error Ocurred while fetching Cbbo details by cbboId" + e);
		}
		logger.info("findBycbboId ended");
		return dto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#inactiveRemovedContactDetails(
	 * com.abm.mainet.sfac.dto.CBBOMasterDto, java.util.List)
	 */
	@Override
	@Transactional
	public void inactiveRemovedContactDetails(CBBOMasterDto masterDto, List<Long> removedContDetIdsList) {

		if (removedContDetIdsList != null && !removedContDetIdsList.isEmpty()) {
			cbboMasterRepository.iactiveContactDetByIds(removedContDetIdsList, masterDto.getUpdatedBy());
			cbboMasterRepository.iactiveContactDetInHistByIds(removedContDetIdsList, masterDto.getUpdatedBy());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#fetchPromotionAgnByCbboId(java.
	 * lang.Long)
	 */
	@Override
	public Long fetchPromotionAgnByCbboId(Long cbboId) {
		Long typeOfpromotionAgen = null;
		typeOfpromotionAgen = cbboMasterRepository.fetchPromotionAgnByCbboId(cbboId);
		if (typeOfpromotionAgen == null)
			typeOfpromotionAgen = 0L;
		return typeOfpromotionAgen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#getDetailsByPanNo(java.lang.
	 * Long)
	 */
	@Override
	public CBBOMasterDto getDetailsByPanNo(String panNo) {

		CBBOMasterDto dto = new CBBOMasterDto();
		List<CBBOMastDetailDto> detailDtoList = new ArrayList<>();
		try {
			logger.info("getDetailsByPanNo Started");
			CBBOMasterEntity entity = cbboMasterRepository.getDetailsByPanNo(panNo);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);
				List<CBBOMastDetailEntity> detEntity = cbboDetRepo.findByMasterEntity(entity);
				if (CollectionUtils.isNotEmpty(detEntity)) {
					detEntity.forEach(d -> {
						CBBOMastDetailDto detDto = new CBBOMastDetailDto();
						BeanUtils.copyProperties(d, detDto);
						if (d.getStatus().equals(MainetConstants.FlagA)) {
							detailDtoList.add(detDto);
						}
					});
				}
				dto.setCbboDetDto(detailDtoList);
			}
		} catch (Exception e) {
			logger.error("Error Ocurred while fetching cbbo details by pan no  " + e);
		}
		logger.info("getDetailsByPanNo ended");
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOMasterService#checkPanNoExist(java.lang.
	 * String)
	 */
	@Override
	public boolean checkPanNoExist(String panNo, Long iaId, Long empId) {
		Boolean present = cbboMasterRepository.checkPanNoExist(panNo, iaId, empId);
		if (present) {
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#findAllIaAssociatedWithCbbo(java.
	 * lang.Long)
	 */
	@Override
	public List<CBBOMasterDto> findAllIaAssociatedWithCbbo(String loginName) {
		List<CBBOMasterDto> masterDtoList = new ArrayList<>();
		try {
			logger.info("findAllIaAssociatedWithCbbo started");
			List<Object[]> entityList = cbboMasterRepository.findAllIaAssociatedWithCbbo(loginName);
			for (Object[] obj : entityList) {
				CBBOMasterDto masterDto = new CBBOMasterDto();
				if (obj[0] != null)
					masterDto.setCbboId((Long) obj[0]);
				if (StringUtils.isNotEmpty((String) obj[1]))
					masterDto.setCbboName((String) obj[1]);
				if (obj[2] != null)
					masterDto.setIaId((Long) obj[2]);
				if (StringUtils.isNotEmpty((String) obj[1]))
					masterDto.setIAName((String) obj[3]);
				masterDtoList.add(masterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Ia Details By empId " + e);
		}
		logger.info("findAllIaAssociatedWithCbbo ended");
		return masterDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#updateCbboMasterDetails(com.abm
	 * .mainet.sfac.dto.CBBOMasterDto)
	 */
	@Override
	@Transactional
	public CBBOMasterDto updateCbboMasterDetails(CBBOMasterDto mastDto) {
		try {
			logger.info("updateCbboMasterDetails Started");
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.CBBO,
					org.getOrgid());
			Long applicationId = null;
			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationId(applicationId);
			CBBOMasterEntity entity = mapDtoToEntity(mastDto);
			entity = cbboMasterRepository.save(entity);

			CBBOMasterHistory histEntity = new CBBOMasterHistory();
			BeanUtils.copyProperties(entity, histEntity);
			histEntity.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			List<CBBOMastDetailHistory> detHistList = new ArrayList<>();
			for (CBBOMastDetailEntity det : entity.getCbboMasterDetail()) {
				CBBOMastDetailHistory detHist = new CBBOMastDetailHistory();
				BeanUtils.copyProperties(det, detHist);
				detHist.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				detHist.setCbboDId(det.getCbboDId());
				detHist.setMasterHistEntity(histEntity);
				detHistList.add(detHist);
			}
			histEntity.setCbboMasterDetailHist(detHistList);
			histRepo.save(histEntity);
			initiateWorkflow(mastDto, sm, requestDto);
		} catch (Exception e) {
			logger.error("Error occured while updating cbbo master details" + e);
			throw new FrameworkException("Error occured while updating cbbo master details" + e);
		}
		logger.info("updateCbboMasterDetails ended");
		return mastDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#findCbboById(java.lang.Long)
	 */
	@Override
	public List<CBBOMasterDto> findCbboById(Long masId) {
		List<CBBOMasterDto> cbboMasterDtolist = new ArrayList<CBBOMasterDto>();
		try {
			logger.info("findCbboById Started");
			cbboMasterDtolist = StreamSupport.stream(cbboMasterRepository.findCbboById(masId).spliterator(), false)
					.filter(distinctByKey(CBBOMasterEntity::getCbboName))// distinct by object attributes(CBBO_NAME)
					.map(entity -> {
						CBBOMasterDto dto = new CBBOMasterDto();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("Error occured while fetching cbbo details in findCbboById()" + e);
			throw new FrameworkException("Error occured while fetching cbbo details in findCbboById()" + e);
		}
		logger.info("findCbboById Ended");
		return cbboMasterDtolist;
	}

	/**
	 * @param object
	 * @return
	 */
	// this method is use only to distinct list by using their object attributes
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CBBOMasterService#findById(java.lang.Long)
	 */
	@Override
	public CBBOMasterDto findById(Long cbboId) {
		CBBOMasterDto dto = new CBBOMasterDto();
		try {
			CBBOMasterEntity entity = cbboMasterRepository.findOne(cbboId);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);
				Long count = fpoMastRepo.getCountOfRegFpoWithCBBO(cbboId);
				if (entity.getFpoAllocationTarget() != null && entity.getFpoAllocationTarget() != 0)
					dto.setRegPendCount(entity.getFpoAllocationTarget() - count);
				dto.setRegCount(count);
			}
		} catch (Exception e) {
			Log.error("Error Ocurred while fetching cbbo records in findById" + e);
		}
		return dto;
	}

	@Override
	public List<CBBOMasterDto> getCBBOList(Long masId) {
		// TODO Auto-generated method stub
		List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
		List<CBBOMasterEntity> cbboMasterEntities = cbboMasterRepository.findByIaId(masId);
		for (CBBOMasterEntity cbboMasterEntity : cbboMasterEntities) {
			CBBOMasterDto cbboMasterDto = new CBBOMasterDto();
			BeanUtils.copyProperties(cbboMasterEntity, cbboMasterDto);
			cbboMasterDtos.add(cbboMasterDto);
		}

		return cbboMasterDtos;
	}

	@Override
	public List<CBBOMasterDto> findIAList(String cbboUniqueId) {
		// TODO Auto-generated method stub
		List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
		List<CBBOMasterEntity> cbboMasterEntities = cbboMasterRepository.findByCbboUniqueId(cbboUniqueId);
		for (CBBOMasterEntity cbboMasterEntity : cbboMasterEntities) {
			CBBOMasterDto cbboMasterDto = new CBBOMasterDto();
			BeanUtils.copyProperties(cbboMasterEntity, cbboMasterDto);
			cbboMasterDtos.add(cbboMasterDto);
		}
		return cbboMasterDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#getCbboByAppId(java.lang.Long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public CBBOMasterDto getCbboByAppId(Long applicationId) {
		CBBOMasterDto dto = new CBBOMasterDto();
		List<CBBOMastDetailDto> dtoList = new ArrayList<>();
		try {
			CBBOMasterEntity entity = cbboMasterRepository.findByApplicationId(applicationId);

			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);
				if (entity.getSdb1() != null) {
					LookUp look = CommonMasterUtility.getHierarchicalLookUp(entity.getSdb1());
					if (look != null) {
						if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
							dto.setState(look.getDescLangFirst());
						else
							dto.setState(look.getDescLangSecond());
					}
				}

				try {
					TbFinancialyear financialYear = null;
					if (entity.getCbboAppoitmentYr() != null)
						financialYear = financialyearService.findYearById(entity.getCbboAppoitmentYr(),
								entity.getOrgId());
					if (financialYear != null)
						dto.setAppYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
				} catch (Exception e) {
					logger.error("Error occured while fetching ia allocation year" + e);
				}

				dto.setAlYrCbbo(Utility.dateToString(entity.getAlcYearToCBBO()));
				List<CBBOMastDetailEntity> entityList = cbboDetRepo.findByMasterEntity(entity);
				entityList.forEach(det -> {
					CBBOMastDetailDto detDto = new CBBOMastDetailDto();
					BeanUtils.copyProperties(det, detDto);
					if (det.getTitleId() != null) {
						LookUp title = CommonMasterUtility.getNonHierarchicalLookUpObject(det.getTitleId());
						if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
							detDto.setTitle(title.getDescLangFirst());
						else
							detDto.setTitle(title.getDescLangSecond());
					}
					if (null != det.getDsgId()) {
						DesignationBean desg = designationService.findById(det.getDsgId());
						if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
							detDto.setDesignation(desg.getDsgname());
						else
							detDto.setDesignation(desg.getDsgnameReg());
					}
					dtoList.add(detDto);
				});
				dto.setCbboDetDto(dtoList);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching cbbo details by application id in getCbboByAppId" + e);
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CBBOMasterService#updateApprovalStatusAndRemark(
	 * com.abm.mainet.sfac.dto.CBBOMasterDto,
	 * com.abm.mainet.sfac.ui.model.CBBOMasterApprovalModel)
	 */
	@Override
	@Transactional
	public void updateApprovalStatusAndRemark(CBBOMasterDto dto, CBBOMasterApprovalModel cbboMasterApprovalModel) {
		if (dto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			dto.setAppStatus(MainetConstants.FlagA);
		else if (dto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			dto.setAppStatus(MainetConstants.FlagR);
		cbboMasterRepository.updateApprovalStatusAndRemark(dto.getCbboId(), dto.getAppStatus(), dto.getRemark());
		histRepo.updateApprovalStatusAndRemark(dto.getCbboId(), dto.getAppStatus(), dto.getRemark());

	}

	/**
	 * @param masterDto
	 * @param sm
	 * @param requestDto
	 */
	@SuppressWarnings("deprecation")
	private void initiateWorkflow(CBBOMasterDto masterDto, ServiceMaster sm, RequestDTO requestDto) {
		try {
			Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masterDto.getApplicationId());
				applicationData.setOrgId(org.getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				masterDto.getApplicantDetailDto().setUserId(masterDto.getCreatedBy());
				masterDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				masterDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				if (requestDto != null && requestDto.getMobileNo() != null) {
					masterDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, masterDto.getApplicantDetailDto());
			}
		} catch (Exception e) {
			logger.error("Error occured while initiating  cbbo master workflow" + e);
			throw new FrameworkException("Error occured while initiating  cbbo master workflow" + e);
		}
	}

}