package com.abm.mainet.swm.ui.controller;

import java.util.List;

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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.ui.model.FileDownLoadModel;

@Controller
@RequestMapping("/filedownload.html")
public class FileDownLoadController extends AbstractFormController<FileDownLoadModel> {
    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("filedownload.html");
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "searchExcelFile", method = { RequestMethod.POST })
    public ModelAndView searchExcelFile(final HttpServletResponse response, final HttpServletRequest request,
            @RequestParam("logbookId") String logbookId) {
        sessionCleanup(request);
        this.getModel().getAttachDocs().setIdfId(logbookId);
        FileDownLoadModel fileDownloadmodel = this.getModel();
        List<AttachDocs> doewnloadFileList = iAttachDocsService
                .findAllDocLikeReferenceId(UserSession.getCurrent().getOrganisation().getOrgid(), logbookId);
        fileDownloadmodel.setListAttachDocs(doewnloadFileList);
        return new ModelAndView("FileDownlodaSummaryForm", MainetConstants.FORM_NAME, fileDownloadmodel);
    }

}
