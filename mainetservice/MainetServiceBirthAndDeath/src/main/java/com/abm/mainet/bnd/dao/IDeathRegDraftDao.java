package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.TbDeathRegdraft;
import com.abm.mainet.bnd.domain.TbDeathreg;

public interface IDeathRegDraftDao {
	
	public List<TbDeathRegdraft> getDeathRegisteredAppliDetail(Long applnId,Date drDod,Long orgId);

	public List<TbDeathRegdraft> getDraftDetail(Long orgId);

	

}
