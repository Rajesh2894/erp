package com.abm.mainet.cfc.checklist.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.dto.TbCfcChecklistMstDto;
import com.abm.mainet.cfc.checklist.dto.TbDocumentGroup;
import com.abm.mainet.cfc.checklist.service.TbDocumentMstService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.dto.ServiceChecklistDTO;
import com.abm.mainet.common.master.repository.TbServicesMstJpaRepository;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.ui.model.ServiceChecklistResponse;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author hiren.poriya
 *
 */
@Controller
@RequestMapping("/DocumentGroupMaster.html")
public class TbDocumentMstController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(TbDocumentMstController.class);
    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "cfcChecklistMstDto";// tbCfcChecklistMst
    private static final String MAIN_LIST_NAME = "list";
    private static final String CLG_PREFIX_LIST = "clgPrefixList";
    private static final String DEFAULT_EXCEPTION_PAGE = "defaultExceptionFormView";

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "tbDocumentGroupMst/form";
    private static final String JSP_LIST = "tbDocumentGroupMst/list";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "DocumentGroupMaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "DocumentGroupMaster.html?update";

    // --- Main entity service
    /*
     * @Resource private TbCfcChecklistMstService tbCfcChecklistMstService;
     */

    @Resource
    private TbDocumentMstService tbDocumentGroupMstService;

    // --- Other service(s)
    @Resource
    private TbServicesMstService tbServicesMstService; // Injected by Spring

    @Resource
    private TbServicesMstJpaRepository tbServicesMstJpaRepository;

    private List<TbDocumentGroup> tbDocGrouptList = new ArrayList<>();

    private List<TbDocumentGroup> documentTemp;
    private List<ServiceChecklistDTO> tbServicesMstList = new ArrayList<>();

    /**
     * Default constructor
     */

    public TbDocumentMstController() {
        super(TbDocumentMstController.class, MAIN_ENTITY_NAME);
        log("TbDocumentMstController created.");
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbCfcChecklistMst
     */
    private void populateModel(final Model model, final TbCfcChecklistMstDto tbCfcChecklistMst, final FormMode formMode) {
        final List<LookUp> setPrefixList = CommonMasterUtility.getLookUps("SET", UserSession.getCurrent().getOrganisation());
        model.addAttribute("setPrefixList", setPrefixList);
        final List<LookUp> clgPrefixList = CommonMasterUtility.getLookUps(PrefixConstants.LookUpPrefix.CLG,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(CLG_PREFIX_LIST, clgPrefixList);
        model.addAttribute(MAIN_ENTITY_NAME, tbCfcChecklistMst);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        }
    }

    /**
     * Shows a list with all the occurrences of TbCfcChecklistMst found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        helpDoc("DocumentGroupMaster.html", model);
        final List<LookUp> clgPrefixList = CommonMasterUtility.getLookUps(PrefixConstants.LookUpPrefix.CLG,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(CLG_PREFIX_LIST, clgPrefixList);
        tbServicesMstList.clear();
        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody ServiceChecklistResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final int page = Integer.parseInt(request.getParameter("page"));
        final ServiceChecklistResponse response = new ServiceChecklistResponse();
        response.setRows(tbServicesMstList);
        response.setTotal(tbServicesMstList.size());
        response.setRecords(tbServicesMstList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, tbServicesMstList);
        return response;
    }

    /**
     * Shows a form page in order to create a new TbCfcChecklistMst
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        final TbCfcChecklistMstDto cfcChecklistMstDto = new TbCfcChecklistMstDto();
        populateModel(model, cfcChecklistMstDto, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbCfcChecklistMst
     * @param model Spring MVC model
     * @param clmId primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate")
    public ModelAndView formForUpdate(final Model model, @RequestParam("groupId") final Long groupId) {
        log("Action 'formForUpdate'");

        final TbCfcChecklistMstDto cfcChecklistMstDto = new TbCfcChecklistMstDto();
        final TbDocumentGroup docGroup = new TbDocumentGroup();
        try {
            tbDocGrouptList = tbDocumentGroupMstService
                    .findAllByGroupIdIdOrgId(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()), groupId);
        } catch (final Exception e) {
            LOGGER.error("Exception in form For Update method " + e);
            return new ModelAndView(DEFAULT_EXCEPTION_PAGE);
        }
        if (tbDocGrouptList.size() > 0) {
            cfcChecklistMstDto.setDocGroupList(tbDocGrouptList);
            documentTemp = tbDocGrouptList;
        }
        docGroup.setGroupCpdId(groupId);
        cfcChecklistMstDto.setDocGroupList(tbDocGrouptList);
        cfcChecklistMstDto.setDocGroupMst(docGroup);
        populateModel(model, cfcChecklistMstDto, FormMode.UPDATE);
        return new ModelAndView(JSP_FORM);
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbCfcChecklistMst entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST) // GET or POST
    public ModelAndView create(final TbCfcChecklistMstDto cfcChecklistMstDto, final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        try {
            if (!bindingResult.hasErrors()) {
                tbDocGrouptList = cfcChecklistMstDto.getDocGroupList();
                tbDocumentGroupMstService.saveDocumentlist(cfcChecklistMstDto, tbDocGrouptList);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.Req_Status.SUCCESS);
            } else {
                populateModel(model, cfcChecklistMstDto, FormMode.CREATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            LOGGER.error("Exception occured In form Create Method" + e);
            return new ModelAndView(DEFAULT_EXCEPTION_PAGE);
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbCfcChecklistMst entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update", method = RequestMethod.POST)
    public ModelAndView update(final TbCfcChecklistMstDto cfcChecklistMstDto, final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        try {
            if (!bindingResult.hasErrors()) {

                tbDocumentGroupMstService.updateDocumentList(cfcChecklistMstDto, tbDocGrouptList, documentTemp);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.Req_Status.SUCCESS);
            } else {
                log("Action 'update' : binding errors");
                populateModel(model, cfcChecklistMstDto, FormMode.UPDATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {
            log("Action 'update' : Exception - " + e.getMessage());
            LOGGER.error("Exception in form update method" + e);
            return new ModelAndView(DEFAULT_EXCEPTION_PAGE);
        }
    }

    @RequestMapping(params = "searchData")
    public @ResponseBody String searchData(final Model model, final HttpServletRequest httpServletRequest,
            @RequestParam(value = "groupId") final Long groupId) {
        tbServicesMstList = tbDocumentGroupMstService
                .getSearchDocumentData(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()), groupId);
        if (tbServicesMstList.size() > 0) {
            return MainetConstants.Common_Constant.NO;
        } else {
            return MainetConstants.Common_Constant.YES;
        }

    }
}
