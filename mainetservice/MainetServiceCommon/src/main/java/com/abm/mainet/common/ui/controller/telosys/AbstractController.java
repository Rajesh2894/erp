package com.abm.mainet.common.ui.controller.telosys;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.service.ICommonHelpDocsService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.MessageHelper;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

public class AbstractController {

    protected static final String MODE = "mode";
    protected static final String MODE_CREATE = "create";
    protected static final String MODE_UPDATE = "update";
    protected static final String MODE_EDIT = "edit";
    protected static final String MODE_VIEW = "view";

    protected static final String SAVE_ACTION = "saveAction";

    private final String entityName;
    private final Logger logger;

    @Resource
    protected MessageHelper messageHelper;
    @Resource
    private MessageSource messageSource;

    private final Map<Locale, CustomDateEditor> customDateEditorByLocales = new HashMap<>();

    public AbstractController(final Class<? extends AbstractController> controllerClass, final String entityName) {
        super();
        this.entityName = entityName;
        logger = LoggerFactory.getLogger(controllerClass);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder, final HttpServletRequest request) {
        final Locale locale = RequestContextUtils.getLocale(request);
        binder.registerCustomEditor(Date.class, getCustomDateEditor(locale));
    }

    private CustomDateEditor getCustomDateEditor(final Locale locale) {
        final CustomDateEditor customDateEditor = customDateEditorByLocales.get(locale);
        if (customDateEditor == null) {
            customDateEditorByLocales.put(locale, customDateEditor);
        }
        return customDateEditor;
    }

    protected void log(final String msg) {
        logger.info(msg);
    }

    protected void logContent(final Model model) {
        final Map<String, Object> map = model.asMap();
        logger.info("Model content (size = " + map.size() + ") : ");
        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            logger.info(" . '" + entry.getKey() + "' : " + entry.getValue());
        }
    }

    protected void logSessionContent(final HttpSession session) {
        final Enumeration<String> enumNames = session.getAttributeNames();
        final List<String> names = Collections.list(enumNames);
        logger.info("Session content (size = " + names.size() + ") : ");
        for (final String name : names) {
            logger.info(" . '" + name + "' : " + session.getAttribute(name));
        }
    }

    private static final String URI_SEPARATOR = "/";

    protected String encodeUrlPathSegments(final HttpServletRequest httpServletRequest, final Object... pathSegments) {
        // --- get character encoding
        String characterEncoding = httpServletRequest.getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }

        // --- encode N segments
        final StringBuffer sb = new StringBuffer();
        int n = 0;
        for (final Object segment : pathSegments) {
            n++;
            if (n > 1) {
                sb.append(URI_SEPARATOR);
            }
            // --- encode 1 segment
            try {
                final String encodedSegment = UriUtils.encodePathSegment(segment.toString(), characterEncoding);
                sb.append(encodedSegment);
            } catch (final UnsupportedEncodingException uee) {
                throw new RuntimeException("encodePathSegment error", uee);
            }

        }
        return sb.toString();
    }

    /**
     * Returns "redirect:/entityName"
     * @return
     */
    protected String redirectToList() {
        return "redirect:/" + entityName;
    }

    /**
     * Returns "redirect:/entityName/form/id1/id2/..."
     * @param httpServletRequest
     * @param idParts
     * @return
     */
    protected String redirectToForm(final HttpServletRequest httpServletRequest, final Object... idParts) {
        return "redirect:" + getFormURL(httpServletRequest, idParts);
    }

    /**
     * Returns "/entityName/form/id1/id2/..."
     * @param httpServletRequest
     * @param idParts
     * @return
     */
    protected String getFormURL(final HttpServletRequest httpServletRequest, final Object... idParts) {
        return "/" + entityName;
    }

    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse, final Model model) {
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        try {
            model.addAttribute("downloadPath",
                    Utility.downloadedFileUrl(downloadLink, outputPath, FileNetApplicationClient.getInstance()));
        } catch (final Exception ex) {
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView(MainetConstants.VIEW_DOWNLOAD_FILE_HELP);
    }

    
    public void helpDoc(final String helpDocRefURL,final Model model) {
    	 ICommonHelpDocsService commonHelpDocsService = ApplicationContextProvider.getApplicationContext()
                 .getBean(ICommonHelpDocsService.class);
   	  CommonHelpDocs commonHelpDocs=commonHelpDocsService.getUploadedFileByDept(helpDocRefURL, UserSession.getCurrent().getOrganisation());
   	model.addAttribute("helpdocFile", commonHelpDocs);
    }

    
    @RequestMapping(method = RequestMethod.GET, params = { "ShowHelpDoc", "helpDocRefURL" })
    public ModelAndView globalHelpDocs(final HttpServletRequest httpServletRequest, final Model model,
            final String helpDocRefURL) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.HelpDoc.HELPDOC;
        ICommonHelpDocsService commonHelpDocsService = ApplicationContextProvider.getApplicationContext()
                .getBean(ICommonHelpDocsService.class);
        final CommonHelpDocs docs = commonHelpDocsService.getUploadedFileByDept(helpDocRefURL,
                UserSession.getCurrent().getOrganisation());
        FileNetApplicationClient filenetClient = FileNetApplicationClient.getInstance();
        if ((docs != null) && (docs.getModuleName() != null) && !docs.getModuleName().equalsIgnoreCase(MainetConstants.BLANK)) {
            try {
                String url = null;
                if (UserSession.getCurrent().getLanguageId() == 1) {
                    url = Utility.downloadedFileUrl(
                            docs.getFilePath() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameEng(), outputPath,
                            filenetClient);
                } else if ((UserSession.getCurrent().getLanguageId() == 2) && (docs.getFileNameReg() != null)
                        && !docs.getFileNameReg().equalsIgnoreCase(MainetConstants.BLANK)) {
                    url = Utility.downloadedFileUrl(
                            docs.getFilePathReg() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameReg(),
                            outputPath, filenetClient);
                } else {
                    url = Utility.downloadedFileUrl(
                            docs.getFilePath() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameEng(), outputPath,
                            filenetClient);
                }
                model.addAttribute("helpDocPath", url);
            } catch (final Exception f) {
                logger.error(MainetConstants.ERROR_OCCURED, f);
                return new ModelAndView("redirect:/404error.jsp.html");
            }
            return new ModelAndView("viewHelp");
        } else {
            /*
             * if (this.getModel().getBeanName().equals(MainetConstants.ValidationMessageCode.FOR_CITI_REGISTRATION)) {
             * return new ModelAndView("redirect:/notFoundFile.html");
             * } else {
             */
            return new ModelAndView("viewHelpNotFound");

            /* } */

        }
    }

}
