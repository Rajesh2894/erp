package com.abm.mainet.smsemail.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dao.ISMSAndEmailDAO;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterfaceDTO;
import com.abm.mainet.smsemail.domain.SmsAndMailTemplate;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Service
public class SMSAndEmailService implements ISMSAndEmailService {

    private static final String DOCTYPE = "doctype";

    private static final String DOLLOR = "$";

    private static final long serialVersionUID = 1L;

    @Autowired
    private ISMSAndEmailDAO smsEmailDAO;

    @Autowired
    private ISMSService smsService;

    @Autowired
    private IEmailService emailService;

    @Autowired 
    private IOrganisationService organisationService; 

    private static final Logger LOGGER = Logger.getLogger(SMSAndEmailService.class);

    @Override
    @Transactional
    public boolean saveMessageTemplate(final SMSAndEmailInterface entity, final String mode) {
        boolean saveFlag = false;
        final SmsAndMailTemplate template = entity.getSmsAndmailTemplate();
        template.setSeId(entity);
        entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        if (entity.getServiceId().getServiceId() == null) {
            entity.setServiceId(null);
        }
        try {
        	saveFlag =   smsEmailDAO.saveMessageTemplate(entity);

        } catch (final Exception exception) {
        	
        	LOGGER.error("Error Occured in SMSAndEmailService::saveMessageTemplate()  while saving the Message Template ", exception);
            saveFlag = false;
        }
        return saveFlag;

    }

    /**
     * @see currently being used for Quartz Scheduler Batch Job & webservices call
     */
    @Override
    @Async
    public Boolean sendEmailSMS(final String deptCode, final String menuURL, final String type, final SMSAndEmailDTO dto,
            final Organisation organisation, final int langId) {

        TemplateLookUp lookup = null;
        lookup = smsEmailDAO.getTemplateFromDBForTran(deptCode, menuURL, type, organisation, langId);
        
        if (lookup == null) {
			Organisation superUserOrg = ApplicationSession.getInstance().getSuperUserOrganization();
			lookup = smsEmailDAO.getTemplateFromDBForTran(deptCode, menuURL, type, superUserOrg, langId);
		}

        if (lookup != null) {
            return this.sendEmailAndSMS(lookup, dto, organisation);
        }

        else {
            LOGGER.error("template not found for following pattern " + deptCode + ">>>" + menuURL + ">>>" + type);
            return false;
        }
    }

