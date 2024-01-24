package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.payment.dto.RePaymentDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;

/**
 * @author ritesh.patil
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface CitizenDashBoardService {

    List<CitizenDashBoardResDTO> getAllApplicationsOfCitizen(CitizenDashBoardReqDTO request);

    List<CitizenDashBoardResDTO> getAllFaliuredOrCancelledOnlineList(String mobNo, Long orgId);

    RePaymentDTO getPayPendingDataByONLTransId(Long valueOf);
    
    CitizenDashBoardResDTO getDocument(CitizenDashBoardResDTO request);
    
    List<PaymentTransactionMas> getCitizenPayPendingDataByDateAndStatus(Date validDate, Long serviceId, String flatNo,Long orgId,String refNo);

}
