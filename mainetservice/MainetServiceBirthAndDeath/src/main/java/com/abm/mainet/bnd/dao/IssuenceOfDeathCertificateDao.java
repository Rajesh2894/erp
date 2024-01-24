package com.abm.mainet.bnd.dao;

import com.abm.mainet.bnd.domain.TbDeathreg;

public interface IssuenceOfDeathCertificateDao {
	
 public TbDeathreg getDeathRegisteredApplicantList(String certNo, String regNo, String regDate, String applicnId,Long orgId,String DeathWFStatus);

 Long getNoOfCopies(String applicnId,Long orgId);
}
