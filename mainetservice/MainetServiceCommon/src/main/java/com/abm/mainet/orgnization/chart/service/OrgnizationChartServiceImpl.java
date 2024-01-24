package com.abm.mainet.orgnization.chart.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.orgnization.chart.dao.OrgnizationChartDao;
import com.abm.mainet.orgnization.chart.dto.OrgnizationChartDto;

@Service
public class OrgnizationChartServiceImpl implements OrgnizationChartService {

	public OrgnizationChartServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OrgnizationChartDao asclOrgnizationDao;
	
	

	@Override
	@Transactional
	public List<OrgnizationChartDto> getOrgCharDtata() {
		// TODO Auto-generated method stub
		List<OrgnizationChartDto> chartlist = new ArrayList<>();
		List<Object[]> orgchartData = asclOrgnizationDao.getOrgChartData();
		OrgnizationChartDto dto = null;
		
		if (CollectionUtils.isNotEmpty(orgchartData)) {
			for (Object[] obj : orgchartData) {
				dto = new OrgnizationChartDto();
				if (obj[0] != null) {
					dto.setId(String.valueOf(obj[0]));
				}

				if (obj[1] != null) {
					dto.setName(String.valueOf(obj[1]));
				}

				if (obj[2] != null) {
					dto.setParent(String.valueOf(obj[2].toString()));
				}
				chartlist.add(dto);
			}
		}
		return chartlist;
	}

}
