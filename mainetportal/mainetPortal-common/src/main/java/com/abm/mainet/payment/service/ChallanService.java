package com.abm.mainet.payment.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author kavali.kiran
 *
 */
@Service
public class ChallanService implements IChallanService {

    private static final String EXCEPTION_WHILE_CREATING_CHALLNA_SERVICE_ID = "Exception while creating challna for service id:";

    private static final String CHN_DD_DATE = "chn.ddDate";

    private static final String CHN_DDNO = "chn.ddno";

    private static final String PDD = "PDD";

    private static final String CHN_PO_DATE = "chn.poDate";

    private static final String CHN_PONO = "chn.pono";

    private static final String PPO = "PPO";

    private static final String PCU = "PCU";

    private static final String CHN_LCDD = "chn.lcdd";

    private static final String CHN_LCD = "chn.lcd";

    private static final String CHN_LABEL_MUN = "chn.labelMun";

    private static final String CHN_LABEL_BANK = "chn.labelBank";

    private static final String PCB = "PCB";

    private static final String ERROR_WHILE_GENERATING_CHALLAN = "Error while generating challan";

    @Autowired
    ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IPortalServiceMasterService portalServiceMasterService;

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    public ApplicationFormChallanDTO getChallanData(final CommonChallanDTO challanDTO, final Organisation organisation) {
        final ApplicationFormChallanDTO rfc = new ApplicationFormChallanDTO();
        final ApplicationSession session = ApplicationSession.getInstance();
        List<String> bankParams = null;
        final String paymentMode = CommonMasterUtility.getNonHierarchicalLookUpObject(challanDTO.getOflPaymentMode())
                .getLookUpCode();
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(PCB)) {
            rfc.setLabelMun(session.getMessage(CHN_LABEL_BANK));
            rfc.setLabelBank(session.getMessage(CHN_LABEL_MUN));
            rfc.setLcd(session.getMessage(CHN_LCD));
            rfc.setLcdd(session.getMessage(CHN_LCDD));

            @SuppressWarnings("unchecked")
            final LinkedHashMap<Long, Object> responseChallan = (LinkedHashMap<Long, Object>) JersyCall
                    .callRestTemplateClient(challanDTO, ServiceEndpoints.CommonPrefixUri.CHALLAN_GETULB_BANK_FOR_CHALLANATBANK);
            final String data = new JSONObject(responseChallan).toString();
            CommonChallanDTO response = null;
            try {
                response = new ObjectMapper()
                        .readValue(data, CommonChallanDTO.class);
                bankParams = response.getBankData();
            } catch (final IOException e) {
                LOGGER.error(ERROR_WHILE_GENERATING_CHALLAN, e);
            }
            if ((bankParams != null) && !bankParams.isEmpty()) {
                rfc.setBankName(bankParams.get(0));
                rfc.setBranch(bankParams.get(1));
                rfc.setBankAccId(bankParams.get(2));
            }
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(PCU)) {
            rfc.setLabelMun(session.getMessage(CHN_LABEL_MUN));
            rfc.setLcd(session.getMessage(CHN_LCD));
            rfc.setLcdd(session.getMessage(CHN_LCDD));
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(PPO)) {
            rfc.setLcd(session.getMessage(CHN_PONO));
            rfc.setLcdd(session.getMessage(CHN_PO_DATE));
            rfc.setDdOrPpDate(challanDTO.getPoDate());
            rfc.setDdOrPpnumber(challanDTO.getPoNo());
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(PDD)) {
            rfc.setLcd(session.getMessage(CHN_DDNO));
            rfc.setLcdd(session.getMessage(CHN_DD_DATE));
            rfc.setDdOrPpDate(challanDTO.getDdDate());
            rfc.setDdOrPpnumber(challanDTO.getDdNo());
        }

        rfc.setLabel_Municipality(session.getMessage("chn.labelMunicipality"));
        rfc.setLabelRtiNo(session.getMessage("chn.labelRtiNo"));
        rfc.setRtiNo(challanDTO.getApplNo());
        rfc.setLoiNo(challanDTO.getLoiNo());
        rfc.setLabelCitizen(session.getMessage("chn.labelCitizen"));
        rfc.setlDateOfDeposite(session.getMessage("chn.lDateOfDeposite"));
        rfc.setlDateOfFilling(session.getMessage("chn.lDateOfFilling"));
        rfc.setlChallanNo(session.getMessage("chn.lChallanNo"));

        if (UserSession.getCurrent().getLanguageId() == 1) {
            rfc.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        }
        if (UserSession.getCurrent().getLanguageId() == 2) {
            rfc.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }

