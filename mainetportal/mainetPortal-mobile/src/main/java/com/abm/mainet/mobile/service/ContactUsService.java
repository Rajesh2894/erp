package com.abm.mainet.mobile.service;

import org.springframework.stereotype.Service;


import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.ui.model.CitizenContactUsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
/**
 * @author rajesh.das
 *
 */

@Service
public class ContactUsService implements IContactUsService {

	CitizenContactUsModel eipUserContactUsModel = new CitizenContactUsModel();

	
	/**
     * This function are used for Citizen Registration
     * @param EipUserContactUs
     * @return Boolean
     */
	@Override
	public Boolean saveContactUsDetails(EipUserContactUs contactUs) {
		// TODO Auto-generated method stub
		eipUserContactUsModel.setEipUserContactUs(contactUs);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IEIPContactUsService.class)
					.saveEIPuserContactus(contactUs);

			if (UserSession.getCurrent().getLanguageId() == 1) {
				final String msg1 = ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1")
						+ MainetConstants.WHITE_SPACE + (UserSession.getCurrent().getOrganisation().getONlsOrgname())
						+ MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3")
						+ MainetConstants.WHITE_SPACE + (UserSession.getCurrent().getOrganisation().getONlsOrgname())
						+ MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
				sendSMSandEMail(UserSession.getCurrent().getEmployee(), msg1, "N");
			}
			if (UserSession.getCurrent().getLanguageId() == 2) {
				final String msg1 = (UserSession.getCurrent().getOrganisation().getONlsOrgnameMar())
						+ MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1")
						+ MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3")
								.concat(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar())
						+ MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
				sendSMSandEMail(UserSession.getCurrent().getEmployee(), msg1, "N");
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private void sendSMSandEMail(final Employee registeredEmployee, final String msg, final String templateType) {
		final TemplateLookUp lookup = new TemplateLookUp();
		EipUserContactUs eipUserContactUs = eipUserContactUsModel.getEipUserContactUs();
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1");
		UserSession.getCurrent().getOrganisation().getONlsOrgname();
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3");
		UserSession.getCurrent().getOrganisation().getONlsOrgname();
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg2");
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg5");
		eipUserContactUs.getEmailId();
		ApplicationSession.getInstance().getMessage("eip.citizen.contactUs.name");
		eipUserContactUs.getFirstName();
		eipUserContactUs.getLastName();
		ApplicationSession.getInstance().getMessage("feedback.Email");
		eipUserContactUs.getEmailId();
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.desc");
		eipUserContactUs.getDescQuery();
		ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg7");

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setMobnumber(eipUserContactUs.getPhoneNo());
		dto.setEmail(eipUserContactUs.getEmailId());
		dto.setAppName(eipUserContactUs.getFirstName());

		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		} else {
			dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
		}

		lookup.setSmsBody(msg);
		lookup.setMailBody(msg);
		lookup.setAlertType("B");
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(lookup, dto,
				UserSession.getCurrent().getOrganisation());
	}

}
