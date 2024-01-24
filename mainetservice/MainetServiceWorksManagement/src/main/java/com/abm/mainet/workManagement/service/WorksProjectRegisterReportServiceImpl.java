package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.workManagement.dao.WorkProjectRegisterReportDao;
import com.abm.mainet.workManagement.dto.WmsProjectStatusReportDetDto;

@Service
public class WorksProjectRegisterReportServiceImpl implements WorksProjectRegisterReportService {

	@Override
	@Transactional(readOnly = true)
	public List<WmsProjectStatusReportDetDto> findProjectRegisterSheetReport(Long schId, Long projId, Long workType,
			Long orgId) {

		List<WmsProjectStatusReportDetDto> wmsProjectStatusReportDetDtoList = new ArrayList<>();

		List<Object[]> estimateMastersEntity = ApplicationContextProvider.getApplicationContext()
				.getBean(WorkProjectRegisterReportDao.class)
				.findProjectRegisterSheetReport(schId, projId, workType, orgId);

		for (Object[] masterEntity : estimateMastersEntity) {

			WmsProjectStatusReportDetDto projectStatusReportDto = new WmsProjectStatusReportDetDto();
			BigInteger bi=(BigInteger)masterEntity[0];
			projectStatusReportDto.setOrgId(bi.longValue());
			projectStatusReportDto.setWmSchNameEng((String) masterEntity[1]);
			projectStatusReportDto.setWmSchNameReg((String) masterEntity[2]);
			projectStatusReportDto.setProjNameEng((String) masterEntity[3]);
			projectStatusReportDto.setProjNameReg((String) masterEntity[4]);
			BigInteger bi2=(BigInteger)masterEntity[5];
			projectStatusReportDto.setWorkType(bi2.longValue());
			projectStatusReportDto.setWorkName((String) masterEntity[6]);
			projectStatusReportDto.setWorkeEstimateNo((String) masterEntity[7]);
			projectStatusReportDto.setWorkEstimAmount((BigDecimal) masterEntity[8]);
			projectStatusReportDto.setTenderAmount((BigDecimal) masterEntity[9]);
			projectStatusReportDto.setStatus((String) masterEntity[10]);
			projectStatusReportDto.setBillReceivedTillDate((Date) masterEntity[11]);

			if (masterEntity[11] != null) {
				projectStatusReportDto.setBillReceivedDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(masterEntity[11]));
			}
			if (masterEntity[12] != null) {
				projectStatusReportDto.setPaymentisDoneTillDateDesc(masterEntity[12].toString());
			}
			
			projectStatusReportDto.setWorkType(bi2.longValue());

			if (masterEntity[5] != null) {
				projectStatusReportDto.setWorkTypeDesc(
						CommonMasterUtility.getCPDDescription(bi2.longValue(), MainetConstants.BLANK));

			}
			wmsProjectStatusReportDetDtoList.add(projectStatusReportDto);
		}
		return wmsProjectStatusReportDetDtoList;

	}
}