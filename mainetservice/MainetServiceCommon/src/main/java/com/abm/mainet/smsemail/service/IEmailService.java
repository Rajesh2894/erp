package com.abm.mainet.smsemail.service;

import java.util.HashMap;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.smsemail.dto.EMail;

public interface IEmailService {

    public void sendHTMLEmail(EMail email, Organisation organisation);

    /**
     * To send single mail.
     * @param emailList : Where HashMap 'key' must be {@link IEmailService.Mail}
     */
    public void sendEmail(HashMap<String, String> emailList, Organisation org);

    public void sendHTMLEmail(EMail email, long orgId);

    public void sendEmail(EMail email, Organisation org);

    /**
     * Mail Parameters as constants.
     */
    interface Mail {
        /**
         * TO = "MAIL TO" String[]
         */
        String TO = "MAIL_TO";

        /**
         * SUBJECT = "MAIL SUBJECT";
         */
        String SUBJECT = "MAIL_SUBJECT";

        /**
         * MESSAGE = "MAIL MESSAGE";
         */
        String MESSAGE = "MAIL_MESSAGE";

        /**
         * From/Sender Email Id
         */
        String FROM = "From/Sender";

        /**
         * Attached File[] Object
         */
        String FILE = "Attached File Object";

        /**
         * CC Email Id String[]
         */
        String CC = "Email Ids CC";

        /**
         * BCC Email Id String[]
         */
        String BCC = "Email Ids BCC";
    }

}
