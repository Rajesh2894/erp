package com.abm.mainet.rnl.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.TenantMaster;
import com.abm.mainet.rnl.dto.TenantMasterGrid;
import com.abm.mainet.rnl.service.ITenantService;
import com.abm.mainet.rnl.ui.model.TenantMasterModel;

/**
 * @author ritesh.patil
 *
 */
@RequestMapping("/TenantMaster.html")
@Controller
public class TenantMasterController extends AbstractFormController<TenantMasterModel> {

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private ITenantService iTenantService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @RequestMapping(method = RequestMethod.POST)
    public String index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        clearFileSession();
        return MainetConstants.TenantMasters.TENANT_MAST_LIST;
    }

    /**
     * Shows a form page in order to create a new Estate
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "tntId", required = false) final Long tntId,
            @RequestParam(value = "type", required = false) final String modeType) {

        final TenantMasterModel tenantMasterModel = getModel();
        populateModel(tntId, tenantMasterModel, modeType);
        return new ModelAndView(MainetConstants.TenantMasters.TENANT_MAST_FORM, MainetConstants.FORM_NAME, tenantMasterModel);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbEstateMaster
     */
    private void populateModel(final Long tntId, final TenantMasterModel tenantMasterModel, final String modeType) {

        if (tntId == null) {
            tenantMasterModel.setTenantMaster(new TenantMaster());
            tenantMasterModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
        } else {
            tenantMasterModel.setTenantMaster(iTenantService.findById(tntId));
            final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    tenantMasterModel.getTenantMaster().getCode());
            tenantMasterModel.setDocumentList(attachDocs);
            if (MainetConstants.RnLCommon.MODE_VIEW.equals(modeType)) {
                tenantMasterModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
                tenantMasterModel.getTenantMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_VIEW);
            } else {
                tenantMasterModel.setModeType(MainetConstants.RnLCommon.MODE_EDIT);
                tenantMasterModel.getTenantMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_EDIT);
                addInMap(attachDocs);
            }
        }
    }

    /**
     * Get Estate Grid data
     * @param request
     * @return
     */
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {

        final List<TenantMasterGrid> tenantMasterGrids = iTenantService
                .findAllOrgTenantRecords(UserSession.getCurrent().getOrganisation().getOrgid());
        if (tenantMasterGrids != null) {
            for (final TenantMasterGrid tenantMasterGrid : tenantMasterGrids) {
                tenantMasterGrid.setTypeName(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(tenantMasterGrid.getType()).getLookUpDesc());
            }
        }
        return getModel().paginate(httpServletRequest, page, rows, tenantMasterGrids);
    }

    @ResponseBody
    @RequestMapping(params = "deleteTenant", method = RequestMethod.POST)
    public boolean deActiveTenantId(@RequestParam("tenantId") final Long tenantId) {
        iTenantService.deleteRecord(tenantId, UserSession.getCurrent().getEmployee().getEmpId());
        return true;
    }

    @Override
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final String fileCode, @RequestParam final String browserType) {
        final JsonViewObject jsonViewObject = super.uploadDocument(httpServletRequest, response, fileCode, browserType);
        if ((getModel().getModeType() == MainetConstants.RnLCommon.MODE_EDIT) && jsonViewObject.isStatus()) {
            jsonViewObject.setMessage(getUploadedMessgeString(jsonViewObject.getMessage()));
        }
        return jsonViewObject;
    }

    @Override
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        List<JsonViewObject> result = new ArrayList<>();
        ;
        if (getModel().getDocumentList() != null) {
            final Map<Long, String> fileNames = new LinkedHashMap<>();
            for (final AttachDocs attachDocs : getModel().getDocumentList()) {
                fileNames.put(attachDocs.getAttId(), attachDocs.getAttFname());
            }
            result = FileUploadUtility.getCurrent().getFileUploadListWithUniqueId(fileNames);
        }
        return result;
    }

    @Override
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType, @RequestParam final Long uniqueId) {
        if (uniqueId != null) {
            getModel().getIds().add(uniqueId);
        }
        final JsonViewObject jsonViewObject = super.doFileDeletion(fileId, httpServletRequest, browserType, uniqueId);
        jsonViewObject.setMessage(getUploadedMessgeString(jsonViewObject.getMessage()));
        return jsonViewObject;
    }

    private void clearFileSession() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
    }

    private void addInMap(final List<AttachDocs> attachDocs) {

        final String guidRanDNum = Utility.getGUIDNumber();
        if (null != FileNetApplicationClient.getInstance()) {
            for (final AttachDocs singleAttachment : attachDocs) {
                fileUploadService.downLoadFilesFromServer(singleAttachment, guidRanDNum, FileNetApplicationClient.getInstance());

            }
            FileUploadUtility.getCurrent().setExistingFolderPath(
                    Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                            + MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR);
        }

    }

    private String getUploadedMessgeString(final String message) {

        final Map<Long, String> fileNames = new LinkedHashMap<>();
        final List<String> existDocs = new ArrayList<>();
        if (getModel().getDocumentList() != null) {
            for (final AttachDocs attachDocs : getModel().getDocumentList()) {
                fileNames.put(attachDocs.getAttId(), attachDocs.getAttFname());
                existDocs.add(FileUploadUtility.getCurrent().generateValidString(attachDocs.getAttFname()));
            }
        }

        File file = null;
        Iterator<File> setFilesItr = null;
        final List<String> allDocs = new ArrayList<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            new ArrayList<>(entry.getValue());
            setFilesItr = entry.getValue().iterator();
            while (setFilesItr.hasNext()) {
                file = setFilesItr.next();
                allDocs.add(FileUploadUtility.getCurrent().generateValidString(file.getName()));
            }
        }

        final StringBuilder b = new StringBuilder("<ul>");
        String text = message;
        text = text.replaceAll("</li>", "").replaceAll("</ul>", "");
        final String[] results = text.split("<li>");
        boolean flag = true;
        for (final String string : existDocs) {
            final Set<Entry<Long, String>> ent = fileNames.entrySet();
            for (final Entry<Long, String> entry : ent) {
                // The first one is empty, remove it
                for (int i = 1; i < results.length; i++) {
                    if (string.equals(FileUploadUtility.getCurrent().generateValidString(entry.getValue()))) {
                        if (flag) {
                            b.append("<li>" + results[i] + "<li>");
                            flag = false;
                        }
                        if (results[i].contains(string)) {
                            b.append("<li>" + results[i] + "<input type='hidden' value='" + entry.getKey() + "'></li>");
                            break;
                        }
                    }
                }
            }
        }
        allDocs.removeAll(existDocs);

        for (final String string : allDocs) {
            for (int i = 1; i < results.length; i++) {
                if (results[i].contains(string)) {
                    b.append("<li>" + results[i] + "<input type='hidden' value=''></li>");

                    break;
                }
            }
        }
        b.append("</ul>");

        return b.toString();
    }

}
