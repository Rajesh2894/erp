package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.ui.model.ConfigurationMasterResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureDet;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbAcCodingstructureMas' management.
 */
@Controller
@RequestMapping("ConfigurationMaster.html")
public class TbAcCodingstructureMasController extends AbstractController {

    private static Logger logger = Logger.getLogger(TbAcCodingstructureMasController.class);

    private static final String JSP_FORM = "tbAcCodingstructureMas/form";

    private static final String JSP_LIST = "tbAcCodingstructureMas/list";

    private static final String SAVE_ACTION_CREATE = "ConfigurationMaster.html?create";

    private static final String SAVE_ACTION_UPDATE = "ConfigurationMaster.html?update";

    private String modeView = MainetConstants.BLANK;

    private Date lmodDate;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private TbComparamDetService comparamDetService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    private List<TbAcCodingstructureMas> tbCodingList = null;

    public TbAcCodingstructureMasController() {
        super(TbAcCodingstructureMasController.class, MainetConstants.CONFIG_MASTER.MAIN_ENTITY_NAME);
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("comCpdId") final Long comCpdId) {
        log("AccountBudgetCode-'getjqGridsearch' : 'get jqGrid search data'");
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MENU.Y);
        Long defaultOrgId = null;
        if (isDafaultOrgExist) {
            defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }

        Organisation defaultOrganization = null;
        if (isDafaultOrgExist) {
            defaultOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrganization = UserSession.getCurrent().getOrganisation();
        }

        final List<LookUp> activeDeActiveMap = CommonMasterUtility
                .getListLookup(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, defaultOrganization);
        tbCodingList = new ArrayList<>();
        tbCodingList.clear();

