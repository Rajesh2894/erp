package com.abm.mainet.bnd.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;

public interface PrintBNDCertificateService {

	List<TbBdCertCopyDTO> getPrintCertificateDetails(Long applicationId, Long orgId);

	BirthRegistrationDTO getBirthRegisteredAppliDetail(String certNo, String regNo, Date regDate, String applNo,
			Long orgId);

	TbDeathregDTO getDeathRegisteredAppliDetail(String certNo, String regNo, Date regDate, String applNo, Long orgId);

	Boolean updatPrintStatus(Long brId, Long orgId, Long bdId);
}
