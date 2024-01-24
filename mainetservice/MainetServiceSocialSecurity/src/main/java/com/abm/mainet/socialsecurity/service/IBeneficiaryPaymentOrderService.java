package com.abm.mainet.socialsecurity.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.socialsecurity.ui.dto.BeneficiaryPaymentOrderDto;

public interface IBeneficiaryPaymentOrderService {

    List<BeneficiaryPaymentOrderDto> filterSearchData(final Long serviceId,  final Long orgId);

    void accountBillEntryforSocialSecurity(BeneficiaryPaymentOrderDto dto, Organisation org);

    void saveBeneficiaryDetails(List<BeneficiaryPaymentOrderDto> dtoList,Organisation org);

    void initiateWorkFlowForFreeService(BeneficiaryPaymentOrderDto dto);

    BeneficiaryPaymentOrderDto getViewDataFromRtgsPayment(Long orgId, String applicationId);

    boolean saveDecision(BeneficiaryPaymentOrderDto bpoDto, Long orgId, Employee emp, WorkflowTaskAction workFlowActionDto,
            RequestDTO requestDTO, Organisation org);

    Boolean checkAccountActiveOrNot();

    Boolean checkWorkflowIsNotDefine(Organisation org, Long orgId, String serviceCode);
    
	// added by rahul.chaubey
	// Gets the date of life certificate.
    Date getCertificateDate( String beneficiaryNo,  Long orgId);
    
	// added by rahul.chaubey
	// generates a unique workorder number for RTGS payment
    String generateWorkOrderNumber(Long sequenceNo, BeneficiaryPaymentOrderDto dto, String organizationShortCode,Long orgId);
  
    //added by rahul.chaubey
    //for validating the number of payment done on the basis of paymentMode(Monthly/Quaterly etc)
    BeneficiaryPaymentOrderDto getRtgsData( String beneficiaryNo,  Long orgId);
    
    //added by rahul.chaubey
    //gets the no of payment done in a month(BiMonthly)
    int getBiMonthlyCount( String beneficiaryNo,  Long orgId,  int month);

	List<BeneficiaryPaymentOrderDto> filterSearchDatas(Long serviceId, Long orgId, String swdward1, String swdward2, Long subServiceId);
    
}
