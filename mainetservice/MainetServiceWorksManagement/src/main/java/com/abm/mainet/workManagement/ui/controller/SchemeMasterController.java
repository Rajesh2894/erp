package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.service.SchemeMasterService;
import com.abm.mainet.workManagement.ui.model.SchemeMasterModel;

/**
 * @author vishwajeet.kumar
 * @since 5 Dec 2017
 */
@Controller
@RequestMapping("/WmsSchemeMaster.html")
public class SchemeMasterController extends AbstractFormController<SchemeMasterModel> {

    @Autowired
    private SchemeMasterService schemeMasterService;

    @Autowired
    IFileUploadService fileUpload;

    private static final Logger LOGGER = Logger.getLogger(SchemeMasterController.class);

    /**
     * used for showing default home page for scheme master
     * 
     * @param httpServletRequest
     * @return default view
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        SchemeMasterModel model = this.getModel();
        this.getModel().setCommonHelpDocs("WmsSchemeMaster.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        model.setSchemeMasterList(schemeMasterService.getSchemeMasterList(null, null, null, orgId));
        model.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        List<LookUp> defaultStatus = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation).stream()
                .filter(c -> c.getDefaultVal().equals(MainetConstants.FlagY)).collect(Collectors.toList());
        if (defaultStatus.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            model.setUADstatusForScheme(defaultStatus.get(0).getLookUpCode());
        }
        populateModel(model);
        return index();
    }

    /**
     * used for create Scheme master details form
     * 
     * @param saveMode
     * @return
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.WorksManagement.ADD_SCHEME_MASTER)
    public ModelAndView addSchemeMaster(final HttpServletRequest request) {
        sessionCleanup(request);
        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || !request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.PROJECT_ADD))

            this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        else {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.PROJECT_ADD);
            request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
        }
        SchemeMasterModel model = this.getModel();
        model.setLookUps(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.SBY,
                UserSession.getCurrent().getOrganisation()));
        model.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        populateModel(model);
        return new ModelAndView(MainetConstants.WorksManagement.ADD_SCHEME_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * used for check duplicate scheme code
     * 
     * @param wmSchCode
     * @param orgId
     * @return
     */

    @RequestMapping(params = MainetConstants.WorksManagement.CHECK_DUPLICATE_SCHEME_CODE, method = RequestMethod.POST)
    public @ResponseBody String checkDuplicateforSchemeCode(
            @RequestParam(MainetConstants.WorksManagement.SCHEME_CODE) final String wmSchCode) {
        return schemeMasterService.checkDuplicateSchemeCode(wmSchCode,
                UserSession.getCurrent().getOrganisation().getOrgid());

    }

