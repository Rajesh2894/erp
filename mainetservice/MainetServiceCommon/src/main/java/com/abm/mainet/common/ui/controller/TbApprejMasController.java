package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.TbApprejMstResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbApprejMas' management.
 */
@Controller
@RequestMapping("/CommonRemarkMaster.html")
public class TbApprejMasController extends AbstractController {

    private static final String COMMON_REMARK_MASTER_HTML = "CommonRemarkMaster.html";
    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = MainetConstants.TB_APPREJ_MAS;
    private static final String MAIN_LIST_NAME = "list";
    private static final String MAIN_LIST_NAME_REMARK = "listRemark";
    private static final String CATEGORY_LIST = "categoryList";

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "tbApprejMas/form";
    private static final String JSP_LIST = "tbApprejMas/list";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "CommonRemarkMaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "CommonRemarkMaster.html?update";
    @Resource
    private TbServicesMstService tbServicesMstService;
    // --- Main entity service
    @Resource
    private TbApprejMasService tbApprejMasService; // Injected by Spring

    @Resource
    private TbDepartmentService tbDepartmentService;
    // --- Other service(s)
    private List<TbDepartment> deptList = Collections.emptyList();
    private List<TbServicesMst> serviceMasList = Collections.emptyList();
    private List<TbServicesMst> serviceList = new ArrayList<>();
    private List<TbApprejMas> apprejMasList = new ArrayList<>();

    public TbApprejMasController() {
        super(TbApprejMasController.class, MAIN_ENTITY_NAME);
        log("TbApprejMasController created.");
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbApprejMas
     */
    private void populateModel(final Model model, final TbApprejMas tbApprejMas, final FormMode formMode) {
        // --- Main entity
        model.addAttribute(MAIN_ENTITY_NAME, tbApprejMas);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            // --- Other data useful in this screen in "create" mode (all fields)
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            // --- Other data useful in this screen in "update" mode (only non-pk fields)
        } else if (formMode == FormMode.VIEW) {
            model.addAttribute(MODE, MODE_VIEW);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        } else if (formMode == FormMode.EDIT) {
            model.addAttribute(MODE, MODE_EDIT);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        }
    }

