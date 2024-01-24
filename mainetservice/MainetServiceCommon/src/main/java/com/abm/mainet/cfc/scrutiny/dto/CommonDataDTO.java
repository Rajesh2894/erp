/**
 *
 */
package com.abm.mainet.cfc.scrutiny.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rajendra.Bhujbal
 *
 */
public class CommonDataDTO {

    private List<String> errors = new ArrayList<>(0);

    /**
     * @Method To get header logs details from "TIM" prefix.
     * @return List of {@link LookUp} for Tool Bar Image
     */
    public List<LookUp> getHeaderDetails() {
	 return Collections.emptyList();
        /*return getAppSession().getHierarchicalLookUp(getUserSession().getOrganisation(), PrefixConstants.LookUp.TOOL_BAR_IMAGE)
                .get(PrefixConstants.LookUp.TOOL_BAR_IMAGE);*/
    }

    /**
     * @Method To get ULB name from super organization.
     * @return {@link LookUp}
     */
    public LookUp getULBName() {
        final Organisation organisation = getUserSession().getOrganisation();
        final LookUp lookUp = new LookUp();

        if (organisation != null) {
            lookUp.setDescLangFirst(organisation.getONlsOrgname());
            lookUp.setDescLangSecond(organisation.getONlsOrgnameMar());
        }

        return lookUp;
    }

    public String getActiveClass() {
        return "";
    }

    public ApplicationSession getAppSession() {
        return ApplicationSession.getInstance();
    }

    private UserSession getUserSession() {
        return UserSession.getCurrent();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }
}
