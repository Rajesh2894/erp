package com.abm.mainet.smsemail.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.repository.SmsEmailRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.TemplateLookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dao.ISMSAndEmailDAO;
import com.abm.mainet.smsemail.dao.ISMSAndEmailTransDAO;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterfaceDTO;
import com.abm.mainet.smsemail.domain.SmsAndMailTemplate;
import com.abm.mainet.smsemail.domain.SmsEmailTransaction;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Service
public class SMSAndEmailService implements ISMSAndEmailService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ISMSAndEmailDAO smsEmailDAO;

	@Autowired
	private ISMSAndEmailTransDAO smsEmailTransDAO;

	@Autowired
	private ISMSService smsService;

	@Autowired
	private IEmailService ieEmailService;

	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private SmsEmailRepository smsEmailRepository;

	@Override
	@Transactional
	public boolean saveMessageTemplate(final SMSAndEmailInterface entity, final String mode) {

		final SmsAndMailTemplate template = entity.getSmsAndmailTemplate();
		template.setSeId(entity);
		entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
		if (entity.getServiceId().getSmServiceId() == null) {
			entity.setServiceId(null);
		}
		return smsEmailDAO.saveMessageTemplate(entity);

	}

	/**
	 * If SMS Template for Department Not Found then Search SMS Template for
	 * SuperUserOrganisation. Eventually not Found for any case return false and log
	 * error Template Not Found
	 *
	 * @see currently being used for Quartz Scheduler Batch Job & webservices call
	 */
	@Override
	public boolean sendEmailSMS(final String deptCode, final String menuURL, final String type,
			final SMSAndEmailDTO dto, final Organisation organisation, final int langId) {

		Employee employee = new Employee();
		if (dto.getUserId() != null) {
			employee.setEmpId(dto.getUserId());
		}
		logger.info("SMS parameters >>> deptCode:  " + deptCode);
		logger.info("SMS parameters >>> menuURL:  " + menuURL);
		logger.info("SMS parameters >>> type:  " + type);
		logger.info("SMS parameters >>> organisation:  " + organisation.getOrgid());
		logger.info("SMS parameters >>> langId:  " + langId);
		try {
			logger.info("SMS parameters >>> menu_dto:  " + Utility.getMapper().writeValueAsString(dto));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Unable to convert DTO with  objectMapper:  ");	
		}

		TemplateLookUp templookup = smsEmailDAO.getTemplateFromDBForTran(deptCode, menuURL, type, organisation, langId);
		if (templookup == null) {
			Organisation superUserOrg = organisationService.getSuperUserOrganisation();
			templookup = smsEmailDAO.getTemplateFromDBForTran(deptCode, menuURL, type, superUserOrg, langId);
		}
		if (templookup != null) {
			logger.info("SMS parameters >>> sms body:  " + templookup.getSmsBody());
			logger.info("SMS parameters >>> template id:  " + templookup.getTemplateId());
		}
		else{
			logger.info("SMS parameters >>> template not defined:  " );
		}

		final TemplateLookUp lookup = templookup;
		dto.setLangId(langId);
		if (lookup != null && employee.getEmpId() != null) {
			new Thread(() -> sendEmailAndSMS(lookup, dto, organisation, employee)).start();
			return true;
		} else {
			logger.error("Template not found for following pattern " + deptCode + ">>>" + menuURL + ">>>" + type + ">>>"
					+ organisation.getONlsOrgname());
			return false;
		}
	}

	/**
	 * NOTE: currently being used for Quartz Batch Job for your requirement use
	 * sendEmailAndSMS(TemplateLookUp lookup,SMSAndEmailDTO dto) this method
	 */
	// @Override
	private boolean sendEmailAndSMS(final TemplateLookUp lookup, final SMSAndEmailDTO dto,
			final Organisation organisation, Employee employee) {
		String result = StringUtils.EMPTY;

		if (lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.S)) {
			if ((dto.getMobnumber() != null) && !dto.getMobnumber().isEmpty()) {

				if ((lookup.getSmsBody() != null) && !lookup.getSmsBody().isEmpty()) {
					String smsTemplate = lookup.getSmsBody();
					if (lookup.getSmsBody().contains("$")) {
						smsTemplate = dataFilledStringTemplate(lookup.getSmsBody(), dto);

					}
					//ENH 108962 Template Id Added new SMS API
					String templateId = lookup.getTemplateId();
					result = smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(),dto.getLangId(), templateId.toString());
					insertSMSTrans(dto, result, smsTemplate, organisation, employee);
				}
			}
		} else if (lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.E)) {
			if ((lookup.getMailBody() != null) && !lookup.getMailBody().isEmpty()) {
				String mailTemplate = "";
				Boolean customMsg = false;
				if (dto.getEmailBody() != null && dto.getEmailBody().length() != 0) {
					mailTemplate = dto.getEmailBody().toString();
					customMsg = true;
				} else {
					mailTemplate = lookup.getMailBody();
				}

				if (lookup.getMailBody().contains("$")) {
					mailTemplate = dataFilledStringTemplate(lookup.getMailBody(), dto);
				}
				final String message = doHtmlMergedData(mailTemplate, dto.getAppName(), dto.getOrganizationName(),
						organisation.getOrgid(), customMsg);
				final EMail email = new EMail();
				email.setMessage(message);
				if ((lookup.getMailSubject() != null) && lookup.getMailSubject().contains("$")) {
					final String subject = dataFilledStringTemplate(lookup.getMailSubject(), dto);
					email.setSubject(subject);
				} else {
					email.setSubject(lookup.getMailSubject());
				}
				if ((dto.getFilesForAttach() != null) && !dto.getFilesForAttach().isEmpty()) {
					email.setFiles(dto.getFilesForAttach());
				}
				if(dto.getInlineImages()!= null && !dto.getInlineImages().isEmpty()) {
					email.setInlineImages(dto.getInlineImages());
				}
				if ((dto.getEmail() != null) && !dto.getEmail().isEmpty()) {
					email.setTo(dto.getEmail().split(MainetConstants.operator.COMMA));
					// method call change by ISRAT in behalf of RAJESH SIR at 29/07/2019
					// ieEmailService.sendHTMLEmail(email, organisation);
					ieEmailService.sendHTMLEmail(email, organisation.getOrgid());
				}
			}
		} else if (lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.B)) {

			if (((lookup.getMailBody() != null) && !lookup.getMailBody().isEmpty())
					&& ((dto.getEmail() != null) && !dto.getEmail().isEmpty())) {

				String mailTemplate = "";
				Boolean customMsg = false;
				if (dto.getEmailBody() != null && dto.getEmailBody().length() != 0) {
					mailTemplate = dto.getEmailBody().toString();
					customMsg = true;
				} else {
					mailTemplate = lookup.getMailBody();
					// To skip Dear applicant message on while sending Email 128052
					customMsg = MainetConstants.Property.PROP_NOT_URL.equals(dto.getServiceUrl()) ? true : false;
				}

				if (lookup.getMailBody().contains("$")) {
					mailTemplate = dataFilledStringTemplate(lookup.getMailBody(), dto);
				}
				// change ABOVE for custom html message in mailTemplate field

				final String message = doHtmlMergedData(mailTemplate, dto.getAppName(), dto.getOrganizationName(),
						organisation.getOrgid(), customMsg);

				final EMail email = new EMail();
				email.setMessage(message);
				if ((lookup.getMailSubject() != null) && lookup.getMailSubject().contains("$")) {
					final String subject = dataFilledStringTemplate(lookup.getMailSubject(), dto);
					email.setSubject(subject);
				} else {
					email.setSubject(lookup.getMailSubject());
				}
				if ((dto.getFilesForAttach() != null) && !dto.getFilesForAttach().isEmpty()) {
					email.setFiles(dto.getFilesForAttach());
				}

				if(dto.getInlineImages()!= null && !dto.getInlineImages().isEmpty()) {
					email.setInlineImages(dto.getInlineImages());
				}

				if ((dto.getEmail() != null) && !dto.getEmail().isEmpty()) {
					email.setTo(dto.getEmail().split(MainetConstants.operator.COMMA));
					// ieEmailService.sendHTMLEmail(email, organisation);
					ieEmailService.sendHTMLEmail(email, organisation.getOrgid());
				}
				insertEmailTrans(dto, result, email, organisation, employee);
			}
			if ((dto.getMobnumber() != null) && !dto.getMobnumber().isEmpty()) {
				String smsTemplate = lookup.getSmsBody();
				if (lookup.getSmsBody().contains("$")) {
					smsTemplate = dataFilledStringTemplate(lookup.getSmsBody(), dto);
				}
				// smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(),
				// dto.getLangId());
				//ENH 108962 Template Id Added new SMS API
				String templateId = lookup.getTemplateId();
				result = smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(), dto.getLangId(), templateId.toString());
				insertSMSTrans(dto, result, smsTemplate, organisation, employee);
			}
		}
		return true;
	}

	private void insertSMSTrans(final SMSAndEmailDTO dto, String result, String smsTemplate,
			final Organisation organisation, final Employee employee) {
		try {
			SmsEmailTransaction trans = new SmsEmailTransaction();
			trans.setLangId(dto.getLangId());
			trans.setMobileNo(dto.getMobnumber());
			trans.setMsgText(smsTemplate.toString());
			trans.setStatus(MainetConstants.SUCCESS_MSG.equals(result) ? "SUCCESS" : "FAIL");
			trans.setSentDt(Calendar.getInstance().getTime());
			// trans.setSentBy(employee.getEmpname());
			trans.setUserId(employee);
			trans.setOrgId(organisation);
			trans.setRefId4("N");
			trans.setRefId3("S");
			trans.setLmodDate(Calendar.getInstance().getTime());
			trans.setServiceId(dto.getServiceId());
			smsEmailRepository.save(trans);
			// logger.info("insertSMSTrans Success -> employee>>" +employee.getEmpname() +
			// ", org>>"+ organisation.getOrgid());
		} catch (Exception e) {
			logger.warn("insertSMSTrans Failed -> employee>>" + e.getMessage());
		}
	}

	private void insertEmailTrans(final SMSAndEmailDTO dto, String result, final EMail email,
			final Organisation organisation, final Employee employee) {
		try {
			SmsEmailTransaction trans = new SmsEmailTransaction();
			trans.setLangId(dto.getLangId());
			trans.setMobileNo(dto.getMobnumber());
			trans.setOrgId(new Organisation(dto.getOrgId()));
			trans.setStatus(email.isSendStatus() ? "SUCCESS" : "FAIL");
			trans.setSentDt(Calendar.getInstance().getTime());
			// trans.setSentBy(employee.getEmpname());
			trans.setUserId(employee);
			trans.setRefId4("N");
			trans.setRefId3("E");
			trans.setEmailbody(StringUtils.substring(email.getMessage(), 0, 2000));
			trans.setEmailId(email.getSender());
			trans.setEmailsubject(StringUtils.substring(email.getSubject(), 0, 1000));
			trans.setLmodDate(Calendar.getInstance().getTime());
			trans.setServiceId(dto.getServiceId());
			smsEmailRepository.save(trans);
			// logger.info("insertEmailTrans Success -> employee>>" +employee.getEmpname() +
			// ", org>>"+ organisation.getOrgid());
		} catch (Exception e) {
			logger.warn("insertEmailTrans Failed -> employee>>" + e.getMessage());
		}
	}

	private String doHtmlMergedData(final String template, final String userName, final String orgName,
			final long orgId, Boolean customMsg) {
		final ApplicationSession session = ApplicationSession.getInstance();
		final StringBuilder builder = new StringBuilder();

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
		// code added by ISRAT at 30/07/2019 because in my case Dear STMT. not want
		if (!customMsg) {
			if (userName != null) {
				// #129079 to get content in both eng and reg language
				if (session.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					builder.append(session.getMessage("smsEmail.dear", 
							"smsEmail.dear",new Locale(MainetConstants.REG_ENG.ENGLISH))).append(" ").append(userName)
					.append(",</singleline> </td> </tr>");
				else
					builder.append(session.getMessage("smsEmail.dear", 
							"smsEmail.dear",new Locale(MainetConstants.REG_ENG.REGIONAL))).append(" ").append(userName)
					.append(",</singleline> </td> </tr>");

			} else {
				if (session.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					builder.append(session.getMessage("smsEmail.dearApplicant", 
							"smsEmail.dearApplicant",new Locale(MainetConstants.REG_ENG.ENGLISH)))
					.append(",</singleline> </td> </tr>");
				else
					builder.append(session.getMessage("smsEmail.dearApplicant", 
							"smsEmail.dearApplicant",new Locale(MainetConstants.REG_ENG.REGIONAL)))
					.append(",</singleline> </td> </tr>");
			}
		}

		builder.append(
				"<tr> <td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\"><multiline label=\"content\"><p style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;margin: 0 0 10px !important;\">");
		builder.append(template);
		builder.append("</p></multiline> <br>&nbsp;</td></tr>");

		builder.append(
				"<tr><td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\">"
						+ session.getMessage("rti.mail.notemsg") + "<br>&nbsp;</td></tr>");

		builder.append(
				"<tr><td height=\"10\"></td></tr><tr> <td valign=\"top\" style=\"padding-right: 30px;\"> &nbsp; </td> </tr><tr><td height=\"10\"></td></tr></table>");
		builder.append(" </td></tr> <tr><td height=\"50\"></td></tr> </table></td> </tr> </table></body></html>");
		return builder.toString();
	}

	private String dataFilledStringTemplate(final String template, final SMSAndEmailDTO dto) {

		final Set<String> list = new HashSet<>();
		try {
			for (int i = 0; i <= template.length();) {
				i = template.indexOf("$", i);
				final int j = template.indexOf("$", i + 1);

				final String temp = template.substring(i + 1, j);
				list.add(temp);
				int z = 0;
				if ((z = template.indexOf("$", j + 1)) < 0) {
					break;
				}
				i = z;
			}
		} catch (final Exception e) {
		}

		final StringTemplate templates = new StringTemplate(template);
		for (final String hole : list) {
			final String init = hole.charAt(0) + MainetConstants.BLANK;
			final String initCap = hole.replaceFirst(hole.charAt(0) + MainetConstants.BLANK, init.toUpperCase());
			try {
				templates.setAttribute(hole, dto.getClass().getMethod("get" + initCap).invoke(dto));
			} catch (final NoSuchMethodException e) {
			} catch (final SecurityException e) {
			} catch (final IllegalAccessException e) {
			} catch (final IllegalArgumentException e) {
			} catch (final InvocationTargetException e) {
			}

		}

		return templates.toString();

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
					dto.setServiceId(template.getServiceId().getSmServiceId());
				}
				if (template.getSmfid() != null) {
					dto.setEventId(template.getSmfid().getSmfid());
				}
				dto.setAlertType(template.getAlertType());
				dto.setIsDeleted(template.getIsDeleted());

				dto.setDeptDesc(template.getDpDeptid().getDpDeptdesc());
				if (template.getServiceId() != null) {
					dto.setServiceDesc(template.getServiceId().getSmServiceName());
				} else {
					dto.setServiceDesc(MainetConstants.NOT_APPLICABLE);
				}
				if (template.getSmfid() != null) {
					dto.setEventDesc(template.getSmfid().getSmfname());
				}
				dto.setAlertTypeDesc(getAlertTypeDesc(template.getAlertType()));
				dto.setTemplateTypeDesc(CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.LookUpPrefix.SMT,
						requestEntity.getOrgId().getOrgid(), template.getSmsAndmailTemplate().getMessageType()));
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
	@Transactional(readOnly = true)
	public boolean checkExistanceForEdit(final SMSAndEmailInterface entity) {
		return smsEmailDAO.checkExistanceForEdit(entity);
	}

	@Override
	public ResponseEntity<?> validate(final WSRequestDTO requestDTO) {
		ApplicationSession appSession = ApplicationSession.getInstance();
		ResponseEntity<?> responseEntity = null;
		if (requestDTO == null) {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(appSession.getMessage("sms.email.wsrequest.dto"));
		}
		if (requestDTO.getDataModel() == null) {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(appSession.getMessage("sms.email.datamodel"));
		}

		return (responseEntity == null
				? ResponseEntity.status(HttpStatus.OK).body(appSession.getMessage("sms.email.validate"))
						: responseEntity);
	}

	/*
	 * @Override public boolean sendTestEmailSMS(final SMSAndEmailDTO dto) {
	 * SMSAndEmailInterface lookup = null; lookup = smsEmailDAO.getTemplateById(1l,
	 * 1l); if (lookup != null) { return
	 * this.sendEmailAndSMS(createTemplateLookup(lookup), dto); } else {
	 * logger.error("template not found for following pattern " +
	 * dto.getDeptShortCode() + ">>>" + dto.getServiceUrl() + ">>>" +
	 * dto.getTemplateType()); return false; } }
	 */

	/*
	 * private TemplateLookUp createTemplateLookup(SMSAndEmailInterface
	 * testtemplate) { List<SMSAndEmailInterface> smsEmailTempList = new
	 * ArrayList<>(); smsEmailTempList.add(testtemplate); TemplateLookUp lookup =
	 * null; SmsAndMailTemplate template = null; if ((smsEmailTempList != null) &&
	 * !smsEmailTempList.isEmpty()) { lookup = new TemplateLookUp();
	 * smsEmailTempList.get(0).getSmsAndmailTemplate().getSeId(); template =
	 * smsEmailTempList.get(0).getSmsAndmailTemplate();
	 * lookup.setAlertType(smsEmailTempList.get(0).getAlertType());
	 * lookup.setMailBody(template.getMailBody());
	 * lookup.setMailSubject(template.getMailSub());
	 * lookup.setSmsBody(template.getSmsBody()); } return lookup; }
	 */

	/*
	 * @Override public boolean sendEmailSMS(final SMSAndEmailDTO dto) {
	 * Organisation organisation = new Organisation();
	 * organisation.setOrgid(dto.getOrgId()); return
	 * this.sendEmailSMS(dto.getDeptShortCode(), dto.getServiceUrl(),
	 * dto.getTemplateType(), dto, organisation, dto.getLangId()); lookup =
	 * smsEmailDAO.getTemplate(dto.getDeptShortCode(), dto.getServiceUrl(),
	 * dto.getTemplateType(),dto, dto.getOrgId() ); if (lookup != null) { return
	 * this.sendEmailAndSMS(lookup, dto); } else {
	 * logger.error("template not found for following pattern " +
	 * dto.getDeptShortCode() + ">>>" + dto.getServiceUrl() + ">>>" +
	 * dto.getTemplateType()); return false; } }
	 */

	/*
	 * private boolean sendEmailAndSMS(final TemplateLookUp lookup, final
	 * SMSAndEmailDTO dto) { String result = StringUtils.EMPTY; if
	 * (lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.S) ||
	 * lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.B)) { if
	 * ((dto.getMobnumber() != null) && !dto.getMobnumber().isEmpty()) { if
	 * ((lookup.getSmsBody() != null) && !lookup.getSmsBody().isEmpty()) { String
	 * smsTemplate = lookup.getSmsBody(); try { if
	 * (lookup.getSmsBody().contains("$")) { smsTemplate =
	 * dataFilledStringTemplate(lookup.getSmsBody(), dto); } result =
	 * smsService.sendSMSInBackground(smsTemplate.toString(), dto.getMobnumber(),
	 * dto.getLangId()); result = MainetConstants.SUCCESS_MESSAGE; } catch
	 * (Exception e) { logger.warn("sendEmailAndSMS->sendSMSInBackground Failed",
	 * e); result = MainetConstants.FAILURE_MSG; } Organisation org =
	 * organisationService.getSuperUserOrganisation(); Employee emp =
	 * employeeService.findEmployeeById(org.getUserId()); insertSMSTrans(dto,
	 * result, smsTemplate, org,emp); } } } if
	 * (lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.E) ||
	 * lookup.getAlertType().equalsIgnoreCase(MainetConstants.MENU.B)) { if
	 * ((lookup.getMailBody() != null) && !lookup.getMailBody().isEmpty()) { EMail
	 * email = new EMail(); try { String mailTemplate = lookup.getMailBody(); if
	 * (lookup.getMailBody().contains("$")) { mailTemplate =
	 * dataFilledStringTemplate(lookup.getMailBody(), dto); } final String message =
	 * doHtmlMergedData(mailTemplate, dto.getAppName(), dto.getOrganizationName(),
	 * dto.getOrgId()); email.setMessage(message); if ((lookup.getMailSubject() !=
	 * null) && lookup.getMailSubject().contains("$")) { final String subject =
	 * dataFilledStringTemplate(lookup.getMailSubject(), dto);
	 * email.setSubject(subject); } else {
	 * email.setSubject(lookup.getMailSubject()); } if ((dto.getEmail() != null) &&
	 * !dto.getEmail().equals(MainetConstants.BLANK)) {
	 * email.setTo(dto.getEmail().split(MainetConstants.operator.COMMA)); } if
	 * ((dto.getCc() != null) && !dto.getCc().equals(MainetConstants.BLANK)) {
	 * email.setCc(dto.getCc().split(MainetConstants.operator.COMMA)); } if
	 * ((dto.getBcc() != null) && !dto.getBcc().equals(MainetConstants.BLANK)) {
	 * email.setBcc(dto.getBcc().split(MainetConstants.operator.COMMA)); } if
	 * ((dto.getFilesForAttach() != null) && !dto.getFilesForAttach().isEmpty()) {
	 * email.setFiles(dto.getFilesForAttach()); }
	 * ieEmailService.sendHTMLEmail(email, dto.getOrgId()); result =
	 * MainetConstants.SUCCESS_MESSAGE; } catch (Exception e) {
	 * logger.warn("sendEmailAndSMS->sendHTMLEmail Failed", e); result =
	 * MainetConstants.FAILURE_MSG; } Organisation org =
	 * organisationService.getSuperUserOrganisation(); Employee emp =
	 * employeeService.findEmployeeById(org.getUserId()); insertEmailTrans(dto,
	 * result, email, org, emp); } } return true; }
	 */
	@Override
	@Transactional
	public SMSAndEmailInterface getTemplate(final Long templateId, final Long orgId) {
		return smsEmailDAO.getTemplateById(templateId, orgId);
	}

	@Override
	@Transactional
	public void deleteTemplate(final Long seId) {

		smsEmailDAO.deleteTemplate(seId);
	}

	@Override
	@Transactional
	public List<SmsEmailTransactionDTO> getEmailSMS(final SMSAndEmailDTO dto) {
		List<SmsEmailTransactionDTO> transDtoList = new ArrayList<>();
		try {
			List<SmsEmailTransaction> transList = smsEmailTransDAO.getSmsEmailTransaction(dto.getOrgId(),
					dto.getUserId());
			for (SmsEmailTransaction trans : transList) {
				SmsEmailTransactionDTO transDto = new SmsEmailTransactionDTO();
				BeanUtils.copyProperties(trans, transDto);
				transDto.setUserId(trans.getUserId().getUserId());
				transDto.setOrgId(trans.getOrgId().getOrgid());
				trans.setRefId4(MainetConstants.FlagY);
				transDtoList.add(transDto);
				smsEmailTransDAO.saveMessageTemplate(trans);
			}
		} catch (FrameworkException e) {
			Log.warn("No SmsEmailTransaction Found getEmailSMS", e);
		}
		return transDtoList;
	}

}
