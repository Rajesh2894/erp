package com.abm.mainet.common.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

public abstract class AbstractFormController<TModel extends AbstractFormModel>
        extends AbstractController<TModel> {

    @Autowired
    private IPortalServiceMasterService iPortalService;
    
    @Autowired
    private IEntitlementService iEntitlementService;


    protected final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final TModel model = getModel();
        try {
            if (model.saveForm()) {
                if ((model.getSuccessMessage() != null)
                        && !model.getSuccessMessage().isEmpty()) {
                    return jsonResult(JsonViewObject.successResult(model
                            .getSuccessMessage()));
                } else {
                    if (MainetConstants.PAY_GATEWAY_AVAIL
                            .equalsIgnoreCase(ApplicationSession
                                    .getInstance()
                                    .getMessage("ulb.payment_gateway.available"))) {
                        return jsonResult(JsonViewObject
                                .successResult(getApplicationSession()
                                        .getMessage("continue.forpayment")));
                    } else {
                        return jsonResult(JsonViewObject
                                .successResult(getApplicationSession()
                                        .getMessage("continue.forchallan")));
                    }
                }
            }
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return (model.getCustomViewName() == null)
                || model.getCustomViewName().isEmpty() ? defaultMyResult()
                        : customDefaultMyResult(model.getCustomViewName());
    }

    @RequestMapping(params = "redirectToPay")
    public String redirectToPayDetails(final HttpServletRequest httpServletRequest,
            final RedirectAttributes redirectAttributes) {
        bindModel(httpServletRequest);
        final PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        getModel().redirectToPayDetails(httpServletRequest,
                paymentRequestDTO);
        try {

            paymentRequestDTO.setFinalAmount(EncryptionAndDecryption
                    .encrypt(paymentRequestDTO.getDueAmt().toString()));
            getModel().setChargesAmount(
                    paymentRequestDTO.getDueAmt().toString());
            getModel().processPayUTransactionOnApplicationSubmit(
                    httpServletRequest, paymentRequestDTO);
            paymentRequestDTO.setOnlineBankList(UserSession.getCurrent()
                    .getOnlineBankList());
            paymentRequestDTO.setRecieptDTO(getModel().getOfflineDTO());
            paymentRequestDTO.setControlUrl(httpServletRequest.getRequestURL()
                    .toString());
            paymentRequestDTO.setChargeFlag(getApplicationSession().getMessage(
                    "showPaymentCharges"));

            redirectAttributes.addFlashAttribute("payRequestDTO",
                    paymentRequestDTO);
            return "redirect:PaymentController.html?redirectDTO";

        }

        catch (final Exception e) {

            throw new FrameworkException(
                    "Some problem facing for fatching Bank Parameters", e);
        }

    }

    public ApplicationPortalMaster calculateDate(final PortalService portalService,
            final ApplicationPortalMaster applicationMaster, final int documentListSize)
            throws Exception {
        final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (documentListSize > 0) {
            applicationMaster
                    .setPamChecklistApp(MainetConstants.Common_Constant.YES); // Y
        } else {
            applicationMaster
                    .setPamChecklistApp(MainetConstants.Common_Constant.NO); // N

            calendar.add(Calendar.DATE, 0);
            final String output1 = sdf.format(calendar.getTime());
            applicationMaster.setPamDocVerificationDate(sdf.parse(output1));

            calendar.setTime(sdf.parse(output1));          
            if(portalService.getSlaDays()!=null) {
            calendar.add(Calendar.DATE,
                    Integer.parseInt(portalService.getSlaDays().toString()));
            final String output2 =sdf.format(calendar.getTime());
            applicationMaster.setPamSlaDate(sdf.parse(output2));
            calendar.setTime(sdf.parse(output2));
            }       
            
            if(portalService.getFirstAppealDuration()!=null) {
            calendar.add(Calendar.DATE, Integer.parseInt(portalService
                    .getFirstAppealDuration().toString()));
            final String output3 = sdf.format(calendar.getTime());
            applicationMaster.setPamFirstAppealDate(sdf.parse(output3));
            calendar.setTime(sdf.parse(output3));
            }
            
            if(portalService.getSecondAppealDuration()!=null) {
            calendar.add(Calendar.DATE, Integer.parseInt(portalService
                    .getSecondAppealDuration().toString()));
            final String output4 = sdf.format(calendar.getTime());
            applicationMaster.setPamSecondAppealDate(sdf.parse(output4));
            }
            
        }

        return applicationMaster;

    }

    /**
     * Returns an Service Id.
     * 
     * @param shortCode an short name for the service
     * @return Service Id
     */
    public Long getServiceId(final String shortCode) {

        Long serviceId = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (orgId != null) {
            serviceId = iPortalService.getServiceId(shortCode, orgId);
        }
        return serviceId;
    }

    /*
     * @RequestMapping(params = "getBankList", method = RequestMethod.POST) public @ResponseBody List<LookUp>
     * getBankList(@RequestParam(value = "pgbankId") final String bankId, final HttpServletRequest httpServletRequest) { return
     * getModel().getBankList(bankId); }
     */

    @RequestMapping(params = "PrintReport")
    public ModelAndView printChallanReceipt(final HttpServletRequest request) {
        try {
            bindModel(request);
            final TModel model = getModel();
            final CommonChallanDTO challanDTO = model.getOfflineDTO();

            IChallanService iChallanService = ApplicationContextProvider.getApplicationContext()
                    .getBean(IChallanService.class);
            final ApplicationFormChallanDTO challanDetails = iChallanService
                    .getChallanData(challanDTO, UserSession.getCurrent()
                            .getOrganisation());
            model.setChallanDTO(challanDetails);
            Long offlinePaymentMode = 0L;
            if (model.getOfflineDTO().getOflPaymentMode() != 0) {
                offlinePaymentMode = model.getOfflineDTO().getOflPaymentMode();
            }
            final LookUp paymentModeLookup = model
                    .getNonHierarchicalLookUpObject(offlinePaymentMode);
            final String offlinePayMode = paymentModeLookup.getLookUpCode();

            if (offlinePayMode.equalsIgnoreCase("PCU")) {
                return new ModelAndView("CommonChallanULBReport",
                        MainetConstants.FORM_NAME, model);
            } else if (offlinePayMode.equalsIgnoreCase("PCB")) {
                return new ModelAndView("CommonChallanBankReport",
                        MainetConstants.FORM_NAME, model);
            }
        } catch (final Exception e) {
            logger.error(
                    "Exception while printing challan in offfline pay mode:", e);
        }
        return null;
    }

    /**
     * common method to display service charge details
     * 
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
    public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {

        return new ModelAndView("ChargesDetail", MainetConstants.FORM_NAME, getModel());
    }

    protected String getRequestParam(final String key, final HttpServletRequest request, final ModelMap model) {
        String value = request.getParameter(key) != null ? request.getParameter(key) : MainetConstants.BLANK;
        if (value.isEmpty()) {
            value = (String) (request.getSession().getAttribute(key) != null ? request.getSession().getAttribute(key)
                    : MainetConstants.BLANK);
            value = (String) (value == null ? request.getAttribute(key) : MainetConstants.BLANK);
        }
        if (value.isEmpty() && (model != null)) {
            value = (String) model.get(key) != null ? (String) model.get(key) : MainetConstants.BLANK;
        }
        return value;
    }
    
    public boolean getMakerCheckerFlag() {
    	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            return true;
        } else {
        	return false;
        }
    	
    }
    
    
    @RequestMapping(method = RequestMethod.POST, params = "cleareFile")
    public @ResponseBody String cleareFile(
            final HttpServletRequest httpServletRequest) {
        int length = 0;
        final String folderPath = FileUploadUtility.getCurrent()
                .getExistingFolderPath();
        FileUploadUtility.getCurrent().getFileMap().clear();
        if (folderPath != null) {
            final File file = new File(folderPath);
            if (file != null) {
                length = file.list() != null ? file.list().length
                        : 0;
            }
            FileUploadUtility.getCurrent()
                    .setFolderCreated(false);
        }
        return length + MainetConstants.BLANK;
    }

}