        tbCodingList = tbAcCodingstructureMasService.findByAllGridSearchData(comCpdId, defaultOrgId);
        for (final TbAcCodingstructureMas bean : tbCodingList) {
            if (isDafaultOrgExist) {
                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    bean.setComChagflag(MainetConstants.MENU.Y);
                }
            } else {
                bean.setComChagflag(MainetConstants.MENU.Y);
            }
            final String cpdIdStatsFlag = bean.getComAppflag();
            String lookUpCode = null;
            if ((cpdIdStatsFlag != null) && !cpdIdStatsFlag.isEmpty()) {
                for (final LookUp lookUp : activeDeActiveMap) {
                    lookUpCode = lookUp.getLookUpCode();
                    if (lookUpCode.equals(cpdIdStatsFlag)) {
                        bean.setComponentStatus(lookUp.getLookUpDesc());
                    }
                }
            }
            bean.setComDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.CMD, bean.getOrgid(), bean.getComCpdId()));
        }
        return tbCodingList;
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbAcCodingstructureMas
     */
    private void populateModel(final Model model, final TbAcCodingstructureMas tbAcCodingstructureMas, final FormMode formMode) {

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MENU.Y);
        if (isDafaultOrgExist) {
            model.addAttribute(MainetConstants.TbAcCodingstructureMas.DEFAULT_STATUS, MainetConstants.MENU.Y);
        } else {
            model.addAttribute(MainetConstants.TbAcCodingstructureMas.NON_DEFAULT_STATUS, MainetConstants.MENU.Y);
        }

        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        } else if (formMode == FormMode.VIEW) {
            model.addAttribute(MODE, MODE_VIEW); // The form is in "view" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        }
        model.addAttribute(MainetConstants.CONFIG_MASTER.MAIN_ENTITY_NAME, tbAcCodingstructureMas);
    }

    @RequestMapping()
    public String list(final Model model) {

        tbCodingList = new ArrayList<>();
        tbCodingList.clear();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MENU.Y);
        final TbAcCodingstructureMas tbAcCodingstructureMas = new TbAcCodingstructureMas();

        if (isDafaultOrgExist) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.TbAcCodingstructureMas.DEFAULT_ID, MainetConstants.MENU.Y);
            }
        } else {
            model.addAttribute(MainetConstants.TbAcCodingstructureMas.NON_DEFAULT_ID, MainetConstants.MENU.Y);
        }

        Long defaultOrgId = null;
        if (isDafaultOrgExist) {
            defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }
        final List<LookUp> confLookUpList = new ArrayList<>();
        final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService.findAllWithOrgId(defaultOrgId);
        for (final TbAcCodingstructureMas acCodingstructureMas : tbCodingList) {
            final Long comCpdId = acCodingstructureMas.getComCpdId();
            final LookUp chartOfAcLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(comCpdId,
                    UserSession.getCurrent().getOrganisation());
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId(chartOfAcLookUp.getLookUpId());
            lookUp.setLookUpCode(chartOfAcLookUp.getLookUpCode());
            lookUp.setDescLangFirst(chartOfAcLookUp.getDescLangFirst());
            confLookUpList.add(lookUp);
        }
        model.addAttribute(MainetConstants.CONFIG_MASTER.LISTOFLOOKUP, confLookUpList);

        populateModel(model, tbAcCodingstructureMas, FormMode.CREATE);
        return JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbAcCodingstructureMas
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {

        final TbAcCodingstructureMas tbAcCodingstructureMas = new TbAcCodingstructureMas();

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MENU.Y);

        Organisation defaultOrganization = null;
        if (isDafaultOrgExist) {
            defaultOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrganization = UserSession.getCurrent().getOrganisation();
        }

        final List<LookUp> componentNameLookUp = CommonMasterUtility.getLookUps(PrefixConstants.CMD, defaultOrganization);
        final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService
                .findAllWithOrgId(defaultOrganization.getOrgid());
        final Map<Long, TbAcCodingstructureMas> map = new LinkedHashMap<>();
        final List<LookUp> tempLooUpList = new ArrayList<>();
        if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
            for (final TbAcCodingstructureMas mas : tbCodingList) {
                map.put(mas.getComCpdId(), mas);
            }
            for (final LookUp lookUp : componentNameLookUp) {
                if (!(map.containsKey(Long.valueOf(lookUp.getLookUpId())))) {
                    tempLooUpList.add(lookUp);
                }
            }
            model.addAttribute(MainetConstants.CONFIG_MASTER.COMPONENT, tempLooUpList);
        } else {
            model.addAttribute(MainetConstants.CONFIG_MASTER.COMPONENT, componentNameLookUp);
        }
        populateModel(model, tbAcCodingstructureMas, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbAcCodingstructureMas
     * @param model Spring MVC model
     * @param codcofId primary key element
     * @return
     */

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("codcofId") final Long codcofId) {

        final TbAcCodingstructureMas tbAcCodingstructureMas = tbAcCodingstructureMasService.findById(codcofId);
        tbAcCodingstructureMas.setTestCodNoLevel(tbAcCodingstructureMas.getCodNoLevel());
        final List<LookUp> componentName = CommonMasterUtility.getListLookup(PrefixConstants.CMD,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.CONFIG_MASTER.LISTOFLOOKUP, componentName);
        final List<LookUp> statuslookUp = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.CONFIG_MASTER.STATUSLOOKUP, statuslookUp);
        final List<TbAcCodingstructureDet> codingInstruction = tbAcCodingstructureMas.getTbAcCodingstructureDetEntity();
        if ((codingInstruction != null) && !codingInstruction.isEmpty()) {
            Collections.sort(codingInstruction, TbAcCodingstructureDet.compareOnCodLevel);
        }
        tbAcCodingstructureMas.setTbAcCodingstructureDetEntity(codingInstruction);
        setLmodDate(tbAcCodingstructureMas.getLmoddate());
        populateModel(model, tbAcCodingstructureMas, FormMode.UPDATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "formForView")
    public String formForView(TbAcCodingstructureMas tbAcCodingstructureMas, final Model model,
            @RequestParam("codcofId") final Long codcofId,
            @RequestParam("MODE_DATA") final String viewmode, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("TbAcCodingstructureMas-'gridData' : 'view'");
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);

            final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MENU.Y);

            Organisation defaultOrganization = null;
            if (isDafaultOrgExist) {
                defaultOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
            } else {
                defaultOrganization = UserSession.getCurrent().getOrganisation();
            }

            tbAcCodingstructureMas = tbAcCodingstructureMasService.findById(codcofId);
            tbAcCodingstructureMas.setTestCodNoLevel(tbAcCodingstructureMas.getCodNoLevel());
            final List<LookUp> componentName = CommonMasterUtility.getListLookup(PrefixConstants.CMD, defaultOrganization);
            model.addAttribute(MainetConstants.CONFIG_MASTER.LISTOFLOOKUP, componentName);
            final List<LookUp> statuslookUp = CommonMasterUtility.getLookUps(PrefixConstants.ACN, defaultOrganization);
            model.addAttribute(MainetConstants.CONFIG_MASTER.STATUSLOOKUP, statuslookUp);

            final List<TbAcCodingstructureDet> codingInstruction = tbAcCodingstructureMas.getTbAcCodingstructureDetEntity();
            if ((codingInstruction != null) && !codingInstruction.isEmpty()) {
                Collections.sort(codingInstruction, TbAcCodingstructureDet.compareOnCodLevel);
            }
            tbAcCodingstructureMas.setTbAcCodingstructureDetEntity(codingInstruction);
            setLmodDate(tbAcCodingstructureMas.getLmoddate());
            populateModel(model, tbAcCodingstructureMas, FormMode.VIEW);
            return JSP_FORM;
        } else {
            log("TbAcCodingstructureMas 'view' : binding errors");
            populateModel(model, tbAcCodingstructureMas, FormMode.VIEW);
            return JSP_FORM;
        }
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbAcCodingstructureMas entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid final TbAcCodingstructureMas tbAcCodingstructureMas, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {

        if (!bindingResult.hasErrors()) {
            if (tbAcCodingstructureMas.getTestCodNoLevel() != null) {
                tbAcCodingstructureMas.setCodNoLevel(tbAcCodingstructureMas.getTestCodNoLevel());
            }
            tbAcCodingstructureMas.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcCodingstructureMas.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcCodingstructureMas.setLmoddate(new Date());
            tbAcCodingstructureMas.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final TbAcCodingstructureMas tbAcCodingstructureMasCreated = tbAcCodingstructureMasService
                    .create(tbAcCodingstructureMas);
            tbAcCodingstructureMasCreated.setAlreadyExist(MainetConstants.MASTER.Y);
            model.addAttribute(MainetConstants.CONFIG_MASTER.MAIN_ENTITY_NAME, tbAcCodingstructureMasCreated);
            return JSP_FORM;
        } else {
            tbAcCodingstructureMas.setAlreadyExist(MainetConstants.MASTER.N);
            logger.error("error in create method for binding result");
            return MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbAcCodingstructureMas entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update") // GET or POST
    public String update(@Valid final TbAcCodingstructureMas tbAcCodingstructureMas, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {

        if (!bindingResult.hasErrors()) {
            if ((tbAcCodingstructureMas.getComAppflag() != null) && !tbAcCodingstructureMas.getComAppflag().isEmpty()) {
                tbAcCodingstructureMas.setComAppflag(tbAcCodingstructureMas.getComAppflag());
            }
            tbAcCodingstructureMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcCodingstructureMas.setUpdatedDate(new Date());
            tbAcCodingstructureMas.setLmoddate(getLmodDate());
            tbAcCodingstructureMas.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            tbAcCodingstructureMas.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
            tbAcCodingstructureMas.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            final TbAcCodingstructureMas tbAcCodingstructureMasSaved = tbAcCodingstructureMasService
                    .update(tbAcCodingstructureMas);
            tbAcCodingstructureMasSaved.setAlreadyExist(MainetConstants.MASTER.Y);
            model.addAttribute(MainetConstants.CONFIG_MASTER.MAIN_ENTITY_NAME, tbAcCodingstructureMasSaved);
            return JSP_FORM;
        } else {
            tbAcCodingstructureMas.setAlreadyExist(MainetConstants.MASTER.N);
            logger.error("error in update method for binding result");
            return MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param redirectAttributes
     * @param codcofId primary key element
     * @return
     */
    @RequestMapping(params = "delete") // GET or POST
    public String delete(final RedirectAttributes redirectAttributes, @RequestParam("codcofId") final Long codcofId) {

        tbAcCodingstructureMasService.delete(codcofId);
        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody ConfigurationMasterResponse gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final ConfigurationMasterResponse response = new ConfigurationMasterResponse();
        response.setRows(tbCodingList);
        response.setTotal(tbCodingList.size());
        response.setRecords(tbCodingList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbCodingList);
        return response;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
}
