package com.abm.mainet.common.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.AdditionalOwnerDTO;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonAppRequestDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IPaymentService;

public abstract class AbstractFormModel extends AbstractModel {

    private static final String SOME_PROBLEM_DUE_TO_SECURITY_REASON = "Some Problem Due To Security Reason...";

    private static final long serialVersionUID = -7620050348809883460L;

    private Employee agencyApplicantDetail = new Employee();
    private static final String JERSEY_URL = "jersey.url";

    @Autowired
    private IPaymentService paymService;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    private String ownerName;
    private Long serviceId;
    private Long pgId;
    private String encrptAmount;
    private String chargesAmount;
    private Double saveamountValue;
    private long saveNoofCopies;
    private String processStatus;
    private String serviceName;
    private CommonChallanDTO offlineDTO = new CommonChallanDTO();
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    private List<AdditionalOwnerDTO> listOfAdditionalOwnerDetail = new ArrayList<>(0);
    private int rowCount = 0;
    private List<ChargeDetailDTO> chargesInfo;
    private String customViewName;
    private String logInUserImage;

    public void initializeApplicantDetail() {
        final UserSession session = UserSession.getCurrent();
        final Employee employee = session.getEmployee();
        final ApplicantDetailDTO dto = getApplicantDetailDto();
        dto.setApplicantTitle(employee.getTitle());
        dto.setApplicantFirstName(employee.getEmpname());
        dto.setApplicantMiddleName(employee.getEmpMName());
        dto.setApplicantLastName(employee.getEmpLName());
        dto.setGender(getGenderId(employee.getEmpGender()));
        dto.setMobileNo(employee.getEmpmobno());
        dto.setEmailId(employee.getEmpemail());
        dto.setCfcCitizenId(employee.getUserAlias());
        dto.setPinCode(employee.getPincode());
        dto.setOrgId(session.getOrganisation().getOrgid());
        dto.setUserId(employee.getEmpId());
        dto.setLangId(session.getLanguageId());
        dto.setAadharNo(employee.getEmpuid());

    }

    public boolean saveForm() throws Exception {
        return false;
    }

    public void initializeAgencyApplicantDetail() {

        final String loggedIn = getLoggedInUserType();
        if (((loggedIn != null) && loggedIn.equals(MainetConstants.NEC.CITIZEN))
                || loggedIn.equals(MainetConstants.NEC.ADVERTISE)) {

            getAgencyApplicantDetail().setTitle(UserSession.getCurrent().getEmployee().getTitle());
            getAgencyApplicantDetail().setEmpname(UserSession.getCurrent().getEmployee().getEmpname());
            getAgencyApplicantDetail().setEmpMName(UserSession.getCurrent().getEmployee().getEmpMName());
            getAgencyApplicantDetail().setEmpLName(UserSession.getCurrent().getEmployee().getEmpLName());
            getAgencyApplicantDetail().setEmpmobno(UserSession.getCurrent().getEmployee().getEmpmobno());
            getAgencyApplicantDetail().setEmpemail(UserSession.getCurrent().getEmployee().getEmpemail());
        }

    }

    public Employee getAgencyApplicantDetail() {
        return agencyApplicantDetail;
    }

    public void setAgencyApplicantDetail(final Employee agencyApplicantDetail) {
        this.agencyApplicantDetail = agencyApplicantDetail;
    }

    /**
     * @return the saveamountValue
     */
    public Double getSaveamountValue() {
        return saveamountValue;
    }

    /**
     * @param saveamountValue the saveamountValue to set
     */
    public void setSaveamountValue(final Double saveamountValue) {
        this.saveamountValue = saveamountValue;
    }

    /**
     * @return the saveNoofCopies
     */
    public long getSaveNoofCopies() {
        return saveNoofCopies;
    }

    /**
     * @param saveNoofCopies the saveNoofCopies to set
     */
    public void setSaveNoofCopies(final long saveNoofCopies) {
        this.saveNoofCopies = saveNoofCopies;
    }

