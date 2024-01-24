package com.abm.mainet.materialmgmt.utility;

import java.util.List;

import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

public class StoreMasterUtility {

	
	public static List<LookUp> getLookUpDesc(final String prefixCode, final int level, final Long orgid) {
		List<LookUp> storeMasterGroupList = CommonMasterUtility.getNextLevelData(prefixCode, level,
				UserSession.getCurrent().getOrganisation().getOrgid());
		return storeMasterGroupList;
	}
}
