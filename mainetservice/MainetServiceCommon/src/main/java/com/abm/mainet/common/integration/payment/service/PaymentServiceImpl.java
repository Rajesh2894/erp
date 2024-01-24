/**
 *
 */
package com.abm.mainet.common.integration.payment.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.loi.repository.EgrassPaymentTransactionRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PAYMENT_POSTING_API;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailResDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.PaymentDetailSearchDTO;
import com.abm.mainet.common.integration.dto.PaymentDetailsResponseDTO;
import com.abm.mainet.common.integration.dto.PaymentPostingDto;
import com.abm.mainet.common.integration.dto.ResponseMessageDto;
import com.abm.mainet.common.integration.payment.dao.PaymentDAO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.common.integration.payment.entity.EgrassPaymentENtity;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement
public class PaymentServiceImpl implements PaymentService {

    protected Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Resource
    private PaymentDAO paymentDAO;

    @Resource
    private IChallanService iChallanService;

    @Resource
    IDepartmentDAO departmentDAO;

    @Resource
    private IFinancialYearService iFinancialYearService;

    @Resource
    IOrganisationDAO orgDAO;

    @Resource
    private ServiceMasterService serviceMasterService;
   @Autowired
   private EgrassPaymentTransactionRepository egrassPaymentTransactionRepository;
    /**
     * This function performs validation save {@link PaymentTransactionMas} When we send request to the respective merchants.
     *
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchFieldException
     *  Test Case :
        1)Mobile request
         applicantName=Vivek, mobileNo=9098889240, email=anuj.dixit@abmindia.com, bankId=171, 
         serviceShortName=MUT, referenceId=CGMNCR0005672000, dueAmt=500, securityId=null, 
         udf1=8721060802011, udf2=Y, udf3=null, udf4=null, udf5=null, udf6=null, udf7=null, 
         challanServiceType=N, feeIds={1=500.0}, documentUploaded=false, txnId=null]

        UDF1= 8721060802011 // app no
        referenceId= CGMNCR0005672000 //prop no
      2)convert mobile request to PaymentRequestDTO
         paymentRequestDTO.setUdf1(service.getSmServiceId().toString());
        paymentRequestDTO.setUdf2(mobilePaymentReqDTO.getReferenceId());  //pro no
        paymentRequestDTO.setApplicationId(mobilePaymentReqDTO.getUdf1()); aapp no
        paymentRequestDTO.setUdf3(mobilePaymentReqDTO.getUdf1());
       3)Convert  paymentRequestDTO to PaymentTransactionMas
       paymentTransactionMas.setReferenceId(payURequestDTO.getApplicationId());	
     * 
     * 
     */
    @Override
    @Transactional
    public void proceesTransactionOnApplication(final HttpServletRequest httpServletRequest,
            final PaymentRequestDTO payURequestDTO) throws FrameworkException {
        final PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
        paymentTransactionMas.setLmodDate(new Date());
        paymentTransactionMas.setLgIpMac(Utility.getMacAddress());
        paymentTransactionMas.setOrgId(payURequestDTO.getOrgId());
        paymentTransactionMas.setUserId(payURequestDTO.getEmpId());
        paymentTransactionMas.setReferenceDate(new Date());
        paymentTransactionMas.setSmServiceId(payURequestDTO.getServiceId());
        paymentTransactionMas.setSendKey(MainetConstants.BankParam.KEY);
        paymentTransactionMas.setSendAmount(payURequestDTO.getDueAmt());
        paymentTransactionMas.setSendProductinfo(payURequestDTO.getServiceName());
        paymentTransactionMas.setSendFirstname(payURequestDTO.getApplicantName());
        paymentTransactionMas.setSendEmail(payURequestDTO.getEmail());
        paymentTransactionMas.setSendPhone(payURequestDTO.getMobNo());
        paymentTransactionMas.setSendSurl(payURequestDTO.getCancelUrl());
        paymentTransactionMas.setSendFurl(payURequestDTO.getFailUrl());
        paymentTransactionMas.setSendSalt(MainetConstants.BankParam.SALT);
        paymentTransactionMas.setSendHash(MainetConstants.BankParam.HASH);
        paymentTransactionMas.setPgType(MainetConstants.BankParam.PG);
        paymentTransactionMas.setReferenceId(payURequestDTO.getUdf2());
        paymentTransactionMas.setSendUrl(MainetConstants.BankParam.SURL);
        paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.PENDING);
        paymentTransactionMas.setFeeIds(payURequestDTO.getFeeIds());
        paymentTransactionMas.setChallanServiceType(payURequestDTO.getChallanServiceType());
        paymentTransactionMas.setDocumentUploaded(payURequestDTO.getDocumentUploaded());
        if(payURequestDTO.getLangId()!=null)
        paymentTransactionMas.setLangId(payURequestDTO.getLangId());
        else
        	paymentTransactionMas.setLangId(1);	

