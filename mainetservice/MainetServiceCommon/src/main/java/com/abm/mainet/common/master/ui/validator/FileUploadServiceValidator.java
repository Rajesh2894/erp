package com.abm.mainet.common.master.ui.validator;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.mapper.FileUploadValidator;
import com.abm.mainet.common.utility.ApplicationContextProvider;


/**
 * @author Vikrant.Thakur
 * @since 07 October 2014. Used for FileUploadService Check List Service
 *
 */

@Component
public class FileUploadServiceValidator {

    public void validateUpload(final BindingResult bindingResult) {

        final Iterator<FileUploadClass> fileUploadItr = FileUploadUtility.getCurrent().getFileUploadSet().iterator();
        ObjectError objectError = null;
        FileUploadClass fileUploadClass = null;
        while (fileUploadItr.hasNext()) {
            fileUploadClass = fileUploadItr.next();

            if (fileUploadClass.getCheckListMandatoryDoc().equals(MainetConstants.IsUploaded.UPLOADED)) {
                boolean check = false;

                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

                    if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                        check = true;
                        break;
                    }

                }

                if (!check) {
                    objectError = new ObjectError(MainetConstants.operator.EMPTY,
                            FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.FILE_UPLOAD_VALIDATION_MSG)
                                    + fileUploadClass.getCheckListDesc());
                    bindingResult.addError(objectError);
                }
            }
        }
    }

    /**
     * Create from FileUploadService bean.
     * @return
     */
    public static FileUploadServiceValidator getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(FileUploadServiceValidator.class);
    }

    public void sessionCleanUpForFileUpload() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
    }

}
