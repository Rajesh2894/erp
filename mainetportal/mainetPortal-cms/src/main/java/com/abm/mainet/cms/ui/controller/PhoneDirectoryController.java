package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.dto.PhoneDirectoryDTO;
import com.abm.mainet.cms.dto.ReadExcelData;
import com.abm.mainet.cms.ui.model.PhoneDirectoryModel;
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
@RequestMapping("/PhoneDirectory.html")
public class PhoneDirectoryController extends AbstractController<PhoneDirectoryModel> {
    private static final Logger LOG = Logger.getLogger(PhoneDirectoryController.class);
    UtilitySupportService utilityService = new UtilitySupportService();

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return super.index();
    }

 
	@RequestMapping(params = "getPhoneDirectory", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView chekker(final HttpServletRequest request,final Model uiModel,
            final HttpServletResponse response) throws IOException {
        getModel().setAttFname(MainetConstants.PHONE_DIRECTORY_NAME + ".xlsx");
        String downloadLink = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "PHONE_DIRECTORY" + File.separator
                + getModel().getAttFname();
        downloadPaySlip(downloadLink, response);
        final String filePath = getApplicationSession().getMessage("upload.physicalPath")+ MainetConstants.FILE_PATH_SEPARATOR+downloadLink;
		try {
			ReadExcelData<PhoneDirectoryDTO> data = new ReadExcelData<>(filePath,
					PhoneDirectoryDTO.class);
			data.parseExcelList();
	        
			if (data.getParseData().isEmpty()) {
				 LOG.error("Data cannot be read");
            }else {
            	uiModel.addAttribute(MainetConstants.PHONE_DIRECTORY, data);
            }
		} catch (Exception e) {
			LOG.error(MainetConstants.ERROR_OCCURED + " at " + filePath ,e);
		}
        return new ModelAndView("UploadedPhoneDirectory", MainetConstants.FORM_NAME, getModel());
    }

    private void downloadPaySlip(String downloadLink, HttpServletResponse response) {

        String outputpath = UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + "PHONE_DIRECTORY";
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
        if(!fileName.equals("Phone_Diretory.xlsx")) {
        	getModel().addValidationError("File name should be as Phone_Diretory!! Remove the previously uploaded file and Upload a new file");
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
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + "PHONE_DIRECTORY";
        try {
            this.getModel().setFilePath(Utility.downloadedFileUrl(downloadLink, outputPath, this.getModel().getFileNetClient()));
        } catch (final Exception ex) {
            LOG.error(MainetConstants.ERROR_OCCURED, ex);
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "OpenPhoneDirectory", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView citizenPaySlip(final HttpServletRequest request, final HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("OpenPhoneDirectory", MainetConstants.FORM_NAME, getModel());
        return modelAndView;
    }
}
