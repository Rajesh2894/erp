package com.abm.mainet.account.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.account.dto.AccountBillRegisterBean;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;

@Controller
@RequestMapping("/DailyReceipt.html")
public class AccountDailyReceiptcontroller extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "dailyReceiptBean";
    private static final String Daily_Receipt = "dailyReceipt";
    protected static final String MODE = "mode";
    protected static final String MODE_CREATE = "create";
    protected static final String MODE_UPDATE = "update";
    protected static final String MODE_EDIT = "edit";
    protected static final String MODE_VIEW = "view";
    protected static final String SAVE_ACTION = "saveAction";

    final AccountBillRegisterBean AccountBillRegisterBean = new AccountBillRegisterBean();

    public AccountDailyReceiptcontroller() {
        super(AccountDailyReceiptcontroller.class, Daily_Receipt);
        log("AccountDailyReceiptcontroller created.");
    }

    @RequestMapping()
    public String getList(final Model model) {
        log("Action 'list'");
        // for REST Webservice testing -Debuggin
        final AccountBillRegisterBean AccountBillRegisterBean = new AccountBillRegisterBean();
        populateModel(model, AccountBillRegisterBean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, AccountBillRegisterBean);
        return Daily_Receipt;
    }

    private void populateModel(final Model model, final AccountBillRegisterBean AccountBillRegisterBean,
            final FormMode formMode) {
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.CREATE);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.UPDATE);
        }
    }

}
