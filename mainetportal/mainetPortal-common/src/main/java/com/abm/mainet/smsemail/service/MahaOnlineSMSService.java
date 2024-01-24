package com.abm.mainet.smsemail.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.ApplicationSession;

@Service
public class MahaOnlineSMSService implements IMahaOnlineSMSService {

    private static final Logger LOGGER = Logger.getLogger(MahaOnlineSMSService.class);

    @Override
    public void sendSMSByURL(final String msgText, final String mobileNumbers) {

        try {
            ApplicationSession applicationSession = ApplicationSession.getInstance();
            String userName = applicationSession.getMessage("sms.userName");
            String password = applicationSession.getMessage("sms.password");
            String smsId = applicationSession.getMessage("sms.smsId");
            String langId = applicationSession.getMessage("sms.langId");
            String smsurl = applicationSession.getMessage("sms.url");
            String senderName = applicationSession.getMessage("sms.senderName");
            // Construct data
            String data = URLEncoder.encode("userName", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(userName, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("passWord", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(password, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("strMobileNo", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(mobileNumbers, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("msgBody", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(msgText, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("smsId", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(smsId, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("langId", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(langId, MainetConstants.UTF8);
            data += MainetConstants.operator.AMPERSAND + URLEncoder.encode("senderName", MainetConstants.UTF8)
                    + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE
                    + URLEncoder.encode(senderName, MainetConstants.UTF8);

            data = data.replace("+", MainetConstants.WHITE_SPACE);
            data = data.replace("%40", "@");
            data = data.replace("%3A", MainetConstants.operator.COLON);

            LOGGER.info("SMS DATA... " + data);
            LOGGER.info("SMS URL... " + smsurl);

            // Send data
            final URL url = new URL(smsurl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            final OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            conn.getInputStream();

            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = rd.readLine()) != null) {
                LOGGER.info(output);
            }

            rd.close();
            wr.close();
            conn.disconnect();
        } catch (final Exception e) {
            LOGGER.error("SMS Sending Failed..." + e.getMessage());
        }
    }

    @Override
    public void sendSMSByRestfulWebServices(final String msgText, final String mobileNumbers) {

    }
}
