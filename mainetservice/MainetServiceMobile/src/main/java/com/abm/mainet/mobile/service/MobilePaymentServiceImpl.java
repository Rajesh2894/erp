package com.abm.mainet.mobile.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.BankDTO;
import com.abm.mainet.common.integration.payment.dto.PayURequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentReceiptDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentBeanFactory;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.integration.payment.service.PaymentStrategy;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.mobile.dto.MobilePaymentReqDTO;
import com.abm.mainet.mobile.dto.MobilePaymentRespDTO;
import com.abm.mainet.smsemail.service.ISMSService;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement
public class MobilePaymentServiceImpl implements MobilePaymentService, Serializable {

    private static final long serialVersionUID = 8026287572039837192L;

    private static final Logger LOG = Logger.getLogger(MobilePaymentServiceImpl.class);

    @Autowired
    private PaymentService iPaymentService;

    @Resource
    private ISMSService smsService;

    @Resource
    private IChallanService iChallanService;

    @Resource
    private CommonService commonService;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Override
    @Transactional
    public MobilePaymentRespDTO saveOnlineTransactionMaster(final MobilePaymentReqDTO mobilePaymentReqDTO,
            final HttpServletRequest request) {

        LOG.info("Inter the saveOnlineTransactionMaster  Method in MobileOnlinePaymentController");
        final ServiceMaster service = serviceMasterService
                .getServiceMasterByShortCode(mobilePaymentReqDTO.getServiceShortName(), mobilePaymentReqDTO.getOrgId());
        final MobilePaymentRespDTO respDto = new MobilePaymentRespDTO();
        final PaymentRequestDTO paymnetRequestDTO = new PaymentRequestDTO();
        paymnetRequestDTO.setEmpId(mobilePaymentReqDTO.getUserId());
        paymnetRequestDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
        try {
            if (service != null) {
                paymnetRequestDTO.setUdf1(service.getSmServiceId().toString());
                paymnetRequestDTO.setServiceId(service.getSmServiceId());
                if (mobilePaymentReqDTO.getLangId() == 1) {
                    paymnetRequestDTO.setServiceName(service.getSmServiceName());

                } else {
                    paymnetRequestDTO.setServiceName(service.getSmServiceNameMar());

                }
            }
            paymnetRequestDTO.setApplicationId(mobilePaymentReqDTO.getReferenceId());
            paymnetRequestDTO.setUdf2(mobilePaymentReqDTO.getReferenceId());
            paymnetRequestDTO.setUdf3(mobilePaymentReqDTO.getUdf1());
            paymnetRequestDTO.setUdf4(String.valueOf(mobilePaymentReqDTO.getOrgId()));
            paymnetRequestDTO.setUdf5(mobilePaymentReqDTO.getServiceShortName());
            paymnetRequestDTO.setUdf6(String.valueOf(mobilePaymentReqDTO.getOrgId()));
            paymnetRequestDTO.setUdf7(String.valueOf(mobilePaymentReqDTO.getUserId()));
            paymnetRequestDTO.setUdf8(MainetConstants.REQUIRED_PG_PARAM.NA);
            paymnetRequestDTO.setUdf9(MainetConstants.REQUIRED_PG_PARAM.NA);
            paymnetRequestDTO.setUdf10(MainetConstants.REQUIRED_PG_PARAM.NA);
            paymnetRequestDTO.setApplicantName(mobilePaymentReqDTO.getApplicantName().trim());
            paymnetRequestDTO.setDueAmt(mobilePaymentReqDTO.getDueAmt());
            if ((mobilePaymentReqDTO.getEmail() != null) && !mobilePaymentReqDTO.getEmail().isEmpty())

            {
                paymnetRequestDTO.setEmail(mobilePaymentReqDTO.getEmail());
            } else {
                paymnetRequestDTO.setEmail(MainetConstants.REQUIRED_PG_PARAM.NA);
            }
            paymnetRequestDTO.setMobNo(mobilePaymentReqDTO.getMobileNo());
            paymnetRequestDTO.setBankId(mobilePaymentReqDTO.getBankId());

            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/";
            if ((null == url) || url.isEmpty()) {
                url = "/";
            } else if (!url.endsWith("/")) {
                url = url.trim().concat("/");
            }
            paymnetRequestDTO.setFailUrl(url + MainetConstants.RESPONSE_URL_PARAM.FAIL);
            paymnetRequestDTO.setCancelUrl(url + MainetConstants.RESPONSE_URL_PARAM.CANCEL);
            paymnetRequestDTO.setControlUrl(request.getRequestURL().toString());

            iPaymentService.proceesTransactionOnApplication(request, paymnetRequestDTO);

            try {
                final String amount = EncryptionAndDecryption.encrypt(paymnetRequestDTO.getDueAmt().toString());
                paymnetRequestDTO.setFinalAmount(amount);
            } catch (final IllegalBlockSizeException e) {
                throw new FrameworkException(e);
            }
            paymnetRequestDTO.setPayModeorType(MainetConstants.PAYMODE.MOBILE);
            iPaymentService.proceesTransactionBeforePayment(request, paymnetRequestDTO);

            if (paymnetRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {
                final PayURequestDTO dto = new PayURequestDTO();
                dto.setKey(paymnetRequestDTO.getKey());
                dto.setAmount(paymnetRequestDTO.getDueAmt().toString());
                dto.setCurl(paymnetRequestDTO.getCancelUrl());
                dto.setEmail(paymnetRequestDTO.getEmail());
                dto.setFirstname(paymnetRequestDTO.getApplicantName());
                dto.setFurl(paymnetRequestDTO.getFailUrl());
                dto.setHash(paymnetRequestDTO.getHash());
                dto.setPg(paymnetRequestDTO.getPgName());
                dto.setPhone(paymnetRequestDTO.getMobNo());
                dto.setProductinfo(paymnetRequestDTO.getServiceName());
                dto.setSurl(paymnetRequestDTO.getSuccessUrl());
                dto.setTxnid(paymnetRequestDTO.getTxnid().toString());
                dto.setUdf1(paymnetRequestDTO.getUdf1());
                dto.setUdf2(paymnetRequestDTO.getUdf2());
                dto.setUdf3(paymnetRequestDTO.getUdf3());
                dto.setUdf4(paymnetRequestDTO.getUdf4());
                dto.setUdf5(paymnetRequestDTO.getUdf5());
                dto.setUdf6(paymnetRequestDTO.getUdf6());
                dto.setUdf7(paymnetRequestDTO.getUdf7());
                dto.setUdf8(paymnetRequestDTO.getUdf8());
                dto.setUdf9(paymnetRequestDTO.getUdf9());
                dto.setUdf10(paymnetRequestDTO.getUdf10());
                dto.setPayuUrl(paymnetRequestDTO.getPgUrl());

                respDto.setPayuReqdto(dto);
                respDto.setErrorlist(Collections.<String> emptyList());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setPayRequestMsg(MainetConstants.REQUIRED_PG_PARAM.NA);
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("eip.payment.successmsg"));

                respDto.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
            }

            else if (paymnetRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)
                    && (paymnetRequestDTO.getPayRequestMsg() != null)
                    && !paymnetRequestDTO.getPayRequestMsg().isEmpty()) {
                respDto.setErrorlist(Collections.<String> emptyList());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setPayRequestMsg(paymnetRequestDTO.getPayRequestMsg());
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.successmsg"));

                respDto.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
            }

            else if (paymnetRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)
                    && (paymnetRequestDTO.getPayRequestMsg() != null)
                    && !paymnetRequestDTO.getPayRequestMsg().isEmpty()) {
                respDto.setErrorlist(Collections.<String> emptyList());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setPayRequestMsg(paymnetRequestDTO.getPayRequestMsg());
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.successmsg"));

                respDto.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
            } else if (paymnetRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)
                    && (paymnetRequestDTO.getPayRequestMsg() != null)
                    && !paymnetRequestDTO.getPayRequestMsg().isEmpty()) {
                respDto.setErrorlist(Collections.<String> emptyList());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setPayRequestMsg(paymnetRequestDTO.getPayRequestMsg());
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.successmsg"));
                respDto.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
            } else if (paymnetRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)
                    && (paymnetRequestDTO.getPayRequestMsg() != null)
                    && !paymnetRequestDTO.getPayRequestMsg().isEmpty()) {
                respDto.setErrorlist(Collections.<String> emptyList());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setPayRequestMsg(paymnetRequestDTO.getPayRequestMsg());
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.failsmsg"));
                respDto.setStatus(MainetConstants.PAYU_STATUS.PENDING);
            }

            else {
                respDto.setErrorlist(paymnetRequestDTO.getErrors());
                respDto.setLangId(mobilePaymentReqDTO.getLangId());
                respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
                respDto.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.failsmsg"));
                respDto.setStatus(MainetConstants.PAYU_STATUS.FAIL);
            }
        } catch (final Exception exception) {

            LOG.error("Exeception Occur in saveOnlineTransactionMaster ", exception);
            respDto.setErrorlist(paymnetRequestDTO.getErrors());
            respDto.setLangId(mobilePaymentReqDTO.getLangId());
            respDto.setOrgId(mobilePaymentReqDTO.getOrgId());
            respDto.setResponseMsg(exception.getMessage());
            respDto.setStatus(MainetConstants.PAYU_STATUS.FAIL);
        }

        return respDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDTO> getPaymentGatewayList(final long orgId) {

        LOG.info("starts the getBankDetailListService method ");
        List<BankDTO> bankVOs = null;
        BankDTO bankVO = null;
        final Map<Long, String> map = commonService.getAllPgBank(orgId);
        if ((map != null) && !map.isEmpty()) {
            bankVOs = new ArrayList<>();
            for (final Map.Entry<Long, String> m : map.entrySet()) {
                bankVO = new BankDTO();
                bankVO.setBankId(m.getKey());
                bankVO.setCbbankname(m.getValue());
                bankVOs.add(bankVO);
            }

        }
        return bankVOs;
    }

 /*   @Override
    @Transactional
    public PaymentReceiptDTO saveMobileOnlineTransAfterPaymentResp(final HttpServletRequest request,
            final String pgFlag) {

        LOG.info("starts the saveMobileOnlineTransAfterPaymentResp method ");
        final PaymentReceiptDTO paymentReceipt = new PaymentReceiptDTO();
        final Properties properties = new Properties();
        
         * Class<?> clazz = null; Object dynamicServiceInstance = null; String serviceClassName = null;
         

        final Map<String, String> responseMap = new HashMap<>(0);
        try {
            properties.load(MobilePaymentServiceImpl.class.getClassLoader()
                    .getResourceAsStream("serviceConfiguration.properties"));

            final Long orgId = (Long) request.getAttribute(MainetConstants.Common_Constant.ORGID);
            final Enumeration<String> paramNames = request.getParameterNames();
            String paramName;
            String paramValue;
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    paramName = paramNames.nextElement();
                    paramValue = request.getParameter(paramName);
                    responseMap.put(paramName, paramValue);
                }
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
                if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)
                        || pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
                    if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY).isEmpty()) {
                        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
                                properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY));

                    }
                    if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV).isEmpty()) {
                        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
                                properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV));
                    }

                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
                            properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_BANKID));
                    getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);

                } else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {

                    PGBankDetail bankDetail = commonService.getBankDetailByBankName(MainetConstants.PG_SHORTNAME.CCA,
                            orgId);

                    final List<PGBankParameter> pgBankParamDets = commonService
                            .getMerchantMasterParamByPgId(bankDetail.getPgId(), orgId);
                    if (pgBankParamDets != null && !pgBankParamDets.isEmpty()) {
                        for (PGBankParameter bankParam : pgBankParamDets) {
                            if (bankParam.getParName().equalsIgnoreCase(MainetConstants.REQUIRED_PG_PARAM.KEY)) {
                                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY, bankParam.getParValue());
                                break;
                            }
                        }
                    }
                    getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE),
                            responseMap, pgFlag);
                }

                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGID,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF6).toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));

                final Organisation organisation = new Organisation();
                if (orgId != null && orgId != 0) {
                    organisation.setOrgid(orgId);
                } else {
                    organisation.setOrgid(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF6)));
                }
                final Employee employee = new Employee();
                employee.setEmpId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF7)));
                LOG.info("response map before calling  proceesTransactionAfterPayment() is " + responseMap);
                PaymentTransactionMas master = iPaymentService.proceesTransactionAfterPayment(responseMap);

                LOG.info("PaymentTransactionMas value is " + master.toString() + " with track id "
                        + responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID));
                if ((master != null) && (master.getRecvStatus() != null)
                        && master.getRecvStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {

                    paymentReceipt.setFirstName(Utility.toCamelCase(master.getSendFirstname()));
                    paymentReceipt.setEmail(master.getSendEmail());
                    paymentReceipt.setMobileNo(master.getSendPhone());
                    paymentReceipt.setNetAmount(master.getRecvNetAmountDebit());
                    if (master.getSendAmount() != null) {
                        paymentReceipt.setAmount(master.getSendAmount().doubleValue());
                    }
                    paymentReceipt.setProductinfo(Utility.toCamelCase(master.getSendProductinfo()));
                    paymentReceipt.setPaymentDateTime(master.getLmodDate());
                    paymentReceipt.setTrackId(master.getTranCmId());
                    paymentReceipt.setBankRefNo(master.getRecvBankRefNum());
                    paymentReceipt.setTransactionId(master.getRecvMihpayid());
                    paymentReceipt.setApplicationId(responseMap.get(MainetConstants.BankParam.UDF3));
                    paymentReceipt.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
                    paymentReceipt.setServiceType(Utility.toCamelCase(master.getSendProductinfo()));
                    paymentReceipt.setErrorMsg(master.getRecvErrm());
                    if (orgId != null && orgId != 0) {
                        paymentReceipt.setOrgId(orgId);
                    } else {
                        paymentReceipt.setOrgId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF6)));
                    }
                    paymentReceipt.setEmpId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF7)));
                    paymentReceipt.setLabelName(ApplicationSession.getInstance()
                            .getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));

                    ServiceMaster service = null;
                    if (orgId != null && orgId != 0) {
                        service = serviceMasterService.getServiceMasterByShortCode(
                                responseMap.get(MainetConstants.BankParam.UDF5), orgId);
                    } else {
                        service = serviceMasterService.getServiceMasterByShortCode(
                                responseMap.get(MainetConstants.BankParam.UDF5),
                                Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF6)));
                    }

                    final CommonChallanDTO offline = new CommonChallanDTO();

                    offline.setApplNo(Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3)));
                    offline.setAmountToPay(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.NET_AMT));
                    if (responseMap.get(MainetConstants.REQUIRED_PG_PARAM.EMAIL) != null) {
                        offline.setEmailId(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.EMAIL));
                        offline.setMobileNumber(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PHONE_NO));
                    }
                    offline.setApplicantName(responseMap.get(MainetConstants.BankParam.FNAME));
                    offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
                    offline.setDocumentUploaded(true);
                    offline.setUserId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF7)));
                    if (orgId != null && orgId != 0) {
                        offline.setOrgId(orgId);
                    } else {
                        offline.setOrgId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF6)));
                    }
                    offline.setLangId(Integer.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG)));
                    offline.setLgIpMac(Utility.getClientIpAddress(request));
                    offline.setFaYearId(UserSession.getCurrent().getFinYearId());
                    offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
                    offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
                    offline.setServiceId(Long.valueOf(responseMap.get(MainetConstants.BankParam.UDF1)));
                    offline.setDeptId(service.getTbDepartment().getDpDeptid());
                    offline.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.PAY_PREFIX.WEB,
                            PrefixConstants.PAY_PREFIX.PREFIX_VALUE, organisation).getLookUpId());
                    offline.setUniquePrimaryId(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2));
                    offline.setFeeIds(null);// need to set for all service payment

                    iChallanService.updateDataAfterPayment(offline);

                    final String msg = ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_MSG,
                            new Object[] { paymentReceipt.getLabelName(),
                                    String.valueOf(paymentReceipt.getApplicationId()),
                                    String.valueOf(paymentReceipt.getTrackId()) });
                    smsService.sendSMSInBackground(msg, String.valueOf(master.getSendPhone()),
                            Integer.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG)));

                } else {

                    paymentReceipt.setStatus(MainetConstants.PAYU_STATUS.FAIL);
                    paymentReceipt.setErrorMsg("Please save the Track ID"
                            + (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
                            + " for future Reference");
                }
            }
        } catch (final Exception exception) {

            LOG.error("Exeception Occur in saveMobileOnlineTransAfterPaymentResp ", exception);
            paymentReceipt.setStatus(MainetConstants.PAYU_STATUS.FAIL);
            paymentReceipt.setErrorMsg(exception.getMessage());

        }
        return paymentReceipt;
    }*/

    private void getPaymentResponseDTO(final String responseString, Map<String, String> responseMap,
            final String pgName) {
        try {

            final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(pgName);
            responseMap = paymentStrategy.generatePaymentResponse(responseString, responseMap);
        } catch (final Exception e) {
            throw new FrameworkException("Exeception occur in getPaymentResponseDTO()", e);
        }

    }

}
