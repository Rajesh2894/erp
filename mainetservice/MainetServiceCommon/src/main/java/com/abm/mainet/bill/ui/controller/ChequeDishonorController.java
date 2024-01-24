package com.abm.mainet.bill.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.ChequeDishonorService;
import com.abm.mainet.bill.ui.model.ChequeDishonorModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/ChequeDishonor.html")
public class ChequeDishonorController extends AbstractFormController<ChequeDishonorModel> {

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private ChequeDishonorService chequeDishonorService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setCommonHelpDocs("ChequeDishonor.html");
        final long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        String activeFlag = null;
        final LookUp aaountActive = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                UserSession.getCurrent().getOrganisation());
        if (aaountActive != null) {
            activeFlag = aaountActive.getDefaultVal();
            getModel().setAccountActive(activeFlag);
        }
        if (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) {
            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.MASTER.U,
                    PrefixConstants.CHEQUE_DISHONOUR.BANK_AC_STATUS_PREFIX, orgid);
            getModel().setBanks(chequeDishonorService.getULBDepositBanks(orgid, statusId));
        }
        getModel().setDepartment(tbDepartmentService.findAllActive(orgid));
        getModel().setFromDate(new Date());
        getModel().setToDate(new Date());
        getModel().setCommonHelpDocs("ChequeDishonor.html");
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachChequeData")
    public ModelAndView searchChequeData(final HttpServletRequest request, @RequestParam("deptId") final Long deptId,
            @RequestParam("fromDate") final Date fromDate, @RequestParam("toDate") final Date toDate,
            @RequestParam("receiptNo") final Long receiptNo, @RequestParam("chequeNo") final Long chequeNo,
            @RequestParam("bankId") final Long bankId) {
        bindModel(request);
        List<TbServiceReceiptMasBean> chequePayData = null;
        final ChequeDishonorModel model = getModel();
        model.setFeeDetail(new ArrayList<TbServiceReceiptMasBean>(0));
        model.setDeptId(deptId);
        model.setFromDate(fromDate);
        model.setToDate(toDate);
        model.setReceiptNo(receiptNo);
        model.setChequeNo(chequeNo);
        model.setBankId(bankId);
        final boolean valid = model.validateSearchData(model);
        if (valid) {
            final Organisation org = UserSession.getCurrent().getOrganisation();
            final List<Long> chequeId = new ArrayList<>(0);
            final List<LookUp> payLookup = model.getLevelData(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, org);
            if ((payLookup != null) && !payLookup.isEmpty()) {
                for (final LookUp payPrefix : payLookup) {
                    if (payPrefix.getLookUpCode().equals(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q)
                            || payPrefix.getLookUpCode().equals(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_P)
                            || payPrefix.getLookUpCode().equals(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_D)) {
                        chequeId.add(payPrefix.getLookUpId());
                    }
                }
            }

            chequePayData = chequeDishonorService.fetchChequePaymentData(chequeId, org.getOrgid(), deptId, fromDate,
                    toDate, receiptNo, chequeNo, bankId);
            if ((chequePayData == null) || chequePayData.isEmpty()) {
                model.addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
            }else {
            	//Defect #119702
            	chequePayData.forEach(tr->{
            		tr.getReceiptModeList().forEach(mode->{
            			if(chequeNo!=null && chequeNo.equals(mode.getRdChequeddno()) && MainetConstants.FlagY.equals(mode.getRdSrChkDis())) {
            				model.addValidationError(ApplicationSession.getInstance().getMessage("prop.no.dues.cheque.dishonor"));
            			}else if( chequeNo!=null &&  chequeNo.equals(mode.getRdChequeddno()) && MainetConstants.FlagN.equals(mode.getRdSrChkDis())) {
            				model.addValidationError(ApplicationSession.getInstance().getMessage("prop.no.dues.cheque.clear"));
            			}
            		});
            	});
            }
            if(!model.hasValidationErrors())
            getModel().setFeeDetail(chequePayData);
        }
        return defaultMyResult();
    }

}
