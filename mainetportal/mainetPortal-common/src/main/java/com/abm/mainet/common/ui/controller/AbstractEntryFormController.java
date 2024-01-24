package com.abm.mainet.common.ui.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author Pranit.Mhatre
 * @param <TModel>
 */
public abstract class AbstractEntryFormController<TModel extends AbstractEntryFormModel<? extends BaseEntity>>
        extends AbstractController<TModel> {

    private String fileList;

    protected Logger logger = Logger.getLogger(this
            .getClass());

    @RequestMapping(params = "add", method = RequestMethod.POST)
    public ModelAndView addForm(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        getModel().addForm();

        return super.defaultResult();
    }

    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject cancelForm(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return JsonViewObject.successResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "cleareFile")
    public @ResponseBody String cleareFile(
            final HttpServletRequest httpServletRequest) {
        int length = 0;
        final String folderPath = FileUploadUtility.getCurrent()
                .getExistingFolderPath();
        FileUploadUtility.getCurrent().getFileMap().clear();
        if (folderPath != null) {
            final File file = new File(folderPath);
            if (file != null) {
                length = file.list() != null ? file.list().length
                        : 0;
            }
            FileUploadUtility.getCurrent()
                    .setFolderCreated(false);
        }
        return length + MainetConstants.BLANK;
    }

    /**
     * To delete the selected row id record.
     * 
     * @param rowId the long literal containing row id.
     * @param httpServletRequest the {@link HttpServletRequest} request object provided by servlet container.
     * @return {@link JsonViewObject} object which containing status and response message.
     */
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject deleteRecord(
            @RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        final TModel model = getModel();

        try {
            model.delete(rowId);

            final JsonViewObject jsonViewObject = JsonViewObject
                    .successResult();

            jsonViewObject
                    .setMessage(getMessageText("Delete.SUCCESS"));

            return jsonViewObject;
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return JsonViewObject.failureResult(ex);
        }
    }

    @RequestMapping(params = "edit", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView editForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        if (getModel().doAuthorization(rowId)) {
            getModel().editForm(rowId);
        } else {
            getModel().addValidationError(
                    getApplicationSession().getMessage("not.authorized.user"));
        }

        return super.defaultResult();

    }

    /**
     * Added by Manika Sawant... if amount is less than zero
     * 
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(params = "noAmount", method = {
            RequestMethod.POST, RequestMethod.GET })
    public ModelAndView noAmount(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        return new ModelAndView("ApplicationRegister",
                MainetConstants.FORM_NAME, getModel());
    }

    protected String onSave() {
        return getViewName();
    }

    /**
     * This method is used when for non-ajax request.
     * 
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final TModel model = getModel();

        if (model.saveForm()) {
            return jsonResult(JsonViewObject
                    .successResult(model
                            .getSuccessMessage()));
        }

        return defaultMyResult();
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final TModel model = getModel();

        if (model.saveOrUpdateForm()) {
            return jsonResult(JsonViewObject
                    .successResult(model
                            .getSuccessMessage()));
        }

        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "sessionCleanUp")
    public @ResponseBody void sessionCleanUp(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        sessionCleanup(httpServletRequest);
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

            final JsonViewObject jsonViewObject = JsonViewObject
                    .successResult();

            refreshMode = refreshMode.substring(refreshMode
                    .length() - 1);

            if (!currentCount.equals(MainetConstants.BLANK)) {
                isSingleFileUploadBox = false;

                currentCount = currentCount
                        .substring(currentCount.length() - 1);
            } else {
                isSingleFileUploadBox = true;
            }

            String newpropertyName = MainetConstants.BLANK;
            try {

                final int i = propertyName.lastIndexOf(MainetConstants.operator.COMA);

                newpropertyName = propertyName.substring(
                        i + 1, propertyName.length());
            } catch (final Exception e) {
                logger.error(MainetConstants.ERROR_OCCURED, e);
                newpropertyName = propertyName;
            }

            if (refreshMode.equals(MainetConstants.IsUploaded.NOT_UPLOADED)) {

                if (removeFileName.equals(MainetConstants.BLANK)) {

                    for (final Map.Entry<Long, String> entry : getModel()
                            .getFileListMap().entrySet()) {
                        if (entry.getKey().toString()
                                .equals(currentCount)) {
                            int fileCount = 0;

                            final String filesNames = entry
                                    .getValue();

                            for (final String retval : filesNames
                                    .split(MainetConstants.operator.COMA)) {
                                fileCount = fileCount + 1;

                                if (retval
                                        .equals(multipartFiles
                                                .get(0)
                                                .getOriginalFilename())) {
                                    jsonViewObject
                                            .setUrl("FILEEXISTS");
                                    jsonViewObject
                                            .setMessage(fileList);
                                    return jsonViewObject;
                                }
                            }
                            if (!(filesNames.equals(MainetConstants.BLANK) || maxFileCount
                                    .equals(MainetConstants.BLANK))) {
                                if (fileCount >= Integer
                                        .parseInt(maxFileCount)) {
                                    jsonViewObject
                                            .setHiddenOtherVal("MAXFILECOUNT");
                                    jsonViewObject
                                            .setUrl(ApplicationSession
                                                    .getInstance()
                                                    .getMessage(
                                                            "attach.only")
                                                    + MainetConstants.WHITE_SPACE
                                                    + maxFileCount
                                                    + MainetConstants.WHITE_SPACE
                                                    + ApplicationSession
                                                            .getInstance()
                                                            .getMessage(
                                                                    "attach.msg1"));
                                    jsonViewObject
                                            .setMessage(fileList);
                                    return jsonViewObject;
                                }
                            }
                        }
                    }
                }

                final String outputFiles = getModel()
                        .doUploading(multipartFiles,
                                newpropertyName,
                                removeFileName, randno,
                                currentCount,
                                isSingleFileUploadBox);
                fileList = outputFiles;
                jsonViewObject.setMessage(outputFiles);
                jsonViewObject.setUrl(MainetConstants.BLANK);
                jsonViewObject.setHiddenOtherVal(MainetConstants.BLANK);
                if (!isSingleFileUploadBox) {
                    if (removeFileName.equals(MainetConstants.BLANK)) {
                        getModel().getFileListMap().put(
                                Long.valueOf(currentCount),
                                outputFiles);
                    }
                }
            } else {
                if (isSingleFileUploadBox) {
                    jsonViewObject.setMessage(fileList);
                } else {
                    jsonViewObject
                            .setMessage(getModel()
                                    .getFileListMap()
                                    .get(Long
                                            .valueOf(currentCount)));
                }
            }
            removeFileName = null;

            return jsonViewObject;
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return JsonViewObject.failureResult(ex);
        }

    }

    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewForm(
            final HttpServletRequest httpServletRequest) {
        return defaultResult();
    }

    @RequestMapping(params = "viewMode", method = RequestMethod.POST)
    public ModelAndView viewForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        if (getModel().doAuthorization(rowId)) {
            getModel().viewForm(rowId);
        } else {
            getModel().addValidationError(
                    getApplicationSession().getMessage("not.authorized.user"));
        }
        return super.defaultResult();

    }

}
