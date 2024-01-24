package com.abm.mainet.smsemail.service;

public interface ISMSService {
    public void sendSMSInBackground(String message, String mobileNumbers, int languageId, String templateId);

}
