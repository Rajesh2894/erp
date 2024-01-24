package com.abm.mainet.cms.ui.validator;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
public class AdminQuickLinkValidator extends BaseEntityValidator<LinksMaster> {

    @Autowired
    private IQuickLinkService iQuickLinkService;

    @Override
    protected void performValidations(final LinksMaster entity, final EntityValidationContext<LinksMaster> validationContext) {
        if (validationContext.rejectIfNull(entity.getLinkOrder(), "linkOrder")) {

            final List<LinksMaster> linksMaster = iQuickLinkService.getLinkMasterByLinkOrder(entity.getLinkOrder(), entity,
                    UserSession.getCurrent().getOrganisation());

            if ((linksMaster != null) && (linksMaster.size() != 0)) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.section.err.linkOrder"));
            }

        }

        validationContext.rejectIfEmpty(entity.getLinkPath(), "linkPath");
        if (entity.getIsLinkModify().equals("null")) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.err.isHighlighted"));
        }
        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);

        if (MainetConstants.NON.equals(check)) {
            if ((entity.getLinkTitleEg() == null) || entity.getLinkTitleEg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getLinkTitleEg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.title1en"));
                } else if ((entity.getLinkTitleEg().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title1"));
                }
            }

            if ((entity.getLinkTitleReg() == null) || entity.getLinkTitleReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getLinkTitleReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.title2mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getLinkTitleEg(), "linkTitleEg");
            validationContext.rejectIfEmpty(entity.getLinkTitleReg(), "linkTitleReg");
        }
    }
}