    public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
            final PaymentRequestDTO paymentRequestDTO) {

    }

    @SuppressWarnings("unchecked")
    public boolean processPayUTransactionOnApplicationSubmit(final HttpServletRequest httpServletRequest,
            PaymentRequestDTO paymentRequestDTO) throws FrameworkException {
        paymentRequestDTO.setUdf2(String.valueOf(paymentRequestDTO.getApplicationId()));
        paymentRequestDTO.setUdf1(String.valueOf(paymentRequestDTO.getServiceId()));
        final String url = httpServletRequest.getRequestURL().toString();
        if(paymentRequestDTO.getOrgId()==null) {
        	paymentRequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        paymentRequestDTO.setLangId(UserSession.getCurrent().getLanguageId());
        paymentRequestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        paymentRequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL);
        paymentRequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL);
        paymentRequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_PAYU);
        paymentRequestDTO.setChallanServiceType(getOfflineDTO().getChallanServiceType());
        paymentRequestDTO.setFeeIds(getOfflineDTO().getFeeIds().toString());
        if(paymentRequestDTO.getDueAmt() != null) {
        	paymentRequestDTO.setValidateAmount(paymentRequestDTO.getDueAmt());
        } else {
        	paymentRequestDTO.setValidateAmount(BigDecimal.ZERO);
        }
        if (getOfflineDTO().isDocumentUploaded()) {
            paymentRequestDTO.setDocumentUploaded("Y");
        } else {
            paymentRequestDTO.setDocumentUploaded("N");
        }
        Boolean isReachable = false;
        Boolean bankStatus = false;
        Boolean saveStatus = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = true;
        }
        if (isReachable) {
            CommonAppRequestDTO responseDTO = new CommonAppRequestDTO();
            responseDTO.setOrgId(paymentRequestDTO.getOrgId());
            responseDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            responseDTO.setServiceId(paymentRequestDTO.getServiceId());
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(responseDTO,
                    ServiceEndpoints.CommonPaymentGateway.GET_BANK_LIST);

            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error("Error Occurred while fetching bank list " + responseEntity);
                throw new FrameworkException("Error Occurred while fetching bank list " + responseEntity);

            }
            try {
                Map<Long, String> map = JersyCall.castResponse(responseEntity, Map.class);
                UserSession.getCurrent().setOnlineBankList(map);
                bankStatus = true;
            } catch (IOException exp) {
                logger.error(
                        "Error Occurred during cast responseEntity for fetching bank list while REST call is success! ",
                        exp);
                throw new FrameworkException(
                        "Error Occurred during cast responseEntity for fetching bank list while REST call is success! "
                                + responseEntity);
            }

            responseEntity = JersyCall.callAnyRestTemplateClient(paymentRequestDTO,
                    ServiceEndpoints.CommonPaymentGateway.SAVE_PAYMENT_REQUEST);

            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error(
                        "Error Occurred during saving payment request on proceesPaymentTransactionOnApplicationSubmit() call "
                                + responseEntity);
                throw new FrameworkException(
                        "Error Occurred during saving payment request on proceesPaymentTransactionOnApplicationSubmit() call "
                                + responseEntity);
            }
            try {
                PaymentRequestDTO paymentDTO = JersyCall.castResponse(responseEntity, PaymentRequestDTO.class);
                BeanUtils.copyProperties(paymentRequestDTO, paymentDTO);
                saveStatus = true;
            } catch (Exception e) {
                logger.error(
                        "Error Occurred during casting responseEntity received while payment save call is success! ",
                        e);
                throw new FrameworkException(
                        "Error Occurred during casting responseEntity received while payment save call is success! ",
                        e);
            }
        } else {
            UserSession.getCurrent()
                    .setOnlineBankList(paymService.getAllPgBank(UserSession.getCurrent().getOrganisation().getOrgid()));
            paymService.proceesPaymentTransactionOnApplicationSubmit(httpServletRequest, paymentRequestDTO);
            bankStatus = true;
            saveStatus = true;
        }
        return (bankStatus && saveStatus);
    }

    public boolean processPayUTransactionBeforePayment(final HttpServletRequest httpServletRequest,
            PaymentRequestDTO paymentRequestDTO) throws FrameworkException {
        boolean status = false;
        setServiceName(paymentRequestDTO.getServiceName());
        setOwnerName(paymentRequestDTO.getApplicantName());
        String amount = null;
        try {
            amount = EncryptionAndDecryption.decrypt(paymentRequestDTO.getFinalAmount());
            setChargesAmount(amount);
            if (amount != null && !amount.isEmpty()) {
                paymentRequestDTO.setValidateAmount(new BigDecimal(amount));
            }
            setEncrptAmount(paymentRequestDTO.getFinalAmount());
            final String url = httpServletRequest.getRequestURL().toString();
            paymentRequestDTO.setControlUrl(url);
            if(paymentRequestDTO.getOrgId()==null) {
                paymentRequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }
            paymentRequestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            paymentRequestDTO.setPayModeorType(MainetConstants.PAYMODE.WEB);
        } catch (final Exception e1) {
            throw new FrameworkException(SOME_PROBLEM_DUE_TO_SECURITY_REASON, e1);
        }

        //Boolean isReachable = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
        /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = JersyCall.pingURL(jerseyUrl, 2000);
        }*/
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(paymentRequestDTO,
                    ServiceEndpoints.CommonPaymentGateway.PROCESS_TRANSACTION_BEFORE_PAYMENT);

            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error(
                        "Error Occurred while saving payment request on processPayUTransactionBeforePayment() call "
                                + responseEntity);
                throw new FrameworkException(
                        "Error Occurred while saving payment request on processPayUTransactionBeforePayment() call "
                                + responseEntity);
            }
            try {
                PaymentRequestDTO paymentDTO = JersyCall.castResponse(responseEntity, PaymentRequestDTO.class);
           
                BeanUtils.copyProperties(paymentRequestDTO, paymentDTO);
                status = true;
            } catch (Exception e) {
                logger.error(
                        "Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
                        e);
                throw new FrameworkException(
                        "Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
                        e);
            }
        } else {
            paymService.proceesPaymentTransactionBeforePayment(httpServletRequest, paymentRequestDTO);
            status = true;
        }

        return status;
    }

    public boolean cancelTransactionBeforePayment(final HttpServletRequest httpServletRequest,
            PaymentRequestDTO paymentRequestDTO) throws FrameworkException {
        //Boolean isReachable = false;
        Boolean status = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
       /* if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = JersyCall.pingURL(jerseyUrl, 2000);
        }*/
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(paymentRequestDTO,
                    ServiceEndpoints.CommonPaymentGateway.CANCEL_TRANSACTION_BEFORE_PAYMENT);
            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error("Error Occurred while saving cancel payment transaction data " + responseEntity);
                throw new FrameworkException(
                        "Error Occurred while saving cancel payment transaction data " + responseEntity);
            }
            status = true;
        } else {
            paymService.canceTransactionBeforePayment(httpServletRequest, paymentRequestDTO);
            status = true;
        }
        return status;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> capturePayUResponseInMapFromRequest(final HttpServletRequest httpServletRequest,
            final String gatewayFlag, final Long orgId, final int langId) {
        Map<String, String> responseMap = new HashMap<>(0);
        final Enumeration<String> paramNames = httpServletRequest.getParameterNames();
        String paramName;
        String paramValue;
        logger.info("Response Parameters are:");
        while (paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();
            paramValue = httpServletRequest.getParameter(paramName);
            responseMap.put(paramName, paramValue);
            logger.info(paramName+":"+paramValue);
        }
        logger.info("gatewayFlag: "+gatewayFlag);
        logger.info("orgId: "+orgId);
        logger.info("langId: "+langId);
        logger.info("getChargesAmount: "+getChargesAmount());
        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, getChargesAmount());
        
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL) && gatewayFlag.equals(MainetConstants.PG_SHORTNAME.CCA)) {
			String txnID = UserSession.getCurrent().getMapList().get("txnId");
			UserSession.getCurrent().getMapList().put("txnId", MainetConstants.operator.EMPTY);
			if (!responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_NO).equals(txnID)) {
				logger.error("Callback from CCAvenue. Wrong transaction Id data is tampered.");
				throw new FrameworkException("Callback from CCAvenue. Wrong transaction Id data is tampered.");
			}
		}
        //Boolean isReachable = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
        /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = JersyCall.pingURL(jerseyUrl, 2000);
        }*/
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            String uri = ServiceEndpoints.CommonPaymentGateway.CAPTURE_RESPONSE_MAP_REQUEST
                    + MainetConstants.operator.FORWARD_SLACE + gatewayFlag + MainetConstants.operator.FORWARD_SLACE
                    + getChargesAmount() + MainetConstants.operator.FORWARD_SLACE + orgId
                    + MainetConstants.operator.FORWARD_SLACE + langId;
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(responseMap, uri);

            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error(
                        "Error Occurred while creating response map from payment gateway response " + responseEntity);
                throw new FrameworkException(
                        "Error Occurred while creating response map from payment gateway response " + responseEntity);
            }
            try {
                Map<String, String> map = JersyCall.castResponse(responseEntity, Map.class);
                responseMap.clear();
                responseMap.putAll(map);
            } catch (Exception e) {
                throw new FrameworkException(
                        "Error Occurred during cast response map received while payment gateway response call is success! ",
                        e);
            }
        } else {
            if ((getPgId() != null) && (getPgId() != 0)) {
                responseMap = paymService.genrateResponse(responseMap, gatewayFlag, getChargesAmount(), getPgId(),
                        orgId, langId);
            } else {
                throw new FrameworkException("Problem ......");
            }
        }
        return responseMap;
    }

    public void processPayUTransactionAfterPayment(final HttpServletRequest httpServletRequest,
            Map<String, String> responseMap) throws FrameworkException {
       // Boolean isReachable = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
       /* if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = JersyCall.pingURL(jerseyUrl, 2000);
        }*/
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(responseMap,
                    ServiceEndpoints.CommonPaymentGateway.PROCESS_TRANSACTION_AFTER_PAYMENT);
            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error("Error Occurred while saving post payment transaction data " + responseEntity);
                throw new FrameworkException(
                        "Error Occurred while saving post payment transaction data " + responseEntity);
            }
        } else {
            paymService.proceesPaymentTransactionAfterPayment(httpServletRequest, responseMap);
        }
    }

    public List<DocumentDetailsVO> getFileUploadList(final List<DocumentDetailsVO> checkList,
            final Map<Long, Set<File>> fileMap) {

        final Map<Long, String> listString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();

        try {
            final List<DocumentDetailsVO> docs = checkList;
            if ((fileMap != null) && !fileMap.isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

                    final List<File> list = new ArrayList<>(entry.getValue());
                    for (final File file : list) {
                        try {
                            final Base64 base64 = new Base64();

                            final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                            fileName.put(entry.getKey(), file.getName());
                            listString.put(entry.getKey(), bytestring);

                        } catch (final IOException e) {
                            logger.error("Exception getFileUploadList  during file I/O :", e);
                            throw new FrameworkException(e);
                        }
                    }
                }
            }
            if (docs != null) {
                if (!docs.isEmpty() && !listString.isEmpty()) {
                    for (final DocumentDetailsVO d : docs) {
                        final long count = d.getDocumentSerialNo() - 1;
                        if (listString.containsKey(count) && fileName.containsKey(count)) {
                            d.setDocumentByteCode(listString.get(count));
                            d.setDocumentName(fileName.get(count));

                            if ((d.getDoc_DESC_Mar() != null) && !d.getDoc_DESC_Mar().isEmpty()) {
                             //Defect #129055
                                d.setDoc_DESC_Mar(d.getDoc_DESC_Mar());
                            }

                        }
                        if ((d.getDoc_DESC_ENGL() == null) || d.getDoc_DESC_ENGL().equals(MainetConstants.BLANK)) {
                            d.setDoc_DESC_ENGL(d.getDocumentSerialNo().toString());
                        }

                    }

                }
            }
            return docs;
        } catch (final Exception e) {
            throw new FrameworkException("FileUploading Exception occur in getFileUploadList", e);
        }
    }

    public boolean getBank() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase("PCB")) {
                return true;
            }
        }
        return false;
    }

    public boolean getUlb() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase("PCU")) {
                return true;
            }
        }
        return false;
    }

    public boolean getDd() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase("PDD")) {
                return true;
            }
        }
        return false;
    }

    public boolean getPostal() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase("PPO")) {
                return true;
            }
        }
        return false;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void editForm(final long rowId) {

    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public List<AdditionalOwnerDTO> getListOfAdditionalOwnerDetail() {
        return listOfAdditionalOwnerDetail;
    }

    public void setListOfAdditionalOwnerDetail(final List<AdditionalOwnerDTO> listOfAdditionalOwnerDetail) {
        this.listOfAdditionalOwnerDetail = listOfAdditionalOwnerDetail;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(final int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return the encrptAmount
     */
    public String getEncrptAmount() {
        return encrptAmount;
    }

    /**
     * @param encrptAmount the encrptAmount to set
     */
    public void setEncrptAmount(final String encrptAmount) {
        this.encrptAmount = encrptAmount;
    }

    public String getChargesAmount() {
        return chargesAmount;
    }

    public void setChargesAmount(final String chargesAmount) {
        this.chargesAmount = chargesAmount;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(final String processStatus) {
        this.processStatus = processStatus;
    }

    public boolean getOfflinePay() {
        if ((getOfflineDTO().getOnlineOfflineCheck() != null)
                && getOfflineDTO().getOnlineOfflineCheck().equalsIgnoreCase(MainetConstants.IsDeleted.NOT_DELETE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * use to get Gender Id
     * 
     * @param lookUpCode
     * @return
     */
    public String getGenderId(final String lookUpCode) {

        String lookUpId = StringUtils.EMPTY;
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
                getUserSession().getOrganisation());
        for (final LookUp lookUp : lookUps) {
            if ((lookUpCode != null) && !lookUpCode.isEmpty()) {
                if (lookUpCode.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    lookUpId = Long.toString(lookUp.getLookUpId());
                    break;
                }
            }
        }
        return lookUpId;
    }

    public CommonChallanDTO getOfflineDTO() {
        return offlineDTO;
    }

    public void setOfflineDTO(final CommonChallanDTO offlineDTO) {
        this.offlineDTO = offlineDTO;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /*
     * public List<LookUp> getBankList(final String bankId) throws FrameworkException { return paymService.getBankList(bankId,
     * UserSession.getCurrent().getLanguageId()); }
     */

    /**
     * @return the pgId
     */
    public Long getPgId() {
        return pgId;
    }

    /**
     * @param pgId the pgId to set
     */
    public void setPgId(final Long pgId) {
        this.pgId = pgId;
    }

    public void updateReiceptData(final CommonChallanDTO offlineDTO) {

        paymService.updateReiceptData(offlineDTO);
    }

    public void sendSMSandEMail(final Map<String, String> responseMap) {
        paymService.sendSMSandEMail(responseMap);
    }

    public String getCustomViewName() {
        return customViewName;
    }

    public void setCustomViewName(String customViewName) {
        this.customViewName = customViewName;
    }

	public String getLogInUserImage() {
		return logInUserImage;
	}

	public void setLogInUserImage(String logInUserImage) {
		this.logInUserImage = logInUserImage;
	}

	public Map<String, String> processTransactionForCallStatus(final HttpServletRequest httpServletRequest,
			PaymentRequestDTO paymentRequestDTO) throws FrameworkException {
		Map<String, String> responseMap = new HashMap<>();
		//Boolean isReachable = false;
		final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
		/*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
			isReachable = JersyCall.pingURL(jerseyUrl, 2000);
		}*/
		if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
			ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(paymentRequestDTO,
					ServiceEndpoints.CommonPaymentGateway.PROCESS_TRANSACTION_FOR_CALLSTATUS);

			if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
				logger.error(
						"Error Occurred while calling status API request in processTransactionForCallStatus() call "
								+ responseEntity);
				throw new FrameworkException(
						"Error Occurred while calling status API request in processTransactionForCallStatus() call"
								+ responseEntity);
			}
			try {
				responseMap = JersyCall.castResponse(responseEntity, Map.class);

			} catch (Exception e) {
				logger.error(
						"Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
						e);
				throw new FrameworkException(
						"Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
						e);
}
		}

		return responseMap;
	}
    public String uploadAndGetFilePath(final FileNetApplicationClient fileNetApplicationClient, final String directoryTree)
            throws Exception {

        String uploadFileName = StringUtils.EMPTY;

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            final Iterator<File> setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {

                final File file = setFilesItr.next();

                uploadFileName = uploadFileName + file.getName();
               
                final List<File> fileList = new ArrayList<>();

                fileList.add(file);

                fileNetApplicationClient.uploadFileList(fileList, directoryTree);

            }
        }

        return uploadFileName;
    }

	/*
	 * As per Palam Sir Code for User session variable data null after payment
	 * gateway page open and payment done
	 */ 
    public boolean processPayUTransactionBeforePayment(final HttpServletRequest httpServletRequest
    		,HttpServletResponse httpServletResponse,PaymentRequestDTO paymentRequestDTO ) throws FrameworkException {
        boolean status = false;
        setServiceName(paymentRequestDTO.getServiceName());
        setOwnerName(paymentRequestDTO.getApplicantName());
        String amount = null;
        try {
            amount = EncryptionAndDecryption.decrypt(paymentRequestDTO.getFinalAmount());
            setChargesAmount(amount);
            if (amount != null && !amount.isEmpty()) {
                paymentRequestDTO.setValidateAmount(new BigDecimal(amount));
            }
            setEncrptAmount(paymentRequestDTO.getFinalAmount());
            final String url = httpServletRequest.getRequestURL().toString();
            paymentRequestDTO.setControlUrl(url);
            if(paymentRequestDTO.getOrgId()==null) {
                paymentRequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }
            paymentRequestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            paymentRequestDTO.setPayModeorType(MainetConstants.PAYMODE.WEB);
        } catch (final Exception e1) {
            throw new FrameworkException(SOME_PROBLEM_DUE_TO_SECURITY_REASON, e1);
        }

        //Boolean isReachable = false;
        final String jerseyUrl = JersyCall.getJerseyUrl(JERSEY_URL);
       /* if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = JersyCall.pingURL(jerseyUrl, 2000);
        }*/
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(paymentRequestDTO,
                    ServiceEndpoints.CommonPaymentGateway.PROCESS_TRANSACTION_BEFORE_PAYMENT);

            if ((responseEntity == null) || (responseEntity.getStatusCode() != HttpStatus.OK)) {
                logger.error(
                        "Error Occurred while saving payment request on processPayUTransactionBeforePayment() call "
                                + responseEntity);
                throw new FrameworkException(
                        "Error Occurred while saving payment request on processPayUTransactionBeforePayment() call "
                                + responseEntity);
            }
            try {
                PaymentRequestDTO paymentDTO = JersyCall.castResponse(responseEntity, PaymentRequestDTO.class);
              
                logger.info("Succsess Url   "+paymentDTO.getSuccessUrl());
                
                logger.info(httpServletResponse.encodeURL(paymentDTO.getSuccessUrl()));//+"&sessionId="+httpServletRequest.getSession().getId())             
				
				  paymentDTO.setSuccessUrl(httpServletResponse.encodeRedirectURL(paymentDTO.
				  getSuccessUrl()+"&sessionId="+httpServletRequest.getSession().getId()));
				  paymentDTO.setFailUrl(httpServletResponse.encodeRedirectURL(paymentDTO.
				  getFailUrl()+"&sessionId="+httpServletRequest.getSession().getId()));
				  paymentDTO.setPayRequestMsg(httpServletResponse.encodeRedirectURL(paymentDTO.
				  getPayRequestMsg()));
				  
				  paymentDTO.setCancelUrl(httpServletResponse.encodeRedirectURL(paymentDTO.
				  getCancelUrl()+"&sessionId="+httpServletRequest.getSession().getId()));
				 
                BeanUtils.copyProperties(paymentRequestDTO, paymentDTO);
                status = true;
                UserSession.getCurrent().getMapList().put("txnId", paymentDTO.getTxnid().toString());
            } catch (Exception e) {
                logger.error(
                        "Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
                        e);
                throw new FrameworkException(
                        "Error Occurred during cast responseEntity received while saving payment request on processPayUTransactionBeforePayment() call is success! ",
                        e);
            }
        } else {
            paymService.proceesPaymentTransactionBeforePayment(httpServletRequest, paymentRequestDTO);
            status = true;
        }
        return status;
    }
}
