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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.TbSlopePrm;
import com.abm.mainet.water.service.SlopePrmService;
import com.abm.mainet.water.ui.model.TbSlopeEntryResponse;
import com.abm.mainet.water.ui.validator.SlopeEntryValidator;

@Controller
@RequestMapping("/TBSlopeParam.html")
public class WaterSlopePrmController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbSlopePrm";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbSlopePrm/form";
    private static final String JSP_LIST = "tbSlopePrm/list";

    @Resource
    private SlopePrmService slopePrmService;

    public WaterSlopePrmController() {
        super(WaterSlopePrmController.class, MAIN_ENTITY_NAME);
        log("TbSlopePrmController created.");
    }

    private void populateModel(final Model model, TbSlopePrm tbSlopePrm, final FormMode formMode) {

        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.EDIT) {
            tbSlopePrm = slopePrmService.findById(tbSlopePrm.getSpId());
            if (tbSlopePrm.getSpFrmdt() != null) {
                tbSlopePrm.setSpFrmdtStringFormat(
                        new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT).format(tbSlopePrm.getSpFrmdt()));
            }
            if (tbSlopePrm.getSpTodt() != null) {
                tbSlopePrm.setSpTodtStringFormat(
                        new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT).format(tbSlopePrm.getSpTodt()));
            }
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.VIEW) {
            tbSlopePrm = slopePrmService.findById(tbSlopePrm.getSpId());
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            model.addAttribute(MODE, MODE_VIEW);
        }
        model.addAttribute(MAIN_ENTITY_NAME, tbSlopePrm);
    }

    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        final List<TbSlopePrm> list = slopePrmService.findAll();
        model.addAttribute(MAIN_LIST_NAME, list);
        return JSP_FORM;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody TbSlopeEntryResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final List<TbSlopePrm> list = slopePrmService.findAll();
        final TbSlopeEntryResponse slopeEntryResponse = new TbSlopeEntryResponse();
        slopeEntryResponse.setRows(list);
        slopeEntryResponse.setTotal(list.size());
        slopeEntryResponse.setRecords(list.size());
        slopeEntryResponse.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return slopeEntryResponse;
    }

    @RequestMapping("/form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");

        final TbSlopePrm tbSlopePrm = new TbSlopePrm();
        populateModel(model, tbSlopePrm, FormMode.CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "form")
    public String formForUpdate(final Model model, @RequestParam("spId") final Long spId,
            @RequestParam("MODE1") final String viewmode) {
        log("Action 'formForUpdate'");

        final TbSlopePrm tbSlopeEntryEntity = new TbSlopePrm();
        tbSlopeEntryEntity.setSpId(spId);
        if (viewmode.equals(FormMode.VIEW) && (spId != null)) {
            populateModel(model, tbSlopeEntryEntity, FormMode.VIEW);
        } else if (viewmode.equals(MainetConstants.CommonConstants.EDIT) && (spId != null)) {
            populateModel(model, tbSlopeEntryEntity, FormMode.EDIT);

        } else {
            populateModel(model, tbSlopeEntryEntity, FormMode.CREATE);
        }
        return JSP_LIST;
    }

    @Override
    protected String redirectToForm(final HttpServletRequest httpServletRequest, final Object... idParts) {
        return "redirect:" + "TBSlopeParam.html";
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid final TbSlopePrm tbSlopePrm, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'create'");

        final SlopeEntryValidator validator = new SlopeEntryValidator();
        validator.validate(tbSlopePrm, bindingResult);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        try {
            if (!bindingResult.hasErrors()) {
                tbSlopePrm.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                final TbSlopePrm tbSlopePrmCreated = slopePrmService.create(tbSlopePrm, orgId, langId, empId);
                model.addAttribute(MAIN_ENTITY_NAME, tbSlopePrmCreated);

                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                populateModel(model, tbSlopePrm, FormMode.CREATE);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());

            messageHelper.addException(model, "tbSlopePrm.error.create", e);
            populateModel(model, tbSlopePrm, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(@Valid final TbSlopePrm tbSlopePrm, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        try {
            if (!bindingResult.hasErrors()) {

                tbSlopePrm.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                final TbSlopePrm tbSlopePrmSaved = slopePrmService.update(tbSlopePrm);
                model.addAttribute(MAIN_ENTITY_NAME, tbSlopePrmSaved);
                messageHelper.addMessage(redirectAttributes,
                        new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));

                log("Action 'update' : update done - redirect");
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                log("Action 'update' : binding errors");
                populateModel(model, tbSlopePrm, FormMode.UPDATE);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            messageHelper.addException(model, "tbSlopePrm.error.update", e);
            log("Action 'update' : Exception - " + e.getMessage());
            populateModel(model, tbSlopePrm, FormMode.UPDATE);
            return JSP_FORM;
        }
    }
}
