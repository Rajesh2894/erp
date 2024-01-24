package com.abm.mainet.account.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.service.AccountPermanentAdvancesRegisterService;
import com.abm.mainet.account.ui.model.PermanentAdvancesRegisterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/PermanentAdvancesRegister.html")
public class AccountPermanentAdvancesRegisterController extends AbstractFormController<PermanentAdvancesRegisterModel> {

    @Resource
    AccountPermanentAdvancesRegisterService accountPermanentAdvancesRegisterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "getPermanentAdvanceRegisterData", method = RequestMethod.POST)
    public ModelAndView viewAbstractreportSheet(@RequestParam("frmDate") String asOnDate) {
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        AdvanceEntryDTO advanceEntryDTO;
        advanceEntryDTO = accountPermanentAdvancesRegisterService.findPermanentAdvanceLadger(asOnDate, currentOrgId);
        getModel().setAdvanceEntryDTO(advanceEntryDTO);
        return new ModelAndView("PermanentRegisterOfAdvance/Report", MainetConstants.FORM_NAME,
                this.getModel());

    }
}
