package com.abm.mainet.common.ui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
public abstract class AbstractController<TModel extends AbstractModel> {

    private final Class<TModel> modelClass;
    private final String viewName;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DmsService dmsService;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        AbstractModel.registerCustomEditors(binder);
    }

    @SuppressWarnings("unchecked")
    public AbstractController() {
        this.modelClass = (Class<TModel>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        final String controllerType = this.getClass().getSimpleName();
        this.viewName = controllerType.substring(0, controllerType.lastIndexOf("Controller"));

    }

    protected String getViewName() {
        return viewName;
    }

    /**
     * @return
     */
    protected ApplicationSession getApplicationSession() {
        return (ApplicationSession) ApplicationContextProvider.getApplicationContext().getBean("applicationSession");
    }

    /**
     * @return
     */
    public TModel getModel() {
        return ApplicationContextProvider.getApplicationContext().getBean(this.modelClass);
    }

    public BindingResult bindModel(final HttpServletRequest request) {
        return getModel().bind(request);
    }

    /**
     * @return
     */
    public ModelAndView index() {
        return defaultResult();
    }

    protected ModelAndView jsonResult(final Object bean) {
        ModelAndView mv;

        if (bean == null) {
            mv = new ModelAndView(new MappingJackson2JsonView());
        } else {
            mv = new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, bean);
        }

        return mv;
    }

    protected ModelAndView getResubmissionView() {
        final String resubmissionName = viewName + "Resubmission";

        return new ModelAndView(resubmissionName, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @return
     */
    protected ModelAndView defaultResult() {
        final TModel model = getModel();

        model.getAppSession().setLangId(UserSession.getCurrent().getLanguageId());

        final ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, model);

        final BindingResult bindingResult = model.getBindingResult();

        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    protected ModelAndView defaultMyResult() {
        final TModel model = getModel();

        model.getAppSession().setLangId(UserSession.getCurrent().getLanguageId());

        final ModelAndView mv = new ModelAndView(viewName + MainetConstants.VALIDN_SUFFIX, MainetConstants.FORM_NAME, model);

        final BindingResult bindingResult = model.getBindingResult();

        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    protected ModelAndView customDefaultMyResult(final String viewName) {
        final TModel model = getModel();

        model.getAppSession().setLangId(UserSession.getCurrent().getLanguageId());

        final ModelAndView mv = new ModelAndView(viewName + MainetConstants.VALIDN_SUFFIX, MainetConstants.FORM_NAME, model);

        final BindingResult bindingResult = model.getBindingResult();

        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    protected final void sessionCleanup(final HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession().removeAttribute(getModel().getBeanName());
        }

    }

    @RequestMapping(params = "FileUpload", method = RequestMethod.POST)
    public @ResponseBody String uploadDocument(@RequestParam("files") final List<MultipartFile> multipartFiles) {
        for (final MultipartFile file : multipartFiles) {
            getModel().getMultipartFiles().add(file);
        }

        return getModel().getUploadedFiles();
    }

    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse) {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + date
                + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        try {
            this.getModel().setFilePath(Utility.downloadedFileUrl(downloadLink, outputPath, this.getModel().getFileNetClient()));
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * To get message text for given message template code.
     * @param msgTemplate the {@link String} literal containing message template code.
     * @return {@link String} object which containing actual text message.
     */
    public final String getMessageText(final String msgTemplate) {
        final TModel model = this.getModel();
        return model.getAppSession().getMessage(msgTemplate);
    }

    @RequestMapping(params = "locale", method = RequestMethod.GET)
    public String welcome(@RequestParam("lang") final String requestLang, @RequestParam("url") final String url,
            final HttpServletRequest request, final HttpServletResponse response) {
        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(requestLang));
        UserSession.getCurrent().setLanguageId(getLanguage(requestLang));
        String finalurl = url.replace("mainetgw9Qk", "&");
        return "redirect:" + finalurl;
    }

    private int getLanguage(final String language) {
        if (MainetConstants.DEFAULT_LOCAL_REG_STRING.equals(language)) {
            return 2;
        } else if (MainetConstants.DEFAULT_LOCALE_STRING.equals(language)) {
            return 1;
        } else {
            return 1;
        }
    }

    @RequestMapping(params = "reset", method = RequestMethod.POST)
    protected @ResponseBody JsonViewObject resetForm(final HttpServletRequest httpServletRequest) {
        try {
            this.sessionCleanup(httpServletRequest);

            FileUploadUtility.getCurrent().getFileMap().clear();
            FileUploadUtility.getCurrent().getFileUploadSet().clear();
            FileUploadUtility.getCurrent().setFolderCreated(false);

            return JsonViewObject.successResult();
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return JsonViewObject.failureResult(ex);
        }
    }

    @RequestMapping(params = "ResubmissionApplication", method = RequestMethod.POST)
    public ModelAndView ResubmissionApplication(@RequestParam("menuparams") final List<String> menuparams,
            @RequestParam("applId") final long applId,
            @RequestParam("filterType") final String filterType, final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        getModel().setApmApplicationId(applId);

        getModel().setFilterType(filterType);

        getModel().viewResubmissionApplication(applId, menuparams);

        getModel().getDataForResubmission(applId, menuparams.get(0));

        return this.getResubmissionView();

    }

    @RequestMapping(params = "getMessage", method = RequestMethod.POST)
    public @ResponseBody String getMessage(@RequestParam("key") final String key, final HttpServletRequest request,
            final HttpServletResponse response) {
        return getApplicationSession().getMessage(key);
    }

    @RequestMapping(method = RequestMethod.GET, params = "ShowHelpDoc")
    public ModelAndView GlobalHelpDocs(final HttpServletRequest httpServletRequest) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.HelpDoc.HELPDOC;
        final CommonHelpDocs docs = this.getModel().getCommonHelpDoc();
        if ((docs != null) && (docs.getModuleName() != null) && !docs.getModuleName().equalsIgnoreCase(MainetConstants.BLANK)) {
            try {
                if (UserSession.getCurrent().getLanguageId() == 1) {
                    this.getModel()
                            .setFilePath(Utility.downloadedFileUrl(
                                    docs.getFilePath() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameEng(), outputPath,
                                    this.getModel().getFileNetClient()));
                } else if ((UserSession.getCurrent().getLanguageId() == 2) && (docs.getFileNameReg() != null)
                        && !docs.getFileNameReg().equalsIgnoreCase(MainetConstants.BLANK)) {
                    this.getModel()
                            .setFilePath(Utility.downloadedFileUrl(
                                    docs.getFilePathReg() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameReg(),
                                    outputPath, this.getModel().getFileNetClient()));
                } else {
                    this.getModel()
                            .setFilePath(Utility.downloadedFileUrl(
                                    docs.getFilePath() + MainetConstants.FILE_PATH_SEPARATOR + docs.getFileNameEng(), outputPath,
                                    this.getModel().getFileNetClient()));
                }
            } catch (final Exception f) {
                logger.error(MainetConstants.ERROR_OCCURED, f);
                return new ModelAndView("redirect:/404error.jsp.html");
            }
            return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
        } else {
            if (this.getModel().getBeanName().equals(MainetConstants.ValidationMessageCode.FOR_CITI_REGISTRATION)) {
                return new ModelAndView("redirect:/notFoundFile.html");
            } else {
                return new ModelAndView("viewHelpNotFound", MainetConstants.FORM_NAME, this.getModel());

            }

        }

    }

    @RequestMapping(method = RequestMethod.GET, params = "ShowPolicy")
    public ModelAndView GlobalPolicy(final HttpServletRequest httpServletRequest) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "POLICY";
        this.getModel().setFilePath(Utility.downloadedFileUrl(UserSession.getCurrent().getOrganisation().getOrgid() + "\\POLICY"
                + MainetConstants.FILE_PATH_SEPARATOR + "Policy.pdf", outputPath, this.getModel().getFileNetClient()));
        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getFilePath", method = RequestMethod.POST)
    public @ResponseBody String getFilePath(final HttpServletRequest httpServletRequest) {
        return this.getModel().getFilePath();
    }

    protected ModelAndView defaultExceptionView() {
        return new ModelAndView("defaultExceptionView", MainetConstants.FORM_NAME, this.getModel());
    }

    protected ModelAndView defaultExceptionFormView() {
        return new ModelAndView("defaultExceptionFormView", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getToplevelTryList", method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP)
    @ResponseBody
    public LookUp getFirstAndSecondLevelList(@RequestParam(value = "level") final int level) {

        final List<LookUp> list = new ArrayList<>();
        final List<LookUp> lookUpList = CommonMasterUtility.getSecondLevelData(PrefixConstants.Common.TRY, level);
        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getDefaultVal().equalsIgnoreCase(MainetConstants.IsLookUp.STATUS.YES)) {
                list.add(lookUp);
            }
        }
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    /*
     * @RequestMapping(params = "getTryList", method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP)
     * public @ResponseBody List<LookUp> getDistrictList(@RequestParam(value = "term") final String term,
     * @RequestParam(value = "parentId") final String parentId) { final List<LookUp> list = new ArrayList<>(); try { final
     * List<LookUp> lookupList = CommonMasterUtility.getChildLookUpsFromParentId(Long.parseLong(parentId)); for (final LookUp
     * lookUp : lookupList) { if (lookUp.getDescLangFirst().matches("(?i)^" + term + ".*$")) { list.add(lookUp); } } } catch
     * (final NumberFormatException e) { logger.error(MainetConstants.ERROR_OCCURED, e); return null; } return list; }
     */

    @RequestMapping(method = RequestMethod.POST, params = "encrypted")
    public @ResponseBody String getEncrypted(@RequestParam("plainText") final String plainText)
            throws IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalStateException, BadPaddingException {
        final String encryptedData = this.getModel().encryptData(plainText);
        return encryptedData;
    }

    @RequestMapping(method = RequestMethod.POST, params = "decrypted")
    public @ResponseBody Double getDecrypted(@RequestParam("plainText") final String plainText)
            throws IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalStateException, BadPaddingException {
        final String decryptedData = this.getModel().decryptData(plainText);
        return Double.parseDouble(decryptedData);
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode, browserType);
        return jsonViewObject;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        JsonViewObject jsonViewObject = JsonViewObject.successResult();
        jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
        return jsonViewObject;
    }

    /**
     * This method get all the look up&acute;s sub information object&acute;s for the given parent code,id and isAlphaNumeric.
     * @param typeCode {@link String} literal containing parent code.
     * @param typeId {@link String} literal containing parent id.
     * @param isAlphaNumeric {@link String} literal containing alphanumericFlag.
     * @return {@link List} of {@link LookUp} objects.
     */
    @RequestMapping(params = "getSubAlphanumericSortInfo", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final List<LookUp> getSubAlphanumericSortInfo(@RequestParam final String typeCode,
            @RequestParam final long typeId, @RequestParam final String isAlphaNumeric) {
        final List<LookUp> lookup = getApplicationSession().getChildLookUpsFromParentId(typeId);
        if ((lookup != null) && !lookup.isEmpty()) {
            if ((isAlphaNumeric != null) && isAlphaNumeric.equals(MainetConstants.IsLookUp.STATUS.YES)) {
                Collections.sort(lookup, LookUp.alphanumericComparator);
            } else {
                Collections.sort(lookup);
            }

        }
        return lookup;
    }

    @RequestMapping(params = "showErrorPage", method = RequestMethod.POST)
    public ModelAndView showErrorPage(final HttpServletRequest httpServletRequest, final Exception exception) {

        logger.error(MainetConstants.EXCEPTION_OCCURED, exception);

        ModelAndView modelAndView;
        modelAndView = new ModelAndView(
                MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW,
                MainetConstants.FORM_NAME, getModel());

        return modelAndView;
    }

    @RequestMapping(params = "DownloadFile", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("docId") final String docId, @RequestParam("docName") final String docName,
            final HttpServletResponse httpServletResponse) throws Exception {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS"
                + MainetConstants.FILE_PATH_SEPARATOR;

        final byte[] byteArray = dmsService.getDocumentById(docId);
        if (byteArray == null) {
            throw new Exception("webservice error");
        }

        Utility.createDirectory(Filepaths.getfilepath() + outputPath);

        outputPath = outputPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);
        outputPath = outputPath + docName;
        final Path path = Paths.get(Filepaths.getfilepath() + outputPath);
        java.nio.file.Files.write(path, byteArray);

        this.getModel().setFilePath(outputPath);

        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getUploadedImage", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final List<String> getdataOfUploadedImage(final HttpServletRequest httpServletRequest,
            final long fileIndex) {
        List<String> fileNameList = new ArrayList<>();
        try {
            if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if (entry.getKey() != null && entry.getKey() == fileIndex) {
                        for (final File file : entry.getValue()) {
                            String fileName = null;
                            try {
                                final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                                        MainetConstants.operator.FORWARD_SLACE);
                                fileName = path.replace(Filepaths.getfilepath(), "");
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                            fileNameList.add(fileName + "_" + file.length() / 1024 + "KB");
                            return fileNameList;
                        }
                    }

                }
            }
            return fileNameList;
        } catch (final Exception e) {

            logger.error(MainetConstants.ERROR_OCCURED, e);
        }
        return fileNameList;
    }

    public boolean getMakerCheckerFlag() {
    	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            return true;
        } else {
        	return false;
        }
    	
    }
    
    //changes for captcha
    
    @RequestMapping(method = RequestMethod.GET, params = "captcha")
    public void GlobalPolicy(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
            @RequestParam final String id) {


        final ApplicationSession session = ApplicationSession.getInstance();
        final int width = 150;
        final int height = 50;
        char[] alphNum;
        try {
            final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            final Graphics2D g2d = bufferedImage.createGraphics();
            final Font font = new Font("Georgia", Font.BOLD, 20);
            g2d.setFont(font);
            final RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHints(rh);
            final GradientPaint gp = new GradientPaint(0, 0,
                    Color.LIGHT_GRAY, 0, height / 2, Color.white, true);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(new Color(51, 51, 51));
            if (session.getMessage("captcha.enable").equalsIgnoreCase(MainetConstants.MENU.FALSE)) {
                alphNum = MainetConstants.NO_CAPTCHA_GENERATE_VALUE.toCharArray();
            } else {
                alphNum = MainetConstants.CAPTCHA_GENERATE_VALUE.toCharArray();
            }
            final Random rnd = new Random();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(alphNum[rnd.nextInt(alphNum.length)]);
            }
            final String generatedCaptcha = sb.toString();
            httpServletRequest.getSession().setAttribute("captcha", generatedCaptcha);
            final char[] charArray = generatedCaptcha.toCharArray();
            int x = 0;
            int y = 0;
            for (int i = 0; i < charArray.length; i++) {
                x += 10 + (Math.abs(rnd.nextInt()) % 15);
                y = 20 + (Math.abs(rnd.nextInt()) % 20);
                g2d.drawChars(charArray, i, 1, x, y);
            }
            g2d.dispose();
            response.setContentType(MainetConstants.GrievanceConstants.CONTENT_TYPE_PNG);
            final OutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, MainetConstants.GrievanceConstants.PNG, os);
            os.close();

        } catch (final IOException e) {
            logger.error(e);
        }
    
    }
    
    @RequestMapping(params = "getdataOfUploadedImageWithThumbNail", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final String getdataOfUploadedImageWithThumbNail(final HttpServletRequest httpServletRequest) {
        try {

            if ((FileUploadUtility.getCurrent().getFileMap() != null)
                    && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if ((entry.getKey() != null) && (entry.getKey().longValue() == 0)) {
                        for (final File file : entry.getValue()) {
                            String fileName = null;
                            fileName = getPath(file, fileName);

                            return fileName;
                        }
                    }

                }
            }
            return MainetConstants.BLANK;
        } catch (final Exception e) {

        	logger.error(MainetConstants.ERROR_OCCURED, e);
        }
        return MainetConstants.BLANK;
    }

    private String getPath(final File file, String fileName) {
        try {
            final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                    MainetConstants.operator.FORWARD_SLACE);
            fileName = path.replace(Filepaths.getfilepath(), org.apache.commons.lang.StringUtils.EMPTY);
        } catch (final Exception e) {
        	logger.error(MainetConstants.ERROR_OCCURED, e);
        }
        return fileName;
    }
}