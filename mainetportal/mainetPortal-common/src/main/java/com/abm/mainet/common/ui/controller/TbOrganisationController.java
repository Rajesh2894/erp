package com.abm.mainet.common.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbOrganisationResponse;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.service.TbOrganisationService;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.validator.OrganisationMasterValidator;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Controller
@RequestMapping("/Organisation.html")
public class TbOrganisationController {

    private static final Logger LOGGER = Logger.getLogger(TbOrganisationController.class);

    @Autowired
    private TbOrganisationService tbOrganisationService;

    @Autowired
    private OrganisationMasterValidator validator;

    Set<TbOrganisationRest> list = new HashSet<>();

    TbOrganisationRest tbOrgBean = null;
    private static final String PATH = "filePath";

    private final Logger logger = Logger.getLogger(TbOrganisationController.class);

    public TbOrganisationController() {
        super();
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * 
     * @param model
     * @param prganisationMasterDto
     */
    private void populateModel(Model model, TbOrganisationRest tbOrganisationRest,
            FormMode formMode) {
        /* ====== Main entity ========== */

        model.addAttribute(MainetConstants.OrgMaster.ENTITY_NAME, tbOrganisationRest);
        model.addAttribute(MainetConstants.FORM_NAME, tbOrganisationRest);

        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.OrgMaster.MODE, MainetConstants.OrgMaster.MODE_CREATE); // The form is in "create"
                                                                                                       // mode

            model.addAttribute(MainetConstants.OrgMaster.SAVE_ACTION, MainetConstants.OrgMaster.SAVE_ACTION_CREATE);
            // Other data useful in this screen in "create" mode (all fields)

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.OrgMaster.MODE, MainetConstants.OrgMaster.MODE_UPDATE); // The form is in "update"
                                                                                                       // mode

