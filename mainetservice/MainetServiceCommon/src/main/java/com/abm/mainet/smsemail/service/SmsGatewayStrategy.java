package com.abm.mainet.smsemail.service;

public interface SmsGatewayStrategy {

    String sendSMS(String msgText, String mobileNumbers, int languageId, String templateId);

}
