package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountYearEndProcessDTO;
import com.abm.mainet.account.service.AccountYearEndProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountYearEndProcess.html")
public class AccountYearEndProcessController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcYearEndProcess";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_LIST = "tbAcYearEndProcess/list";
    private static final String JSP_FORM = "tbAcYearEndProcess/form";
    private String modeView = MainetConstants.BLANK;
    private List<AccountYearEndProcessDTO> chList = null;

    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private AccountYearEndProcessService accountYearEndProcessService;

    public AccountYearEndProcessController() {
        super(AccountYearEndProcessController.class, MAIN_ENTITY_NAME);
        log("AccountYearEndProcessController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountYearEndProcessController-'gridData' : 'Get grid Data'");
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
    public String getList(final Model model) throws Exception {
        log("AccountYearEndProcessController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final AccountYearEndProcessDTO dto = new AccountYearEndProcessDTO();
        populateModel(model, dto, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    private void populateModel(final Model model, final AccountYearEndProcessDTO dto, final FormMode formMode)
            throws Exception {
        log("AccountYearEndProcessController-'populateModel' : populate model");
        final Map<Long, String> financeMap = secondaryheadMasterService.getAllFinincialYear(
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId());
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FINANCE_MAP, financeMap);
        Map<Long, String> acHeadMap = secondaryheadMasterService.getSecondaryHeadcodesForIncomeAndExpenditure(orgId);
        model.addAttribute("acHeadMap", acHeadMap);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "getAccountYearEndProcessList", method = RequestMethod.POST)
    public String loadBudgetAllocationData(final AccountYearEndProcessDTO dto, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long faYearId = dto.getFaYearid();
        // Long sacHeadId = dto.getSacHeadId();
        AccountYearEndProcessDTO finalDTO = accountYearEndProcessService.processFaYearEndProcessReport(faYearId,
                orgId);
        request.getSession().setAttribute("finalDTO", finalDTO);
        if (finalDTO == null) {
            populateModel(model, new AccountYearEndProcessDTO(), FormMode.CREATE);
            model.addAttribute(MAIN_ENTITY_NAME, new AccountYearEndProcessDTO());
            result = JSP_FORM;
        } else {
            populateModel(model, dto, FormMode.CREATE);
            model.addAttribute(MAIN_ENTITY_NAME, finalDTO);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountYearEndProcessDTO dtos, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountBudgetCodeBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        AccountYearEndProcessDTO dto = (AccountYearEndProcessDTO) httpServletRequest.getSession().getAttribute("finalDTO");
        if (!bindingResult.hasErrors()) {
            dto.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            dto.setOrgid(userSession.getOrganisation().getOrgid());
            dto.setLangId(userSession.getLanguageId());
            dto.setUserId(userSession.getEmployee().getEmpId());
            dto.setUpdatedBy(userSession.getEmployee().getEmpId());
            dto.setUpdatedDate(new Date());
            dto.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
            dto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            dto.setLmoddate(new Date());
            populateModel(model, dto, FormMode.CREATE);
            AccountYearEndProcessDTO tbAcYearEndProcessDTO=new AccountYearEndProcessDTO();
			try {
			    tbAcYearEndProcessDTO = accountYearEndProcessService.updateYearEndProcessFormData(dto);
				//model.addAttribute(MAIN_ENTITY_NAME, tbAcYearEndProcessDTO);
			} catch (FrameworkException e) {
				bindingResult.addError(new ObjectError("error", "Next Finanacial Year is Not Exist."));
				tbAcYearEndProcessDTO.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
				model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
				model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
				result = JSP_FORM;
				return result;
			}
            
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));

            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            result = JSP_FORM;
        } else {

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, dto, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(String modeView) {
        this.modeView = modeView;
    }

}
