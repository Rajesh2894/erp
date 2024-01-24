package com.abm.mainet.adh.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.repository.ADHReportRepository;
@Service
public class ADHReportServiceImpl implements ADHReportService {

	@Resource
	ADHReportRepository adhReportRepository;
	
	@Override
	public AdvertiserMasterDto findAdvertiserRegister(String fromDate, String toDate, Long orgId) {
		AdvertiserMasterDto dto = new AdvertiserMasterDto(); //returning object
		String ds1 = fromDate;
		String ds2 = toDate;
		
		Date from_date = null;
		Date to_date = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fromDate = sdf2.format(sdf1.parse(ds1));
			toDate = sdf2.format(sdf1.parse(ds2));
		    from_date =new SimpleDateFormat("yyyy-MM-dd").parse(fromDate); 
			to_date =new SimpleDateFormat("yyyy-MM-dd").parse(toDate); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<AdvertiserMasterEntity> reportObject = adhReportRepository.findAdvertiserRegisterRepository(from_date, to_date, orgId);
		List<AdvertiserMasterDto> dataList = new ArrayList();
		reportObject.forEach(adhMaster->{
			AdvertiserMasterDto adhMasterDto = new AdvertiserMasterDto();
			BeanUtils.copyProperties(adhMaster, adhMasterDto);
			
			String [] CreateDate = adhMaster.getCreatedDate().toString().split("\\s+");
			String [] licToDate = adhMaster.getAgencyLicToDate().toString().split("\\s+");
		
		
			String createdDate = CreateDate[0];
			String licToDate1 = licToDate[0];
			
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			
			try 
			{
				createdDate = df1.format(df2.parse(createdDate));
				licToDate1 = df1.format(df2.parse(licToDate1));
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			adhMasterDto.setRegDate(createdDate);
			adhMasterDto.setValidUpto(licToDate1);
			adhMasterDto.setStatus(adhMaster.getAgencyStatus());
			dataList.add(adhMasterDto);
		});
			
		dto.setAdvertiserMasterDtoList(dataList);
			return dto;
	}

}






/*if(reportObject != null || !reportObject.isEmpty())
{
	for(Object[] obj : reportObject)
	{
		tempDto = new AdvertiserMasterDto();
		
		if(obj[0] != null || obj[0] != "")
		{
			tempDto.setAgencyLicNo(obj[0].toString());
		}
		if(obj[1] != null || obj[1] != "")
		{
			tempDto.setAgencyName(obj[1].toString());
		}
		if(obj[2] != null || obj[2] != "")
		{	
			String[] dateSplit = obj[2].toString().split("\\s+");
			String created = dateSplit[0];
			SimpleDateFormat created_df1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat created_df2 = new SimpleDateFormat("yyyy-MM-dd");
			try {
			
				dateSplit[0] = created_df1.format(created_df2.parse(created));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tempDto.setRegDate(dateSplit[0]);
		}
		if(obj[3] != null || obj[3] != "")
		{
			String validupto = obj[3].toString();
			SimpleDateFormat created_df1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat created_df2 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				validupto = created_df1.format(created_df2.parse(validupto));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tempDto.setValidUpto(validupto);
		}
		if(obj[4] != null || obj[4] != "")
		{
			tempDto.setAgencyAdd(obj[4].toString());
		}
		if(obj[5] != null || obj[5] != "")
		{
			tempDto.setPanNumber(obj[5].toString());
		}
		if(obj[6] == null || obj[6] == "")
		{
			tempDto.setGstNo(" ");
		}
		else 
		{
			tempDto.setGstNo(obj[6].toString());
		}
		if(obj[7]!=null || obj[7] != "")
		{
			tempDto.setStatus(obj[7].toString());
		}
		
		dataList.add(tempDto);
		
	}
}	
	dto.setAdvertiserMasterDtoList(dataList);*/
