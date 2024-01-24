package com.abm.mainet.payment.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.payment.service.IPaymentService;
import com.abm.mainet.payment.ui.model.PaymentModel;

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@Controller
@RequestMapping("PaymentController.html")
public class PaymentController extends AbstractController<PaymentModel> {

    public static final Logger LOG = Logger.getLogger(PaymentController.class);

    @Autowired
	private IPaymentService paymentService;
    
    @Autowired
    private IOrganisationService iOrganisationService;
    
    @Autowired
    private IServiceMasterService serviceMaster;

    @RequestMapping(params = "redirectDTO", method = RequestMethod.GET)
    public ModelAndView showPayMentDetailForm(
            @ModelAttribute("payRequestDTO") final PaymentRequestDTO paymentRequestDTO,
            final BindingResult mapping1BindingResult, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ModelAndView mv = null;

        getModel().setPaymentRequestDTO(paymentRequestDTO);
        getModel().setOfflineDTO(paymentRequestDTO.getRecieptDTO());

        mv = new ModelAndView("PayFormDetail", MainetConstants.REQUIRED_PG_PARAM.COMMAND, paymentRequestDTO);
        Organisation organisation = iOrganisationService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.EASYPAY_PARAM.CHECK_PAYMENT_STATUS, organisation);
        if(CollectionUtils.isNotEmpty(lookUps)) {
		if (StringUtils.isNotBlank(lookUps.get(0).getLookUpCode()) && lookUps.get(0).getLookUpCode().equals(MainetConstants.PAY_STATUS_FLAG.YES)) {
			PaymentTransactionMasDTO paymentTransactionMas =paymentService.getStatusByReferenceNo(paymentRequestDTO.getApplicationId());
	        if(paymentTransactionMas.getReferenceId()!=null) {
	        	mv.addObject("PaymentStatus", MainetConstants.PAY_STATUS_FLAG.YES);
				getModel().setPaymentTransactionMas(paymentTransactionMas);
	        }
		 }
        }
        return mv;
    }

	/*
	 * As per Palam SirCode for User session variable data null after payment
	 * gateway page open and payment done
	 */ 
    @RequestMapping(params = "confirm", method = RequestMethod.POST)
    public ModelAndView confirmPayment(@ModelAttribute("command") final PaymentRequestDTO paymentRequestDTO,
            final HttpServletRequest httpServletRequest,final HttpServletResponse httpServletResponse) {
        ModelAndView modelAndView = null;
        bindModel(httpServletRequest);
        getModel().setOwnerName(paymentRequestDTO.getApplicantName());
        getModel().setPgId(paymentRequestDTO.getBankId());
        if (getModel().getPaymentRequestDTO() != null && getModel().getPaymentRequestDTO().getOrgId() != null) {
            getModel().setOrgId(getModel().getPaymentRequestDTO().getOrgId());
        } else {
            getModel().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        if (UserSession.getCurrent().getLanguageId() == 1) {
            getModel().setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            getModel().setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        getModel().setLangId(UserSession.getCurrent().getLanguageId());
        if (paymentRequestDTO.getEmail() != null) {
            getModel().setEmailId(paymentRequestDTO.getEmail());
        }
        if (paymentRequestDTO.getMobNo() != null) {
            getModel().setMobNo(paymentRequestDTO.getMobNo());
        }
        final PaymentRequestDTO payRequestDTO = getModel().getPaymentRequestDTO();
        try {
        	
            payRequestDTO.setBankId(paymentRequestDTO.getBankId());
            payRequestDTO.setEmail(paymentRequestDTO.getEmail());
            payRequestDTO.setApplicantName(paymentRequestDTO.getApplicantName());
            payRequestDTO.setMobNo(paymentRequestDTO.getMobNo());
            LOG.error("Logged before calling processPayUTransactionBeforePayment for Application Id: "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
            if (getModel().processPayUTransactionBeforePayment(httpServletRequest,httpServletResponse, payRequestDTO)) {
            	LOG.error("Logged after calling processPayUTransactionBeforePayment for Application Id: "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
                switch (payRequestDTO.getPgName()) {

                case MainetConstants.PG_SHORTNAME.PAYU:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.PAYU,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.TECH_PROCESS:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.TP,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.MAHA_ONLINE:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.MOL,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.HDFC:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.HDFC,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.CCA:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.CCA,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.AWL:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.AWL,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                case MainetConstants.PG_SHORTNAME.EASYPAY:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.Easypay,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                case MainetConstants.PG_SHORTNAME.ICICI:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.ICICI,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                case MainetConstants.PG_SHORTNAME.BILLCLOUD:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.BILLCLOUD,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                case MainetConstants.PG_SHORTNAME.BILLDESK:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.BILLDESK,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                case MainetConstants.PG_SHORTNAME.RAZORPAY:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.RAZORPAY,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.ATOMPAY:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.ATOMPAY,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;    
                case MainetConstants.PG_SHORTNAME.EASEBUZZ:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.EASEBUZZ,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.ISGPAY:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.ISGPAY,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                case MainetConstants.PG_SHORTNAME.NicGateway:
                    modelAndView = new ModelAndView(MainetConstants.REQUIRED_PG_PARAM.NicGateway,
                            MainetConstants.REQUIRED_PG_PARAM.COMMAND, payRequestDTO);
                    break;
                    
                }
                LOG.error("redirecting to :"+payRequestDTO.getPgName()+ " for Application Id "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
              
                return modelAndView;
            }
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            final Map<String, String> responseMap = new HashMap<>(0);

            if (paymentRequestDTO.getErrorCause() == null) {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, getApplicationSession().getMessage("common.payment.tech.issue"));
            } else {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, payRequestDTO.getErrorCause());
            }
            LOG.error("redirecting to : payExceptionView");
            return paymentExceptionView(responseMap);

        }
        LOG.error("redirecting to : PayFormDetail");
        return new ModelAndView("PayFormDetail", MainetConstants.REQUIRED_PG_PARAM.COMMAND, paymentRequestDTO);
    }

    private ModelAndView paymentExceptionView(final Map<String, String> responseMap) {
        return new ModelAndView("payExceptionView", MainetConstants.REQUIRED_PG_PARAM.COMMAND, responseMap);
    }

    @RequestMapping(params = "failPayU", method = RequestMethod.POST)
    public ModelAndView failurePayment(final HttpServletRequest httpServletRequest) {

        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.PAYU, httpServletRequest);
    }

    @RequestMapping(params = "cancelPayU", method = RequestMethod.POST)
    public ModelAndView cencalPayment(final HttpServletRequest httpServletRequest) {

        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.PAYU, httpServletRequest);
    }

    @RequestMapping(params = "payuSuccess", method = RequestMethod.POST)
    public ModelAndView suceessPayUPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.PAYU, httpServletRequest);
    }
    @RequestMapping(params = "atomPaymentRedirect", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView suceessAtomPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.ATOMPAY, httpServletRequest);
    }
    @RequestMapping(params = "atomCancel", method = RequestMethod.POST)
    public ModelAndView cancelAtomPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.ATOMPAY, httpServletRequest);
    }
    @RequestMapping(params = "payuCancel", method = RequestMethod.POST)
    public ModelAndView cancelPayUPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.PAYU, httpServletRequest);
    }

    @RequestMapping(params = "payuFail", method = RequestMethod.POST)
    public ModelAndView failPayUPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.PAYU, httpServletRequest);
    }

    @RequestMapping(params = "tpSuccess", method = RequestMethod.POST)
    public ModelAndView suceessTpPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.TECH_PROCESS, httpServletRequest);
    }

    @RequestMapping(params = "tpFail", method = RequestMethod.POST)
    public ModelAndView failTpPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.TECH_PROCESS, httpServletRequest);
    }

    @RequestMapping(params = "tpCancel", method = RequestMethod.POST)
    public ModelAndView cancelsTpPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.TECH_PROCESS, httpServletRequest);
    }

    @RequestMapping(params = "mhSuccess", method = RequestMethod.POST)
    public ModelAndView suceessMHPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.MAHA_ONLINE, httpServletRequest);
    }

    @RequestMapping(params = "mhFail", method = RequestMethod.POST)
    public ModelAndView failMHPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.MAHA_ONLINE, httpServletRequest);
    }

    @RequestMapping(params = "mhCancel", method = RequestMethod.POST)
    public ModelAndView cancelMHPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.MAHA_ONLINE, httpServletRequest);
    }

    @RequestMapping(params = "hdfcSuccess", method = RequestMethod.POST)
    public ModelAndView suceessHDFCPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.HDFC, httpServletRequest);
    }

    @RequestMapping(params = "hdfcFail", method = RequestMethod.POST)
    public ModelAndView failHDFCPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.HDFC, httpServletRequest);
    }

    @RequestMapping(params = "hdfcCancel", method = RequestMethod.POST)
    public ModelAndView cancelHDFCPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.HDFC, httpServletRequest);
    }

    @RequestMapping(params = "hdfcCCARedirect", method = RequestMethod.POST)
    public ModelAndView suceessHdfcCCAPayment(final HttpServletRequest httpServletRequest) {
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA))
    	{
    		return sudaProcessPayResponse("successPayU", MainetConstants.PG_SHORTNAME.CCA, httpServletRequest);
    	}
    		return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.CCA, httpServletRequest);
    	
		
    }

    @RequestMapping(params = "hdfcCCAcancel", method = RequestMethod.POST)
    public ModelAndView cancelHdfcCCAPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.CCA, httpServletRequest);
    }
    
    @RequestMapping(params = "hdfcAWLRedirect", method = RequestMethod.POST)
    public ModelAndView suceessHdfcAWLPayment(final HttpServletRequest httpServletRequest) {
    	LOG.error("Response received from AWL");
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.AWL, httpServletRequest);
    }

    @RequestMapping(params = "hdfcAWLAcancel", method = RequestMethod.POST)
    public ModelAndView cancelHdfcAWLPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.AWL, httpServletRequest);
    }
    
    @RequestMapping(params = "hdfcAWLfail", method = RequestMethod.POST)
    public ModelAndView failHdfcAWLPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.AWL, httpServletRequest);
    }
    
    @RequestMapping(params = "easypayRedirect", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView suceessEasypayPayment(final HttpServletRequest httpServletRequest) {
    	LOG.error("Response received from Easypay");
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.EASYPAY, httpServletRequest);
    }
    
    @RequestMapping(params = "iciciCCAcancel", method = RequestMethod.POST)
    public ModelAndView cancelICICICCAPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.ICICI, httpServletRequest);
    }
    
    @RequestMapping(params = "iciciCCARedirect", method = RequestMethod.POST)
    public ModelAndView suceessICICICCAPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.ICICI, httpServletRequest);
    }
    
    @RequestMapping(params = "iciciCCAfail", method = RequestMethod.POST)
    public ModelAndView failICICICCAPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.ICICI, httpServletRequest);
    }
    
    @RequestMapping(params = "billCloudRedirect", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView suceessBillCloudPayment(final HttpServletRequest httpServletRequest) {
    	LOG.error("Response received from Bill Cloud");
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.BILLCLOUD, httpServletRequest);
    }
    
    @RequestMapping(params = "billDeskRedirect", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView suceessBillDeskPayment(final HttpServletRequest httpServletRequest) {
    	LOG.error("Response received from Bill Desk");
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.BILLDESK, httpServletRequest);
    }

    @RequestMapping(params = "redirectToRazorpay", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView suceessRazorPayment(final HttpServletRequest httpServletRequest) {
    	LOG.error("Response received from Razorpay");
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.RAZORPAY, httpServletRequest);
    }
    
    @RequestMapping(params = "easebuzzRedirect", method = RequestMethod.POST)
    public ModelAndView suceessEasebuzzPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.EASEBUZZ, httpServletRequest);
    }

    @RequestMapping(params = "easebuzzcancel", method = RequestMethod.POST)
    public ModelAndView cancelEasebuzzPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("cancelPayU", MainetConstants.PG_SHORTNAME.EASEBUZZ, httpServletRequest);
    }
    
    @RequestMapping(params = "easebuzzfail", method = RequestMethod.POST)
    public ModelAndView failEasebuzzPayment(final HttpServletRequest httpServletRequest) {
        return processPayResponse("failPayU", MainetConstants.PG_SHORTNAME.EASEBUZZ, httpServletRequest);
    }
    @RequestMapping(params = "ISGPayredirect", method = RequestMethod.POST)
    public ModelAndView isgPayRedirect(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.ISGPAY, httpServletRequest);
    }
    @RequestMapping(params = "NICPayredirect", method = RequestMethod.POST)
    public ModelAndView nicPayredirect(final HttpServletRequest httpServletRequest) {
        return processPayResponse("successPayU", MainetConstants.PG_SHORTNAME.NicGateway, httpServletRequest);
    }
    
    private ModelAndView processPayResponse(String viewName, final String gatewayFlag,
            final HttpServletRequest httpServletRequest) {
        Map<String, String> responseMap = new HashMap<>(0);
        LOG.error("Response Received from :"+gatewayFlag);
        try {
        	LOG.error("before calling capturePayUResponseInMapFromRequest");
        	
            responseMap = getModel().capturePayUResponseInMapFromRequest(httpServletRequest, gatewayFlag,
                    getModel().getOrgId(), getModel().getLangId());
            LOG.error("after calling capturePayUResponseInMapFromRequest");

            if (responseMap == null) {
                responseMap = new HashMap<>(0);
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, "No response received from service!");
                LOG.error("No response received from service: ");
                throw new FrameworkException("No response received from service!");
            }

            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.OWNER_NAME, getModel().getOwnerName());
            if ((getModel().getApmApplicationId() != 0) && (getModel().getServiceId() != null)) {
                // get applicationId and serviceId from Model
            	LOG.error("get applicationId and serviceId from Model ");
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO,
                        Long.valueOf(getModel().getApmApplicationId()).toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, getModel().getServiceId().toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGNAME,
                        Long.valueOf(getModel().getApmApplicationId()).toString());
            } else { // get applicationId and serviceId from Payment response
            	LOG.error("get applicationId and serviceId from Payment response ");
            	
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2).toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1).toString());
            }
            LOG.error("Success Msg: "+getApplicationSession().getMessage("common.payment.applNo"));
            if(StringUtils.equals(MainetConstants.Property.PROP_DEPT_SHORT_CODE, getModel().getPaymentRequestDTO().getAddField6())) {
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
                        getApplicationSession().getMessage("common.payment.propNo"));
            }else if(StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.WATER, getModel().getPaymentRequestDTO().getAddField6())){
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
            			getApplicationSession().getMessage("common.payment.ConnNo"));
            }else {
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
            			getApplicationSession().getMessage("common.payment.applNo"));
            }
            if ((responseMap.get(MainetConstants.BankParam.EMAIL) == null) && (getModel().getEmailId() != null)) {
                responseMap.put(MainetConstants.BankParam.EMAIL, getModel().getEmailId());
            }
            if ((responseMap.get(MainetConstants.BankParam.PHONE) == null) && (getModel().getMobNo() != null)) {
                responseMap.put(MainetConstants.BankParam.PHONE, getModel().getMobNo());
            }
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGID, getModel().getOrgId().toString());
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGNAME, getModel().getOrgName());
            responseMap.put(MainetConstants.BankParam.PROD_INFO, getModel().getServiceName());

            if (MainetConstants.PAYU_STATUS.SUCCESS
                    .equalsIgnoreCase(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS))) {
                final Long payId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE,
                        MainetConstants.PAY_PREFIX.PREFIX_VALUE, UserSession.getCurrent().getOrganisation()).getLookUpId();
                LOG.error("Transaction is successfull with payId: "+payId);
                //override Transaction Status "success" to "Success" as per Ambar Sir
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
                getModel().getOfflineDTO().setPayModeIn(payId);
                getModel().getOfflineDTO().setPaymentStatus(MainetConstants.PAYU_STATUS.SUCCESS);
                //D#81402 set orderId(TxnId ->pg_refid)
                getModel().getOfflineDTO().setPgRefId(getModel().getPaymentRequestDTO().getTxnid());
                LOG.error("Updating Receipt Entry for this payment: "+payId);
                getModel().updateReiceptData(getModel().getOfflineDTO());
                LOG.error("Reciept Entry updated successfully: "+payId);

            } else {
            	LOG.error("Payment is failed for this Transaction ");
                getModel().getOfflineDTO().setPaymentStatus(MainetConstants.PAYU_STATUS.FAIL);
                LOG.error("Updating failed receipt for this Transaction");
                final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(getModel().getOfflineDTO(),
                        ServiceEndpoints.PAYMENT_URL.PAYMENT_RECEIPT_FAILURE);
                LOG.error("Failed receipt updated for this Transaction");
                if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    Log.error("Exception occured while updating payment failure details");
                }
            }
            if ((MainetConstants.PAYU_STATUS.FAIL
                    .equalsIgnoreCase(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS)))
                    && (viewName.equals("successPayU"))) {
            	LOG.error("Redirecting to failed payment Page: failPayU");
                viewName = "failPayU";
            }
            LOG.error("Going to save Response object ");
            getModel().processPayUTransactionAfterPayment(httpServletRequest, responseMap);
            LOG.error("Response saved successfully. Sending sms and email ");
            //getModel().sendSMSandEMail(responseMap);
            LOG.error("Redirecting to view : "+viewName);
            if(getModel().getPaymentRequestDTO()!=null)
            	responseMap.put("FLAT_NO", getModel().getPaymentRequestDTO().getFlatNo());
            return new ModelAndView(viewName, MainetConstants.REQUIRED_PG_PARAM.COMMAND, responseMap);

        } catch (final RuntimeException e) {

            Log.error("Some Exception occured in processing payment response: " + this.getClass() + " :", e);

            if (!responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION)) {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, getApplicationSession().getMessage("common.payment.tech.issue"));
            }
            LOG.error("Redirecting to view : payExceptionView");
            return paymentExceptionView(responseMap);
        } finally {
            sessionCleanup(httpServletRequest);
        }
    }

    @RequestMapping(params = "cancelTransaction", method = { RequestMethod.POST })

    public ModelAndView cancelTransaction(final HttpServletRequest httpServletRequest) {
    	LOG.error("Transaction cancelled for this paymnet");
        bindModel(httpServletRequest);
        PaymentRequestDTO payURequestDTO = this.getModel().getPaymentRequestDTO();
        boolean cancelFlag = this.getModel().cancelTransactionBeforePayment(httpServletRequest, payURequestDTO);
        String respMsg = "";
        if (cancelFlag) {
            respMsg = getApplicationSession().getMessage("common.payment.cancel");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.REQUIRED_PG_PARAM.ERR_MSG, respMsg);
    }
    
    @RequestMapping(params = "callStatusAPI", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView callStatusAPI(@ModelAttribute("command") final PaymentRequestDTO paymentRequestDTO,
    		 final HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = null;
        bindModel(httpServletRequest);
        final PaymentRequestDTO payRequestDTO = getModel().getPaymentRequestDTO();
        PaymentTransactionMasDTO paymentTransactionMas=getModel().getPaymentTransactionMas();
        payRequestDTO.setPgName(paymentTransactionMas.getPgSource());
        payRequestDTO.setKey(paymentTransactionMas.getSendKey());
        payRequestDTO.setTxnid(paymentTransactionMas.getTranCmId());
        final Long payId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE,
                MainetConstants.PAY_PREFIX.PREFIX_VALUE).getLookUpId();
        payRequestDTO.setRecieptDTO(getModel().getOfflineDTO());
        payRequestDTO.getRecieptDTO().setPayModeIn(payId);
        payRequestDTO.getRecieptDTO().setPgRefId(paymentRequestDTO.getTxnid());
        payRequestDTO.setEmail(paymentRequestDTO.getEmail());
        payRequestDTO.setMobNo(paymentRequestDTO.getMobNo());
        getModel().setPaymentRequestDTO(paymentRequestDTO);
        getModel().setOfflineDTO(paymentRequestDTO.getRecieptDTO());
        
        try {
            
            LOG.error("Logged before calling processPayUTransactionBeforePayment for Application Id: "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
            Map<String, String> responseMap = getModel().processTransactionForCallStatus(httpServletRequest, payRequestDTO);
            
            	LOG.error("Logged after calling processPayUTransactionBeforePayment for Application Id: "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
               
						modelAndView = new ModelAndView("pendingPayU", MainetConstants.REQUIRED_PG_PARAM.COMMAND,
								responseMap);
            }
         catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            final Map<String, String> responseMap = new HashMap<>(0);

            if (paymentRequestDTO.getErrorCause() == null) {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, getApplicationSession().getMessage("common.payment.tech.issue"));
            } else {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, payRequestDTO.getErrorCause());
            }
            LOG.error("redirecting to : payExceptionView");
            return paymentExceptionView(responseMap);

        }
        LOG.error("redirecting to :"+payRequestDTO.getPgName()+ " for Application Id "+payRequestDTO.getApplicationId()+" and orderId: "+payRequestDTO.getTxnid());
        return modelAndView; 
    }
    
    private ModelAndView sudaProcessPayResponse(String viewName, final String gatewayFlag,
            final HttpServletRequest httpServletRequest) {
        Map<String, String> responseMap = new HashMap<>(0);
        LOG.error("Response Received from :"+gatewayFlag);
        Long applicationId=null;
        Long serviceId=null;
        try {
        	LOG.error("before calling capturePayUResponseInMapFromRequest");
        	
            responseMap = getModel().capturePayUResponseInMapFromRequest(httpServletRequest, gatewayFlag,
                    getModel().getOrgId(), getModel().getLangId());
            LOG.error("after calling capturePayUResponseInMapFromRequest");

            if (responseMap == null) {
                responseMap = new HashMap<>(0);
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, "No response received from service!");
                LOG.error("No response received from service: ");
                throw new FrameworkException("No response received from service!");
            }

            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.OWNER_NAME, getModel().getOwnerName());
            if ((getModel().getApmApplicationId() != 0) && (getModel().getServiceId() != null)) {
                // get applicationId and serviceId from Model
            	LOG.error("get applicationId and serviceId from Model ");
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO,
                        Long.valueOf(getModel().getApmApplicationId()).toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, getModel().getServiceId().toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGNAME,
                        Long.valueOf(getModel().getApmApplicationId()).toString());
                applicationId=getModel().getApmApplicationId();
                serviceId=getModel().getServiceId();
            } else { // get applicationId and serviceId from Payment response
            	LOG.error("get applicationId and serviceId from Payment response ");
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2).toString());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID,
                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1).toString());
                if(StringUtils.isNumeric(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2)))
                	applicationId=Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2));
                if(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1)!=null)
                serviceId=Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));
            }
            LOG.error("Success Msg: "+getApplicationSession().getMessage("common.payment.applNo"));
            if(StringUtils.equals(MainetConstants.Property.PROP_DEPT_SHORT_CODE, getModel().getPaymentRequestDTO().getAddField6())) {
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
                        getApplicationSession().getMessage("common.payment.propNo"));
            }else if(StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.WATER, getModel().getPaymentRequestDTO().getAddField6())){
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
            			getApplicationSession().getMessage("common.payment.ConnNo"));
            }else {
            	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.APP_ID_LABEL,
            			getApplicationSession().getMessage("common.payment.applNo"));
            }
            if ((responseMap.get(MainetConstants.BankParam.EMAIL) == null) && (getModel().getEmailId() != null)) {
                responseMap.put(MainetConstants.BankParam.EMAIL, getModel().getEmailId());
            }
            if ((responseMap.get(MainetConstants.BankParam.PHONE) == null) && (getModel().getMobNo() != null)) {
                responseMap.put(MainetConstants.BankParam.PHONE, getModel().getMobNo());
            }
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGID, getModel().getOrgId().toString());
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGNAME, getModel().getOrgName());
            responseMap.put(MainetConstants.BankParam.PROD_INFO, getModel().getServiceName());

            if (MainetConstants.PAYU_STATUS.SUCCESS
                    .equalsIgnoreCase(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS))) {
                final Long payId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE,
                        MainetConstants.PAY_PREFIX.PREFIX_VALUE, UserSession.getCurrent().getOrganisation()).getLookUpId();
                LOG.error("Transaction is successfull with payId: "+payId);
                //override Transaction Status "success" to "Success" as per Ambar Sir
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
                getModel().getOfflineDTO().setPayModeIn(payId);
                getModel().getOfflineDTO().setPaymentStatus(MainetConstants.PAYU_STATUS.SUCCESS);
                //D#81402 set orderId(TxnId ->pg_refid)
                getModel().getOfflineDTO().setPgRefId(getModel().getPaymentRequestDTO().getTxnid());
                LOG.error("Updating Receipt Entry for this payment: "+payId);
                getModel().updateReiceptData(getModel().getOfflineDTO());
                LOG.error("Reciept Entry updated successfully: "+payId);

            } else {
            	LOG.error("Payment is failed for this Transaction ");
                getModel().getOfflineDTO().setPaymentStatus(MainetConstants.PAYU_STATUS.FAIL);
                LOG.error("Updating failed receipt for this Transaction");
                final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(getModel().getOfflineDTO(),
                        ServiceEndpoints.PAYMENT_URL.PAYMENT_RECEIPT_FAILURE);
                LOG.error("Failed receipt updated for this Transaction");
                if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    Log.error("Exception occured while updating payment failure details");
                }
            }
            if ((MainetConstants.PAYU_STATUS.FAIL
                    .equalsIgnoreCase(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS)))
                    && (viewName.equals("successPayU"))) {
            	LOG.error("Redirecting to failed payment Page: failPayU");
                viewName = "failPayU";
            }
            LOG.error("Going to save Response object ");
            getModel().processPayUTransactionAfterPayment(httpServletRequest, responseMap);
            LOG.error("Response saved successfully. Sending sms and email ");
            //getModel().sendSMSandEMail(responseMap);
            LOG.error("Redirecting to view : "+viewName);
            if(getModel().getPaymentRequestDTO()!=null)
            	responseMap.put("FLAT_NO", getModel().getPaymentRequestDTO().getFlatNo());
            try {
            Long servId=	serviceMaster.getServiceId(MainetConstants.NewWaterServiceConstants.WNC, getModel().getOrgId());
				if (MainetConstants.PAYU_STATUS.SUCCESS
	                    .equalsIgnoreCase(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS))&&(servId != null && servId.equals(serviceId))) {
					ProvisionalCertificateDTO dto = paymentService.getProvisinalCertData(applicationId,
							getModel().getOrgId());
					getModel().setProvisional(dto);
					getModel().getProvisional().setPaymentMode(MainetConstants.AdvertisingAndHoarding.ONLINE);
					getModel().getProvisional().setPaymentDate(new Date());
					getModel().getProvisional().setProvPaymentDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date()));
					getModel().getProvisional().setApplicationFee(Double.valueOf(getModel().getChargesAmount()));
					return new ModelAndView("NewWaterConnProvisionalCertificate",
							MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
				}
            }
            catch (Exception e) {
            	LOG.error("Error at the time of fetching service id for New water Connection "+e);
			}
            return new ModelAndView(viewName, MainetConstants.REQUIRED_PG_PARAM.COMMAND, responseMap);

        } catch (final RuntimeException e) {

            LOG.error("Some Exception occured in processing payment response: " + this.getClass() + " :", e);

            if (!responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION)) {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION, getApplicationSession().getMessage("common.payment.tech.issue"));
            }
            LOG.error("Redirecting to view : payExceptionView");
            return paymentExceptionView(responseMap);
        } finally {
            sessionCleanup(httpServletRequest);
        }
    }
    
    /*private ProvisionalCertificateDTO getProvisionalCertificateData(Long applicationNo, 
			ProvisionalCertificateDTO provisionCertificateDTO) {
    	LOG.error("Begin getProvisionalCertificateData--> " + this.getClass().getSimpleName());
		try {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			
			applicationNo = getModel().getProvisional().getApplicationNo();
			if(applicationNo!=null) {
				if ((getModel().getApmApplicationId() != 0) && (getModel().getServiceId() != null)) {
				model.setProvisionCertificateDTO(provisional);
			return	provision = new ModelAndView("NewWaterConnProvisionalCertificate", MainetConstants.FORM_NAME,
						getModel());
			}
			}
			else{
				
			return	provision = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
		}
		provision.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return provision;
	}*/

}
