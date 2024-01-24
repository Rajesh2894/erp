package com.abm.mainet.adh.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author cherupelli.srikanth
 * @since 30 september 2019
 */
@Service
public class ADHCommonServiceImpl implements ADHCommonService {

    @Autowired
    private ILicenseValidityMasterService licenseValidityMasterService;

    @Autowired
    private TbFinancialyearService financialyearService;

    @Override
    public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, Long licType) {

        Long licenseMaxTenureDays = 0l;
        Date currentDate = new Date();
        if (licToDate != null && Utility.compareDate(new Date(), licToDate)) {
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

                                Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                                        date));

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
                                Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                                        date));

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
                    int currentYear = Integer.valueOf(Year.now().toString());
                    LocalDate currLocalDate = LocalDate.now();
                    LocalDate with = currLocalDate.with(lastDayOfYear());

                    licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
                            Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
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
