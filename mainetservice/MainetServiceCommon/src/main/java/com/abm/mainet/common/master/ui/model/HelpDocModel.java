package com.abm.mainet.common.master.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.master.service.ICommonHelpDocsService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.master.ui.validator.HelpDocValidator;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")

public class HelpDocModel extends AbstractEntryFormModel<CommonHelpDocs> implements Serializable {
    private static final long serialVersionUID = -3002245014338527249L;

    private static final Logger LOG = LoggerFactory.getLogger(HelpDocModel.class);

    @Autowired
    private ICommonHelpDocsService iHelpDocService;
    private String modeOfType;

    private String selectedService;

    private String moduleName;

    private Map<Long, String> serviceNames;

    private Map<String, String> nodes;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDirectoryTree() {

        String moduleName = getEntity().getModuleName();
        if (moduleName.contains(MainetConstants.operator.QUE_MARK))
            moduleName = moduleName.replaceAll(MainetConstants.operator.DOUBLE_BACKWARD_SLACE + MainetConstants.operator.QUE_MARK,
                    MainetConstants.operator.UNDER_SCORE);
        return UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.HelpDoc.HELPDOC + MainetConstants.FILE_PATH_SEPARATOR + moduleName;
    }

    @Override
    public void addForm() {
        setEntity(new CommonHelpDocs());
    }

    public String getModeOfType() {
        return modeOfType;
    }

    public void setModeOfType(final String modeOfType) {
        this.modeOfType = modeOfType;
    }

    @Override
    public boolean saveForm() {

        final CommonHelpDocs helpDocs = getEntity();

        if (getModeOfType().equalsIgnoreCase("A") && !getEntity().getModuleName().equalsIgnoreCase(MainetConstants.BLANK)) {

            final boolean resultmoduleName = iHelpDocService.checkExistingModuleName(getEntity().getModuleName(),
                    UserSession.getCurrent().getOrganisation());
            setSelectedService(getEntity().getModuleName());

            if (resultmoduleName) {
                addValidationError((getAppSession().getMessage("helpDoc.fileExist")));
                return false;
            } else {
                validateBean(helpDocs, HelpDocValidator.class);
                if (hasValidationErrors()) {
                    return false;
                }
            }
        }

        final boolean status = filledFiles(FileUploadUtility.getCurrent().getFileMap());

        return status;
    }

    @Override
    public void editForm(final long rowId) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setEntity(iHelpDocService.getCommonHelpDocs(rowId, UserSession.getCurrent().getOrganisation()));

    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
        setNodes(iHelpDocService.getAllNodes(UserSession.getCurrent().getLanguageId()));
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
    }

    public Map<Long, String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(final Map<Long, String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    private boolean filledFiles(final Map<Long, Set<File>> files) {
        int fileCount = 0;
        CommonHelpDocs commonDocs = null;
        if ((files != null) && !files.isEmpty()) {
            List<File> list = null;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                list = new ArrayList<>(entry.getValue());

                final Iterator<File> setFilesItr = entry.getValue().iterator();

                while (setFilesItr.hasNext()) {
                    String tempDirPath = MainetConstants.operator.EMPTY;
                    final File multipartFile = setFilesItr.next();

                    if ((fileCount == 0) && getModeOfType().equalsIgnoreCase("A")) {
                        commonDocs = new CommonHelpDocs();
                        commonDocs.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
                        commonDocs.updateAuditFields();
                        commonDocs.setModuleName(getEntity().getModuleName());
                        commonDocs.setDeptCode(getEntity().getDeptCode());
                    }
                    if ((fileCount == 0) && getModeOfType().equalsIgnoreCase("E")) {
                        commonDocs = getEntity();
                        commonDocs.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
                        commonDocs.updateAuditFields();

                    }

                    for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent().getFileUploadSet()) {
                        if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                            boolean pathStatus = true;

                            if (pathStatus) {
                                if (fileUploadClass.getFolderName().equalsIgnoreCase(MainetConstants.FileCount.ZERO)) {
                                    tempDirPath = getDirectoryTree() + MainetConstants.FILE_PATH_SEPARATOR
                                            + MainetConstants.REG_ENG.ENGLISH;
                                    commonDocs.setFileNameEng(multipartFile.getName());
                                    final String ext = StringUtility
                                            .staticStringAfterChar(MainetConstants.operator.DOT, multipartFile.getName())
                                            .toLowerCase();
                                    commonDocs.setFileTypeEng(getFileType(ext));
                                    commonDocs.setFilePath(tempDirPath);
                                }

                                else if (fileUploadClass.getFolderName().equalsIgnoreCase(MainetConstants.FileCount.ONE)) {
                                    tempDirPath = getDirectoryTree() + MainetConstants.FILE_PATH_SEPARATOR
                                            + MainetConstants.REG_ENG.REGIONAL;
                                    final String ext = StringUtility
                                            .staticStringAfterChar(MainetConstants.operator.DOT, multipartFile.getName())
                                            .toLowerCase();
                                    commonDocs.setFileTypeReg(getFileType(ext));
                                    commonDocs.setFileNameReg(multipartFile.getName());
                                    commonDocs.setFilePathReg(tempDirPath);
                                }
                                pathStatus = false;
                            }
                        }
                    }
                    fileCount++;
                    try {
                        getFileNetClient().uploadFileList(list, tempDirPath);
                    } catch (final Exception e) {
                        LOG.error(MainetConstants.ERROR_OCCURED, e);
                        return false;
                    }
                }
            }
            return iHelpDocService.saveHelpDoc(commonDocs, getModeOfType());
        } else if (getModeOfType().equalsIgnoreCase("E")) {
            return true;
        }
        return false;
    }

    private char getFileType(final String fileExtension) {
        if (fileExtension.equals(MainetConstants.FileExt.PDF)) {
            return MainetConstants.FileTypes.PDF;
        } else if (fileExtension.equals(MainetConstants.FileExt.XLS) || fileExtension.equals(MainetConstants.FileExt.XLSX)) {
            return MainetConstants.FileTypes.XLS;
        } else if (fileExtension.equals(MainetConstants.FileExt.DOCX) || fileExtension.equals(MainetConstants.FileExt.DOC)) {
            return MainetConstants.FileTypes.DOC;
        } else {
            return MainetConstants.FileTypes.OTHER;
        }

    }

    public List<LookUp> getFilesDetails() {

        final List<LookUp> detailsList = new ArrayList<>();
        LookUp lookUp = null;
        final CommonHelpDocs doc = getEntity();
        lookUp = new LookUp(1, doc.getFilePath());
        lookUp.setLookUpDesc(doc.getFilePath());
        lookUp.setLookUpCode(doc.getFileNameEng());
        detailsList.add(lookUp);

        if ((doc.getFileNameReg() != null) && !doc.getFileNameReg().isEmpty()) {
            lookUp = new LookUp(2, doc.getFilePathReg());
            lookUp.setLookUpDesc(doc.getFilePathReg());
            lookUp.setLookUpCode(doc.getFileNameReg());
            detailsList.add(lookUp);
        }

        return detailsList;
    }

    public Map<String, String> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, String> nodes) {
        this.nodes = nodes;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(final String selectedService) {
        this.selectedService = selectedService;
    }

}
