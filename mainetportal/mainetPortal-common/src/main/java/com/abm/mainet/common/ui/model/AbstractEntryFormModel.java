package com.abm.mainet.common.ui.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;

public abstract class AbstractEntryFormModel<TEntity extends BaseEntity>
        extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    String deptEditMode;

    private TEntity entity;

    /**
     * for setting Help Doc Flag End
     */

    private final Class<TEntity> entityClass;

    private Map<Long, String> fileListMap = new LinkedHashMap<>(
            0);

    private boolean helpdocFlag;

    private List<String> listGuid = new ArrayList<>(
            0);

    protected org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(this
                    .getClass());

    private String ownerName;
    private String processStatus;

    private String sguid;

    List<File> signFileList = new ArrayList<>(
            0);

    @SuppressWarnings("unchecked")
    public AbstractEntryFormModel() {
        entityClass = (Class<TEntity>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    /**
     * To open form in new mode i.e, in add new mode.
     */

    public void addForm() {
        try {
            setEntity(entityClass.newInstance());
        } catch (InstantiationException
                | IllegalAccessException ex) {

            throw new FrameworkException(
                    "Unable to instanttiate new instance of "
                            + entityClass.getSimpleName()
                            + ". because of following exception :"
                            + ex.getLocalizedMessage(),
                    ex);
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * This function ids to cleare file list
     */
    public void cleareUploadFile() {

    }

    /**
     * To delete selected row for given id.
     * 
     * @param rowId the long literal containing row id.
     */
    public void delete(final long rowId) {
    }

    public void deleteFile(final String filename) {

    }

    public void deleteSectionFile(final String filename,
            final String filetype) {

    }

    /**
     * To perform file upload related action using given {@link MultipartFile} objects.
     * <p>
     * Also provide facility to do other action.
     * </p>
     * 
     * @param multipartFiles the {@link List} containing {@link MultipartFile} objects.
     * @param propertyName the {@link String} object containing property for which uploaded file(s) name to be set.
     * @param htmlObj
     * @param isUpload
     * @return {@link String} object containing uploaded file(s) name.
     */
    public String doUploading(
            final List<MultipartFile> multipartFiles,
            final String propertyName, String removeFileName,
            final String randno, final String count,
            final boolean isSingleFileUploadBox)
            throws IOException,
            FileSizeLimitExceededException {

        sguid = randno;

        if (!isSingleFileUploadBox) {
            listGuid.set(Integer.parseInt(count), randno);
        }

        boolean isProfileImage = false;

        for (final String excPath : MainetConstants.excludePath) {
            isProfileImage = excPath.equals(propertyName);
            if (isProfileImage) {
                break;
            }
        }

        String propertyValue = MainetConstants.BLANK;

        String propValue = MainetConstants.BLANK;

        String existingFilePath = MainetConstants.BLANK;

        final BeanWrapper wrapper = new BeanWrapperImpl(
                getEntity());

        final boolean foundProperty = wrapper
                .isReadableProperty(propertyName);

        if (foundProperty) {
            propertyValue = wrapper
                    .getPropertyValue(propertyName) != null ? wrapper
                            .getPropertyValue(propertyName)
                            .toString() : MainetConstants.BLANK;
        }

        boolean isNotNullOrNotEmpty = (propertyValue != null)
                && !MainetConstants.BLANK.equals(propertyValue);
        // Check if property is not null and already has some value in
        // database
        if (isNotNullOrNotEmpty) {
            // Check whether have more than one or more uploaded file(s) path
            // in
            // database.

            String tt = MainetConstants.BLANK;

            // If then split it and take first file path.
            for (final String tempFile : propertyValue.split(MainetConstants.operator.COMA)) {

                tt = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE,
                        tempFile);

                if (existingFilePath.equals(MainetConstants.BLANK)) {
                    // Get file path of already store file in database.
                    existingFilePath = MainetConstants.BLANK.equals(tt) ? tt
                            : tt + MainetConstants.operator.FORWARD_SLACE;
                }

                tt = StringUtility.staticStringAfterChar(
                        MainetConstants.operator.FORWARD_SLACE, tempFile);

                propValue += (MainetConstants.BLANK.equals(tt) ? tempFile : tt)
                        + MainetConstants.operator.COMA;

                tt = MainetConstants.BLANK;
            }

            // Get actual file(s) name which are stored in database.
            tt = StringUtility.staticRemoveLastChar(MainetConstants.operator.COMA,
                    propValue);

            propertyValue = MainetConstants.BLANK.equals(tt) ? propValue : tt;

        }

        // Get the file storage cache object from current entity.
        final FileStorageCache cache = getEntity()
                .getFileStorageCache();

        final ListIterator<MultipartFile> listIterator = multipartFiles
                .listIterator();

        MultipartFile file = null;
        File file2 = null;
        Iterator<Map.Entry<Long, String>> iter = null;
        Map.Entry<Long, String> entry = null;
        while (listIterator.hasNext()) {
            file = listIterator.next();

            if (removeFileName.equals(MainetConstants.BLANK)) {
                cache.getMultipleFiles()
                        .addRemove(
                                file,
                                MainetConstants.Transaction.Mode.ADD);
            }
            if (isNotNullOrNotEmpty) {
                propertyValue += MainetConstants.operator.COMA;

                if (!removeFileName.equals(MainetConstants.BLANK)) {
                    try {
                        final int s3 = removeFileName
                                .lastIndexOf(MainetConstants.operator.COMA);
                        removeFileName = removeFileName
                                .substring(s3);
                        removeFileName = removeFileName
                                .substring(1);

                        file2 = new File(removeFileName);
                        cache.getMultipleFiles()
                                .addRemove(
                                        file2,
                                        MainetConstants.Transaction.Mode.DELETE);

                        iter = getFileListMap().entrySet()
                                .iterator();
                        while (iter.hasNext()) {
                            entry = iter.next();

                            if (count
                                    .equalsIgnoreCase(entry
                                            .getKey()
                                            .toString())) {
                                iter.remove();
                            }

                        }

                        propertyValue = propertyValue
                                .replace(removeFileName
                                        + MainetConstants.operator.COMA, MainetConstants.BLANK);
                        propertyValue = propertyValue
                                .substring(0, propertyValue
                                        .length() - 1);
                    } catch (final Exception e) {
                        logger.error("Remove first file", e);
                    }
                }
            }
            if (removeFileName.equals(MainetConstants.BLANK)) {
                propertyValue += file.getOriginalFilename();
            }

            isNotNullOrNotEmpty = true;

        }

        // Make empty property value before updating.
        wrapper.setPropertyValue(propertyName, MainetConstants.BLANK);

        // Split the string and append with existing file path stored in
        // database.

        final String[] splitArr = propertyValue.split(MainetConstants.operator.COMA);
        propValue = MainetConstants.BLANK;
        for (int i = 0; i < splitArr.length; i++) {
            propValue = wrapper
                    .getPropertyValue(propertyName)
                    + existingFilePath + splitArr[i];

            if ((i + 1) < splitArr.length) {
                propValue += MainetConstants.operator.COMA;
            }

            wrapper.setPropertyValue(propertyName,
                    propValue);
        }
        return propertyValue;

    }

    /**
     * To open form in draft edit mode.
     * 
     * @param rowId the long literal containing draft id of object to be updated.
     * @param serviceId the long literal containing service id of that draft id. .
     */
    public void draftEditForm(final long rowId, final long serviceId,
            final String serviceURL) {

    }

    /**
     * To open form in edit mode.
     * 
     * @param rowId the long literal containing record id of object to be updated.
     */
    @Override
    public void editForm(final long rowId) {

    }

    public String getDeptEditMode() {
        return deptEditMode;
    }

    public TEntity getEntity() {
        return entity;
    }

    protected Class<TEntity> getEntityClass() {
        return entityClass;
    }

    /**
     * @return the fileListMap
     */
    public Map<Long, String> getFileListMap() {
        return fileListMap;
    }

    /**
     * @return the listGuid
     */
    public List<String> getListGuid() {
        return listGuid;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @return the processStatus
     */
    @Override
    public String getProcessStatus() {
        return processStatus;
    }

    public String getUUIDNo() {

        if (hasValidationErrors()) {
            return sguid;
        } else {
            final UUID guid = UUID.randomUUID();
            return guid.toString();
        }
    }

    public String getUUIDNo(final int count) {

        if (hasValidationErrors()) {
            return listGuid.get(count);

        } else {

            final UUID guid = UUID.randomUUID();

            listGuid.add(guid.toString());

            return guid.toString();
        }
    }

    /**
     * To check corresponding form is in add mode or not.
     * 
     * @return {@link Boolean} <code>true</code> if form is in add mode otherwise <code>false</code>.
     */
    public boolean isAddMode() {
        return entity.getId() == 0;
    }

    /**
     * for setting Help Doc Flag start
     */

    public boolean isHelpdocFlag() {
        return helpdocFlag;
    }

    /**
     * To check corresponding form is in update mode or not.
     * 
     * @return {@link Boolean} <code>false</code> if form is in update mode otherwise <code>true</code>.
     */
    public boolean isUpdateMode() {
        return !isAddMode();
    }

    @Override
    public boolean saveForm() {
        return false;
    }

    /**
     * To save or update form information.
     * 
     * @param bindingResult the {@link BindingResult} object which containing validation errors.
     * @return {@link Boolean} <code>true</code> if save/updated successfully otherwise <code>false</code>.
     */
    public boolean saveOrUpdateForm() {
        return false;
    }

    public void setDeptEditMode(final String deptEditMode) {
        this.deptEditMode = deptEditMode;
    }

    public void setEntity(final TEntity entity) {
        this.entity = entity;
    }

    /**
     * @param fileListMap the fileListMap to set
     */
    public void setFileListMap(final Map<Long, String> fileListMap) {
        this.fileListMap = fileListMap;
    }

    public void setHelpdocFlag(final boolean helpdocFlag) {
        this.helpdocFlag = helpdocFlag;
    }

    /**
     * @param listGuid the listGuid to set
     */
    public void setListGuid(final List<String> listGuid) {
        this.listGuid = listGuid;
    }

    @Override
    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @param processStatus the processStatus to set
     */
    @Override
    public void setProcessStatus(final String processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * To open form in view mode.
     * 
     * @param rowId the long literal containing record id of object to be updated.
     */
    public void viewForm(final long rowId) {

    }
    
    public boolean getMakerCheckerFlag() {
    	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getEmployee().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            return true;
        } else {
        	return false;
        }
    	
    }

}
