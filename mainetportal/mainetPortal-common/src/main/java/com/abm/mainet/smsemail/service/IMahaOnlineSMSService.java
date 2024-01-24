package com.abm.mainet.smsemail.service;

public interface IMahaOnlineSMSService {
    public void sendSMSByURL(String msgText, String mobileNumbers);

    public void sendSMSByRestfulWebServices(String msgText, String mobileNumbers);
}
