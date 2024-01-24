package com.abm.mainet.common.workflow.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.workflow.dto.TaskNotificationRequest;

/**
 * This service provides API for task related notification. This service will make use of ISMSAndEmailService to send notification
 * as per the templates defined in master data.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * @author sanket.joshi
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface TaskNotificationService {

    /**
     * This method will prepare instance of SMSAndEmailDTO and send it to SMSAndEmailService to send SMS and Email to specified
     * users. SMSAndEmailDTO will expect few template parameters, those parameters will be retrieved from DB using
     * application/reference number and workflow ID.This is asynchronous service to register request and return quickly with void.
     * 
     * This method support asynchronous execution.
     * 
     * @param taskNotificationRequest
     */
    void notifyUser(TaskNotificationRequest taskNotificationRequest);
}
