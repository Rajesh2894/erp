
package com.abm.mainet.adh.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.dao.IAdvertisementDataEntryDao;
import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.NewAdvertisementApplication;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationDet;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.repository.AdvertiserMasterRepository;
import com.abm.mainet.adh.repository.NewAdvertisementApplicationRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author Anwarul.Hassan
 * @since 11-Oct-2019
 */
@Service
public class AdvertisementDataEntryServiceImpl implements IAdvertisementDataEntryService {
    private static final Logger LOGGER = Logger.getLogger(AdvertisementDataEntryServiceImpl.class);
    @Autowired
    private NewAdvertisementApplicationRepository newAdvertisementRepository;
    @Autowired
    private ILocationMasService locationMasService;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    AdvertiserMasterRepository advertiserMasterRepository;
    
    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;
    
    @Autowired
    private ILicenseValidityMasterService licenseValidityMasterService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#getAdvertisementDataByOrgId(java.lang.Long)
     */
    @Override
    public List<NewAdvertisementApplicationDto> getAdvertisementDetailsByOrgId(Long orgId) {
        List<NewAdvertisementApplicationDto> applicationDtoList = new ArrayList<>();
        try {
            List<NewAdvertisementApplication> entityList = newAdvertisementRepository
                    .findAdvertisementDetailsByOrgId(orgId);
            if (entityList != null) {
                entityList.forEach(entity -> {
                    NewAdvertisementApplicationDto applicationDto = new NewAdvertisementApplicationDto();
                    AdvertiserMasterEntity ame = advertiserMasterRepository.findByOrgIdAndAgencyId(orgId, entity.getAgencyId());
                    if (ame != null && ame.getAgencyName() != null) {
                        applicationDto.setAgencyName(ame.getAgencyName());
                    }
                    if (entity.getAdhStatus().equals("A")) {
                        applicationDto.setAdhStatusDesc("Active");
                    }
                    if (entity.getAdhStatus().equals("T")) {
                        applicationDto.setAdhStatusDesc("Terminate");
                    }
                    if (entity.getAdhStatus().equals("C")) {
                        applicationDto.setAdhStatusDesc("Close");
                    }
                    BeanUtils.copyProperties(entity, applicationDto);
                    applicationDtoList.add(applicationDto);
                });
            }
        } catch (Exception exception) {
            LOGGER.error("Exception Occured While fetching the Advertisement Data by orgId", exception);
            throw new FrameworkException("Exception Occured While fetching the Advertisement Data by orgId", exception);
        }
        return applicationDtoList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#saveAdvertisementData(com.abm.mainet.adh.dto.
     * NewAdvertisementApplicationDto)
     */
    @Override
    @Transactional
    public NewAdvertisementApplicationDto saveAdvertisementData(@RequestBody NewAdvertisementApplicationDto applicationDto) {
        NewAdvertisementApplication applicationEntity = mapDtoToEntity(applicationDto);

        try {
            ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
                            applicationDto.getOrgId());
            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                    applicationDto.getLocId(), serviceMaster.getTbDepartment().getDpDeptid());
            if(applicationEntity.getAdhId() == null) {
            	 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                 Date newDate = new Date();
                 Long empId = UserSession.getCurrent().getEmployee().getEmpId();
                 applicationDto.getNewAdvertisementReqDto().setLangId((long) UserSession.getCurrent().getLanguageId());
                 applicationDto.getNewAdvertisementReqDto().setOrgId(orgId);
                 applicationDto.getNewAdvertisementReqDto().getAdvertisementDto().setCreatedBy(empId);
                 applicationDto.getNewAdvertisementReqDto().getAdvertisementDto().setCreatedDate(newDate);
            String advertisementLicenseNo = newAdvApplicationService.generateNewAdvertisementLicenseNumber(
                    UserSession.getCurrent().getOrganisation(),
                    serviceMaster.getTbDepartment().getDpDeptid());
            applicationEntity.setLicenseNo(advertisementLicenseNo);
            applicationDto.setLicenseNo(advertisementLicenseNo);
            RequestDTO requestDto = setApplicantRequestData(applicationDto.getNewAdvertisementReqDto(), serviceMaster);
            Long applicationId = null;
            if (applicationDto.getApmApplicationId() == null) {
            applicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
                    .createApplication(requestDto);
            applicationEntity.setApmApplicationId(applicationId);
            }
            }
            if (wzMapping.getCodIdOperLevel1() != null) {
                applicationEntity.setAdhZone1(wzMapping.getCodIdOperLevel1());
            }
            if (wzMapping.getCodIdOperLevel2() != null) {
                applicationEntity.setAdhZone2(wzMapping.getCodIdOperLevel2());
            }
            if (wzMapping.getCodIdOperLevel3() != null) {
                applicationEntity.setAdhZone3(wzMapping.getCodIdOperLevel3());
            }
            if (wzMapping.getCodIdOperLevel4() != null) {
                applicationEntity.setAdhZone4(wzMapping.getCodIdOperLevel4());
            }
            if (wzMapping.getCodIdOperLevel4() != null) {
                applicationEntity.setAdhZone4(wzMapping.getCodIdOperLevel4());
            }
            if (wzMapping.getCodIdOperLevel5() != null) {
                applicationEntity.setAdhZone5(wzMapping.getCodIdOperLevel5());
            }

