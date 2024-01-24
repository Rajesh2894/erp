package com.abm.mainet.smsemail.service;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Service
public class SMSService implements ISMSService {

    /**
     * Hostname verification, in case of secure HTTP connection, from hostname property.
     */
    static {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
            if (hostname.equals(ApplicationSession.getInstance().getMessage("hostname"))) {
                return true;
            }
            return false;
        });
    }

    @Override
    public void sendSMSInBackground(final String message, final String mobileNumbers, final int languageId, final String templateId) {

        sendSMS(message, mobileNumbers, languageId, templateId);
    }

    /**
     * @param msgText
     * @param mobileNumbers
     * @param languageId
     * @return
     * 
     */
    private String sendSMS(final String msgText, final String mobileNumbers, final int languageId, final String templateId) {
        String output = ApplicationSession.getInstance().getMessage("bihar.sms.msg.content");
        /**
         * we can provide dynamic bean name to connect sms gateway strategy qualifies name in serviceConfiguration.properties
         * file.
         */
        final String smsBeanName = ApplicationSession.getInstance().getMessage("sms.strategy.beanname");
        if (smsBeanName == null || smsBeanName.isEmpty()) {
            throw new FrameworkException("sms.strategy.beanname property is not configured in serviceConfiguration.properties");
        }
        final SmsGatewayStrategy smsGatewayStrategy = ApplicationContextProvider.getApplicationContext().getBean(smsBeanName,
                SmsGatewayStrategy.class);

        if (smsGatewayStrategy == null) {
            throw new FrameworkException(
                    "No such bean found ... check sms.strategy.beanname property configured in serviceConfiguration.properties");
        }

        /**
         * In this given method will be work on multiple Integrations wise, Ex:- Maha Online SMS, MSG91, SMSConnection
         * Integrations.
         */
        output = smsGatewayStrategy.sendSMS(msgText, mobileNumbers, languageId, templateId);
        return output;
    }

}