            model.addAttribute(MainetConstants.OrgMaster.SAVE_ACTION, MainetConstants.OrgMaster.SAVE_ACTION_UPDATE);
            // Other data useful in this screen in "update" mode (only non-pk fields)

        }
    }

    /**
     * Shows a list with all the occurrences of TbOrganisation found in the database
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        list.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        tbOrgBean = tbOrganisationService.findById(orgId);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

        final String status = tbOrgBean.getDefaultStatus();
        if ((status != null) && status.equals(MainetConstants.Common_Constant.YES)) {
            list = tbOrganisationService.findAll();
        } else {
            list.add(tbOrgBean);
        }
        model.addAttribute(MainetConstants.OrgMaster.LIST, list);
        return MainetConstants.OrgMaster.JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbOrganisation
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {

        // --- Populates the model with a new instance
        final TbOrganisationRest tbOrganisation = new TbOrganisationRest();
        final boolean existstatus = tbOrganisationService.defaultexist(MainetConstants.Common_Constant.YES);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

        final String exist = MainetConstants.TbDeporgMap.EXIST;
        if (existstatus) {
            model.addAttribute(exist, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(exist, MainetConstants.Common_Constant.NO);
        }

        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.OTY,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> divisionList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.DVN,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> ostList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.OST,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> disList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.DIS,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> sttList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.SIT,
                UserSession.getCurrent().getOrganisation());
        populateModel(model, tbOrganisation, FormMode.CREATE);
        model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
        model.addAttribute(MainetConstants.CommonMasterUi.DIVISION_LIST, divisionList);
        model.addAttribute(MainetConstants.CommonMasterUi.OST_LIST, ostList);
        model.addAttribute(MainetConstants.CommonMasterUi.DIS_LIST, disList);
        model.addAttribute(MainetConstants.CommonMasterUi.STT_LIST, sttList);
        return MainetConstants.OrgMaster.JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbOrganisation
     *
     * @param model Spring MVC model
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model,
            @RequestParam("orgid") final Long orgid) {
        // --- Search the entity by its primary key and stores it in the model
        final TbOrganisationRest tbOrganisation = tbOrganisationService.findById(orgid);

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.CommonMasterUi.SHOW_DOCS;

        if (tbOrganisation.getOLogo() != null) {
            tbOrganisation.setFilePath(
                    Utility.downloadedFileUrl(tbOrganisation.getOLogo(), outputPath, FileNetApplicationClient.getInstance()));
        }

        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.OTY,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> divisionList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.DVN,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> ostList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.OST,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> disList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.DIS,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> sttList = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.SIT,
                UserSession.getCurrent().getOrganisation());

        if (tbOrganisation.getEsdtDate() != null) {
            tbOrganisation.setEstDtStr(UtilityService.convertDateToDDMMYYYY(tbOrganisation.getEsdtDate()));
        }
        if (tbOrganisation.getTranStartDate() != null) {
            tbOrganisation.setTrnsDtStr(UtilityService.convertDateToDDMMYYYY(tbOrganisation.getTranStartDate()));
        }

        populateModel(model, tbOrganisation, FormMode.UPDATE);
        model.addAttribute(MainetConstants.CommonMasterUi.ORG_DEFAULT_STATUS, tbOrganisation.getDefaultStatus());

        model.addAttribute(MainetConstants.CommonMasterUi.LOOK_UP_LIST, lookUpList);
        model.addAttribute(MainetConstants.CommonMasterUi.DIVISION_LIST, divisionList);
        model.addAttribute(MainetConstants.CommonMasterUi.OST_LIST, ostList);
        model.addAttribute(MainetConstants.CommonMasterUi.DIS_LIST, disList);
        model.addAttribute(MainetConstants.CommonMasterUi.STT_LIST, sttList);

        return MainetConstants.OrgMaster.JSP_FORM;
    }

    @RequestMapping(params = "create")
    public @ResponseBody String create(@Valid TbOrganisationRest tbOrganisation, BindingResult bindingResult,
            Model model,
            HttpServletRequest httpServletRequest) {

        String result = MainetConstants.OrgMaster.FAILURE_MSG;

        ApplicationSession appSession = ApplicationSession.getInstance();
        UserSession userSession = null;
        tbOrganisation.setUserId(new Long(UserSession.getCurrent().getEmployee().getEmpId()));
        tbOrganisation.setLangId(new Short((short) UserSession.getCurrent().getLanguageId()));

        tbOrganisation.setLmoddate(new Date());

        if (tbOrganisation.getEstDtStr() != null)
            tbOrganisation.setEsdtDate(UtilityService.convertStringDateToDateFormat(tbOrganisation.getEstDtStr()));
        tbOrganisation
                .setTranStartDate(UtilityService.convertStringDateToDateFormat(tbOrganisation.getTrnsDtStr()));
        tbOrganisation.setOrgShortNm(tbOrganisation.getOrgShortNm().toUpperCase());

        validator.validate(tbOrganisation, bindingResult);

        if (!bindingResult.hasErrors()) {
            userSession = UserSession.getCurrent();

            TbOrganisationRest tbOrganisationRestCreated = null;

            try {

                tbOrganisationRestCreated = tbOrganisationService
                        .create(tbOrganisation, appSession, userSession, getDirectry(),
                                FileNetApplicationClient.getInstance());
                model.addAttribute("tbOrganisationRest", tbOrganisationRestCreated);
                result = MainetConstants.SUCCESS;
                return result;

            } catch (Exception ex) {
                throw new RuntimeException(result);
            }
        } else {
            populateModel(model, tbOrganisation, FormMode.CREATE);
            result = MainetConstants.OrgMaster.FAILURE_MSG;
            return result;
        }

    }

    @RequestMapping(params = "update")
    // GET or POST
    public @ResponseBody String update(@Valid final TbOrganisationRest tbOrganisation,
            final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest) {
        String result = MainetConstants.OrgMaster.FAILURE_MSG;
        final UserSession userSession = UserSession.getCurrent();
        try {
            if (!bindingResult.hasErrors()) {
                tbOrganisation.setEsdtDate(UtilityService.convertStringDateToDateFormat(tbOrganisation.getEstDtStr()));
                tbOrganisation.setUserId(new Long(UserSession.getCurrent().getEmployee().getEmpId()));
                tbOrganisation.setLangId(new Short((short) UserSession.getCurrent().getLanguageId()));
                tbOrganisation.setLmoddate(new Date());
                tbOrganisation.setEsdtDate(UtilityService.convertStringDateToDateFormat(tbOrganisation.getEstDtStr()));
                tbOrganisation.setTranStartDate(UtilityService.convertStringDateToDateFormat(tbOrganisation.getTrnsDtStr()));
                tbOrganisation.setUpdatedBy(new Long(UserSession.getCurrent().getEmployee().getEmpId()));
                tbOrganisation.setUpdatedDate(new Date());
                final TbOrganisationRest tbOrganisationSaved = tbOrganisationService
                        .update(tbOrganisation, getDirectry(), FileNetApplicationClient.getInstance());

                final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                        + MainetConstants.CommonMasterUi.SHOW_DOCS;
                final String filePath = Utility.downloadedFileUrl(tbOrganisationSaved.getOLogo(), outputPath,
                        FileNetApplicationClient.getInstance());

                userSession.setOrgLogoPath(filePath);
                model.addAttribute("tbOrganisation", tbOrganisationSaved);
                result = MainetConstants.SUCCESS;
                return result;
            } else {
                populateModel(model, tbOrganisation, FormMode.UPDATE);
                result = MainetConstants.OrgMaster.FAILURE_MSG;
                return result;
            }
        } catch (final Exception e) {
            // messageHelper.addException(model, "tbOrganisation.error.update", e);
            logger.error("Action 'update' : Exception - " + e.getMessage());
            populateModel(model, tbOrganisation, FormMode.UPDATE);
            return MainetConstants.OrgMaster.JSP_FORM;
        }
    }

    @RequestMapping(params = "checkForExist", method = RequestMethod.POST)
    public @ResponseBody List<String> checkForExist(final Model model,
            final HttpServletRequest httpServletRequest,
            @RequestParam(value = "mode") final String mode,
            @RequestParam(value = "orgId") final Long orgId,
            @RequestParam(value = "ulbOrgId") final Long ulbOrgId,
            @RequestParam(value = "orgName") final String orgName,
            @RequestParam(value = "orgNameMar") final String orgNameMar) {
        List<String> errorList = null;
        final ApplicationSession appSession = ApplicationSession.getInstance();
        errorList = tbOrganisationService
                .exist(mode, orgId, ulbOrgId, orgName, orgNameMar, appSession);
        return errorList;
    }

    @RequestMapping(params = "searchOrganisation")
    public @ResponseBody void searchOrganisation(
            @RequestParam(value = "orgid") final Long orgid) {
        list.clear();
        if (orgid != null) {
            tbOrgBean = tbOrganisationService.findById(orgid);
            list.add(tbOrgBean);
        } else {
            list = tbOrganisationService.findAll();
        }

    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody TbOrganisationResponse gridData(
            final HttpServletRequest request, final Model model) {
        final TbOrganisationResponse response = new TbOrganisationResponse();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.Common_Constant.PAGE));

        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.MAIN_LIST_NAME, list);
        return response;
    }

    @RequestMapping(params = "delete")
    // GET or POST
    public @ResponseBody String delete(@RequestParam("orgid") final Long orgid) {
        String message = MainetConstants.COMMON_STATUS.FAIL;
        try {
            tbOrganisationService.delete(orgid);
            message = MainetConstants.SUCCESS;
        } catch (final Exception e) {
            logger.error("Error : " + e.getMessage());
        }
        return message;
    }

    @RequestMapping(params = "checkDuplicateShortCode", method = RequestMethod.POST)
    public @ResponseBody String checkDuplicateShortCode(@RequestParam(value = "orgShortCode") final String orgShortCode) {
        String error = null;
        final Organisation organisation = tbOrganisationService.findByShortCode(orgShortCode);
        if (organisation != null) {
            error = ApplicationSession.getInstance().getMessage(MainetConstants.CommonMasterUi.TB_ORGANIZATION_ERROR_ORGSHRNAME);
        }
        return error;

    }

    @RequestMapping(params = "getUploadedImage", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final String getdataOfUploadedImage(final HttpServletRequest httpServletRequest) {
        try {

            if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if ((entry.getKey() != null) && (entry.getKey().longValue() == 0)) {
                        for (final File file : entry.getValue()) {
                            String fileName = null;
                            try {
                                final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                                        MainetConstants.WINDOWS_SLASH);
                                fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
                            } catch (final Exception e) {
                                LOGGER.error(MainetConstants.ERROR_OCCURED, e);

                            }

                            return fileName;
                        }
                    }

                }
            }
            return MainetConstants.BLANK;
        } catch (final Exception e) {

            LOGGER.error(MainetConstants.ERROR_OCCURED, e);

        }
        return MainetConstants.BLANK;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode, browserType);
        return jsonViewObject;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        JsonViewObject jsonViewObject = JsonViewObject.successResult();
        jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
        return jsonViewObject;
    }

    @RequestMapping(params = "getRealImagePath", method = RequestMethod.POST)
    public @ResponseBody String getRealImagePath(@RequestParam final Long orgId) {
        String filePath = null;
        if (orgId != null) {
            final TbOrganisationRest tbOrganisation = tbOrganisationService.findById(orgId);
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                    + MainetConstants.CommonMasterUi.SHOW_DOCS;
            filePath = Utility.downloadedFileUrl(tbOrganisation.getOLogo(), outputPath, FileNetApplicationClient.getInstance());
        }
        return filePath;
    }

    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse, final Model model) {
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER +
                MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
        try {
            final String downloadPath = Utility.downloadedFileUrl(downloadLink, outputPath,
                    FileNetApplicationClient.getInstance());
            model.addAttribute(PATH,
                    downloadPath);
        } catch (final Exception ex) {
            return new ModelAndView("redirect:/404error.jsp.html");
        }
        return new ModelAndView(MainetConstants.CommonMasterUi.VIEW_HELP, MainetConstants.FORM_NAME, model);
    }

    public String getDirectry() {
    	SimpleDateFormat formatter=new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date=formatter.format(new Date());
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + "ORGANISATION_MASTER"
                + File.separator + Utility.getTimestamp();
    }
    
    @RequestMapping(params = "locale", method = RequestMethod.GET)
    public String welcome(@RequestParam("lang") final String requestLang, @RequestParam("url") final String url,
            final HttpServletRequest request, final HttpServletResponse response) {
        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, org.springframework.util.StringUtils.parseLocaleString(requestLang));
        UserSession.getCurrent().setLanguageId(getLanguage(requestLang));
        String finalurl = url.replace("mainetgw9Qk", "&");
        return "redirect:" + finalurl;
    }
    private int getLanguage(final String language) {
        if (MainetConstants.DEFAULT_LOCAL_REG_STRING.equals(language)) {
            return 2;
        } else if (MainetConstants.DEFAULT_LOCALE_STRING.equals(language)) {
            return 1;
        } else {
            return 1;
        }
    }
}