    /**
     * used for edit All Scheme master data
     * 
     * @param wmSchId
     * @return
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.EDIT_SCHEME_MASTER_DATA, method = RequestMethod.POST)
    public ModelAndView editSchemeMaster(@RequestParam(MainetConstants.WorksManagement.SCHEME_ID) final Long wmSchId) {
        fileUpload.sessionCleanUpForFileUpload();
        SchemeMasterModel model = this.getModel();
        model.setSaveMode(MainetConstants.WorksManagement.EDIT);

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        SchemeMasterDTO schemeMasterDTO = schemeMasterService.getSchemeMasterBySchemeId(wmSchId);
        populateModel(model);
        List<Long> ids = new ArrayList<>();
        List<SchemeMasterDTO> schemelistDto = schemeMasterService.getSchemeMasterList(null, null, null, orgId);
        for (SchemeMasterDTO dto : schemelistDto) {
            if (dto.getWmSchCodeId2() != null)
                ids.add(dto.getWmSchCodeId2());
        }

        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), schemeMasterDTO.getWmSchCode());
        model.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1, orgId));
        List<LookUp> sourceNames = new ArrayList<>();
        if (schemeMasterDTO.getWmSchCodeId1() != null) {

            List<LookUp> obj = CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            for (LookUp look : obj) {
                if (schemeMasterDTO.getWmSchCodeId1() == look.getLookUpParentId()) {
                    if (!ids.contains(look.getLookUpId()) || schemeMasterDTO.getWmSchCodeId2() == look.getLookUpId())
                        sourceNames.add(look);
                }
            }

            model.setSchemeLookUps(sourceNames);
        }
        model.setAttachDocsList(attachDocs);
        model.setSchemeMasterDTO(schemeMasterDTO);
        return new ModelAndView(MainetConstants.WorksManagement.ADD_SCHEME_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * used for check scheme is associated with project
     * 
     * @param wmSchId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.CHECK_PROJECT, method = RequestMethod.POST)
    public boolean checkSchemeProjectIntegration(
            @RequestParam(MainetConstants.WorksManagement.SCHEME_ID) final Long wmSchId) {
        boolean isDeleted = false;
        SchemeMasterDTO schemeMasterDTO = schemeMasterService.getSchmMastToProject(wmSchId);
        if (schemeMasterDTO.getSchemeProjectlist() == null || schemeMasterDTO.getSchemeProjectlist().isEmpty()) {
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * used for getting Scheme master data display on Scheme Summary
     * 
     * @param wmSchId
     * @param startDate
     * @param endDate
     * @param wmSchNameEng
     * @return status
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_SCHEME_MASTER_GRID_SUMMRY, method = RequestMethod.POST)
    public List<SchemeMasterDTO> SchemeMasterList(
            @RequestParam(MainetConstants.WorksManagement.SOURCE_CODE) final Long sourceCode,
            @RequestParam(MainetConstants.WorksManagement.SOURCE_NAME) final Long sourceName,
            @RequestParam(MainetConstants.WorksManagement.SCHEME_NAME) final String wmSchNameEng) {

        List<SchemeMasterDTO> schemelistDto = schemeMasterService.getSchemeMasterList(sourceCode, sourceName,
                wmSchNameEng, UserSession.getCurrent().getOrganisation().getOrgid());
        return schemelistDto;
    }

    /**
     * used for delete scheme master only set flag Y or N
     * 
     * @param wmSchId
     * @return isDeleted flag
     */

    @RequestMapping(params = MainetConstants.WorksManagement.DELETE_SCHEME_MASTER, method = RequestMethod.POST)
    public @ResponseBody boolean deleteSchemeMaster(
            @RequestParam(MainetConstants.WorksManagement.SCHEME_ID) final Long wmSchId) {
        boolean isDeleted = true;
        /* To check Scheme Associated with project or not */
        SchemeMasterDTO schemeMasterDTO = schemeMasterService.getSchmMastToProject(wmSchId);

        if (schemeMasterDTO.getSchemeProjectlist() == null || schemeMasterDTO.getSchemeProjectlist().isEmpty()) {
            schemeMasterService.deleteSchemeMasterById(wmSchId);
            for (SchemeMasterDTO wmMasterDTO : this.getModel().getSchemeMasterList()) {
                if (wmMasterDTO.getWmSchId().longValue() == wmSchId.longValue()) {
                    this.getModel().getSchemeMasterList().remove(wmMasterDTO);
                    break;
                }
            }
        } else {
            isDeleted = false;
        }

        return isDeleted;

    }

