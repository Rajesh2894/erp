package com.abm.mainet.smsemail.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.dto.EMail;

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

    private static final long serialVersionUID = 8316837618661833138L;

    @Autowired
    private JavaMailSenderImpl emailSender;

    private static final Logger LOGGER = Logger.getLogger(EmailService.class);

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
        
        LOGGER.info("Enter into  setSenderDetails " + organisation);
        try {
            String host = MainetConstants.BLANK;
            String port = MainetConstants.BLANK;
            String username = MainetConstants.BLANK;
            String password = MainetConstants.BLANK;

            final Map<String, List<LookUp>> map = ApplicationSession.getInstance().getNonHierarchicalLookUp(organisation,
                    MEMBER_OF_LEGISLATIVE_ASSEMBLY);
            final List<LookUp> lookUps = map.get(MEMBER_OF_LEGISLATIVE_ASSEMBLY);
            if (null != lookUps) {
                for (final LookUp lookUp : lookUps) {
                    if (lookUp.getLookUpCode().equalsIgnoreCase(SENDERID)) {
                        username = lookUp.getOtherField();
                        LOGGER.info("set userName " + username);
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(SENDERPWD)) {
                        password = lookUp.getOtherField();
                        LOGGER.info("set password " + password);
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.MENU.D)) {
                        host = lookUp.getOtherField();
                        LOGGER.info("set host " + host);
                    } else if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.MENU.P)) {
                        port = lookUp.getOtherField();
                        LOGGER.info("set port " + port);
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
            	emailSender.setHost(ApplicationSession.getInstance().getMessage("host"));
                emailSender.setUsername(ApplicationSession.getInstance().getMessage("username"));
                emailSender.setPassword(ApplicationSession.getInstance().getMessage("password"));
                emailSender.setPort(Integer.parseInt(ApplicationSession.getInstance().getMessage("port")));
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
                LOGGER.info("Sending mail with default configuration");
                flag = true;
            }
        } catch (final Exception exception) {
        	emailSender.setHost(ApplicationSession.getInstance().getMessage("host"));
            emailSender.setUsername(ApplicationSession.getInstance().getMessage("username"));
            emailSender.setPassword(ApplicationSession.getInstance().getMessage("password"));
            emailSender.setPort(Integer.parseInt(ApplicationSession.getInstance().getMessage("port")));

        	
        	flag = false;
            
            LOGGER.error(MainetConstants.ERROR_OCCURED, exception);

        }
        LOGGER.info("come out from   setSenderDetails method ");

        return flag;
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
            final Map<String, List<LookUp>> map = ApplicationSession.getInstance().getNonHierarchicalLookUp(organisation,
                    MEMBER_OF_LEGISLATIVE_ASSEMBLY);
            final List<LookUp> lookUps = map.get(MEMBER_OF_LEGISLATIVE_ASSEMBLY);

            for (final LookUp lookUp : lookUps) {
                if (SENDERID.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    username = lookUp.getOtherField();
                } else if (SENDERPWD.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    password = lookUp.getOtherField();
                } else if (MainetConstants.MENU.D.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    host = lookUp.getOtherField();
                } else if (MainetConstants.MENU.P.equalsIgnoreCase(lookUp.getLookUpCode())) {
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
        } catch (final Exception exception) {
            flag = false;
            LOGGER.error("Error while setting mail server parameters:", exception);
        }
        return flag;
    }

    private void sendInBackground(final SimpleMailMessage[] newMailsArray) {
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

        if (message != null) {
            try {
                emailSender.send(message);
                success = true;
            } catch (final Exception e) {
                success = false;
                LOGGER.error(EXCEPTION_E_MAIL_SENDING, e);
            }
            if (success) {
                LOGGER.info(E_MAIL_SENDING_COMPLETED);
            }
        } else {
            LOGGER.error("null Message");
        }

        return success;
    }
    @Async //df#117826
    private void sendInBackground(final MimeMessage[] mimeMessages) {
        final MimeMessage[] mimeMessagesArray = new MimeMessage[mimeMessages.length];

        for (int i = 0; mimeMessages.length > i; i++) {
            mimeMessagesArray[i] = mimeMessages[i];
        }

        if ((mimeMessagesArray != null) && (mimeMessagesArray.length > 0)) {

            boolean success = true;
            try {
                //Thread.sleep(5000);//df#117826
                emailSender.send(mimeMessagesArray);
            } catch (final Exception e) {
                success = false;
                LOGGER.error(EXCEPTION_IN_SENDING_E_MAIL_WITH_ATTACHMENT, e);
            }
            if (success) {
                LOGGER.info(EMAIL_WITH_ATTACHMENT_COMPLETED);
            }

        }
    }

    private boolean send(final MimeMessage mimeMessages) {
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
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, MainetConstants.UTF8);

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
        } catch (final MessagingException e) {
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
    public void sendHTMLEmail(final EMail email) {
        if (email != null) {
            if (setSenderDetails(UserSession.getCurrent().getOrganisation())) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) && (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {

                    if (email.isBackground()) {
                        final MimeMessage[] newMailsArray = new MimeMessage[1];
                        newMailsArray[0] = getMimeMessage(email);
                        this.sendInBackground(newMailsArray);
                    }

                    else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }
        }
    }

    @Override
    public void sendHTMLEmail(final EMail email, final Organisation organisation) {
    	
        LOGGER.info("Enter into  sendHTMLEmail " + email + organisation);

        if (email != null) {
            if (setSenderDetails(organisation)) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) && (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {

                    if (email.isBackground()) {
                        final MimeMessage[] newMailsArray = new MimeMessage[1];
                        newMailsArray[0] = getMimeMessage(email);
                        sendInBackground(newMailsArray);
                    }

                    else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
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
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }
        }
    }

    @Override
    public void sendHTMLEmail(final EMail email, final long orgId) {

        if (email != null) {
            if (setSenderDetails(orgId)) {
                email.setSender(emailSender.getUsername());
                if ((email.getFiles() != null) && (email.getFiles().size() > 0)) {
                    sendEmailWithAttachment(email);
                } else {

                    if (email.isBackground()) {
                        final MimeMessage[] newMailsArray = new MimeMessage[1];
                        newMailsArray[0] = getMimeMessage(email);
                        sendInBackground(newMailsArray);
                    }

                    else {
                        email.setSendStatus(this.send(this.getSimpleMailMessage(email)));
                    }
                }
            } else {
                LOGGER.error(EMAIL_MLA_PREFIX_DATA_NOT_FOUND);
            }
        }

    }

}
