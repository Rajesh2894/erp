package com.abm.mainet.intranet.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.intranet.domain.IntranetMaster;
import com.abm.mainet.intranet.dto.report.IntranetDTO;

@WebService					
public interface IIntranetUploadService {
	
	IntranetDTO saveIntranetData(IntranetDTO intranetDTO, Long langId, Long userId, Long orgId, List<AttachDocs> attachDocs);
	
	List<IntranetDTO> getIntranetData(Long orgId, Long docCateType);
	
	IntranetDTO getIntranetDataByInId(Long inId, Long orgId);

	String getdeptdesc(Long deptId, Long orgid);

	List<IntranetMaster> getAllIntranetData(Long orgId);
	
	
}
