package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IReceivableDemandEntryService;
import com.abm.mainet.common.ui.model.MiscDemandCollectionModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author satish.kadu
 *
 */
@Controller
@RequestMapping("/MiscDemandCollection.html")
public class MiscDemandCollectionController extends AbstractFormController<MiscDemandCollectionModel> {

    protected final Logger logger = Logger.getLogger(MiscDemandCollectionController.class);

    @Autowired
    private IReceivableDemandEntryService receivableDemandEntryService;

    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Autowired
    private TbTaxMasService taxMasService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("MiscDemandCollection.html");
        return defaultResult();
    }

    /**
     * @param billNo
     * @param httpServletRequest
     * @return complete bill Details with outstanding Amount
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.SEARCH_CONNECTION_NUMBER)
    public ModelAndView searchMscDemandEntry(@RequestParam("billNo") final String billNo,
            @RequestParam("refNumber") String refNumber, final HttpServletRequest httpServletRequest) {
        logger.info("Searching Bill Number  :" + billNo);
        try {
            bindModel(httpServletRequest);
            searchBill(billNo, refNumber);
        } catch (Exception e) {
            logger.error("Exception while printing challan :", e);
            this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.SEARCH_BILL_NO_EXCEPTION) + billNo);
        }
        if (this.getModel().hasValidationErrors()) {
            return customResult(MainetConstants.ReceivableDemandEntry.MISC_JSP_FORM_SEARCH);
        } else {
            return new ModelAndView(MainetConstants.ReceivableDemandEntry.MISC_JSP_FORM_SEARCH, MainetConstants.FORM_NAME, this.getModel());
        }
    }

    private void searchBill(String billNo, String refNumber) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<ReceivableDemandEntryDTO> newReceivableDemandEntryDtoList = new ArrayList<>();
        int dueDates = Integer.parseInt(CommonMasterUtility.getDefaultValue(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL_DUE_DATE).getLookUpCode().toString());
        List<ReceivableDemandEntryDTO> receivableDemandEntryDtoList = receivableDemandEntryService.getBillInfoListByBillNoOrRefNo(billNo, refNumber, dueDates, orgId);
        Long getCustomerInfo = 1L;

        if (receivableDemandEntryDtoList == null || receivableDemandEntryDtoList.isEmpty()) {
            this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_NUMBER_NOT_FOUND) + billNo + ":" + refNumber);
        } else {
            if (receivableDemandEntryDtoList.size() == 1 && !billNo.isEmpty() && receivableDemandEntryDtoList.get(0).getApplicationId()!=null) {                
                this.getModel().setBillAgainstCCN(false);
            } else { 
                if(receivableDemandEntryDtoList.get(0).getApplicationId()!=null) {
                this.getModel().setBillAgainstCCN(false);
            } else {
                this.getModel().setBillAgainstCCN(true);
            }
            }
            for (ReceivableDemandEntryDTO receivableDemandEntryDto : receivableDemandEntryDtoList) {
                if (getCustomerInfo.equals(1L)) {
                    this.getModel().setReceivableDemandEntryDto(receivableDemandEntryDto);
                    getCustomerInfo++;
                }
                if (receivableDemandEntryDto.getReceiptId() != null) {
                    this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_PAID) + billNo);
                } else {
                    Calendar c = Calendar.getInstance();
                    c.setTime(receivableDemandEntryDto.getCreatedDate());
                    c.add(Calendar.DAY_OF_MONTH, dueDates);
                    if (!(new Date().before(receivableDemandEntryDto.getCreatedDate()) || new Date().after(c.getTime()))) {
                        receivableDemandEntryDto.getRcvblDemandList().forEach(tr -> {
                            Long taxId = tr.getTaxId();
                            Long trOrgId = tr.getOrgid();
                            String taxDesc = taxMasService.findTaxByTaxIdAndOrgId(taxId, trOrgId).getTaxDesc();
                            tr.setTaxName(taxDesc);

                            if (tr.getSacHeadId() != null) {   // NOTE: tr.getSacHeadId() will get only inCase Of SLI is Live
                                String accCode = secondaryheadMasterService.findByAccountHead(tr.getSacHeadId());
                                if (StringUtils.isNotEmpty(accCode)) {
                                    tr.setAccHead(accCode);
                                }
                            }
                        });
                        LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
                        if (defaultVal != null && MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE.equals(defaultVal.getLookUpCode())) {
                            receivableDemandEntryDto.setSliStatus(MainetConstants.Common_Constant.ACTIVE);
                        } else {
                            receivableDemandEntryDto.setSliStatus(MainetConstants.Common_Constant.INACTIVE);
                        }
                    } else {
                        this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_DUE_DATE_OVER) + billNo);
                    }
                }
                newReceivableDemandEntryDtoList.add(receivableDemandEntryDto);
            }
            this.getModel().setReceivableDemandEntryDtosList(newReceivableDemandEntryDtoList);
        }
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView supplementryBillPayDashBoard(@RequestParam("appNo") final Long appNo,
            @RequestParam("taskId") final String serviceId, @RequestParam("refNo") final String billNo,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId, final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        this.getModel().setProcessUpdate(MainetConstants.FlagY);
        try {
            this.getModel().setServiceId(Long.valueOf(serviceId));
            this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
            this.getModel().getWorkflowActionDto().setReferenceId(billNo);
            this.getModel().getWorkflowActionDto().setApplicationId(appNo);
            searchBill(billNo, null);
        } catch (Exception e) {
            logger.error("Exception while printing challan :", e);
            this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.SEARCH_BILL_NO_EXCEPTION) + billNo);
        }
        if (this.getModel().hasValidationErrors()) {
            return customResult(MainetConstants.ReceivableDemandEntry.MISC_JSP_FORM_SEARCH);
        } else {
            return new ModelAndView(MainetConstants.ReceivableDemandEntry.MISC_JSP_FORM_SEARCH, MainetConstants.FORM_NAME, this.getModel());
        }
    }

    @RequestMapping(params = "PrintSupplementryBill", method = RequestMethod.POST)
    public ModelAndView printSupplementryBill(final HttpServletRequest request) {
        bindModel(request);
        String path = null;
        if (this.getModel().isBillAgainstCCN() == true) {
            path = receivableDemandEntryService.printSupplementryBill(this.getModel().getReceiptDTO().getReceiptId(), MainetConstants.ReceivableDemandEntry.CCN);
        } else {
            path = receivableDemandEntryService.printSupplementryBill(this.getModel().getReceiptDTO().getReceiptId(), MainetConstants.ReceivableDemandEntry.IDN);
        }
        this.getModel().setFilePath(path);
        return new ModelAndView("MiscDemandPrint", MainetConstants.FORM_NAME, this.getModel());
    }

}