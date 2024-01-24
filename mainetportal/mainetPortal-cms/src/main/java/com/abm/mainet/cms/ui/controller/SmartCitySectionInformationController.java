package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.IVIEWQuickLinkService;
import com.abm.mainet.cms.ui.model.SectionInformationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.client.FileNetApplicationClient;

@Controller
@RequestMapping("smartcity.html")
public class SmartCitySectionInformationController extends AbstractEntryFormController<SectionInformationModel> {

    private static final Logger LOG = Logger.getLogger(SmartCitySectionInformationController.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.controller.AbstractController#index()
     */
    @RequestMapping
    public ModelAndView index(final HttpServletRequest request) {
        final CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

        getModel().setViewMode(false);

        final IVIEWQuickLinkService iviewQuickLinkService = ApplicationContextProvider.getApplicationContext()
                .getBean(IVIEWQuickLinkService.class);
        try {
            UserSession.getCurrent().setQuickLinkEng(
                    iviewQuickLinkService.getQuickLinkforSamrtcity(UserSession.getCurrent().getOrganisation(), token));
        } catch (final Exception e) {
            throw new FrameworkException(e);

        }
        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "showDoc")
    public ModelAndView QuickLink(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletRequest httpServletRequest) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "HELP_DOCS";
        try {

            getModel().setFilePath(downloadedQuickLinkUrl(downloadLink, outputPath, getModel().getFileNetClient()));
        } catch (final Exception e) {
            LOG.error(MainetConstants.SHOWDOC_ERROR, e);
            return new ModelAndView("redirect:/404error.jsp.html");
        }
        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, getModel());

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
    @Override
    public ModelAndView editForm(final long rowId, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().editForm(rowId);
        return new ModelAndView("SmartCitySectionInformationDetail", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ViewCommitee", method = RequestMethod.POST)
    public ModelAndView addForm(@RequestParam final String prefixvalue, final HttpServletRequest httpServletRequest) {
        final Long id = getModel().getCommitteInfo(prefixvalue);

        editForm(id, httpServletRequest);

        return super.defaultResult();
    }

    public String downloadedQuickLinkUrl(final String existingPath, String outputpath,
            final FileNetApplicationClient fileNetApplicationClient) {
        final String fileName = StringUtility.staticStringAfterChar(MainetConstants.WINDOWS_SLASH, existingPath);

        String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.WINDOWS_SLASH, existingPath);

        directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        FileOutputStream fos = null;
        File file = null;
        try {

            final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

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
                LOG.error(MainetConstants.ERROR_OCCURED, e);
            }
        }

        outputpath = outputpath + MainetConstants.FILE_PATH_SEPARATOR;

        outputpath = outputpath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);

        return outputpath + file.getName();

    }

}
