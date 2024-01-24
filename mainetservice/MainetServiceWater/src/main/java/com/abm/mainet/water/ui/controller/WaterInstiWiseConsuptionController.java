package com.abm.mainet.water.ui.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.TbWtInstCsmp;
import com.abm.mainet.water.service.InstWiseConsmpService;
import com.abm.mainet.water.ui.model.TbWtInstCsmpResponse;
import com.abm.mainet.water.ui.validator.InstitueWiseConsumptionValidator;

@Controller
@RequestMapping("WaterInstiWiseConsuption.html")
public class WaterInstiWiseConsuptionController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbWtInstCsmp";
    private static final String MAIN_LIST_NAME = MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME;

    private static final String JSP_FORM = "tbWtInstCsmp/form";
    private static final String JSP_LIST = "tbWtInstCsmp/list";

    private static final String SAVE_ACTION_CREATE = "/tbWtInstCsmp/create";
    private static final String SAVE_ACTION_UPDATE = "/tbWtInstCsmp/update";

    @Resource
    private InstWiseConsmpService instWiseConsumptionService;

    public WaterInstiWiseConsuptionController() {
        super(WaterInstiWiseConsuptionController.class, MAIN_ENTITY_NAME);
        log("TbWtInstCsmpController created.");
    }

    private void populateModel(final Model model, final TbWtInstCsmp tbWtInstCsmp, final FormMode formMode) {

        model.addAttribute(MAIN_ENTITY_NAME, tbWtInstCsmp);
        if (formMode == FormMode.CREATE) {
            final List<LookUp> listOfInstType = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.ITP,
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.WATERMASTERS.INSTITUTEWISECONSUMPTIONMASTER.LIST_OF_INST_TYPE, listOfInstType);
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode == FormMode.VIEW) {
            model.addAttribute(MODE, MODE_VIEW);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            tbWtInstCsmp.setInstFrmDtStringFormat(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS).format(tbWtInstCsmp.getInstFrmdt()));
            tbWtInstCsmp.setInstToDtStringFormat(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS).format(tbWtInstCsmp.getInstTodt()));
        } else {
            if ((tbWtInstCsmp.getInstFrmdt() != null) || (tbWtInstCsmp.getInstTodt() != null)) {
                tbWtInstCsmp.setInstFrmDtStringFormat(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS).format(tbWtInstCsmp.getInstFrmdt()));
                tbWtInstCsmp.setInstToDtStringFormat(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS).format(tbWtInstCsmp.getInstTodt()));
            }
            model.addAttribute(MODE, MODE_EDIT);
        }
    }

    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        final List<TbWtInstCsmp> list = instWiseConsumptionService.findAll();
        model.addAttribute(MAIN_LIST_NAME, list);
        return JSP_FORM;
    }

    @RequestMapping("/form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");

        final TbWtInstCsmp tbWtInstCsmp = new TbWtInstCsmp();
        populateModel(model, tbWtInstCsmp, FormMode.CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("instId") final Long instId,
            @RequestParam("MODE1") final String viewmode) {
        log("Action 'formForUpdate'");
        TbWtInstCsmp tbWtInstCsmp = null;
        if (instId != null) {
            tbWtInstCsmp = instWiseConsumptionService.findById(instId);
        }
        if (null == tbWtInstCsmp) {
            tbWtInstCsmp = new TbWtInstCsmp();
        }
        if (MainetConstants.CommonConstants.VIEW.equals(viewmode)) {
            populateModel(model, tbWtInstCsmp, FormMode.VIEW);
        } else if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE.equals(viewmode)) {
            populateModel(model, tbWtInstCsmp, FormMode.CREATE);
        } else {
            populateModel(model, tbWtInstCsmp, FormMode.UPDATE);
        }
        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody TbWtInstCsmpResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final List<TbWtInstCsmp> list = instWiseConsumptionService.findAll();

        final TbWtInstCsmpResponse instiWiseConsputResponse = new TbWtInstCsmpResponse();
        instiWiseConsputResponse.setRows(list);
        instiWiseConsputResponse.setTotal(list.size());
        instiWiseConsputResponse.setRecords(list.size());
        instiWiseConsputResponse.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return instiWiseConsputResponse;
    }

    @RequestMapping(params = "create")
    public String create(@Valid final TbWtInstCsmp tbWtInstCsmp, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        final InstitueWiseConsumptionValidator validator = new InstitueWiseConsumptionValidator();
        validator.validate(tbWtInstCsmp, bindingResult);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        try {
            if (!bindingResult.hasErrors()) {
                final TbWtInstCsmp tbWtInstCsmpCreated = instWiseConsumptionService.create(tbWtInstCsmp, orgId, langId, empId);
                model.addAttribute(MAIN_ENTITY_NAME, tbWtInstCsmpCreated);
                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                populateModel(model, tbWtInstCsmp, FormMode.CREATE);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            return MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
    }

    @RequestMapping(params = "update")
    public String update(@Valid final TbWtInstCsmp tbWtInstCsmp, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'update'");

        final InstitueWiseConsumptionValidator validator = new InstitueWiseConsumptionValidator();
        validator.validate(tbWtInstCsmp, bindingResult);

        try {
            if (!bindingResult.hasErrors()) {
                final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                final int langId = UserSession.getCurrent().getLanguageId();
                final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
                tbWtInstCsmp.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                final TbWtInstCsmp tbWtInstCsmpSaved = instWiseConsumptionService.update(tbWtInstCsmp, orgId, langId, empId);
                model.addAttribute(MAIN_ENTITY_NAME, tbWtInstCsmpSaved);

                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
                log("Action 'update' : update done - redirect");
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                log("Action 'update' : binding errors");
                populateModel(model, tbWtInstCsmp, FormMode.UPDATE);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            log("Action 'update' : errors" + e);
            messageHelper.addException(model, "tbWtInstCsmp.error.update", e);
            return MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
    }
}