		//String service = null;
		String serviceName = session.getMessage("challan.message.serviceName");
		/* String serviceName = "Application Form"; */
		/*
		 * if ((service =
		 * portalServiceMasterService.findServiceNameForServiceId(challanDTO.
		 * getServiceId())) != null) { serviceName = service; }
		 */
		//Task#84992 start
		PortalService serviceMaster = portalServiceMasterService.getPortalService(challanDTO.getServiceId());
		if (serviceMaster != null && serviceMaster.getServiceName() != null) {
			serviceName = serviceMaster.getServiceName();
			if (serviceMaster.getPsmDpDeptCode() != null
					&& serviceMaster.getPsmDpDeptCode().equals(MainetConstants.DEPT_SHORT_NAME.RTI)) {
				rfc.setlPaymentForRti((session.getMessage("chn.payment.for." + serviceMaster.getShortName())));
			} else {
				rfc.setlPaymentForRti(session.getMessage("challan.message.paymentFor") + serviceName);
			}
		} else {
			rfc.setlPaymentForRti(session.getMessage("challan.message.paymentFor") + serviceName);
		}
		//end
        rfc.setlName(session.getMessage("chn.lName"));
        rfc.setName(challanDTO.getApplicantName());
        rfc.setlAddress(session.getMessage("chn.lAddress"));
        rfc.setAddress(challanDTO.getApplicantAddress());
        rfc.setlContact(session.getMessage("chn.lContact"));
        rfc.setContact(challanDTO.getMobileNumber());
        rfc.setlEmail(session.getMessage("chn.lEmail"));
        rfc.setEmail(challanDTO.getEmailId());
        rfc.setlAmountPayable(session.getMessage("chn.lAmountPayable"));
        rfc.setlAmountInWords(session.getMessage("chn.lAmountInWords"));
        if (challanDTO.getAmountToPay() != null) {
            rfc.setAmount(Float.valueOf(challanDTO.getAmountToPay()));
            final String amountInWords = Utility.convertNumberToWord(Double.valueOf(challanDTO.getAmountToPay()));
            rfc.setAmountInWords(amountInWords);
        }

        rfc.setIpaymentMode(session.getMessage("chn.ipaymentMode"));
        rfc.setPaymentModeValue(session.getMessage("chn.paymentModeValue"));

        rfc.setIpaymentText(session.getMessage("chn.ipaymentText"));

        rfc.setlBankName(session.getMessage("chn.lBankName"));
        rfc.setlBranch(session.getMessage("chn.lBranch"));
        rfc.setlTransactionId(session.getMessage("chn.lTransactionId"));
        rfc.setlSignatureStamp(session.getMessage("chn.lSignatureStamp"));
        rfc.setlSignOfDepositer(session.getMessage("chn.lSignatureOfDepositer"));
        rfc.setlAR(session.getMessage("chn.lAR"));
        rfc.setTodate(new Date());
        if (challanDTO.getChallanNo() != null) {
            rfc.setChallanNo(Long.valueOf(challanDTO.getChallanNo()));
        }

        rfc.setLvalidDate(session.getMessage("chn.lvalidDate"));
        rfc.setLwebSiteLink(session.getMessage("chn.lwebSiteLink"));
        rfc.setValidDate(challanDTO.getChallanValidDate());
        rfc.setLbankaccId(session.getMessage("chn.lbankaccId"));

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setChallanNo(challanDTO.getChallanNo());
        dto.setEmail(challanDTO.getEmailId());
        dto.setMobnumber(challanDTO.getMobileNumber());
        dto.setServName(serviceName);
        if (challanDTO.getApplNo() != null) {
            dto.setAppNo(challanDTO.getApplNo().toString());
        }
        int langId = Utility.getDefaultLanguageId(organisation);
        ismsAndEmailService.sendEmailSMS("RTI", "ChallanUpdate.html", MainetConstants.SMS_EMAIL.NORMAL, dto, organisation,
                langId);

        return rfc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommonChallanDTO generateChallanNumber(final CommonChallanDTO offline) {
        LinkedHashMap<Long, Object> responseChallan = null;

        responseChallan = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(offline,
                ServiceEndpoints.PAYMENT_URL.CHALLAN_GENERATION_URL);
        final String data = new JSONObject(responseChallan).toString();
        CommonChallanDTO response = null;
        try {
            response = new ObjectMapper()
                    .readValue(data, CommonChallanDTO.class);
        } catch (final Exception e) {
            throw new FrameworkException(EXCEPTION_WHILE_CREATING_CHALLNA_SERVICE_ID + offline.getServiceId(), e);
        }
        if (response.getChallanNo() == null) {
            throw new FrameworkException(EXCEPTION_WHILE_CREATING_CHALLNA_SERVICE_ID + offline.getServiceId());
        }
        return response;
    }

}
