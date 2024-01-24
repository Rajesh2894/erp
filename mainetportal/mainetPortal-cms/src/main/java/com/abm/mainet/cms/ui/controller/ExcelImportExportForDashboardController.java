package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.IExcelImportExportForDashboardService;
import com.abm.mainet.cms.ui.model.ExcelImportExportForDashboardModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author Jugnu Pandey
 *
 * 
 *
 */
@Controller
@RequestMapping("/ExcelImportExportForDashboard.html")
public class ExcelImportExportForDashboardController extends AbstractEntryFormController<ExcelImportExportForDashboardModel>
        implements Serializable {
    private static final long serialVersionUID = 8637711314484219250L;
    private static final Logger logger = Logger.getLogger(ExcelImportExportForDashboardController.class);
    @Autowired
    private IExcelImportExportForDashboardService iExcelImportExportForDashboardService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return super.index();
    }

    @RequestMapping(params = "getExcelDocument", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView chekker(@RequestParam("scheme") final String scheme, final HttpServletRequest request) {
        ModelAndView modelAndView = null;
        getModel().setAttachDocs(iExcelImportExportForDashboardService.getExcelDocument(scheme,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        getModel().setDocName(getModel().getAttachDocs().getAttFname());
        getModel().setAttDate(getModel().getAttachDocs().getAttDate());
        modelAndView = new ModelAndView("UploadedSchemeDocument", MainetConstants.FORM_NAME, getModel());
        return modelAndView;
    }

    @Override
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ExcelImportExportForDashboardModel model = getModel();
        String fileName = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                fileName = file.getName();
                break;
            }
        }
        if (model.getAttachDocs().getAttDate() == null) {
            model.addValidationError("Attach Date cannot be empty");
        }
        if (model.getAttachDocs().getIdfId().equalsIgnoreCase("0")) {
            model.addValidationError("Please select Scheme");
        }
        if (fileName == null) {
            model.addValidationError("Please Uploaded document");
        }
        if ((model.getDocName() != null && fileName != null && !model.getDocName().equalsIgnoreCase(fileName))
                && (model.getAttDate() != null && model.getAttachDocs().getAttDate() != null
                        && Utility.dateToString(
                                model.getAttDate())
                                .equalsIgnoreCase(Utility.dateToString(model.getAttachDocs().getAttDate())))) {
            model.addValidationError("Uploaded document is different from downloaded template");
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

    @SuppressWarnings("rawtypes")
    @RequestMapping(params = "getData", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String getData(@RequestParam("scheme") final String scheme,
            @RequestParam("attachDt") final String attachDt,
            final HttpServletRequest request) {
        Class className = null;
        className = getModel().getClassName(scheme);
        if (className != null) {
            Date date = Utility.stringToDate(attachDt);
            getModel().setList(iExcelImportExportForDashboardService.getData(className, date));

            if (getModel().getList() != null && getModel().getList().size() > 0) {
                return "Failure";
            }
            return "success";
        }
        return "null";
    }

}
