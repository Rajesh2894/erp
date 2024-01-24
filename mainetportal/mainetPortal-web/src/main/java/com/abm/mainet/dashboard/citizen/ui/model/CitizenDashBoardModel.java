package com.abm.mainet.dashboard.citizen.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.dto.ApplicationPortalMasterDTO;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dashboard.citizen.service.ICitizenDashBoardService;

@Component
@Scope("session")
public class CitizenDashBoardModel extends AbstractEntryFormModel<ApplicationPortalMaster> implements Serializable {

    /**
     * Flag Used Description
     * 
     * Application Status
     * 
     * O -->open (Pending from citizen side either payment related or checklist realted) P -->Pending from ULB side C --> Process
     * completed R --> application Rejected
     * 
     * Payment Status
     * 
     * F --> Free services //no payment Y --> Payment Completed N --> Payment is pending -(Empty) --> before pending
     * 
     * 
     * 
     * USED flag in query
     * 
     * 
     * P --> Payment pending (Failed) A --> Application Realted
     */
    private static final long serialVersionUID = 5035544750206891215L;

    private final String prefixName = PrefixConstants.Prefix.PSD;
    @Autowired
    private ICitizenDashBoardService iCitizenDashBoardService;

    public List<ApplicationPortalMasterDTO> getApplicationStatusList(final String flag) {
        final UserSession userSession = UserSession.getCurrent();

        return iCitizenDashBoardService.findApplicationStatusList(flag, userSession.getEmployee(), userSession.getOrganisation(),
                userSession.getLanguageId());

    }

    public int countApplicationStatus(final String flag, final String type) {
        final UserSession userSession = UserSession.getCurrent();

        if (MainetConstants.AGENCY.NAME.CITIZEN.equalsIgnoreCase(type)) {
            return iCitizenDashBoardService.countForApplicationStatus(flag, userSession.getEmployee(),
                    userSession.getOrganisation());
        } else if (MainetConstants.AGENCY.NAME.EMPLOYEE.equalsIgnoreCase(type.trim())) {
            return iCitizenDashBoardService.countForApplicationStatusForAdmin(flag, userSession.getOrganisation());
        } else {
            return 0;
        }
    }

    public String getfileDescription(final Long codeId, final int langId) {

        final List<LookUp> descLookuP = getLevelData(prefixName);
        String desc = null;
        for (final LookUp lookUp : descLookuP) {
            if (lookUp.getLookUpId() == codeId) {
                if (langId == MainetConstants.ENGLISH) {
                    desc = lookUp.getDescLangFirst();
                } else {
                    desc = lookUp.getDescLangSecond();
                }
                break;
            }
        }
        return desc;

    }

    public List<ApplicationPortalMasterDTO> getApplicationStatusListForAdmin(final String flag) {
        final UserSession userSession = UserSession.getCurrent();
        return iCitizenDashBoardService.getApplicationListForAdmin(flag, userSession.getOrganisation(),
                userSession.getLanguageId());

    }

}