    /**
     * used for View Scheme master and Project Master details association with scheme and show JQGridResponse
     * 
     * @param wmSchId
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_SCHEME_MASTER, method = RequestMethod.POST)
    public ModelAndView viewSchemeMaster(@RequestParam(MainetConstants.WorksManagement.SCHEME_ID) final Long wmSchId) {

        SchemeMasterModel model = this.getModel();
        model.setSaveMode(MainetConstants.WorksManagement.VIEW);

        SchemeMasterDTO schemeMasterDTO = schemeMasterService.getSchemeMasterBySchemeId(wmSchId);
        model.setSchemeMasterDTO(schemeMasterDTO);
        List<WmsProjectMasterDto> masterDTO = schemeMasterService.getSchmMastToProject(wmSchId).getSchemeProjectlist();
        model.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        List<LookUp> sourceNames = new ArrayList<>();
        if (schemeMasterDTO.getWmSchCodeId1() != null) {

            List<LookUp> obj = CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            for (LookUp look : obj) {
                if (schemeMasterDTO.getWmSchCodeId1() == look.getLookUpParentId()) {
                    sourceNames.add(look);
                }
            }
            model.setSchemeLookUps(sourceNames);
        }
        model.setProjectMasterDtos(masterDTO);
        populateModel(model);
        return new ModelAndView(MainetConstants.WorksManagement.VIEW_SCHEME_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * Used to get Default Value of SLI PREFIX
     * 
     * @param model
     */
    private void populateModel(SchemeMasterModel model) {
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
            model.setCpdMode(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE)
                    .getLookUpCode());
            if (model.getCpdMode().equals(MainetConstants.FlagL)) {
                try {
                    model.fundList();
                } catch (final Exception exception) {
                    LOGGER.error("Exception ocours to get fund list from Fund Master" + exception);
                }
            }
        } else {
            model.setCpdMode(null);
        }

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.GET_ALLPROJVIEW)
    public ModelAndView openProjectViewForm(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
            final HttpServletRequest request) {
        bindModel(request);

        request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE, MainetConstants.WorksManagement.SM);

        return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_PROJMASTER + projId);
    }

    @RequestMapping(params = MainetConstants.WorksManagement.SHOW_SCHEMEVIEW, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final SchemeMasterModel schemeModel = this.getModel();
        return new ModelAndView(MainetConstants.WorksManagement.VIEW_SCHEME, MainetConstants.FORM_NAME, schemeModel);
    }

    /**
     * Used to get Scheme Name and Codes By Scheme Fund Source
     * 
     * @param sourceName
     * @param projId
     * @return List<LookUp>
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_SCHEMEFUND, method = RequestMethod.POST)
    public List<LookUp> getSchemeNames(@RequestParam(MainetConstants.WorksManagement.SOURCE_ID) Long sourceId,
            @RequestParam(MainetConstants.WorksManagement.MODE) String mode) {
        List<LookUp> sourceNames = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        if (mode.equals(MainetConstants.FlagC)) {
            List<SchemeMasterDTO> schemelistDto = schemeMasterService.getSchemeMasterList(null, null, null,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            for (SchemeMasterDTO schemeMasterDTO : schemelistDto) {
                if (schemeMasterDTO.getWmSchCodeId2() != null && !ids.contains(schemeMasterDTO.getWmSchCodeId2()))
                    ids.add(schemeMasterDTO.getWmSchCodeId2());
            }
        }
        List<LookUp> obj = CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (this.getModel().getSaveMode() != null && this.getModel().getSaveMode().equals(MainetConstants.FlagE)) {
            for (LookUp look : obj) {
                if (look.getLookUpParentId() == sourceId.longValue()) {

                    if (ids.contains(look.getLookUpId())) {
                        if (this.getModel().getSchemeMasterDTO().getWmSchCodeId2() != null
                                && this.getModel().getSchemeMasterDTO().getWmSchCodeId2() == look.getLookUpId())
                            sourceNames.add(look);
                    } else {
                        sourceNames.add(look);
                    }

                }
            }
        } else {
            for (LookUp lookUp : obj) {
                if (lookUp.getLookUpParentId() == sourceId.longValue()) {
                    if (mode.equals(MainetConstants.FlagC)) {
                        if (!ids.contains(lookUp.getLookUpId()))
                            sourceNames.add(lookUp);
                    } else {
                        sourceNames.add(lookUp);
                    }

                }
            }
        }

        return sourceNames;

    }
}
