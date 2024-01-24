package com.abm.mainet.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IDepartmentUserLogReportDao;
import com.abm.mainet.cms.dto.DepartmentUserLogReportDTO;

@Service
public class DepartmentUserLogReportService implements IDepartmentUserLogReportService{
@Autowired
IDepartmentUserLogReportDao departmentUserLogReportDao;
	@Override
	@Transactional
	public List<DepartmentUserLogReportDTO> getUserLogReport(String section, Date fromDate, Date toDate,
			Long orgId) {
		List<DepartmentUserLogReportDTO> dtoList=new ArrayList<>();
		List<Object[]> objList=departmentUserLogReportDao.getUserLogReport(section,fromDate,toDate,orgId);
				objList.forEach(objects->{
					DepartmentUserLogReportDTO dto = new DepartmentUserLogReportDTO();
					if(objects[7]!=null && objects[8]!=null && objects[9]!=null) {
					if(objects[1]!=null) {
						dto.setEmpName(objects[1].toString());
					}
					if(objects[2]!=null) {
						dto.setEmpLoginName(objects[2].toString());
					}
					if(objects[4]!=null) {
						dto.setOrgName(objects[4].toString());
					}
                	if(objects[7]!=null) {
                	dto.setActivity(objects[7].toString());
                	}
                	if(objects[8]!=null) {
                	dto.setActivityDesc(objects[8].toString());
                	}
                	if(objects[9]!=null) {
                	dto.setLoginDate(objects[9].toString());
                	}
                	dtoList.add(dto);
					}
				});
	
		return dtoList;
	}

}
