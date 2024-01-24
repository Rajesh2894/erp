package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.model.SectionInformationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.UtilitySupportService;

/**
 * @author Pranit.Mhatre
 * @since 01 March, 2014
 */
@Controller
@RequestMapping("/SectionInformation.html")
public class SectionInformationController extends AbstractEntryFormController<SectionInformationModel> {

    private static final Logger LOG = Logger.getLogger(SectionInformationController.class);
    @Autowired
    private ISectionService iSectionService;
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.controller.AbstractController#index()
     */

    UtilitySupportService utilityService = new UtilitySupportService();

    @Override
    @RequestMapping
    public ModelAndView index() {
        getModel().setViewMode(false);
        getModel().setIdformap(MainetConstants.BLANK);
        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "showDoc")
    public ModelAndView QuickLink(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletRequest httpServletRequest) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "HELP_DOCS";
        try {

            getModel().setFilePath(downloadedQuickLinkUrl(downloadLink, outputPath));
        } catch (final Exception e) {
            LOG.error(MainetConstants.SHOWDOC_ERROR, e);
            return new ModelAndView("redirect:/404error.jsp.html");
        }
        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, getModel());

    }

    @RequestMapping(params = "id", method = RequestMethod.POST)
    public void getEmployeeByDeptId(@RequestParam("id") final String id, final HttpServletRequest httpServletRequest) {

    }

    /*
     * @RequestMapping(params = "LIST_SECTION", method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP)
     * public @ResponseBody JQGridResponse<SubLinkMaster> getSectionList(@RequestParam final String page,
     * @RequestParam final String rows, final HttpServletRequest httpServletRequest) { return
     * getModel().paginate(httpServletRequest, page, rows, getModel().getSections()); }
     */

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.controller.AbstractEntryFormController#editForm(long, javax.servlet.http.HttpServletRequest)
     */

    @RequestMapping(params = "editForm", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView editForm(@RequestParam  Long rowId, @RequestParam(required = false) final String page, final HttpServletRequest request) {
        bindModel(request);
        /* Start of code To Fetch link master by page name */        
        if(!page.isEmpty()) {
        		SubLinkMaster subLinkMaster = iSectionService.findSublinksbyename(page);	
        		if(subLinkMaster==null) {
            		getModel().addValidationError(getApplicationSession().getMessage("eip.page.notfound"));  
            		sessionCleanup(request);
            		return super.defaultResult();
            	}else {
            		rowId=subLinkMaster.getId();
            	}
        	}
        /* End of code To Fetch link master by page name */
        if(rowId != null) {
        if (getModel().doAuthorization(rowId)) {
            getModel().editForm(rowId);
            if(!getModel().isNotFound()) {
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                request.setAttribute("pageName", UserSession.getCurrent().getOrganisation().getONlsOrgname() + " - "
                        + getModel().getEntity().getSubLinkNameEn());
            } else {
                request.setAttribute("pageName", UserSession.getCurrent().getOrganisation().getONlsOrgnameMar() + " - "
                        + getModel().getEntity().getSubLinkNameRg());
            }
            String metaDescription = UserSession.getCurrent().getKeywords();
            metaDescription = metaDescription.replaceAll("[^a-zA-Z0-9\\s+\\,+]", "");
            request.setAttribute("metaKeywords", metaDescription);
        } else {
            	getModel().addValidationError(getApplicationSession().getMessage("eip.page.notfound"));            	
        		sessionCleanup(request);
           }
        }} else {                           
        	getModel().addValidationError(getApplicationSession().getMessage("eip.page.notfound"));  
    		sessionCleanup(request);
    		LOG.error("Empty parameters passed");
        }

        return super.defaultResult();

    }

    @RequestMapping(params = "ViewCommitee", method = RequestMethod.POST)
    public ModelAndView addForm(@RequestParam final String prefixvalue, final HttpServletRequest httpServletRequest) {
        final Long id = getModel().getCommitteInfo(prefixvalue);

        editForm(id, httpServletRequest);

        return super.defaultResult();
    }

    public String downloadedQuickLinkUrl(final String existingPath, String outputpath) {
        final String fileName = StringUtility.staticStringAfterChar(MainetConstants.WINDOWS_SLASH, existingPath);

        String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.WINDOWS_SLASH, existingPath);

        directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        FileOutputStream fos = null;
        File file = null;
        try {
            final byte[] image = utilityService.getFile(fileName, directoryPath);

            Utility.createDirectory(Filepaths.getfilepath() + outputpath + MainetConstants.FILE_PATH_SEPARATOR);

            file = new File(Filepaths.getfilepath() + outputpath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

            fos = new FileOutputStream(file);

            fos.write(image);

            fos.close();

        } catch (final HttpClientErrorException fileException) {
            LOG.error(MainetConstants.FILENET_ERROR, fileException);
            return MainetConstants.BLANK;
        }

        catch (final Exception e) {

            LOG.error(MainetConstants.FILENET_ERROR, e);
        } finally {
            try {

                if (fos != null) {
                    fos.close();
                }

            } catch (final IOException e) {
                LOG.error(MainetConstants.EXCEPTION_OCCURED, e);
            }
        }

        outputpath = outputpath + MainetConstants.FILE_PATH_SEPARATOR;

        outputpath = outputpath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);
        if (file != null) {
            return outputpath + file.getName();
        } else {
            return outputpath;
        }

    }

}
