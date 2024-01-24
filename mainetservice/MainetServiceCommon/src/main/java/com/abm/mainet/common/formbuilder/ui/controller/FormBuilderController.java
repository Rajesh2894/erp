/*
 * Created on 21 Mar 2016 ( Time 19:07:04 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.formbuilder.ui.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.ScrutinyServiceDto;
import com.abm.mainet.common.formbuilder.dto.FormBuilder;
import com.abm.mainet.common.formbuilder.dto.FormBuildersDto;
import com.abm.mainet.common.formbuilder.service.IFormBuilderService;
import com.abm.mainet.common.formbuilder.ui.validator.FormBuilderValidator;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.ui.model.ScrutinyServiceDataResponse;
import com.abm.mainet.common.master.ui.model.TbScrutinyMstListItem;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * Spring MVC controller for 'FormBuilder' management.
 */
@Controller
@RequestMapping("/FormBuilder.html")
public class FormBuilderController extends AbstractController {

    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "formBuilders";
    private static final String MAIN_LIST_NAME = "list";

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "formBuilder/form";
    private static final String JSP_LIST = "formBuilder/list";
    private static final String JSP_FORM_VIEW = "formBuilder/viewform";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "FormBuilder.html?create";
    private static final String SAVE_ACTION_UPDATE = "FormBuilder.html?update";

    // --- Main entity service
    @Resource
    private IFormBuilderService formBuilderService; // Injected by Spring

    @Resource
    private TbDepartmentService tbDepartmentService; // Injected by Spring

    @Autowired
    private IEmployeeService employeeService; // Injected by Spring

    @Resource
    private TbServicesMstService servicesMstService; // Injected by Spring

    private List<FormBuilder> scrutinyLabelsList = new ArrayList<>();
    private FormBuilder formBuilserTemp;
    private List<ScrutinyServiceDto> scrutinyServiceList = new ArrayList<>();

    // --------------------------------------------------------------------------------------
    /**
     * Default constructor
     */
    public FormBuilderController() {
        super(FormBuilderController.class, MAIN_ENTITY_NAME);
        log("FormBuilderController created.");
    }

    // --------------------------------------------------------------------------------------
    // Spring MVC model management
    // --------------------------------------------------------------------------------------
    /**
     * Populates the combo-box "items" for the referenced entity "TbScrutinyMst"
     * @param model
     */
    private void populateListOfTbScrutinyMstItems(final Model model) {
        final List<TbScrutinyMstListItem> items = new LinkedList<>();
        model.addAttribute(MainetConstants.CommonMasterUi.lIST_OF_SCRUITINY_MSTITEMS, items);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param formBuilder
     */
    private void populateModel(final Model model, final FormBuilder formBuilder, final FormMode formMode) {
        // --- Main entity
        final List<String> yesNoList = new ArrayList<>();
        yesNoList.add(MainetConstants.YESL);
        yesNoList.add(MainetConstants.NOL);
        model.addAttribute(MainetConstants.CommonMasterUi.YES_NO_LIST, yesNoList);
        model.addAttribute(MAIN_ENTITY_NAME, formBuilder);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            // --- Other data useful in this screen in "create" mode (all fields)
            populateListOfTbScrutinyMstItems(model);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            // --- Other data useful in this screen in "update" mode (only non-pk fields)
            populateListOfTbScrutinyMstItems(model);
        }
    }

