package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.BirthRegdraftEntity;

public interface IBirthRegDraftDao {

	List<BirthRegdraftEntity> getDraftDetail(Long orgId);

	List<BirthRegdraftEntity> getBirthRegDraftAppliDetail(Long applnId, Date brDob, Long orgId);
	

}
