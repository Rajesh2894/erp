
package com.abm.mainet.smsemail.service;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.dto.SMSRequestDto;

@Service
public class EmailService implements IEmailService {

    private static final String SENDERPWD = "SENDERPWD";

    private static final String SENDERID = "SENDERID";

    private static final String EMAIL_MLA_PREFIX_DATA_NOT_FOUND = "EMAIL (MLA)prefix data not found.";

    private static final String EMAIL_WITH_ATTACHMENT_COMPLETED = "Email With Attachment Completed...";

    private static final String EXCEPTION_IN_SENDING_E_MAIL_WITH_ATTACHMENT = "Exception in sending e-mail with attachment...";

    private static final String E_MAIL_SENDING_COMPLETED = "E-Mail sending Completed ...";

    private static final String EXCEPTION_E_MAIL_SENDING = "Exception E-Mail sending ...";

    private static final String MEMBER_OF_LEGISLATIVE_ASSEMBLY = "MLA";

    @Autowired
    private JavaMailSenderImpl emailSender;

    Logger LOGGER = Logger.getLogger(this.getClass());

    public void sendEmailWithAttachment(final EMail email) {

        if (email != null) {
            final MimeMessage[] newMailsArray = new MimeMessage[1];
            newMailsArray[0] = getMimeMessage(email);

            if (email.isBackground()) {
                this.sendInBackground(newMailsArray);
            } else {
                for (final MimeMessage message : newMailsArray) {
                    email.setSendStatus(this.send(message));
                }
            }
        }
    }

    @Override
    public void sendEmail(final HashMap<String, String> email, final Organisation org) {

        if (email != null) {
            synchronized (this) {
                final SimpleMailMessage[] newMailsArray = new SimpleMailMessage[1];
                newMailsArray[0] = this.getSimpleMailMessage(email);

                if (setSenderDetails(org)) {
                    sendInBackground(newMailsArray);
                } else {
                    LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
                }

            }
        }
    }

    private boolean setSenderDetails(final Organisation organisation) {
        boolean flag = false;

        try {
            String host = MainetConstants.BLANK;
            String port = MainetConstants.BLANK;
            String username = MainetConstants.BLANK;
            String password = MainetConstants.BLANK;

            final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MEMBER_OF_LEGISLATIVE_ASSEMBLY,
                    UserSession.getCurrent().getOrganisation());

            if (null != lookUps) {
                for (final LookUp lookUp : lookUps) {
                    if (lookUp.getLookUpCode().equalsIgnoreCase(SENDERID)) {
                        username = lookUp.getOtherField();
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(SENDERPWD)) {
                        password = lookUp.getOtherField();
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.MENU.D)) {
                        host = lookUp.getOtherField();
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.MENU.P)) {
                        port = lookUp.getOtherField();
                    }
                }

                try {
                    if ((port != null) && !port.equalsIgnoreCase(MainetConstants.BLANK)) {
                        emailSender.setPort(Integer.parseInt(port));
                    }
                } catch (final Exception exception) {
                    emailSender.setPort(25);// Default
                    LOGGER.error(MainetConstants.ERROR_OCCURED, exception);
                }

                emailSender.setHost(host);
                emailSender.setUsername(username);
                emailSender.setPassword(password);

