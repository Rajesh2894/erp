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
import com.abm.mainet.workManagement.dao.WorksDepositRegisterReportDao;
import com.abm.mainet.workManagement.dto.WorksDepositRegisterReportDto;

@Service
public class WorksDepositRegisterReportServiceImpl implements WorksDepositRegisterReportService {

	@Override
	@Transactional(readOnly = true)
	public List<WorksDepositRegisterReportDto> getWorksDepositRegisterReport(Long orgId, Date fromDate, Date toDate) {
		List<WorksDepositRegisterReportDto> worksDeductionReportDtoList = new ArrayList<>();

		List<Object[]> depositRegisterReport = ApplicationContextProvider.getApplicationContext()
				.getBean(WorksDepositRegisterReportDao.class).getWorksDepositRegisterReport(orgId, fromDate, toDate);
		WorksDepositRegisterReportDto worksDepositRegisterReportDto = null;
		BigDecimal total=new BigDecimal(0);
		for (Object[] depositRegister : depositRegisterReport) {

			worksDepositRegisterReportDto = new WorksDepositRegisterReportDto();

			worksDepositRegisterReportDto.setVmVendorid((Long) depositRegister[0]);
			worksDepositRegisterReportDto.setVmVendorname((String) depositRegister[1]);
			worksDepositRegisterReportDto.setProjectId((Long) depositRegister[2]);
			worksDepositRegisterReportDto.setProjNameEng((String) depositRegister[3]);
			worksDepositRegisterReportDto.setProjNameReg((String) depositRegister[4]);

			if (depositRegister[5] != null) {
				worksDepositRegisterReportDto
						.setProjStartDateDesc(CommonUtility.dateToString((Date) depositRegister[5]));
			}
			if (depositRegister[6] != null) {
				worksDepositRegisterReportDto.setProjEndDateDesc(CommonUtility.dateToString((Date) depositRegister[6]));
			}
			
			worksDepositRegisterReportDto.setWorkcode((String) depositRegister[8]);
			
			worksDepositRegisterReportDto.setWorkName((String) depositRegister[9]);

			/* REMOVE AS PER SUDA UAT */
			/*
			 * worksDepositRegisterReportDto.setWorkStartDateDesc((String)
			 * depositRegister[9]);
			 * worksDepositRegisterReportDto.setWorkEndDateDesc((String)
			 * depositRegister[10]);
			 */
			worksDepositRegisterReportDto.setWorkStatus((String) depositRegister[12]);
			worksDepositRegisterReportDto.setCpdDesc((String) depositRegister[13]);
			worksDepositRegisterReportDto.setCpdDescMar((String) depositRegister[14]);
			if (depositRegister[15] != null) {

				worksDepositRegisterReportDto
						.setDepDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(depositRegister[15]));

			}
			worksDepositRegisterReportDto.setDepAmount((BigDecimal) depositRegister[16]);
			
			/*
			 * worksDepositRegisterReportDto.setRmRcptno((Long)depositRegister[17]);
			 * worksDepositRegisterReportDto.setRmDate((Date) depositRegister[18]);
			 */
			total=total.add((BigDecimal) depositRegister[16]);
			worksDeductionReportDtoList.add(worksDepositRegisterReportDto);
		}
		if(!worksDeductionReportDtoList.isEmpty())
			worksDeductionReportDtoList.get(0).setTotalSum(total);
		return worksDeductionReportDtoList;
	}
}