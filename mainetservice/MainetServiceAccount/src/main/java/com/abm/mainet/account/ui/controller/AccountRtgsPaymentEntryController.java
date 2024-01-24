package com.abm.mainet.account.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.account.ui.model.RTGSPaymentEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/RtgsPaymentEntry.html")
public class AccountRtgsPaymentEntryController extends AbstractFormController<RTGSPaymentEntryModel> {

    @Resource
    private TbAcVendormasterService vendorMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        populateVendorList();
        return index();
    }

    private void populateVendorList() {
        // TODO Auto-generated method stub
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(org.getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);
    }

    @ResponseBody
    @RequestMapping(params = "formForCreate", method = RequestMethod.POST)
    public ModelAndView viewAbstractreportSheet(@RequestParam("frmDate") String frmDate) {
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        return new ModelAndView("AccountRtgsPaymentEntry/Form", MainetConstants.FORM_NAME, this.getModel());

    }

}
