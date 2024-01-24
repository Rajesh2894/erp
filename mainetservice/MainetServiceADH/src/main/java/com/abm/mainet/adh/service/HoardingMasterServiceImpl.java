package com.abm.mainet.adh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.adh.dao.IHoardingMasterDao;
import com.abm.mainet.adh.domain.HoardingBookingEntity;
import com.abm.mainet.adh.domain.HoardingMasterEntity;
import com.abm.mainet.adh.domain.HoardingMasterHistoryEntity;
import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.repository.HoardingBookingRepository;
import com.abm.mainet.adh.repository.HoardingMasterRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class HoardingMasterServiceImpl implements IHoardingMasterService {

    @Autowired
    private HoardingMasterRepository hoardingMasterRepository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ILocationMasService locationMasService;
    @Autowired
     private HoardingBookingRepository hoardingBookingRepository;
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.INewHoardingApplicationService# getHoardingNumberListByOrgId(java.lang.Long)
     */
    @Override
    @Transactional
    public List<String> getHoardingNumberListByOrgId(Long orgId) {
        return hoardingMasterRepository.findHoardingNumberByOrgId(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterService#getAllHoardingNumberByOrgId (java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<HoardingMasterDto> getAllHoardingNumberByOrgId(Long orgId) {
        List<HoardingMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<HoardingMasterEntity> entityList = hoardingMasterRepository.getAllHoardingNumberByOrgId(orgId);
            if (entityList != null) {
                entityList.forEach(entity -> {
                    HoardingMasterDto masterDto = new HoardingMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Number by orgId", exception);
        }
        return masterDtoList;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterService# getHoardingDetailsByOrgIdAndHoardingNo(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public HoardingMasterDto getHoardingDetailsByOrgIdAndHoardingNo(Long orgId, String hoardingNumber) {
        HoardingMasterDto masterDto = null;
        try {
            HoardingMasterEntity entity = hoardingMasterRepository.findDetailsByOrgIdAndHoardingNo(orgId,
                    hoardingNumber);
            if (entity != null) {
                masterDto = new HoardingMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException(
                    "Error Occured While fetching the Hoarding Details by orgId and Hoarding Number", exception);
        }
        return masterDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterSevice#saveHoardingMasterData(com. abm.mainet.adh.dto.HoardingMasterDto)
     */
    @Override
    @Transactional
    public HoardingMasterDto saveHoardingMasterData(HoardingMasterDto masterDto) {
        HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();
        try {
            HoardingMasterEntity hoardingMasterEntity = new HoardingMasterEntity();

            ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                    .getBean(ServiceMasterService.class).getServiceMasterByShortCode("ADH", masterDto.getOrgId());
            if(masterDto.getLocationId() != null) {
	            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
	                    masterDto.getLocationId(), serviceMaster.getTbDepartment().getDpDeptid());
	            if (wzMapping != null) {
	                if (wzMapping.getCodIdOperLevel1() != null) {
	                    masterDto.setHoardingZone1(wzMapping.getCodIdOperLevel1());
	                }
	                if (wzMapping.getCodIdOperLevel2() != null) {
	                    masterDto.setHoardingZone2(wzMapping.getCodIdOperLevel2());
	                }
	                if (wzMapping.getCodIdOperLevel3() != null) {
	                    masterDto.setHoardingZone3(wzMapping.getCodIdOperLevel3());
	                }
	                if (wzMapping.getCodIdOperLevel4() != null) {
	                    masterDto.setHoardingZone4(wzMapping.getCodIdOperLevel4());
	                }
	                if (wzMapping.getCodIdOperLevel5() != null) {
	                    masterDto.setHoardingZone5(wzMapping.getCodIdOperLevel5());
	                }
	            }
            }
            BeanUtils.copyProperties(masterDto, hoardingMasterEntity);

            String hoardingNumber = generateHoardingNumber(masterDto);
            hoardingMasterEntity.setHoardingNumber(hoardingNumber);
            hoardingMasterEntity.setHoardingArea(
                    hoardingMasterEntity.getHoardingLength().multiply(hoardingMasterEntity.getHoardingHeight()));
            HoardingMasterEntity entity = hoardingMasterRepository.save(hoardingMasterEntity);
            HoardingMasterHistoryEntity history = new HoardingMasterHistoryEntity();
            history.sethStatus(MainetConstants.FlagA);
            auditService.createHistory(hoardingMasterEntity, history);
            hoardingMasterDto.setHoardingNumber(entity.getHoardingNumber());
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while saving the hoarding master data", exception);
        }
        return hoardingMasterDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterService#updateHoardingMasterData( com.abm.mainet.adh.dto.HoardingMasterDto)
     */
    @Override
    @Transactional
    public HoardingMasterDto updateHoardingMasterData(HoardingMasterDto masterDto) {
        try {
            HoardingMasterEntity hoardingMasterEntity = new HoardingMasterEntity();
            ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
                    .getBean(ServiceMasterService.class).getServiceMasterByShortCode("ADH", masterDto.getOrgId());
            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                    masterDto.getLocationId(), serviceMaster.getTbDepartment().getDpDeptid());
            if (wzMapping != null) {
                if (wzMapping.getCodIdOperLevel1() != null) {
                    masterDto.setHoardingZone1(wzMapping.getCodIdOperLevel1());
                }
                if (wzMapping.getCodIdOperLevel2() != null) {
                    masterDto.setHoardingZone2(wzMapping.getCodIdOperLevel2());
                }
                if (wzMapping.getCodIdOperLevel3() != null) {
                    masterDto.setHoardingZone3(wzMapping.getCodIdOperLevel3());
                }
                if (wzMapping.getCodIdOperLevel4() != null) {
                    masterDto.setHoardingZone4(wzMapping.getCodIdOperLevel4());
                }
                if (wzMapping.getCodIdOperLevel5() != null) {
                    masterDto.setHoardingZone5(wzMapping.getCodIdOperLevel5());
                }
            }
            BeanUtils.copyProperties(masterDto, hoardingMasterEntity);
            hoardingMasterEntity.setHoardingArea(
                    hoardingMasterEntity.getHoardingLength().multiply(hoardingMasterEntity.getHoardingHeight()));
            hoardingMasterRepository.save(hoardingMasterEntity);
            HoardingMasterHistoryEntity history = new HoardingMasterHistoryEntity();
            history.sethStatus(MainetConstants.FlagU);
            auditService.createHistory(hoardingMasterEntity, history);
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while updating the hoarding master data", exception);
        }
        return masterDto;
    }

    private String generateHoardingNumber(HoardingMasterDto masterDto) {

        // get financial by date
        FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());

        // get financial year formate
        String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

        // gerenerate sequence
        final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.AdvertisingAndHoarding.HOARDING_MASTER,
                        MainetConstants.AdvertisingAndHoarding.TB_HOARDING_MAS,
                        MainetConstants.AdvertisingAndHoarding.HRD_NO, masterDto.getOrgId(), MainetConstants.FlagC,
                        financiaYear.getFaYear());

        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
                .getOrganisationById(masterDto.getOrgId());
        StringBuilder hoardingNo = new StringBuilder();
        hoardingNo.append(org.getOrgShortNm()).append(MainetConstants.WINDOWS_SLASH);
        if (masterDto.getHoardingTypeId1() != null) {
            LookUp hierarchicalLookUp1 = CommonMasterUtility.getHierarchicalLookUp(masterDto.getHoardingTypeId1(),
                    masterDto.getOrgId());
            hoardingNo.append(hierarchicalLookUp1.getLookUpCode()).append(MainetConstants.WINDOWS_SLASH);
        }
        if (masterDto.getHoardingTypeId2() != null) {
            LookUp hierarchicalLookUp2 = CommonMasterUtility.getHierarchicalLookUp(masterDto.getHoardingTypeId2(),
                    masterDto.getOrgId());
            hoardingNo.append(hierarchicalLookUp2.getLookUpCode()).append(MainetConstants.WINDOWS_SLASH);
        }
        if (masterDto.getHoardingTypeId3() != null) {
            LookUp hierarchicalLookUp3 = CommonMasterUtility.getHierarchicalLookUp(masterDto.getHoardingTypeId3(),
                    masterDto.getOrgId());
            hoardingNo.append(hierarchicalLookUp3.getLookUpCode()).append(MainetConstants.WINDOWS_SLASH);
        }
        if (masterDto.getHoardingTypeId4() != null) {
            LookUp hierarchicalLookUp4 = CommonMasterUtility.getHierarchicalLookUp(masterDto.getHoardingTypeId4(),
                    masterDto.getOrgId());
            hoardingNo.append(hierarchicalLookUp4.getLookUpCode()).append(MainetConstants.WINDOWS_SLASH);
        }
        if (masterDto.getHoardingTypeId5() != null) {
            LookUp hierarchicalLookUp5 = CommonMasterUtility.getHierarchicalLookUp(masterDto.getHoardingTypeId5(),
                    masterDto.getOrgId());
        }
        String hoardingNumber = hoardingNo.toString()
                + String.format(MainetConstants.CommonMasterUi.PADDING_SIX, sequence);
        return hoardingNumber;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterSevice#getByOrgIdAndHoardingId(java .lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public HoardingMasterDto getByOrgIdAndHoardingId(Long orgId, Long hoardingId) {
        HoardingMasterDto masterDto = null;
        try {
            HoardingMasterEntity entity = hoardingMasterRepository.findByOrgIdAndHoardingId(orgId, hoardingId);
            if (entity != null) {
                masterDto = new HoardingMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master by hoardingId", exception);
        }
        return masterDto;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId) {
    	List<HoardingBookingDto> bookingDtoList = new ArrayList<>();
        try {
            List<HoardingBookingEntity> entityList = hoardingBookingRepository.fetchHoardingDetailsByOrgId(orgId);
            if (entityList != null) {
            	entityList.forEach(entity-> {
            		HoardingBookingDto bookingDto = new HoardingBookingDto();
            		Long hoardingId=entity.getHoardingMasterEntity().getHoardingId();
                    BeanUtils.copyProperties(entityList, bookingDto);
                    bookingDto.setHoardingId(hoardingId);
                    bookingDtoList.add(bookingDto);
            	});
            	
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master by hoardingId", exception);
        }
        return bookingDtoList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterService#getByOrgIdAndHoardingIdList(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<HoardingMasterDto> getByOrgIdAndHoardingIdList(Long orgId, Long hoardingId) {
        List<HoardingMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<HoardingMasterEntity> entityList = hoardingMasterRepository.findByOrgIdAndHoardingIdList(orgId, hoardingId);
            if (entityList != null) {
                entityList.forEach(entity -> {
                    HoardingMasterDto masterDto = new HoardingMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }

        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master by hoardingId", exception);
        }
        return masterDtoList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IHoardingMasterSevice#searchHoardingMasterData( java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<HoardingMasterDto> searchHoardingMasterData(Long orgId, String hoardingNumber, Long hoardingStatus,
            Long hoardingType, Long hoardingSubType, Long hoardingSubType3, Long hoardingSubType4,
            Long hoardingSubType5, Long hoardingLocation) {
        List<HoardingMasterDto> masterDtoList = new ArrayList<>();

        try {
            List<HoardingMasterEntity> entityList = ApplicationContextProvider.getApplicationContext()
                    .getBean(IHoardingMasterDao.class).searchHoardingMasterData(orgId, hoardingNumber, hoardingStatus,
                            hoardingType, hoardingSubType, hoardingSubType3, hoardingSubType4, hoardingSubType5,
                            hoardingLocation);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    HoardingMasterDto masterDto = new HoardingMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDto.setHrdFmtDate(Utility.dateToString(entity.getHoardingRegDate()));
                    // masterDto.setHoardingTypeDesc(entity.getHoardingTypeId1().toString());
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while fetching Hoarding Master data", exception);
        }
        return masterDtoList;
    }

    @Override
    @Transactional
    public List<String[]> getHoardingNumberAndIdListByOrgId(Long orgId) {
        return hoardingMasterRepository.findHoardingNumberAndIdByOrgId(orgId);
    }

	@Override
	 @Transactional
	public List<HoardingBookingDto> getHoardingBookingDetails(Long adhId,Long orgId) {
		List<HoardingBookingDto> hoardingDto = new ArrayList<>();
		List<HoardingBookingEntity> entityList = hoardingBookingRepository.findHoardingDetailsByAdhIdAndOrgId(adhId, orgId);
		if(CollectionUtils.isNotEmpty(entityList)) {
			entityList.forEach(entity -> {
				HoardingBookingDto masterDto = new HoardingBookingDto();
				Long hoardingId=entity.getHoardingMasterEntity().getHoardingId();
                BeanUtils.copyProperties(entity, masterDto);
                masterDto.setHoardingId(hoardingId);
                hoardingDto.add(masterDto);
            });
		}
		return hoardingDto;
	}
}
