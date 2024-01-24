package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.workManagement.dao.WorksDeductionReportDao;
import com.abm.mainet.workManagement.dto.WorksDeductionReportDto;

@Service
public class WorksDeductionReportServiceImpl implements WorksDeductionReportService {

    @Override
    @Transactional(readOnly = true)
    public List<WorksDeductionReportDto> getWorksDeductionReport(Long orgId, Date fromDate, Date toDate) {
        List<WorksDeductionReportDto> worksDeductionReportDtoList = new ArrayList<>();

        List<Object[]> deductionReport = ApplicationContextProvider.getApplicationContext().getBean(WorksDeductionReportDao.class)
                .getWorksDeductionReport(orgId,
                        fromDate, toDate);
        WorksDeductionReportDto worksDeductionReportDto = null;
        for (Object[] deduction : deductionReport) {

            worksDeductionReportDto = new WorksDeductionReportDto();

            worksDeductionReportDto.setVmVendorid((Long) deduction[0]);
            worksDeductionReportDto.setVmVendorname((String) deduction[1]);
            worksDeductionReportDto.setProjectId((Long) deduction[2]);
            worksDeductionReportDto.setProjNameEng((String) deduction[3]);
            worksDeductionReportDto.setProjNameReg((String) deduction[4]);
            if (deduction[5] != null) {
                worksDeductionReportDto.setProjStartDate(CommonUtility.dateToString((Date) deduction[5]));
            }
            if (deduction[6] != null) {
                worksDeductionReportDto.setProjEndDate(CommonUtility.dateToString((Date) deduction[6]));
            }
            worksDeductionReportDto.setWorkcode((String) deduction[7]);
            worksDeductionReportDto.setWorkName((String) deduction[8]);
            if (deduction[9] != null) {

                worksDeductionReportDto.setContractFromDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(deduction[9]));
            }
            if (deduction[10] != null) {

                worksDeductionReportDto.setContractToDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(deduction[10]));
            }
            worksDeductionReportDto.setTaxId((Long) deduction[11]);
            worksDeductionReportDto.setTaxDesc((String) deduction[12]);

            if (deduction[13] != null) {

                worksDeductionReportDto.setBmDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(deduction[13]));
            }
            worksDeductionReportDto.setMbTaxValue((BigDecimal) deduction[14]);
            worksDeductionReportDto.setRaCode((String) deduction[15]);

            worksDeductionReportDtoList.add(worksDeductionReportDto);
        }

        return worksDeductionReportDtoList;
    }
}