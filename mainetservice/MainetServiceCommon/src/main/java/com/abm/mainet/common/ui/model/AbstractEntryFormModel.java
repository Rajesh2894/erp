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
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.utility.FileStorageCache;
import com.abm.mainet.common.utility.StringUtility;

public abstract class AbstractEntryFormModel<TEntity extends BaseEntity> extends AbstractFormModel {
    
    private static final long serialVersionUID = 1L;

    protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());


    private String sguid;

    private List<String> listGuid = new ArrayList<>(0);

    private Map<Long, String> fileListMap = new LinkedHashMap<>(0);

    private String processStatus;

    private String ownerName;

    String deptEditMode;

    private String payUserviceName;

    List<File> signFileList = new ArrayList<>(0);

    private boolean helpdocFlag;

    public static final String KEYSTORE = "src" + File.separator + "com" + File.separator + "abm" + File.separator + "sign"
            + File.separator + "CTest" + File.separator + "digitalpdfsign.pfx";
    public static final String MAIL_KEYSTORE = "src" + File.separator + "com" + File.separator + "abm" + File.separator + "sign"
            + File.separator + "CTest" + File.separator + "usersign.pfx";
    public static final char[] PASSWORD = "abcd1234".toCharArray();
    

    /**
     * for setting Help Doc Flag start
     */

    public boolean isHelpdocFlag() {
        return helpdocFlag;
    }

    public void setHelpdocFlag(final boolean helpdocFlag) {
        this.helpdocFlag = helpdocFlag;
    }

    /**
     * for setting Help Doc Flag End
     */


    private final Class<TEntity> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractEntryFormModel() {
        entityClass = (Class<TEntity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Class<TEntity> getEntityClass() {
        return entityClass;
    }

    private TEntity entity;

    public TEntity getEntity() {
        return entity;
    }

    public void setEntity(final TEntity entity) {
        this.entity = entity;
    }

    /**
     * To open form in new mode i.e, in add new mode.
     */

    public void addForm() {
        try {
            setEntity(entityClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ex) {

            throw new FrameworkException("Unable to instanttiate new instance of " + entityClass.getSimpleName()
                    + ". because of following exception :" + ex.getLocalizedMessage(), ex);
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * To open form in edit mode.
     * @param rowId the long literal containing record id of object to be updated.
     */
    @Override
    public void editForm(final long rowId) {

    }

    /**
     * To open form in draft edit mode.
     * @param rowId the long literal containing draft id of object to be updated.
     * @param serviceId the long literal containing service id of that draft id. .
     */
    public void draftEditForm(final long rowId, final long serviceId, final String serviceURL) {

    }

    /**
     * To open form in view mode.
     * @param rowId the long literal containing record id of object to be updated.
     */
    public void viewForm(final long rowId) {

    }

    /**
     * To save or update form information.
     * @param bindingResult the {@link BindingResult} object which containing validation errors.
     * @return {@link Boolean} <code>true</code> if save/updated successfully otherwise <code>false</code>.
     */
    public boolean saveOrUpdateForm() {
        return false;
    }

    @Override
    public boolean saveForm() {
        return false;
    }

    /**
     * To delete selected row for given id.
     * @param rowId the long literal containing row id.
     */
    public void delete(final long rowId) {
    }

    /**
     * To check corresponding form is in add mode or not.
     * @return {@link Boolean} <code>true</code> if form is in add mode otherwise <code>false</code>.
     */
    public boolean isAddMode() {
        return (entity.getId() == 0);
    }

    /**
     * To check corresponding form is in update mode or not.
     * @return {@link Boolean} <code>false</code> if form is in update mode otherwise <code>true</code>.
     */
    public boolean isUpdateMode() {
        return !isAddMode();
    }

    /**
     * To perform file upload related action using given {@link MultipartFile} objects.
     * <p>
     * Also provide facility to do other action.
     * </p>
     * @param multipartFiles the {@link List} containing {@link MultipartFile} objects.
     * @param propertyName the {@link String} object containing property for which uploaded file(s) name to be set.
     * @param htmlObj
     * @param isUpload
     * @return {@link String} object containing uploaded file(s) name.
     */
    public String doUploading(final List<MultipartFile> multipartFiles, final String propertyName, String removeFileName,
            final String randno,
            final String count, final boolean isSingleFileUploadBox) throws IOException, FileSizeLimitExceededException {
        

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

        final BeanWrapper wrapper = new BeanWrapperImpl(getEntity());

        final boolean foundProperty = wrapper.isReadableProperty(propertyName);

        if (foundProperty) {
            propertyValue = wrapper.getPropertyValue(propertyName) != null ? wrapper.getPropertyValue(propertyName).toString()
                    : MainetConstants.BLANK;
        }

        boolean isNotNullOrNotEmpty = ((propertyValue != null) && !MainetConstants.BLANK.equals(propertyValue));
        // Check if property is not null and already has some value in
        // database
        if (isNotNullOrNotEmpty) {
            // Check whether have more than one or more uploaded file(s) path
            // in
            // database.

            String tt = MainetConstants.BLANK;

            // If then split it and take first file path.
            for (final String tempFile : propertyValue.split(MainetConstants.operator.COMMA)) {
                

                tt = StringUtility.getStringBeforeChar("/", tempFile);

                if (existingFilePath.equals(MainetConstants.BLANK)) {
                    // Get file path of already store file in database.
                    existingFilePath = (MainetConstants.BLANK.equals(tt)) ? tt : tt + "/";
                }

                tt = StringUtility.staticStringAfterChar("/", tempFile);

                propValue += ((MainetConstants.BLANK.equals(tt)) ? tempFile : tt) + MainetConstants.operator.COMMA;

                tt = MainetConstants.BLANK;
            }

            // Get actual file(s) name which are stored in database.
            tt = StringUtility.staticRemoveLastChar(MainetConstants.operator.COMMA, propValue);

            propertyValue = (MainetConstants.BLANK.equals(tt)) ? propValue : tt;

        }

        // Get the file storage cache object from current entity.
        final FileStorageCache cache = getEntity().getFileStorageCache();

        final ListIterator<MultipartFile> listIterator = multipartFiles.listIterator();

        MultipartFile file = null;
        File file2 = null;
        Iterator<Map.Entry<Long, String>> iter = null;
        Map.Entry<Long, String> entry = null;
        while (listIterator.hasNext()) {
            file = listIterator.next();

            if (removeFileName.equals(MainetConstants.BLANK)) {
                cache.getMultipleFiles().addRemove(file, MainetConstants.Transaction.Mode.ADD);
            }
            if (isNotNullOrNotEmpty) {
                propertyValue += MainetConstants.operator.COMMA;

                if (!removeFileName.equals(MainetConstants.BLANK)) {
                    try {
                        final int s3 = removeFileName.lastIndexOf(MainetConstants.operator.COMMA);
                        removeFileName = removeFileName.substring(s3);
                        removeFileName = removeFileName.substring(1);

                        file2 = new File(removeFileName);
                        cache.getMultipleFiles().addRemove(file2, MainetConstants.Transaction.Mode.DELETE);
                        iter = getFileListMap().entrySet().iterator();
                        while (iter.hasNext()) {
                            entry = iter.next();

                            if (count.equalsIgnoreCase(entry.getKey().toString())) {
                                iter.remove();
                            }

                        }

                        propertyValue = propertyValue.replace(removeFileName + MainetConstants.operator.COMMA, MainetConstants.BLANK);
                        propertyValue = propertyValue.substring(0, propertyValue.length() - 1);
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

        final String[] splitArr = propertyValue.split(MainetConstants.operator.COMMA);
        propValue = MainetConstants.BLANK;
        for (int i = 0; i < splitArr.length; i++) {
            propValue = wrapper.getPropertyValue(propertyName) + (existingFilePath + splitArr[i]);

            if ((i + 1) < splitArr.length) {
                propValue += MainetConstants.operator.COMMA;
            }

            wrapper.setPropertyValue(propertyName, propValue);
        }
        return propertyValue;


    }

    public final void setFilePath(final Object beanClass, final String propertyName, final String directoryTree)
            throws FrameworkException {

        if (beanClass == null) {
            throw new FrameworkException("Wrapping 'beanClass' cannot be null.");
        }

        final BeanWrapper wrapper = new BeanWrapperImpl(beanClass);

        final boolean foundProperty = wrapper.isReadableProperty(propertyName);

        if (foundProperty) {
            final Object value = wrapper.getPropertyValue(propertyName);

            if (value != null) {
                
                final String[] tempArr = value.toString().split(MainetConstants.operator.COMMA);

                wrapper.setPropertyValue(propertyName, MainetConstants.BLANK);

                String[] splitTempArrBySlash;
                String path;
                for (int i = 0; i < tempArr.length; i++) {
                    path = MainetConstants.BLANK;
                    splitTempArrBySlash = tempArr[i].split("/");

                    if (splitTempArrBySlash.length == 1) {
                        path = directoryTree + "/" + tempArr[i];
                    }

                    else {
                        path = directoryTree + "/" + splitTempArrBySlash[splitTempArrBySlash.length - 1];
                    }

                    if (splitTempArrBySlash.length == 0) {
                        path = directoryTree + "/" + tempArr[i];
                    }

                    if (i < (tempArr.length - 1)) {
                        path += MainetConstants.operator.COMMA;
                    }

                    wrapper.setPropertyValue(propertyName, wrapper.getPropertyValue(propertyName) + path);

                }


            }

        } else {
            throw new FrameworkException(
                    "Unable to found property " + propertyName + " of class " + beanClass.getClass().getSimpleName());
        }
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

    public void deleteFile(final String filename) {

    }


    /**
     * @return the processStatus
     */
    @Override
    public String getProcessStatus() {
        return processStatus;
    }

    /**
     * @param processStatus the processStatus to set
     */
    @Override
    public void setProcessStatus(final String processStatus) {
        this.processStatus = processStatus;
    }


    /**
     * @return the listGuid
     */
    public List<String> getListGuid() {
        return listGuid;
    }

    /**
     * @param listGuid the listGuid to set
     */
    public void setListGuid(final List<String> listGuid) {
        this.listGuid = listGuid;
    }

    /**
     * @return the fileListMap
     */
    public Map<Long, String> getFileListMap() {
        return fileListMap;
    }

    /**
     * @param fileListMap the fileListMap to set
     */
    public void setFileListMap(final Map<Long, String> fileListMap) {
        this.fileListMap = fileListMap;
    }

    /**
     * This function ids to cleare file list
     */
    public void cleareUploadFile() {

    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPayUserviceName() {
        return payUserviceName;
    }

    public void setPayUserviceName(final String payUserviceName) {
        this.payUserviceName = payUserviceName;
    }

    public void deleteSectionFile(final String filename, final String filetype) {

    }

    public String getDeptEditMode() {
        return deptEditMode;
    }

    public void setDeptEditMode(final String deptEditMode) {
        this.deptEditMode = deptEditMode;
    }

}
