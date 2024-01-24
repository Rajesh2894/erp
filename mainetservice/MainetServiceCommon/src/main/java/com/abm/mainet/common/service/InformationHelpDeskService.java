package com.abm.mainet.common.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.dto.InformationDeskDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;

@WebService
public interface InformationHelpDeskService {

	List<TbDepartment> getDeptarmetnList();

	List<TbServicesMst> findServiceListByDeptId(Long deptId, Long orgId);

	InformationDeskDto getServiceInfo(Long deptId, Long serviceId, Long orgId, Long categoryId);

}
