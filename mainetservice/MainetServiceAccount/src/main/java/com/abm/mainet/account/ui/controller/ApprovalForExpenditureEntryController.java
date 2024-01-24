package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.ApprovalForExpenditureEntryDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/ApprovalForExpenditureEntry.html")
public class ApprovalForExpenditureEntryController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ApprovalForExpenditureEntryController.class);

    private static final String MAIN_ENTITY_NAME = "tbAcApprovalForExpenditureEntry";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcApprovalForExpenditureEntry/form";
    private static final String JSP_LIST = "tbAcApprovalForExpenditureEntry/list";
    private String modeView = MainetConstants.BLANK;

    private List<ApprovalForExpenditureEntryDTO> chList = null;

    public ApprovalForExpenditureEntryController() {
        super(ApprovalForExpenditureEntryController.class, MAIN_ENTITY_NAME);
        log("ApprovalForExpenditureEntryController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("ApprovalForExpenditureEntryController-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping()
    public String getList(final Model model) {
        log("ApprovalForExpenditureEntryController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        try {
            chList = new ArrayList<>();
            chList.clear();
            if (chList != null) {
                for (final ApprovalForExpenditureEntryDTO bean : chList) {

                }
            }
            final ApprovalForExpenditureEntryDTO bean = new ApprovalForExpenditureEntryDTO();
            populateModel(model, bean, FormMode.CREATE);
            model.addAttribute(MAIN_ENTITY_NAME, bean);
            model.addAttribute(MAIN_LIST_NAME, chList);
            result = JSP_LIST;
        } catch (final Exception e) {
            LOGGER.error("In list method" + e);
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("ApprovalForExpenditureEntryController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        try {
            final ApprovalForExpenditureEntryDTO bean = new ApprovalForExpenditureEntryDTO();
            populateModel(model, bean, FormMode.CREATE);
            result = JSP_FORM;
        } catch (final Exception e) {
            LOGGER.error("In formForCreate method" + e);
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
        return result;
    }

    private void populateModel(final Model model, final ApprovalForExpenditureEntryDTO bean, final FormMode formMode)
            throws Exception {
        log("ApprovalForExpenditureEntryController-'populateModel' : populate model");
        final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.BUG_SUB_PREFIX,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUG_SUB_TYPE_LEVEL_MAP, bugSubTypelevelMap);

        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }

}
