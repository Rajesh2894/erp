package com.abm.mainet.adh.ui.validator;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author cherupelli.srikanth
 * @since 05 august 2019
 */
@Component
public class AdverstiserMasterValidator extends BaseEntityValidator<AdvertiserMasterDto> {

    /**
     * Validating the Advertiser Master Entry Form
     */
    @Override
    protected void performValidations(AdvertiserMasterDto model,
            EntityValidationContext<AdvertiserMasterDto> entityValidationContext) {

        final ApplicationSession session = ApplicationSession.getInstance();

        if (StringUtils.isBlank(model.getAgencyName())) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.name"));
        }
        if (StringUtils.isBlank(model.getAgencyAdd())) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.address"));
        }

       if (StringUtils.isBlank(model.getAgencyContactNo())) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.mobileNo"));
        }

        if (StringUtils.isBlank(model.getAgencyEmail())) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.emailId"));
        }

        if (StringUtils.isBlank(model.getAgencyOwner())) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.owner"));
        }

        if (model.getAgencyLicFromDate() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.licfromdate"));
        }
        if (model.getAgencyLicToDate() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.lictodate"));
        }

        if ((model.getAgencyLicToDate() != null && model.getAgencyLicFromDate() != null)
                && (Utility.compareDate(model.getAgencyLicToDate(), model.getAgencyLicFromDate()))) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("advertiser.master.validate.lic.to.date.from.date"));
        }

        if (model.getAgencyCategory() == null || model.getAgencyCategory() == 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("agency.registration.validate.category"));
        }
        if (StringUtils.equalsIgnoreCase(model.getApplicationTypeFlag(), MainetConstants.FlagM)) {
            if (model.getAgencyLicIssueDate() == null) {
                entityValidationContext
                        .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.issue.date"));
            } else {
            	//Defect #124744  LicIssueDate and LicToDate commented and new code added for  LicIssueDate and LicFromDate
                /*if (model.getAgencyLicIssueDate() != null && model.getAgencyLicToDate() != null) {

                    if (Utility.compareDate(model.getAgencyLicToDate(), model.getAgencyLicIssueDate())) {
                        entityValidationContext.addOptionConstraint(
                                session.getMessage("advertiser.master.validate.lic.to.date.issue.date"));
                    }
                }*/
                if (model.getAgencyLicIssueDate() != null && model.getAgencyLicFromDate() != null && !(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {

                    if (Utility.compareDate(model.getAgencyLicFromDate(), model.getAgencyLicIssueDate())) {
                        entityValidationContext.addOptionConstraint(
                                session.getMessage("advertiser.master.validate.lic.from.date.issue.date"));
                    }
                }
            }

        }
        if (StringUtils.equalsIgnoreCase(model.getApplicationTypeFlag(), MainetConstants.FlagR)) {
            if (StringUtils.isNotBlank(model.getAgencyLicNo())) {
                AgencyRegistrationResponseDto agencyRegDto = ApplicationContextProvider.getApplicationContext()
                        .getBean(IAdvertiserMasterService.class)
                        .getAgencyDetailByLicnoAndOrgId(model.getAgencyLicNo(), model.getOrgId());
                if (agencyRegDto.getMasterDto() != null) {
                    if (Utility.compareDate(model.getAgencyLicFromDate(), agencyRegDto.getMasterDto().getAgencyLicToDate())
                            || model.getAgencyLicFromDate().equals(agencyRegDto.getMasterDto().getAgencyLicToDate())) {
                        entityValidationContext.addOptionConstraint(session.getMessage(
                                "License from date cannot be less than or equal to previous license to date"));
                    }
                }
            }

        }

        if (StringUtils.equalsIgnoreCase(model.getApplicationTypeFlag(), MainetConstants.FlagN)) {
            if (model.getAgencyLicFromDate() == null) {
                entityValidationContext
                        .addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.licfromdate"));
            } else {
                if (!Utility.comapreDates(model.getAgencyLicFromDate(), new Date())) {
                    entityValidationContext
                            .addOptionConstraint(session.getMessage("License from date should be current date"));
                }
            }
        }
    }

}
