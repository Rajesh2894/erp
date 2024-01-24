package com.abm.mainet.socialsecurity.dao;

import java.util.List;

import com.abm.mainet.socialsecurity.domain.ConfigurationMasterEntity;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;

public interface ConfigurationMasterDao {
	
	List<ConfigurationMasterEntity> getData(Long configurationId,Long schemeMstId, Long orgId);

	List<SocialSecurityApplicationForm> getAppData(Long selectSchemeName, Long subSchemeName, String swdward1,
			String swdward2, String swdward3, String swdward4, String swdward5, String aadharCard, String status, Long orgId);

}