                flag = true;
            } else {
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
                LOGGER.info("Sending mail with default configuration");
                flag = true;
            }
        } catch (final Exception exception) {
            flag = false;
            LOGGER.error(MainetConstants.ERROR_OCCURED, exception);

        }

        return flag;
    }

    private void sendInBackground(final SimpleMailMessage[] newMailsArray) {
        LOGGER.info("E-Mail sending START ...");
        final SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[newMailsArray.length];

        for (int i = 0; newMailsArray.length > i; i++) {
            mailMessageArray[i] = newMailsArray[i];
        }

        if ((mailMessageArray != null) && (mailMessageArray.length > 0)) {
            boolean success = true;
            try {
                emailSender.send(mailMessageArray);
            } catch (final Exception e) {
                success = false;
                LOGGER.error(EXCEPTION_E_MAIL_SENDING, e);
            }
            if (success) {
                LOGGER.info(E_MAIL_SENDING_COMPLETED);
            }
        }
    }

    private boolean send(final SimpleMailMessage message) {
        boolean success = false;

        LOGGER.info("E-Mail sending START ...");
        if (message != null) {
            try {
                emailSender.send(message);
                success = true;
            } catch (final Exception e) {
                success = false;
                LOGGER.error("Exception E-Mail sending ..." + e.getMessage());
            }
            if (success) {
                LOGGER.info("E-Mail sending Completed ...");
            }
        } else {
            LOGGER.info("null Message");
        }

        return success;
    }

    private void sendInBackground(final MimeMessage[] mimeMessages) {
        LOGGER.info("Email With Attachment START ...");
        final MimeMessage[] mimeMessagesArray = new MimeMessage[mimeMessages.length];

        for (int i = 0; mimeMessages.length > i; i++) {
            mimeMessagesArray[i] = mimeMessages[i];
        }

        if ((mimeMessagesArray != null) && (mimeMessagesArray.length > 0)) {

            LOGGER.info("Asynchronous method call of send email -- Complete");
            boolean success = true;
            try {
                Thread.sleep(5000);
                LOGGER.info("Asynchronous method call of send email -- Start");
                emailSender.send(mimeMessagesArray);
            } catch (final Exception e) {
                success = false;
                LOGGER.error("Exception in sending e-mail with attachment..." + e);
            }
            if (success) {
                LOGGER.info("Email With Attachment Completed...");
            }

        }
    }

    private boolean send(final MimeMessage mimeMessages) {
        LOGGER.info("Email With Attachment START ...");
        boolean success = false;

        if (mimeMessages != null) {
            try {
                emailSender.send(mimeMessages);
                success = true;
            } catch (final Exception e) {
                LOGGER.error(EXCEPTION_IN_SENDING_E_MAIL_WITH_ATTACHMENT, e);
            }
            if (success) {
                LOGGER.info(EMAIL_WITH_ATTACHMENT_COMPLETED);
            }
        }
        return success;
    }

    private SimpleMailMessage getSimpleMailMessage(final EMail eMail) {
        final SimpleMailMessage message = new SimpleMailMessage();
        if (eMail.getSender() != null) {
            message.setFrom(eMail.getSender());
        }
        if (eMail.getTo() != null) {
            message.setTo(eMail.getTo());
        }
        if (eMail.getSubject() != null) {
            message.setSubject(eMail.getSubject());
        }
        if (eMail.getMessage() != null) {
            message.setText(eMail.getMessage());
        }
        if (eMail.getCc() != null) {
            message.setCc(eMail.getCc());
        }
        if (eMail.getBcc() != null) {
            message.setBcc(eMail.getBcc());
        }
        return message;
    }

    private MimeMessage getMimeMessage(final EMail eMail) {
        final MimeMessage message = emailSender.createMimeMessage();

        try {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            if (eMail.getFiles() != null) {
                for (final File f : eMail.getFiles()) {
                    final FileSystemResource file = new FileSystemResource(f);
                    helper.addAttachment(file.getFilename(), file);
                }
            }

            if (eMail.getTo() != null) {
                helper.setTo(eMail.getTo());
            }
            if (eMail.getSender() != null) {
                helper.setFrom(eMail.getSender());
            }
            if (eMail.getSubject() != null) {
                helper.setSubject(eMail.getSubject());
            }
            if (eMail.getSendDate() != null) {
                helper.setSentDate(eMail.getSendDate());
            }
            if (eMail.getMessage() != null) {
                helper.setText(eMail.getMessage(), true);
            }
            if (eMail.getBcc() != null) {
                helper.setBcc(eMail.getBcc());
            }
            if (eMail.getCc() != null) {
                helper.setCc(eMail.getCc());
            }

            // add INLINE image
            if (eMail.getInlineImages() != null && eMail.getInlineImages().size() > 0) {
                Set<String> setImageID = eMail.getInlineImages().keySet();
                for (String contentId : setImageID) {
                    helper.addInline(contentId, new File(eMail.getInlineImages().get(contentId)));
                }

            }

        } catch (final Exception e) {
            throw new MailParseException(e);
        }

        return message;
    }

    private SimpleMailMessage getSimpleMailMessage(final HashMap<String, String> emailMessage) {
        final SimpleMailMessage message = new SimpleMailMessage();
        if (emailMessage.get(Mail.FROM) != null) {
            message.setFrom(emailMessage.get(Mail.FROM));
        }
        if (emailMessage.get(Mail.TO) != null) {
            message.setTo(emailMessage.get(Mail.TO));
        }
        if (emailMessage.get(Mail.SUBJECT) != null) {
            message.setSubject(emailMessage.get(Mail.SUBJECT));
        }
        if (emailMessage.get(Mail.MESSAGE) != null) {
            message.setText(emailMessage.get(Mail.MESSAGE));
        }
        if (emailMessage.get(Mail.CC) != null) {
            message.setText(emailMessage.get(Mail.CC));
        }
        if (emailMessage.get(Mail.BCC) != null) {
            message.setText(emailMessage.get(Mail.BCC));
        }
        return message;
    }

    @Override
    public void sendHTMLEmail(final EMail email, final Organisation organisation) {
        if (email != null) {

            if (setSenderDetails(organisation)) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) &&
                        (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {
                    if (email.isBackground()) {
                        final MimeMessage[] newMailsArray = new MimeMessage[1];
                        newMailsArray[0] = getMimeMessage(email);
                        sendInBackground(newMailsArray);
                    } else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.info(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }

        }
    }

    @Override
    public void sendEmail(final EMail email, final Organisation org) {
        if (email != null) {
            if (setSenderDetails(org)) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) && (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {
                    if (email.isBackground()) {
                        final SimpleMailMessage[] newMailsArray = new SimpleMailMessage[1];
                        newMailsArray[0] = this.getSimpleMailMessage(email);
                        this.sendInBackground(newMailsArray);
                    }

                    else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.info(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }
        }
    }

    @Override
    public void sendHTMLEmail(final EMail email, final long orgId) {

        if (email != null) {
        	 ApplicationSession applicationSession = ApplicationSession.getInstance();
             String response = null;
        	if(null!=applicationSession.getMessage("app.name")  && 
        			(applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.TCP))){
            	//TCP SMS code implementation 
			try {
				SMSRequestDto smsTokenreq = new SMSRequestDto();
				smsTokenreq.setUserId(applicationSession.getMessage("tcp_sms_userId"));
				smsTokenreq.setTpUserId(applicationSession.getMessage("tcp_sms_tpUserId"));
				smsTokenreq.setEmailId(applicationSession.getMessage("tcp_sms_emailId"));
				RestTemplate restToken = new RestTemplate();
				HttpHeaders headersToken = new HttpHeaders();
				headersToken.add("access_key", applicationSession.getMessage("tcp_sms_access_key"));
				headersToken.add("secret_key", applicationSession.getMessage("tcp_sms_secret_key"));
				headersToken.add("Content-Type", "application/json");
				HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(smsTokenreq, headersToken);
				ResponseEntity<String> respToken = restToken.exchange(new URI(applicationSession.getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
						httpentity1, String.class);
				String token = null;
				if (respToken != null && respToken.getStatusCode() == HttpStatus.OK) {
					JSONObject jsonObject = new JSONObject(respToken.getBody());
					java.util.Map<String, String> myMap = new java.util.HashMap<>();
					myMap.put(jsonObject.getString("Key"), jsonObject.getString("Value"));
					token = (String) myMap.get("1");
					if (token != null) {
						SMSRequestDto smsreq = new SMSRequestDto();
						smsreq.setToEmailId(String.join(",", email.getTo()));
						smsreq.setSubject(email.getSubject());
						smsreq.setBody(email.getMessage());
						smsreq.setPurpose(applicationSession.getMessage("tcp_sms_purpose"));
						smsreq.setTokenId(token);
						smsreq.setUserloginId(applicationSession.getMessage("tcp_sms_UserLoginId"));
						smsreq.setModuleId(applicationSession.getMessage("tcp_sms_moduleId"));
						String uri = applicationSession.getMessage("tcp_email_sendUrl");
						LOGGER.info("URL== " + uri);
						RestTemplate restReq = new RestTemplate();
						HttpHeaders headers = new HttpHeaders();
						headers.add("Content-Type", "application/json");
						HttpEntity<SMSRequestDto> httpentity = new HttpEntity<SMSRequestDto>(smsreq, headers);
						ResponseEntity<String> resp = restReq.exchange(new URI(uri), HttpMethod.POST, httpentity,String.class);
						LOGGER.info("response== " + resp.getBody());
						if (resp != null) {
							response = "SMS Send Successfully Done";
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
            }

        	else if (setSenderDetails(orgId)) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) &&
                        (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {
                    if (email.isBackground()) {
                        final MimeMessage[] newMailsArray = new MimeMessage[1];
                        newMailsArray[0] = getMimeMessage(email);
                        sendInBackground(newMailsArray);
                    } else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.info(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }

        }

    }

    private boolean setSenderDetails(final long orgId) {
        boolean flag = false;

        try {
            String host = MainetConstants.BLANK;
            String port = MainetConstants.BLANK;
            String username = MainetConstants.BLANK;
            String password = MainetConstants.BLANK;
            final Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);

            /*
             * final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.MLA,
             * UserSession.getCurrent().getOrganisation());
             */
            final List<LookUp> lookUps = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.MLA, orgId);

            for (final LookUp lookUp : lookUps) {
                if (MainetConstants.SENDER_ID.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    username = lookUp.getOtherField();
                } else if (MainetConstants.SENDER_PWD.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    password = lookUp.getOtherField();
                } else if (PrefixConstants.D.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    host = lookUp.getOtherField();
                } else if (PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_P.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    port = lookUp.getOtherField();
                }
            }

            try {
                if ((port != null) && !port.equalsIgnoreCase(MainetConstants.BLANK)) {
                    emailSender.setPort(Integer.parseInt(port));
                }
            } catch (final Exception exception) {
                emailSender.setPort(25);// Default
            }

            emailSender.setHost(host);
            emailSender.setUsername(username);
            emailSender.setPassword(password);

            flag = true;
        } catch (final Exception exception) {
            flag = false;
            LOGGER.error("Error while setting mail server parameters:", exception);
        }
        return flag;
    }

}
