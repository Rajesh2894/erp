package com.abm.mainet.swm.ui.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.ui.model.FileUploadModel;

@Controller
@RequestMapping("/fileUpload.html")
public class FileUploadController extends AbstractFormController<FileUploadModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("fileUpload.html");
        sessionCleanup(httpServletRequest);

        fileUpload.sessionCleanUpForFileUpload();
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final FileUploadModel model = this.getModel();
        String fileName = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                fileName = file.getName();
                break;
            }
        }
        if (fileName == null) {
            model.addValidationError("Please Check Uploaded document");
        }
        if (!model.hasValidationErrors()) {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            }
        } else {
            return defaultMyResult();
        }
        return defaultMyResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchExcelFile", method = { RequestMethod.POST })
    public ModelAndView searchExcelFile(final HttpServletResponse response, final HttpServletRequest request,
            @RequestParam("logbookId") String logbookId) {
        sessionCleanup(request);
        this.getModel().getAttachDocs().setIdfId(logbookId);
        fileUpload.sessionCleanUpForFileUpload();
        FileUploadModel fileUploadmodel = this.getModel();
        List<AttachDocs> uploadFileList = iAttachDocsService
                .findAllDocLikeReferenceId(UserSession.getCurrent().getOrganisation().getOrgid(), logbookId);
        fileUploadmodel.setListAttachDocs(uploadFileList);
        if (uploadFileList.size() == 0) {
            fileUploadmodel.setStatus("Empty");
        }
        return new ModelAndView("FileUploadSummaryForm", MainetConstants.FORM_NAME, fileUploadmodel);
    }

    /*
     * @RequestMapping(params = "deleteDoc", method = { RequestMethod.POST }) public ModelAndView deleteExcelFile(final
     * HttpServletResponse response, final HttpServletRequest request,
     * @RequestParam("logbookId") Long logbookId) { List<Long> enclosureRemoveById = new ArrayList<Long>();
     * enclosureRemoveById.add(logbookId); iAttachDocsService.deleteContractDocFileById(enclosureRemoveById,
     * UserSession.getCurrent().getEmployee().getEmpId()); sessionCleanup(request); fileUpload.sessionCleanUpForFileUpload();
     * FileUploadModel fileUploadmodel = this.getModel(); return new ModelAndView("FileUploadSummaryForm",
     * MainetConstants.FORM_NAME, fileUploadmodel); }
     */

}
