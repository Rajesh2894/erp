package com.abm.mainet.smsemail.service;

public interface ISMSService {
    public String sendSMSInBackground(String message, String mobileNumbers, int languageId, String templateId);

}
