package com.abm.mainet.common.ui.controller;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.ActionResponse;

/**
 * @author Pranit.Mhatre
 * @param <TModel>
 */
public abstract class AbstractEntryFormController<TModel extends AbstractEntryFormModel<? extends BaseEntity>>
        extends AbstractController<TModel> {
    protected Logger logger = Logger.getLogger(this.getClass());

    private String fileList;

    protected String onSave() {
        return getViewName();
    }

    @RequestMapping(params = "add", method = RequestMethod.POST)
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        getModel().addForm();

        return super.defaultResult();
    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        try {
            if (getModel().doAuthorization(rowId)) {
                getModel().editForm(rowId);
            } else {
                getModel().addValidationError(ApplicationSession.getInstance().getMessage("abstract.entry.form.auth.user"));
            }
        } catch (final Throwable ex) {

            logger.error("", ex);

            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return super.defaultResult();

    }

    @RequestMapping(params = "viewMode", method = RequestMethod.POST)
    public ModelAndView viewForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        if (getModel().doAuthorization(rowId)) {
            getModel().viewForm(rowId);
        } else {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("abstract.entry.form.auth.user"));
        }
        return super.defaultResult();

    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final TModel model = getModel();

        try {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            }
        } catch (final Throwable ex) {

            final StringWriter errors = new StringWriter();

            errors.toString();

            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return defaultResult();
    }

    /**
     * To delete the selected row id record.
     *
     * @param rowId the long literal containing row id.
     * @param httpServletRequest the {@link HttpServletRequest} request object provided by servlet container.
     * @return {@link JsonViewObject} object which containing status and response message.
     */
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject deleteRecord(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        final TModel model = getModel();

        try {
            model.delete(rowId);

            final JsonViewObject jsonViewObject = JsonViewObject.successResult();

            jsonViewObject.setMessage(getMessageText("Delete.SUCCESS"));

            return jsonViewObject;
        } catch (final Throwable ex) {
            return JsonViewObject.failureResult(ex);
        }
    }

    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewForm(final HttpServletRequest httpServletRequest) {
        return defaultResult();
    }

    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject cancelForm(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return JsonViewObject.successResult();
    }

    @RequestMapping(params = "FileUploads", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject uploadDocument(
            @RequestParam("elemPath") final String propertyName,
            @RequestParam("files") final List<MultipartFile> multipartFiles,
            @RequestParam("removeFile") String removeFileName,
            @RequestParam("refreshMode") String refreshMode,
            @RequestParam("randnum") final String randno,
            @RequestParam("currentCount") String currentCount,
            @RequestParam("maxFileCount") final String maxFileCount,
            final HttpServletRequest httpServletRequest) {
        boolean isSingleFileUploadBox = false;
        try {
            bindModel(httpServletRequest);

            final JsonViewObject jsonViewObject = JsonViewObject.successResult();

            refreshMode = refreshMode.substring(refreshMode.length() - 1);

            if (!(currentCount.equals(MainetConstants.BLANK))) {
                isSingleFileUploadBox = false;

                currentCount = currentCount
                        .substring(currentCount.length() - 1);
            } else {
                isSingleFileUploadBox = true;
            }

            String newpropertyName = MainetConstants.BLANK;
            try {

                final int i = propertyName.lastIndexOf(MainetConstants.operator.COMMA);

                newpropertyName = propertyName.substring(i + 1,
                        propertyName.length());
            } catch (final Exception e) {
                newpropertyName = propertyName;
            }

            if (refreshMode.equals(MainetConstants.MENU.N)) {

                if (removeFileName.equals(MainetConstants.BLANK)) {

                    for (final Map.Entry<Long, String> entry : getModel()
                            .getFileListMap().entrySet()) {
                        if (entry.getKey().toString().equals(currentCount)) {
                            int fileCount = 0;

                            final String filesNames = entry.getValue();

                            for (final String retval : filesNames.split(MainetConstants.operator.COMMA)) {
                                fileCount = fileCount + 1;

                                if (retval.equals(multipartFiles.get(0)
                                        .getOriginalFilename())) {
                                    jsonViewObject.setUrl(MainetConstants.FILE_EXISTS);
                                    jsonViewObject.setMessage(fileList);
                                    return jsonViewObject;
                                }
                            }
                            if (!(filesNames.equals(MainetConstants.BLANK) || maxFileCount
                                    .equals(MainetConstants.BLANK))) {
                                if (fileCount >= Integer.parseInt(maxFileCount)) {
                                    jsonViewObject
                                            .setHiddenOtherVal(MainetConstants.MAX_FILE_COUNT);
                                    jsonViewObject.setUrl(ApplicationSession
                                            .getInstance().getMessage(
                                                    "attach.only")
                                            + " "
                                            + maxFileCount
                                            + " "
                                            + ApplicationSession.getInstance()
                                                    .getMessage("attach.msg1"));
                                    jsonViewObject.setMessage(fileList);
                                    return jsonViewObject;
                                }
                            }
                        }
                    }
                }

                final String outputFiles = getModel().doUploading(multipartFiles,
                        newpropertyName, removeFileName, randno, currentCount,
                        isSingleFileUploadBox);
                fileList = outputFiles;
                jsonViewObject.setMessage(outputFiles);
                jsonViewObject.setUrl(MainetConstants.BLANK);
                jsonViewObject.setHiddenOtherVal(MainetConstants.BLANK);
                if (!isSingleFileUploadBox) {
                    if (removeFileName.equals(MainetConstants.BLANK)) {
                        getModel().getFileListMap().put(
                                Long.valueOf(currentCount), outputFiles);
                    }
                }
            } else {
                if (isSingleFileUploadBox) {
                    jsonViewObject.setMessage(fileList);
                } else {
                    jsonViewObject.setMessage(getModel().getFileListMap().get(
                            Long.valueOf(currentCount)));
                }
            }
            removeFileName = null;

            return jsonViewObject;
        } catch (final Throwable ex) {
            return JsonViewObject.failureResult(ex);
        }

    }

    /**
     * This method is used when for non-ajax request.
     *
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final TModel model = getModel();

        try {
            if (model.saveForm()) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            }
        } catch (final Exception ex) {

            final StringWriter errors = new StringWriter();

            errors.toString();
            logger.error(ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return defaultMyResult();
    }

    @RequestMapping(params = "DeleteFile", method = RequestMethod.POST)
    public @ResponseBody void DeleteFile(
            @RequestParam("filename") final String filename,
            final HttpServletResponse httpServletResponse) {
        getModel().deleteFile(filename);
    }

    @RequestMapping(params = "draftEdit", method = RequestMethod.POST)
    public ModelAndView DraftEditForm(@RequestParam final long draftId,
            @RequestParam final long serviceId, @RequestParam final String serviceURL,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        try {
            if (getModel().doAuthorization(draftId)) {
                getModel().draftEditForm(draftId, serviceId, serviceURL);
            } else {
                getModel().addValidationError(ApplicationSession.getInstance().getMessage("abstract.entry.form.auth.user"));
            }
        } catch (final Throwable ex) {
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return super.defaultResult();

    }

    /**
     * Added by Manika Sawant... if amount is less than zero
     *
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(params = "noAmount", method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView noAmount(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        return new ModelAndView(MainetConstants.APPLICATION_REGISTER,MainetConstants.CommonConstants.COMMAND,
                getModel());
    }

    @RequestMapping(params = MainetConstants.REDIRECT_TO_PAY)
    public ModelAndView redirectToPayDetails(
            final HttpServletRequest httpServletRequest) {

        return new ModelAndView(MainetConstants.REDIRECT_TO_PAY);
    }

    @RequestMapping(method = RequestMethod.POST, params = "sessionCleanUp")
    public @ResponseBody void sessionCleanUp(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        sessionCleanup(httpServletRequest);
    }

    protected ModelAndView redirectToHome(final ActionResponse actResponse,
            final HttpServletRequest request, final String moduleUrl,
            final RedirectAttributes redirectAttributes, final String decision) {
        String message = MainetConstants.BLANK;
        if (actResponse != null) {
            if ((null != actResponse.getError())
                    && !MainetConstants.BLANK.equals(actResponse.getError())) {
                request.getSession().setAttribute(MainetConstants.DELETE_ERROR,
                        actResponse.getError());
            } else {
                final String actionKey = actResponse.getResponseData(MainetConstants.RESPONSE);
                final String decision_ = actResponse.getResponseData(MainetConstants.DECISION);
                if (null != actionKey) {
                    final String requestNo = actResponse.getResponseData(MainetConstants.REQUEST_NO);
                    if (null != requestNo) {
                        message = MainetConstants.REQUEST_NUMBER + requestNo + " " + decision_
                                + MainetConstants.SUCCESSFULLY;
                        redirectAttributes.addFlashAttribute(MainetConstants.SUCCESS_MESSAGE,
                                message);
                    }
                }
            }
        }
        return new ModelAndView(MainetConstants.REDIRECT + moduleUrl);
    }

    protected ModelAndView redirectToModulePage(final String moduleUrl, final Long id,
            final String taskId, final String taskName) {
        String pageUrl = MainetConstants.BLANK;
        if ((null != taskId) && !MainetConstants.BLANK.equals(taskId)) {
            pageUrl = MainetConstants.REDIRECT + moduleUrl + "?";
        } else if ((null != id) && (id != 0)) {
            pageUrl = MainetConstants.REDIRECT + moduleUrl + "?";
        } else {
            pageUrl = MainetConstants.REDIRECT + moduleUrl + "?";
        }
        return new ModelAndView(pageUrl);
    }

    protected String getRequestParam(final String key, final HttpServletRequest request,
            final ModelMap model) {
        String value = request.getParameter(key) != null ? request
                .getParameter(key) : "";
        if (value.isEmpty()) {
            value = (String) (request.getSession().getAttribute(key) != null ? request
                    .getSession().getAttribute(key) : "");
            value = (String) (value == null ? request.getAttribute(key) : "");
        }
        if (value.isEmpty() && (model != null)) {
            value = (String) model.get(key) != null ? (String) model.get(key)
                    : "";
        }
        return value;
    }

}
