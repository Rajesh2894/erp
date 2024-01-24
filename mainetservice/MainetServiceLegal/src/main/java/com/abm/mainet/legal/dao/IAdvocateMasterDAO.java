package com.abm.mainet.legal.dao;

import java.util.List;

import com.abm.mainet.legal.domain.AdvocateMaster;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;

public interface IAdvocateMasterDAO {

    List<AdvocateMaster> validateAdvocate(String mobile, String email, String pancard, String aadhar, Long advId, Long orgid);

	

	List<AdvocateMaster> findDetails(Long advId, Long crtId, String barCouncilNo, String advStatus, Long orgId);
}
