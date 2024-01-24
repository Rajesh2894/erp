package com.abm.mainet.water.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.TbCcnsizePrm;
import com.abm.mainet.water.service.ConnectionSizePrmService;
import com.abm.mainet.water.ui.model.TbWaterConnSizeResponse;
import com.abm.mainet.water.ui.validator.DiameterWiseConnSizeValidator;

@Controller
@RequestMapping("/TBCcnsizePrm.html")
public class WaterConnectionSizeController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbCcnsizePrm";
    private static final String MAIN_LIST_NAME = MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME;

    private static final String JSP_FORM = "tbCcnsizePrm/form";
    private static final String JSP_LIST = "tbCcnsizePrm/list";

    @Resource
    private ConnectionSizePrmService connectionSizePrmService; // Injected by Spring

    private final Map<Locale, CustomDateEditor> customDateEditorByLocales = new HashMap<>();

    public WaterConnectionSizeController() {
        super(WaterConnectionSizeController.class, MAIN_ENTITY_NAME);
        log("TbCcnsizePrmController created.");
    }

    private void populateModel(final Model model, TbCcnsizePrm tbCcnsizePrm, final FormMode formMode) {

        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.EDIT) {
            tbCcnsizePrm = connectionSizePrmService.findById(tbCcnsizePrm.getCnsId());
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.VIEW) {
            tbCcnsizePrm = connectionSizePrmService.findById(tbCcnsizePrm.getCnsId());
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            model.addAttribute(MODE, MODE_VIEW);
        }
        model.addAttribute(MAIN_ENTITY_NAME, tbCcnsizePrm);
    }

    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        final List<TbCcnsizePrm> list = connectionSizePrmService.findAll();
        model.addAttribute(MAIN_LIST_NAME, list);
        return JSP_FORM;
    }

    @RequestMapping("/form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        final TbCcnsizePrm tbCcnsizePrm = new TbCcnsizePrm();
        populateModel(model, tbCcnsizePrm, FormMode.CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(@RequestParam("cnsId") final Long cnsId, final Model model,
            @RequestParam("MODE1") final String viewmode) {
        log("Action 'formForUpdate'");
        final TbCcnsizePrm tbCcnsizePrm = new TbCcnsizePrm();
        tbCcnsizePrm.setCnsId(cnsId);
        if (viewmode.equals(MainetConstants.CommonConstants.VIEW) && (cnsId != null)) {
            populateModel(model, tbCcnsizePrm, FormMode.VIEW);
        } else if (viewmode.equals(MainetConstants.CommonConstants.EDIT) && (cnsId != null)) {
            populateModel(model, tbCcnsizePrm, FormMode.EDIT);

        } else {
            populateModel(model, tbCcnsizePrm, FormMode.CREATE);
        }

        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody TbWaterConnSizeResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final List<TbCcnsizePrm> list = connectionSizePrmService.findAll();

        final TbWaterConnSizeResponse waterConnSizeResponse = new TbWaterConnSizeResponse();
        waterConnSizeResponse.setRows(list);
        waterConnSizeResponse.setTotal(list.size());
        waterConnSizeResponse.setRecords(list.size());
        waterConnSizeResponse.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return waterConnSizeResponse;
    }

    private CustomDateEditor getCustomDateEditor(final Locale locale) {
        CustomDateEditor customDateEditor = customDateEditorByLocales.get(locale);
        if (customDateEditor == null) {
            final String dateFormatPattern = MainetConstants.DATE_FORMAT;
            final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
            customDateEditor = new CustomDateEditor(dateFormat, true);
            customDateEditorByLocales.put(locale, customDateEditor);
        }
        return customDateEditor;
    }

    @Override
    @InitBinder
    public void initBinder(final WebDataBinder binder, final HttpServletRequest request) {
        final Locale locale = RequestContextUtils.getLocale(request);
        binder.registerCustomEditor(Date.class, getCustomDateEditor(locale));
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid final TbCcnsizePrm tbCcnsizePrm, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        final DiameterWiseConnSizeValidator validator = new DiameterWiseConnSizeValidator();
        validator.validate(tbCcnsizePrm, bindingResult);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        try {
            if (!bindingResult.hasErrors()) {
                final TbCcnsizePrm tbCcnsizePrmCreated = connectionSizePrmService.create(tbCcnsizePrm, orgId, langId, empId);
                model.addAttribute(MAIN_ENTITY_NAME, tbCcnsizePrmCreated);
                populateModel(model, tbCcnsizePrmCreated, FormMode.CREATE);
                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
                model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL,
                        MainetConstants.WATERMASTERS.DIAMETERWISECONNSIZE.URL);
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                populateModel(model, tbCcnsizePrm, FormMode.CREATE);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            messageHelper.addException(model, MainetConstants.WATERMASTERS.DIAMETERWISECONNSIZE.TB_CCNSIZE_PRM_ERROR_CREATE, e);
            populateModel(model, tbCcnsizePrm, FormMode.CREATE);
            return JSP_LIST;
        }
    }

    @RequestMapping(params = "update")
    public String update(@Valid final TbCcnsizePrm tbCcnsizePrm, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        try {
            if (!bindingResult.hasErrors()) {
                final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                final int langId = UserSession.getCurrent().getLanguageId();
                final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
                final TbCcnsizePrm tbCcnsizePrmSaved = connectionSizePrmService.update(tbCcnsizePrm, orgId, langId, empId);
                model.addAttribute(MAIN_ENTITY_NAME, tbCcnsizePrmSaved);

                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
                log("Action 'update' : update done - redirect");
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                log("Action 'update' : binding errors");
                populateModel(model, tbCcnsizePrm, FormMode.UPDATE);
                return JSP_FORM;
            }
        } catch (final Exception e) {
            messageHelper.addException(model, MainetConstants.WATERMASTERS.DIAMETERWISECONNSIZE.TB_CCNSIZE_PRM_ERROR_UPDATE, e);
            log("Action 'update' : Exception - " + e.getMessage());
            populateModel(model, tbCcnsizePrm, FormMode.UPDATE);
            return JSP_FORM;
        }
    }
}
