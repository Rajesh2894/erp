package com.abm.mainet.dms.utility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.UserSession;

public class MakerChekerUtilityService {
	
	private static final Logger LOG = Logger.getLogger(MakerChekerUtilityService.class);

	@Autowired
    private IEntitlementService iEntitlementService;
	
	public boolean getMakerChekerFlag(final String flag) {
		if(MainetConstants.FLAGY.equalsIgnoreCase(flag)){
        	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
            	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
            	return false;
            }
        }
		return true;
	}
}
