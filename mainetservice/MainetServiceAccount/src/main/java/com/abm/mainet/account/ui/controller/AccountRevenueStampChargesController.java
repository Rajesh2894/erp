package com.abm.mainet.account.ui.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.RevenueStampChargeDTO;
import com.abm.mainet.account.service.AccountRevanueStampChargeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountRevenueStampCharges.html")
public class AccountRevenueStampChargesController extends AbstractController {

    @Resource
    AccountRevanueStampChargeService accountRevanueStampChargeService;

    private static final String MAIN_ENTITY_NAME = "revenueStampChargeDTO";
    private static final String INDEX = "AccountRevenueStampChargesForm/list";
    private static final String INDEX_FORM = "AccountRevenueStampChargesForm/form";
    private static final String REVENUE_STAMP_CHARGE_DTO = "revenueStampChargeDTO";

    public AccountRevenueStampChargesController() {
        super(AccountRevenueStampChargesController.class, MAIN_ENTITY_NAME);

    }

    public void populateModel(final ModelMap model, final RevenueStampChargeDTO revenueStampChargeDTO,
            final FormMode formMode) {

        model.addAttribute(MAIN_ENTITY_NAME, revenueStampChargeDTO);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);

        }
    }

    @RequestMapping()
    public String index(final ModelMap model) {
        RevenueStampChargeDTO revenueStampChargeDTO = new RevenueStampChargeDTO();
        populateModel(model, revenueStampChargeDTO, FormMode.CREATE);

        return INDEX;

    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String createRevanueStamp(@Valid final RevenueStampChargeDTO revenueStampChargeDTO,
            final BindingResult bindingResult,
            final ModelMap model, final HttpServletRequest httpServletRequest, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {

            final Organisation organisation = UserSession.getCurrent().getOrganisation();

            revenueStampChargeDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            revenueStampChargeDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            revenueStampChargeDTO.setCreatedDate(new Date());
            revenueStampChargeDTO.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            RevenueStampChargeDTO revenueStampChargeDTO1 = accountRevanueStampChargeService
                    .createRevenueEntry(revenueStampChargeDTO);
            model.addAttribute(MAIN_ENTITY_NAME, revenueStampChargeDTO1);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            return INDEX_FORM;
        } else {

            populateModel(model, revenueStampChargeDTO, FormMode.CREATE);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            model.addAttribute(MAIN_ENTITY_NAME, revenueStampChargeDTO);
            return INDEX_FORM;
        }

    }

}