        paymentTransactionMas.setFlatNo(payURequestDTO.getFlatNo());
        paymentDAO.savePaymentTransaction(paymentTransactionMas);
        payURequestDTO.setTxnid(paymentTransactionMas.getTranCmId());
    }

    @Override
    @Transactional
    public void proceesTransactionBeforePayment(final HttpServletRequest httpServletRequest,
            final PaymentRequestDTO payURequestDTO) throws FrameworkException {
        final List<PGBankParameter> pgBankParamDets = paymentDAO
                .getMerchantMasterParamByBankId(payURequestDTO.getBankId(), payURequestDTO.getOrgId());
        ServiceMaster sm = serviceMasterService.getServiceMaster(payURequestDTO.getServiceId(),
                payURequestDTO.getOrgId());
        if ((pgBankParamDets != null) && !pgBankParamDets.isEmpty()) {
            for (final PGBankParameter pgBankParamDet : pgBankParamDets) {
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.KEY)) {
                    payURequestDTO.setKey(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SALT)) {
                    payURequestDTO.setSalt(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SCHEME_CODE)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setSchemeCode(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.REQUESTTYPE)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setRequestType(pgBankParamDet.getParValue());
                }

                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PRODUCTION)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setProduction(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PG)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setPgName(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SURL)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setSuccessUrl(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.FURL)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setFailUrl(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.CURL)
                        && (pgBankParamDet.getParValue() != null)) {
                    payURequestDTO.setCancelUrl(pgBankParamDet.getParValue());
                }
                // add awl param

                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ORDER_ID)) {
                    payURequestDTO.setOrderId(pgBankParamDet.getParValue());
                }

                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRA_CIN)) {
                    payURequestDTO.setTrnCurrency(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRA_DEC)) {
                    payURequestDTO.setTrnRemarks(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRANS_TYE)) {
                    payURequestDTO.setRequestType(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.RESP_URL)) {
                      payURequestDTO.setResponseUrl(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F1)) {
                    payURequestDTO.setAddField1(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F2)) {
                    payURequestDTO.setAddField2(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F3)) {
                    payURequestDTO.setAddField3(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F4)) {
                    payURequestDTO.setAddField4(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F5)) {
                    payURequestDTO.setAddField5(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F6)) {
                    payURequestDTO.setAddField6(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F7)) {
                    payURequestDTO.setAddField7(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F8)) {
                    payURequestDTO.setAddField8(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F9)) {
                    payURequestDTO.setRecurrPeriod(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F10)) {
                    payURequestDTO.setRecurrDay(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F11)) {
                    payURequestDTO.setNoOfRecurring(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ENABLE_CHILD_WINDOW_POSTING)) {
                	if(pgBankParamDet.getParValue().contentEquals(MainetConstants.FlagT)) {
                		 payURequestDTO.setEnableChildWindowPosting(true);
                	}else {
                		 payURequestDTO.setEnableChildWindowPosting(false);
                	}
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ENABLE_PAYMENT_RETRY)) {
                	if(pgBankParamDet.getParValue().contentEquals(MainetConstants.FlagT)) {
                		 payURequestDTO.setEnablePaymentRetry(true);
                	}else {
                		 payURequestDTO.setEnablePaymentRetry(false);
                	}
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.RETRY_ATTEMPT_COUNT)) {
                    payURequestDTO.setRetryAttemptCount(new Long(pgBankParamDet.getParValue()));
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TXT_PAY_CATEGORY)) {
                    payURequestDTO.setTxtPayCategory(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.CURRENCY_TYPE)) {
                    payURequestDTO.setTrnCurrency(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TY_F1)) {
                    payURequestDTO.setTypeField1(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TY_F2)) {
                    payURequestDTO.setTypeField2(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TY_F3)) {
                    payURequestDTO.setTypeField3(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SECURITYID)) {
                    payURequestDTO.setSecurityId(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.CustAccNo)) {
                    payURequestDTO.setCustAccNo(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.Login)) {
                    payURequestDTO.setLogin(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.Password)) {
                    payURequestDTO.setPassword(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.prodId)) {
                    payURequestDTO.setProdid(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ChallanYear)) {
                    payURequestDTO.setRecurrDay(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.DDO)) {
                    payURequestDTO.setDdo(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.DTO)) {
                    payURequestDTO.setDto(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.Deptcode)) {
                    payURequestDTO.setDeptCode(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.STO)) {
                    payURequestDTO.setSto(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.BankId)) {
                    payURequestDTO.setBankIdStr(pgBankParamDet.getParValue());
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SchemeCount)) {
                    payURequestDTO.setSchemeCount(pgBankParamDet.getParValue());
                }
               

                // U#97807 serviceID_smcode
                if (sm != null) {
                    if (pgBankParamDet.getParName()
                            .equals(MainetConstants.BILLCLOUD_PARAM.SERVICE_ID + "_" + sm.getTbDepartment().getDpDeptcode())) {
                        payURequestDTO.setServiceID(pgBankParamDet.getParValue());
                    }
                    // checksumkey_smcode
                    if (pgBankParamDet.getParName().equals("checksumkey_" + sm.getTbDepartment().getDpDeptcode())) {
                        payURequestDTO.setChecksumKey(pgBankParamDet.getParValue());
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BILLCLOUD_PARAM.FUND_ID)) {
                        payURequestDTO.setFundID(pgBankParamDet.getParValue());
                    }
                }

            }
        } else {
            payURequestDTO.setErrorCause("Some problem facing for fatching Bank Parameters");

            throw new FrameworkException("Some problem facing for fatching Bank Parameters");
        }
        
        String url =  httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName()
        		+ MainetConstants.operator.QUOTES + httpServletRequest.getServerPort()
        		+ httpServletRequest.getContextPath() + MainetConstants.operator.FORWARD_SLACE;
        Organisation org = new Organisation();
        org.setOrgid(sm.getOrgid());
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && MainetConstants.MobileCommon.IOS.equalsIgnoreCase(payURequestDTO.getUdf10())) {
        	String scheme = httpServletRequest.getHeader("X-Forwarded-Proto");
        	if(scheme!=null) {
        		url =  scheme + "://" + httpServletRequest.getServerName()
                + MainetConstants.operator.QUOTES + httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath() + MainetConstants.operator.FORWARD_SLACE;
            }    
		}
        final PGBankDetail bankMaster = paymentDAO.getBankDetailByBankId(payURequestDTO.getBankId(),
                payURequestDTO.getOrgId());

        if (bankMaster != null) {
            payURequestDTO.setPgUrl(bankMaster.getPgUrl());
            payURequestDTO.setMerchantId(bankMaster.getMerchantId());
            payURequestDTO.setBankId(bankMaster.getBankid());
            payURequestDTO.setPgName(bankMaster.getPgName());
        } else {
            throw new FrameworkException("Some problem due to security reasons try again...");
        }

        try {
            final String amount = EncryptionAndDecryption.decrypt(payURequestDTO.getFinalAmount());
            if (amount != null && !amount.isEmpty()) {
                payURequestDTO.setDueAmt(new BigDecimal(amount));
            }

        } catch (final Exception e) {
            throw new FrameworkException("Some problem due to security reasons try again...");
        }
        String baseURL = payURequestDTO.getControlUrl();
        baseURL = baseURL.split(MainetConstants.operator.DOUBLE_BACKWARD_SLACE + MainetConstants.operator.QUE_MARK)[0];

        if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {
            if ((payURequestDTO.getPayModeorType() != null)
                    && payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_PAYU_SUCCESS
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                        + payURequestDTO.getFinalAmount());
                payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_PAYU_CANCEL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                        + payURequestDTO.getFinalAmount());
                payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_PAYU_FAIL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                        + payURequestDTO.getFinalAmount());
            } else {
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_PAYU);
                payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_PAYU);
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_PAYU);
            }

            getRequestDTO(payURequestDTO);
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if ((payURequestDTO.getPayModeorType() != null)
                    && payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_TP_SUCCESS
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_TP_CANCEL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_TP_FAIL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
            } else {
                payURequestDTO.setSuccessUrl(payURequestDTO.getSuccessUrl());
                
                LOG.info("in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in Techprocess "
                                + payURequestDTO.getSuccessUrl());
                
             payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_TP);
             payURequestDTO.setCancelUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_TP);
            }
            getRequestDTO(payURequestDTO);
            payURequestDTO.setHash(payURequestDTO.getPayRequestMsg());
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)) {

            if ((payURequestDTO.getPayModeorType() != null)
                    && payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_MOL_SUCCESS
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_MOL_CANCEL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_MOL_FAIL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
            } else {
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_MH);
                payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_MH);
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_MH);
            }
            getRequestDTO(payURequestDTO);
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if ((payURequestDTO.getPayModeorType() != null)
                    && payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_HDFC_SUCCESS
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_HDFC_CANCEL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
                payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_HDFC_FAIL
                        + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId());
            } else {
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_HDFC);
                payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_HDFC);
                payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_HDFC);
            }
            getRequestDTO(payURequestDTO);
            payURequestDTO.setHash(payURequestDTO.getPayRequestMsg());
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
                if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_CCA_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_CCA_CANCEL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_CCA_FAIL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                } else {
                    if (payURequestDTO.getSuccessUrl() == null || payURequestDTO.getSuccessUrl().isEmpty()) {
                        payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_HDFC_CCA);
                    }
                    if (payURequestDTO.getCancelUrl() == null || payURequestDTO.getCancelUrl().isEmpty()) {
                        payURequestDTO.setCancelUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_HDFC_CCA);
                    }
                    if (payURequestDTO.getFailUrl() == null || payURequestDTO.getFailUrl().isEmpty()) {
                        payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_HDFC_CCA);
                    }
                }
            }
            getRequestDTO(payURequestDTO);

        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
                if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_AWL_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_AWL_CANCEL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_AWL_FAIL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                } else {

                    payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_HDFC_AWL);
                    payURequestDTO.setCancelUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_HDFC_AWL);
                    payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_HDFC_AWL);
                }
            }
            getRequestDTO(payURequestDTO);
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
                if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_EASYPAY_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    LOG.info(
                            "in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in easypay mobile "
                                    + payURequestDTO.getSuccessUrl());

                } else {
                    LOG.info(
                            "in proceesTransactionBeforePayment method of PaymentServiceImpl class at service side in Easypay condition");
                    payURequestDTO.setSuccessUrl(payURequestDTO.getResponseUrl());
                }
            }
            getRequestDTO(payURequestDTO);
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
                if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_ICICI_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setCancelUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_ICICI_CANCEL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    payURequestDTO.setFailUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_ICICI_FAIL
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                } else {
                    if (payURequestDTO.getSuccessUrl() == null || payURequestDTO.getSuccessUrl().isEmpty()) {
                        payURequestDTO.setSuccessUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_ICICI_CCA);
                    }
                    if (payURequestDTO.getCancelUrl() == null || payURequestDTO.getCancelUrl().isEmpty()) {
                        payURequestDTO.setCancelUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.CANCEL_ICICI_CCA);
                    }
                    if (payURequestDTO.getFailUrl() == null || payURequestDTO.getFailUrl().isEmpty()) {
                        payURequestDTO.setFailUrl(baseURL + MainetConstants.REQUIRED_PG_PARAM.FAIL_ICICI_CCA);
                    }
                }
            }
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLCLOUD)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            payURequestDTO.setSalt(MainetConstants.REQUIRED_PG_PARAM.NA);
            payURequestDTO.setKey(payURequestDTO.getChecksumKey());
            if (payURequestDTO.getPayModeorType() != null) {
                if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    // for Bill Cloud Mobile make URL
                    /*
                     * payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_EASYPAY_SUCCESS +
                     * MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH +
                     * payURequestDTO.getFinalAmount());
                     */
                    LOG.info(
                            "in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in BillCloud mobile "
                                    + payURequestDTO.getSuccessUrl());

                } else {
                    LOG.info(
                            "in proceesTransactionBeforePayment method of PaymentServiceImpl class at service side in BillCloud condition");
                    payURequestDTO.setSuccessUrl(payURequestDTO.getResponseUrl());
                    
                }
            }
            getRequestDTO(payURequestDTO);
            LOG.info("requested url for BIll cloud " + payURequestDTO.getPayRequestMsg());
        } else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLDESK)) {
        	
            if (payURequestDTO.getPayModeorType() != null) {         	
              
            } 
            getRequestDTO(payURequestDTO);
            payURequestDTO.setHash(payURequestDTO.getPayRequestMsg());
           
        }else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.RAZORPAY)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
            	if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_RAZORPAY_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    LOG.info("in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in razorpay mobile "+ payURequestDTO.getSuccessUrl());
                } else {
					LOG.info("in proceesTransactionBeforePayment method of PaymentServiceImpl class at service side in razorpay condition");
                    payURequestDTO.setSuccessUrl(payURequestDTO.getResponseUrl());
                }
                
            }
            getRequestDTO(payURequestDTO);
            LOG.info("requested url for Razorpay " + payURequestDTO.getPayRequestMsg());
        }
        else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ATOMPAY)) {
            payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
            if (payURequestDTO.getPayModeorType() != null) {
            	if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
                    payURequestDTO.setSuccessUrl(url+ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_ATOM_SUCCESS
                            + MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
                            + payURequestDTO.getFinalAmount());
                    LOG.info("in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in Atom mobile "+ payURequestDTO.getSuccessUrl());
                }
                
            }
            getRequestDTO(payURequestDTO);
            LOG.info("requested url for AtomPay " + payURequestDTO.getPayRequestMsg());
        }else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASEBUZZ)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			if (payURequestDTO.getPayModeorType() != null) {
				if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
					payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.MOBILE_EASEBUZZ_SUCCESS
							+ MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
							+ payURequestDTO.getFinalAmount());
					LOG.info(
							"in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in Easebuzz mobile "
									+ payURequestDTO.getSuccessUrl());
				}
				 
			}
			getRequestDTO(payURequestDTO);
			LOG.info("requested url for EASEBUZZ " + payURequestDTO.getPayRequestMsg());
		}
        else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.NicGateway)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			payURequestDTO.setKey(MainetConstants.REQUIRED_PG_PARAM.NA);
			payURequestDTO.setSalt(MainetConstants.REQUIRED_PG_PARAM.NA);
			if (payURequestDTO.getPayModeorType() != null) {
				if (payURequestDTO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
					payURequestDTO.setSuccessUrl(url + ServiceEndpoints.RESPONSE_URL_PARAM.NicGateway
							+ MainetConstants.WINDOWS_SLASH + payURequestDTO.getOrgId() + MainetConstants.WINDOWS_SLASH
							+ payURequestDTO.getFinalAmount());
					LOG.info(
							"in proceesTransactionBeforePayment method of PaymentServiceImpl class SuccessUrl in NicGateway mobile "
									+ payURequestDTO.getSuccessUrl());
				}
				 
			}
			getRequestDTO(payURequestDTO);
			LOG.info("requested url for NicGateway " + payURequestDTO.getPayRequestMsg());
		}
        saveOnlineTransactionMaster(payURequestDTO);

    }

    private void getRequestDTO(PaymentRequestDTO paymentRequestDTO) {
        try {
            final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(paymentRequestDTO.getPgName());
            paymentRequestDTO = paymentStrategy.generatePaymentRequest(paymentRequestDTO);
        } catch (final Exception e) {
            throw new FrameworkException("Exeception occur in getRequestDTO()...", e);
        }

    }

    public void saveOnlineTransactionMaster(final PaymentRequestDTO payURequestDTO) throws FrameworkException {
        LOG.info("Start saveOnlineTransactionMaster of PaymentServiceImpl class");
        final PaymentTransactionMas paymentTransactionMas = paymentDAO
                .getOnlineTransactionMasByTrackId(payURequestDTO.getTxnid());
        paymentTransactionMas.setSendFirstname(payURequestDTO.getApplicantName());
        paymentTransactionMas.setSendPhone(payURequestDTO.getMobNo());
        paymentTransactionMas.setSendEmail(payURequestDTO.getEmail());
        paymentTransactionMas.setLmodDate(new Date());
        paymentTransactionMas.setLgIpMac(Utility.getMacAddress());
        paymentTransactionMas.setSendUrl(payURequestDTO.getPgUrl());
        paymentTransactionMas.setSendKey(payURequestDTO.getKey());
        paymentTransactionMas.setSendSalt(payURequestDTO.getSalt());
        paymentTransactionMas.setSendHash(payURequestDTO.getHash());
        paymentTransactionMas.setSendSurl(payURequestDTO.getSuccessUrl());
        paymentTransactionMas.setSendFurl(payURequestDTO.getFailUrl());
        paymentTransactionMas.setPgType(String.valueOf(payURequestDTO.getBankId()));
        paymentTransactionMas.setPgSource(payURequestDTO.getPgName());
        paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.PENDING);
        if(StringUtils.isNotBlank(payURequestDTO.getOrderId())) {
        paymentTransactionMas.setRecvMihpayid(payURequestDTO.getOrderId());
        }
        paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);
        LOG.info("End saveOnlineTransactionMaster of PaymentServiceImpl class");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaymentTransactionMas proceesTransactionAfterPayment(final Map<String, String> responseMap)
            throws FrameworkException {

    	Long transId = null;
		PaymentTransactionMas paymentTransactionMas = null;
		if (responseMap != null && responseMap.get(MainetConstants.BankParam.TXN_ID) != null) {
			transId = Long.parseLong(responseMap.get(MainetConstants.BankParam.TXN_ID));
			paymentTransactionMas = paymentDAO.getOnlineTransactionMasByTrackId(transId);
		}
		LOG.info("TxnId  >>>>>  "+transId);

        if (paymentTransactionMas == null) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION,
                    "Could not find current transaction ! Please try again!");

            throw new FrameworkException("Could not find current transaction ! Please try again!");
        }

        paymentTransactionMas.setRecvStatus(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS));
        paymentTransactionMas.setRecvErrm(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ERR_STR));
        paymentTransactionMas.setRecvMode(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MODE));
        if (responseMap.get(MainetConstants.REQUIRED_PG_PARAM.NET_AMT) != null) {
        paymentTransactionMas
                .setRecvNetAmountDebit(Double.parseDouble(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.NET_AMT)));
        }
        paymentTransactionMas.setRecvHash(responseMap.get(MainetConstants.BankParam.HASH));
        paymentTransactionMas.setUpdatedDate(new Date());
        paymentTransactionMas.setRecvBankRefNum(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO));
        paymentTransactionMas.setRecvMihpayid(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID));
        paymentTransactionMas.setPgSource(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC));
        paymentTransactionMas = paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);
        return paymentTransactionMas;

    }

    @Transactional(readOnly = true)
    public String getServiceShortName(final Long smServiceId, final long orgid) {

        return paymentDAO.getServiceShortName(smServiceId, orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getAllPgBank(long orgId) throws FrameworkException {

        LOG.info("starts the getBankDetailListService method ");
        return ApplicationContextProvider.getApplicationContext().getBean(CommonService.class).getAllPgBank(orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentTransactionMas getOnlineTransactionMasByTrackId(Long trackId) {
        return paymentDAO.getOnlineTransactionMasByTrackId(trackId);
    }

    /*
     * @Override
     * @Transactional public void updateReiceptData(CommonChallanDTO offlineDTO) { try { final Object[] finData =
     * ApplicationContextProvider.getApplicationContext() .getBean(IFinancialYearService.class).getFinacialYearByDate(new Date());
     * if ((finData != null) && (finData.length > 0)) { offlineDTO.setFaYearId(finData[0].toString()); }
     * iChallanService.updateDataAfterPayment(offlineDTO); } catch (final Exception exception) { throw new
     * FrameworkException("Exception occur in updateReiceptData ...", exception); } }
     */

    @Override
    public Map<String, String> genrateResponse(Map<String, String> responseMap, String gatewayFlag,
            String sessionAmount, Long orgId, Integer langId) {

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		PGBankDetail bankDetail = null;
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
			Long orderNo = Long.parseLong(responseMap.get("orderNo"));
			PaymentTransactionMas paymentTransactionMas = paymentDAO.getOnlineTransactionMasByTrackId(orderNo);
			LOG.info("Order No is -----> " + orderNo);
			Long serviceId = paymentTransactionMas.getSmServiceId();
			ServiceMaster sm = serviceMasterService.getServiceMaster(serviceId, orgId);
			if (null != sm.getTbDepartment().getDpDeptcode() && !sm.getTbDepartment().getDpDeptcode().isEmpty()
					&& (MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(sm.getTbDepartment().getDpDeptcode())
							|| MainetConstants.DEPT_SHORT_NAME.WATER.equals(sm.getTbDepartment().getDpDeptcode()))) {
				bankDetail = paymentDAO.getBankDetailByBankNameAndDeptCode(gatewayFlag, orgId,
						sm.getTbDepartment().getDpDeptcode());
			} else {
				bankDetail = paymentDAO.getBankDetailByBankNameAndDeptCode(gatewayFlag, orgId, MainetConstants.ALL);
			}
		} else {
			bankDetail = paymentDAO.getBankDetailByBankName(gatewayFlag, orgId);
		}
        //final PGBankDetail bankDetail = paymentDAO.getBankDetailByBankName(gatewayFlag, orgId);
        Long pgId = bankDetail.getPgId();
        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf(langId));
        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID, pgId.toString());
        setPgbankDeatilInMap(pgId, responseMap, orgId);

        if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
                && gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {

            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);
        }

        if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
                && gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)) {

            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);

        }

        else if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
                && gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);

        }

        else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {

            final String responseAmount = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.AMT);
            final String[] amount = responseAmount.split("\\.");
            final String mnt = amount[0];
            final String[] checkamount = sessionAmount.split("\\.");
            final String sesAmount = checkamount[0];
            if ((responseAmount != null) && !responseAmount.isEmpty() && mnt.equals(sesAmount)) {
                getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);
            } else {
                throw new FrameworkException("Some Problem Due To Security Reason...Amount Are Not Match");
            }

        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
                    responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
                    gatewayFlag);
        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL)) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
                    responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
            responseMap.put(MainetConstants.BankParam.MID, bankDetail.getMerchantId());
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
                    gatewayFlag);
        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
                    responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
            responseMap.put(MainetConstants.BankParam.MID, bankDetail.getMerchantId());
            LOG.info("ENCRYPTED_RESPONSE in generate Respone of PaymentServiceImpl"
                    + responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE));
            LOG.info("responseMap in generate Respone of PaymentServiceImpl " + responseMap);
            LOG.info("gatewayFlag in generate Respone of PaymentServiceImpl " + gatewayFlag);
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
                    gatewayFlag);
        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI)) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
                    responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
                    gatewayFlag);
        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLCLOUD)) {
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);
        } else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLDESK)) {
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);
        }else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.RAZORPAY)) {
        	PaymentTransactionMas transMas = paymentDAO.getTransMasByMihpayId(responseMap.get(MainetConstants.RAZORYPAY_PARAM.ORDER_ID));
        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,String.valueOf(transMas.getTranCmId()));
        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,transMas.getReferenceId());
        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,String.valueOf(transMas.getSmServiceId()));
           getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, gatewayFlag);
        }
        else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ATOMPAY)) {
           
            getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
                    gatewayFlag);
        }else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASEBUZZ)) {
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap,
					gatewayFlag);
		}
        else if (gatewayFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.NicGateway)) {
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap,
					gatewayFlag);
		}
        return responseMap;
    }

    private void getPaymentResponseDTO(final String responseString, Map<String, String> responseMap,
            final String pgName) {
        try {

            final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(pgName);
            responseMap = paymentStrategy.generatePaymentResponse(responseString, responseMap);
        } catch (final Exception e) {
            throw new FrameworkException("Exeception occur in getPaymentResponseDTO...", e);
        }

    }

    @Transactional(readOnly = true)
    private void setPgbankDeatilInMap(final Long bankId, final Map<String, String> responseMap, final Long orgId) {

        final List<PGBankParameter> pgBankParamDets = paymentDAO.getMerchantMasterParamByBankId(bankId, orgId);
        if ((pgBankParamDets != null) && !pgBankParamDets.isEmpty()) {
            for (final PGBankParameter pgBankParamDet : pgBankParamDets) {
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.KEY)) {
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY, pgBankParamDet.getParValue());

                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SALT)) {
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV, pgBankParamDet.getParValue());
                }

                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SCHEME_CODE)) {
                    if (pgBankParamDet.getParValue() != null) {
                        responseMap.put(MainetConstants.BankParam.SCHEME_CODE, pgBankParamDet.getParValue());
                    }
                }

                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PRODUCTION)) {
                    if (pgBankParamDet.getParValue() != null) {
                        responseMap.put(MainetConstants.BankParam.PRODUCTION, pgBankParamDet.getParValue());
                    }
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.REQUESTTYPE)) {
                    if (pgBankParamDet.getParValue() != null) {
                        responseMap.put(MainetConstants.BankParam.REQUESTTYPE, pgBankParamDet.getParValue());
                    }
                }
                if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F3)) {
                    if (pgBankParamDet.getParValue() != null) {
                        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3, pgBankParamDet.getParValue());
                    }
                }

            }
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID, bankId.toString());
        } else {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
                    "Some problem facing for fatching Bank Parameters");
            throw new FrameworkException("Some problem facing for fatching Bank Parameters");
        }
    }

    @Override
    @Transactional
    public void cancelTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException {
        final PaymentTransactionMas paymentTransactionMas = paymentDAO
                .getOnlineTransactionMasByTrackId(payURequestDTO.getTxnid());
        paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.CANCEL);
        paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId) {
        return paymentDAO.getPaymentMasterListById(tranCmId);
    }

    @Transactional
    public PaymentTransactionMas setTransactionMasDto(PaymentPostingDto paymentPostingDto, CommonChallanDTO result) {
        final PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
        paymentTransactionMas.setLmodDate(new Date());
        paymentTransactionMas.setLgIpMac(paymentPostingDto.getIpMac());
        paymentTransactionMas.setOrgId(result.getOrgId());
        paymentTransactionMas.setUserId(Long.valueOf(MainetConstants.NUMBERS.TWO));
        paymentTransactionMas.setReferenceDate(
                Utility.stringToDate(paymentPostingDto.getPaymentDateAndTime(), MainetConstants.DATE_AND_TIME_FORMAT_PAY));
        paymentTransactionMas.setSmServiceId(result.getServiceId());
        paymentTransactionMas.setSendKey(MainetConstants.BankParam.KEY);
        paymentTransactionMas.setSendAmount(new BigDecimal(paymentPostingDto.getPaymentAmount()));
        paymentTransactionMas.setSendProductinfo(result.getServiceName());
        paymentTransactionMas.setSendFirstname(paymentPostingDto.getPayeeName());
        paymentTransactionMas.setSendEmail(paymentPostingDto.getPayeeEmail());
        paymentTransactionMas.setSendPhone(paymentPostingDto.getPayeeMobileNo());
        paymentTransactionMas.setSendSurl(MainetConstants.BLANK);
        paymentTransactionMas.setSendFurl(MainetConstants.BLANK);
        paymentTransactionMas.setSendSalt(MainetConstants.BankParam.SALT);
        paymentTransactionMas.setSendHash(MainetConstants.BankParam.HASH);
        paymentTransactionMas.setPgType(MainetConstants.BankParam.PG);
        paymentTransactionMas.setReferenceId(paymentPostingDto.getApplNo());
        paymentTransactionMas.setSendUrl(MainetConstants.BankParam.SURL);
        paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.PENDING);
        // paymentTransactionMas.setFeeIds(payURequestDTO.getFeeIds());
        paymentTransactionMas.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        // paymentTransactionMas.setDocumentUploaded(payURequestDTO.getDocumentUploaded());
        paymentTransactionMas.setLangId((new Short(MainetConstants.ENGLISH)).intValue());
        paymentTransactionMas.setRecvBankRefNum(paymentPostingDto.getBankRefNum());
        paymentTransactionMas.setRecvMihpayid(paymentPostingDto.getRequestTransactionNo());
        paymentTransactionMas.setCfcMode(paymentPostingDto.getModeOfPay());
        paymentTransactionMas.setRecvMode(paymentPostingDto.getModeOfPay());
        paymentTransactionMas.setRecvNetAmountDebit(Double.valueOf(paymentPostingDto.getPaymentAmount()));
        paymentTransactionMas.setSendProductinfo(result.getServiceName());
        paymentDAO.savePaymentTransaction(paymentTransactionMas);
        return paymentTransactionMas;

    }

    @Override
    public Map<String, String> getPaymentPostingStatus(String requestTransactionNo) {
        Map<String, String> responseMap = new HashMap<>();
        PaymentTransactionMas paymentTransactionMas = paymentDAO.getTransMasByMihpayId(requestTransactionNo);
        if (paymentTransactionMas != null) {
            if (paymentTransactionMas.getRecvStatus().equals(MainetConstants.COMMON_STATUS.SUCCESS)) {
            	
                responseMap.put(MainetConstants.ResponseCode, MainetConstants.FlagS);
                responseMap.put(MainetConstants.ResponseMessage, MainetConstants.COMMON_STATUS.SUCCESS);
                responseMap.put(MainetConstants.ResponseDiscription, MainetConstants.PAYMENT_POSTING_API.SUCCESS_TRANS_MSG+ requestTransactionNo +MainetConstants.PAYMENT_POSTING_API.SUCCESS_RECEIPT_MSG+paymentTransactionMas.getMenuprm1());
            } else {
                responseMap.put(MainetConstants.ResponseCode, MainetConstants.FlagF);
                responseMap.put(MainetConstants.ResponseMessage, MainetConstants.COMMON_STATUS.FAILURE);
                responseMap.put(MainetConstants.ResponseDiscription, MainetConstants.COMMON_STATUS.FAILURE);
            }
        } else {
            responseMap.put(MainetConstants.ResponseCode, MainetConstants.FlagF);
            responseMap.put(MainetConstants.ResponseMessage, MainetConstants.COMMON_STATUS.FAILURE);
            responseMap.put(MainetConstants.ResponseDiscription, MainetConstants.Transaction_No_not_Dount);
        }

        return responseMap;
    }

    @Override
    public long getCountByTransactionId(String tranCmId, String orgCode) {
        Organisation orgn = orgDAO.getOrganisationByShortName(orgCode);
        Long count = paymentDAO.getCountByTransactionId(tranCmId, orgn.getOrgid());
        return count;
    }

    @Override
    public String getEncryptDecryptKey(String bankShortCode, String orgCode) {
        Organisation orgn = orgDAO.getOrganisationByShortName(orgCode);
        String key = null;
        PGBankDetail pg = paymentDAO.getBankDetailByBankName(bankShortCode, orgn.getOrgid(),null);
        if (pg != null) {
            List<PGBankParameter> pgParamList = paymentDAO.getMerchantMasterParamByBankId(pg.getPgId(), orgn.getOrgid());
            if (CollectionUtils.isNotEmpty(pgParamList)) {
                for (PGBankParameter pgDet : pgParamList) {
                    if (pgDet.getParName().equalsIgnoreCase(MainetConstants.REQUIRED_PG_PARAM.KEY)) {
                        key = pgDet.getParValue();
                        break;
                    }
                }
            }
        }
        return key;
    }

    @Override
    @Transactional
    public ResponseMessageDto paymentPostingDataUpdate(HttpServletRequest request, PaymentPostingDto paymentPostingDto,
            String pgName) throws Exception {
        PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
        CommonChallanDTO offline = new CommonChallanDTO();
        ResponseMessageDto messageDto = new ResponseMessageDto();
        try {
            offline = updateDataAfterPayment(paymentPostingDto);
            messageDto = prepareResponse(paymentPostingDto, MainetConstants.FlagS);
            paymentTransactionMas = saveTransactionDetails(paymentPostingDto, offline, messageDto, pgName);
            messageDto.setResponseTransactionNo(Long.valueOf(paymentTransactionMas.getMenuprm1()));

        } catch (Exception exception) {
            messageDto = prepareResponse(paymentPostingDto, MainetConstants.FlagF);
            paymentTransactionMas = saveTransactionDetails(paymentPostingDto, offline, messageDto, pgName);
        }
        return messageDto;
    }

    private CommonChallanDTO updateDataAfterPayment(PaymentPostingDto paymentPostingDto) {
        CommonChallanDTO offline = new CommonChallanDTO();
        Organisation orgn = orgDAO.getOrganisationByShortName(paymentPostingDto.getOrgCode());
        offline.setOrgId(orgn.getOrgid());
        offline.setUniquePrimaryId(paymentPostingDto.getApplNo());
        offline.setUserId(MainetConstants.Property.UserId);
        offline = iChallanService.getBillDetails(offline, paymentPostingDto.getDept());
        offline.setAmountToPay(paymentPostingDto.getPaymentAmount());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.Property.DES,
                offline.getOrgId());
        offline.setServiceId(serviceMaster.getSmServiceId());
        offline.setLgIpMac(paymentPostingDto.getIpMac());
        offline.setDeptCode(paymentPostingDto.getDept());
        offline.setServiceName(serviceMaster.getSmServiceName());
        offline.setDeptId(departmentDAO.getDepartmentIdByDeptCode(paymentPostingDto.getDept(), MainetConstants.STATUS.ACTIVE));
        final Long payModeIn = CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE, MainetConstants.PAY_PREFIX.PREFIX_VALUE, orgn)
                .getLookUpId();
        offline.setPayModeIn(payModeIn);
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), orgn)
                .getLookUpCode());
        Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date());
        if ((finData != null) && (finData.length > 0)) {
            offline.setFaYearId(finData[0].toString());
        }
        ChallanReceiptPrintDTO receiptPrintDTO = iChallanService.updateDataAfterPayment(offline);
        offline.setManualReceiptNo(receiptPrintDTO.getReceiptNo());
        return offline;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaymentTransactionMas saveTransactionDetails(PaymentPostingDto paymentPostingDto, CommonChallanDTO result,
            ResponseMessageDto messageDto, String pgName) throws JsonProcessingException {
        final PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
        PGBankDetail pg = null;
        Organisation orgn = orgDAO.getOrganisationByShortName(paymentPostingDto.getOrgCode());
        ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.Property.DES, orgn.getOrgid());
        if (StringUtils.isEmpty(pgName)) {
            pg = paymentDAO.getBankDetailByBankName(MainetConstants.PAYMENT_POSTING_API.PAYTM, orgn.getOrgid(),null);
        } else {
            pg = paymentDAO.getBankDetailByBankName(pgName, orgn.getOrgid(),null);
        }
        paymentTransactionMas.setLmodDate(new Date());
        paymentTransactionMas.setLgIpMac(paymentPostingDto.getIpMac());
        paymentTransactionMas.setOrgId(orgn.getOrgid());
        paymentTransactionMas.setUserId(Long.valueOf(MainetConstants.NUMBERS.TWO));
        paymentTransactionMas.setReferenceDate(
                Utility.stringToDate(paymentPostingDto.getPaymentDateAndTime(), MainetConstants.DATE_AND_TIME_FORMAT_PAY));
        paymentTransactionMas.setSmServiceId(serviceMaster.getSmServiceId());
        paymentTransactionMas.setSendKey(MainetConstants.BankParam.KEY);
        paymentTransactionMas.setSendAmount(new BigDecimal(paymentPostingDto.getPaymentAmount()));
        paymentTransactionMas.setSendProductinfo(serviceMaster.getSmServiceName());
        paymentTransactionMas.setSendFirstname(paymentPostingDto.getPayeeName());
        paymentTransactionMas.setSendEmail(paymentPostingDto.getPayeeEmail());
        paymentTransactionMas.setSendPhone(paymentPostingDto.getPayeeMobileNo());
        paymentTransactionMas.setSendSurl(MainetConstants.BLANK);
        paymentTransactionMas.setSendFurl(MainetConstants.BLANK);
        paymentTransactionMas.setSendSalt(MainetConstants.BankParam.SALT);
        paymentTransactionMas.setReferenceId(paymentPostingDto.getApplNo());
        paymentTransactionMas.setSendUrl(MainetConstants.BankParam.SURL);
        paymentTransactionMas.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        paymentTransactionMas.setLangId((new Short(MainetConstants.ENGLISH)).intValue());
        paymentTransactionMas.setRecvBankRefNum(paymentPostingDto.getBankRefNum());
        paymentTransactionMas.setRecvMihpayid(paymentPostingDto.getRequestTransactionNo());
        paymentTransactionMas.setCfcMode(paymentPostingDto.getModeOfPay());
        paymentTransactionMas.setRecvMode(paymentPostingDto.getModeOfPay());
        paymentTransactionMas.setRecvNetAmountDebit(Double.valueOf(paymentPostingDto.getPaymentAmount()));
        paymentTransactionMas.setSendHash(mapper.writeValueAsString(paymentPostingDto));
        paymentTransactionMas.setUpdatedDate(new Date());
        paymentTransactionMas.setRecvStatus(messageDto.getResponseMessage());
        paymentTransactionMas.setRecvErrm(messageDto.getErrorDesc());
        paymentTransactionMas.setRecvHash(mapper.writeValueAsString(messageDto));
        paymentTransactionMas.setMenuprm1(result.getManualReceiptNo());
        if (pg != null) {
            paymentTransactionMas.setPgType(String.valueOf(pg.getPgId()));
            paymentTransactionMas.setPgSource(pg.getPgName());
        }
        // paymentDAO.savePaymentTransaction(paymentTransactionMas);
        paymentDAO.saveTranscationBeforeResponse(paymentTransactionMas);
        return paymentTransactionMas;

    }

    private ResponseMessageDto prepareResponse(PaymentPostingDto paymentPostingDto, String statusFlag) {
        ResponseMessageDto messageDto = new ResponseMessageDto();
        if (statusFlag.equalsIgnoreCase(MainetConstants.FlagS)) {
            messageDto.setResponseCode(MainetConstants.FlagS);
            messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.SUCCESS);
            messageDto.setErrorDesc(MainetConstants.REQUIRED_PG_PARAM.NA);
        } else {
            messageDto.setResponseCode(MainetConstants.FlagF);
            messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
            messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108);
            messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108_MSG);
        }
        messageDto.setRequestTransactionNo(paymentPostingDto.getRequestTransactionNo());
        return messageDto;

    }

    @Override
    public PaymentTransactionMasDTO getStatusByReferenceNo(String referenceId) {
        PaymentTransactionMas entity = new PaymentTransactionMas();
        PaymentTransactionMasDTO dto = new PaymentTransactionMasDTO();
        entity = paymentDAO.getStatusByReferenceId(referenceId);
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    @Override
    @Transactional
    public Map<String, String> processTransactionForCallStatusAPI(HttpServletRequest httpServletRequest,
            final PaymentRequestDTO payURequestDTO) throws FrameworkException {
        LOG.info("Start processTransactionForCallStatusAPI in paymentServiceImpl");
        Map<String, String> responseMap = new HashMap<>();
        PGBankDetail pg = paymentDAO.getBankDetailByBankName(payURequestDTO.getPgName(), payURequestDTO.getOrgId());
        if (pg != null) {
            final List<PGBankParameter> pgBankParamDets = paymentDAO.getMerchantMasterParamByBankId(pg.getPgId(),
                    payURequestDTO.getOrgId());
            if ((pgBankParamDets != null) && !pgBankParamDets.isEmpty()) {
                for (final PGBankParameter pgBankParamDet : pgBankParamDets) {

                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F1)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField1(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F2)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField2(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F3)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setUdf3(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F4)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField4(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F5)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField5(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F6)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField6(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F7)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField7(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F8)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setAddField8(pgBankParamDet.getParValue());
                        }
                    }

                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRANS_TYE)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setRequestType(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SALT)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setSalt(pgBankParamDet.getParValue());
                        }
                    }
                    if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.KEY)) {
                        if (pgBankParamDet.getParValue() != null) {
                            payURequestDTO.setKey(pgBankParamDet.getParValue());
                        }
                    }
                }
            }
            payURequestDTO.setMerchantId(pg.getMerchantId());

        }

        responseMap = getRequestStatusCall(payURequestDTO);
        LOG.info("End processTransactionForCallStatusAPI in paymentServiceImpl"
                + responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS));

        try {
            if (responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS).equalsIgnoreCase(MainetConstants.PAYU_STATUS.SUCCESS)) {
                payURequestDTO.getRecieptDTO().setManualReceiptNo(responseMap.get(MainetConstants.BankParam.TXN_ID));
                payURequestDTO.getRecieptDTO()
                        .setManualReeiptDate(Utility.stringToDate(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PAYDATE)));
                iChallanService.updateDataAfterPayment(payURequestDTO.getRecieptDTO());
            }

        } catch (Exception e) {
            LOG.error("End processTransactionForCallStatusAPI in paymentServiceImpl", e);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                    MainetConstants.PAYU_STATUS.IN_PROGRESS);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
                    ApplicationSession.getInstance().getMessage("eip.success.techerror.msg"));
        }
        LOG.info("processTransactionForCallStatusAPI in paymentServiceImpl responseMap :- " + responseMap);
        proceesTransactionAfterPayment(responseMap);

        LOG.info("End processTransactionForCallStatusAPI in paymentServiceImpl");
        return responseMap;

    }

    private Map<String, String> getRequestStatusCall(PaymentRequestDTO paymentRequestDTO) {
        LOG.info("start getRequestStatusCall in paymentServiceImpl");
        Map<String, String> responseMap = new HashMap<>();
        try {
            final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(paymentRequestDTO.getPgName());
            return responseMap = paymentStrategy.generatePaymentRequestForStatusAPI(paymentRequestDTO);
        } catch (final Exception e) {
            throw new FrameworkException("Exeception occur in getRequestDTO()...", e);
        }

    }

    @Override
    @Transactional
    public PaymentDetailsResponseDTO fetchPaymentDetails(PaymentDetailSearchDTO paySearchDeatilDto, Long orgId) {
        PaymentDetailsResponseDTO payResponseDto = new PaymentDetailsResponseDTO();
        CommonChallanDTO offline = new CommonChallanDTO();
        try {
            offline.setOrgId(orgId);
            offline.setUniquePrimaryId(paySearchDeatilDto.getApplNo());
            offline.setPropNoConnNoEstateNoL(paySearchDeatilDto.getLegacyApplNo());
            offline.setUserId(MainetConstants.Property.UserId);
            offline = iChallanService.getBillDetails(offline, paySearchDeatilDto.getDeptCode());
            if (offline != null) {
                payResponseDto.setApplNo(offline.getPropNoConnNoEstateNoV());
                payResponseDto.setLegacyApplNo(offline.getReferenceNo());
                payResponseDto.setPayeeName(offline.getApplicantFullName());
                payResponseDto.setPayeeEmail(offline.getEmailId());
                payResponseDto.setPayeeAddress(offline.getApplicantAddress());
                payResponseDto.setPayeeMobileNo(offline.getMobileNumber());
                payResponseDto.setPaymentAmount(offline.getAmountToPay());
                payResponseDto.setOrgCode(paySearchDeatilDto.getOrgCode());
                payResponseDto.setDeptCode(paySearchDeatilDto.getDeptCode());
                payResponseDto.setResponseCode(MainetConstants.FlagS);
                payResponseDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.SUCCESS);
                if (!offline.getBillDetails().isEmpty()) {
                    payResponseDto.setBillNumber(offline.getBillDetails().get("bmIdno"));
                    payResponseDto.setBillDate(offline.getBillDetails().get("bmBilldt"));
                    payResponseDto.setDueDate(offline.getBillDetails().get("bmDuedate"));
                    payResponseDto.setBillPeriod(offline.getBillDetails().get("billPeriod"));
                }
            }
        } catch (Exception ex) {
            payResponseDto.setResponseCode(MainetConstants.FlagF);
            payResponseDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
        }

        return payResponseDto;
    }

    @Override
    @Transactional
    public Long getCountByApplNoOrLegacyNo(String applNo, String legacyApplNo, Long orgId, String deptCode) {
        Long count = null;
        try {
            BillDetailsService billDetailsService = null;
            switch (deptCode) {
            case MainetConstants.DEPT_SHORT_NAME.PROPERTY:
                billDetailsService = ApplicationContextProvider.getApplicationContext().getBean(
                        MainetConstants.PAYMENT_POSTING_API.PROPERTY_BILL_PAYMENT,
                        BillDetailsService.class);
                break;
            case MainetConstants.DEPT_SHORT_NAME.WATER:
                billDetailsService = ApplicationContextProvider.getApplicationContext().getBean(
                        MainetConstants.PAYMENT_POSTING_API.WATER_BILL_SERVICE,
                        BillDetailsService.class);
                break;
            }
            count = billDetailsService.getCountByApplNoOrLegacyNo(applNo, legacyApplNo, orgId);
        } catch (Exception e) {
            throw new FrameworkException(
                    "Exception in getCountByApplNoOrLegacyNo of paymentserviceImpl :",
                    e);
        }
        return count;
    }

    @Override
    @Transactional
    public Map<String, Object> checkAuthorization(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        PGBankDetail pg = new PGBankDetail();
        Organisation orgn = new Organisation();
        boolean authKey = false;
        String pgUser = null;
        String pgPass = null;
        String userId = null;
        String password = null;
        String orgCode = request.getHeader(MainetConstants.PAYMENT_POSTING_API.ORG_CODE);
        String encodedUserIdAndPass = request.getHeader(MainetConstants.PAYMENT_POSTING_API.AUTHORIZATION);
        if (StringUtils.isEmpty(encodedUserIdAndPass) || StringUtils.isEmpty(orgCode)) {
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE, PAYMENT_POSTING_API.ERROR_CODE_104);
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_MSG, PAYMENT_POSTING_API.ERROR_CODE_104_MSG);
            map.put(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS, authKey);
            return map;
        }
        try {
            orgn = orgDAO.getOrganisationByShortName(orgCode);
        } catch (Exception e) {
            LOG.info("Error occurs in checkAuthorization while fetching organisation details");
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE, PAYMENT_POSTING_API.ERROR_CODE_111);
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_MSG, PAYMENT_POSTING_API.ERROR_CODE_111_MSG);
            map.put(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS, authKey);
            return map;
        }

        if (orgn != null) {
            byte[] valueDecoded = Base64.decodeBase64(encodedUserIdAndPass);
            String decodedUserIdAndPass = new String(valueDecoded);
            LOG.info("Decoded value is " + decodedUserIdAndPass);
            try {
                int subStringIndex = decodedUserIdAndPass.indexOf(":");
                userId = decodedUserIdAndPass.substring(0, subStringIndex);
                LOG.info("UserId  is " + userId);
                password = decodedUserIdAndPass.substring(subStringIndex + 1);
                LOG.info("password  is " + password);
            } catch (Exception e) {
                LOG.info("In checkAuthorization while fetching authentication details");
                map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE, PAYMENT_POSTING_API.ERROR_CODE_110);
                map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_MSG, PAYMENT_POSTING_API.ERROR_CODE_110_MSG);
                map.put(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS, authKey);
                return map;
            }

            if (orgn != null) {
                pg = paymentDAO.getBankDetailByMerchantId(userId, orgn.getOrgid());
                List<PGBankParameter> pgParamList = paymentDAO.getMerchantMasterParamByBankId(pg.getPgId(),
                        orgn.getOrgid());
                if (pg != null) {
                    if (CollectionUtils.isNotEmpty(pgParamList)) {
                        for (PGBankParameter pgDet : pgParamList) {
                            if (pgDet.getParName().equals(MainetConstants.BankParam.MID)) {
                                pgUser = pgDet.getParValue();
                            }
                            if (pgDet.getParName().equals(MainetConstants.BankParam.SALT)) {
                                pgPass = pgDet.getParValue();
                            }
                        }
                    }
                }
            }
            if (userId.equals(pgUser) && password.equals(pgPass)) {
                authKey = true;
                map.put(MainetConstants.PAYMENT_POSTING_API.PG_NAME, pg.getPgName());
                map.put(MainetConstants.PAYMENT_POSTING_API.ORG_CODE, orgCode);
                map.put(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS, authKey);
            }
        }
        if (authKey == false) {
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE, PAYMENT_POSTING_API.ERROR_CODE_110);
            map.put(MainetConstants.PAYMENT_POSTING_API.ERROR_MSG, PAYMENT_POSTING_API.ERROR_CODE_110_MSG);
            map.put(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS, authKey);
        }
        return map;
    }

    @Override
    @Transactional
    public Organisation getOgrnByOrgCode(String orgCode) {
        Organisation orgn = null;
        try {
            orgn = orgDAO.getOrganisationByShortName(orgCode);
        } catch (Exception e) {
            LOG.info("Error occurs in getOgrnByOrgCode while fetching organisation details");
        }
        return orgn;
    }

    @Override
    public ResponseMessageDto checkValidationForPaymentPostingV2(PaymentPostingDto payDto, ResponseMessageDto messageDto) {
        if (StringUtils.isEmpty(payDto.getApplNo()) || StringUtils.isEmpty(payDto.getPayeeName()) ||
                StringUtils.isEmpty(payDto.getPayeeMobileNo()) ||
                StringUtils.isEmpty(payDto.getDept()) || StringUtils.isEmpty(payDto.getPaymentAmount()) ||
                StringUtils.isEmpty(payDto.getBankRefNum()) || StringUtils.isEmpty(payDto.getRequestTransactionNo()) ||
                StringUtils.isEmpty(payDto.getModeOfPay()) || StringUtils.isEmpty(payDto.getOrgCode()) ||
                StringUtils.isEmpty(payDto.getIpMac()) || StringUtils.isEmpty(payDto.getPaymentDateAndTime())) {
            messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104);
            messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104_MSG);
        } else {
            if (!(payDto.getDept().equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) &&
                    !(payDto.getDept().equals(MainetConstants.DEPT_SHORT_NAME.WATER))) {
                messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109);
                messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109_MSG);
            } else {
                Long count = getCountByTransactionId(payDto.getRequestTransactionNo(), payDto.getOrgCode());
                if (count > 0) {
                    messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_102);
                    messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_102_MSG);
                } else {
                    messageDto.setErrorCode("");
                    messageDto.setErrorDesc("");
                }
            }

        }

        return messageDto;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getDetails(PaymentDetailSearchDTO paySearchDeatilDto) {
        PaymentDetailsResponseDTO payResponseDto = new PaymentDetailsResponseDTO();
        ResponseEntity<?> responseEntity = null;
        Organisation orgn = getOgrnByOrgCode(paySearchDeatilDto.getOrgCode());
        Long count = 0L;
        if (StringUtils.isNotBlank(paySearchDeatilDto.getApplNo())) {
            count = getCountByApplNoOrLegacyNo(paySearchDeatilDto.getApplNo(),
                    null, orgn.getOrgid(), paySearchDeatilDto.getDeptCode());
            if (count > 0) {
                paySearchDeatilDto.setLegacyApplNo(MainetConstants.BLANK);
            }
        }
        if (count == 0 && StringUtils.isNotBlank(paySearchDeatilDto.getLegacyApplNo())) {
            count = getCountByApplNoOrLegacyNo(null,
                    paySearchDeatilDto.getLegacyApplNo(), orgn.getOrgid(),
                    paySearchDeatilDto.getDeptCode());
            if (count > 0) {
                paySearchDeatilDto.setApplNo(MainetConstants.BLANK);
            }
        }
        if (count != null && count > 0) {
            payResponseDto = fetchPaymentDetails(paySearchDeatilDto, orgn.getOrgid());
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(payResponseDto);
        } else {

            payResponseDto.setResponseCode(MainetConstants.FlagF);
            payResponseDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
            payResponseDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_105);
            payResponseDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_105_MSG);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(payResponseDto);
        }
        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseMessageDto checkAllValidation(PaymentPostingDto payDto, ResponseMessageDto messageDto) {
        if (StringUtils.isEmpty(payDto.getApplNo()) || StringUtils.isEmpty(payDto.getPayeeName()) ||
                StringUtils.isEmpty(payDto.getPayeeMobileNo()) ||
                StringUtils.isEmpty(payDto.getDept()) || StringUtils.isEmpty(payDto.getPaymentAmount()) ||
                StringUtils.isEmpty(payDto.getBankRefNum()) || StringUtils.isEmpty(payDto.getRequestTransactionNo()) ||
                StringUtils.isEmpty(payDto.getModeOfPay()) || StringUtils.isEmpty(payDto.getOrgCode()) ||
                StringUtils.isEmpty(payDto.getIpMac()) || StringUtils.isEmpty(payDto.getPaymentDateAndTime())) {
            messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104);
            messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104_MSG);
        } else {
            if ((payDto.getOrgCode() == null && !(payDto.getDept().equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)))) {

                messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109);
                messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109_MSG);
            } else {
                try {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(payDto.getOrgCode()))
                        ;
                    Organisation orgn = orgDAO.getOrganisationByShortName(payDto.getOrgCode());
                } catch (Exception e) {
                    messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109);
                    messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109_MSG);
                    return messageDto;
                }
                Long count = getCountByTransactionId(payDto.getRequestTransactionNo(), payDto.getOrgCode());
                if (count > 0) {
                    messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_102);
                    messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_102_MSG);
                } else {
                    messageDto.setErrorCode("");
                    messageDto.setErrorDesc("");
                }
            }

        }

        return messageDto;
    }

	@Override
	public ResponseMessageDto getPaymentPostingStatusV2(String requestTransactionNo) {
		ResponseMessageDto messageDto = new ResponseMessageDto();
		PaymentTransactionMas paymentTransactionMas = paymentDAO.getTransMasByMihpayId(requestTransactionNo);
		if (paymentTransactionMas != null) {
			if (paymentTransactionMas.getRecvStatus().equals(MainetConstants.COMMON_STATUS.SUCCESS)) {
				messageDto.setResponseCode(MainetConstants.FlagS);
				messageDto.setResponseMessage(MainetConstants.COMMON_STATUS.SUCCESS);
				messageDto.setRequestTransactionNo(requestTransactionNo);
				if(StringUtils.isNotBlank(paymentTransactionMas.getMenuprm1()))
				messageDto.setResponseTransactionNo(Long.valueOf(paymentTransactionMas.getMenuprm1()));
				messageDto.setErrorCode("");
				messageDto.setErrorDesc("");
			} else {
				messageDto.setResponseCode(MainetConstants.FlagF);
				messageDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
				messageDto.setRequestTransactionNo(requestTransactionNo);
				if(StringUtils.isNotBlank(paymentTransactionMas.getMenuprm1())) {
				messageDto.setResponseTransactionNo(Long.valueOf(paymentTransactionMas.getMenuprm1()));
				}else {
					messageDto.setResponseTransactionNo(-1);
				}
				messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_112);
				messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_112_MSG);
			}
		} else {
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
			messageDto.setRequestTransactionNo(requestTransactionNo);
			messageDto.setResponseTransactionNo(-1);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_106);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_106_MSG);
		}

		return messageDto;
	}

	@Override
	public PaymentDetailResDto checkPaymentStatus(String referenceId) {
		// TODO Auto-generated method stub
		PaymentDetailResDto citizenDashDto = new PaymentDetailResDto();
		Object[] resDto = paymentDAO.checkPaymentStatus(referenceId);
		if (resDto != null) {
			citizenDashDto.setLabelName(ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));
			citizenDashDto.setOrder_number(resDto[0].toString());
			citizenDashDto.setTransaction_date_time(new SimpleDateFormat(MainetConstants.DATE_AND_TIME_FORMAT_PAYRECIEPT).format(resDto[1]));
			citizenDashDto.setService_information(resDto[2].toString());
			citizenDashDto.setApplicant_name(resDto[3].toString());
			citizenDashDto.setContact_number(resDto[4].toString());

			citizenDashDto.setEmail_id(resDto[5].toString());
			citizenDashDto.setTransaction_status(resDto[6].toString());
			
			citizenDashDto.setPayment_amount(resDto[7].toString());
			if(resDto[8]!=null)
			citizenDashDto.setTransaction_reference_number(resDto[8].toString());
			return citizenDashDto;
		}
		return citizenDashDto;
	}

	@Override
	@Transactional
	public String generateNicGateWayRequestUrl(PaymentRequestDTO paymentReqDto,CommonChallanDTO challandto,HttpServletRequest request) {
		PGBankDetail bankDet=null;
		if(StringUtils.equalsIgnoreCase(paymentReqDto.getPayModeorType(),"Payu")) {
			bankDet=paymentDAO.getBankDetailByBankName(MainetConstants.PG_SHORTNAME.PAYU, paymentReqDto.getOrgId());
		}else
			bankDet=paymentDAO.getBankDetailByBankName(MainetConstants.PG_SHORTNAME.NicGateway, paymentReqDto.getOrgId());
		paymentReqDto.setBankId(bankDet.getPgId());
		if(paymentReqDto.geteGrsPayMode1()!=null) 
			paymentReqDto.setPaymentType(CommonMasterUtility.getHierarchicalLookUp(paymentReqDto.geteGrsPayMode1(), paymentReqDto.getOrgId()).getLookUpCode());
		if(paymentReqDto.geteGrsPayMode2()!=null) 
			paymentReqDto.setBankIdStr(CommonMasterUtility.getHierarchicalLookUp(paymentReqDto.geteGrsPayMode2(), paymentReqDto.getOrgId()).getLookUpCode());
		proceesTransactionBeforePayment(request, paymentReqDto);
		if(paymentReqDto!=null) {
			saveEgrassPaymentData(paymentReqDto,challandto);
		}
		return paymentReqDto.getPayRequestMsg();
	}

	private void saveEgrassPaymentData(PaymentRequestDTO paymentReqDto, CommonChallanDTO challandto) {
		EgrassPaymentENtity entity=new EgrassPaymentENtity();
		entity.setPaymode(paymentReqDto.geteGrsPayMode1());
		entity.setAggrigator(paymentReqDto.geteGrsPayMode2());
		entity.setChequeDDDate(challandto.getBmChqDDDate());
		entity.setChequeDDNo(challandto.getBmChqDDNo());
		entity.setOrgId(paymentReqDto.getOrgId());
		entity.setLgIpMac(Utility.getMacAddress());
		entity.setLangId(1);
		entity.setPayBank(challandto.getBmDrawOn());
		entity.setPayeeName(challandto.getApplicantName());
		entity.setRemarks(paymentReqDto.getTrnRemarks());
		if(paymentReqDto.getEmpId()!=null)
		entity.setUserId(paymentReqDto.getEmpId());
		else
			entity.setUserId(1L);	
		entity.setTrnsCmId(paymentReqDto.getTxnid());
		entity.setReferenceId(paymentReqDto.getUdf2());
		entity.setLmodDate(new Date());
		entity.setSendAmount(paymentReqDto.getDueAmt());
		try {
			egrassPaymentTransactionRepository.save(entity);
		} catch (Exception e) {
			LOG.error("Exception occure at the time of saving saveEgrassPaymentData data");
		}
	}
	@Override
	@Transactional
	public EgrassPaymentENtity  getEgrassChallanData(String refNo,Long orgId) {
		List<EgrassPaymentENtity> entity=egrassPaymentTransactionRepository.fetchPaymentStatus(refNo, orgId);
		if(CollectionUtils.isNotEmpty(entity)) {
			return entity.get(0);
		}
		return null;
	}
       @Override
	public Object[] getLicNoAndApplicationId(String refNo, Long orgId) {
    	   
    	  try {
			List<Object[]> objArr=egrassPaymentTransactionRepository.getInformationFromTbBPMSLICMaster(refNo, orgId);
			if(CollectionUtils.isNotEmpty(objArr)) {
				Object[] objArr1=objArr.get(0);
				return objArr1;
			}
		} catch (Exception e) {
			LOG.error("Exception occure at the time of fetching getLicNoAndApplicationId data");
		}
    	   return null;

	}
     
       
	@Override
	@Transactional(readOnly = true)
	public Map<Long, String> getAllPgBankByDeptCode(long orgId, String deptCode) throws FrameworkException {

		LOG.info("starts the getBankDetailListService method ");
		return ApplicationContextProvider.getApplicationContext().getBean(CommonService.class)
				.getAllPgBankByDeptCode(orgId, deptCode);
	}
    
    
	
}