    private Boolean sendEmailAndSMS(final TemplateLookUp lookup, final SMSAndEmailDTO dto, final Organisation organisation) {

        if (MainetConstants.MENU.S.equalsIgnoreCase(lookup.getAlertType())
                && MainetConstants.SmsAndEmailConstant.TRANSACTION_TYPE_TRANSACTIONAL
                        .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("sms.server.isavailable"))) {
            if ((dto.getMobnumber() != null) && !dto.getMobnumber().isEmpty()) {
                if ((lookup.getSmsBody() != null) && !lookup.getSmsBody().isEmpty()) {
                    String smsTemplate = lookup.getSmsBody();
                    if (lookup.getSmsBody().contains(DOLLOR)) {
                        smsTemplate = dataFilledStringTemplate(lookup.getSmsBody(), dto);

                    }
                    Long templateId = lookup.getTemplateId();
                    smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(), dto.getLangId(), templateId.toString());
                } else {
                    LOGGER.error("SMS body found null or empty while sending sms to Mobile=" + dto.getMobnumber());
                }
            } else {
                throw new NullPointerException("SMS cannot be sent as SMSAndEmailDTO's field mobNumber found null or empty.");
            }

        } else if (MainetConstants.MENU.E.equalsIgnoreCase(lookup.getAlertType())
                && MainetConstants.SmsAndEmailConstant.TRANSACTION_TYPE_TRANSACTIONAL
                        .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("mail.server.isavailable"))) {
        	if((dto.getEmail() != null) && !dto.getEmail().isEmpty()) {
            if ((lookup.getMailBody() != null) && !lookup.getMailBody().isEmpty()) {
                String mailTemplate = lookup.getMailBody();
                if (lookup.getMailBody().contains(DOLLOR)) {
                    mailTemplate = dataFilledStringTemplate(lookup.getMailBody(), dto);
                }
                final String message = doHtmlMergedData(mailTemplate, dto.getAppName(), dto.getOrganizationName(),
                        organisation.getOrgid(), dto.getContextPath());
                final EMail email = new EMail();
                email.setTo(dto.getEmail().split(MainetConstants.operator.COMA));
                email.setMessage(message);
                if ((lookup.getMailSubject() != null) && lookup.getMailSubject().contains(DOLLOR)) {
                    final String subject = dataFilledStringTemplate(lookup.getMailSubject(), dto);
                    email.setSubject(subject);
                } else {
                    email.setSubject(lookup.getMailSubject());
                }
                if ((dto.getFilesForAttach() != null) && !dto.getFilesForAttach().isEmpty()) {
                    email.setFiles(dto.getFilesForAttach());
                }
                if ((dto.getEmail() != null) && !dto.getEmail().isEmpty()) {
                    emailService.sendHTMLEmail(email, organisation);
                }
            }
        	}
        } else if (lookup.getAlertType().equalsIgnoreCase("B")) {

            if (MainetConstants.SmsAndEmailConstant.TRANSACTION_TYPE_TRANSACTIONAL
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("mail.server.isavailable"))) {
                if (((lookup.getMailBody() != null) && !lookup.getMailBody().isEmpty())
                        && ((dto.getEmail() != null) && !dto.getEmail().isEmpty())) {
                    String mailTemplate = lookup.getMailBody();
                    if (lookup.getMailBody().contains(DOLLOR)) {
                        mailTemplate = dataFilledStringTemplate(lookup.getMailBody(), dto);
                    }
                    final String message = doHtmlMergedData(mailTemplate, dto.getAppName(), dto.getOrganizationName(),
                            organisation.getOrgid(), dto.getContextPath());

                    final EMail email = new EMail();
                    email.setTo(dto.getEmail().split(MainetConstants.operator.COMA));
                    email.setMessage(message);
                    if ((lookup.getMailSubject() != null) && lookup.getMailSubject().contains(DOLLOR)) {
                        final String subject = dataFilledStringTemplate(lookup.getMailSubject(), dto);
                        email.setSubject(subject);
                    } else {
                        email.setSubject(lookup.getMailSubject());
                    }
                    if ((dto.getFilesForAttach() != null) && !dto.getFilesForAttach().isEmpty()) {
                        email.setFiles(dto.getFilesForAttach());
                    }
                    if ((dto.getEmail() != null) && !dto.getEmail().isEmpty()) {
                        emailService.sendHTMLEmail(email, organisation);
                    }
                }
            }
            if (MainetConstants.SmsAndEmailConstant.TRANSACTION_TYPE_TRANSACTIONAL
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("sms.server.isavailable"))) {
                if ((dto.getMobnumber() != null) && !dto.getMobnumber().isEmpty()) {
                    String smsTemplate = lookup.getSmsBody();
                    if (lookup.getSmsBody().contains(DOLLOR)) {
                        smsTemplate = dataFilledStringTemplate(lookup.getSmsBody(), dto);
                    }
                    Long templateId = lookup.getTemplateId();
                    smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(), dto.getLangId(), templateId.toString());
                } else {
                    throw new NullPointerException("SMS cannot be sent as SMSAndEmailDTO's field mobNumber found null or empty.");
                }

            }
        }

        return true;
    }

    private String doHtmlMergedData(final String template, final String userName, final String orgName, final long orgId,
            final String contextPath) {
        final ApplicationSession session = ApplicationSession.getInstance();
        final StringBuilder builder = new StringBuilder();
        String domainURL = HttpHelper.getDomainURL(String.valueOf(orgId), true);
        if ((null == domainURL) || domainURL.isEmpty()) {
            domainURL = session.getMessage("sms.email.protocol") + "://" + session.getMessage("sms.email.ip")
                    + MainetConstants.operator.COLON
                    + session.getMessage("sms.email.port") + contextPath + MainetConstants.WINDOWS_SLASH;
        }

        builder.append(
                "<!doctype html><html><head><meta charset=\"utf-8\" accept-charset=\"UTF-8\"><title>EMAIL NOTIFICATION</title></head><body>");
        builder.append(
                "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;background-color: #fff;\"><tr> <td align=\"center\" valign=\"top\">");

        builder.append(
                "<tr><td valign=\"top\"></td></tr></table><table align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr>");

        builder.append(
                "<td width=\"50\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;border: none;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><tr>");

        builder.append("<td style=\"border:5px solid #F0F0F0;\">&nbsp;</td></tr> </table>");

        builder.append(
                "</td> <td width=\"35\" style=\"background-color: #f0f0f0;\"></td><td width=\"365\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr><td valign=\"top\">");

        builder.append(
                "</td></tr><tr> <td align=\"center\" mc:edit=\"title1\" style=\"font-weight: 400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 16px;line-height: 20px;text-transform: uppercase;padding-right: 30px;\"> <singleline label=\"title1\">"
                        + session.getMessage("smsEmail.welcome") + "</singleline> </td> </tr>");
        builder.append(
                "<tr><td height=\"20\"></td></tr> <tr><td align=\"left\" mc:edit=\"title2\" style=\"font-weight:400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 14px;line-height: 24px;padding-right: 30px;\"><singleline label=\"title2\">");
        if (userName != null) {
            builder.append(session.getMessage("smsEmail.dear")).append(" ").append(userName).append(",</singleline> </td> </tr>");
        } else {
            builder.append(session.getMessage("smsEmail.dearApplicant")).append(",</singleline> </td> </tr>");
        }

        builder.append(
                "<tr> <td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\"><multiline label=\"content\"><p style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;margin: 0 0 10px !important;\">");
        builder.append(template);
        builder.append("</p></multiline> <br>&nbsp;</td></tr>");

        builder.append(
                "<tr><td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\">"
                        + session.getMessage("email.footer.note") + "<br>&nbsp;</td></tr>");

        builder.append(
                "<tr><td height=\"10\"></td></tr><tr> <td valign=\"top\" style=\"padding-right: 30px;\"> &nbsp; </td> </tr><tr><td height=\"10\"></td></tr></table>");
        builder.append(" </td></tr> <tr><td height=\"50\"></td></tr> </table></td> </tr> </table></body></html>");
        return builder.toString();
    }

    private String dataFilledStringTemplate(final String template, final SMSAndEmailDTO dto) {

        final Set<String> list = new HashSet<>();
        try {

            for (int i = 0; i <= template.length();) {
                i = template.indexOf(DOLLOR, i);
                final int j = template.indexOf(DOLLOR, i + 1);

                final String temp = template.substring(i + 1, j);
                list.add(temp);
                int z = 0;
                if ((z = template.indexOf(DOLLOR, j + 1)) < 0) {
                    break;
                }
                i = z;
            }
        } catch (final Exception e) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, e);
        }

        final StringTemplate templates = new StringTemplate(template);
        for (final String hole : list) {
            final String init = hole.charAt(0) + MainetConstants.BLANK;
            final String initCap = hole.replaceFirst(hole.charAt(0) + MainetConstants.BLANK, init.toUpperCase());
            try {
                templates.setAttribute(hole, dto.getClass().getMethod("get" + initCap).invoke(dto));
            } catch (final NoSuchMethodException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            } catch (final SecurityException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            } catch (final IllegalAccessException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            } catch (final IllegalArgumentException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            } catch (final InvocationTargetException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            }
        }

        return templates.toString();

    }

    @Override
    public boolean checkExistanceForEdit(final SMSAndEmailInterface entity) {
        return smsEmailDAO.checkExistanceForEdit(entity);
    }

    @Override
    public List<SMSAndEmailInterfaceDTO> searchTemplateFromDB(final SMSAndEmailInterface requestEntity) {
        List<SMSAndEmailInterfaceDTO> responseDTOList = null;
        final List<SMSAndEmailInterface> interfaceList = smsEmailDAO.searchTemplates(requestEntity);

        if (!interfaceList.isEmpty()) {
            SMSAndEmailInterfaceDTO dto = null;
            responseDTOList = new ArrayList<>();
            for (final SMSAndEmailInterface template : interfaceList) {
                dto = new SMSAndEmailInterfaceDTO();
                dto.setSeId(template.getSeId());
                dto.setTemplateId(template.getSmsAndmailTemplate().getTpId());
                dto.setDpDeptid(template.getDpDeptid().getDpDeptid());
                if (template.getServiceId() != null) {
                    dto.setServiceId(template.getServiceId().getServiceId());
                }
                if (template.getSmfid() != null) {
                    dto.setEventId(template.getSmfid().getSmfid());
                }
                dto.setAlertType(template.getAlertType());
                dto.setIsDeleted(template.getIsDeleted());

                dto.setDeptDesc(template.getDpDeptid().getDpDeptdesc());
                if (template.getServiceId() != null) {
                    dto.setServiceDesc(template.getServiceId().getServiceName());
                } else {
                    dto.setServiceDesc(MainetConstants.NOT_APPLICABLE);
                }
                if (template.getSmfid() != null) {
                    dto.setEventDesc(template.getSmfid().getSmfname());
                }
                dto.setAlertTypeDesc(getAlertTypeDesc(template.getAlertType()));
                dto.setTemplateTypeDesc(CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.Prefix.SMT,
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        template.getSmsAndmailTemplate().getMessageType()));
                responseDTOList.add(dto);
            }
        }

        return responseDTOList;
    }

    private String getAlertTypeDesc(final String alertType) {
        String desc = null;
        if (alertType != null) {
            if (alertType.equals(MainetConstants.MENU.S)) {
                desc = MainetConstants.SMS;
            } else if (alertType.equals(MainetConstants.MENU.E)) {
                desc = MainetConstants.E_MAIL;
            } else if (alertType.equals(MainetConstants.MENU.B)) {
                desc = MainetConstants.SMS_AND_E_MAIL;
            }
        }
        return desc;
    }

    @Override
    @Transactional
    public void deleteTemplate(final Long seId) {

        smsEmailDAO.deleteTemplate(seId);
    }

    @Override
    @Transactional
    public SMSAndEmailInterface getTemplate(final Long templateId, final Long orgId) {
        return smsEmailDAO.getTemplateById(templateId, orgId);
    }

    @Override
    public SMSAndEmailInterface getTemplateFromDB(final SMSAndEmailInterface entity) {
        return smsEmailDAO.getTemplateFromDBForTran(entity);
    }

    @Override
    public void sendEmailAndSMS(final SMSAndEmailDTO dto, final long orgId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        try {
            if (dto == null) {
                throw new NullPointerException("Passed argument SMSAndEmailDTO cannot be null");
            }
            final TemplateLookUp lookUp = getTemplateFromDBForTran(dto, orgId);
            if (lookUp == null) {
                throw new NullPointerException(
                        "No Template found for deptShortCode=" + dto.getDeptShortCode() + ", serviceUrl=" + dto.getServiceUrl());
            }
            this.sendEmailSMS(lookUp, dto, org);
        } catch (final NullPointerException n) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, n);
        } catch (final Exception e) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, e);
        }
    }

    private TemplateLookUp getTemplateFromDBForTran(final SMSAndEmailDTO dto, final long org) {

        // before query to DB, validate required parameters
        verifyArguments(dto, org);

        return smsEmailDAO.getTemplateFromDBForTran(dto.getDeptShortCode(), dto.getServiceUrl(),
                dto.getTemplateType(), dto.getTransactionType(), dto.getLangId(), org);
    }

    private void verifyArguments(final SMSAndEmailDTO dto, final long orgId) {

        if ((dto.getDeptShortCode() == null) || dto.getDeptShortCode().isEmpty()) {
            throw new NullPointerException("SMSAndEmailDTO's field deptShortCode cannot be null or empty.");
        }
        if ((dto.getServiceUrl() == null) || dto.getServiceUrl().isEmpty()) {
            throw new NullPointerException("SMSAndEmailDTO's field serviceUrl cannot be null or empty.");
        }
        if ((dto.getTemplateType() == null) || dto.getTemplateType().isEmpty()) {
            throw new NullPointerException("SMSAndEmailDTO's field templateType cannot be null or empty.");
        }
        if ((dto.getTransactionType() == null) || dto.getTransactionType().isEmpty()) {
            throw new NullPointerException("SMSAndEmailDTO's field transactionType cannot be null or empty.");
        }
        if (dto.getLangId() == 0) {
            throw new IllegalArgumentException("SMSAndEmailDTO's field langId must be either 1 or 2");
        }
        if (orgId == 0) {
            throw new IllegalArgumentException(MainetConstants.BLANK);
        }
        if ((dto.getEmail() == null) || dto.getEmail().isEmpty()) {
            throw new NullPointerException("SMSAndEmailDTO's field email cannot be null or empty.");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findEventList(final String menuURL, final String activeStatus) {

        return smsEmailDAO.findAllEvents(menuURL, activeStatus);
    }

    @Override
    public void sendHTMLNewsLetterEmail(final EMail email, Organisation orgid) {

        if (!email.getMessage().contains(DOCTYPE)) {
            email.setMessage(convertToHtmlNewsLetter(email, null, orgid.getONlsOrgname(), orgid.getOrgid(), "/"));
        }

        new Thread(() -> emailService.sendHTMLEmail(email, orgid)).start();

    }

    private String convertToHtmlNewsLetter(EMail template, final String userName, final String orgName, final long orgId,
            final String contextPath) {
        final ApplicationSession session = ApplicationSession.getInstance();
        final StringBuilder builder = new StringBuilder();
        String domainURL = HttpHelper.getDomainURL(String.valueOf(orgId), true);
        domainURL = domainURL.replaceAll("CitizenHome.html", "");
        if ((null == domainURL) || domainURL.isEmpty()) {
            domainURL = session.getMessage("sms.email.protocol") + "://" + session.getMessage("sms.email.ip")
                    + MainetConstants.operator.COLON
                    + session.getMessage("sms.email.port") + contextPath + MainetConstants.WINDOWS_SLASH;
        }

        builder.append(
                "<!doctype html><html><head><meta charset=\"utf-8\" accept-charset=\"UTF-8\"><title>EMAIL NOTIFICATION</title></head><body>");
        builder.append(
                "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;background-color: #fff;\"><tr> <td align=\"center\" valign=\"top\">");

        builder.append(
                "<tr><td valign=\"top\"></td></tr></table><table align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr>");

        builder.append(
                "<td width=\"50\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;border: none;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><tr>");

        builder.append("<td style=\"border:5px solid #F0F0F0;\">&nbsp;</td></tr> </table>");

        builder.append(
                "</td> <td width=\"35\" style=\"background-color: #f0f0f0;\"></td><td width=\"365\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr><td valign=\"top\">");

        builder.append(
                "</td></tr><tr> <td align=\"center\" mc:edit=\"title1\" style=\"font-weight: 400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 16px;line-height: 20px;text-transform: uppercase;padding-right: 30px;\"> <singleline label=\"title1\">"
                        + session.getMessage("smsEmail.welcome") + "</singleline> </td> </tr>");
        builder.append(
                "<tr><td height=\"20\"></td></tr> <tr><td align=\"left\" mc:edit=\"title2\" style=\"font-weight:400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 14px;line-height: 24px;padding-right: 30px;\"><singleline label=\"title2\">");
        if (userName != null) {
            builder.append(session.getMessage("smsEmail.dear")).append(" ").append(userName).append(",</singleline> </td> </tr>");
        } else {
            builder.append(session.getMessage("smsEmail.dearApplicant")).append(",</singleline> </td> </tr>");
        }

        builder.append(
                "<tr> <td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\"><multiline label=\"content\"><p style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;margin: 0 0 10px !important;\">");
        builder.append(session.getMessage("email.newsletter.msg") + "<br>");
        String[] massages = template.getMessage().split(":");
        builder.append("English Link : <br>");
        builder.append(domainURL + contextPath + massages[0]);
        builder.append("<br>Hindi Link : <br>");
        builder.append(domainURL + contextPath + massages[1]);
        builder.append("</p></multiline> <br>&nbsp;</td></tr>");

        builder.append(
                "<tr><td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\">"
                        + session.getMessage("email.footer.note")
                        + " Click <a href=" + domainURL
                        + template.getUnsubscribeLink() + ">here</a> to Unsubscribe"
                        + "<br>&nbsp;</td></tr>");

        builder.append(
                "<tr><td height=\"10\"></td></tr><tr> <td valign=\"top\" style=\"padding-right: 30px;\"> &nbsp; </td> </tr><tr><td height=\"10\"></td></tr></table>");
        builder.append(" </td></tr> <tr><td height=\"50\"></td></tr> </table></td> </tr> </table></body></html>");
        return builder.toString();
    }

    @Override
    public void sendEmailSMS(final TemplateLookUp lookup, final SMSAndEmailDTO dto, final Organisation organisation) {

        new Thread(() -> sendEmailAndSMS(lookup, dto, organisation)).start();
    }

}
