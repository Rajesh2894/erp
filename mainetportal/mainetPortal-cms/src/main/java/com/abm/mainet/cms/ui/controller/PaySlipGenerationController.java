package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.PaySlipGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.dms.utility.UtilitySupportService;

/**
 * @author Jugnu Pandey
 */
@Controller
@RequestMapping("/PaySlipGeneration.html")
public class PaySlipGenerationController extends AbstractController<PaySlipGenerationModel> {
    private static final Logger LOG = Logger.getLogger(PaySlipGenerationController.class);
    UtilitySupportService utilityService = new UtilitySupportService();

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return super.index();
    }

    @RequestMapping(params = "getPaySlip", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView chekker(@RequestParam("fileName") final String file, final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelAndView = null;
        getModel().setAttFname(file + ".pdf");
        String downloadLink = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "PAY_SLIP" + File.separator
                + getModel().getAttFname();
        downloadPaySlip(downloadLink, response);

        modelAndView = new ModelAndView("UploadedPaySlip", MainetConstants.FORM_NAME, getModel());
        return modelAndView;
    }

    private void downloadPaySlip(String downloadLink, HttpServletResponse response) {

        String outputpath = UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + "PAY_SLIP";
        try {

            final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR, downloadLink);

            String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR, downloadLink);

            directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);

            byte[] image = null;
            try {
                image = utilityService.getFile(fileName, directoryPath);

            } catch (final HttpClientErrorException fileException) {
                LOG.error(MainetConstants.FILENET_ERROR, fileException);
            }

            catch (final Exception e) {
                LOG.error(MainetConstants.FILENET_ERROR, e);
            }

            if (image != null) {
                this.getModel().setUploadedPath(outputpath);
            } else {
                this.getModel().setUploadedPath("");
            }
        }

        catch (final Exception ex) {

            LOG.error(MainetConstants.ERROR_OCCURED, ex);

        }

    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        String fileName = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                fileName = file.getName();
                break;
            }
        }

        if (fileName == null) {
            getModel().addValidationError("Please Upload document");
        }
        if (!getModel().hasValidationErrors()) {

            if (getModel().saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
            } else {
                return defaultMyResult();
            }
        }
        return defaultMyResult();
    }

    @Override
    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + "PAY_SLIP";
        try {
            this.getModel().setFilePath(Utility.downloadedFileUrl(downloadLink, outputPath, this.getModel().getFileNetClient()));
        } catch (final Exception ex) {
            LOG.error(MainetConstants.ERROR_OCCURED, ex);
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "CitizenPaySlip", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView citizenPaySlip(final HttpServletRequest request, final HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("CitizenPaySlipGeneration", MainetConstants.FORM_NAME, getModel());
        return modelAndView;
    }
}