            newAdvertisementRepository.save(applicationEntity);
        } catch (Exception exception) {
            LOGGER.error("Exception Occured While saving the Advertisement Data", exception);
            throw new FrameworkException("Exception Occured While saving the Advertisement Data", exception);
        }
        return applicationDto;
    }

    private NewAdvertisementApplication mapDtoToEntity(NewAdvertisementApplicationDto applicationDto) {
        NewAdvertisementApplication applicationEntity = new NewAdvertisementApplication();
        List<NewAdvertisementApplicationDet> advertisementApplicationDets = new ArrayList<>();
        BeanUtils.copyProperties(applicationDto, applicationEntity);
        if (CollectionUtils.isNotEmpty(applicationDto.getNewAdvertDetDtos())) {
            applicationDto.getNewAdvertDetDtos().forEach(det -> {
                NewAdvertisementApplicationDet detDto = new NewAdvertisementApplicationDet();
                BeanUtils.copyProperties(det, detDto);
                detDto.setNewAdvertisement(applicationEntity);
                advertisementApplicationDets.add(detDto);
            });
        }
        applicationEntity.setNewAdvertisetDetails(advertisementApplicationDets);
        return applicationEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#findAgencyCategoryByOrgId(java.lang.Long)
     */
    @Override
    public NewAdvertisementApplicationDto findAgencyCategoryByOrgId(Long orgId) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#searchAdvtDataEntry(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<NewAdvertisementApplicationDto> searchAdvtDataEntry(Long orgId, Long agencyId, Long licenseType, String adhStatus,
            Long locId,Date licenFromDate,Date licenToDate) {
    	java.sql.Date licenseFromDate=UtilityService.getSQLDate(licenFromDate);
    	java.sql.Date  licenseToDate=UtilityService.getSQLDate(licenToDate);
        List<NewAdvertisementApplicationDto> applicationDtoList = new ArrayList<>();
        try {
            List<NewAdvertisementApplication> entityList = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAdvertisementDataEntryDao.class)
                    .searchDataEntry(orgId, agencyId, licenseType, adhStatus, locId,licenseFromDate,licenseToDate);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    NewAdvertisementApplicationDto applicationDto = new NewAdvertisementApplicationDto();
                    BeanUtils.copyProperties(entity, applicationDto);
                    if (entity.getAdhStatus().equals("A")) {
                        applicationDto.setAdhStatusDesc("Active");
                    }
                    if (entity.getAdhStatus().equals("T")) {
                        applicationDto.setAdhStatusDesc("Terminate");
                    }
                    if (entity.getAdhStatus().equals("C")) {
                        applicationDto.setAdhStatusDesc("Close");
                    }
                    applicationDtoList.add(applicationDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while fetching Advertisement data", exception);
        }
        return applicationDtoList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#getAdvertisementDetailsByOrgIdAndAhdId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional
    public NewAdvertisementApplicationDto getAdvertisementDetailsByOrgIdAndAdhId(Long adhId, Long orgId) {
        NewAdvertisementApplicationDto applicationDto = null;
        List<NewAdvertisementApplicationDetDto> applicationDetList = new ArrayList<>();
        try {
            NewAdvertisementApplication entity = newAdvertisementRepository.findAdvertisementDetailsByOrgIdAndAdhId(adhId, orgId);
            applicationDto = new NewAdvertisementApplicationDto();
            BeanUtils.copyProperties(entity, applicationDto);
            entity.getNewAdvertisetDetails().forEach(detEntity -> {
                NewAdvertisementApplicationDetDto det = new NewAdvertisementApplicationDetDto();
                BeanUtils.copyProperties(detEntity, det);
                applicationDetList.add(det);
            });

            applicationDto.setNewAdvertDetDtos(applicationDetList);
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertisement data ", exception);
        }
        return applicationDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IAdvertisementDataEntryService#getLocationByOrgIdAndLocId(java.lang.Long, java.lang.Long)
     */
    @Override
    public Long getLocationByOrgIdAndLocId(Long orgId, Long locationId) {
        // TODO Auto-generated method stub
        return null;
    }

    // Defect #79106
    @Override
    @Transactional
    public void deleteAdvertisementId(List<Long> removeAdverId) {
        newAdvertisementRepository.deleteAdvetisementdetailByIds(removeAdverId);

    }
    
    private RequestDTO setApplicantRequestData(NewAdvertisementReqDto advertisementReqDto,
            ServiceMaster serviceMaster) {

        RequestDTO requestDto = new RequestDTO();
        requestDto.setTitleId(advertisementReqDto.getApplicantDetailDTO().getApplicantTitle());
        requestDto.setfName(advertisementReqDto.getApplicantDetailDTO().getApplicantFirstName());
        requestDto.setmName(advertisementReqDto.getApplicantDetailDTO().getApplicantMiddleName());
        requestDto.setlName(advertisementReqDto.getApplicantDetailDTO().getApplicantLastName());
        requestDto.setGender(advertisementReqDto.getApplicantDetailDTO().getGender());
        requestDto.setMobileNo(advertisementReqDto.getApplicantDetailDTO().getMobileNo());
        requestDto.setEmail(advertisementReqDto.getApplicantDetailDTO().getEmailId());
        requestDto.setFlatBuildingNo(advertisementReqDto.getApplicantDetailDTO().getFlatBuildingNo());
        requestDto.setBldgName(advertisementReqDto.getApplicantDetailDTO().getBuildingName());
        requestDto.setRoadName(advertisementReqDto.getApplicantDetailDTO().getRoadName());
        requestDto.setBlockName(advertisementReqDto.getApplicantDetailDTO().getBlockName());
        requestDto.setAreaName(advertisementReqDto.getApplicantDetailDTO().getAreaName());
        requestDto.setCityName(advertisementReqDto.getApplicantDetailDTO().getVillageTownSub());
        requestDto.setPincodeNo(Long.valueOf(advertisementReqDto.getApplicantDetailDTO().getPinCode()));
        if ((StringUtils.isNotEmpty(advertisementReqDto.getApplicantDetailDTO().getAadharNo()))
                && (advertisementReqDto.getApplicantDetailDTO().getAadharNo().length() > 0)) {
            requestDto.setUid(Long.valueOf(advertisementReqDto.getApplicantDetailDTO().getAadharNo()
                    .replaceAll(MainetConstants.AdvertisingAndHoarding.REPLACE_NO, MainetConstants.BLANK)));
        }

        requestDto.setServiceId(serviceMaster.getSmServiceId());
        requestDto.setUserId(advertisementReqDto.getAdvertisementDto().getCreatedBy());
        requestDto.setOrgId(advertisementReqDto.getOrgId());
        requestDto.setLangId((long) advertisementReqDto.getLangId());
        requestDto.setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
        requestDto.setFloor(advertisementReqDto.getApplicantDetailDTO().getFloorNo());
        requestDto.setWing(advertisementReqDto.getApplicantDetailDTO().getWing());
        requestDto.setHouseComplexName(advertisementReqDto.getApplicantDetailDTO().getHousingComplexName());
        requestDto.setBplNo(advertisementReqDto.getApplicantDetailDTO().getBplNo());
        if (advertisementReqDto.isFree()) {
            requestDto.setPayStatus(MainetConstants.FlagF);
        }
        // requestDto.setPayAmount(1D);
        return requestDto;
    }
    
    @Override
    public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, Long licType) {

        Long licenseMaxTenureDays = 0l;
        Date currentDate = new Date();
        if (licToDate != null) {
            currentDate = licToDate;
        }

        List<LicenseValidityMasterDto> licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId,
                deptId, serviceId, MainetConstants.ZERO_LONG,licType);
        if (CollectionUtils.isNotEmpty(licValidityMster)) {
            LicenseValidityMasterDto licValMasterDto = licValidityMster.get(0);
            Organisation organisationById = ApplicationContextProvider.getApplicationContext()
                    .getBean(IOrganisationService.class).getOrganisationById(orgId);
            LookUp dependsOnLookUp = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

            LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
                    organisationById);

            Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
            if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
                licenseMaxTenureDays = tenure - 1;
            }
            if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
                licenseMaxTenureDays = 1l;
            }
            if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
                int currentYear = Integer.valueOf(Year.now().toString());
                Month monthObject = Month.from(LocalDate.now());
                int month = monthObject.getValue();
                licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
                if (tenure > 1) {
                    for (int i = 2; i <= tenure; i++) {
                        licenseMaxTenureDays = licenseMaxTenureDays
                                + Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
                        if (month == 12) {
                            month = 0;
                            currentYear++;
                        }
                    }
                }
            }

            if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
                if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
                    int month = 0;
                    int currentYear = Integer.valueOf(Year.now().toString());
                    TbFinancialyear financialYear;
                    LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int monthValue = currLocalDate.getMonthValue();
                    int currentMonthValue = currLocalDate.getMonthValue();

                    if (monthValue > 3 && monthValue <= 12) {

                        for (int i = monthValue; i <= 15; i++) {

                            if (i == currentMonthValue) {
                                LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
                                Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

                               /* Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                                        date));*/
                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                LocalDate currentSelectedDate= currentDate.toInstant().atZone(defaultZoneId).toLocalDate();
                                LocalDate LastDateOfYear = date.toInstant().atZone(defaultZoneId).toLocalDate();
                                Long valueOf = ChronoUnit.DAYS.between(currentSelectedDate, LastDateOfYear);

                                licenseMaxTenureDays = valueOf;
                                monthValue++;
                            } else {
                                if (monthValue <= 12) {
                                    licenseMaxTenureDays = licenseMaxTenureDays
                                            + Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
                                    monthValue++;
                                } else if (monthValue > 12) {
                                    licenseMaxTenureDays = licenseMaxTenureDays
                                            + Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
                                    monthValue++;
                                    --currentYear;
                                }

                            }

                        }

                    } else {
                        for (int i = monthValue; i <= 3; i++) {
                            if (i == currentMonthValue) {
                                LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
                                Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                /*Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                                        date));*/
                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                LocalDate currentSelectedDate= currentDate.toInstant().atZone(defaultZoneId).toLocalDate();
                                LocalDate LastDateOfYear = date.toInstant().atZone(defaultZoneId).toLocalDate();
                                Long valueOf = ChronoUnit.DAYS.between(currentSelectedDate, LastDateOfYear);
                                licenseMaxTenureDays = valueOf;
                                monthValue++;
                                // Long currMonthDays = Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
                            } else {
                                licenseMaxTenureDays = licenseMaxTenureDays
                                        + Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
                                monthValue++;
                            }

                        }
                    }
                    if (tenure > 1) {
                        for (int i = 2; i <= tenure; i++) {
                            monthValue = 4;
                            currentYear++;
                            month = 0;
                            for (int j = monthValue; j <= 15; j++) {
                                if (monthValue <= 12) {
                                    licenseMaxTenureDays = licenseMaxTenureDays
                                            + Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
                                    monthValue++;
                                } else if (monthValue > 12) {
                                    licenseMaxTenureDays = licenseMaxTenureDays
                                            + Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
                                    monthValue++;
                                    --currentYear;
                                }
                            }
                        }
                    }
                }
                if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
                	ZoneId defaultZoneId = ZoneId.systemDefault();
                    //int currentYear = Integer.valueOf(Year.now().toString());
                	int currentYear = currentDate.getYear();
                    LocalDate currLocalDate = LocalDate.now();
                    //LocalDate with = currLocalDate.with(lastDayOfYear());
                    LocalDate with = currentDate.toInstant().atZone(defaultZoneId).toLocalDate().with(lastDayOfYear());

                    /*licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                            Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));*/
                    
                    LocalDate currentSelectedDate= currentDate.toInstant().atZone(defaultZoneId).toLocalDate();
                    LocalDate LastDateOfYear = with.atStartOfDay(ZoneId.systemDefault()).toInstant().atZone(defaultZoneId).toLocalDate();
                    licenseMaxTenureDays = ChronoUnit.DAYS.between(currentSelectedDate, LastDateOfYear);
                    if (tenure > 1) {

                        for (int i = 2; i <= tenure; i++) {
                            Year year = Year.of(++currentYear);
                            licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
                        }

                    }
                }
                if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
                    LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Instant instant1 = LocalDate
                            .of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
                                    currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth())
                            .atStartOfDay(ZoneId.systemDefault()).toInstant();
                    Date from1 = Date.from(instant1);
                    licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));

                }
            }
        }
        return licenseMaxTenureDays;

    }

}