    /**
     * Shows a list with all the occurrences of TbApprejMas found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        final TbApprejMas tbApprejMas = new TbApprejMas();
        final List<TbApprejMas> list = new ArrayList<>();
        log("Action 'list'");
        helpDoc("CommonRemarkMaster.html", model);
        deptList = tbDepartmentService.findByOrgId(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()),
                Long.valueOf(UserSession.getCurrent().getLanguageId()));
        serviceMasList = tbServicesMstService
                .findAllServiceListByOrgId(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
        serviceList = new ArrayList<>();
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.SERVICE_MAS_LIST, serviceMasList);
        model.addAttribute(MainetConstants.IS_DEFAULT_ORG, UserSession.getCurrent().getOrganisation().getDefaultStatus());
        model.addAttribute(MainetConstants.LANGUAGE_ID, UserSession.getCurrent().getLanguageId());
        model.addAttribute(MAIN_LIST_NAME, list);
        model.addAttribute(MAIN_ENTITY_NAME, tbApprejMas);
        apprejMasList.clear();
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MAIN_LIST_NAME_REMARK, lookUpList);
       //US#151005
        final List<LookUp> categoryList = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1, UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(CATEGORY_LIST, categoryList);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
        	model.addAttribute("kdmcEnv", "Y");
        else
        	model.addAttribute("kdmcEnv", "N");
        return JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbApprejMas
     * @param model Spring MVC model
     * @return
     */

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("artId") final Long artId,
            @RequestParam("MODE") final String MODE) {
        log("Action 'formForUpdate'");
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final TbApprejMas tbApprejMas = tbApprejMasService.findById(artId, orgid);
        if (MODE.equalsIgnoreCase(MainetConstants.EDIT)) {
            model.addAttribute(MainetConstants.EDIT, MainetConstants.EDIT);
            populateModel(model, tbApprejMas, FormMode.UPDATE);
        }

        if (MODE.equalsIgnoreCase(MainetConstants.CommonConstants.VIEW)) {
            populateModel(model, tbApprejMas, FormMode.VIEW);
        }
        return JSP_FORM;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        final List<TbApprejMas> list = new ArrayList<>();
        model.addAttribute(MAIN_LIST_NAME, list);
        final TbApprejMas tbApprejMas = new TbApprejMas();
        populateModel(model, tbApprejMas, FormMode.CREATE);
        model.addAttribute(MainetConstants.TB_APPREJ_MAS, tbApprejMas);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbApprejMas
     * @param model Spring MVC model
     * @param artId primary key element
     * @param orgid primary key element
     * @return
     */

    @RequestMapping(params = "searchServiceMst")
    public @ResponseBody List<TbApprejMas> searchServiceMst(final Model model, @RequestParam("deptId") final Long deptId,
            @RequestParam("serviceId") Long serviceId) {
        log("Action 'searchServiceMst'");
        
		LookUp lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(deptId);
		if(!lookUp.getLookUpCode().equals("CAT") && !lookUp.getLookUpCode().equals("WOA")) {
			if (serviceId.intValue() == -1) { 
				  serviceId = null; 
			  }
		}
		
        return apprejMasList = tbApprejMasService.findByServiceId(deptId, serviceId);
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody TbApprejMstResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");
        final TbApprejMstResponse response = new TbApprejMstResponse();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(apprejMasList);
        response.setTotal(apprejMasList.size());
        response.setRecords(apprejMasList.size());
        response.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, serviceList);

        return response;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbApprejMas entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create") // GET or POST
    public String create(@Valid final TbApprejMas tbApprejMas, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest,
            @RequestParam("MODE") final String MODE) {
        log("Action 'create'");
        try {
            if (!bindingResult.hasErrors()) {

                tbApprejMas.setStatusflag(MainetConstants.Y_FLAG);
                if (tbApprejMas.getArtServiceId() == null) {

                }
                if (tbApprejMas.getArtId() == null) {
                    tbApprejMas.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                } else {
                    tbApprejMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                }
                tbApprejMas.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                tbApprejMas.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
                tbApprejMas.setUserId( UserSession.getCurrent().getEmployee().getEmpId());
               
                final TbApprejMas tbApprejMasCreated = tbApprejMasService.create(tbApprejMas);
                model.addAttribute(MAIN_ENTITY_NAME, tbApprejMasCreated);
                model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL, COMMON_REMARK_MASTER_HTML);
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                populateModel(model, tbApprejMas, FormMode.CREATE);
                return JSP_FORM;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            e.printStackTrace();
            messageHelper.addException(model, "tbApprejMas.error.create", e);
            populateModel(model, tbApprejMas, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbApprejMas entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "Inactivate")
    public String update(@Valid final TbApprejMas tbApprejMas, final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest, @RequestParam("artId") final Long artId) {
        log("Action 'update'");
        try {
        	//124336
			if (!bindingResult.hasErrors()) {
				if (tbApprejMas.getArtId() == null) {
					tbApprejMas.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				} else {
					tbApprejMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				}
				tbApprejMas.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				tbApprejMas.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				tbApprejMas.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				final TbApprejMas tbApprejMasupdated = tbApprejMasService.update(tbApprejMas);
				model.addAttribute(MAIN_ENTITY_NAME, tbApprejMasupdated);
				model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL, COMMON_REMARK_MASTER_HTML);
				return MainetConstants.CommonConstants.SUCCESS_PAGE;
			} else {
				populateModel(model, tbApprejMas, FormMode.UPDATE);
				return JSP_FORM;
			}
           
        } catch (final Exception e) {
            messageHelper.addException(model, "tbApprejMas.error.update", e);
            log("Action 'update' : Exception - " + e.getMessage());
            populateModel(model, tbApprejMas, FormMode.UPDATE);
            return JSP_FORM;
        }
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param redirectAttributes
     * @param artId primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(value = "/delete/{artId}/{orgid}") // GET or POST
    public String delete(final RedirectAttributes redirectAttributes, @PathVariable("artId") final Long artId,
            @PathVariable("orgid") final Long orgid) {
        log("Action 'delete'");
        try {
            tbApprejMasService.delete(artId, orgid);
        } catch (final Exception e) {
            messageHelper.addException(redirectAttributes, "tbApprejMas.error.delete", e);
        }
        return redirectToList();
    }

}
