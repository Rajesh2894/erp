/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dao.IAMasterDao;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterDetailEntity;
import com.abm.mainet.sfac.domain.IAMasterDetailHistory;
import com.abm.mainet.sfac.domain.IAMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterHistEntity;
import com.abm.mainet.sfac.dto.IAMasterDetailDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.IAMasterDetailRepository;
import com.abm.mainet.sfac.repository.IAMasterHistRepository;
import com.abm.mainet.sfac.repository.IAMasterRepository;

/**
 * @author pooja.maske
 *
 */

@Service
public class IAMasterServiceImpl implements IAMasterService {

	private static final Logger logger = Logger.getLogger(IAMasterServiceImpl.class);

	@Autowired
	private IAMasterRepository iaMasterRepository;

	@Autowired
	private IAMasterDao iaMasterDao;

	@Autowired
	private TbFinancialyearService financialyearService;


	@Autowired
	private IAMasterDetailRepository iaDetailRepository;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private CBBOMasterRepository cbboMasterRepository;

	@Autowired
	FPOMasterRepository fpoMasterRepository;


	@Autowired
	private IAMasterHistRepository iaMastHistRepo;
	
	@Autowired
	private IEmployeeService empService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#saveAndUpdateApplication(com.abm.
	 * mainet.sfac.dto.IAMasterDto)
	 */
	@Override
	@Transactional
	public IAMasterDto saveAndUpdateApplication(IAMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			IAMasterEntity masEntity = mapDtoToEntity(mastDto);
			iaMasterRepository.save(masEntity);

			IAMasterHistEntity histEntity = new IAMasterHistEntity();
			BeanUtils.copyProperties(masEntity, histEntity);
			histEntity.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());

			List<IAMasterDetailHistory> detHistList = new ArrayList<>();
			for (IAMasterDetailEntity det : masEntity.getIaMasterDetail()) {
				IAMasterDetailHistory detHist = new IAMasterDetailHistory();
				BeanUtils.copyProperties(det, detHist);
				detHist.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
				detHist.setMasterHistEntity(histEntity);
				detHist.setIadIdH(histEntity.getIaHId());
				detHistList.add(detHist);
			}
			histEntity.setDetailHist(detHistList);
			iaMastHistRepo.save(histEntity);
			//update isdeleted in employee table if inactive status
           if (StringUtils.isNotEmpty(masEntity.getActiveInactiveStatus()) &&  masEntity.getActiveInactiveStatus().equals(MainetConstants.FlagI) ) {
        	   empService.updateIsDeletedFlagByMasId(MainetConstants.ONE,mastDto.getIAId(),UserSession.getCurrent().getEmployee().getEmpId(),null,mastDto.getOrgId());
           }
		} catch (Exception e) {
			logger.error("error occured while saving IA master  details" + e);
			throw new FrameworkException("error occured while saving IA master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	/**
	 * @param mastDto
	 * @return
	 */
	private IAMasterEntity mapDtoToEntity(IAMasterDto mastDto) {
		IAMasterEntity masEntity = new IAMasterEntity();
		List<IAMasterDetailEntity> detailsList = new ArrayList<>();
		BeanUtils.copyProperties(mastDto, masEntity);
		mastDto.getIaDetailDto().forEach(det -> {
			IAMasterDetailEntity entity = new IAMasterDetailEntity();
			BeanUtils.copyProperties(det, entity);
			entity.setMasterEntity(masEntity);
			detailsList.add(entity);
		});
		masEntity.setIaMasterDetail(detailsList);
		return masEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#getIaDetailsByIds(java.lang.Long,
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<IAMasterDto> getIaDetailsByIds(Long IAName, Long allocationYear, Long orgId) {
		List<IAMasterDto> masterDtoList = new ArrayList<>();
		List<IAMasterDetailDto> detailDtoList = new ArrayList<>();
		try {
			logger.info("getIaDetailsByIds started");
			List<IAMasterEntity> entityList = iaMasterDao.getIaDetailsByIds(IAName, allocationYear, orgId);
			for (IAMasterEntity entity : entityList) {
				IAMasterDto masterDto = new IAMasterDto();
				BeanUtils.copyProperties(entity, masterDto);
				List<IAMasterDetailEntity> detailEnitity = iaDetailRepository.getIaDetail(entity);
				if (CollectionUtils.isNotEmpty(detailEnitity)) {
					detailEnitity.forEach(d -> {
						IAMasterDetailDto detDto = new IAMasterDetailDto();
						BeanUtils.copyProperties(d, detDto);
						detailDtoList.add(detDto);
					});
				}
				masterDto.setIaDetailDto(detailDtoList);
				
				if (StringUtils.isNotEmpty(entity.getActiveInactiveStatus()) && entity.getActiveInactiveStatus().equals(MainetConstants.FlagI)) 
					masterDto.setSummaryStatus(MainetConstants.Common_Constant.INACTIVE);
				else if(StringUtils.isNotEmpty(entity.getActiveInactiveStatus()) && entity.getActiveInactiveStatus().equals(MainetConstants.FlagA)) {
					masterDto.setSummaryStatus(MainetConstants.Common_Constant.ACTIVE);
				}else {
					masterDto.setSummaryStatus(MainetConstants.CommonConstants.NA);
				}

				/*
				 * CBBOMasterDto dto = cbboMasterService.getCbboDetailsByIAId(entity.getIAId());
				 * String cbboName = ""; String cbboUniqueId = ""; if (null != dto &&
				 * StringUtils.isNotEmpty(dto.getCbboName()))
				 * masterDto.setCbboName(dto.getCbboName()); else
				 * masterDto.setCbboName(cbboName); if (null != dto &&
				 * StringUtils.isNotEmpty(dto.getCbboUniqueId()))
				 * masterDto.setCbboUniqueId(dto.getCbboUniqueId()); else
				 * masterDto.setCbboUniqueId(cbboUniqueId);
				 */

				TbFinancialyear financialYear = financialyearService.findYearById(entity.getAlcYear(), orgId);
				try {
					masterDto.setAllcYear(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
				} catch (Exception e) {
					logger.error("Error occured while fetching financial year " + e);
				}
				masterDtoList.add(masterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Ia Details By Ids " + e);
		}
		logger.info("getIaDetailsByIds started");
		return masterDtoList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#getIAListByOrgId(java.lang.Long)
	 */
	@Override
	public List<IAMasterDto> getIAListByOrgId(Long orgId) {
		List<IAMasterDto> masterDtoList = new ArrayList<>();
		try {
			logger.info("getIAListByOrgId started");
			List<IAMasterEntity> entityList = iaMasterRepository.getIAListByOrgId(orgId);
			for (IAMasterEntity entity : entityList) {
				IAMasterDto masterDto = new IAMasterDto();
				BeanUtils.copyProperties(entity, masterDto);
				masterDtoList.add(masterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Ia Details By orgId " + e);
		}
		logger.info("geIAListByOrgId ended");
		return masterDtoList;
	}

	@Override
	public List<IAMasterDto> findAllIA() {
		List<IAMasterDto> masterDtoList = new ArrayList<>();
		try {
			logger.info("findAllIA started");
			List<IAMasterEntity> entityList = iaMasterRepository.findAllIA();
			for (IAMasterEntity entity : entityList) {
				IAMasterDto masterDto = new IAMasterDto();
				BeanUtils.copyProperties(entity, masterDto);
				masterDtoList.add(masterDto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching Ia Details By orgId " + e);
		}
		logger.info("findAllIA ended");
		return masterDtoList;
	}

	@Override
	public List<TbFinancialyear> getfinancialYearList(Organisation org) {
		List<TbFinancialyear> faYearsList = new ArrayList<>();
		try {
			final List<TbFinancialyear> finYearList = financialyearService.findAllFinancialYearByOrgId(org);
			// to get financial year from 2019 and so on as per requirement
			List<TbFinancialyear> yearList = new ArrayList<>();
			for (TbFinancialyear tbFinancialyear : finYearList) {
				int fromDate = Utility.getYearByDate(tbFinancialyear.getFaFromDate());
				if (fromDate >= 2019)
					yearList.add(tbFinancialyear);
			}

			if (yearList != null && !yearList.isEmpty()) {
				yearList.forEach(finYearTemp -> {
					try {
						finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
						faYearsList.add(finYearTemp);
					} catch (Exception ex) {
						throw new FrameworkException("Exception occured while fetching fianancial year details" + ex);
					}
				});
				Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
						Comparator.reverseOrder());
				Collections.sort(faYearsList, comparing);
			}

		} catch (Exception e) {
			logger.error("Error Ocurred while fetching details of financial year" + e);
		}
		return faYearsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.IAMasterService#findByIaId(java.lang.Long)
	 */
	@Override
	public IAMasterDto findByIaId(Long iAId) {
		IAMasterDto dto = new IAMasterDto();
		List<IAMasterDetailDto> detailDtoList = new ArrayList<>();
		try {
			IAMasterEntity entity = iaMasterRepository.findOne(iAId);
			BeanUtils.copyProperties(entity, dto);
			List<IAMasterDetailEntity> detailEnitity = iaDetailRepository.getIaDetail(entity);
			if (CollectionUtils.isNotEmpty(detailEnitity)) {
				detailEnitity.forEach(d -> {
					IAMasterDetailDto detDto = new IAMasterDetailDto();
					BeanUtils.copyProperties(d, detDto);
					if (d.getStatus().equals(MainetConstants.FlagA)) {
						detailDtoList.add(detDto);
					}
				});
			}
			dto.setIaDetailDto(detailDtoList);
		} catch (Exception e) {
			logger.error("Error Ocurred while fething Ia details by IaId  " + e);
		}

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#inactiveRemovedContactDetails(com
	 * .abm.mainet.sfac.dto.IAMasterDto, java.util.List)
	 */
	@Override
	@Transactional
	public void inactiveRemovedContactDetails(IAMasterDto iaMasterDto, List<Long> removedContDetIdsList) {
		if (removedContDetIdsList != null && !removedContDetIdsList.isEmpty()) {
			iaMasterRepository.iactiveContactDetByIds(removedContDetIdsList, iaMasterDto.getUpdatedBy());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#getIaALlocationYear(java.lang.
	 * String)
	 */
	@Override
	public Long getIaALlocationYear(Long iaId) {
		Long alcYear = iaMasterRepository.findByIAId(iaId);
		if (alcYear == null)
			return 0L;
		else
			return alcYear;

	}

	@Override
	public List<CommonMasterDto> getMasterDetail(Long orgid) {
		List<CommonMasterDto> CommonMasterDtoList = new ArrayList<>();
		Organisation org;
		try {
			if (orgid == null) {
				org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
				orgid = org.getOrgid();
			} else {
				org = orgService.getOrganisationById(orgid);
			}
			if (org.getOrgShortNm().equals(MainetConstants.Sfac.IA)) {
				List<IAMasterEntity> iaMasterEntity = iaMasterRepository.getIAListByOrgId(orgid);
				if (iaMasterEntity != null) {
					for (IAMasterEntity entity : iaMasterEntity) {
						CommonMasterDto dto = new CommonMasterDto();
						dto.setId(entity.getIAId());
						dto.setName(entity.getIAName());
						dto.setShortCode(MainetConstants.Sfac.IA);
						CommonMasterDtoList.add(dto);
					}
				}
			} else if (org.getOrgShortNm().equals(MainetConstants.Sfac.CBBO)) {
				List<CBBOMasterEntity> entity = cbboMasterRepository.getCBBODetailsByorgId(orgid);
				for (CBBOMasterEntity masterEntity : entity) {
					CommonMasterDto dto = new CommonMasterDto();
					dto.setId(masterEntity.getCbboId());
					dto.setName(masterEntity.getCbboName());
					dto.setIaName(masterEntity.getIAName());
					dto.setShortCode(MainetConstants.Sfac.CBBO);
					CommonMasterDtoList.add(dto);
				}
			} else if (org.getOrgShortNm().equals(MainetConstants.Sfac.FPO)) {
				List<FPOMasterEntity> mastEntity = fpoMasterRepository.getFpoDetByOrgId(orgid);
				for (FPOMasterEntity entity : mastEntity) {
					CommonMasterDto dto = new CommonMasterDto();
					dto.setId(entity.getFpoId());
					dto.setName(entity.getFpoName());
					dto.setIaName(entity.getIaName());
					dto.setShortCode(MainetConstants.Sfac.FPO);
					CommonMasterDtoList.add(dto);
				}
			}
		} catch (Exception ex) {
			logger.info("Could not find data for orgid " + orgid + " " + ex.getMessage());
		}
		return CommonMasterDtoList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#fetchNameById(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public String fetchNameById(Long orgTypeId, Long organizationNameId) {
		String name = null;
		Organisation org = orgService.getOrganisationById(orgTypeId);
		if (org.getOrgShortNm().equals(MainetConstants.Sfac.IA)) {
			name = iaMasterRepository.fetchNameById(organizationNameId);
		} else if (org.getOrgShortNm().equals(MainetConstants.Sfac.CBBO)) {
			name = cbboMasterRepository.fetchNameById(organizationNameId);
		} else if (org.getOrgShortNm().equals(MainetConstants.Sfac.FPO)) {
			name = fpoMasterRepository.fetchNameById(organizationNameId);
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.IAMasterService#findAllIaAssociatedWithCbbo(java.
	 * lang.Long)
	 */
	@Override
	public List<IAMasterDto> findAllIaAssociatedWithCbbo(Long empId) {
		List<IAMasterDto> masterDtoList = new ArrayList<>();
		try {
			logger.info("findAllIaAssociatedWithCbbo started");
			List<IAMasterEntity> entityList = iaMasterRepository.findAllIaAssociatedWithCbbo(empId);
			for (IAMasterEntity entity : entityList) {
				IAMasterDto masterDto = new IAMasterDto();
				BeanUtils.copyProperties(entity, masterDto);
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
	 * com.abm.mainet.sfac.service.IAMasterService#updateIaMasterDetail(com.abm.
	 * mainet.sfac.dto.IAMasterDto)
	 */
	@Override
	@Transactional
	public IAMasterDto updateIaMasterDetail(IAMasterDto mastDto) {
		try {
			logger.info("updateIaMasterDetail Started");
			IAMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = iaMasterRepository.save(masEntity);
			IAMasterHistEntity histEntity = new IAMasterHistEntity();
			histEntity.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			BeanUtils.copyProperties(masEntity, histEntity);

			List<IAMasterDetailHistory> detHistList = new ArrayList<>();
			for (IAMasterDetailEntity det : masEntity.getIaMasterDetail()) {
				IAMasterDetailHistory detHist = new IAMasterDetailHistory();
				BeanUtils.copyProperties(det, detHist);
				detHist.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				detHist.setMasterHistEntity(histEntity);
				detHist.setIadIdH(histEntity.getIaHId());
				detHistList.add(detHist);
			}
			histEntity.setDetailHist(detHistList);
			iaMastHistRepo.save(histEntity);
			//update isdeleted in employee table if inactive status
			if (StringUtils.isNotEmpty(masEntity.getActiveInactiveStatus())
					&& masEntity.getActiveInactiveStatus().equals(MainetConstants.FlagI)) {
				empService.updateIsDeletedFlagByMasId(MainetConstants.ONE, mastDto.getIAId(),
						UserSession.getCurrent().getEmployee().getEmpId(),null,mastDto.getOrgId());
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating IA Master details" + e);
			throw new FrameworkException("Error occured while updating IA Master details" + e);
		}
		logger.info("updateIaMasterDetail Ended");
		return mastDto;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.IAMasterService#checkIANameExist(java.lang.String)
	 */
	@Override
	public boolean checkIANameExist(String iAName) {
		Boolean result = false;
		try {
			result = iaMasterRepository.checkIaNameExist(iAName);
		} catch (Exception e) {
			logger.error("Error occured while checking IA name present or not in checkFpoNameExist()" + e);
		}
		return result;
	
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.IAMasterService#checkIaShortNmExist(java.lang.String)
	 */
	@Override
	public boolean checkIaShortNmExist(String iaShortName) {
		Boolean result = false;
		try {
			result = iaMasterRepository.checkIaShortNmExist(iaShortName);
		} catch (Exception e) {
			logger.error("Error occured while checking IA short name present or not in checkIaShortNmExist()" + e);
		}
		return result;
	
	}
}