    // --------------------------------------------------------------------------------------
    // Request mapping
    // --------------------------------------------------------------------------------------
    /**
     * Shows a list with all the occurrences of FormBuilder found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        helpDoc("FormBuilder.html", model);
        final ScrutinyServiceDto serviceDto = new ScrutinyServiceDto();
        scrutinyServiceList = new ArrayList<>();
        final List<TbDepartment> deptList = tbDepartmentService.searchDeptData(null,
                MainetConstants.SolidWasteManagement.SHORT_CODE);
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, serviceDto);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
        return JSP_LIST;
    }

    /**
     * Gets the Grid Data
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "getGridData")
    public @ResponseBody ScrutinyServiceDataResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));

        final List<ScrutinyServiceDto> list = scrutinyServiceList;
        final ScrutinyServiceDataResponse response = new ScrutinyServiceDataResponse();
        final int dataSize = list.size();
        response.setRows(list);
        response.setTotal(dataSize);
        response.setRecords(dataSize);
        response.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return response;
    }

    /**
     * Shows a form page in order to create a new FormBuilder
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        // --- Populates the model with a new instance
        final FormBuilder formBuilder = new FormBuilder();
        formBuilserTemp = formBuilder;
        final FormBuildersDto formBuildersDto = new FormBuildersDto();
        scrutinyLabelsList = new ArrayList<>();

        final List<ScrutinyServiceDto> serviceList = formBuilderService
                .getAllServices(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.SolidWasteManagement.SHORT_CODE);
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.FORM_BUILDER_TYPE,
                UserSession.getCurrent().getOrganisation());
        final List<GroupMaster> groupDataList = getRoleCode();

        populateModel(model, formBuilder, FormMode.CREATE);
        model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, serviceList);
        model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, formBuildersDto);
        model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
        model.addAttribute(MainetConstants.CommonMasterUi.GROUP_DATA_LIST, groupDataList);

        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing FormBuilder
     * @param model Spring MVC model
     * @param slLabelId primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("smShortdesc") final String smShortdesc) {
        log("Action 'formForUpdate'");
        // --- Search the entity by its primary key and stores it in the model
        final FormBuildersDto scrutinyLabelsDto = new FormBuildersDto();
        final FormBuilder tbScrutinyLabels = new FormBuilder();
        scrutinyLabelsList = formBuilderService.findAllFormBuilderLabelData(smShortdesc,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final List<ScrutinyServiceDto> serviceList = formBuilderService
                .getFormBuilderServices(UserSession.getCurrent().getOrganisation().getOrgid());
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.FORM_BUILDER_TYPE,
                UserSession.getCurrent().getOrganisation());
        final List<GroupMaster> groupDataList = getRoleCode();
        final List<String> yesNoList = new ArrayList<>();
        yesNoList.add(MainetConstants.YESL);
        yesNoList.add(MainetConstants.NOL);

        if (UserSession.getCurrent().getLanguageId() == 1) {
            for (final LookUp lookUpData : lookUpList) {
                lookUpData.setLookUpDesc(lookUpData.getDescLangFirst());
            }
        } else {
            for (final LookUp lookUpData : lookUpList) {
                lookUpData.setLookUpDesc(lookUpData.getDescLangSecond());
            }
        }

        if (!scrutinyLabelsList.isEmpty()) {
            formBuilserTemp = scrutinyLabelsList.get(0);
            scrutinyLabelsDto.setScrutinyLabels(formBuilserTemp);
            scrutinyLabelsDto.setScrutinyLabelsList(scrutinyLabelsList);
        }
        populateModel(model, tbScrutinyLabels, FormMode.UPDATE);
        model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, serviceList);
        model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, scrutinyLabelsDto);
        model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
        model.addAttribute(MainetConstants.CommonMasterUi.GROUP_DATA_LIST, groupDataList);
        model.addAttribute(MainetConstants.CommonMasterUi.YES_NO_LIST, yesNoList);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing FormBuilder
     * @param model Spring MVC model
     * @param slLabelId primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "formForView")
    public String formForView(final Model model, @RequestParam("smShortdesc") final String smShortdesc) {
        log("Action 'formForUpdate'");
        // --- Search the entity by its primary key and stores it in the model
        final FormBuildersDto formBuildersDto = new FormBuildersDto();
        final FormBuilder tbScrutinyLabels = new FormBuilder();
        scrutinyLabelsList = formBuilderService.findAllFormBuilderLabelData(smShortdesc,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final List<ScrutinyServiceDto> serviceList = formBuilderService
                .getFormBuilderServices(UserSession.getCurrent().getOrganisation().getOrgid());
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.FORM_BUILDER_TYPE,
                UserSession.getCurrent().getOrganisation());
        final List<GroupMaster> groupDataList = getRoleCode();

        if (!scrutinyLabelsList.isEmpty()) {
            formBuilserTemp = scrutinyLabelsList.get(0);
            formBuildersDto.setScrutinyLabels(formBuilserTemp);
            formBuildersDto.setScrutinyLabelsList(scrutinyLabelsList);
        }
        populateModel(model, tbScrutinyLabels, FormMode.UPDATE);
        model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, serviceList);
        model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, formBuildersDto);
        model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
        model.addAttribute(MainetConstants.CommonMasterUi.GROUP_DATA_LIST, groupDataList);
        return JSP_FORM_VIEW;
    }

    private List<GroupMaster> getRoleCode() {
        long swmDept = tbDepartmentService.findDepartmentByCode(MainetConstants.SolidWasteManagement.SHORT_CODE).getDpDeptid();
        final List<GroupMaster> groupDataList = employeeService
                .getGroupDataList(UserSession.getCurrent().getOrganisation().getOrgid(), swmDept);
        return groupDataList;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbScrutinyLabels entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create") // GET or POST
    public ModelAndView create(@Valid final FormBuildersDto formBuildersDto, final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        final ModelAndView mv = new ModelAndView(JSP_FORM);
        try {
            final FormBuilderValidator validator = new FormBuilderValidator();
            validator.validate(formBuildersDto, bindingResult);

            if (!bindingResult.hasErrors()) {
                scrutinyLabelsList = formBuildersDto.getScrutinyLabelsList();
                formBuilderService.createFormBuilderLabel(formBuildersDto, scrutinyLabelsList);
                model.addAttribute(MAIN_ENTITY_NAME, new FormBuilder());
                populateModel(model, new FormBuilder(), FormMode.CREATE);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.COMMON_STATUS.SUCCESS);
            } else {

                final List<ScrutinyServiceDto> serviceList = formBuilderService
                        .getAllServices(UserSession.getCurrent().getOrganisation().getOrgid(),
                                MainetConstants.SolidWasteManagement.SHORT_CODE);
                final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(
                        MainetConstants.CommonMasterUi.FORM_BUILDER_TYPE,
                        UserSession.getCurrent().getOrganisation());
                final List<GroupMaster> groupDataList = getRoleCode();
                final List<String> yesNoList = new ArrayList<>();
                yesNoList.add(MainetConstants.YESL);
                yesNoList.add(MainetConstants.NOL);

                if (!scrutinyLabelsList.isEmpty()) {
                    formBuilserTemp = scrutinyLabelsList.get(0);
                    formBuildersDto.setScrutinyLabels(formBuilserTemp);
                    formBuildersDto.setScrutinyLabelsList(scrutinyLabelsList);
                }
                model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, serviceList);
                model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, formBuildersDto);
                model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
                model.addAttribute(MainetConstants.CommonMasterUi.GROUP_DATA_LIST, groupDataList);
                model.addAttribute(MainetConstants.CommonMasterUi.YES_NO_LIST, yesNoList);

                model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, formBuildersDto);
                populateModel(model, new FormBuilder(), FormMode.CREATE);
                return mv;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            messageHelper.addException(model, "tbScrutinyLabels.error.create", e);
            populateModel(model, new FormBuilder(), FormMode.CREATE);
            return mv;
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbScrutinyLabels entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update") // GET or POST
    public ModelAndView update(@Valid final FormBuildersDto formBuildersDto, final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        try {
            final FormBuilderValidator validator = new FormBuilderValidator();
            validator.validate(formBuildersDto, bindingResult);

            if (!bindingResult.hasErrors()) {
                // --- Perform database operations
                formBuilderService.updateFormBuilderLabel(formBuildersDto, scrutinyLabelsList, formBuilserTemp);
                final FormBuilder tbScrutinyLabels = new FormBuilder();
                model.addAttribute(MAIN_ENTITY_NAME, tbScrutinyLabels);
                // --- Set the result message
                populateModel(model, tbScrutinyLabels, FormMode.UPDATE);
                log("Action 'update' : update done - redirect");
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.COMMON_STATUS.SUCCESS);
            } else {
                log("Action 'update' : binding errors");
                final List<ScrutinyServiceDto> serviceList = formBuilderService
                        .getFormBuilderServices(UserSession.getCurrent().getOrganisation().getOrgid());
                final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(
                        MainetConstants.CommonMasterUi.FORM_BUILDER_TYPE,
                        UserSession.getCurrent().getOrganisation());
                final List<GroupMaster> groupDataList = getRoleCode();
                final List<String> yesNoList = new ArrayList<>();
                yesNoList.add(MainetConstants.YESL);
                yesNoList.add(MainetConstants.NOL);

                model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, serviceList);
                model.addAttribute(MainetConstants.CommonMasterUi.FORM_BUILDERS_DTO, formBuildersDto);
                model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
                model.addAttribute(MainetConstants.CommonMasterUi.GROUP_DATA_LIST, groupDataList);
                model.addAttribute(MainetConstants.CommonMasterUi.YES_NO_LIST, yesNoList);
                final FormBuilder tbScrutinyLabels = new FormBuilder();
                populateModel(model, tbScrutinyLabels, FormMode.UPDATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {
            messageHelper.addException(model, "tbScrutinyLabels.error.update", e);
            log("Action 'update' : Exception - " + e.getMessage());
            final FormBuilder tbScrutinyLabels = new FormBuilder();
            populateModel(model, tbScrutinyLabels, FormMode.UPDATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param redirectAttributes
     * @param slLabelId primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "delete") // GET or POST
    public String delete(@RequestParam("slLabelId") final Long slLabelId, @RequestParam("orgid") final Long orgid) {
        log("Action 'delete'");
        try {
            formBuilderService.delete(slLabelId, orgid);
            // --- Set the result message
        } catch (final Exception e) {
            log("ERROR : " + e.getMessage());
        }
        return JSP_FORM;
    }

    @RequestMapping(params = "refreshService")
    public @ResponseBody List<TbServicesMst> refreshService(final Model model, final HttpServletRequest httpServletRequest,
            @RequestParam(value = "deptId") final Long deptId) {

        final List<TbServicesMst> serviceMst = servicesMstService.findALlActiveServiceByDeptId(deptId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return serviceMst;
    }

    @RequestMapping(params = "searchData")
    public @ResponseBody void searchData(final Model model,
            final HttpServletRequest httpServletRequest,
            @RequestParam(value = "serviceCode") final String serviceCode,
            @RequestParam(value = "deptId") final Long deptId) {
        long swmDept = tbDepartmentService.findDepartmentByCode(MainetConstants.SolidWasteManagement.SHORT_CODE).getDpDeptid();

        scrutinyServiceList = formBuilderService.findAllServiceListData(
                Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()),
                serviceCode, swmDept);

    }

}
